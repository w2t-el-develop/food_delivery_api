# Git Incident Report and Recovery Runbook

## Document purpose

This document explains the Git incident involving the order-management, menu, and menu-item changes on November 13, 2026. It is written for an engineer who is new to the project and needs to understand:

- what happened;
- why the changes appeared to be lost;
- how the changes were recovered;
- how the investigation was performed; and
- how to investigate a similar incident without destroying recoverable data.

The recovery described here was local only. No recovery branch was pushed to the remote repository during the incident response.

## Executive summary

The changes were not lost because of an ordinary branch switch. The main incident occurred after `feature/order-managemnt` was fast-forwarded to the latest `develop` commit and was then reset to an older commit using the default, mixed form of `git reset`.

A mixed reset does three relevant things:

1. It moves the current branch pointer to the selected commit.
2. It resets the Git index to that commit.
3. It leaves working-tree files in place.

Consequently, files that had already existed in newer commits appeared as modified or untracked files against the older commit. A subsequent checkout of the old commit left `HEAD` detached, which made the repository state more confusing and made the work appear to have disappeared from its feature branches.

Most work was still present in the working tree, existing commits, and stashes. The completed total-price implementation was a separate case: it had never been recorded in a Git commit, stash, or dangling Git object. IntelliJ IDEA had automatically preserved it in a shelf created before an earlier checkout. That shelf was the authoritative recovery source for the missing implementation.

## Impact

At the beginning of the investigation:

- `HEAD` was detached at commit `bcd611a`;
- the local `feature/order-managemnt` pointer had been moved backward;
- 30 tracked paths appeared modified or deleted;
- approximately 35 paths appeared untracked;
- order, menu, menu-item, exception, configuration, and supporting code were mixed in one working tree; and
- `TotalPriceCalculationHandler` contained an incomplete `.map(order)` expression and did not compile.

No remote history was rewritten during recovery. Existing stashes were preserved.

## Incident timeline

Times below are from the Git reflog in the `Africa/Cairo` workspace timezone.

### November 13, 2026

- **15:09:11** — local `develop` was pulled and fast-forwarded to `2b15346`.
- **15:09:25** — the checkout moved from `develop` to `feature/order-managemnt`.
- **15:09:57** — `feature/order-managemnt` was fast-forwarded from `9cbafce` to `2b15346` by merging `origin/develop`.
- **15:43:55** — `git reset` moved `feature/order-managemnt` backward to `bcd611a`.
- **15:45:03** — the checkout moved from `feature/order-managemnt` to commit `bcd611a` directly, producing a detached `HEAD`.
- **15:53:58** — the complete working tree was preserved on safety branch `recovery/order-menu-2026-11-13` in commit `e45f6a1`.
- **15:56:48** — the exact recovered tree was placed on top of `develop`, producing branch `recovered/order-menu-work` and commit `93591f6`.
- **16:02:34** — the total-price implementation and its companion order-item changes were restored from IntelliJ's shelf and committed as `c0382b3`.

## Root cause analysis

### Primary root cause: branch pointer moved backward with a mixed reset

The reflog contained the decisive evidence:

```text
2b15346 ... merge origin/develop: Fast-forward
bcd611a ... reset: moving to bcd611a...
bcd611a ... checkout: moving from feature/order-managemnt to bcd611a...
```

The reset moved the feature branch from the newer `develop` state back to an older order commit. Because no reset mode was specified, Git used its default mixed behavior. The branch and index moved backward, but the files remained in the working tree.

This explains why many files looked newly created or modified even though much of their content already existed in newer commits. The repository was comparing a newer working tree with an older commit.

### Contributing factor: detached `HEAD`

Checking out a commit hash rather than a branch placed the repository in detached-`HEAD` state. Detached `HEAD` is valid, but new commits made there are not attached to a normal branch unless a branch is created. It also makes it harder for an engineer to understand which feature branch owns the current files.

The detached checkout did not cause the original mixed-reset behavior, but it increased the risk of losing subsequent commits and contributed to the perception that the changes had vanished.

### Contributing factor: unfinished work was never recorded in Git

The completed total-price implementation was not present in:

- any current branch;
- either existing stash;
- the reflogs;
- reachable Git blobs; or
- unreachable/dangling Git blobs.

Git could therefore not recover that version. It was found in:

```text
.idea/shelf/Uncommitted_changes_before_Checkout_at_10_24_26,_9_51 PM_[Changes]/shelved.patch
```

IntelliJ had automatically shelved the work before a checkout. This is why Git-only recovery initially produced the incomplete handler.

### What was not the root cause

Normal `git switch` or `git checkout` operations do not ordinarily delete tracked, committed work. Git usually blocks a switch if it would overwrite uncommitted changes. In this incident, the important state change was the reset of the branch pointer, followed by a detached checkout. Describing the issue only as “changes lost while switching branches” would hide the actionable root cause.

## Investigation methodology

The investigation followed an evidence-first, least-destructive process.

### 1. Freeze the repository state

No reset, cleanup, stash pop, garbage collection, or force push was performed. The first objective was to avoid destroying working-tree files, reflog entries, dangling objects, or IDE recovery data.

Initial commands:

```bash
git status --short --branch
git branch --all --verbose --no-abbrev
git stash list
git reflog --all --date=iso -n 80
```

These established the current commit, detached state, branch pointers, stashes, and recent branch movement.

### 2. Inventory every visible change

The working tree and untracked files were inventoried separately:

```bash
git diff --stat
git diff --name-status
git ls-files --others --exclude-standard
```

This showed that the allegedly lost order and menu work was still physically present.

### 3. Inspect independent recovery sources

Stashes were inspected without applying or dropping them:

```bash
git stash show --stat 'stash@{0}'
git stash show --name-status --include-untracked 'stash@{0}'
```

The two pre-existing stashes were left untouched because applying them before preserving the working tree could have introduced conflicts or obscured provenance.

### 4. Reconstruct branch movement with the reflog

The reflog, rather than commit history alone, revealed the reset and detached checkout. Reflogs record local movements of `HEAD` and branch references even when ordinary history no longer shows those movements.

Useful commands:

```bash
git reflog --date=iso
git reflog show --all --date=iso
git reflog show feature/order-managemnt --date=iso
```

### 5. Preserve the exact working tree before reorganizing it

Once the files were confirmed to be present, an explicit safety branch and commit were created:

```bash
git switch -c recovery/order-menu-2026-11-13
git add -A
git commit -m "recovery: preserve order, menu, and menu-item worktree"
```

This produced commit `e45f6a1`. It is an exact snapshot of the discovered working tree, including changes that were later identified as already belonging to `develop`.

This safety commit was created before attempting to separate recovered work from changes already present on other branches.

### 6. Rebase the recovered tree conceptually onto the correct base

The intended base was `develop` at `2b15346`. A new recovery branch was created from that base, and the exact saved tree was restored onto it:

```bash
git switch -c recovered/order-menu-work develop
git restore --source=recovery/order-menu-2026-11-13 --staged --worktree :/
git commit -m "recovery: restore unfinished order and menu work"
```

This produced commit `93591f6`. Git recorded only the difference between the recovered tree and `develop`: 22 files with 250 insertions and 13 deletions. This separated genuine unfinished work from content already present in the base branch.

### 7. Compile to expose incomplete recovery

The first compilation stopped at:

```text
TotalPriceCalculationHandler.java: ';' expected
```

The handler contained:

```java
order.getOrderItems().stream()
        .map(order)
```

This proved that preserving the visible working tree was not sufficient; an earlier, completed version had to be located.

### 8. Search all Git objects, including unreachable objects

All Git blobs were searched for versions of `TotalPriceCalculationHandler`. Only two versions existed:

- an empty handler; and
- the incomplete handler found in the working tree.

The object-level search used:

```bash
git cat-file --batch-all-objects --batch-check='%(objectname) %(objecttype)' \
  | awk '$2 == "blob" {print $1}' \
  | while read -r oid; do
      if git cat-file blob "$oid" | rg -q 'class TotalPriceCalculationHandler'; then
        printf 'BLOB %s\n' "$oid"
        git cat-file blob "$oid"
      fi
    done
```

This search is useful when a commit or stash reference was deleted but its objects have not yet been pruned.

### 9. Search editor and IDE recovery sources

Because Git did not contain the implementation, IntelliJ shelves, Local History, patch files, swap files, and backups were inspected.

```bash
find .idea . \
  -path './.git' -prune -o \
  -path './target' -prune -o \
  \( -iname '*TotalPriceCalculationHandler*' \
     -o -iname '*.patch' \
     -o -iname '*.diff' \
     -o -iname '*.bak' \
     -o -iname '*.swp' \
     -o -iname '*~' \) -print

rg -n -C 20 \
  'TotalPriceCalculationHandler|totalOrderPrice|getOrderItemTotalPrice' \
  .idea/shelf
```

The completed implementation was found in an IntelliJ shelf created on October 24.

### 10. Restore the complete behavior, not only the syntax

The shelf showed that total-price calculation depended on two companion changes:

- `OrderItem.getOrderItemTotalPrice()` multiplies the menu item's unit price by the requested quantity.
- `MenuItemAvailabilityCheckHandler` builds each `OrderItem` with its menu item ID, requested quantity, and current menu price.

Restoring only `TotalPriceCalculationHandler` would have compiled but later failed or calculated incorrectly because item prices and quantities could remain null. All three related changes were therefore restored together and committed as `c0382b3`.

The restored calculation is conceptually:

```java
BigDecimal total = order.getOrderItems().stream()
        .map(OrderItem::getOrderItemTotalPrice)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
order.setOrderTotalPrice(total);
```

### 11. Verify the recovered state

Verification included:

```bash
./mvnw -DskipTests compile
git symbolic-ref --short HEAD
git rev-parse HEAD
git diff-index --quiet HEAD --
```

Results:

- compilation succeeded;
- the active branch was `recovered/order-menu-work`;
- `HEAD` was `c0382b3`; and
- the working tree was clean.

The full Maven test phase attempted to download missing Surefire artifacts but could not write to the sandboxed, read-only Maven repository at `~/.m2`. Compilation succeeded before that environmental failure. Full tests should still be run in a normal development environment with a writable Maven repository.

## Final recovered references

| Reference | Commit | Purpose |
| --- | --- | --- |
| `recovery/order-menu-2026-11-13` | `e45f6a1` | Exact safety snapshot of the initially discovered working tree |
| `recovered/order-menu-work` | `93591f6` | Recovered unfinished work applied on top of `develop` |
| `recovered/order-menu-work` | `c0382b3` | Restored total-price implementation and companion changes |
| `develop` at recovery time | `2b15346` | Correct base used for the repaired branch |

The original stashes remained available as `stash@{0}` and `stash@{1}` at the end of the investigation.

## Runbook for similar incidents

### Phase A: stop and preserve evidence

Immediately stop branch switching and editing. Do not run destructive or cleanup commands.

Avoid all of the following until recovery is complete:

```bash
git reset --hard
git clean -fd
git checkout -- .
git restore .
git stash drop
git stash clear
git reflog expire
git gc --prune=now
git push --force
```

These operations can remove the only remaining copy of a change or make recovery much harder.

Record the initial state in a terminal log or incident note:

```bash
date --iso-8601=seconds
git status --short --branch
git symbolic-ref --short HEAD || echo "detached HEAD"
git rev-parse HEAD
git branch --all --verbose --no-abbrev
git stash list
git reflog --all --date=iso -n 100
```

### Phase B: inventory the working tree

```bash
git diff --stat
git diff --name-status
git diff --cached --stat
git diff --cached --name-status
git ls-files --others --exclude-standard
```

Remember that untracked files are not included in an ordinary `git diff`.

### Phase C: inspect recoverable Git sources

Inspect, but do not immediately apply:

```bash
git stash list
git stash show --stat 'stash@{0}'
git stash show --include-untracked --name-status 'stash@{0}'
git reflog --all --date=iso
git log --graph --decorate --oneline --all
git fsck --full --no-reflogs --unreachable
```

Potential sources include:

- current working-tree and index changes;
- local and remote branches;
- stashes, including their untracked-file parents;
- `HEAD` and branch reflogs;
- dangling commits, trees, and blobs; and
- patches previously exported by an engineer.

### Phase D: inspect non-Git sources

If Git has no complete version, check:

- IntelliJ IDEA Shelf;
- IntelliJ Local History;
- VS Code Timeline or local history extensions;
- editor swap and backup files;
- filesystem snapshots or workstation backups;
- CI workspaces and build artifacts;
- another engineer's clone or branch; and
- code-review diffs, pull requests, chat attachments, or emailed patches.

Do not run Git garbage collection while searching. Unreachable objects may be recoverable only until pruning occurs.

### Phase E: create an immutable safety point

When the desired files are visible in the working tree, preserve them before reorganizing anything:

```bash
git switch -c recovery/<incident-name>-<date>
git add -A
git commit -m "recovery: preserve incident worktree"
```

Review staged content before committing if the repository may contain secrets:

```bash
git diff --cached --stat
git diff --cached
```

If creating a commit is not acceptable, export tracked changes to a patch and separately copy untracked files to a clearly identified backup location. A recovery branch and local commit are usually safer because they preserve file modes, deletions, renames, and untracked additions together.

### Phase F: reconstruct on the correct base

Do not treat the safety snapshot as the final branch automatically. Determine the correct base first, then create a separate repaired branch.

For an exact-tree recovery like this incident:

```bash
git switch -c recovered/<incident-name> <correct-base>
git restore --source=recovery/<incident-name>-<date> --staged --worktree :/
git diff --cached --stat
git commit -m "recovery: restore <feature> work"
```

This records only the recovered tree's difference from the correct base. Keep the original safety branch until the repaired branch has been reviewed, tested, and pushed.

### Phase G: validate behavior and provenance

Run the smallest checks that prove the recovery, then the normal project suite:

```bash
./mvnw -DskipTests compile
./mvnw test
git diff-index --quiet HEAD --
```

Also verify:

- the active branch is correct;
- the repaired commit has the expected parent;
- no unrelated files were introduced;
- no secret or local configuration was committed;
- stashes were not accidentally dropped; and
- the remote has not been rewritten.

## How to prevent recurrence

### Commit or intentionally stash before changing branch history

Use small work-in-progress commits on a private branch when practical. A local commit is much easier to recover than an editor buffer or an untracked file.

If a temporary stash is required, include untracked files and give it a descriptive message:

```bash
git stash push --include-untracked -m "WIP order pricing before branch sync"
```

### Inspect before resetting

Before any reset, record the current commit and inspect what will change:

```bash
git status --short --branch
git rev-parse HEAD
git diff --stat <target>..HEAD
```

If the goal is only to inspect an older revision, create a temporary branch or use a separate worktree instead of moving an active feature branch.

### Prefer a separate worktree for parallel branch work

```bash
git worktree add ../food-delivery-order feature/order-managemnt
git worktree add ../food-delivery-menu feature/menu-item-management
```

Each branch then has a separate directory, reducing accidental cross-branch working-tree state.

### Treat detached `HEAD` as a temporary inspection state

If useful work is created while detached, attach it immediately:

```bash
git switch -c recovery/descriptive-name
```

### Verify after every history-changing command

After reset, rebase, merge, or checkout, run:

```bash
git status --short --branch
git log -1 --oneline
```

This makes unexpected branch movement visible immediately, while reflog recovery is still straightforward.

## Key lessons

1. “Lost during branch switching” is a symptom, not a root-cause diagnosis.
2. Reflog is the primary source for reconstructing local branch and `HEAD` movement.
3. A mixed reset can make committed files appear uncommitted when a branch is moved backward.
4. Preserve the exact working tree before attempting to clean up or separate changes.
5. Recovery should be rebuilt on the correct base, while retaining an untouched safety snapshot.
6. Search all Git objects when normal branch and stash inspection is insufficient.
7. Git cannot recover work that was never stored in Git; IDE shelves and Local History are important secondary sources.
8. Restore behavioral dependencies together. A compiling method is not necessarily a complete recovery.
9. Do not delete stashes, prune objects, force-push, or run destructive cleanup during an active recovery.
10. Finish with compilation, tests, branch verification, and a clean working-tree check.

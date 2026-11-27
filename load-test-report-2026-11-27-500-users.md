# Load Test Report: Customer Order Flow, 500 Users

| | |
|---|---|
| **System under test** | `food_delivery_api` (Spring Boot 4.1.1, Java 21 target, run on JDK 25) |
| **Code version** | branch `feature/order-managemnt` @ `847a9b1` + uncommitted changes (see §9) |
| **Test date** | 2026-11-27, 22:10:08 – 22:11:57 (UTC+2) |
| **Tool** | Apache JMeter 5.6.3 (non-GUI mode) |
| **Test plan** | `~/load-tests/food-delivery.jmx` |
| **Result** | ✅ **PASS**: 0 errors in 3,000 requests; overall p95 122 ms |

---

## 1. Executive summary

- **500 customers** each completed the full order flow **once**: login → clear cart → add to cart → view cart → place order.
- **0% errors** across 3,000 requests. All 500 orders were created and verified in the database (one per user).
- **Fast responses:** median **13 ms**, p95 **122 ms** across all customer requests. After JVM warm-up (first ~15 s), p95 dropped to **91 ms**.
- **Slowest step is login** (p95 305 ms). This is expected: passwords are hashed with Argon2, which is deliberately expensive.
- **Plenty of headroom:** at most **6 of 10** database connections were in use, and the machine was about half loaded.
- **This was a correctness-under-load test, not a stress test.** Users arrived over 60 s with 1–3 s think time, so the server never handled more than **20 requests at the same moment**. The system's breaking point is **not yet known** (see §8).

**Verdict:** the order flow is functionally correct and comfortably fast at this volume. We need a stress test and a spike test before making capacity claims.

---

## 2. Scope and scenario

### 2.1 Business flow tested

Each virtual user is one customer:

| # | Step | Endpoint | Expected response |
|---|---|---|---|
| 0 | Register (setup phase, not timed as load) | `POST /api/v1/auth/register` | 201 (or 409 if already registered) |
| 1 | Login (once per user) | `POST /api/v1/auth/login` | 200 + JWT |
| 2 | Clear cart (start from a clean state) | `DELETE /api/v1/carts/me` | 204, or 404 if the user has no cart yet |
| 3 | Add item to cart | `POST /api/v1/carts/me` | 201 |
| 4 | View cart | `GET /api/v1/carts/me` | 200 |
| 5 | Place order, built from the cart contents | `POST /api/v1/orders` | 200 + `orderId` |

All steps after login are **authenticated**: the client sends only `Authorization: Bearer <token>`. No customer IDs are sent; the server resolves the customer from the token.

### 2.2 Out of scope

- Menu management, restaurant management, image upload (SeaweedFS), token refresh, logout
- **Payment.** The order pipeline's payment step is currently a **stub** (it only logs "Payment success"). Real payment-provider latency is **not** included and will add to order times once integrated.
- Browsing endpoints (none exist yet for restaurants and menus)

---

## 3. Test environment

| Component | Details |
|---|---|
| Machine | Single laptop: Intel Core i5-10300H (4 cores / 8 threads), 14 GB RAM |
| OS | Ubuntu 26.04.1 LTS, kernel 7.0.0-44 |
| Application | Packaged jar (`java -jar`), JDK 25.0.3, default JVM settings, port 8099 |
| Database | PostgreSQL 19.0, local |
| DB connection pool | HikariCP default: **10 connections** |
| Web server | Embedded Tomcat default: 200 worker threads |
| Load generator | JMeter 5.6.3, heap 1–2 GB, **on the same machine** |
| Other services running | PostgreSQL, SeaweedFS, and a second (idle) instance of the app started from the IDE |

### Deviations from a production-like setup

| Setting | Test value | Why |
|---|---|---|
| Access-token lifetime | 2 h (default 15 min) | Avoid token expiry mid-test |
| Load generator location | Same machine as the app and DB | Only one machine available; competes for CPU |
| Logging | Unchanged: the `LoggingAndPerformanceAspect` logs every method call at INFO | Measure the app as it is today |
| Data volume | 1 restaurant, 1 menu item, ~13k existing orders | Current dev data set |

---

## 4. Methodology

### 4.1 Load profile

| Parameter | Value |
|---|---|
| Virtual users (threads) | **500** |
| Iterations per user | **1** (each user places exactly one order) |
| Ramp-up | **60 s** (~8.3 new users per second) |
| Think time | **1–3 s** (uniform random) before each shopping request |
| Error handling | If a step fails, that user stops, so no broken orders are sent |

### 4.2 Test data

- The setup phase registered 500 dedicated test users (`+201500000001` … `+201500000500`) with one shared test password.
- All users ordered the same menu item from the same restaurant. The quantity was random (1–3).
- The delivery `addressId` was a random UUID, because the API does not validate addresses yet (see §6.4).

### 4.3 Pass/fail checks

Every request had a **status-code assertion** (table in §2.1), and *Place order* also asserted that `$.orderId` is present. After the run, order creation was verified **directly in the database**.

### 4.4 Server-side monitoring

Sampled every 2 s during the run: app CPU and memory (RSS), JVM thread count, PostgreSQL connections (active and open), machine load average.

---

## 5. Results

### 5.1 Overall

| Metric | Value |
|---|---|
| Total requests | 3,000 (500 register + 2,500 customer flow) |
| **Errors** | **0 (0.00%)** |
| Orders created (verified in DB) | **500**, one per user |
| Load-phase duration | 68 s |
| Average throughput (load phase) | 36.8 req/s |
| Steady-state throughput | ~42 req/s (10–60 s window) |
| Peak active users (threads) | 96 |
| **Peak requests in flight at once** | **20** |

### 5.2 Response times per step (ms)

| Step | Requests | Errors | Avg | Median | p90 | **p95** | p99 | Max |
|---|---|---|---|---|---|---|---|---|
| 00 Register *(setup)* | 500 | 0 | 70 | 63 | 83 | 98 | 221 | 495 |
| 01 Login | 500 | 0 | 103 | 74 | 155 | **305** | 536 | 659 |
| 02 Clear cart | 500 | 0 | 9 | 5 | 12 | **18** | 162 | 334 |
| 03 Add to cart | 500 | 0 | 27 | 11 | 36 | **103** | 383 | 695 |
| 04 View cart | 500 | 0 | 11 | 4 | 12 | **24** | 175 | 360 |
| 05 Place order | 500 | 0 | 31 | 20 | 43 | **57** | 287 | 561 |
| **All customer requests** | **2,500** | **0** | **36** | **13** | **83** | **122** | **369** | **695** |

### 5.3 Against proposed targets

These targets are a **proposal** and have not been agreed yet. They are a reasonable starting point for an interactive API.

| Target | Result | Status |
|---|---|---|
| Error rate < 1% | 0.00% | ✅ |
| p95 cart operations < 500 ms | 18–103 ms | ✅ |
| p95 place order < 1,000 ms | 57 ms | ✅ |
| p95 login < 1,000 ms | 305 ms | ✅ |

### 5.4 Timeline (customer requests, 10-second buckets)

| Window | Requests | Throughput (req/s) | p95 (ms) | Active users |
|---|---|---|---|---|
| 0–10 s | 224 | 22.4 | **431** | 84 |
| 10–20 s | 443 | 44.3 | 104 | 96 |
| 20–30 s | 415 | 41.5 | 83 | 75 |
| 30–40 s | 414 | 41.4 | 85 | 76 |
| 40–50 s | 417 | 41.7 | 75 | 74 |
| 50–60 s | 418 | 41.8 | **255** | 75 |
| 60–70 s | 169 | 16.9 | 22 | 67 |

### 5.5 Server resources during the run

| Metric | Min | Max | Avg |
|---|---|---|---|
| App memory (RSS, MB) | 428 | 707 | 558 |
| App JVM threads | 47 | 58 | 53 |
| DB connections **active** | 1 | **6** | 1.2 |
| Machine load average (8 logical CPUs) | 3.5 | 4.9 | 4.2 |

*(About 24 DB connections were open in total. That number includes the pools of both app instances and the monitoring session; the instance under test has a pool of 10.)*

---

## 6. Analysis and findings

### 6.1 Functional correctness under load: confirmed ✅

Every user completed every step with the expected status code, and the database contains exactly one new order per user. This also confirms, under concurrent load, the changes made just before the test (§9): customer identity resolved from the token, role-based access, and the repaired view-cart endpoint.

### 6.2 JVM warm-up dominates the slow tail

Of the 46 requests slower than 300 ms, **32 happened in the first 15 seconds**. p95 was 351 ms in that window and **91 ms** afterwards. This is typical just-in-time compilation and connection-pool warm-up. In production a short warm-up after deploy, or a readiness delay, would prevent the first real users from paying this cost.

### 6.3 Login is the most expensive operation, by design

Login (p95 305 ms, p99 536 ms) is 3–10× slower than other steps because passwords are hashed with **Argon2** (≈19 MB of memory and several iterations per hash). This is the right security choice, but it means:
- **Login bursts** (e.g. a promotion at 12:00) are the most likely first bottleneck;
- every 100 concurrent logins need roughly 2 GB of memory just for hashing.

### 6.4 Minor latency spike at 50–60 s

p95 rose to 255 ms in the 50–60 s window without a change in load. The cause wasn't identified in this run. Likely candidates are a garbage-collection pause or contention from JMeter and other processes on the same laptop. Watch for it in longer runs. If it repeats, enable GC logging.

### 6.5 Resources are far from saturated

- Database pool: at most **6 of 10** connections in use, so it is not a constraint at this load.
- CPU: load average ~4.2 on 8 logical CPUs, **including JMeter itself**.
- Memory: peak ~700 MB RSS, stable.

### 6.6 What this test does *not* tell us

Because of the 60 s ramp-up and the 1–3 s think time, the server never had more than **20 requests in flight**. The test shows the system works correctly and quickly at this arrival rate, but it says **nothing about the maximum capacity** or about behaviour when Tomcat threads, DB connections or CPU are exhausted.

---

## 7. Limitations and caveats

1. **Single machine.** The app, PostgreSQL, SeaweedFS and JMeter shared one laptop CPU. Absolute numbers will differ in production. Use these results for **relative comparison** between runs.
2. **Low concurrency** (see §6.6).
3. **Thin data set.** One restaurant and one menu item mean every order touches the same rows. That's good for finding lock contention, but not representative of a varied catalogue.
4. **No address validation.** Orders used random address IDs (a known open issue).
5. **Logging aspect enabled.** Per-method INFO logging adds I/O on every request. Results include this overhead.
6. **One short run.** A single 2-minute run; there is no soak test for memory leaks or slow degradation yet.
7. **Payment is stubbed.** Place-order times (p95 57 ms) exclude any real payment-provider call, which typically adds hundreds of milliseconds and new failure modes (timeouts, retries).

---

## 8. Recommendations and next steps

| Priority | Action | Purpose |
|---|---|---|
| **High** | **Stress test:** same plan with `-Jrampup=10 -Jloops=5` (500 users, 2,500 orders, faster arrival) | Find where p95 or errors start to rise |
| **High** | **Spike test:** 1,000 users released together (Synchronizing Timer, no think time) | Behaviour under a sudden burst; needs 500 more test users |
| **High** | **Agree performance targets (SLOs)** with product, e.g. the ones in §5.3 | Turn "fast enough" into pass/fail criteria |
| Medium | **Login burst test:** many concurrent logins only | Size memory and CPU for Argon2 under peak sign-in |
| Medium | Re-run with `LoggingAndPerformanceAspect` at WARN | Measure the logging overhead; decide the production log level |
| Medium | **Soak test:** 100 users for 1–2 hours | Detect memory leaks, pool exhaustion, token-expiry handling |
| Medium | Run on a **production-like environment** with JMeter on a separate machine | Numbers that can be used for capacity planning |
| Low | Add a 5-user **smoke load test to CI** | Catch functional regressions in the order flow early |
| Low | Enable GC logging for load tests | Explain latency spikes like the one in §6.4 |

---

## 9. Changes delivered before this test

The test exercised the following changes. Each is documented separately.

| Change | Why | Document |
|---|---|---|
| Customer identity resolved **server-side from the JWT**; `/carts/me` endpoints; `customerId` removed from the order request | Fix an IDOR/BOLA vulnerability: any user could read, change or clear another customer's cart, or place orders as them | `idor-customer-id-from-token.md` |
| Role-based access with `@PreAuthorize` (`CUSTOMER` for cart/orders, `ADMIN` for menu creation) | No role checks existed before | same document |
| `CartItemResponseDto.cartItemPrice`: `Double` → `BigDecimal` | View cart returned 500 for every customer with a cart; this blocked the order flow | `cart-view-500-dto-projection-type-mismatch.md` |

> ⚠️ These changes are **not yet committed**, and they include breaking API changes (new cart URLs, no `customerId` in the order body). Frontend and API clients must be updated before release.

---

## 10. How to reproduce

```bash
# 1. Build and start the app (from the project root)
./mvnw -q package -DskipTests
JWT_ACCESS_TOKEN_TTL=2h java -jar target/api-0.0.1-SNAPSHOT.jar --server.port=8099

# 2. Run the test (the restaurant must be open: 11:09–23:09)
cd ~/apache-jmeter-5.6.3/bin
HEAP="-Xms1g -Xmx2g" ./jmeter -n -t ~/load-tests/food-delivery.jmx -Jport=8099 \
  -l ~/load-tests/results/run-500.jtl -e -o ~/load-tests/results/report-500
```

Settings (`-Jusers`, `-Jrampup`, `-Jloops`, `-Jhost`, `-Jport`, …) are documented in `~/load-tests/README.md`.

---

## Appendix: artifacts

| Artifact | Location |
|---|---|
| JMeter test plan | `~/load-tests/food-delivery.jmx` |
| Test plan README | `~/load-tests/README.md` |
| Raw results (3,000 samples) | `~/load-tests/results/run-500.jtl` |
| JMeter HTML dashboard (charts over time) | `~/load-tests/results/report-500/index.html` |

**Test data left in the dev database:** 500 test users (`+2015…`) and 510 orders (500 from this run, 10 from earlier smoke runs).

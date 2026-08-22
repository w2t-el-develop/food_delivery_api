# food_delivery_api
for java eats project in mentorship

# Cart management api
## update quantity   

###flow chart update
```mermaid
flowchart TD
    Start((start))
    Start --> A{If user not exist}
    A -->|yes| B[return Error<br/>cus_NOT_FOUND]
    A -->|no| C[open cart]
    C --> D{Is customer<br/>has cart ?}
    D -->|no| E[return Error]
    D --> F[select newQuantity<br/>&& sumit]
    F --> G{newQuantity<br/>between 1, 99}
    G -->|no| H[return Error]
    G --> I{Is cartItem<br/>available}
    I -->|no| J[return Error]
    I --> K[sava newQuantity<br/>% change total price]
    K --> End((END))
```
### sequence diagram updateQuantity

```mermaid
sequenceDiagram
    participant UI
    participant controller
    participant Repository
    participant DataBase

    UI->>controller: api/cusID
    controller->>Repository: checkExitCustomer(cusID)
    Repository->>DataBase: findByID(cusID)
    DataBase-->>Repository: 
    Repository-->>controller: 

    UI->>controller: open cart
    controller->>Repository: checkCustomerHasCart
    Repository->>DataBase: findByID(cartID)
    DataBase-->>Repository: 
    Repository-->>controller: 

    UI->>controller: select newQuantity && sumit
    controller->>controller: newQuantity Between 1, 99
    controller->>Repository: checkIsAvailable
    Repository->>DataBase: findStatusItem(CartItemID)
    DataBase-->>Repository: 
    Repository-->>controller: 

    controller->>Repository: sateNewQuantity
    Repository->>DataBase: sava(cartItem)
    DataBase-->>Repository: 
    Repository-->>controller: 
    controller-->>UI: ok or NotFound
```

### Request Body updateQuantity
<img width="617" height="695" alt="RequestBody" src="https://github.com/user-attachments/assets/2a0ef000-03d6-4187-a536-fcd96477f8d6" />

### pseodu code  updateQuantity 
```text
START

    Is user exist?

    IF user does not exist
        RETURN Error
    END IF

    Is user has cart?

    IF user does not have cart
        RETURN Error

    // update  quantity

    If newQuantity is  unavailable in menu
        not change quantity
    END IF

    IF newQuantity > 1 && newQuantity < 99
        update quantity
        Change total price
    END use

    sava change in Db
```


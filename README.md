# food_delivery_api

for java eats lite documentation.

This document contains the core software specifications, architecture flows, diagrams, and backend pseudo-code for the Java eats Lite modules.

---

## Cart Management Module

---

### 1. Add to Cart Feature Specification

The Add to Cart feature allows customers to append items to their active shopping cart upon verifying authentication, cart existence, and item availability in the restaurant's menu.

#### A. API Endpoint Specification

* **Signature (URL):** `/api/v1/cart/add`
* **HTTP Method:** `POST`
* **Success Status Code:** `201 Created`

#### Request Body Input
```json
{
  "customerId": "cust_99",
  "menu_item_id": "item_123",
  "quantity": 2
}
```

#### Success Response Body (201 Created)
```json
{
  "status": "success",
  "message": "Item added to cart successfully"
}
```

#### Error Response Body (404 Not Found)
```json
{
  "status": "error",
  "message": "Item Not Available"
}
```

---

### B. Feature Architecture & Workflows

#### |. Process Flowchart

```mermaid
flowchart TD
    Start([Start: Customer Request]) --> CheckAuth{Is Customer Signed In?}
    
    CheckAuth -- No --> SignUp[Process Sign Up & Store in DB] --> CreateCart[Create New Cart for Customer]
    CheckAuth -- Yes --> VerifyCustomer{Customer Exists in DB?}
    
    VerifyCustomer -- No --> SignUp
    VerifyCustomer -- Yes --> CheckCustomerCart{Does Customer Have a Cart?}
    
    CheckCustomerCart -- No --> CreateCart
    CheckCustomerCart -- Yes --> GetCart[Fetch Customer cart_id]
    CreateCart --> GetCart
    
    GetCart --> CheckQuantity{Is quantity > 0?}
    
    CheckQuantity -- No --> ErrQty[Return 400: Invalid Quantity]
    CheckQuantity -- Yes --> CheckItem[Query Menu_Item by menu_item_id]
    
    CheckItem --> IsAvailable{Is Item Available in Restaurant?}
    
    IsAvailable -- No --> ErrItem[Return 404: Item Not Available]
    IsAvailable -- Yes --> InsertCartItem[INSERT into Cart_Item Table]
    
    InsertCartItem --> Success([Return 201 Created])
```

#### ||. Sequence Diagram

```mermaid
sequenceDiagram
    autonumber
    actor Client as Client 
    participant Auth as Auth Module
    participant CartAPI as Cart Controller
    participant Service as Cart Service
    participant DB as Database

    Client->>Auth: Customer Sign In / Check Status
    Auth->>DB: SELECT FROM Customer WHERE customer_id
    alt Customer does not exist
        Auth->>DB: INSERT INTO Customer (Sign Up)
        Auth->>DB: INSERT INTO Cart (Create initial Cart)
    end

    Client->>CartAPI: POST /api/v1/cart/add (customer_Id, menu_item_id, quantity)
    CartAPI->>Service: addToCart(customerId, menu_item_id, quantity)
    
    Service->>DB: SELECT FROM Cart WHERE customer_id
    alt Cart does not exist
        Service->>DB: INSERT INTO Cart (cart_id, customer_id)
        DB-->>Service: New Cart Created
    else Cart exists
        DB-->>Service: Existing Cart Details
    end

    Service->>DB: SELECT FROM Menu_Item WHERE menu_item_id
    alt Item does not exist or Not Available
        Service-->>CartAPI: Exception: Item Not Available
        CartAPI-->>Client: 404 / 400 Exception Response
    else Item Available
        DB-->>Service: Menu_Item Details (Price)
        Service->>DB: INSERT INTO Cart_Item (cart_item_id, cart_id, menu_item_id, quantity, price)
        DB-->>Service: Operation Success
        Service-->>CartAPI: Success Result
        CartAPI-->>Client: 201 Created Response
    end
```

---
### C. Backend Implementation Logic

#### Pseudo Code

```text
FUNCTION addToCart(customerId, menu_item_id, quantity):

    // 1. Customer Authentication & Existence Check
    user = Database.find("Customer", WHERE customer_id == customerId)
    IF user IS NULL THEN
        Customer = signUpCustomer(customerId)
    END IF

    // 2. Ensure Customer Has an Active Cart
    cart = Database.find("Cart", WHERE customer_id == customerId)
    IF cart IS NULL THEN
        cart = NEW Cart()
        cart.cart_id = GENERATE_UUID()
        cart.customer_id = customerId
        Database.save(cart)
    END IF

    // 3. Input Validation
    IF quantity <= 0 THEN
        RETURN Response(statusCode=400, message="Quantity must be greater than zero")
    END IF

    // 4. Validate Menu Item Existence & Availability
    menuItem = Database.find("Menu_Item", WHERE menu_item_id == menu_item_id)
    IF menuItem IS NULL OR menuItem.is_available == FALSE THEN
        THROW EXCEPTION "Item Not Available"
        RETURN Response(statusCode=404, message="Item is not available in the restaurant")
    END IF

    // 5. Direct Insertion into Cart_Item
    newCartItem = NEW Cart_Item()
    newCartItem.cart_item_id = GENERATE_UUID()
    newCartItem.cart_id = cart.cart_id
    newCartItem.menu_item_id = menu_item_id
    newCartItem.cart_item_quantity = quantity
    newCartItem.cart_item_price = menuItem.menu_item_price
    
    Database.save(newCartItem)

    // 6. Return Success Response
    RETURN Response(statusCode=201, message="Item added to cart successfully")

END FUNCTION
```

---

## 2. Update Quantity

### A. Flow Chart Update
```mermaid
flowchart TD
    Start((start))
    Start --> A{If user not exist}
    A -->|yes| B[return Error<br/>customer_NOT_FOUND]
    A -->|no| C[open cart]
    C --> D{Is customer<br/>has cart ?}
    D -->|no| E[return Error]
    D --> F[select newQuantity<br/>&& sumit]
    F --> G{Is newQuantity<br/>between 1, 99}
    G -->|no| H[return Error]
    G --> I{Is cartItem<br/>available}
    I -->|no| J[return Error]
    I --> K[sava newQuantity<br/>% change total price]
    K --> End((END))
```

### B. Sequence Diagram Update Quantity

```mermaid
sequenceDiagram
    participant controller
    participant Repository
    participant DataBase

    controller->>Repository: checkExitCustomer(cusID)
    Repository->>DataBase: findByID(cusID)
    DataBase-->>Repository: 
    Repository-->>controller: 

    controller->>Repository: checkCustomerHasCart
    Repository->>DataBase: findByID(cartID)
    DataBase-->>Repository: 
    Repository-->>controller: 

    controller->>controller: IS newQuantity Between 1, 99
    controller->>Repository: checkcartItemIsAvailable
    Repository->>DataBase: findStatusItem(CartItemID)
    DataBase-->>Repository: 
    Repository-->>controller: 

    controller->>Repository: save NewQuantity
    Repository->>DataBase: sava(cartItem)
    DataBase-->>Repository: 
    Repository-->>controller: 
```

### C. Request Body Update Quantity
<img width="617" height="695" alt="RequestBody" src="https://github.com/user-attachments/assets/2a0ef000-03d6-4187-a536-fcd96477f8d6" />

### D. Pseudo Code Update Quantity
```text
START

    Is user exist?

    IF customer does not exist
        RETURN Error
    END IF

    Is customer has cart?

    IF customer does not have cart
        RETURN Error

    // update quantity

    If newQuantity is unavailable in menu
        not change quantity
    END IF

    IF newQuantity > 1 && newQuantity < 99
        update quantity
        Change total price
    END IF

    sava change in Db
```

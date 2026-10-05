# sequenceDiagram
``` mermaid
sequenceDiagram
    autonumber
    actor Client
    participant AuthController
    participant AuthService
    participant Database

    Client->>AuthController: POST /api/v1/auth/register (fullName, phone, password, confirmPassword)
    AuthController->>AuthService: registerUser(payload)
    
    rect 
        Note over AuthService, Database: Validation & Database Checks
        AuthService->>AuthService: Validate input format (fullName, phone, password, confirmPassword)
        alt Invalid Format
            AuthService-->>AuthController: Throw ValidationException (400)
            AuthController-->>Client: 400 Bad Request
        end

        AuthService->>AuthService: Verify password and confirmPassword match
        alt Passwords Mismatch
            AuthService-->>AuthController: Throw ValidationException (400)
            AuthController-->>Client: 400 Bad Request
        end

        AuthService->>Database: Check for existing phone
        alt User Exists
            AuthService-->>AuthController: Throw ConflictException (409)
            AuthController-->>Client: 409 Conflict
        end
    end

    rect 
        Note over AuthService, Database: Storage & Token Generation
        AuthService->>AuthService: Hash password using Argon2
        AuthService->>Database: Insert new user record
        alt Database Insert Fails
            AuthService-->>AuthController: Throw InternalErrorException (500)
            AuthController-->>Client: 500 Internal Server Error
        else Success
            AuthService->>AuthService: Generate JWT
            AuthService-->>AuthController: Return User DTO + JWT
            AuthController-->>Client: 201 Created (JWT + User Profile)
        end
    end

```


# flowchart
```mermaid
flowchart TD
    Start([Start])
    Start --> ReceiveRequest["POST /api/register"]
    ReceiveRequest --> ParseBody["Parse request body"]
    ParseBody --> ValidateFormat["Validate input format<br/>fullName, phone, password, confirmPassword required"]
    
    ValidateFormat --> CheckInputValid{Input valid?}
    CheckInputValid -->|No| Error400Format["400 Bad Request<br/>Missing or invalid fields"]
    
    CheckInputValid -->|Yes| CheckPasswords["Check if password == confirmPassword"]
    CheckPasswords --> PasswordsMatch{Match?}
    PasswordsMatch -->|No| Error400Match["400 Bad Request<br/>Passwords do not match"]
    
    PasswordsMatch -->|Yes| CheckPhoneUnique["Check phone uniqueness<br/>Query database by phone"]
    CheckPhoneUnique --> PhoneExists{Phone exists?}
    PhoneExists -->|Yes| Error409Phone["409 Conflict<br/>Phone number already registered"]
    
    PhoneExists -->|No| HashPassword["Hash password<br/>Argon2 or bcrypt"]
    
    HashPassword --> InsertDB["Insert customer record<br/>Save to database with hashed password"]
    InsertDB --> CheckInsert{Insert success?}
    CheckInsert -->|No| Error500["500 Server Error<br/>Database operation failed"]
    CheckInsert -->|Yes| Success["201 Created<br/>Return customer object + JWT"]
    
    Error400Format --> End([End])
    Error400Match --> End
    Error409Phone --> End
    Error500 --> End
    Success --> End
    
   
```
# peducode 



```
FUNCTION handleUserRegistration(httpRequest):
    TRY:
        // ====================================================
        // STEP 1: RECEIVE & PARSE REQUEST
        // ====================================================
        payload = httpRequest.body
        fullName = payload.fullName
        phone = payload.phone
        password = payload.password
        confirmPassword = payload.confirmPassword

        // ====================================================
        // STEP 2: FORMAT VALIDATION & PASSWORD MATCH (Client Error 400)
        // ====================================================
        // Check required fields
        IF fullName IS NULL OR phone IS NULL OR password IS NULL OR confirmPassword IS NULL:
            RETURN Response(status = 400, body = { "error": "Missing required fields" })

        IF fullName IS EMPTY:
            RETURN Response(status = 400, body = { "error": "Full name is required" })

        IF NOT matchesRegex(phone, "^\\+?[0-9]{7,15}$"):
            RETURN Response(status = 400, body = { "error": "Phone must contain 7-15 digits and may start with +" })

        IF LENGTH(password) < 8:
            RETURN Response(status = 400, body = { "error": "Password must be at least 8 characters long" })
            
        IF password != confirmPassword:
            RETURN Response(status = 400, body = { "error": "Passwords do not match" })

        // ====================================================
        // STEP 3: DATABASE UNIQUENESS CHECK (Client Error 409)
        // ====================================================
        existingUser = Database.findUserByPhone(phone)

        IF existingUser EXISTS:
            RETURN Response(status = 409, body = { "error": "Phone number already registered" })


        // ====================================================
        // STEP 4: PASSWORD HASHING
        // ====================================================
        hashedPassword = Argon2.hash(password)


        // ====================================================
        // STEP 5: PERSIST USER (Server Error 500)
        // ====================================================
        TRY:
            newUser = Database.insertUser({
                "fullName": fullName,
                "phone": phone,
                "passwordHash": hashedPassword,
                "createdAt": getCurrentTimestamp()
            })
        CATCH DatabaseException AS dbErr:
            RETURN Response(status = 500, body = { "error": "Failed to create user record due to a database error" })


        // ====================================================
        // STEP 6: TOKEN GENERATION & SUCCESS RESPONSE (201)
        // ====================================================
        jwtToken = JWT.generateToken({
            "userId": newUser.id,
            "phone": newUser.phone
        })

        userProfile = {
            "id": newUser.id,
            "fullName": newUser.fullName,
            "phone": newUser.phone
        }

        RETURN Response(
            status = 201,
            body = {
                "token": jwtToken,
                "user": userProfile
            }
        )

    CATCH UnexpectedException AS err:
        RETURN Response(status = 500, body = { "error": "An unexpected error occurred" })
```

## Registration API Endpoint

### Request

```http
POST /api/v1/auth/register
Content-Type: application/json
```

```json
{
   
    "password": "SecurePass123!",
    "confirmPassword": "SecurePass123!",
    "fullName": "Sam Carter",
    "phone": "+14155552671"
}
```

### Response Cases

#### Case 1: Registration successful

**Status:** `201 Created`

```json
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
    
    
}
```

#### Case 2: Invalid request data

**Status:** `400 Bad Request`

```json
{
    "error": "Phone must contain 7-15 digits and may start with +"
}
```

#### Case 3: Phone already registered

**Status:** `409 Conflict`

```json
{
    "error": "Phone already registered"
}
```

#### Case 4: password not match confirm password

**Status:** `409 Conflict`

```json
{
    "error": "Passwords do not match"
}
```

#### Case 5: Database or unexpected server error

**Status:** `500 Internal Server Error`

```json
{
    "error": "An unexpected error occurred"
}
```
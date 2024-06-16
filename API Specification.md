
### Admin
**1. Sign Up Admin account**
- **Method :** `POST`
- **Endpoint :** `/admins/register`
- **Request Body**
  - **Valid email :**
      ```json
      {
          "name": "name",
          "email": "email@gmail.com",
          "password": "password"
      }
      ```
  - **Invalid email :**
      ```json
      {
          "name": "name",
          "email": "emailgmailcom",
          "password": "password"
      }  
      ```
**Scenario :**
  - **Register Admin created Successfully**
    - **Response Body :**
        ```json
        [
            "data": {
                "id": "<generated-id>",
                "email": "email@gmail.com",
                "password": "<hashed-password>"
            },
            "message": "Register Admin created successfully"
        ]
        ```
    - **Status Code :**
    <span style="color: springgreen">201 Created</span>
  - **Failed due to Validation error**
    - **Response Body :**
        ```json
        [
            "data": {
                "email": "Please provide a valid email"
            },
            "message": "Validation Errors"
        ]
        ```
    - **Status Code :** 
    <span style="color: lightsalmon">400 Bad Request</span> 
****
**2. Login as Admin**
- **Method :** `POST`
- **Endpoint :** `/admins/login`
- **Request Body :**
    ```json
    {
        "email": "email@gmail.com",
        "password": "password"
    }
    ```
**Scenario :**
- **Registered Admin**
    - **Response Body :**
        ```json
        {
            "access_token": "<generated_token>"
        }
        ```
    - **Status Code :**
      <span style="color: springgreen">200 Ok</span>
- **Unregistered Admin**
    - **Response Body :**
        ```json
        {
            "access_token": ""
        }
        ```
    - **Status Code :**
      <span style="color: springgreen">200 Ok</span>
****
**3. List of Admin**
- **Method :** `GET`
- **Endpoint :** `/admins`
- **Headers :**
  
  | Key           | Value          |
  |---------------|----------------|
  | Authorization | <access_token> |

**Scenario :**
- **Valid Admin token**
  - **Response Body :**
    ```json
    {
        "data": {
            "content": [
                {
                    "id": "<generated-id>",
                    "name": "name1",
                    "email": "email1@gmail.com",
                    "password": "<hashed-password>"
                },
                {
                    "id": "<generated-id>",
                    "name": "name2",
                    "email": "email2@gmail.com",
                    "password": "<hashed-password>"
                }
            ],
            "total_elements": 2,
            "total_pages": 1,
            "page": 0,
            "size": 10
        },
        "message": "ok"
    }
    ```
  - **Status Code :**
    <span style="color: springgreen">200 Ok</span>
- **Invalid token**
  - **Response Body :**
    ```text
        Invalid Token
    ```
  - **Status Code :**
    <span style="color: lightsalmon">403 Forbidden</span>
- **Invalid Admin token**
    - **Response Body :**
      ```text
          Access Denied
      ```
    - **Status Code :**
      <span style="color: lightsalmon">403 Forbidden</span>  

## Admin
***
#### Index:
1. [Register](#1-register-admin-credentials)
2. [Login](#2-login-as-admin) 
3. [Get All](#3-list-of-admin)
4. [Get By](#4-get-admin-by-credentials)
5. [Update](#5-update-admin-credentials)
6. [Delete](#6-delete-admin)
***  
#### **1. Register Admin Credentials**
- **Method :** `POST`
- **Endpoint :** `/admins/register`
- **Request Body**
  - **Valid email :**
      ```json
      {
          "name": "admin",
          "email": "admin@gmail.com",
          "password": "admin"
      }
      ```
  - **Invalid email :**
      ```json
      {
          "name": "admin",
          "email": "admingmailcom",
          "password": "admin"
      }  
      ```
**Scenario :**
  - **Register Admin created Successfully**
    - **Response Body :**
        ```json
        [
            "data": {
                "id": "generateduuidadmin",
                "email": "admin@gmail.com",
                "password": "hashedpassword1"
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
    <span style="color: tomato">400 Bad Request</span>
****
[Go to Index](#index)
****
#### **2. Login as Admin**
- **Method :** `POST`
- **Endpoint :** `/admins/login`
- **Request Body :**
    ```json
    {
        "email": "admin@gmail.com",
        "password": "admin"
    }
    ```
**Scenario :**
- **Registered Admin**
    - **Response Body :**
        ```json
        {
            "access_token": "admintoken"
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
[Go to Index](#index)
****
#### **3. List of Admin**
- **Method :** `GET`
- **Endpoint :** `/admins`
- **Request Headers :**
  
  | Key           | Value      |
  |---------------|------------|
  | Authorization | admintoken |

**Scenario :**
- **Valid Admin token**
  - **Response Body :**
    ```json
    {
        "data": {
            "content": [
                {
                    "id": "generateduuidadmin",
                    "name": "admin",
                    "email": "admin@gmail.com",
                    "password": "hashedpassword1"
                },
                {
                    "id": "generateduuidadmin2",
                    "name": "admin2",
                    "email": "admin2@gmail.com",
                    "password": "hashedpassword2"
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
- **Invalid token or Token Expired**
  - **Response Body :**
    ```text
        Invalid Token
    ```
  - **Status Code :**
    <span style="color: tomato">403 Forbidden</span>
- **Invalid Admin token**
    - **Response Body :**
      ```text
          Access Denied
      ```
    - **Status Code :**
      <span style="color: tomato">403 Forbidden</span>
****
[Go to Index](#index)
****
#### **4. Get admin by credentials**
- **Method :** `GET`
- **Endpoint :** `/admins`
- **Request Param :**

  | Key   | Value              |
  |-------|--------------------|
  | id    | generateduuidadmin |
  | name  | admin              |
  | email | admin@gmail.com    |
- **Request Headers :** 

  | Key           | Value      |
  |---------------|------------|
  | Authorization | admintoken |
**Scenario :**
- **Based on params credentials**
  - **Response Body :**
    ```json
    {
        "data": {
            "content": [
                {
                    "id": "generateduuidadmin",
                    "name": "admin",
                    "email": "admin@gmail.com",
                    "password": "hashedpassword1"
                }
            ],
            "total_elements": 1,
            "total_pages": 1,
            "page": 0,
            "size": 10
        },
        "message": "ok"
    }    
    ```
  - **Status Code :**
    <span style="color: springgreen">200 Ok</span>  
- **Invalid token or Token expired**
    - **Response Body :**
      ```text
          Invalid Token
      ```
    - **Status Code :**
      <span style="color: tomato">403 Forbidden</span>
- **Invalid Admin token**
    - **Response Body :**
      ```text
          Access Denied
      ```
    - **Status Code :**
      <span style="color: tomato">403 Forbidden</span>
****
[Go to Index](#index)
****
#### **5. Update Admin credentials**
- **Method :** `PUT`
- **Endpoint :** `/admins/update`
- **Request Header :**

  | Key           | Value      |
  |---------------|------------|
  | Authorization | admintoken |
- **Request Body :**
    ```json
    {
        "id": "generateduuidadmin"
        "name": "adminupdate",
        "email": "edminupdate@gmailcom",
        "password": "passwordupdate"
    }  
    ```
**Scenario**
- **Success update**
  - **Response Body :**
    ```json
    {
        "id": "generateduuidadmin",
        "email": "adminupdate@gmail.com",
        "password": "hashedpasswordupdate"
    }
    ```
  - **Status Code :**
    <span style="color: springgreen">200 Ok</span>
- **Token doesn't match**
  - **Response Body :**
    ```text
        Failed to Find
    ```
  - **Status Code :**
    <span style="color: tomato">403 Forbidden</span>
****
[Go to Index](#index)
****
#### **6. Delete admin**
- **Method :** `DELETE`
- **Endpoint :** `/admins/{id}`
- **Path Variable :** `/generateduuidadmin`
- **Request Header :**

  | Key           | Value      |
  |---------------|------------|
  | Authorization | admintoken |
**Scenario :**
- **Successfull delete**
  - **Response Body :**
    ```json
    {
        "data": null,
        "message": "Admin deleted successfully"
    }
    ```
  - **Status Code :**
    <span style="color: springgreen">200 Ok</span>
- **Token doesn't match**
  - **Response Body :**
    ```text
    Failed to delete admin
    ```
  - **Status Code :**
    <span style="color: orangered">500 Internal Server Error</span>
- **Id doesn't match**
    - **Response Body :**
      ```text
      Failed to find
      ```
    - **Status Code :**
      <span style="color: tomato">403 Forbidden</span>
****
[Go to Index](#index)
****
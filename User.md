## User
***
#### Index:
1. [Register](#1-register-user-credentials)
2. [Login](#2-login-as-user)
3. [Get All](#3-list-of-users)
4. [Get By](#4-get-user-credentials)
5. [Update](#5-update-user-credentials)
6. [Delete](#6-delete-user)
***
#### **1. Register User Credentials**
- **Method :** `POST`
- **Endpoint :** `/users/register`
- **Request Body**
    - **Valid email :**
        ```json
        {
            "name": "user",
            "email": "user@gmail.com",
            "password": "user"
        }
        ```
    - **Invalid email :**
        ```json
        {
            "name": "user",
            "email": "usergmailcom",
            "password": "user"
        }  
        ```
**Scenario :**
- **Register User created Successfully**
    - **Response Body :**
        ```json
        [
            "data": {
                "id": "generateduuiduser",
                "email": "user@gmail.com",
                "password": "hashedpassword3"
            },
            "message": "Register User created successfully"
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
#### **2. Login as User**
- **Method :** `POST`
- **Endpoint :** `/users/login`
- **Request Body :**
    ```json
    {
        "email": "user@gmail.com",
        "password": "user"
    }
    ```
**Scenario :**
- **Registered User**
    - **Response Body :**
        ```json
        {
            "access_token": "usertoken"
        }
        ```
    - **Status Code :**
      <span style="color: springgreen">200 Ok</span>
- **Unregistered User**
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
#### **3. List of Users**
- **Method :** `GET`
- **Endpoint :** `/users`
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
                      "id": "generateduuiduser",
                      "name": "user",
                      "email": "user@gmail.com",
                      "password": "hashedpassword3"
                  },
                  {
                      "id": "generateduuiduser2",
                      "name": "user2",
                      "email": "user2@gmail.com",
                      "password": "hashedpassword4"
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
- **Using user Token**
    - **Response Body :**
      ```text
          Access Denied
      ```
    - **Status Code :**
      <span style="color: tomato">403 Forbidden</span>
****
[Go to Index](#index)
****
#### **4. Get user credentials**
- **Method :** `GET`
- **Endpoint :** `/users`
- **Path Variable :** `/generateduuiduser`
- **Request Headers :**

  | Key           | Value     |
  |---------------|-----------|
  | Authorization | usertoken |
**Scenario :**
- **Based on params credentials**
    - **Response Body :**
      ```json
      {
          "data": {
              "content": [
                  {
                      "id": "generateduuiduser",
                      "name": "user",
                      "email": "user@gmail.com",
                      "password": "hashedpassword3"
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
          Failed to find
      ```
    - **Status Code :**
      <span style="color: tomato">403 Forbidden</span>
****
[Go to Index](#index)
****
#### **5. Update user credentials**
- **Method :** `PUT`
- **Endpoint :** `/users/update`
- **Request Header :**

  | Key           | Value     |
  |---------------|-----------|
  | Authorization | usertoken |
- **Request Body :**
    ```json
    {
        "id": "generateduuiduser"
        "name": "userupdate",
        "email": "userupdate@gmailcom",
        "password": "userupdate"
    }  
    ```
**Scenario**
- **Success update**
    - **Response Body :**
      ```json
      {
          "id": "generateduuiduser",
          "email": "userupdate@gmail.com",
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
#### **6. Delete user**
- **Method :** `DELETE`
- **Endpoint :** `/users/{id}`
- **Path Variable :** `/generateduuiduser`
- **Request Header :**

  | Key           | Value     |
  |---------------|-----------|
  | Authorization | usertoken |
**Scenario :**
- **Successfull delete**
    - **Response Body :**
      ```json
      {
          "data": null,
          "message": "User deleted successfully"
      }
      ```
    - **Status Code :**
      <span style="color: springgreen">200 Ok</span>
- **Token doesn't match**
    - **Response Body :**
      ```text
      Failed to delete user
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
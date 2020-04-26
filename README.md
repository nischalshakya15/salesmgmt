## SalesManagement authorization module
This project demonstrates how we can build an authorization module in spring security and how we can pass authentication to 
another module i.e. [spring-security](https://github.com/nischalshakya15/spring-security) authentication module.  

It is the simple salesmgmt CRUD application using ArrayList. Since, it is the authorization module where user should be 
authenticated before accessing the API. In this case, authentication is done solely in spring-security module and 
only the authorization part is done in this module. 

We have two user (user and admin). User can view sales only whereas Admin can view, update and delete sales. 

## Prerequisite
* Java 8 or higher
* Maven 
* Docker (optional)
* Heroku (optional)
* IDE 

## Build and Run
* git clone https://github.com/nischalshakya15/salesmgmt.git

* Go to the project directory.
    
    ``cd sales-mgmt``
    
* Build the project with specific profile. i.e: dev or prod. By default, profile will be dev if not specified.

    ``./mvnw clean install -P{PROFILE_NAME}``
    
* Go to the target directory.
    
    ``cd /target``
    
* Run the project 
    
    ``java -jar salesmgmt-authorization.war``

## Run with docker 
* Build the project using mvn command. 

    ``./mvnw clean install -P{PROFILE_NAME} package``
    
* Build and run the container. 

    ``docker-compose --compatibility up``
    
## Deploy in Heroku 
* Build the project.
    
    ``./mvnw clean install -P{PROFILE_NAME}``
    
* Add the following dependency in plugins section of pom.xml. 

    ```xml
    <plugin>
      <groupId>com.heroku.sdk</groupId>
      <artifactId>heroku-maven-plugin</artifactId>
      <version>3.0.2</version>
    </plugin>
    ```

* Login into you heroku account.

    ``heroku login``

* Create a Procfile in root directory of your project.

    ``touch Procfile``

* Add the following command in Procfile. 

    ``web: java -jar target/{WAR_FILE_NAME}.war``

* Create an app in heroku.

    ``heroku create --app {APP_NAME}``

* Deploy the war file in heroku. 

    ``./mvnw clean package heroku:deploy-war``

* Open the app. 

    ``heroku open``

* View the app logs.

    ``heroku logs --tail``
             

## Swagger documentation
* Go to the browser

   ``http://localhost:8080/swagger-ui.html``

   ![API Endpoints](./images/salesmgmt-swaggerui.png)


## Access an API endpoints 

* Authenticate the user with **userName admin**  and **password admin** or user with **user** and **user** from 
  [spring-security](https://spring-security-jwt-module.herokuapp.com/swagger-ui.html#/auth-resource/authenticateUserUsingPOST) **/api/auth/authenticate** 
  then copy the accessToken.
  
  ![Authentication successful](./images/authenticate.png)

* Using that accessToken we can access the protected end points of **sales-resource**.
   
  ![Access SaleResource](./images/findAllSales.png) 

* If user token is provided instead of admin token then user can only view the sales 
  but cannot delete or create a sales.

  ![Access User SaleResource](./images/userAccessSaleResource.png)
  
  ![AccessDenied User SaleResource](./images/userResourceAccessDenied.png)  

**Note: You can also use curl command as shown in Curl section of swagger-ui**
    
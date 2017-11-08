# bookstore-backend

### backend written in Java EE for managing books for a bookstore

## This app uses:
  ### maven as build and package manager
  ### JOBSS (Wildfly) as application server
  ### H2 as database for inbuilt storing
  
 ## To run:
  . import the source code
  . build it as maven project (clean, install, compile)
  . setup your local instance of Wildfly - and run it
  . build the app 
  . run and deployt the app in Wildfly
  . access the app at http://localhost:8080/bookstore-back/
  
 ## to access the API
   . use any third party tool like Postman
   . use inbuild Rest plugins available in IDE, like in Intellij
   . use swagger-ui created by this app (once hosted in Wildfly) at http://localhost:8080/bookstore-back/swagger-ui/#/

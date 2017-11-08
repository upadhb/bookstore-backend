# bookstore-backend

Backend API written in Java EE for managing books for a bookstore

## This app uses:
  1. maven as build and package manager
  2. JOBSS (Wildfly) as application server
  3. H2 as database for inbuilt storing
  
 ## To run:
  1. import the source code
  2. build it as maven project (clean, install, compile)
  3. setup your local instance of Wildfly - and run it
  4. build the app 
  5. run and deployt the app in Wildfly
  6. access the app at http://localhost:8080/bookstore-back/
  
 ## to access the API
   1. use any third party tool like Postman
   2. use inbuild Rest plugins available in IDE, like in Intellij
   3. use swagger-ui created by this app (once hosted in Wildfly) at http://localhost:8080/bookstore-back/swagger-ui/#/

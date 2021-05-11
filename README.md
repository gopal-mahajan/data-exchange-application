
## IoT Data Exchange Application


Created a Java spring boot application of an Iot Data Exchange Application and PostgreSQL for data storing.  Application follows separation of concerns and SOLID principles.

DataController has four RestApi’s which are:- 

**Get /data** -
*Input- dataId 
Logic - A getDatabyId() will be called which will check whether the dataId exists in the database or not. If present it will return the response entity along with the Data else DataNotFoundException will be thrown.

**Post /data** -
*Input: DataDTO  
Logic - first data is validated if the provided data is not in correct format Invlid Input exception will be thrown and returned.
if the data is in correct format then the database will be checked whether it contains the same id or not. If the given id is already present, a DataAlreadyExistException will be thrown. Else data will be saved in the database.


**Put /data/update** 
*Input all fields that has to be edited in sample with dataId marked as required. 
Logic - editData() will first check in the database if it has the data for the given id or not. If present, non null values of the input will be updated and saved. Else DataNotFoundException will be thrown.

**Delete /data** - *Input dataId
Logic - deleteData() will be called which will check for the database for dataId existence. If found that data will be deleted. If not dataNotFound Exception will be thrown.
Also, given test cases using JUnit 5.

In this application, I have added authentication as well.

As soon as the user starts the application and opens localhost:8080 in the browser, an authentication page will appear which will only allow assigned users to get into the application .



After the authentication process only a user can call APIs.

**Postman Collection link :**
https://www.getpostman.com/collections/bca92b6a790843d0c7cc

To use this collection import the collection in Postman.

# Deployment

To Run project locally : -
You need to have Postgres, if not installed then follow - https://www.postgresql.org/docs/12/installation.html

Create database called - “data_exchanges”
Either Set user and password as  “postgres” and 123 respectively in postgres. Or edit application.yml and set your user and password in spring.datasource.username 
And spring.datasource.password  

Build the project using - mvn clean install

Run the project and follow any one of the following steps to run the application:
1) add configuration in intellij - add main class - "com.data.kaveri.dataexchange.IoTDataExchangeApplication"
2) java -jar <path_to_repo>\data-exchange\target\data-exchange-0.0.1-SNAPSHOT.jar 

First, open browser and open localhost:8080 and Login
for username: "user"
password:"123" 

1) Using browser link -  localhost:8080/swagger-ui.html
2) Using Postman.

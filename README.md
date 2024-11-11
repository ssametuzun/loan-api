# Project Title
Loan API Service

## Description
This repository provides an API for managing loan and installment operations, including creating loans, listing 
loans by customer ID, and making payments towards a loan. The API includes validation for business rules like allowed 
interest rates and installment numbers, as well as handling of loan payments with penalties and discounts based on due dates.

## Technologies
* Spring Boot: Framework used for building the API.
* Spring Data JPA: To interact with the database and perform CRUD operations.
* H2 Database: In-memory database for testing and development purposes.
* Lombok: To reduce boilerplate code for getters, setters, builders, and constructors.


## Requirements
* Java 8 or above
* Maven 3.6.0 or above
* Git

### Clone the repository
git clone https://github.com/ssamet-uzun/loan-api.git

### Go to project directory
cd loan-api

### Build
mvn clean install

### Run the project
mvn spring-boot:run

The application will start on http://localhost:8080 by default.


## Configuration
The service uses application.yml for configuration.

### Environment Variables
The following environment variables can be configured:

domain.allowing-number-of-installment: A list of allowed numbers of installments.
domain.allowing-interest-rate: A list of allowed interest rates.


## Endpoints
There are two roles: ADMIN and USER. USER can only use endpoints that it is allowed to access, 
otherwise ADMIN use all endpoints.
The other important part is that CUSTOMER and USER are not the same. USER is the user that the customer needs to use to perform their transactions.
Therefore, first we need to have the CUSTOMER, then we can create USERS through the CUSTOMER. And all transactions
customer by the associated user.

Before getting started, all endpoints is secured with token except "/auth/**". To use endpoints, you should have a 
token. 


### To Get Token
Send request /auth/login with existing user, otherwise you will get error. If you use the system for first time, use 
admin credentials

curl --location 'http://localhost:8090/api/v1/auth/login' \
--header 'Content-Type: application/json' \
--data '{
"username": "admin",
"password": "pass"
}'

### To list all customers
Only admin can access this endpoint.

curl --location 'http://localhost:8090/api/v1/customer/list-all-customers' \
--header 'Authorization: Bearer __TOKEN__'

### To list all users
Only admin can access this endpoint.

curl --location 'http://localhost:8090/api/v1/list-all-users' \
--header 'Authorization: Bearer __TOKEN__'


### To create a customer
Only admin can access this endpoint.

curl --location 'http://localhost:8090/api/v1/customer/create' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer __TOKEN___' \
--data '{
    "name": "john",
    "surname": "doe",
    "creditLimit": 250000
}'


### To create a user
After creating a customer, customer should have an user information to use system. 
Following information is required to create a new user.

customerId: Customer ID we have created
username: Username for the user
password: Password for the user
admin: true or false

curl --location 'http://localhost:8090/api/v1/auth/sign-up' \
--header 'Content-Type: application/json' \
--data '{
    "customerId": 1,
    "username": "john-doe",
    "password": "pass",
    "admin": false
}'

Now we have a user who can use the system

### To create a loan

Before going on, get token for user who is newly created.

No need to send customer id, system will automatically detect user with customer by parsing token. You cannot create a 
loan for other users.

curl --location 'http://localhost:8090/api/v1/loan/create-loan' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer __TOKEN___' \
--data '{
"customerId": 1,
"amount": 500,
"interestRate": 0.4,
"numberOfInstallments": 9
}'

### To list installments

Users who is logged in can see installments on them. It's pageable api. User can keep the list shorter with changing 
parameters

curl --location 'http://localhost:8090/api/v1/loan/list-installments-by-loan-id?page=0&size=10&loanId=1' \
--header 'Authorization: Bearer __TOKEN__'

### To list loan on customer

Users can see all loans that is on them. You can use this api with customer id, but if customer wants to see loans 
of other customers, system will now allow them

curl --location 'http://localhost:8090/api/v1/loan/list-loan-by-customer-id?customerId=1&page=0&size=10' \
--header 'Authorization: Bearer __TOKEN__'

### To pay loan

To pay loan, user should send a request with loan id, and amount that will paid. Customer cannot pay the loan of 
other customers, system will now allow this operation

curl --location 'http://localhost:8090/api/v1/loan/pay-loan' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer __TOKEN__' \
--data '{
"loanId": 1,
"amountToBePaid": 165
}'

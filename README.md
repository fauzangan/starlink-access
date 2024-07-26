# Dokumentation For Starlink Access

- ### Overview
The Starlink Access project uses springboot as a framework. We can visit https://start.spring.io/ to create a springboot project.
Starlink Access provides services to customers such as registration for monthly subscriptions to Starlink packages, purchase of Starlink antennas, 
and purchase of Starlink internet quota. The project is also integrated with Mitrans for payment needs.

- ### Project Structure
In this project, several dependencies are used, including In this project, some of the dependencies used include Lombok which functions to facilitate 
writing code with annotations, JDBC API functions to connect to the database, Spring Data JPA functions for CRUD operations on the database, 
Spring Security functions for authentication and authorization, PostgreSQL Driver as a database driver for PostgreSQL.

- Database

The Starlink Access application uses PostgreSQL as the main database. The database schema includes several tables, including a user table that is useful 
for storing customer information, a payment table to store payment history, a product table to store (consisting of antennas and quotas) and a transaction 
table to store purchase transaction information.

- Securtiy Settings

To maintain security in the application, this project uses JWT Authentication for user authentication and authorization. In addition, Role-Based Access Control (RBAC) 
with JSON Web Tokens (JWT) serves to restrict system access based on the role that the user has, so that each role has certain predefined access rights.

- ### Usage Flow

- Account Registration and Management

In the initial stage, customers are required to register so as to get a new account through the registration form. After registration, 
customers can login for authentication so that they can access the services available on Starlink Access.

- Payment

Payment activities were integrated with Mitrans. The system will process payments, perform validation, and store customer payment 
history. With this integration, customers can easily make payments and get confirmation instantly.

- Antenna and Internet Quota Purchase

Customers can purchase Starlink antennas and internet quota through the purchase page. After selecting the desired product, customers 
will be directed to make payment through Mitrans. After successful payment, the purchase status will be automatically updated and customers 
can start using the purchased products.




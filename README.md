# CoursePlatform

service layer vs repository
-----------------------------

Repository layer is implemented to access the database and helps to extend the CRUD operations on the database. Whereas a service layer consists of the business logic of the application and may use the repository layer to implement certain logic involving the database. In an application, it is better to have a separate repository layer and service layer. Having separate repository and service layers make the code more modular and decouple the database from business logic.


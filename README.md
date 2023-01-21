# Web Development Project 

Example E-commerce application using Java Spring MVC, Maven, Bootstrap and Docker.

Features:
- Search
- User account creation with password hashing
- Functional Shopping Cart
- Order History, 
- Payment Flow Emulation
- Inventory Management (Admin)
- Order Management (Admin)
- Custom Product SKU Generator using SequenceStyleGenerator
- Product Reccomendation

 [](wireframe/home_screen.png)

[Wireframe](https://gitlab.com/comp30860/2021/distributed-imagination/-/tree/master/wireframe)


## Running via docker-compose

First, from the root of the repository, install all dependencies and build the jars with maven:
```
mvn clean install
```

Then you can build the docker images and finally, run them:
```
docker-compose build
docker-compose up
```

## Running without docker

First, from the root of the repository, install all dependencies and build the jars with maven:
```
mvn clean install
```

Then you can run with Springboot
```
mvn spring-boot:run
```

## General Use

You can log in as an administrator with the following credentials:
```
username: admin
username: admin
```

Otherwise you can browse the site without logging in, or log in to make a purchase!




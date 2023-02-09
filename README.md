# MKBankApp2

![MKBankApp2- screen](https://user-images.githubusercontent.com/96115456/217782586-3cb6bf5e-11bf-43df-976f-859be9058450.png)

## What is this?
This is small project created for training purposes. Application is based on Java, Hibernate and MySQL Database. This is continuation of my first project which was based on JDBC ([MKBankApp](https://github.com/mat0714/mk-bank)).

Using this application you can:
- create new customer profile
- create account for existing customer (checking or savings)
- show single customer profile (with accounts)
- deposit money
- withdraw money
- show all customers profiles (with their accounts)
- change all savings accounts rate of interest

Every operation you can initiate directly from the console.

## How to use it?
After running application in the console you should see welcome message and actions which you are able to perform. Type the number of operation which you want to do and press enter on your keyboard. Next provide necessary data. All results of your activity will be safely saved in database.

## Prerequisites
- Java 17
- Maven 3.6
- MySQL 8.0
- IDE (I use IntelliJ IDEA)

## Run
1. Clone the source code from Github:
```
https://github.com/mat0714/mk-bank-2
```
2. Create MySQL schema and give it the name "mkbank2"
3. Open project in your IDE. In IntelliJ IDEA simply click "run". All needed dependencies should be downloaded automatically by Maven).

## Technologies
<img src="https://img.shields.io/badge/-JAVA 17-red" alt="Java 17" />  <img src="https://img.shields.io/badge/-HIBERNATE-red" alt="Hibernate" />
<img src="https://img.shields.io/badge/-JUNIT-red" alt="JUnit" /> <img src="https://img.shields.io/badge/-MYSQL-red" alt="MySQL" />
<img src="https://img.shields.io/badge/-MAVEN-red" alt="Maven" /> 

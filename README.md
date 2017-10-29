This guide walks you through the process of building a RESTful service with [Spring Boot]( https://projects.spring.io/spring-boot/).

## Goal

The goal is to build a RESTful application that stores user information and provides the following functionality:

* Get a list of users in the system
* Create user
* Update user
* Change password for a given user

## Usage

### List Users

The service will handle `GET` requests for `/users`. The `GET` request should return a `200 OK` response with JSON in the body that lists information about each of the users. The JSON should look something like this:

```
[
  {
    "username": "johndoe",
    "email": "johndoe@example.com",
    "firstName": null,
    "lastName": null
  },
  {
    "username": "janedoe"
    "email": "janedoe@example.com",
    "firstName": null,
    "lastName": null
  }
]
```

### Create a User

The service will handle `POST` request for `/users`. The `POST` request should return a `201 CREATED` response. The resource for the new user should be available at `/users/{username}` where `{username}` is the name of the user.

After the resource is created, the service will handle `GET` request for `/users/{username}`. The `GET` request should return a `200 OK` response. The response body will be JSON and it should look something like this:

```
{
  "username": "johndoe",
  "email": "johndoe@example.com",
  "firstName": null,
  "lastName": null
}
```

### Update a User

The service will handle `PUT` request for `/users/{username}` where `{username}` is the name of the user. If the resource already exists, then it will be updated. Otherwise, a new user will be created and the resource will be available.

Note that PUT replaces the entire resource. Fields not supplied will be replaced with null. 

### Change password

A `PUT` request for `/users/{username}` can be used to change the password for the user with name `{username}`.

## See Also

The following guides may also be helpful:

* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Accessing JPA Data With REST](https://spring.io/guides/gs/accessing-data-rest/)

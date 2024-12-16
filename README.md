# A3-Pokedome

Assignment 3 for CS6310 - Group 17 - Jacob Berwick, Andrew Johnson, Dev Patel, Kateka Seth, Mitchell Wacker

## Description

This project is a web application that will simulate Pokemon battles in the Pokedome.

## Setup Pre-requisites

1. Download Docker Desktop from [here](https://www.docker.com/products/docker-desktop)
2. Download Maven from [here](https://maven.apache.org/download.cgi) (Only needed if you want to run backend outside docker)
3. Install make by running `brew install make` (MacOS)
4. Set JAVA_HOME variable to use Java 21 in order to build the project (Only needed if you want to run backend outside docker)

## Quick Start

We can simply run `make` to start our service. For those that do not have `make` installed, we can
effectively do the same thing by running the following:

```
docker build -t pokedome/backend -f ./images/Dockerfile.backend ./backend
docker build -t pokedome/frontend -f ./images/Dockerfile.frontend ./frontend
docker-compose -p pokedome -f docker-compose.yml up -d
```

- Go to [http://localhost:3001](http://localhost:3001) to view the front end
- Go to [http://localhost:8080/hello](http://localhost:8080) to view the back end (This hits a test API to make sure backend is running)

Run `make clean` to remove all containers and images. For those that do not have `make` installed, we can effectively do the same thing by running the following:

```
docker-compose -p pokedome -f docker-compose.yml down
docker image rm pokedome/frontend
docker image rm pokedome/backend
docker image rm postgres:16.2
```

To change the frontend, we have to manually build the flutter project before running the `make` calls above. Do this by `cd frontend` and then `flutter build web`. Change back to the root project directory and run the `make` calls above. This is **not** required if frontend is not chnaged.

## Debugging

To view docker logs for the backend service, run `docker ps` to get the container ID. The
run `docker logs <container_id>`

## Docker

- docker-compose.yml - This file is well commented for additional details, and we recommend reading over this file to
understand what is happening.
- images/Dockerfile.backend - This file is used to build the backend service image
- images/Dockerfile.frontend - This file is used to build the frontend service image

Useful Docker commands:

- `docker ps` - to list all running containers
- `docker stop $(docker ps -a -q)` - to stop all containers
- `docker container prune` or sometimes  `docker rm $(docker ps -a -q)` - to delete all stopped containers
- `docker network prune` - to delete all networks
- `docker image prune` - to delete all images

## Frontend

The frontend service is built on the Flutter framework. It calls the backend through RESTful API requests. You should be able to navigate to [http://localhost:3001](http://localhost:3001) to view the page

## Backend

Our backend is Spring Boot application with multiple controllers. This service (as defined in our docker-compose file)
is exposed on port 8080.
It is important to note that since our backend and frontend are split between two containers, we need to deal
with [cross-origin resource sharing](https://en.wikipedia.org/wiki/Cross-origin_resource_sharing). You see that this
application has been pre-defined to allow our front end service to access our backend using Spring's `@CrossOrigin`
annotation. If you deviate from our starter code, and split your frontend container from your backend, you will also
need to ensure your web application sets this response header.
Our backend is bootstrapped to communicate with our database container using the Java Persistenace API (JPA) with
Hibernate.
You will see the configuration in the `application.properties` file. Note that we have hibernate configured to
automatically create the table as needed

A lot of this backend uses the [Spring Boot](https://spring.io/projects/spring-boot) framework. Take a look at the
documentation to familiarize yourself with it.

## Database

The database is postgres 9.6.12.

## Maven

This project uses [Apache Maven](https://maven.apache.org/) to manage itself.
You will see the dependencies defined in `/backend/pom.xml`, and the maven commands called by the backend's Dockerfile.
Most used commands are:
- `mvn clean install` - to build the project
- `mvn test` - to run the tests
- `mvn spring-boot:run` - to run the application

## Testing

Unit tests are done using Mockito 2 and Junit 5.

Use `mvn test` to run tests.

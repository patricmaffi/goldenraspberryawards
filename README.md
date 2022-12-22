# Golden Raspberry Awards #

This project is technical challenge made to me for a Java Senior opportunity.<br/>

### What is this repository for? ###
* Quick summary <br/>
It's a Back End Java Spring Boot Web Application. <br/>
The application consists of a list of movies informations loaded in a H2 database.<br/>
It is possible calling rest api to insert new movies, list those movies and see the winners of Golden Raspberry Awards.<br/>
The main challenge is to show up the min and max interval of awards winners producers.<br/>

### How do I get set up? ###
#### Java Execution ####
* mvn clean package -f pom.xml
* java -jar target/GoldenRaspberryAwards-0.0.1-SNAPSHOT.jar

#### Java Dependencies ####
* [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
* [Maven](https://maven.apache.org/download.cgi)

#### Container Execution ####
* docker run -p 8080:8080 patricmaffi/golden-raspberry-awards

#### Container Dependencies ####
* [Docker](https://docs.docker.com/get-docker/)

### Who do I check movies results? ###

#### Web Browser ####
* http://localhost:8080/movie
* http://localhost:8080/awards
* http://localhost:8080/awards/interval

#### curl ####
* curl http://localhost:8080/movie
* curl http://localhost:8080/awards
* curl http://localhost:8080/awards/interval
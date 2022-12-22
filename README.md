# Golden Raspberry Awards #

This project is technical challenge made to me for a Java Senior opportunity.<br/>

### What is this repository for? ###
* Quick summary <br/>
The application consist in a list of movies informations loaded in a H2 database.<br/>
It is possible to insert new movies, list those movies and see the winners of Golden Raspberry Awards.<br/>
The main challenge is show up the min and max interval of awards winners producers.<br/>

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

### Who do I talk to? ###

* Repo owner or admin
* Other community or team contact
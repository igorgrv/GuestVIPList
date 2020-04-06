# VIPGuestList
## About the Project
This is a simple project, used to practice creating a **_Spring boot_** application!
The **purpose** of this project is to add guests to a "Vip" list and inform them via email that they have been invited
### Technologies used
* Spring Boot;
* Spring MVC;
* Spring DevTools &amp; LiveRealod;
* Spring Initializer;
* JPA;
* Java Commom Email; 
* AJAX;

## Spring Boot
### Why to use? 
The main utility of **Spring Boot** is at:
* Simplify application development.
* Configure Spring through Java classes, not XML

### How to use? 
1. Create a maven project;
2. Add the dependency below into the pom.xml
```xml
<parent>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-parent</artifactId>
	<version>2.2.2.RELEASE</version>
	<relativePath />
</parent>

<dependencies>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-web</artifactId>
	</dependency>
</dependencies>
```

### How to Configure the Server

### How to Configure the JPA
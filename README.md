# VIPGuestList

## About the Project

This is a simple project, based on **_Alura's Spring Boot course_**, used to practice creating a Spring boot application!
The **purpose** of this project is to add guests to a "Vip" list and inform them via email that they have been invited.
### Technologies/Database used
* Spring Boot;
* Spring MVC;
* Spring DevTools &amp; LiveRealod;
* Spring Initializer;
*  Thymeleaf;
* JPA;
* Java Commom Email; 
* AJAX;
* MySQL;

## Summary
1. [Starting the project - Spring Boot](#boot)
	* [Why to use Spring boot?](#why)
	* [How to use?](#howto)
	* [How to Configure/Run the Server](#configureserver)
	* [How to Configure the JPA + MySQL](#configurejpa)
2. [Creating the MVC](#mvc)
	* [Entity](#entity)
	* [Repository](#repository)
	* [Service](#service)
	* [Controller](#controller)
	* [Views - Thymeleaf](#views)
3. [Spring DevTools + LiveReload Plugin](#devtools)
4. [Spring Initializer](#initializer)
## <a name="boot"></a>Starting the project - Spring Boot
###  <a name="why"></a>Why to use Spring boot? 
The main utility of **Spring Boot** is at:
* Simplify application development.
* Configure Spring through Java classes, not XML

###  <a name="howto"></a>How to use? 
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

### <a name="configureserver"></a>How to Configure/Run the Server

1. Create a package _(com.vipguestlist.configurations)_ into the _src/main/java_;
2. Create a class with the **@SpringBootApplication** annotation and with a 'main method';
3. Run the server using the _run_ method;
4. Test, acessing `localhost:8080` and look if the "Whitelabel Error Page" appears;
_by default, the port used is 8080 and the container server is tomcat_
* @ComponentScan: is used to map the Controller/Service
* @EntityScan: to map the Entity
* @EnableJpaRepositories: to map the Repository


```java
package com.vipguestlist.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@ComponentScan({"com.vipguestlist.controller", "com.vipguestlist.service"})
@EntityScan({"com.vipguestlist.model"})
@EnableJpaRepositories({"com.vipguestlist.repository"})
public class Configuration {

	public static void main(String[] args) {
		SpringApplication.run(Configuration.class, args);
	}
}
```
### <a name="configurejpa"></a>How to Configure the JPA + MySQL
1. Add into the pom.xml the dependencies;
```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<dependency>
	<groupId>mysql</groupId>
	<artifactId>mysql-connector-java</artifactId>
</dependency>
```
2. Create a file into the folder **_src/main/resources_**, with the name **_application.properties_** and with the code below:
```
# Database
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost/viplist?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto = update
spring.jpa.show-sql = true
```
* **Another option**, it will be adding the code below, into the Configuration Class:
```java
@SpringBootApplication
public class Configuration {

	public static void main(String[] args) {
		SpringApplication.run(Configuration.class, args);
	}
	
	/** JPA ConfigurationS
	 *  @bean it's necessary for Spring to "manage" the class
	 *  DriverManagerDataSource returns DataSource
	 */
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource driver = new DriverManagerDataSource();
		driver.setDriverClassName("com.mysql.cj.jdbc.Driver");
		driver.setUrl("jdbc:mysql://localhost/viplist?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC");
		driver.setUsername("root");
		driver.setPassword("");
		return driver;
	}
}
```
## <a name="mvc"></a>MVC
## <a name="entity"></a>Entity
That will be a simple entity, called Guest.
1. Creates the Class Guest, into the package **_com.vipguestlist.model_**
```java
@Entity
public class Guest {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name, email, phone;

	/**
	 * @deprecated hibernate only
	 */
	public Guest() {}
	
	public Guest(String name, String email, String phone) {
		this.name = name;
		this.email = email;
		this.phone = phone;
	}
	
	//Getters and Setters
}
```


## <a name="repository"></a>Repository
Creates the **Interface** GuestRepository that extends **CrudRepository**, into the package **_com.vipguestlist.repository_** , passing the Guest Class, and the type of Id used (long)
```java
public interface GuestRepository extends CrudRepository<Guest, Long>{}
```
* Extending this interface, allows us to use a lot of pre-established methods, such as:
	*  save();
	* saveAll();
	* findOne();
	* findAll();
	* count();
	* delete();
	* existsById();
* If necessary, it's possible to create methods, such as the **"findByName"** method that we will implement, which will allow us to find a list of guests by the name:
 ```java
public interface GuestRepository extends CrudRepository<Guest, Long>{
	List<Guest> findByName(String name); 
}
```

## <a name="service"></a>Service

By default, the controller layer should not access business rules, in other words, the controller should not access the repository, but rather a "service", therefore:

Creates the class **GuestService** , into the package **_com.vipguestlist.service_** , adding the methods that we will use;

 ```java
@Service
public class GuestService {

	@Autowired
	private GuestRepository guestRepository;
	
	public Iterable<Guest> findAll(){
		return guestRepository.findAll();
	}
	
	public void save(Guest guest) {
		guestRepository.save(guest);
	}
}
```

## <a name="controller"></a>Controller
```java
@Controller
public class GuestController {

	@Autowired
	private GuestService service;
	
	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("guestlist")
	public ModelAndView guestList() {
		ModelAndView mv = new ModelAndView("guestlist");
		Iterable<Guest> guests = service.findAll();
		mv.addObject("guests", guests);
		return mv;
	}
	
	@PostMapping("save")
	public ModelAndView save(Guest guest) {
		service.save(guest);
		return new ModelAndView("redirect:guestlist");
	}
}
```
## <a name="views"></a>Views - Thymeleaf
  
In this project instead of jsp, **Thymeleaf** will be used, which is **Spring's default Expression Language**. 
To use, it's necessary to add the dependency into the pom.xml:
```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```
* By convention, templates (HTML) are placed into the **_main/resources/templates_** 
and resources, as Bootstrap, jQuery, into **_main/resources/static_**.

To use the Thymeleaf, it's necessary to put insithe de .html, the code below. That way, we can use the tag "th".
```html
<html xmlns:th="http://www.thymeleaf.org">
```
### ForEach - Thymeleaf
to use the "foreach" from the controller, just follow the code below:
```html
<tr th:each="guest : ${guests}">
	<td><span th:text="${guest.name}"></span></td>
	<td><span th:text="${guest.email}"></span></td>
	<td><span th:text="${guest.phone}"></span></td>
</tr>
```

---
**index.html**
```html
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>VIP List</title>
	<link href="/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
	</head>
	<body>
		<div class="container" >
			<div class="jumbotron" align="center" style="margin-top: 50px;">
				<h1>Welcome to the VIP List</h1>
				<div align="center">
					<a href="guestlist" class="btn btn-lg btn-primary">Click to see the VIP Guest List!</a>
				</div>
			</div>
		</div>
	    
	    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	    <script src="/bootstrap/js/bootstrap.min.js"></script>
	
	</body>
</html>
```
**guestlist.html**
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>VIP List</title>
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" />
</head>
<body>
	<div class="container">
		<div id="listaDeConvidados">
			<table class="table table-hover">
				<thead>
					<tr>
						<th>Name</th>
						<th>Email</th>
						<th>Phone</th>
					</tr>
				</thead>
				<tr th:each="guest : ${guests}">
					<td><span th:text="${guest.name}"></span></td>
					<td><span th:text="${guest.email}"></span></td>
					<td><span th:text="${guest.phone}"></span></td>
				</tr>
			</table>
		</div>
		
		<hr/>

		<form action="save" method="post">
			<div class="form-group">
				<label for="name">Full name</label> 
				<input type="text" class="form-control" id="name" name="name" placeholder="Name" />
			</div>
			<div class="form-group">
				<label for="email">Email</label> 
				<input type="email" class="form-control" id="email" name="email" placeholder="Email" />
			</div>
			<div class="form-group">
				<label for="phone">Phone</label> 
				<input type="text" class="form-control" id="phone" name="phone" placeholder="Phone" />
			</div>
			<button type="submit" class="btn btn-success">Invite</button>
		</form>
	</div>

	<script 	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="bootstrap/js/bootstrap.min.js"></script>
</body>
</html>
```
## <a name="devtools"></a> Spring DevTools + LiveReload Plugin

Spring DevTools + LiveRealod chrome
O spring DevTools é utilizado para fazer as atualizações automaticamente no servidor, tornando mais rápido os refreshs, pois ele irá apenas atualizar o que foi alterado, não a aplicação toda (hot deploy)
O liveReload é um plugin que vem para complementar, fazendo o refresh da página automaticamente!
```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-devtools</artifactId>
	<optional>true</optional>
</dependency>
```
[LiveReload - chrome](https://chrome.google.com/webstore/detail/livereload/jnihajbhpnppcggbcgedagnkighmdlei?hl=pt-BR)
## <a name="initializer"></a> Spring Initializer

Spring Initializer
O spring initializer facilita a criação dos projetos, de que selecionamos o que precisamos e ele configura o pom.xml e classes
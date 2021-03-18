# business-process-administration-portal

##### Business process administration portal service utilizes capabilities of Camunda API:

* `It is admin application for BMPS. The application brings the following functionality:`
    * [Camunda Cockpit](https://docs.camunda.org/manual/latest/webapps/cockpit/) for monitoring and operations; 
    * [Camunda Admin](https://docs.camunda.org/manual/latest/webapps/admin/) for user management;
    * [Camunda Tasklist](https://docs.camunda.org/manual/latest/webapps/tasklist/) for human task management;

##### Running the tests:

* Tests could be run via maven command:
    * `mvn verify` OR using appropriate functions of your IDE.

##### Local development:

* run spring boot application using 'local' profile:
  * `mvn spring-boot:run -Drun.profiles=local` OR using appropriate functions of your IDE.
* by default (*can be configured in the application-local.yml file*), the application will be available on:  http://localhost:8080
  * username: `camunda`
  * password: `camunda`
* logging settings (*level,pattern,output file*) for local development specified in the `application-local.yml` file;
  
##### Logging:

* `Default:`
  * For classes with annotation RestController/Service, logging is enabled by default for all public methods of a class;
* `To set up logging:`
  * *@Logging* - can annotate a class or method to enable logging;
  * *@Confidential* - can annotate method or method parameters to exclude confidential data from logs:
    - For a method - exclude the result of execution;
    - For method parameters - exclude method parameters;

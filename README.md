# business-process-administration-portal

### Overview

* Business process administration portal service utilizes capabilities of Camunda API. It is admin
  application for BMPS;
* [Camunda Cockpit](https://docs.camunda.org/manual/latest/webapps/cockpit/) for monitoring and
  operations;
* [Camunda Admin](https://docs.camunda.org/manual/latest/webapps/admin/) for user management;
* [Camunda Tasklist](https://docs.camunda.org/manual/latest/webapps/tasklist/) for human task
  management.
  
### Usage

#### Prerequisites:

* Postgres database is configured and running;
* Keycloak is configured and running.

#### Configuration

Available properties for authentication camunda web application:

* `keycloakClientId` -  client identifier;
* `keycloakAdminUrl` - url for admin realm;
* `keycloakcCamundaPluginAdminUrl` - keycloak admin url.

#### Run application:

* `java -jar <file-name>.jar`

### Local development

1. Run spring boot application using 'local' profile:
    * `mvn spring-boot:run -Drun.profiles=local` OR using appropriate functions of your IDE;
    * `application-local.yml` is configuration file for local profile.
2. The application will be available on: http://localhost:8080
    * username: `camunda`;
    * password: `camunda`.

### Test execution

* Tests could be run via maven command:
    * `mvn verify` OR using appropriate functions of your IDE.
    
### License

The business-process-administration-portal is Open Source software released under
the [Apache 2.0 license](https://www.apache.org/licenses/LICENSE-2.0).
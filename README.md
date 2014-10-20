How to use
==============

Regular WAR file: 
- Java 8
- Maven 3.2.1+
- mvn clean package

Implemented event endpoints: 
- http://localhost:8080/integrationchallenge/rest/api/subscription/create?url={eventUrl}
- http://localhost:8080/integrationchallenge/rest/api/update?url={eventUrl}
- http://localhost:8080/integrationchallenge/rest/api/cancel?url={eventUrl}
- http://localhost:8080/integrationchallenge/rest/api/user/assign?url={eventUrl}
- http://localhost:8080/integrationchallenge/rest/api/user/unassign?url={eventUrl}

Cloud version deployed at: http://integrationchallenge-devmind.rhcloud.com/
==============

- http://integrationchallenge-devmind.rhcloud.com/rest/api/subscription/create?url={eventUrl}
- http://integrationchallenge-devmind.rhcloud.com/rest/api/subscription/update?url={eventUrl}
- http://integrationchallenge-devmind.rhcloud.com/rest/api/subscription/cancel?url={eventUrl}
- http://integrationchallenge-devmind.rhcloud.com/rest/api/user/assign?url={eventUrl}
- http://integrationchallenge-devmind.rhcloud.com/rest/api/user/unassign?url={eventUrl}

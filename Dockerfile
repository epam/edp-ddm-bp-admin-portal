FROM nexus-docker-registry.apps.cicd2.mdtu-ddm.projects.epam.com/adoptopenjdk/openjdk11:alpine-jre
ADD *.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]

FROM maven:3.6.3-openjdk-14-slim

WORKDIR /app

# Prepare by downloading dependencies
ADD pom.xml /app/pom.xml
ADD api-test/pom.xml /app/api-test/pom.xml
ADD ui-test/pom.xml /app/ui-test/pom.xml
RUN mvn dependency:go-offline -B
ADD api-test/src /app/api-test/src
ADD ui-test/src /app/ui-test/src
RUN mvn package -DskipTests=true

ARG MODULE
ENTRYPOINT ["mvn", "-T=2", "test", "-Dtest.localDriver=false"]
#!/bin/sh

function run() {
  ./mvnw -Dmaven.tomcat.port=8081 tomcat7:run -pl *-server -am -Denv=dev & ./mvnw gwt:codeserver -pl *-client -am
  # mvn quarkus:dev -pl *-api -am
}

function cleanup {
  killall java
}

run
{ set +x; } 2>/dev/null
trap cleanup EXIT





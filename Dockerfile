FROM tomcat:jdk11
COPY target/*.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080

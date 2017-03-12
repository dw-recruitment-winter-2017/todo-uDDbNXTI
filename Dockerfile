FROM java:8-alpine
MAINTAINER Your Name <you@example.com>

ADD target/uberjar/dwcode.jar /dwcode/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/dwcode/app.jar"]

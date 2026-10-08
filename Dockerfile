# Stage 1: Build the Java classes
FROM eclipse-temurin:21-jdk as builder
WORKDIR /app
COPY . .
RUN mkdir -p web/WEB-INF/classes
RUN javac -cp "jakarta.servlet-api.jar" -d web/WEB-INF/classes src/exceptions/*.java src/model/*.java src/service/*.java src/web/*.java

# Stage 2: Deploy to Tomcat
FROM tomcat:11.0-jdk21
# Remove default Tomcat apps
RUN rm -rf /usr/local/tomcat/webapps/*
# Copy our compiled web application to the ROOT context
COPY --from=builder /app/web /usr/local/tomcat/webapps/ROOT
EXPOSE 8080
CMD ["catalina.sh", "run"]

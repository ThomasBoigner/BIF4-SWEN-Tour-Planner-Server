FROM openjdk:24 as builder
LABEL maintainer="Dominik Banach, Thomas Boigner"
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM openjdk:24
LABEL maintainer="Dominik Banach, Thomas Boigner"
COPY --from=builder dependencies/ ./
RUN true
# COPY --from=builder internal-dependencies/ ./
COPY --from=builder snapshot-dependencies/ ./
RUN true
COPY --from=builder spring-boot-loader/ ./
RUN true
COPY --from=builder application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]

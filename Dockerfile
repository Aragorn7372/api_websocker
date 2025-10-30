# contenedor temporal donde creamos el jar
FROM gradle:jdk25-alpine AS build
# directorio principal de trabajo
WORKDIR /app
# copimos los archivos del proyecto
COPY gradle gradle
COPY src src
COPY build.gradle.kts .
COPY gradlew .
# configurar entorno para que el host se pueda comunicar con el contenedor
ARG DOCKER_HOST_ARG=tcp://host.docker.internal:2375
ENV DOCKER_HOST=$DOCKER_HOST_ARG
# build
RUN ./gradlew build
# contenedor final con solo el jar y la ejecucion asi quitamos la basura
FROM eclipse-temurin:25-jre-alpine AS run
# Directorio principal de trabajo
WORKDIR /app
# copia el jar del anterior contenedor
COPY --from=build /app/build/libs/*SNAPSHOT.jar /app/my-app.jar
# Ejecuta el programa
ENTRYPOINT ["java","-jar","/app/my-app.jar"]
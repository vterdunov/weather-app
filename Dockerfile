FROM maven:3.9.9-amazoncorretto-17 AS builder

WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn dependency:go-offline
RUN mvn clean package assembly:single -DskipTests


FROM gcr.io/distroless/java17-debian12

COPY --from=builder /app/target/*-jar-with-dependencies.jar /app/app.jar

WORKDIR /app

CMD ["app.jar"]
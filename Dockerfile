# Build stage
FROM maven:latest AS BUILD
WORKDIR /workspace/app
COPY . .
RUN mvn clean package

#ADD ./target/kafka-consumer-0.0.1-SNAPSHOT.jar app.jar
ADD ./target/*.jar app.jar

RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../../app.jar)
RUN cat target/dependency/META-INF/MANIFEST.MF| perl -pe 'undef $/;s/\r\n //' |tr -d '\r'|grep Start-Class:|cut -f2 -d" " > target/dependency/start-class.txt

# Package stage
# Use a lightweight base image with a compatible JDK version
FROM eclipse-temurin:21-jre-ubi9-minimal

ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
COPY --from=build ${DEPENDENCY}/start-class.txt start-class.txt

ENV JAVA_OPTS=""
ENV SPRING_PROFILES_ACTIVE=""
EXPOSE 8080
# use exec java to ensure correct signal handling during shutdown of service,this will allow for de-registration from Eureka
ENTRYPOINT ["sh","-c","exec java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -cp app:app/lib/* $(cat start-class.txt)"]
#ENTRYPOINT exec java -jar $APP_HOME/$JAR_NAME
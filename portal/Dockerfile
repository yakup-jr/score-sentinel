FROM amazoncorretto:17-alpine
ADD /build/libs/ss-portal-0.0.1-SNAPSHOT.jar portal.jar
LABEL authors="yakup-jr"
ENTRYPOINT ["java", "-jar", "portal.jar"]
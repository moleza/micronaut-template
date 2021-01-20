FROM oracle/graalvm-ce:20.1.0-java11 as graalvm
RUN gu install native-image

COPY . /home/app/parks-service
WORKDIR /home/app/parks-service

RUN native-image --no-server -cp build/libs/parks-service-*-all.jar

FROM frolvlad/alpine-glibc
RUN apk update && apk add libstdc++
EXPOSE 8080
COPY --from=graalvm /home/app/parks-service/parks-service /app/parks-service
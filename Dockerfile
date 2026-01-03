# Java 21 kullanıyoruz (Projenle uyumlu sürüm)
FROM openjdk:21-jdk-slim

# Çalışma dizini belirle
WORKDIR /app

# Maven ile oluşturulan jar dosyasını container içine kopyala
# (Target klasöründeki .jar uzantılı dosyayı alıp app.jar yapıyoruz)
COPY target/*.jar app.jar

# Uygulamanın çalışacağı port
EXPOSE 8080

# Uygulamayı başlat
ENTRYPOINT ["java", "-jar", "app.jar"]
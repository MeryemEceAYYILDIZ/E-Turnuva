# Eclipse Temurin (Standart Java 21 İmajı) kullanıyoruz
FROM eclipse-temurin:21-jdk

# Çalışma dizini belirle
WORKDIR /app

# Maven ile oluşturulan jar dosyasını container içine kopyala
COPY target/*.jar app.jar

# Uygulamanın çalışacağı port
EXPOSE 8080

# Uygulamayı başlat
ENTRYPOINT ["java", "-jar", "app.jar"]
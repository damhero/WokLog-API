# ----- 1. Faza Budowania (Build Stage) -----
# Używamy oficjalnego obrazu Maven z Javą 21 do zbudowania aplikacji
FROM maven:3.9-eclipse-temurin-21 AS builder

# Ustawiamy katalog roboczy wewnątrz kontenera
WORKDIR /app

# Kopiujemy sam plik pom.xml, aby pobrać zależności
# Docker inteligentnie zbuforuje ten krok, jeśli pliki pom się nie zmienią
COPY pom.xml .
RUN mvn dependency:go-offline

# Kopiujemy resztę kodu źródłowego
COPY src ./src

# Budujemy aplikację i tworzymy plik .jar (pomijając testy, by przyspieszyć)
RUN mvn clean install -DskipTests


# ----- 2. Faza Uruchamiania (Run Stage) -----
# Używamy lekkiego obrazu JRE (Java Runtime Environment) w wersji 21
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Kopiujemy plik .jar zbudowany w poprzednim etapie (zmieniamy jego nazwę na app.jar)
# Upewnij się, że nazwa pliku .jar jest poprawna!
COPY --from=builder /app/target/WokLog-0.0.1-SNAPSHOT.jar app.jar

# Informujemy Dockera, że aplikacja będzie działać na porcie 8080
EXPOSE 8080

# Komenda, która uruchomi aplikację przy starcie kontenera
ENTRYPOINT ["java", "-jar", "app.jar"]
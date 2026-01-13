# Dockerfile pour le déploiement - Responsabilité Raph
# Image de base
FROM openjdk:11-jre-slim

# Variables d'environnement
ENV SPRING_PROFILES_ACTIVE=prod
ENV DB_HOST=postgres
ENV DB_PORT=5432

# Répertoire de travail
WORKDIR /app

# Copier le JAR
COPY target/taches-raph-1.0.0.jar app.jar

# Exposer le port
EXPOSE 8080

# Commande de démarrage
ENTRYPOINT ["java", "-jar", "app.jar"]

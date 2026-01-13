#!/bin/bash
echo "🚀 Démarrage de l'application avec MySQL..."

# Vérifier MySQL
if ! systemctl is-active --quiet mysql; then
    echo "⚠️  MySQL n'est pas démarré. Démarrage en cours..."
    sudo systemctl start mysql
    sleep 2
fi

# Vérifier la base de données
echo "🔍 Vérification de la base de données..."
mysql -u root -proot -e "CREATE DATABASE IF NOT EXISTS vehicules_db;" 2>/dev/null || {
    echo "❌ Impossible de se connecter à MySQL. Vérifiez:"
    echo "  1. MySQL est installé: sudo apt install mysql-server"
    echo "  2. MySQL est démarré: sudo systemctl start mysql"
    echo "  3. Le mot de passe root est correct dans application.properties"
    exit 1
}

# Nettoyer et compiler
echo "📦 Nettoyage et compilation..."
mvn clean compile -DskipTests

if [ $? -eq 0 ]; then
    echo "✅ Compilation réussie !"
    echo "🌐 Lancement de l'application sur http://localhost:8080"
    mvn spring-boot:run -DskipTests
else
    echo "❌ Échec de la compilation."
    echo "Essayez: rm -rf ~/.m2/repository/mysql/ && mvn clean compile -DskipTests -U"
fi

# Fantoir-groupe3



## La société Establish a besoin d'une base de données pour le recensement des adresses postales Françaises. Du fait que leur application ne sait gérer ces données, elle fait appel à nos services en nous demandant une solution.


### Pré-requis

Java 1.8

VSCode

Talend


### Installation

Importer l'archive Talend dans Talend

Une base de donnée sqlite est disponible ici

Dans le shell de VSCode , lancer la commande ./mvnw spring-boot:run


### Business implémentation

* traitement des fichiers adresses et lieux-dits : intégration dans la base de donnée

* Recherche des villes par le nom de leur adresses :

http://localhost:8080/recherche/GetCities?name=[mot-clé]



### Fichiers archives

Fichier archive csv : \src\main\resources\adresses-01.csv.gz

Fichier archive talend : Extraction.7z


### Base de donnée

image bdd : fantoir.db


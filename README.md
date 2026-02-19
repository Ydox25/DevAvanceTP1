# TP2 - MasterAnnonce : Modernisation JPA/Hibernate

**Auteur :** Yassine BENOUDA 
**Cours :** Développement Avancé

## 1. Contexte et Objectifs
Ce projet s'inscrit dans le cadre du TP2 de Développement Avancé. L'objectif principal était de refactoriser l'application "MasterAnnonce" (initialement développée avec JDBC brut) pour la moderniser en utilisant **JPA (Hibernate)** . Ce passage à un ORM s'est accompagné d'une restructuration complète de l'application en couches métiers cohérentes et de l'ajout de fonctionnalités de sécurité et de tests.

## 2. Architecture Technique
L'application respecte le modèle **MVC (Modèle-Vue-Contrôleur)** et une architecture en couches stricte :

* **Couche Présentation (Web/Contrôleurs)** : Les Servlets (`AnnonceAdd`, `AnnonceList`, etc.) gèrent les requêtes HTTP. Les vues sont gérées par des JSP utilisant la **JSTL** pour un affichage dynamique et propre.
* **Couche Service (`AnnonceService`, `UserService`)** : C'est le cœur de l'application. Elle orchestre la logique métier et **gère exclusivement les transactions** . Aucune transaction n'est ouverte dans les Servlets.
* **Couche Persistance (Repositories)** : `AnnonceRepository`, `UserRepository`, `CategoryRepository`. Ces classes utilisent l'objet `EntityManager` pour interagir avec la base de données PostgreSQL via des requêtes **JPQL** exclusivement (plus aucun code SQL natif) .
* **Modèle (Entités)** : Classes Java (`User`, `Category`, `Annonce`) annotées avec `@Entity` pour le mapping objet-relationnel.

## 3. Problèmes Rencontrés et Solutions Apportées

Conformément aux attentes du TP, voici les principaux défis techniques rencontrés lors du développement et leurs solutions :

### Problème 1 : Conflit de Mapping sur les Relations (`@OneToMany`)
* **Symptôme :** Erreur de compilation *"Attribute value type should not be 'Annonce'"* lors de la création de la relation entre `Category` et `Annonce`.
* **Explication :** L'IDE importait automatiquement l'ancienne classe Java `com.example.tp1.Annonce` (qui était un simple POJO sans annotations) au lieu de la nouvelle entité JPA `com.example.tp1.entity.Annonce`. JPA refusait de créer une relation vers une classe non déclarée comme `@Entity`.
* **Solution :** Nettoyage strict des imports dans toutes les classes. En plaçant toutes les nouvelles entités dans le même package `com.example.tp1.entity`, le mapping s'est fait naturellement sans conflit.

### Problème 2 : Erreur de compilation Maven (`invalid target release: 21`)
* **Symptôme :** Impossible de compiler le projet via la commande `mvn clean install`. Maven renvoyait une erreur fatale concernant la version de Java.
* **Explication :** Le fichier `pom.xml` était configuré pour utiliser les fonctionnalités de Java 21 (`<maven.compiler.target>21</maven.compiler.target>`), mais le terminal de l'environnement de développement (machines de l'université) fonctionnait sous Java 17.
* **Solution :** Rétrogradation de la configuration de compilation dans le `pom.xml` en modifiant les balises `source` et `target` vers la version `17` pour assurer la compatibilité avec l'environnement d'exécution.

### Problème 3 : Contraintes d'unicité lors des Tests d'Intégration
* **Symptôme :** Échec du test `GlobalFlowTest` avec l'erreur PostgreSQL `duplicate key value violates unique constraint` sur l'email utilisateur.
* **Explication :** La base de données PostgreSQL étant persistante, le premier lancement du test a inséré l'utilisateur de test `flow@test.com`. Les exécutions suivantes plantaient car l'entité `User` possède une contrainte `@Column(unique = true)` sur l'email et le nom d'utilisateur.
* **Solution :** Introduction d'un identifiant temporel unique (`System.currentTimeMillis()`) concaténé aux noms d'utilisateurs et emails générés dans la méthode `@BeforeAll` des tests. Cela garantit des données uniques à chaque exécution du cycle de test.

## 4. Fonctionnalités Implémentées
*  **CRUD Complet** : Gestion des entités Annonce, Utilisateurs et Catégories.
*  **Recherche & Pagination** : Moteur de recherche JPQL multicritères avec gestion de l'Offset/Limit pour la pagination .
*  **Authentification & Sécurité** : Connexion par session et sécurisation des routes d'écriture via un `AuthFilter`.
*  **Validation Serveur** : Vérification des données (longueur du titre, format d'email) avant insertion, avec renvoi des messages d'erreur et conservation des anciennes saisies dans la JSP .

## 5. Exécution du Projet

1. **Base de données** : Assurez-vous d'avoir une base de données PostgreSQL accessible (configurée dans `src/main/resources/META-INF/persistence.xml`).
2. **Compilation** : Dans le terminal, à la racine du projet, lancez :
   ```bash
   mvn clean install
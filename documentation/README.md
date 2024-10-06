# FormationContinue

FormationContinue est un logiciel dédié à la validation des déclarations d'activités de formation continue pour les membres d'un ordre professionnel. Chaque ordre impose à ses membres de réaliser un certain nombre d'heures de formation sur une période définie, appelée cycle. Dans ce cas, le cycle s'étend sur deux ans, et les membres doivent accumuler au minimum 40 heures de formation continue durant ce laps de temps.

Le logiciel n'inclue pas d'interface utilisateur, car il conçu pour être intégré et invoqué via une application web. Ainsi, le contrat se concentre uniquement sur le développement du moteur de validation de l'application.

## Références
Nous utilisons des sites comme : 
	https://www.w3schools.com/java/java_strings.asp
	https://www.jmdoudoux.fr/java/dej/chap-enums.htm
	https://openclassrooms.com/fr/courses/8383791-apprenez-a-programmer-en-java

Pour nous réintingrer au langage JAVA.


## Technologies

- **Langage** : Java (version 22.0 ou supérieure)

- **Librairie** : 
  - `commons-beanutils-1.8.0.jar`
  - `commons-collections-3.2.1.jar`
  - `commons-io-2.4.jar`
  - `commons-lang-2.5.jar`
  - `commons-logging-1.1.1.jar`
  - `ezmorph-1.0.6.jar`
  - `json-lib-2.4-jdk15.jar`

- **IDE**: IntelliJ IDEA

## Prérequis

Avant de compiler et d'exécuter le projet, assurez-vous d'avoir les éléments suivants installés :

- [Java JDK](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) (version 22.0 ou supérieure)
- Un IDE (comme IntelliJ IDEA, Eclipse ou NetBeans) ou un terminal capable d'exécuter des commandes Java.

## Installation

1. Clonez le dépôt git :
   ```bash
   git clone https://gitlab.info.uqam.ca/NOM_UTILISATEUR/inf2050-a24-projet-equipe6.git

2. Accédez au dossier du projet : 
   ```bash
   cd inf2050-a24-projet-equipe6

3. Installez les dépendances et les librairie et le tour est joué.

## Exécution

- **Terminal** : 
java -jar FormationContinue.jar declaration.json resultat.json

- **IDE** : juste cliquer sur "RUN"

## Licence

Ce projet est fourni sans licence et est destiné à être utilisé tel quel, sans aucune garantie d'aucune sort. Vous modifier et de le distribuer.

## Historique des versions
- **v1.0.0** - 2024-09-30
- Lancement initial du projet.
- Mise en place du moteur de validation des déclarations d'activités de formation continue.
- Documentation initiale créée, incluant les instructions d'installation et d'utilisation.
- Pas d'interface utilisateur, conçu pour être invoqué via une application web.

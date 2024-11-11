# FormationContinue

FormationContinue est un logiciel dédié à la validation des déclarations d'activités de formation continue pour les membres d'un ordre professionnel. Chaque ordre impose à ses membres de réaliser un certain nombre d'heures de formation sur une période définie, appelée cycle. Dans ce cas, le cycle s'étend sur deux ans, et les membres doivent accumuler au minimum 40 heures de formation continue durant ce laps de temps.

Le logiciel n'inclut pas d'interface utilisateur, car il est conçu pour être intégré et invoqué via une application web. Ainsi, le contrat se concentre uniquement sur le développement du moteur de validation de l'application.

## Technologies

- **Langage** : Java (version 22.0 ou supérieure)

- **Maven** : Gestionnaire de dépendances et de build
  - JSON Processing
    - `json-lib-2.4-jdk15`
    - `commons-beanutils`, `commons-collections`, `commons-io`, `commons-lang`, `commons-logging`, `ezmorph`
  - Testing avec JUnit5
    - `junit-jupiter`, `junit-platform-commons`, `opentest4j`

- **IDE** : IntelliJ IDEA

## Prérequis

Avant de compiler et d'exécuter le projet, assurez-vous d'avoir les éléments suivants installés :

- [Java JDK](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) (version 22.0 ou supérieure)
- Maven
- Un IDE (comme IntelliJ IDEA, Eclipse ou NetBeans) ou un terminal capable d'exécuter des commandes Java.

## Installation

1. Clonez le dépôt git :
   ```bash
   git clone https://gitlab.info.uqam.ca/NOM_UTILISATEUR/inf2050-a24-projet-equipe6.git
2. Accédez au dossier du projet : 
   ```bash
   cd inf2050-a24-projet-equipe6

## Configuration des arguments d'entrée

### Étapes pour configurer les arguments du programme :

1. **Ouvrez le projet dans IntelliJ IDEA.**

2. **Accédez aux configurations d'exécution :**
  - Cliquez sur la flèche verte en haut
  - Dans le menu déroulant, sélectionnez "Edit Configurations".
3. **Configurer la classe principale :**
  - Sélectionnez ou créez une nouvelle configuration sous "Application" (en cliquant sur le bouton "+" si nécessaire).
  - Assurez-vous que le champ `Main class` pointe vers votre classe contenant la méthode `main`.
4. **Ajouter les arguments du programme :**
   - **Important** : Le fichier `outputFile.json` ne sera pas présent dans le dossier `resources` au départ. Il sera **automatiquement généré** lors de l'exécution du programme grâce à l'utilisation du `FileWriter`. Vous devez simplement spécifier le **chemin du fichier de sortie** lors de la configuration des arguments du programme, comme suit :
     - Dans la section "Program arguments", entrez les chemins des fichiers en partant du **répertoire racine du projet**.
     - Par exemple :
       ```bash
       Vendor/Ressources/inputFile.json Vendor/Ressources/outputFile.json
       ```
     - Copiez et collez ces chemins dans le champ "Program arguments", en les séparant par un espace.

5. **Appliquer les modifications :**
  - Cliquez sur "OK" ou "Apply" pour enregistrer les changements.


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

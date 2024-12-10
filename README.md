# FormationContinue

**FormationContinue** est un logiciel dédié à la validation des déclarations d'activités de formation continue pour les membres de divers ordres professionnels. Chaque ordre impose à ses membres de réaliser un certain nombre d'heures de formation sur une période définie, appelée **cycle**. Le logiciel prend en charge les cycles suivants :

- **Architectes** : cycles **2018-2020**, **2020-2022**, **2023-2025** ;
- **Géologues** : cycle **2021-2024** ;
- **Psychologues** : cycle **2020-2025** ;
- **Podiatres** : cycle **2021-2024**.

Le logiciel valide les déclarations selon ces cycles et génère des erreurs lorsque les cycles ou les ordres ne correspondent pas aux exigences. Si un ordre ou un cycle non valide est fourni, le logiciel enregistre une erreur.

En plus de la validation des déclarations, le logiciel est désormais capable de **calculer et conserver des statistiques détaillées** à chaque exécution. Ces statistiques sont sauvegardées dans un fichier au format de votre choix (par exemple, `.json`, `.xml`, `.txt`, ou `.yml`), sans nécessiter d’installation supplémentaire.

---

## Fonctionnalités de validation spécifiques à chaque ordre

Chaque ordre a des règles précises concernant la durée de son cycle et le nombre minimum d’heures requises :

### **Géologues**
- **Durée du cycle** : 3 ans (cycle **2021-2024**).
- **Période valide** : activités effectuées entre le **1er juin 2021** et le **1er juin 2024** inclusivement.
- **Nombre total d'heures minimum** : 55 heures par cycle.
- **Numéro de permis** : Composé de **2 lettres majuscules** suivies de **4 chiffres**.
  - La **première lettre** correspond à la **première lettre du nom du membre**, en majuscule.
    - La **deuxième lettre** correspond à la **première lettre du prénom du membre**, en majuscule.
    - Exemple : DS3822 (D = nom, S = prénom).
- **Heures requises par catégorie** :
    - **Cours** : minimum **22 heures**.
    - **Projet de recherche** : minimum **3 heures**.
    - **Groupe de discussion** : minimum **1 heure**.
- **Autres règles** :
    - Pas de transfert d’heures vers un autre cycle (**les heures du cycle précédent ne sont pas comptabilisées**).
  

### **Psychologues**
- **Durée du cycle** : 5 ans (cycle **2020-2025**).
- **Période valide** : activités effectuées entre le **1er janvier 2020** et le **1er janvier 2025** inclusivement.
- **Nombre total d'heures minimum** : 90 heures par cycle.
- **Numéro de permis** : Composé de **5 chiffres**, d’un **trait d’union** et de **2 chiffres** (exemple : 83723-34).
- **Heures requises par catégorie** :
    - **Cours** : minimum **25 heures**.
    - **Conférence** : maximum **15 heures**. Les heures supplémentaires ne sont pas comptabilisées mais ne génèrent pas d'erreur.
- **Autres règles** :
    - Pas de transfert d’heures vers un autre cycle (**les heures du cycle précédent ne sont pas comptabilisées**).
  

## Architectes

- **Durée du cycle** : 2 ans (cycle 2023-2025).
- **Nombre total d'heures minimum** : 40 heures par cycle.
- **Période valide** : Activités complétées entre **1er avril 2023** et **1er avril 2025** (ISO 8601 : AAAA-MM-JJ).
- **Numéro de permis** : Composé d’une **lettre majuscule (A ou T)** suivie de **4 chiffres** (exemple : T3443).
- **Heures transférées** :
    - Maximum : **7 heures** transférables.
    - Si supérieur à 7 : seul 7 heures sont comptées, message produit.
    - Si négatif : ignoré, message produit.
- **Heures requises par catégories** :
    - Minimum **17 heures** pour **cours, atelier, séminaire, colloque, conférence, lecture dirigée** (heures transférées incluses).
    - Maximum :
        - **23 heures** : présentation et projet de recherche (au-delà ignoré, pas de message).
        - **17 heures** : groupe de discussion et rédaction professionnelle (au-delà ignoré, pas de message).


## Podiatres
- **Durée du cycle** : 3 ans (cycle 2021-2024).
- **Nombre total d'heures minimum** : 60 heures minimum
- **Période valide** : Activités complétées entre **1er juin 2021** et **1er juin 2024**.
- **Numéro de permis** : Composé de **5 chiffres** (exemple : 83453).
- **Heures requises par catégories** : 
    - **Cours** : minimum **22 heures**.
    - **Projet de recherche** : minimum **3 heures**.
    - **Groupe de discussion** : minimum **1 heure**.
- **Transfert d’heures** : Non autorisé.

---

## Fonctionnalités de statistiques

Le logiciel calcule et enregistre les statistiques suivantes à chaque exécution :

1. **Nombre total de déclarations traitées** ;
2. **Nombre total de déclarations complètes** ;
3. **Nombre total de déclarations incomplètes ou invalides** ;
4. **Nombre total de déclarations faites par des hommes** ;
5. **Nombre total de déclarations faites par des femmes** ;
6. **Nombre total de déclarations faites par des gens de sexe inconnu** ;
7. **Nombre total d'activités dans les déclarations valides (complètes ou incomplètes)** ;
8. **Nombre d'activités par catégorie dans les déclarations valides complètes** ;
9. **Nombre total de déclarations valides et complètes par ordre professionnel** ;
10. **Nombre total de déclarations valides (complètes ou incomplètes) par ordre professionnel** ;
11. **Nombre de déclarations soumises avec un numéro de permis invalide**.

Ces statistiques permettent aux administrateurs de mieux comprendre et suivre les comportements de déclaration des membres des différents ordres professionnels. Elles sont mises à jour et cumulées à chaque exécution du programme.

---

## Technologies

- **Langage** : Java (version 22.0 ou supérieure)

- **Maven** : Gestionnaire de dépendances et de build
  - JSON Processing
    - `json-lib-2.4-jdk15`
    - `commons-beanutils`, `commons-collections`, `commons-io`, `commons-lang`, `commons-logging`, `ezmorph`
  - Testing avec JUnit5
    - `junit-jupiter`, `junit-platform-commons`, `opentest4j`

- **IDE** : IntelliJ IDEA

---

## Prérequis

Avant de compiler et d'exécuter le projet, assurez-vous d'avoir les éléments suivants installés :

- **[Java JDK](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)** (version 22.0 ou supérieure)
- **Maven**:
    Avant d'exécuter le programme, Maven doit être installé sur votre machine. Voici les instructions pour chaque système d'exploitation :

    - **MacOS** : Installez Maven à l'aide de Homebrew :
      ```bash
      brew install maven
      ```

    - **Linux** : Utilisez votre gestionnaire de paquets (par exemple, `apt` pour Ubuntu ou `dnf` pour Fedora) :
      ```bash
      sudo apt update
      sudo apt install maven
      ```

    - **Windows** :
        1. Téléchargez Maven depuis le site officiel : [Maven Downloads](https://maven.apache.org/download.cgi).
        2. Décompressez l'archive téléchargée.
        3. Ajoutez le chemin du dossier `bin` de Maven aux variables d'environnement.

        Une fois Maven installé, vérifiez son installation en exécutant la commande suivante dans votre terminal :
        ```bash
        mvn -v
        ```


- Un **IDE** (comme IntelliJ IDEA, Eclipse ou NetBeans) ou un terminal capable d'exécuter des commandes Java.

## Installation

1. Clonez le dépôt git :
   ```bash
   git clone https://gitlab.info.uqam.ca/NOM_UTILISATEUR/inf2050-a24-projet-equipe6.git
2. Accédez au dossier du projet : 
   ```bash
   cd inf2050-a24-projet-equipe6

---

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

---

## Fonctionnement

### **Exécution via le terminal**
Le logiciel n'inclut pas d'interface utilisateur, car il est conçu pour être intégré et invoqué via une application web. Il peut néanmoins être exécuté via une interface en ligne de commande pour les tâches suivantes :

1. **Validation des déclarations** :
   ```bash
   java -jar FormationContinue.jar declaration.json resultat.json
   ```
    - Cette commande traite un fichier de déclaration (`declaration.json`) et génère un fichier de résultat (`resultat.json`).

2. **Affichage des statistiques cumulées** :
   ```bash
   java -jar FormationContinue.jar -S
   ```
    - Cette commande affiche les statistiques actuelles enregistrées dans le fichier de statistiques.

3. **Réinitialisation des statistiques** :
   ```bash
   java -jar FormationContinue.jar -SR
   ```
    - Cette commande réinitialise toutes les statistiques accumulées.


### **Exécution via le terminal l'IDE** : 
    Cliquer sur "RUN"

---

## Licence

Ce projet est fourni sans licence et est destiné à être utilisé tel quel, sans aucune garantie d'aucune sort. Vous modifier et de le distribuer.

## Historique des versions
- **v1.1.2** - 2024-09-30
- Lancement initial du projet.
- Mise en place du moteur de validation des déclarations d'activités de formation continue.
- Documentation **v1.1.0** incluant les instructions détaillées sur la validation des cycles et activités des ordres professionnels, le calcul des statistiques, et des étapes claires pour l’installation et l’exécution du programme sous macOS, Linux, et Windows.
- Pas d'interface utilisateur, conçu pour être invoqué via une application web.

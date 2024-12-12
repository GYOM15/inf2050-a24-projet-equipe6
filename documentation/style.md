# FormationContinue

# Guide de Style de Code

## Introduction
Ce document présente les conventions de style de programmation que nous avons suivies tout au long du développement de notre projet, afin de garantir une cohérence et une lisibilité optimales du code.

## Langage
Nous avons utilisé **Java** comme langage principal pour le développement du programme.

## Langue
- Les noms des classes, des méthodes, des fonctions, et des variables sont écrits en **anglais**.
- Les commentaires explicatifs et la documentation sont rédigés en **français**.

## Convention de Nommage

### Variables, Méthodes et Fonctions
- Nous utilisons le style **camelCase** pour nommer les variables, les méthodes et les fonctions.
  - Exemple : `permitNumber`, `firstChar`, `validatePermitNumber()`, `determineFileType()`.

### Constantes
- Les constantes suivent la convention standard Java : **MAJUSCULES** avec des underscores pour séparer les mots.
  - Exemple : `GROUPE_DE_DISCUSSION`, `COURS`.

### Noms des Classes
- Les classes suivent le style **PascalCase**.
  - Exemple : `FilePathResolver`, `ActivityValidator`.

### Méthodes
- Les méthodes et fonctions commencent par un verbe pour indiquer clairement l’action qu’elles accomplissent.
  - Exemple : `incrementTotalDeclarations`, `resetStatistics`.

## Commentaires

### Documentation
- Nous utilisons **Javadoc** pour documenter les classes, les méthodes et les paramètres, afin de faciliter la compréhension et l’utilisation du code.
  - Exemple :
    ```java
    /**
     * Valide le numéro de permis en fonction des critères de l'ordre.
     * @param permitNumber Le numéro de permis à valider.
     * @param errorHandler Le gestionnaire d'erreurs pour enregistrer les messages d'erreur.
     * @return true si le numéro est valide, false sinon.
     */
    public static boolean validatePermitNumber(String permitNumber, ErrorHandler errorHandler) {
        // Implémentation ici
    }
    ```

### Explications en Ligne
- Nous utilisons des commentaires simples (`//`) ou multi-lignes (`/* */`) pour expliquer des parties spécifiques du code si nécessaire.

  - Exemple :
    ```java
    // Valider les heures transférées uniquement si elles dépassent le seuil minimum.
    int transferredHours = validateTransferredHours(obj, errorHandler);
    ```

## Indentation
- L'indentation est automatiquement gérée par notre IDE (IntelliJ IDEA) pour garantir la cohérence et éviter les erreurs d'alignement.

## Longueur des Lignes
- Nous limitons la longueur des lignes à environ **80-120 caractères**, afin de garantir la lisibilité du code, particulièrement sur des écrans standards.

## Structures de Contrôle
- Nous favorisons des structures de contrôle claires avec des blocs explicites, même pour des instructions uniques.
- Exemple :
  ```java
  if (isValid) {
    updateStatistics();
  } else {
    logError();
  }
  ```

## Bonne Pratique de la Synchronisation
- Les sections critiques partagées entre plusieurs threads utilisent `synchronized` pour éviter les conditions de concurrence, comme dans `StatisticsData`.

## Conclusion
Le respect des conventions de style et de structure est essentiel pour assurer une collaboration efficace et une maintenance facile du code à long terme. Ce guide de style sert de référence pour tout développement futur dans le projet.
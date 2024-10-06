# FormationContinue - Groupe 6

# Guide de Style de Code 

## Introduction
Ce document présente le style que nous avons appliqué, tout au long du développement de notre projet, pour garder une cohérence et une lisibilité claire de notre code.

## Langage
Nous avons utilisé **Java** pour le développement du programme.

## Langue
- Pour les classes, les méthodes, les fonctions et les variables nous utilisons l'**Anglais**
- Au niveau des commentaires nous les avons écrit en **Français**.

## Convention de nommage
- Nous avons opté pour l'utilisation du **camelCase** pour la nomination de nos variables, méthodes et fonctions.
  - Exemple : `permitNumber`, `firstChar`, `validatePermitNumber()`, `determineFileType()`.
- Pour les constantes d'après la norme de JAVA, tout en **MAJUSCULES** avec des underscores.
  - Exemple : `GROUPE_DE_DISCUSSION`, `COURS`.
- Spécifiquement au fonctions et méthodes nous commençons par un verbe pour indiquer l'action. 
- Pour les classes, ont utilisent le **PascalCase**.
  - Exemple : `FileTypeDetermine`, `ActivityValidator`.

## Commentaires
- Les commentaires sont en Javadoc pour documenter les classes et les fonctions.
  - Exemple :
    ```java
    /**
     * @param permitNumber pour le numéro de permis à vérifier
     * @param errorHandler pour stocker les erreurs liées au numéro de permis
     * @return boolean
     */
    public static boolean validatePermitNumber(String permitNumber, ErrorHandler errorHandler) {
        // Implementation ici
    }
    ```
- Nous utilisons aussi des commentaire simple et parfois sur plusieurs lignes pour expliquer certains détails de notre code.
- Exemple :  ``` //, /* */  ```


## Indentation
- Pour l'indentation, elle est automatiquement gérée par l'IDE.

## Longueur des Lignes
- Nous avons essayé de limitez les caractères par ligne pour améliorer la lisibilité et pour ne pas avoir des très longues lignes de code.


## Conclusion
Le respect du style appliquer dans le programme est essentiel pour le travail en équipe et la maintenance du code.

# Workflow de Développement - Gestion des Branches avec Cherry-Pick

Ce document décrit le workflow de développement pour le projet, en expliquant la gestion des branches, l’utilisation de `cherry-pick` pour appliquer des modifications spécifiques, et le processus de fusion dans `main`. Ce workflow est conçu pour maintenir une intégration fluide et contrôlée des fonctionnalités dans la branche principale.

## 1. Structure des Branches

Le workflow utilise plusieurs branches pour structurer le développement :

- **`main`** : La branche principale, stable et destinée à la production. Les nouvelles fonctionnalités ne sont fusionnées dans `main` qu’après avoir été validées et testées.

- **`feature-validation-multi-ordres`** : Branche de développement principale pour les validations multi-ordres. Tous les changements concernant la validation multi-ordres sont initialement développés ici, et cette branche sert de base pour les autres fonctionnalités.

- **Branches de Fonctionnalité** : Ces branches sont créées à partir de `main` pour le développement de fonctionnalités spécifiques. Les modifications pertinentes de `feature-validation-multi-ordres` sont appliquées à ces branches à l’aide de `cherry-pick` pour assurer la cohérence et l’alignement avec les dernières validations.

## 2. Workflow de Développement avec Cherry-Pick et Fusion dans Main

1. **Développement des Modifications Principales dans `feature-validation-multi-ordres`**  
   Tous les changements relatifs aux validations multi-ordres sont d’abord développés et testés dans la branche `feature-validation-multi-ordres`. Cette branche sert de base pour toutes les validations de logique métier et les modifications majeures de validation.

2. **Création de Branches de Fonctionnalité à partir de `main`**  
   Pour chaque nouvelle fonctionnalité ou correction spécifique, une branche de fonctionnalité (`feature-branch`) est créée à partir de `main`.
    - **Commande :**
      ```bash
      git checkout main
      git checkout -b feature-new-feature-name
      ```

3. **Application des Commits de `feature-validation-multi-ordres` avec Cherry-Pick**  
   Les commits spécifiques de `feature-validation-multi-ordres` sont appliqués aux branches de fonctionnalité à l’aide de `git cherry-pick`. Cette approche permet de garder chaque branche de fonctionnalité synchronisée avec les changements de `feature-validation-multi-ordres` sans fusionner l’ensemble de la branche.
    - **Commande :**
      ```bash
      git checkout feature-branch
      git cherry-pick <commit-hash>
      ```

4. **Push des Branches de Fonctionnalité vers le Dépôt Distant**  
   Après avoir appliqué les commits nécessaires avec `cherry-pick` et testé la branche localement, la branche de fonctionnalité est poussée vers le dépôt distant.
    - **Commande :**
      ```bash
      git push origin feature-branch
      ```

5. **Création d’une Pull Request et Fusion dans `main`**  
   Une fois les modifications validées et poussées sur le dépôt distant, une Pull Request est créée pour fusionner la branche de fonctionnalité dans `main`. Après approbation, la branche est fusionnée dans `main` pour rendre la fonctionnalité disponible en production.
    - **Commande pour fusionner localement (si nécessaire) :**
      ```bash
      git checkout main
      git merge feature-branch
      ```

6. **Nettoyage des Branches de Fonctionnalité**  
   Après la fusion dans `main`, les branches de fonctionnalité sont supprimées localement et sur le dépôt distant pour garder l’environnement de développement propre.
    - **Commandes :**
      ```bash
      git branch -d feature-branch
      git push origin --delete feature-branch
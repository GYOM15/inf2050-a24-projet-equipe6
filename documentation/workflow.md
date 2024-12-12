# **Workflow de Développement - Feature Branch Workflow**

Ce document décrit le workflow de développement du projet, en expliquant la gestion des branches, leur rôle, et le processus de fusion dans `main`. Le workflow est basé sur les branches existantes dans le projet, selon la capture fournie.

---

## **1. Structure des Branches**

Le workflow repose sur une hiérarchie claire des branches :

### **Branches Principales**
- **`main`** :  
  La branche principale, stable et destinée à la production. Les fonctionnalités y sont fusionnées uniquement après avoir été entièrement testées et validées. Elle est protégée pour éviter les pushs directs.

### **Branches de Développement**
- **`feature-validation-generals-rules`** :  
  Branche dédiée au développement et à la validation des règles générales. Elle sert de base pour les validations spécifiques.

- **`feature-statistic-integration-to-projects`** :  
  Branche utilisée pour intégrer la gestion des statistiques dans le projet. Les modifications liées aux calculs ou à l’utilisation des statistiques y sont développées.

- **`feature-refactoring`** :  
  Branche utilisée pour le nettoyage du code, la réorganisation des classes et l’amélioration des imports.

- **`feature-CI-CD-setUp`** :  
  Branche dédiée à la configuration de l’intégration et du déploiement continu (CI/CD). Elle contient les scripts et ajustements nécessaires pour automatiser les tests et le déploiement.

- **`feature-sprint3-podiateManager`** :  
  Branche utilisée pour développer et ajuster les fonctionnalités spécifiques au gestionnaire de podiatres.

- **`feature-bug-fix`** :  
  Branche dédiée à la correction des bugs identifiés pendant le développement ou les tests.

- **`feature-unit-tests`** :  
  Branche utilisée pour développer et améliorer la couverture des tests unitaires, notamment avec des outils comme Jacoco.

- **`feature-documentation`** :  
  Branche dédiée à la création et à la mise à jour des fichiers de documentation tels que `style.md`, `equipe.md`, et `workflow.md`.

- **`feature-javadoc`** :  
  Branche dédiée à l’ajout et à la mise à jour de la documentation Javadoc pour le code source.

---

## **2. Workflow de Développement et Fusion dans `main`**

### **Étape 1 : Développement sur les Branches Dédiées**
- Chaque branche est dédiée à une tâche ou une thématique spécifique :
   - **Règles générales de validation** → `feature-validation-generals-rules`
   - **Intégration des statistiques** → `feature-statistic-integration-to-projects`
   - **Refactorisation du code** → `feature-refactoring`
   - **Correction de bugs** → `feature-bug-fix`
   - **Tests unitaires** → `feature-unit-tests`
   - **CI/CD** → `feature-CI-CD-setUp`
   - **Documentation** → `feature-documentation`
   - **Javadoc** → `feature-javadoc`

**Commandes pour travailler sur une branche :**
```bash
git checkout <nom-de-la-branche>
git pull origin <nom-de-la-branche>  # S'assurer que la branche est à jour
```

---

### **Étape 2 : Création de Branches de Fonctionnalité**
- Si une nouvelle fonctionnalité est nécessaire, créer une branche dédiée à partir de `main` ou d’une branche pertinente (par exemple, `feature-validation-generals-rules`).

**Commandes :**
```bash
git checkout main
git pull origin main
git checkout -b feature-nom-fonctionnalite
```

---

### **Étape 3 : Développement et Tests**
- Travailler sur les modifications nécessaires dans la branche.
- Si des changements ou corrections effectués dans d’autres branches (par exemple, `feature-bug-fix`) sont requis dans votre branche, utiliser `git cherry-pick` pour appliquer des commits spécifiques.

**Commandes :**
```bash
git log  # Trouver le hash des commits nécessaires
git cherry-pick <commit-hash>
```

---

### **Étape 4 : Push des Modifications**
- Une fois les modifications apportées et validées, pousser-les vers le dépôt distant.

**Commandes :**
```bash
git add .
git commit -m "Message clair sur les changements apportés"
git push origin <nom-de-la-branche>
```

---

### **Étape 5 : Fusion dans `main` via Merge Request**
- Une fois les modifications terminées et testées, ouvrir une **Merge Request** pour fusionner votre branche dans `main`.
- S’assurer que la Merge Request passe tous les contrôles automatiques (tests unitaires, validation CI/CD).
- Après approbation, fusionner la Merge Request dans `main`.

**Commandes pour fusionner localement (si nécessaire) :**
```bash
git checkout main
git pull origin main  # Récupérer les derniers changements de main
git merge <nom-de-la-branche>
git push origin main
```

---

### **Étape 6 : Nettoyage des Branches**
- Après la fusion dans `main`, supprimer la branche de fonctionnalité localement et sur le dépôt distant.

**Commandes :**
```bash
git branch -d <nom-de-la-branche>  # Supprimer localement
git push origin --delete <nom-de-la-branche>  # Supprimer sur le dépôt distant
```

---

## **3. Rôles des Branches dans le Contexte Actuel**

| **Branche**                                 | **Rôle**                                                                             |
|---------------------------------------------|--------------------------------------------------------------------------------------|
| `main`                                      | Branche principale pour la production.                                               |
| `feature-validation-generals-rules`         | Validation des règles générales. Base pour les branches nécessitant ces validations. |
| `feature-statistic-integration-to-projects` | Intégration et gestion des statistiques.                                             |
| `feature-refactoring`                       | Nettoyage et amélioration du code existant.                                          |
| `feature-CI-CD-setUp`                       | Mise en place et configuration de l’intégration/déploiement continu.                 |
| `feature-sprint3-podiateManager`            | Développement des fonctionnalités pour la gestion des podiatres.                     |
| `feature-bug-fix`                           | Correction des bugs identifiés.                                                      |
| `feature-unit-tests`                        | Développement et amélioration des tests unitaires.                                   |
| `feature-documentation`                     | Création et mise à jour des fichiers de documentation (style.md, equipe.md, etc.).   |
| `feature-javadoc`                           | Ajout et mise à jour de la documentation Javadoc pour le code source.                |

---

## **Résumé des Étapes**

1. Développer sur des branches spécifiques au besoin de votre tâche.
2. Synchroniser les modifications importantes des autres branches avec `git cherry-pick` si nécessaire.
3. Pousser vos modifications et ouvrir une Merge Request pour fusionner dans `main`.
4. Nettoyer les branches fusionnées pour maintenir un dépôt organisé.

---

### **Bonnes Pratiques**
- **Utiliser des noms explicites pour les branches** : Ex. `feature-login-validation`, `bugfix-dashboard-crash`.
- **Commiter régulièrement avec des messages descriptifs.**
- **Rebaser vos branches de fonctionnalité si nécessaire** pour garder l’historique propre et éviter les conflits.

---

Ce workflow garantit une gestion claire et efficace des branches, tout en minimisant les risques d’introduire des erreurs dans la branche principale.

# Classification Non Supervisée avec K-Means

## 📁 Structure du Projet

```
src/
├── CorpusPreparation.java      # Prépare le corpus en renommant et organisant les documents
├── CorpusReader.java           # Charge le corpus et renvoie une liste des chemins des documents
├── Preprocessing.java          # Prépresse le corpus (supprime les stopwords, applique le stemming)
├── TFIDFCalculator.java        # Calcule le TF-IDF pour chaque document
├── KMeans.java                 # Applique l'algorithme de KMeans sur les documents traités
└── Main.java                   # Classe principale qui relie toutes les étapes
```

## 🚀 Vue d'ensemble du projet

Ce projet montre la classification non supervisée en utilisant l'algorithme **KMeans** sur un corpus de documents textuels. Le processus est divisé en plusieurs étapes, notamment la préparation du corpus, le prétraitement, le calcul du TF-IDF et le regroupement en clusters. Voici une vue d'ensemble de chaque classe et de ses responsabilités.

## 📝 Description des Classes

### 1. **CorpusPreparation.java**
Cette classe gère l'organisation du corpus. Elle lit le répertoire source contenant des sous-répertoires (représentant les classes), puis renomme les documents pour refléter leur nom de classe.

**Fonctions :**
- `prepareCorpus(int n)`: Prépare le corpus en sélectionnant `n` classes aléatoires et en renommant les fichiers avec leur nom de classe.

### 2. **CorpusReader.java**
Cette classe est responsable de la lecture du corpus et du chargement de tous les chemins de documents dans une liste.

**Fonctions :**
- `loadCorpus(String directoryPath)`: Charge tous les chemins des fichiers dans le répertoire spécifié.

### 3. **Preprocessing.java**
Le prétraitement est essentiel pour améliorer la qualité des données. Cette classe supprime les stopwords et applique le stemming aux documents.

**Fonctions :**
- `preprocessFile(String filePath)`: Traite un seul document en supprimant les stopwords et en appliquant le stemming.

### 4. **TFIDFCalculator.java**
Cette classe calcule le **Term Frequency-Inverse Document Frequency (TF-IDF)** pour chaque document. Le TF-IDF est utilisé pour transformer les textes en représentations numériques pour le clustering.

**Fonctions :**
- `calculateTFIDF(List<String> corpus, Map<String, String> preprocessedFiles)`: Calcule le TF-IDF pour chaque document en fonction du corpus prétraité.

### 5. **KMeans.java**
Cette classe implémente l'algorithme de **KMeans**. Elle regroupe les documents en `K` clusters en fonction de la similarité de leurs vecteurs TF-IDF.

**Fonctions :**
- `applyKMeans(List<String> corpus, Map<String, double[]> tfidfMap)`: Applique l'algorithme KMeans pour regrouper les documents en fonction de leurs vecteurs TF-IDF.

### 6. **Main.java**
Il s'agit de la classe principale qui relie toutes les étapes. Elle charge le corpus, prétraite les fichiers, calcule le TF-IDF et applique KMeans pour classifier les documents en clusters.

**Fonctions :**
- `main(String[] args)`: La fonction principale qui orchestre le processus entier.

## 🛠️ Étapes
1. **Préparer le corpus** : Organiser et renommer les documents avec les noms de classe.
2. **Prétraiter les données** : Nettoyer les données en supprimant les stopwords et en appliquant le stemming.
3. **Calculer le TF-IDF** : Convertir les documents texte en format numérique.
4. **Appliquer KMeans** : Effectuer le regroupement avec l'algorithme KMeans.
5. **Afficher les résultats** : Voir les documents regroupés dans des clusters.

## 💡 Dépendances
- [Algorithme de Stemming Snowball](https://snowballstem.org/) : Utilisé pour appliquer le stemming aux documents.
- Version Java 8 ou supérieure.

## 📊 Résultats
Après l'exécution de l'algorithme KMeans, les documents sont classés en `K` clusters. Chaque cluster contient des documents similaires, bien qu'aucune étiquette de classe ne soit fournie pour la classification.

Amusez-vous bien à explorer la classification non supervisée avec KMeans ! ✨

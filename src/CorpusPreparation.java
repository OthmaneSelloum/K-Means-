package src;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class CorpusPreparation {

    private final String sourceDirectory;
    private final String targetDirectory;

    // Constructeur pour initialiser les répertoires
    public CorpusPreparation(String sourceDirectory, String targetDirectory) {
        this.sourceDirectory = sourceDirectory;
        this.targetDirectory = targetDirectory;
    }

    // Fonction principale pour préparer le corpus
    public void prepareCorpus(int n) throws IOException {
        // Récupérer tous les dossiers de classes
        File[] classDirectories = new File(sourceDirectory).listFiles(File::isDirectory);
        if (classDirectories == null || classDirectories.length < n) {
            throw new IllegalArgumentException("Le corpus ne contient pas assez de dossiers.");
        }

        // Sélectionner n dossiers aléatoires parmi les classes disponibles
        List<File> selectedClasses = selectRandomClasses(classDirectories, n);

        // Créer le dossier de destination s'il n'existe pas
        Path targetPath = Paths.get(targetDirectory);
        if (!Files.exists(targetPath)) {
            Files.createDirectories(targetPath);
        }

        // Pour chaque classe sélectionnée, renommer les fichiers et les déplacer dans le dossier 'Corpus'
        for (File classDir : selectedClasses) {
            String className = classDir.getName();  // Nom du dossier, qui est la classe
            File[] files = classDir.listFiles(); // Sélectionner tous les fichiers, quelle que soit leur extension
            if (files != null) {
                for (File file : files) {
                    String newFileName = file.getName() + " - " + className; // Nom du fichier + nom de la classe
                    Path newFilePath = Paths.get(targetPath.toString(), newFileName);
                    Files.copy(file.toPath(), newFilePath, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Fichier renommé : " + file.getName() + " -> " + newFileName);
                }
            } else {
                System.out.println("Aucun fichier trouvé dans le dossier : " + classDir.getName());
            }
        }
        System.out.println("Corpus préparé avec succès dans : " + targetDirectory);
    }

    // Fonction pour sélectionner n classes aléatoirement
    private List<File> selectRandomClasses(File[] classDirectories, int n) {
        List<File> classList = new ArrayList<>(Arrays.asList(classDirectories));
        Collections.shuffle(classList); // Mélanger les classes
        return classList.subList(0, n); // Sélectionner n classes
    }

    public static void main(String[] args) {
        try {
            String sourceDir = "mini_newsgroups"; // Assurez-vous que ce chemin est correct
            String targetDir = "Corpus";

            CorpusPreparation preparation = new CorpusPreparation(sourceDir, targetDir);
            preparation.prepareCorpus(6); // Ajustez le nombre de dossiers à sélectionner

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

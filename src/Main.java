package src;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        String directoryPath = "Corpus";
        String stopwordsPath = "StopwordsEn.txt";

        // Chargement et prétraitement
        List<String> corpus = CorpusReader.loadCorpus(directoryPath);
        System.out.println("Nombre total de fichiers dans le corpus : " + corpus.size()); // Afficher le nombre total de fichiers

        Preprocessing preprocessing = new Preprocessing(stopwordsPath);
        Map<String, String> preprocessedFiles = new HashMap<>();

        for (String file : corpus) {
            preprocessedFiles.put(file, preprocessing.preprocessFile(file));
        }

        // Calcul du TF-IDF
        TFIDFCalculator tfidfCalculator = new TFIDFCalculator();
        Map<String, double[]> tfidfMap = tfidfCalculator.calculateTFIDF(corpus, preprocessedFiles);

        // Appliquer K-means
        List<List<String>> clusters = KMeans.applyKMeans(corpus, tfidfMap);

        // Afficher les résultats
        for (int i = 0; i < clusters.size(); i++) {
            System.out.println("Cluster " + i + " (" + clusters.get(i).size() + " fichiers) :"); // Afficher le nombre de fichiers dans le cluster
            for (String doc : clusters.get(i)) {
                // Extraire le nom de fichier et la classe
                String[] parts = new File(doc).getName().split("-");
                String fileName = parts[0];
                String fileClass = parts.length > 1 ? parts[1].replace(".txt", "") : "Unknown";

                System.out.println(" - " + fileName + "-" + fileClass);
            }
        }
    }
}

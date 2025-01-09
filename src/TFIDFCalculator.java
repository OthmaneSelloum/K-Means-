package src;

import java.util.*;

public class TFIDFCalculator {
    public Map<String, double[]> calculateTFIDF(List<String> corpus, Map<String, String> preprocessedFiles) {
        Map<String, Map<String, Double>> termFrequencies = new HashMap<>();
        Map<String, Integer> documentFrequency = new HashMap<>();
        int totalDocs = corpus.size();

        // Calcul du TF
        for (String file : corpus) {
            String content = preprocessedFiles.get(file);
            Map<String, Double> tfMap = new HashMap<>();
            String[] words = content.split("\\s+");

            for (String word : words) {
                tfMap.put(word, tfMap.getOrDefault(word, 0.0) + 1.0);
            }

            // Normalisation TF
            double maxFreq = Collections.max(tfMap.values());
            for (String word : tfMap.keySet()) {
                tfMap.put(word, tfMap.get(word) / maxFreq);
                documentFrequency.put(word, documentFrequency.getOrDefault(word, 0) + 1);
            }

            termFrequencies.put(file, tfMap);
        }

        // Calcul du TF-IDF
        Map<String, double[]> tfidfMap = new HashMap<>();
        for (String file : corpus) {
            Map<String, Double> tfMap = termFrequencies.get(file);
            double[] tfidfVector = new double[documentFrequency.size()];

            int index = 0;
            for (String term : documentFrequency.keySet()) {
                double tf = tfMap.getOrDefault(term, 0.0);
                double idf = Math.log((double) totalDocs / (1 + documentFrequency.get(term)));
                tfidfVector[index++] = tf * idf;
            }

            tfidfMap.put(file, tfidfVector);
        }

        return tfidfMap;
    }


}

package src;

import java.util.*;

public class KMeans {
    private static final int K = 6;

    public static List<List<String>> applyKMeans(List<String> corpus, Map<String, double[]> tfidfMap) {
        List<String> centroids = initializeCentroids(corpus, tfidfMap);
        List<List<String>> clusters = new ArrayList<>();

        for (int i = 0; i < K; i++) {
            clusters.add(new ArrayList<>());
        }

        boolean converged = false;
        int iterations = 0; // Compteur d'itérations

        while (!converged) {
            iterations++; // Incrémenter le compteur d'itérations

            // Réinitialiser les clusters
            for (List<String> cluster : clusters) {
                cluster.clear();
            }

            // Assignation des documents
            for (String doc : corpus) {
                int closestCluster = findClosestCluster(tfidfMap.get(doc), centroids, tfidfMap);
                clusters.get(closestCluster).add(doc);
            }

            // Mise à jour des centroids
            converged = true;
            for (int i = 0; i < K; i++) {
                if (!clusters.get(i).isEmpty()) {
                    String newCentroid = updateCentroid(clusters.get(i), tfidfMap);
                    if (!newCentroid.equals(centroids.get(i))) {
                        centroids.set(i, newCentroid);
                        converged = false;
                    }
                }
            }
        }

        System.out.println("Nombre d'itérations : " + iterations); // Affichage des itérations
        return clusters;
    }

    private static List<String> initializeCentroids(List<String> corpus, Map<String, double[]> tfidfMap) {
        Random random = new Random();
        List<String> centroids = new ArrayList<>();

        // Choisir le premier centroid aléatoirement
        centroids.add(corpus.get(random.nextInt(corpus.size())));

        // Ajouter les autres centroids
        for (int i = 1; i < K; i++) {
            double[] distances = new double[corpus.size()];
            double totalDistance = 0.0;

            for (int j = 0; j < corpus.size(); j++) {
                double minDistance = Double.MAX_VALUE;
                double[] vector = tfidfMap.get(corpus.get(j));

                for (String centroid : centroids) {
                    double[] centroidVector = tfidfMap.get(centroid);
                    double distance = euclideanDistance(vector, centroidVector);
                    minDistance = Math.min(minDistance, distance);
                }

                distances[j] = minDistance;
                totalDistance += minDistance;
            }

            double threshold = random.nextDouble() * totalDistance;
            double cumulativeDistance = 0.0;

            for (int j = 0; j < corpus.size(); j++) {
                cumulativeDistance += distances[j];
                if (cumulativeDistance >= threshold) {
                    centroids.add(corpus.get(j));
                    break;
                }
            }
        }

        return centroids;
    }


    private static int findClosestCluster(double[] vector, List<String> centroids, Map<String, double[]> tfidfMap) {
        double maxSimilarity = -1; // Valeur initiale minimale pour la Cosine Similarity
        int closestCluster = -1;

        for (int i = 0; i < K; i++) {
            double[] centroidVector = tfidfMap.get(centroids.get(i));
            double similarity = cosineSimilarity(vector, centroidVector);
            if (similarity > maxSimilarity) {
                maxSimilarity = similarity;
                closestCluster = i;
            }
        }

        return closestCluster;
    }

    private static String updateCentroid(List<String> clusterDocs, Map<String, double[]> tfidfMap) {
        // Calculer le centroïde comme la moyenne des vecteurs
        double[] centroidVector = new double[tfidfMap.get(clusterDocs.get(0)).length];
        for (String doc : clusterDocs) {
            double[] vector = tfidfMap.get(doc);
            for (int i = 0; i < vector.length; i++) {
                centroidVector[i] += vector[i];
            }
        }

        // Normaliser le vecteur centroïde
        for (int i = 0; i < centroidVector.length; i++) {
            centroidVector[i] /= clusterDocs.size();
        }
        centroidVector = normalizeVector(centroidVector);

        // Trouver le document le plus proche du centroïde en utilisant la Cosine Similarity
        String closestDoc = clusterDocs.get(0);
        double maxSimilarity = -1.0; // Initialisation de la Similarité Cosine minimale
        for (String doc : clusterDocs) {
            double similarity = cosineSimilarity(centroidVector, normalizeVector(tfidfMap.get(doc)));
            if (similarity > maxSimilarity) {
                maxSimilarity = similarity;
                closestDoc = doc;
            }
        }

        return closestDoc;
    }

    private static double[] normalizeVector(double[] vector) {
        double norm = 0.0;
        for (double value : vector) {
            norm += Math.pow(value, 2);
        }
        norm = Math.sqrt(norm);

        for (int i = 0; i < vector.length; i++) {
            vector[i] /= norm;
        }
        return vector;
    }


    private static double euclideanDistance(double[] vector1, double[] vector2) {
        double sum = 0.0;
        for (int i = 0; i < vector1.length; i++) {
            sum += Math.pow(vector1[i] - vector2[i], 2);
        }
        return Math.sqrt(sum);
    }

    private static double cosineSimilarity(double[] vector1, double[] vector2) {
        double dotProduct = 0.0;
        double norm1 = 0.0;
        double norm2 = 0.0;

        for (int i = 0; i < vector1.length; i++) {
            dotProduct += vector1[i] * vector2[i];
            norm1 += Math.pow(vector1[i], 2);
            norm2 += Math.pow(vector2[i], 2);
        }

        return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }

}

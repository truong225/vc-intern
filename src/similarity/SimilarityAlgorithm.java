package similarity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class SimilarityAlgorithm {
    public double calculateCosine(int[] vectorA, int[] vectorB) {
        int dotProduct = 0;
        double lengthA, lengthB;
        lengthA = lengthB = 0.0f;

        for (int ite = 0; ite < vectorA.length; ite++) {
            dotProduct += vectorA[ite] * vectorB[ite];
            lengthA += Math.pow(vectorA[ite], 2);
            lengthB += Math.pow(vectorB[ite], 2);
        }

        lengthA = Math.sqrt(lengthA);
        lengthB = Math.sqrt(lengthB);
        return dotProduct / (lengthA * lengthB);
    }

    //Manhattan distance, L1 norm, Taxicab, City-block distance
    public double calculateManhattanDistance(int[] vectorA, int[] vectorB) {
        double manhattanDistance = 0f;
        for (int ite = 0; ite < vectorA.length; ite++)
            manhattanDistance += Math.abs(vectorA[ite] - vectorB[ite]);
        return manhattanDistance;
    }

    //Euclidean, L2 norm, Ruler distance
    public double calculateEuclidean(int[] vectorA, int[] vectorB) {
        double length = 0.0f;
        for (int ite = 0; ite < vectorA.length; ite++)
            length += Math.pow(vectorA[ite] - vectorB[ite], 2);
        return Math.sqrt(length);
    }

    public double calculateMinkowski(int[] vectorA, int[] vectorB, int order) {
        double minkowskiDistance = 0f;
        for (int ite = 0; ite < vectorA.length; ite++)
            minkowskiDistance += Math.pow(Math.abs(vectorA[ite] -
                    vectorB[ite]), order);
        return Math.pow(minkowskiDistance, (double) 1 / order);
    }

    public double calculate

    public static void main(String[] args) {
        SimilarityAlgorithm similarityAlgorithm = new SimilarityAlgorithm();
        int[] A = {5, 1, 10};
        int[] B = {9, 10, 10};
        System.out.println("Cosine: " + similarityAlgorithm.calculateCosine(A,
                B));
        System.out.println("Euclidean: " + similarityAlgorithm
                .calculateEuclidean(A, B));
        System.out.println("Manhattan: " + similarityAlgorithm
                .calculateManhattanDistance(A, B));
        System.out.println("Minkowski: " + similarityAlgorithm
                .calculateMinkowski(A, B, 2));
    }
}

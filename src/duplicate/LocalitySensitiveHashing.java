package duplicate;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class LocalitySensitiveHashing {

    //Implementation of Minhash
    public static double THRESHOLD=0.5f;
    public static void main(String[] args) throws FileNotFoundException {
        int maxDoc = 1000;
        int numHash = 10;
        String input = "clean_data.txt";
        ArrayList<String> documents = new ArrayList<>();
        ArrayList<String> docID = new ArrayList<>();
        ArrayList<ArrayList<String>> shingleList = new ArrayList<>();

        FileInputStream fis = new FileInputStream(input);
        Scanner scan = new Scanner(fis);
        for (int i = 0; i < maxDoc; i++) {
            if (scan.hasNextLine()) {
                String line = scan.nextLine();
                if (line.equals("") || line.split("\t").length==1)
                    continue;
                else
                    documents.add(line);
            }
        }
        scan.close();

        for (String line : documents) {
            ArrayList<String> shingle = new ArrayList<>();
            docID.add(line.split("\t")[0]);

            String[] listWords = line.split("\t")[1].split(" ");
            for (int i = 0; i < listWords.length - 2; i++) {
                shingle.add((long) (listWords[i] + " " + listWords[i + 1] + " " + listWords[i + 2]).hashCode() + "");
            }
            shingleList.add(shingle);
            // dict.put(line.split("\t")[0], shingle);
        }

        long[] biasA = new long[numHash];
        long[] biasB = new long[numHash];
        Random rand = new Random();
        for (int i = 0; i < numHash; i++) {
            biasA[i] = rand.nextInt((int) (Math.pow(2, 32) - 1));
            biasB[i] = rand.nextInt((int) (Math.pow(2, 32) - 1));
        }

        ArrayList<long[]> signatures = new ArrayList<>();
        long prime = 4294967311L;
        for (ArrayList<String> listShingle : shingleList) {
            long[] signature = new long[numHash];
            for (int i = 0; i < numHash; i++) {
                long minHash = prime + 1;
                for (String shingleCode : listShingle) {
                    long hashCode = (biasA[i] * Long.parseLong(shingleCode) + biasB[i]) % prime;
                    if (hashCode < minHash)
                        minHash = hashCode;
                }
                // System.out.print(minHash + " ");
                signature[i] = minHash;
            }
            signatures.add(signature);
        }

        for (int i = 0; i < signatures.size() - 1; i++) {
            for (int j = i + 1; j < signatures.size(); j++) {
                int count = 0;
                for (int t = 0; t < numHash; t++) {
                    if (signatures.get(i)[t] == signatures.get(j)[t])
                        count++;
                }
                if((double) (count / numHash)>=THRESHOLD)
                    System.out.println(docID.get(i) + "-" + docID.get(j) + ": " + (double) (count / numHash));
            }
        }
    }
}
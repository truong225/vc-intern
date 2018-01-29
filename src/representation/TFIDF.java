package representation;

import java.util.ArrayList;

public class TFIDF {
    public double tf(String word, String document) {
        int count = 0;
        for (String t : document.split(" ")) {
            if (word.equalsIgnoreCase(t))
                count++;
        }
        return (double) count / document.split(" ").length;
    }

    public double idf(String word, ArrayList<String> listDocument) {
        int count = 0;
        for (String document : listDocument) {
            for (String term : document.split(" ")) {
                if (term.equalsIgnoreCase(word)) {
                    count++;
                    break;
                }
            }
        }
        return Math.log(listDocument.size() / count);
    }

    public double tfIdf(String word, String document,
                        ArrayList<String> listDocument) {
        return tf(word, document) * idf(word, listDocument);
    }

    public static void main(String[] args) {
        TFIDF tfidf = new TFIDF();

        ArrayList<String> list = new ArrayList<>();
        list.add("The game of life is a game of everlasting learning");
        list.add("The unexamined life is not worth living");
        list.add("Never stop learning");
        System.out.println("TF-IDF: " + tfidf.tfIdf("game", list.get(0), list));
    }
}

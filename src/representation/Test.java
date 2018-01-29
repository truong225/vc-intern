package representation;

import javax.xml.soap.Text;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws IOException {
        String inputFilename = "clean_data.txt";
        String outputFilename = "out_ngram.txt";

        FileInputStream inputStream = null;
        Scanner sc = null;
        Scanner scan = null;
        FileOutputStream outputStream = null;

        System.out.println("1. n-gram" + "\n2. Bag of word" + "\n3. tf-idf");

        try {
            scan = new Scanner(System.in);
            int choose = Integer.parseInt(scan.nextLine());

            inputStream = new FileInputStream(inputFilename);
            sc = new Scanner(inputStream, "UTF-8");
            outputStream = new FileOutputStream(outputFilename);

            switch (choose) {
                case 1:
                    int it = 0;
                    for (int i = 0; i < 100; i++) {
                        String data = sc.nextLine();
                        System.out.print("Processing number " + data.split("\t")[0] + " ...");
                        ArrayList<String> listGram = TextRepresentation.nGram(data, 20);
                        for (String gram : listGram) {
                            outputStream.write((gram + "\n").getBytes());
                            it++;
                        }
                        outputStream.write("\n".getBytes());
                        System.out.println("\tDone");
                    }
                    System.out.println(it);
                    break;
                case 2:
                    FileOutputStream fos = new FileOutputStream("bag_of_word.txt");
                    //Input data
                    ArrayList<String> data = new ArrayList<>();
                    for (int i = 0; i < 1000; i++) {
                        String news = sc.nextLine();
                        if (!news.endsWith("\t"))
                            data.add(news);
                    }

                    //Build the dictionary
                    TextRepresentation.buildDictionary(data);

                    //Build bag of word
                    for (String paragraph : data) {
                        System.out.print("Processing " + paragraph.split("\t")[0] + " ...");
                        ArrayList<String> vectors =
                                TextRepresentation.bagOfWord(paragraph, "dictionary.txt");
                        fos.write((paragraph.split("\t")[0] + "\t").getBytes());
                        for (String vector : vectors) {
                            fos.write((vector + " ").getBytes());
                        }
                        fos.write("\n".getBytes());
                        System.out.println(" Done");
                    }
                    fos.close();
                    break;
                case 3:
                    ArrayList<String> listDocument = new ArrayList<>();
                    for (int i = 0; i < 40; i++) {
                        String news = sc.nextLine();
                        if (!news.endsWith("\t"))
                            listDocument.add(TextRepresentation.preProcessing(news));
                    }

                    FileOutputStream output = new FileOutputStream("tf-idf" +
                            ".txt");
                    TFIDF tfidf = new TFIDF();

                    int i = 0;
                    for (String document : listDocument) {
                        System.out.println("Processing " + (i++) + " ... ");
                        output.write((document.split(" ").length + "")
                                .getBytes());

                        document = TextRepresentation.preProcessing
                                (document);
                        for (String word : document.split(" ")) {
                            if (word.equals(""))
                                continue;
//                            output.write((" " + word + ":" + tfidf.tfIdf(word,
//                                    document,
//                                    listDocument)).getBytes());
                            output.write((" " + word + ":" + tfidf.tfIdf
                                    (word, document, listDocument)).getBytes());
                        }
                        output.write("\n".getBytes());
                    }
                    output.close();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            outputStream.close();
            scan.close();
            sc.close();
        }
    }
}
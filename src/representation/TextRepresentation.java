package representation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 1, Tìm hiểu các cách biểu diễn 1 văn bản: n-gram, bow (bag of word), tf-idf.
 * 2, Tìm hiểu các độ đo độ tương tự.
 * 3, Tìm hiểu check trùng văn bản, LSH.
 * 4, Phân cụm: cài đặt K-Mean.
 */
public class TextRepresentation {
    public static String preProcessing(String data) {
        return data.replaceAll("[^\\\\da-zA-Z\\s\\u0080-\\u9fff]", "").replaceAll("[“”\"\\–-]", "").replaceAll("\t", "")
                .toLowerCase();
    }

    /**
     * n-gram()
     */
    public static ArrayList<String> nGram(String paragraph, int n) {
        String[] data = preProcessing(paragraph).split("\\s+");
        ArrayList<String> listGram = new ArrayList<>();
        for (int i = 0; i < data.length - n + 1; i++) {
            String str = "";
            for (int start = i; start < i + n; start++) {
                if (start == i + n - 1)
                    str += data[start];
                else
                    str += data[start] + " ";
            }
            if (str.startsWith(" ") || str.equals(""))
                continue;
            listGram.add(str);
        }
        return listGram;
    }

    /**
     * bag of word
     * @param paragraph:   Đoạn tin tức xử lý
     * @param dictFilename
     * @return
     * @throws FileNotFoundException
     */
    public static ArrayList<String> bagOfWord(String paragraph, String dictFilename) throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(dictFilename);
        Scanner scan = new Scanner(fis);
        ArrayList<String> vectors = new ArrayList<>();
        String[] wordList = paragraph.split(" ");
        while (scan.hasNextLine()) {
            String[] dict = scan.nextLine().split(":");
            int count = paragraph.split("\\b" + dict[0] + "\\b").length - 1;
            vectors.add(dict[1] + ":" + count);
        }
        return vectors;
    }

    public static void buildDictionary(ArrayList<String> data) throws IOException {
        FileOutputStream fos = new FileOutputStream("dictionary.txt");
        ArrayList<String> dictionary = new ArrayList<>();

        for (String paragraph : data) {
            //Check if news is null
            if (paragraph.endsWith("\t"))
                continue;
            paragraph = preProcessing(paragraph.split("\t")[1]);

            String[] wordList = paragraph.trim().split(" ");
            for (String word : wordList) {
                if (word.equals("") || dictionary.contains(word))
                    continue;
                dictionary.add(word);
            }
        }

        //Sort dictionary
        dictionary.sort(new Comparator<String>() {
            @Override
            public int compare(String str1, String str2) {
                return str1.compareTo(str2);
            }
        });

        int index = 0;
        for (String word : dictionary) {
            fos.write((word + ":" + index + "\n").getBytes());
            index++;
        }
        fos.close();
    }
}
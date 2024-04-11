import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.Arrays;
import java.util.ArrayList;

public class WordFrequencyMatrix {

    public static void main(String[] args) {
        String directoryPath = "D://chrome download folder//musictextfilejava"; //Directory to files
        File directory = new File(directoryPath);
        String[] fileNames = directory.list(); //Display all files in the directory

        Map<String, int[]> wordFrequencyMap = new HashMap<>();

        for (String fileName : fileNames) {
            File file = new File(directoryPath + "\\" + fileName);
            try {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNext()) {
                    String word = scanner.next().toLowerCase();
                    if (!word.isEmpty() && word.matches("[a-z]+")) { // Check if word is not empty and contains only alphabets
                        int[] frequencies = wordFrequencyMap.getOrDefault(word, new int[fileNames.length]);
                        frequencies[getIndex(fileNames, fileName)]++;
                        wordFrequencyMap.put(word, frequencies);
                    }
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                System.out.println("File not found: " + fileName);
            }
        }

//        for (Map.Entry<String, int[]> entry : wordFrequencyMap.entrySet()) {
//            System.out.print(entry.getKey() + ": ");
//            int[] frequencies = entry.getValue();
//            for (int frequency : frequencies) {
//                System.out.print(frequency + ", ");
//            }
//            System.out.println();
//        }


        // Sort map alphabetically
        List<Map.Entry<String, int[]>> sortedEntries = new ArrayList<>(wordFrequencyMap.entrySet());
        Collections.sort(sortedEntries, Map.Entry.comparingByKey());

        // Determine maximum word length for alignment
        int maxWordLength = sortedEntries.stream().mapToInt(entry -> entry.getKey().length()).max().orElse(0);

        // Print word frequency matrix
        System.out.print("Words\t|\t");
        for (String fileName : fileNames) {
            System.out.print(String.format("%-" + (maxWordLength + 2) + "s", fileName) + "\t|\t");
        }
        System.out.println();
        for (Map.Entry<String, int[]> entry : sortedEntries) {
            System.out.print(String.format("%-" + (maxWordLength + 2) + "s", entry.getKey()) + "\t|\t");
            int[] frequencies = entry.getValue();
            for (int frequency : frequencies) {
                System.out.print(frequency + "\t|\t");
            }
            System.out.println();
        }
    }

    // Utility method to get index of a file name in the fileNames array
    private static int getIndex(String[] fileNames, String fileName) {
        for (int i = 0; i < fileNames.length; i++) {
            if (fileNames[i].equals(fileName)) {
                return i;
            }
        }
        return -1;
    }
}

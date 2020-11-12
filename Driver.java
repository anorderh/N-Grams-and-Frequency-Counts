import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.util.Scanner;
import java.util.List;

public class Driver {

    public static void main(String[] args) {
        try {
            if (args.length != 1) {
                throw new IllegalArgumentException();
            }
            List<String> tokens = extractContents(args[0]);

            FrequencyCount freqCount = new FrequencyCount(tokens);
            FrequencyCount freqCountDegree4 = new FrequencyCount(tokens, 4);

            System.out.println("\nX----------------------FREQUENCY COUNT, DEGREE OF 1----------------------X");
            printHeadAndTail(freqCount);

            System.out.println("\nX----------------------FREQUENCY COUNT, DEGREE OF 4----------------------X");
            printHeadAndTail(freqCountDegree4);
            generatePoem(freqCountDegree4);

        } catch (FileNotFoundException e) {
            System.out.println("File not found.\n");
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.out.println("usage: Driver filename\n");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("File can't be opened.\n");
            e.printStackTrace();
        }

    }

    private static List<String> extractContents(String filename) throws IOException {
        ArrayList<String> contents = new ArrayList<>();
        Scanner input = new Scanner(new FileInputStream(filename));

        while (input.hasNext()) {
            contents.add(input.next().toLowerCase());
        }

        input.close();
        return contents;
    }

    private static void printHeadAndTail(FrequencyCount freqCount) {
        List<String> mostFrequent = freqCount.head();
        List<String> leastFrequent = freqCount.tail();
        int tokenQuantity = 20;

        if (mostFrequent.size() < 20) {
            tokenQuantity = mostFrequent.size();
        }
        String furtherInfo = "* (Most frequent moving from top to bottom)\n* (Percentages rounded to 3 decimal places)";

        System.out.println("\nFrequencyCount's " + tokenQuantity + " most frequent tokens!");
        System.out.println(furtherInfo);

        for (int i = 0; i < mostFrequent.size(); i++) {
            String token =  mostFrequent.get(i);
            System.out.println("\t" + (i+1) + ": \"" + token + "\" | Frequency: " + freqCount.count(token) +
                    " | Percentage: " + String.format("%.3f", freqCount.percent(token)));
        }

        System.out.println("\nFrequencyCount's " + tokenQuantity + " least frequent tokens!");
        System.out.println(furtherInfo);

        for (int i = 0; i < leastFrequent.size(); i++) {
            String token =  leastFrequent.get(i);
            System.out.println("\t" + (i+1) + ": \"" + token + "\" | Frequency: " + freqCount.count(token) +
                    " | Percentage: " + String.format("%.3f", freqCount.percent(token)));
        }
    }

    private static void generatePoem(FrequencyCount freqCount) {

        System.out.println("\nGenerating poem based on 4 random tokens...");
        System.out.println("* (Attributed to Frequency Count of 4th degree)\n");
        System.out.println("\t\"" + freqCount.randomToken() + "\n\t" + freqCount.randomToken() +
                " " + freqCount.randomToken() + "\n\t" + freqCount.randomToken() + "\"");
    }

}

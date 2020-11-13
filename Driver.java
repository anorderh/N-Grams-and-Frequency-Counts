/**
 * (CS-310 / Program 3 - N-Grams and Frequency Counts) Driver Class
 * Class that prints internal functioning of FrequencyCount objects, allowing for visual examiantion
 * @author Anthony Norderhaug
 * @date 11/13/2020
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.util.Scanner;
import java.util.List;

public class Driver {

    public static void main(String[] args) {
        // within try-catch block to account for errors
        try {
            // checking if current arguments follow desired usage
            if (args.length != 1) {
                throw new IllegalArgumentException();
            }
            List<String> tokens = extractContents(args[0]);
            // throws IOException, including FileNotFoundException, in case file not found or insufficient user perms

            FrequencyCount freqCount = new FrequencyCount(tokens); // creating Frequency Count with solely tokens
            FrequencyCount freqCountDegree4 = new FrequencyCount(tokens, 4); // creating to degree of 4

            System.out.println("\nX----------------------FREQUENCY COUNT, DEGREE OF 1----------------------X");
            printHeadAndTail(freqCount);

            System.out.println("\nX----------------------FREQUENCY COUNT, DEGREE OF 4----------------------X");
            printHeadAndTail(freqCountDegree4);
            generatePoem(freqCountDegree4); // call to helper method, generates poem

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

    /**
     * extractContents() derives Scanner from String filename and populates List contents. Scanner is closed.
     *
     * @param filename                                      String representing desired file
     * @return                                              List<String> containing all individual tokens
     * @throws IOException                                  Exception in case file not found or insufficient perms
     */
    private static List<String> extractContents(String filename) throws IOException {
        ArrayList<String> contents = new ArrayList<>();
        Scanner input = new Scanner(new FileInputStream(filename));

        while (input.hasNext()) {
            contents.add(input.next().toLowerCase());
        }

        input.close();
        return contents;
    }

    /**
     * printHeadAndTail pulls and formats data from FrequencyCount's head() and tail() files. Includes frequency and
     * percentage compared to all present data
     *
     * @param freqCount                                     FrequencyCount object to be used
     */
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

    /**
     * generatePoem() pulls 4 random tokens and forms a text poem, formatting tokens 1-2-1
     *
     * @param freqCount                                     FrequencyCount object to be used
     */
    private static void generatePoem(FrequencyCount freqCount) {

        System.out.println("\nGenerating poem based on 4 random tokens...");
        System.out.println("* (Attributed to Frequency Count of 4th degree)\n");
        System.out.println("\t\"" + freqCount.randomToken() + "\n\t" + freqCount.randomToken() +
                " " + freqCount.randomToken() + "\n\t" + freqCount.randomToken() + "\"");
    }

}

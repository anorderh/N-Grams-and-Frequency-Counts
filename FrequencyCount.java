/**
 * (CS-310 / Program 3 - N-Grams and Frequency Counts) FrequencyCount Class
 * Class using HashMap to store Strings in a List and track each'S frequency with associated Integers
 * @author Anthony Norderhaug
 * @date 11/13/2020
 */
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class FrequencyCount {
    // HashMap field, acts as base for FrequencyCount object
    private HashMap<String, Integer> frequencies = new HashMap<>();

    /**
     * constructor with input List and no degree specification, defaults to 1 and initializes class HashMap
     *
     * @param text                                              input List of tokens
     */
    public FrequencyCount(List<String> text) {
        initializeMap(text);
    }

    /**
     * constructor with input List and specified degree. Forms List degreeTokens based off on inputList and degree.
     * Initializes class HashMap with degreeTokens
     *
     * @param text                                              input List of tokens
     * @param degree                                            int representing degree
     */
    public FrequencyCount(List<String> text, int degree) {
        String current = "";
        ArrayList<String> degreeTokens = new ArrayList<String>();
        int startIndex = 0;

        while (degree + startIndex <= text.size()) {
            for (int i = 0; i < degree; i++) {
                if (i == 0) {
                    current += text.get(i + startIndex);
                } else {
                    current += " " + text.get(i + startIndex);
                }
            }
            degreeTokens.add(current);
            current = "";
            startIndex++;
        }
        initializeMap(degreeTokens);
    }

    /**
     * initializeMap adds each token within List text to the class HashMap
     * @param text
     */
    private void initializeMap(List<String> text) {
        for (String token: text) {
            add(token);
        }
    }

    /**
     * sortedTokens() creates ArrayList based off of class HashMap's keySet and sorts. Tokens with larger frequencies in front,
     * lower frequencies closer to end.
     *
     * @return                                                 ArrayList representing sorted tokens
     */
    private ArrayList<String> sortedTokens() {
        ArrayList<String> tokens = new ArrayList<String>(frequencies.keySet());
        String curToken;
        int prevIndex;

        // insertion sort
        for (int i = 1; i < tokens.size(); i++) {
            curToken = tokens.get(i);
            prevIndex = i - 1;

            // moving lesser elements to the back, greater in front
            while (prevIndex >= 0 && frequencies.get(tokens.get(prevIndex)) < frequencies.get(curToken)) {
                tokens.set(prevIndex+1, tokens.get(prevIndex));
                prevIndex--;
            }
            tokens.set(prevIndex+1, curToken);
        }

        return tokens;
    }

    /**
     * head() uses SortedTokens() to return subList of first 20 terms, i.e 20 most frequent. If class HashMap's size < 20,
     * entire list is returned.
     *
     * @return                                                 List representing sorted terms (first 20 or entire List)
     */
    public List<String> head() {
        ArrayList<String> sortedTokens = sortedTokens();

        if (sortedTokens.size() < 20) {
            return sortedTokens;
        } else {
            return sortedTokens.subList(0, 20);
        }
    }

    /**
     * tail() uses SortedTokens() to return subList of last 20 terms, i.e 20 least frequent. If class HashMap's size < 20,
     * entire list is returned.
     *
     * @return                                                 List representing sorted terms (last 20 or entire List)
     */
    public List<String> tail() {
        ArrayList<String> sortedTokens = sortedTokens();

        if (sortedTokens.size() < 20) {
            return sortedTokens;
        } else {
            return sortedTokens.subList(sortedTokens.size()-20, sortedTokens.size());
        }
    }

    /**
     * randomToken() returns a random token in frequencies. Facilitated by randGen's randomized index used within ArrayList
     * of unique tokens.
     *
     * @return                                                  Random token retrieved by randGen
     */
    public String randomToken() {
        ArrayList<String> tokens = new ArrayList<String>(frequencies.keySet());
        Random randGen = new Random();

        return tokens.get(randGen.nextInt(tokens.size()));
    }

    /**
     * count() returns frequency of the input token. If not present, 0 is returned.
     *
     * @param token                                             String representing desired token
     * @return                                                  int representing found frequency
     */
    public int count(String token) {
        return frequencies.getOrDefault(token, 0);
    }

    /**
     * percent() takes entire frequency sum of base HashMap and divides input token's frequency by it. Returns value,
     * representing desired token's held percentage compared to all present words
     *
     * @param token                                             String representing desired token
     * @return                                                  int representing percentage amongst all present words
     */
    public double percent(String token) {
        int frequencySum = 0;

        for (String curToken: frequencies.keySet()) {
            frequencySum += frequencies.get(curToken);
        }

        return (frequencies.get(token) * 1.0)/frequencySum;
    }

    /**
     * add() adds an input token into the base HashMap. If a token is already present, it updates frequency by +1. If
     * not, it adds onto with starting frequency of 1.
     *
     * @param token                                             String representing desired token
     * @return                                                  Frequency after token addition
     */
    public int add(String token) {
        if (frequencies.containsKey(token)) {
            frequencies.replace(token, frequencies.get(token) +1);
        } else {
            frequencies.put(token, 1);
        }
        return frequencies.get(token);
    }

}

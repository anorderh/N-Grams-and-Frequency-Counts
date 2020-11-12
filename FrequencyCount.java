import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class FrequencyCount {
    private HashMap<String, Integer> frequencies = new HashMap<>();

    public FrequencyCount(List<String> text) {
        initializeMap(text);
    }

    public FrequencyCount(List<String> text, int degree) {
        String current = "";
        int size = text.size();
        int startIndex = 0;

        while (degree + startIndex <= size) {
            for (int i = 0; i < degree; i++) {
                if (i == 0) {
                    current += text.get(i + startIndex);
                } else {
                    current += " " + text.get(i + startIndex);
                }
            }
            text.add(current);
            current = "";
            startIndex++;
        }
        text = text.subList(size, size + startIndex);
        initializeMap(text);
    }

    private void initializeMap(List<String> text) {
        for (String token: text) {
            add(token);
        }
    }

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

    public List<String> head() {
        ArrayList<String> sortedTokens = sortedTokens();

        if (sortedTokens.size() < 20) {
            return sortedTokens;
        } else {
            return sortedTokens.subList(0, 20);
        }
    }

    public List<String> tail() {
        ArrayList<String> sortedTokens = sortedTokens();

        if (sortedTokens.size() < 20) {
            return sortedTokens;
        } else {
            return sortedTokens.subList(sortedTokens.size()-20, sortedTokens.size());
        }
    }

    public String randomToken() {
        ArrayList<String> tokens = new ArrayList<String>(frequencies.keySet());
        Random randGen = new Random();

        return tokens.get(randGen.nextInt(tokens.size()));
    }

    public int count(String token) {
        return frequencies.getOrDefault(token, 0);
    }

    public double percent(String token) {
        int frequencySum = 0;

        for (String curToken: frequencies.keySet()) {
            frequencySum += frequencies.get(curToken);
        }

        return (frequencies.get(token) * 1.0)/frequencySum;
    }

    public int add(String token) {
        if (frequencies.containsKey(token)) {
            frequencies.replace(token, frequencies.get(token) +1);
        } else {
            frequencies.put(token, 1);
        }
        return frequencies.get(token);
    }

}

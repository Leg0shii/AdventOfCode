package de.legoshi.AOC_2023.day4;

import de.legoshi.FileHandler;

import java.util.*;

public class Day4 {

    public static void execute() {
        String input = FileHandler.readFile("src/main/java/de/legoshi/AOC_2023/day4/input.txt");
        String[] values = input.split("\n");

        HashMap<Integer, Integer> cardsMap = new HashMap<>();
        int totalPointCount = 0;

        for (int id = 1; id <= values.length; id++) {
            cardsMap.put(id, 1);
        }

        // create two lists for card
        int currentID = 1;
        for (String card : values) {
            int winnerCount = 0;

            String tempString = card.split(":")[1];
            String[] firstPartCard = tempString.split(" \\| ")[0].split(" ");
            List<String> secondPartCard = Arrays.asList(tempString.split(" \\| ")[1].split(" "));

            for (String s : firstPartCard) {
                if (s.equals("")) continue;
                if (secondPartCard.contains(s)) {
                    winnerCount++;
                }
            }

            int thisCardAmount = cardsMap.get(currentID);
            for (int id = currentID + 1; id <= currentID + winnerCount; id++) {
                if (cardsMap.containsKey(id)) {
                    cardsMap.put(id, cardsMap.get(id) + thisCardAmount);
                }
            }

            totalPointCount = totalPointCount + (int) Math.pow(2, winnerCount-1);
            currentID++;
        }

        int totalCards = cardsMap.values().stream().mapToInt(Integer::intValue).sum();

        System.out.println(totalPointCount);
        System.out.println(totalCards);
    }


}

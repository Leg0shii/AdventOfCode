package de.legoshi.AOC_2023.day6;

import de.legoshi.FileHandler;

import java.util.ArrayList;
import java.util.List;

public class Day6 {

    public static void execute() {
        String input = FileHandler.readFile("src/main/java/de/legoshi/AOC_2023/day6/input.txt");
        String[] values = input.split("\n");

        List<Long> timeList = parseList(values[0]);
        List<Long> distanceList = parseList(values[1]);
        List<Long> possibilities = new ArrayList<>();

        // get all factors above distance
        for (int index = 0; index < timeList.size(); index++) {
            long count = 0;
            long time = timeList.get(index);
            long distance = distanceList.get(index);

            for (long attempt = 1; attempt < time; attempt++) {
                long product = attempt * (time - attempt);
                if (product > distance) {
                    count++;
                }
            }
            possibilities.add(count);
        }

        long product = 1;
        for (long possibility : possibilities) {
            product = product * possibility;
        }
        System.out.println(product);

    }

    public static List<Long> parseList(String input) {
        List<Long> timeList = new ArrayList<>();
        for (String value : input.split(" ")) {
            try {
                timeList.add(Long.parseLong(value));
            } catch (NumberFormatException ignored) { }
        }
        return timeList;
    }

}

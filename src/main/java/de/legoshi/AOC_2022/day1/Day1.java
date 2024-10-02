package de.legoshi.AOC_2022.day1;

import de.legoshi.FileHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day1 {

    public static void execute() {
        String input = FileHandler.readFile("src/main/java/de/legoshi/day1/input.txt");
        String[] values = input.split(" ");
        List<Integer> ints = new ArrayList<>();
        for (String value : values) {
            int addValue = 0;
            for (String intValue : value.split("\n")) {
                if (!intValue.equals("")) {
                    addValue = addValue + Integer.parseInt(intValue);
                }
            }
            ints.add(addValue);
        }

        Collections.sort(ints);
        System.out.println(ints.get(ints.size()-1) + ints.get(ints.size()-2) + ints.get(ints.size()-3));
    }

}

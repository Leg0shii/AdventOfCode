package de.legoshi.AOC_2023.day9;

import de.legoshi.FileHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day9 {

    public static void execute() {
        String input = FileHandler.readFile("src/main/java/de/legoshi/AOC_2023/day9/input.txt");
        String[] values = input.split("\n");
        int resultTask1 = 0;
        int resultTask2 = 0;

        for (String line : values) {
            List<List<Integer>> numbers = new ArrayList<>();
            numbers.add(Arrays.stream(line.split(" ")).map(Integer::parseInt).collect(Collectors.toList()));

            // Task 1
            List<Integer> toCheck = numbers.get(0);
            while (!containsAll0(toCheck)) {
                List<Integer> newList = new ArrayList<>();
                for (int i = 0; i < (toCheck.size() - 1); i++) {
                    newList.add(toCheck.get(i + 1) - toCheck.get(i));
                }
                numbers.add(newList);
                toCheck = new ArrayList<>(newList);
            }

            for (List<Integer> list : numbers) {
                resultTask1 = resultTask1 + list.get(list.size()-1);
            }

            // Task 2
            Collections.reverse(numbers);
            numbers.get(0).add(0, 0);
            for (int i = 0; i < numbers.size() - 1; i++) {
                List<Integer> list = numbers.get(i);
                List<Integer> listAbove = numbers.get(i + 1);
                listAbove.add(0, listAbove.get(0) - list.get(0));
            }

            resultTask2 = resultTask2 + numbers.get(numbers.size() - 1).get(0);
        }

        System.out.println(resultTask1);
        System.out.println(resultTask2);
    }

    public static boolean containsAll0(List<Integer> list) {
        for (int i : list) {
            if (i != 0) return false;
        }
        return true;
    }

}

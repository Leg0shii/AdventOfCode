package de.legoshi.AOC_2022.day3;

import de.legoshi.FileHandler;

public class Day3 {

    public static void execute() {
        String input = FileHandler.readFile("src/main/java/de/legoshi/day3/input.txt");
        String[] items = input.split("\n");

        int score = 0;
        for (String item : items) {
            String partOne = item.substring(0, item.length()/2);
            String partTwo = item.substring(item.length()/2);
            // System.out.println(partOne.length() + " " + partTwo.length());
            for (char c : partOne.toCharArray()) {
                if (partTwo.contains(String.valueOf(c))) {
                    score = score + getScore(c);
                    System.out.println(c + " " + getScore(c));
                    break;
                }
            }
        }

        System.out.println(score);
    }

    public static void execute2() {
        String input = FileHandler.readFile("src/main/java/de/legoshi/day3/input.txt");
        String[] items = input.split("\n");

        int score = 0;
        for (int i = 0; i <= items.length - 3; i = i + 3) {
            String partOne = items[i];
            String partTwo = items[i+1];
            String partThree = items[i+2];
            for (char c : partOne.toCharArray()) {
                if (partTwo.contains(String.valueOf(c)) && partThree.contains(String.valueOf(c))) {
                    score = score + getScore(c);
                    break;
                }
            }
        }

        System.out.println(score);
    }

    private static int getScore(char c) {
        if (c >= 'a' && c <= 'z') {
            return (int) c - 96;
        } else {
            return (int) c - 65 + 27;
        }
    }

}

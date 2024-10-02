package de.legoshi.AOC_2023.day2;

import de.legoshi.FileHandler;

public class Day2 {

    public static void execute() {
        String input = FileHandler.readFile("src/main/java/de/legoshi/AOC_2023/day2/input.txt");
        String[] values = input.split("\n");

        int result = 0;
        int cubedResult = 0;

        int maxRed = 12;
        int maxGreen = 13;
        int maxBlue = 14;

        for (String game : values) {
            String[] gameValues = game.split(":");
            int id = Integer.parseInt(gameValues[0].replace("Game ", "").trim());

            int blue = getMaxColorCount(gameValues[1], "blue");
            int green = getMaxColorCount(gameValues[1], "green");
            int red = getMaxColorCount(gameValues[1], "red");

            if (blue <= maxBlue && green <= maxGreen && red <= maxRed) {
                result = result + id;
            }
            cubedResult = cubedResult + (blue * green * red);
        }

        System.out.println(result);
        System.out.println(cubedResult);
    }

    public static int getMaxColorCount(String game, String color) {
        String[] colorValues = game.replace(";", ",").split(",");
        int max = -1;
        for (String value : colorValues) {
            if (value.contains(color)) {
                int temp = Integer.parseInt(value.replace(" " + color, "").trim());
                if (temp > max) {
                    max = temp;
                }
            }
        }
        return max;
    }

}

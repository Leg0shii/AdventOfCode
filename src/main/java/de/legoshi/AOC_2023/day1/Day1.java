package de.legoshi.AOC_2023.day1;

import de.legoshi.FileHandler;

public class Day1 {

    public static void execute() {
        String input = FileHandler.readFile("src/main/java/de/legoshi/AOC_2023/day1/input.txt");
        String[] values = input.split("\n");

        int result = 0;
        for (String value : values) {
            value = replaceChars(value);
            String subString = "";
            for (char c : value.toCharArray()) {
                int numValue = Character.getNumericValue(c);
                if (numValue <= 9 && numValue > 0) {
                    subString += numValue;
                }
            }
            result += Character.getNumericValue(subString.charAt(0)) * 10 + Character.getNumericValue(subString.charAt(subString.length() - 1));
        }
        System.out.println(result);
    }

    // 50960 too low
    // 53875
    // 54900
    public static String replaceChars(String value) {
        String helper = "";

        for (char c : value.toCharArray()) {
            helper = helper + c;
            helper = helper.replaceFirst("one", "1e");
            helper = helper.replaceFirst("two", "2o");
            helper = helper.replaceFirst("three", "3e");
            helper = helper.replaceFirst("four", "4");
            helper = helper.replaceFirst("five", "5e");
            helper = helper.replaceFirst("six", "6");
            helper = helper.replaceFirst("seven", "7n");
            helper = helper.replaceFirst("eight", "8t");
            helper = helper.replaceFirst("nine", "9e");
        }
        return helper;
    }

}

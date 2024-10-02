package de.legoshi.AOC_2023.day14;

import de.legoshi.FileHandler;

public class Day14 {

    // too high 91016
    public static void execute() {
        String input = FileHandler.readFile("src/main/java/de/legoshi/AOC_2023/day14/input.txt");
        String[] strings = input.split("\n");
        char[][] values = new char[strings.length][strings[0].length()];

        for (int y = 0; y < values.length; y++) {
            for (int x = 0; x < values[0].length; x++) {
                values[y][x] = strings[y].charAt(x);
            }
        }

        for (int i = 1; i < 10000; i++) {
            cycleNorth(values);
            cycleWest(values);
            cycleSouth(values);
            cycleEast(values);

            int result = 0;
            for (int y = 0; y < values.length; y++) {
                for (int x = 0; x < values[0].length; x++) {
                    if (values[y][x] == 'O') {
                        result += values.length - y;
                    }
                }
            }

            System.out.println(i + ". " + result);
        }

    }

    public static void cycleNorth(char[][] values) {
        for (int y = 1; y < values.length; y++) {
            for (int back = y; back > 0; back--) {
                for (int x = 0; x < values[0].length; x++) {
                    if (values[back][x] == 'O' && values[back - 1][x] == '.') {
                        values[back][x] = '.';
                        values[back - 1][x] = 'O';
                    }
                }
            }
        }
    }

    public static void cycleSouth(char[][] values) {
        for (int y = values.length - 1; y > 0; y--) {
            for (int back = y; back < values.length; back++) {
                for (int x = 0; x < values[0].length; x++) {
                    if (values[back - 1][x] == 'O' && values[back][x] == '.') {
                        values[back - 1][x] = '.';
                        values[back][x] = 'O';
                    }
                }
            }
        }
    }

    public static void cycleWest(char[][] values) {
        for (int x = 1; x < values[0].length; x++) {
            for (int back = x; back > 0; back--) {
                for (int y = 0; y < values.length; y++) {
                    if (values[y][back] == 'O' && values[y][back - 1] == '.') {
                        values[y][back] = '.';
                        values[y][back - 1] = 'O';
                    }
                }
            }
        }
    }

    public static void cycleEast(char[][] values) {
        for (int x = values[0].length - 2; x >= 0; x--) {
            for (int back = x; back < values[0].length - 1; back++) {
                for (int y = 0; y < values.length; y++) {
                    if (values[y][back] == 'O' && values[y][back + 1] == '.') {
                        values[y][back] = '.';
                        values[y][back + 1] = 'O';
                    }
                }
            }
        }
    }

}

package de.legoshi.AOC_2023.day3;

import de.legoshi.FileHandler;

import java.awt.geom.Point2D;
import java.util.*;

public class Day3 {

    public static void execute() {
        String input = FileHandler.readFile("src/main/java/de/legoshi/AOC_2023/day3/input.txt");
        String[] values = input.split("\n");
        Character[][] map = new Character[values.length][values[0].length()];

        int result = 0;
        int gearRation = 0;

        for (int y = 0; y < values.length; y++) {
            for (int x = 0; x < values[y].length(); x++) {
                map[y][x] = values[y].charAt(x);
            }
        }

        for (int y = 0; y < values.length; y++) {
            for (int x = 0; x < values[y].length(); x++) {
                result = result + checkNumber(x, y, map);
                gearRation = gearRation + calculateGear(x, y, map);
            }
        }

        System.out.println(result);
        System.out.println(gearRation);
    }

    public static int checkNumber(int x, int y, Character[][] map) {
        if (isNewNumber(x, y, map)) {
            int fullNumber = getFullNumber(x, y, map);
            int numberLength = ("" + fullNumber).length();

            // check surroundings of the number
            if (hasSurroundings(x, y, map, numberLength)) {
                return fullNumber;
            }
            return 0;
        }
        return 0;
    }

    private static boolean hasSurroundings(int x, int y, Character[][] map, int numberLength) {
        for (int iterY = y - 1; iterY <= y + 1; iterY++) {
            for (int iterX = x - 1; iterX <= x + numberLength; iterX++) {
                if (iterY < 0 || iterX < 0 || iterY >= map.length || iterX >= map[iterY].length) {
                    continue;
                }
                char toCheck = map[iterY][iterX];
                if (toCheck != '.' && !isNumber(iterX, iterY, map)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static int getFullNumber(int x, int y, Character[][] map) {
        int number;
        if (isNumber(x, y, map)) {
            number = Character.getNumericValue(map[y][x]);
        } else {
            return 0;
        }

        if (x + 1 < map[y].length && isNumber(x + 1, y, map)) {
            number = number * 10 + Character.getNumericValue(map[y][x + 1]);
        } else {
            return number;
        }

        if (x + 2 < map[y].length && isNumber(x + 2, y, map)) {
            number = number * 10 + Character.getNumericValue(map[y][x + 2]);
        }
        return number;
    }

    public static boolean isNewNumber(int x, int y, Character[][] map) {
        if (x == 0) return true;
        return !isNumber(x - 1, y, map) && isNumber(x, y, map);
    }

    public static boolean isNumber(int x, int y, Character[][] map) {
        int intVal = Character.getNumericValue(map[y][x]);
        return intVal >= 0;
    }

    // 69586672 too low
    // 72114486 too low
    // 79158270 too high
    public static int calculateGear(int x, int y, Character[][] map) {
        HashMap<Point2D.Double, Integer> hashMap = new HashMap<>();

        if (map[y][x] != '*') return 0;

        for (int iterY = y - 1; iterY <= y + 1; iterY++) {
            for (int iterX = x - 1; iterX <= x + 1; iterX++) {
                if (iterY < 0 || iterX < 0 || iterY >= map.length || iterX >= map[iterY].length) {
                    continue;
                }

                if (!isNumber(iterX, iterY, map)) continue;

                int backX = iterX - 1;
                int backBackX = iterX - 2;

                if (backX >= 0 && isNumber(backX, iterY, map) && backBackX >= 0 && isNumber(backBackX, iterY, map)) {
                    Point2D.Double point = new Point2D.Double(backBackX, iterY);
                    hashMap.put(point, getFullNumber(backBackX, iterY, map));
                } else if (backX >= 0 && isNumber(backX, iterY, map)) {
                    Point2D.Double point = new Point2D.Double(backX, iterY);
                    hashMap.put(point, getFullNumber(backX, iterY, map));
                } else {
                    Point2D.Double point = new Point2D.Double(iterX, iterY);
                    hashMap.put(point, getFullNumber(iterX, iterY, map));
                }
            }

        }

        if (hashMap.size() == 2) {
            return hashMap.values().stream().reduce(1, (a, b) -> a * b);
        }
        return 0;
    }

}

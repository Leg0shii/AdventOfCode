package de.legoshi.AOC_2023.day11;

import de.legoshi.FileHandler;

import java.util.ArrayList;
import java.util.List;

public class Day11 {

    // too low 82000292
    // too high 4.477.368.640.566
    public static void execute() {
        String input = FileHandler.readFile("src/main/java/de/legoshi/AOC_2023/day11/input.txt");
        String[] values = input.split("\n");

        List<Integer[]> positions = new ArrayList<>();
        List<Integer> xRows = new ArrayList<>();
        List<Integer> yRows = new ArrayList<>();

        for (int y = 0; y < values.length; y++) {
            for (int x = 0; x < values[0].length(); x++) {
                if (values[y].charAt(x) == '#') {
                    positions.add(new Integer[]{x, y});
                }
            }
            if (!values[y].contains("#")) xRows.add(y);
        }

        for (int x = 0; x < values[0].length(); x++) {
            boolean hasGalaxy = true;
            for (String value : values) {
                if (value.charAt(x) == '#') {
                    hasGalaxy = false;
                    break;
                }
            }
            if (hasGalaxy) yRows.add(x);
        }

        long resultTask1 = 0;
        long resultTask2 = 0;
        for (int i = 0; i < positions.size(); i++) {
            for (int j = i; j < positions.size(); j++) {
                if (i == j) continue;
                resultTask1 = resultTask1 + calcDistance(positions.get(i)[0], positions.get(i)[1], positions.get(j)[0], positions.get(j)[1], xRows, yRows, 1);
                resultTask2 = resultTask2 + calcDistance(positions.get(i)[0], positions.get(i)[1], positions.get(j)[0], positions.get(j)[1], xRows, yRows, 999999);
            }
        }

        System.out.println(resultTask1);
        System.out.println(resultTask2);

    }

    public static long calcDistance(int x1, int y1, int x2, int y2, List<Integer> xRows, List<Integer> yRows, long zoom) {
        long dist = Math.abs(x1 - x2) + Math.abs(y1 - y2);
        long count = 0;
        for (int x = Math.min(x1, x2); x < Math.max(x1, x2); x++) {
            if (yRows.contains(x)) count++;
        }
        for (int y = Math.min(y1, y2); y < Math.max(y1, y2); y++) {
            if (xRows.contains(y)) count++;
        }
        return dist + count * zoom;
    }

}

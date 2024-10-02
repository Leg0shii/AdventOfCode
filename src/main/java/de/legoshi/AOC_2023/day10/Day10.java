package de.legoshi.AOC_2023.day10;

import de.legoshi.FileHandler;

import java.util.ArrayList;
import java.util.List;

public class Day10 {

    // too low 298
    // too low 306
    // wrong 528
    // too high 8222
    public static void execute() {
        String input = FileHandler.readFile("src/main/java/de/legoshi/AOC_2023/day10/input.txt");
        String[] values = input.split("\n");
        Node[][] map = new Node[values.length][values[0].length()];

        int[] start = new int[2];
        for (int y = 0; y < values.length; y++) {
            for (int x = 0; x < values[y].length(); x++) {
                map[y][x] = new Node(values[y].charAt(x));
                if (map[y][x].value == 'S') {
                    start[0] = x;
                    start[1] = y;
                    map[y][x].value = 'L';
                }
            }
        }

        int count = 1;
        List<Integer[]> possiblePos = getSteps(map, start[0], start[1]);
        map[start[1]][start[0]].visited = true;

        while (!possiblePos.isEmpty()) {
            Integer[] curr = possiblePos.get(0);
            possiblePos = getSteps(map, curr[0], curr[1]);
            map[curr[1]][curr[0]].visited = true;
            count++;
        }

        int resultTask2 = 0;
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (!map[y][x].visited && isSurrounded(map, x, y)) {
                    resultTask2++;
                }
            }
        }

        System.out.println(count / 2);
        System.out.println(resultTask2);
    }

    private static boolean isSurrounded(Node[][] map, int x, int y) {
        int wallCount = 0;
        boolean checked = false;
        String wall = "";
        for (int checkX = 0; checkX < map[0].length; checkX++) {

            if (checked && !isHorizontalConnected(map, checkX, y)) {
                if (wall.contains("L") && wall.contains("J") || wall.contains("F") && wall.contains("7")) {
                    wallCount++;
                }
                checked = false;
                wall = "";
            }

            if (checked && isHorizontalConnected(map, checkX, y)) {
                wall = wall + map[y][checkX].value + "";
            }

            if (map[y][checkX].visited && !checked) {
                checked = true;
                wallCount++;
                wall = wall + map[y][checkX].value + "";
            }

            if (x == checkX) {
                if (wallCount % 2 == 0) return false;
                wallCount = 0;
                wall = "";
            }
        }
        if (checked && !isHorizontalConnected(map, map[0].length, y)) {
            if (wall.contains("L") && wall.contains("J") || wall.contains("F") && wall.contains("7")) {
                wallCount++;
            }
            checked = false;
            wall = "";
        }

        if (wallCount % 2 == 0) return false;
        wall = "";
        checked = false;
        wallCount = 0;

        for (int checkY = 0; checkY < map.length; checkY++) {

            if (checked && !isVerticalConnected(map, x, checkY)) {
                if (wall.contains("L") && wall.contains("F") || wall.contains("J") && wall.contains("7")) {
                    wallCount++;
                }
                checked = false;
                wall = "";
            }

            if (checked && isVerticalConnected(map, x, checkY)) {
                wall = wall + map[checkY][x].value + "";
            }

            if (map[checkY][x].visited && !checked) {
                checked = true;
                wallCount++;
                wall = wall + map[checkY][x].value + "";
            }

            if (y == checkY) {
                if (wallCount % 2 == 0) return false;
                wallCount = 0;
            }
        }
        if (checked && !isVerticalConnected(map, x, map.length)) {
            if (wall.contains("L") && wall.contains("F") || wall.contains("J") && wall.contains("7")) {
                wallCount++;
            }
            checked = false;
            wall = "";
        }
        if (wallCount % 2 == 0) return false;

        return true;
    }

    public static boolean isHorizontalConnected(Node[][] map, int currX, int currY) {
        if (currX - 1 < 0 || currX >= map[0].length) return false;

        char prevSym = map[currY][currX - 1].value;
        char currSym = map[currY][currX].value;

        if (prevSym == 'L' && (currSym == '-' || currSym == 'J' || currSym == '7')) return true;
        if (prevSym == '-' && (currSym == '-' || currSym == 'J' || currSym == '7')) return true;
        if (prevSym == 'F' && (currSym == '-' || currSym == 'J' || currSym == '7')) return true;

        return false;
    }

    public static boolean isVerticalConnected(Node[][] map, int currX, int currY) {
        if (currY - 1 < 0 || currY >= map.length) return false;

        char prevSym = map[currY - 1][currX].value;
        char currSym = map[currY][currX].value;

        if (prevSym == '|' && (currSym == '|' || currSym == 'L' || currSym == 'J')) return true;
        if (prevSym == '7' && (currSym == '|' || currSym == 'L' || currSym == 'J')) return true;
        if (prevSym == 'F' && (currSym == '|' || currSym == 'L' || currSym == 'J')) return true;

        return false;
    }

    public static List<Integer[]> getSteps(Node[][] map, int currX, int currY) {
        List<Integer[]> step = new ArrayList<>();

        char value = map[currY][currX].value;
        if (currY - 1 >= 0 && !map[currY - 1][currX].visited) {
            if (value == 'L' || value == 'J' || value == '|') {
                step.add(new Integer[]{currX, currY - 1});
            }
        }
        if (currY + 1 < map.length && !map[currY + 1][currX].visited) {
            if (value == 'F' || value == '|' || value == '7') {
                step.add(new Integer[]{currX, currY + 1});
            }
        }
        if (currX - 1 >= 0 && !map[currY][currX - 1].visited) {
            if (value == '-' || value == 'J' || value == '7') {
                step.add(new Integer[]{currX - 1, currY});
            }
        }
        if (currX + 1 < map[currY].length && !map[currY][currX + 1].visited) {
            if (value == '-' || value == 'F' || value == 'L') {
                step.add(new Integer[]{currX + 1, currY});
            }
        }

        return step;
    }

    public static class Node {

        public char value;
        public boolean visited;

        public Node(char value) {
            this.value = value;
            this.visited = false;
        }
    }

}

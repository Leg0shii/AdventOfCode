package de.legoshi.AOC_2023.day16;

import de.legoshi.FileHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Day16 {

    public static void execute() {
        String input = FileHandler.readFile("src/main/java/de/legoshi/AOC_2023/day16/input.txt");
        String[] values = input.split("\n");

        char[][] symbolMap = new char[values.length][values[0].length()];

        for (int y = 0; y < values.length; y++) {
            for (int x = 0; x < values[0].length(); x++) {
                symbolMap[y][x] = values[y].charAt(x);
            }
        }

        List<Movement> startPos = new ArrayList<>();
        for (int y = 0; y < values.length; y++) {
            for (int x = 0; x < values[0].length(); x++) {
                if (y == 0) {
                    startPos.add(new Movement(new int[]{0, 1}, new int[]{x, y - 1}));
                }
                if (y == values.length - 1) {
                    startPos.add(new Movement(new int[]{0, -1}, new int[]{x, y + 1}));
                }
                if (x == 0) {
                    startPos.add(new Movement(new int[]{1, 0}, new int[]{x - 1, y}));
                }
                if (x == values[0].length() - 1) {
                    startPos.add(new Movement(new int[]{-1, 0}, new int[]{x + 1, y}));
                }
            }
        }

        int max = 0;
        for (Movement start : startPos) {
            Stack<Movement> movements = new Stack<>();
            Movement[][] lightMap = new Movement[values.length][values[0].length()];
            movements.push(start);

            while (!movements.isEmpty()) {
                Movement current = movements.pop();

                if (!(current.pos[0] < 0 || current.pos[0] >= values[0].length() || current.pos[1] < 0 || current.pos[1] >= values.length)) {
                    Movement before = lightMap[current.pos[1]][current.pos[0]];
                    if (before != null && before.vec[0] == current.vec[0] && before.vec[1] == current.vec[1]) {
                        continue;
                    }
                    lightMap[current.pos[1]][current.pos[0]] = current;
                }

                int[] nextPos = new int[]{current.pos[0] + current.vec[0], current.pos[1] + current.vec[1]};
                int[] nextVec = new int[]{current.vec[0], current.vec[1]};

                if (!(nextPos[1] >= 0 && nextPos[1] < values.length && nextPos[0] >= 0 && nextPos[0] < values[0].length())) {
                    continue;
                }

                if (symbolMap[nextPos[1]][nextPos[0]] == '|') {
                    if (nextVec[0] == 1 || nextVec[0] == -1) {
                        movements.push(new Movement(new int[]{0, 1}, nextPos));
                        movements.push(new Movement(new int[]{0, -1}, nextPos));
                        continue;
                    }
                }

                if (symbolMap[nextPos[1]][nextPos[0]] == '-') {
                    if (nextVec[1] == 1 || nextVec[1] == -1) {
                        movements.push(new Movement(new int[]{1, 0}, nextPos));
                        movements.push(new Movement(new int[]{-1, 0}, nextPos));
                        continue;
                    }
                }

                if (symbolMap[nextPos[1]][nextPos[0]] == '/') {
                    if (nextVec[0] == 1) movements.push(new Movement(new int[]{0, -1}, nextPos));
                    else if (nextVec[0] == -1) movements.push(new Movement(new int[]{0, 1}, nextPos));
                    else if (nextVec[1] == -1) movements.push(new Movement(new int[]{1, 0}, nextPos));
                    else if (nextVec[1] == 1) movements.push(new Movement(new int[]{-1, 0}, nextPos));
                    continue;
                }

                if (symbolMap[nextPos[1]][nextPos[0]] == '\\') {
                    if (nextVec[0] == 1) movements.push(new Movement(new int[]{0, 1}, nextPos));
                    else if (nextVec[0] == -1) movements.push(new Movement(new int[]{0, -1}, nextPos));
                    else if (nextVec[1] == 1) movements.push(new Movement(new int[]{1, 0}, nextPos));
                    else if (nextVec[1] == -1) movements.push(new Movement(new int[]{-1, 0}, nextPos));
                    continue;
                }

                movements.push(new Movement(nextVec, nextPos));
            }

            int result = 0;
            for (int y = 0; y < values.length; y++) {
                for (int x = 0; x < values[0].length(); x++) {
                    if (lightMap[y][x] != null) {
                        result++;
                    }
                }
            }

            if (max < result) {
                max = result;
            }
        }

        System.out.println(max);

    }

    public static class Movement {

        public int[] vec;
        public int[] pos;

        public Movement(int[] vec, int[] pos) {
            this.vec = vec;
            this.pos = pos;
        }

    }

}

package de.legoshi.AOC_2023.day13;

import de.legoshi.FileHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Day13 {

    // task 1
    // too high 37999
    // too high 38026

    // task 2
    // too low 32377
    public static void execute() {
        String input = FileHandler.readFile("src/main/java/de/legoshi/AOC_2023/day13/input.txt");
        String[] values = input.split("\n");
        List<List<String>> lines = parseInput(values);
        lines.addAll(parseInputSwitched(lines));

        List<Day13Entry> entries = new ArrayList<>();
        for (int i = 0; i < lines.size() / 2; i++) {
            entries.add(new Day13Entry(lines.get(i), lines.get(i + (lines.size() / 2))));
        }

        for (Day13Entry entry : entries) {
            calculate(entry.vertical, entry.arr, entry.boo, true);
            calculate(entry.horizontal, entry.arr, entry.boo, false);

            for (int y = 0; y < entry.vertical.size(); y++) {
                for (int x = 0; x < entry.vertical.get(y).length(); x++) {
                    entry.vertical.set(y, switchState(entry.vertical.get(y), x));
                    calculate(entry.vertical, entry.arr, entry.boo, true);
                    entry.vertical.set(y, switchState(entry.vertical.get(y), x));
                }
            }
            for (int y = 0; y < entry.horizontal.size(); y++) {
                for (int x = 0; x < entry.horizontal.get(y).length(); x++) {
                    entry.horizontal.set(y, switchState(entry.horizontal.get(y), x));
                    calculate(entry.horizontal, entry.arr, entry.boo, false);
                    entry.horizontal.set(y, switchState(entry.horizontal.get(y), x));
                }
            }
        }

        int resultTask1 = 0;
        int resultTask2 = 0;
        for (Day13Entry entry : entries) {
            resultTask1 += entry.boo[0] ? (entry.arr[0] + 1) * 100 : (entry.arr[0] + 1);
            resultTask2 += entry.boo[1] ? (entry.arr[1] + 1) * 100 : (entry.arr[1] + 1);
        }

        System.out.println(resultTask1);
        System.out.println(resultTask2);

    }

    public static String replaceCharAt(String str, int index, char ch) {
        if (index < 0 || index >= str.length()) {
            return str; // index is out of bounds
        }
        char[] chars = str.toCharArray();
        chars[index] = ch;
        return new String(chars);
    }

    public static String switchState(String state, int val) {
        if (state.charAt(val) == '#') {
            state = replaceCharAt(state, val, '.');
        } else {
            state = replaceCharAt(state, val, '#');
        }
        return state;
    }

    public static void calculate(List<String> field, int[] arr, boolean[] bo, boolean type) {
        for (int i = 0; i < field.size() - 1 && arr[1] == -1; i++) {
            int above = i;
            int below = i+1;
            if (field.get(above).equals(field.get(below))) {
                boolean isSym = true;
                while (above >= 0 && below < field.size()) {
                    if (!field.get(above).equals(field.get(below))) {
                        isSym = false;
                        break;
                    }
                    above--;
                    below++;
                }
                if (isSym) {
                    if (arr[0] == -1) {
                        arr[0] = i;
                        bo[0] = type;
                    } else if (arr[0] != i || bo[0] != type) {
                        arr[1] = i;
                        bo[1] = type;
                    }
                }
            }
        }
    }

    public static List<List<String>> parseInput(String[] values) {
        List<List<String>> lines = new ArrayList<>();
        List<String> line = new ArrayList<>();
        for (String value : values) {
            if (value.equals(" ")) {
                lines.add(line);
                line = new ArrayList<>();
                continue;
            }
            line.add(value);
        }
        lines.add(line);
        return lines;
    }

    public static List<List<String>> parseInputSwitched(List<List<String>> fields) {
        List<List<String>> newFields = new ArrayList<>();
        for (List<String> field : fields) {
            HashMap<Integer, String> newField = new HashMap<>();
            for (int x = 0; x < field.get(0).length(); x++) {
                for (String s : field) {
                    newField.put(x, newField.get(x) == null ? s.charAt(x) + "" : newField.get(x) + s.charAt(x));
                }
            }
            newFields.add(new ArrayList<>(newField.values()));
        }
        return newFields;
    }

    public static class Day13Entry {

        public List<String> vertical;
        public List<String> horizontal;

        public boolean[] boo = new boolean[2];
        public int[] arr = new int[2];

        public Day13Entry(List<String> vertical, List<String> horizontal) {
            this.vertical = vertical;
            this.horizontal = horizontal;
            arr[0] = -1;
            arr[1] = -1;
        }

    }

}

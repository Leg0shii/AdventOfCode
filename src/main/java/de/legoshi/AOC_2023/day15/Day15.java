package de.legoshi.AOC_2023.day15;

import de.legoshi.FileHandler;

import java.util.*;

// too low 283453
public class Day15 {

    public static void execute() {
        String input = FileHandler.readFile("src/main/java/de/legoshi/AOC_2023/day15/input.txt");
        String[] values = input.replace("\n", "").split(",");

        int task1 = 0;
        HashMap<Integer, List<Box>> boxes = new HashMap<>();

        for (String value : values) {
            int calc = 0;
            for (char c : value.toCharArray()) {
                if (c == '-')
                    boxes.getOrDefault(calc, new ArrayList<>()).removeIf(box -> box.label.equals(value.replace("-", "")));
                if (c == '=') addBox(calc, value.split("="), boxes);
                calc = ((c + calc) * 17) % 256;
            }
            task1 += calc;
        }

        int task2 = 0;
        for (Integer calc : boxes.keySet()) {
            for (int i = 0; i < boxes.get(calc).size(); i++) {
                task2 += (calc + 1) * (i + 1) * boxes.get(calc).get(i).number;
            }
        }

        System.out.println(task1);
        System.out.println(task2);
    }

    private static void addBox(int calc, String[] split, HashMap<Integer, List<Box>> boxes) {
        for (Box box : boxes.getOrDefault(calc, new ArrayList<>())) {
            if (box.label.equals(split[0])) {
                box.number = Integer.parseInt(split[1]);
                return;
            }
        }
        boxes.computeIfAbsent(calc, k -> new ArrayList<>()).add(new Box(Integer.parseInt(split[1]), split[0]));
    }

    public static class Box {

        public int number;
        public String label;

        public Box(int number, String label) {
            this.number = number;
            this.label = label;
        }
    }

}

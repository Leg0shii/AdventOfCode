package de.legoshi.AOC_2023.day8;

import de.legoshi.AOC_2023.day7.LCMCalculator;
import de.legoshi.FileHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Day8 {

    // to low 1103700
    // too high 7618585436622935552
    // too high 4368707950453336576
    public static void execute() {
        String input = FileHandler.readFile("src/main/java/de/legoshi/AOC_2023/day8/input.txt");
        String[] values = input.split("\n");

        String inputs = values[0];
        HashMap<String, LRNode> nodes = new HashMap<>();

        for (int i = 2; i < values.length; i++) {
            String key = values[i].substring(0, 3);
            String left = values[i].substring(7, 10);
            String right = values[i].substring(12, 15);
            nodes.put(key, new LRNode(left, right));
        }

        // task 1
        long task1 = getCount(inputs, nodes, "AAA");

        // task 2
        HashMap<String, Long> map = new HashMap<>();
        List<String> endings = nodes.keySet().stream().filter(s -> s.charAt(2) == 'A').collect(Collectors.toList());
        for (String ending : endings) {
            map.put(ending, getCount(inputs, nodes, ending));
        }
        long task2 = LCMCalculator.calculateLCM(new ArrayList<>(map.values()));

        System.out.println(task1);
        System.out.println(task2);
    }

    public static long getCount(String inputs, HashMap<String, LRNode> nodes, String startNode) {
        long count = 0;
        String result = startNode;
        for (; result.charAt(2) != 'Z'; count++) {
            long index = count % inputs.length();
            result = inputs.charAt((int) index) == 'L' ? nodes.get(result).left : nodes.get(result).right;
        }
        return count;
    }

    public static class LRNode {

        public String left;
        public String right;

        public LRNode(String left, String right) {
            this.left = left;
            this.right = right;
        }

    }

}

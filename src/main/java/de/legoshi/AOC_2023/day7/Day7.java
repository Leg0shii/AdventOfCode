package de.legoshi.AOC_2023.day7;

import de.legoshi.FileHandler;

import java.util.*;
import java.util.stream.Collectors;

public class Day7 {

    // too high 255159686
    // too high 254533101
    public static void execute() {
        String input = FileHandler.readFile("src/main/java/de/legoshi/AOC_2023/day7/input.txt");
        String[] values = input.split("\n");

        List<Hand> handsTask1 = Arrays.stream(values)
                .map(x -> new Hand(x.split(" ")[0], Integer.parseInt(x.split(" ")[1]), false))
                .sorted((a, b) -> a.compareHand(a, b))
                .collect(Collectors.toList());

        List<Hand> handsTask2 = Arrays.stream(values)
                .map(x -> new Hand(x.split(" ")[0], Integer.parseInt(x.split(" ")[1]), true))
                .sorted((a, b) -> a.compareHand(a, b))
                .collect(Collectors.toList());

        System.out.println(handsTask2);

        int resultTask1 = 0;
        int resultTask2 = 0;
        for (int i = 1; i <= handsTask1.size(); i++) {
            resultTask1 = resultTask1 + (i * handsTask1.get(i-1).bid);
            resultTask2 = resultTask2 + (i * handsTask2.get(i-1).bid);
        }

        System.out.println(resultTask1);
        System.out.println(resultTask2);
    }

    public static class Hand {

        public String originalHand;
        public String hand;
        public int score;
        public int bid;
        public boolean task;

        public Hand(String hand, int bid, boolean task) {
            this.originalHand = hand + "";
            this.task = task;
            if (task) {
                hand = hand.replace("J", highestOcc(hand));
            }
            this.hand = hand;
            this.bid = bid;

            String copy = hand + "";
            int replaceCount = 0;
            while (copy.length() > 0) {
                copy = copy.replace(copy.substring(0, 1), "");
                replaceCount++;
            }

            copy = hand + "";
            switch (replaceCount) {
                case 0:
                case 1:
                    this.score = 6;
                    break;
                case 2:
                    String first = copy.substring(0, 1);
                    int firstCount = copy.replace(first, "").length();
                    if (firstCount == 4 || firstCount == 1) {
                        this.score = 5;
                    } else {
                        this.score = 4;
                    }
                    break;
                case 3:
                    first = copy.substring(0, 1);
                    firstCount = copy.replace(first, "").length();
                    if (firstCount == 3) {
                        this.score = 2;
                        break;
                    } else if (firstCount == 2) {
                        this.score = 3;
                        break;
                    }
                    copy = copy.replace(first, "");
                    first = copy.substring(0, 1);
                    firstCount = copy.replace(first, "").length();
                    if (firstCount == 2) {
                        this.score = 2;
                        break;
                    }
                    this.score = 3;
                    break;
                case 4:
                    this.score = 1;
                    break;
                default:
                this.score = 0;
            }

        }

        public int compareHand(Hand a, Hand b) {
            int diff = a.score - b.score;
            int count = 0;
            while (diff == 0) {
                diff = getNum(a.originalHand.substring(count, 1 + count)) - getNum(b.originalHand.substring(count, 1 + count));
                count++;
            }
            return diff;
        }

        @Override
        public String toString() {
            if (task) return originalHand + " -> " + hand + " " + score + " " + bid;
            else return hand + " " + score + " " + bid;
        }

        public static String highestOcc(String s) {
            int max = 0;
            String result = "";
            while (s.length() > 0) {
                int count = s.length() - s.replace(s.substring(0, 1), "").length();
                if (count > max && s.charAt(0) != 'J') {
                    max = count;
                    result = s.substring(0, 1);
                }
                s = s.replace(s.substring(0, 1), "");
            }
            return result;
        }

        public int getNum(String s) {
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                switch (s) {
                    case "T":
                        return 10;
                    case "J":
                        if (task) return 1;
                        return 11;
                    case "Q":
                        return 12;
                    case "K":
                        return 13;
                    case "A":
                        return 14;
                }
            }
            return 0;
        }

    }

}

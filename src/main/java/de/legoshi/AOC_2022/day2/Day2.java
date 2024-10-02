package de.legoshi.AOC_2022.day2;

import de.legoshi.FileHandler;

public class Day2 {

    public static void execute() {
        String input = FileHandler.readFile("src/main/java/de/legoshi/day2/input.txt");
        String[] inputPairs = input.split("\n");

        int score = 0;
        for (String inputPair : inputPairs) {
            if (inputPair.equals("")) {
                continue;
            }
            String[] val = inputPair.split(" ");
            char opponent = val[0].charAt(0);
            char me = val[1].charAt(0);
            score = score + calculateScore2(opponent, me);
        }
        System.out.println(score);
    }

    // X means you need to lose, Y means you need to end the round in a draw, and Z means you need to win
    public static int calculateScore2(char opponent, char me) {
        if (opponent == 'A') {
            if (me == 'X') {
                return 3;
            } else if (me == 'Y') {
                return 1 + 3;
            } else {
                return 2 + 6;
            }
        } else if (opponent == 'B') {
            if (me == 'X') {
                return 1;
            } else if (me == 'Y') {
                return 2 + 3;
            } else {
                return 3 + 6;
            }
        } else {
            if (me == 'X') {
                return 2;
            } else if (me == 'Y') {
                return 3 + 3;
            } else {
                return 1 + 6;
            }
        }
    }
    /*
    1 for Rock, 2 for Paper, 3 for Scissors
    0 if lost, 3 if draw, and 6 if won
     */
    public static int calculateScore(char opponent, char me) {
        if (me == 'X') {
            if (opponent == 'A') {
                return 3 + 1;
            } else if (opponent == 'B') {
                return 1;
            } else {
                return 1 + 6;
            }
        } else if (me == 'Y') {
            if (opponent == 'A') {
                return 2 + 6;
            } else if (opponent == 'B') {
                return 2 + 3;
            } else {
                return 2;
            }
        } else {
            if (opponent == 'A') {
                return 3;
            } else if (opponent == 'B') {
                return 3 + 6;
            } else {
                return 3 + 3;
            }
        }
    }

}

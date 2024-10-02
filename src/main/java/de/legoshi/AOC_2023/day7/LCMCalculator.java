package de.legoshi.AOC_2023.day7;

import java.util.List;

public class LCMCalculator {

    // Method to calculate LCM of two numbers
    private static long lcm(long a, long b) {
        return a * (b / gcd(a, b));
    }

    // Method to calculate GCD of two numbers
    private static long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    // Method to calculate LCM of a list of integers
    public static long calculateLCM(List<Long> numbers) {
        long result = numbers.get(0);
        for (int i = 1; i < numbers.size(); i++) {
            result = lcm(result, numbers.get(i));
        }
        return result;
    }
}
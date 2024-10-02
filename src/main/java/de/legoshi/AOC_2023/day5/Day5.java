package de.legoshi.AOC_2023.day5;

import de.legoshi.FileHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day5 {

    public static void execute() {
        String input = FileHandler.readFile("src/main/java/de/legoshi/AOC_2023/day5/input.txt");
        String[] values = input.split("\n");

        // Parse for task 1
        List<Long> seeds = Arrays.stream(values[0].replace("seeds: ", "").split(" "))
                .mapToLong(Long::parseLong)
                .boxed()
                .collect(Collectors.toList());
        List<Long> shiftedSeeds = new ArrayList<>();

        // Parse for task 2
        List<LongRange> seedRanges = parseRange(seeds);
        List<LongRange> shiftedRangeSeeds = new ArrayList<>();

        for (String line : values) {
            if (line.contains("seeds") || line.equals(" ")) continue;
            if (line.contains("map")) {
                // Shift seeds for task 1
                shiftedSeeds.addAll(seeds);
                seeds = new ArrayList<>(shiftedSeeds);
                shiftedSeeds.clear();

                // Shift seeds for task 2
                shiftedRangeSeeds.addAll(seedRanges);
                seedRanges = new ArrayList<>(shiftedRangeSeeds);
                shiftedRangeSeeds.clear();
                continue;
            }

            String[] split = line.split(" ");
            long dest = Long.parseLong(split[0]);
            long source = Long.parseLong(split[1]);
            long length = Long.parseLong(split[2]);

            task1(seeds, shiftedSeeds, dest, source, length);
            task2(seedRanges, shiftedRangeSeeds, dest, source, length);
        }

        long task1MinValue = seeds.stream().mapToLong(Long::longValue).min().getAsLong();
        long task2MinValue = seedRanges.stream().mapToLong(l -> l.start).min().getAsLong();
        System.out.println(task1MinValue);
        System.out.println(task2MinValue);
    }

    public static void task1(List<Long> seeds, List<Long> shiftedSeeds, long dest, long source, long length) {
        for (long seed : new ArrayList<>(seeds)) {
            if (seed >= source && seed < source + length) {
                seeds.remove(seed);
                long difference = dest - source;
                long newPosition = seed + difference;
                shiftedSeeds.add(newPosition);
            }
        }
    }

    public static List<LongRange> parseRange(List<Long> seeds) {
        List<LongRange> seedRanges = new ArrayList<>();
        for (int i = 0; i < seeds.size() - 1; i = i + 2) {
            long seed = seeds.get(i);
            long length = seeds.get(i + 1);
            seedRanges.add(new LongRange(seed, seed + length - 1));
        }
        return seedRanges;
    }

    public static void task2(List<LongRange> seedRanges, List<LongRange> shiftedRangeSeeds, long dest, long source, long length) {
        for (LongRange seedRange : new ArrayList<>(seedRanges)) {
            long seedStart = seedRange.start;
            long seedEnd = seedRange.end;
            long difference = dest - source;

            if (seedStart >= source && seedEnd <= source + length) { // exact overlap
                seedRange.start = seedStart + difference;
                seedRange.end = seedEnd + difference;
                seedRanges.remove(seedRange);
                shiftedRangeSeeds.add(seedRange);
            } else if (seedStart <= source && seedEnd >= source && seedEnd <= source + length) { // left overlap
                seedRange.end = source - 1;
                shiftedRangeSeeds.add(new LongRange(source + difference, seedEnd + difference));
            } else if (seedStart >= source && seedEnd >= source + length && seedStart <= source + length) { // right overlap
                seedRange.start = source + length + 1;
                shiftedRangeSeeds.add(new LongRange(seedStart + difference, source + length + difference));
            } else if (seedStart < source && seedEnd > source + length) { // inside
                seedRange.end = source - 1;
                seedRanges.add(new LongRange(source + length + 1, seedEnd));
                shiftedRangeSeeds.add(new LongRange(source + difference, source + length + difference));
            } // no overlap

        }
    }


    public static class LongRange {

        public long start;
        public long end;

        public LongRange(long start, long end) {
            this.start = start;
            this.end = end;
        }

    }

}

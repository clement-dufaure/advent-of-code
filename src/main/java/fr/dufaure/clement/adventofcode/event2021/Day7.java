package fr.dufaure.clement.adventofcode.event2021;

import java.util.Arrays;
import java.util.List;

import fr.dufaure.clement.adventofcode.utils.Day;
import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class Day7 implements Day {

    @Override
    public String part1(String inputPath) {
        var positions = Arrays.stream(ImportUtils.getString(inputPath).split(",")).map(i -> Integer.parseInt(i))
                .sorted().toList();
        var median = 0;
        if (positions.size() % 2 == 0) {
            median = Math
                    .round((positions.get((positions.size() - 1) / 2) + positions.get((positions.size() + 1) / 2)) / 2);
        } else {
            median = positions.get(positions.size() / 2);
        }
        final var m = median;
        return String.valueOf(positions.stream().map(i -> Math.abs(i - m)).mapToInt(i -> i).sum());
    }

    @Override
    public String part2(String inputPath) {
        var positions = Arrays.stream(ImportUtils.getString(inputPath).split(",")).map(i -> Integer.parseInt(i))
                .toList();
        int minFuel = Integer.MAX_VALUE;
        for (int point = positions.stream().mapToInt(i -> i).min().getAsInt(); point <= positions.stream()
                .mapToInt(i -> i)
                .max().getAsInt(); point++) {
            var fuelConsumed = fuelConsumedForPoint(point, positions);
            if (fuelConsumed < minFuel) {
                minFuel = fuelConsumed;
            }
        }
        return String.valueOf(minFuel);
    }

    private int fuelConsumedForPoint(int point, List<Integer> positions) {
        return positions.stream().map(i -> sumOfFirstInts(Math.abs(i - point))).mapToInt(i -> i).sum();
    }

    private int sumOfFirstInts(int i) {
        return (i * (i + 1)) / 2;
    }

}

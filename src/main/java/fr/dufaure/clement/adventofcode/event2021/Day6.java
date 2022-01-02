package fr.dufaure.clement.adventofcode.event2021;

import java.util.ArrayList;
import java.util.Arrays;

import fr.dufaure.clement.adventofcode.utils.Day;
import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class Day6 implements Day {

    @Override
    public String part1(String inputPath) {
        var lanterfishs = new ArrayList<Integer>();
        lanterfishs.addAll(
                Arrays.stream(ImportUtils.getString(inputPath).split(",")).map(s -> Integer.parseInt(s)).toList());
        for (var i = 0; i < 80; i++) {
            var newFishes = lanterfishs.stream().filter(f -> f.equals(0)).map(f -> 8).toList();
            lanterfishs = new ArrayList<>(lanterfishs.stream().map(f -> {
                if (f.equals(0)) {
                    return 6;
                } else {
                    return f - 1;
                }
            }).toList());
            lanterfishs.addAll(newFishes);
        }
        return String.valueOf(lanterfishs.size());
    }

    @Override
    public String part2(String inputPath) {
        final var days = new long[9];

        Arrays.stream(ImportUtils.getString(inputPath).split(",")).map(s -> Integer.parseInt(s))
                .forEach(i -> days[i] = days[i] + 1);

        var currentDays = Arrays.stream(days).boxed().toList();

        for (var i = 0; i < 256; i++) {
            var newFish = currentDays.get(0);
            var newDays = new ArrayList<Long>();
            for (int j = 1; j < 9; j++) {
                newDays.add(currentDays.get(j));
            }
            newDays.add(newFish);
            newDays.set(6, newDays.get(6) + currentDays.get(0));
            currentDays = newDays;
        }
        return String.valueOf(currentDays.stream().mapToLong(l -> l).sum());
    }

}

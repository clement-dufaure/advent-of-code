package fr.dufaure.clement.adventofcode.event2020;

import java.util.List;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day9 {

    public static void main(String[] args) {
        part1();
        part2();
    }

    public static void part1() {
        List<Long> liste = ImportUtils.getListeLongUnParLigne("./src/main/resources/2020/day9");
        System.out.println(returnFirstBadInput(liste));
    }

    public static void part2() {
        List<Long> liste = ImportUtils.getListeLongUnParLigne("./src/main/resources/2020/day9");
        List<Long> subsequence = findSequenceSum(liste, returnFirstBadInput(liste));
        System.out.println(subsequence.stream().mapToLong(l -> l).min().getAsLong()
                + subsequence.stream().mapToLong(l -> l).max().getAsLong());
    }

    static long returnFirstBadInput(List<Long> input) {
        mainLoop: for (int i = 25; i < input.size(); i++) {
            for (int j = i - 25; j < i; j++) {
                for (int k = j + 1; k < i; k++) {
                    if (input.get(i) == input.get(j) + input.get(k)) {
                        continue mainLoop;
                    }
                }
            }
            // sum not found
            return input.get(i);
        }
        throw new RuntimeException("All inputs are good");
    }

    static List<Long> findSequenceSum(List<Long> input, long sum) {
        for (int start = 0; start < input.size(); start++) {
            long currentSum = input.get(start);
            for (int end = start + 1; end < input.size(); end++) {
                currentSum += input.get(end);
                if (currentSum == sum) {
                    return input.subList(start, end + 1);
                }
                if (currentSum > sum) {
                    break;
                }
            }
        }
        throw new RuntimeException("Subsequence not found");
    }

}

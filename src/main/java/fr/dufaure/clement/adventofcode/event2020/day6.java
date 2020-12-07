package fr.dufaure.clement.adventofcode.event2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day6 {

    public static void main(String[] args) {
        part1();
        part2();
    }

    private static void part1() {
        System.out
                .println(Arrays.asList(ImportUtils.getStringWithNewLine("./src/main/resources/2020/day6").split("\n\n"))
                        .stream().map(s -> s.replace("\n", "")).mapToLong(day6::countCharDistinct).sum());
    }

    private static void part2() {
        System.out
                .println(Arrays.asList(ImportUtils.getStringWithNewLine("./src/main/resources/2020/day6").split("\n\n"))
                        .stream().map(s -> s.split("\n")).mapToLong(day6::countCommonChar).sum());

    }

    static long countCharDistinct(String str) {
        List<Integer> questionYes = new ArrayList<>();
        for (char c : str.toCharArray()) {
            questionYes.add((int) c);
        }
        return questionYes.stream().mapToInt(i -> i).distinct().count();
    }

    static long countCommonChar(String[] strs) {
        Stream<Integer> start = stringToListInt(strs[0]).stream();
        for (int i = 1; i < strs.length; i++) {
            start = start.filter(stringToListInt(strs[i])::contains);
        }
        return start.count();
    }

    static List<Integer> stringToListInt(String str) {
        List<Integer> questionYes = new ArrayList<>();
        for (char c : str.toCharArray()) {
            questionYes.add((int) c);
        }
        return questionYes;
    }

}

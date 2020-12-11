package fr.dufaure.clement.adventofcode.event2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day10 {

    public static void main(String[] args) {
        part1();
        part2();
    }

    public static void part1() {
        List<Integer> liste = ImportUtils.getListeEntierUnParLigne("./src/main/resources/2020/day10");
        liste.add(0);
        liste = liste.stream().sorted().collect(Collectors.toList());
        int numberOfDiff1 = 0;
        int numberOfDiff3 = 0;
        for (int i = 0; i < liste.size() - 1; i++) {
            if (liste.get(i + 1) - liste.get(i) == 1) {
                numberOfDiff1++;
            }
            if (liste.get(i + 1) - liste.get(i) == 3) {
                numberOfDiff3++;
            }
        }
        // add a diff3 by device connected
        numberOfDiff3++;
        System.out.println(numberOfDiff1 * numberOfDiff3);
    }

    public static void part2() {
        List<Integer> liste = ImportUtils.getListeEntierUnParLigne("./src/main/resources/2020/day10");
        liste.add(0);
        liste = liste.stream().sorted().collect(Collectors.toList());
        System.out.println(countPossibilities(liste));

    }

    static long countPossibilities(List<Integer> liste) {
        Map<Integer, List<Integer>> nextSteps = new HashMap<>();
        for (int i = 0; i < liste.size() - 1; i++) {
            nextSteps.put(liste.get(i), new ArrayList<>());
            if (liste.get(i + 1) - liste.get(i) <= 3) {
                nextSteps.get(liste.get(i)).add(liste.get(i + 1));
            }
            try {
                if (liste.get(i + 2) - liste.get(i) <= 3) {
                    nextSteps.get(liste.get(i)).add(liste.get(i + 2));
                }
                if (liste.get(i + 3) - liste.get(i) <= 3) {
                    nextSteps.get(liste.get(i)).add(liste.get(i + 3));
                }
            } catch (IndexOutOfBoundsException e) {
                continue;
            }
        }
        // nextStep does not contains the max

        Map<Integer, Long> numberOfNextSteps = new HashMap<>();
        numberOfNextSteps.put(liste.get(liste.size() - 1), 1L);
        for (int i = liste.size() - 2; i >= 0; i--) {
            numberOfNextSteps.put(liste.get(i),
                    nextSteps.get(liste.get(i)).stream().mapToLong(next -> numberOfNextSteps.get(next)).sum());
        }
        return numberOfNextSteps.get(0);

    }

}

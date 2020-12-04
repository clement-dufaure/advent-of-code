package fr.dufaure.clement.adventofcode.event2020;

import java.util.List;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day1 {

    public static void main(String[] args) {
        part1();
        part2();
    }

    public static void part1() {
        List<Integer> liste = ImportUtils.getListeEntierUnParLigne("./src/main/resources/2020/day1");

        for (int i = 1; i < liste.size(); i++) {
            for (int j = i; j < liste.size(); j++) {
                if (liste.get(i) + liste.get(j) == 2020) {
                    System.out.println(liste.get(i) * liste.get(j));
                    return;
                }
            }
        }
    }

    public static void part2() {
        List<Integer> liste = ImportUtils.getListeEntierUnParLigne("./src/main/resources/2020/day1");

        for (int i = 1; i < liste.size(); i++) {
            for (int j = i; j < liste.size(); j++) {
                for (int k = j; k < liste.size(); k++) {
                    if (liste.get(i) + liste.get(j) + +liste.get(k) == 2020) {
                        System.out.println(liste.get(i) * liste.get(j) * liste.get(k));
                        return;
                    }
                }
            }
        }
    }

}

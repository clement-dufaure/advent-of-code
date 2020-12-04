package fr.dufaure.clement.adventofcode.event2020;

import java.util.List;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day3 {

    public static void main(String[] args) {
        part1();
        part2();
    }

    public static void part1() {
        System.out.println(slope(3, 1));
    }

    public static void part2() {
        System.out.println(slope(1, 1) * slope(3, 1) * slope(5, 1) * slope(7, 1) * slope(1, 2));
    }

    static long slope(int right, int down) {
        List<String> liste = ImportUtils.getListStringUnParLigne("./src/main/resources/2020/day3");
        int where = 0;
        int lineSize = liste.get(0).length();
        int numberOfTrees = 0;
        int i = 0;
        String str;
        do {
            str = liste.get(i);
            if (str.charAt(where) == '#') {
                numberOfTrees++;
            }
            where = (where + right) % lineSize;
            i += down;
        } while (i < liste.size());
        return numberOfTrees;
    }

}

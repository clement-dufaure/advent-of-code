package fr.dufaure.clement.adventofcode.event2021;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class Day1 {

    public String part1(String input) {
        var liste = ImportUtils.getListeEntierUnParLigne(input);
        var numberIncrease = 0;
        for (int i = 1; i < liste.size(); i++) {
            if (liste.get(i) > liste.get(i - 1)) {
                numberIncrease++;
            }
        }
        return String.valueOf(numberIncrease);
    }

    public String part2(String input) {
        var liste = ImportUtils.getListeEntierUnParLigne(input);
        var numberIncrease = 0;
        for (int i = 3; i < liste.size(); i++) {
            var currentWindowSum = liste.get(i) + liste.get(i - 1) + liste.get(i - 2);
            var lastWindowSum = liste.get(i - 1) + liste.get(i - 2) + liste.get(i - 3);
            if (currentWindowSum > lastWindowSum) {
                numberIncrease++;
            }
        }
        return String.valueOf(numberIncrease);
    }

}

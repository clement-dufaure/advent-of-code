package fr.dufaure.clement.adventofcode.event2019;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day1 {

    public static void main(String[] args) {
        part1();
        part2();
    }

    public static void part1() {
        System.out.println(ImportUtils.getListStringUnParLigne("./src/main/resources/2019/day1").stream()
                .mapToInt(Integer::valueOf).map(mass -> (mass / 3) - 2).sum());
    }

    public static void part2() {
        System.out.println(ImportUtils.getListStringUnParLigne("./src/main/resources/2019/day1").stream()
                .mapToInt(Integer::valueOf).map(day1::calculateModuleTotalFuel).sum());
    }

    public static int calculateModuleTotalFuel(int mass) {
        int sum = 0;
        int partSum = (mass / 3) - 2;
        while (partSum >= 0) {
            sum += partSum;
            partSum = (partSum / 3) - 2;
        }
        return sum;
    }

}
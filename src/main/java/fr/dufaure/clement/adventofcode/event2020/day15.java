package fr.dufaure.clement.adventofcode.event2020;

import java.util.HashMap;
import java.util.Map;

public class day15 {

    public static void main(String[] args) {
        part1();
        part2();
    }

    static String input = "12,1,16,3,11,0";

    static void part1() {
        int numeroTour = 0;
        int lastSaid = 0;
        Map<Integer, Integer> lastTourSeen = new HashMap<>();
        String[] firstNumbers = input.split(",");
        numeroTour++;
        lastSaid = Integer.parseInt(firstNumbers[0]);
        for (int i = 1; i < firstNumbers.length; i++) {
            lastTourSeen.put(lastSaid, numeroTour);
            numeroTour++;
            lastSaid = Integer.parseInt(firstNumbers[i]);
        }
        while (numeroTour < 30000000) {
            numeroTour++;
            if (!lastTourSeen.containsKey(lastSaid)) {
                lastTourSeen.put(lastSaid, numeroTour - 1);
                lastSaid = 0;
            } else {
                int difference = numeroTour - 1 - lastTourSeen.get(lastSaid);
                lastTourSeen.put(lastSaid, numeroTour - 1);
                lastSaid = difference;
            }
        }
        System.out.println(lastSaid);

    }

    static void part2() {
    }

}

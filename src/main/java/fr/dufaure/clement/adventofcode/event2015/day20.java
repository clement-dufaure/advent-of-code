package fr.dufaure.clement.adventofcode.event2015;

public class day20 {

    static final int input = 34000000;

    public static void main(String[] args) {
        long start1 = System.currentTimeMillis();
        // part1();
        System.out.println("Execution part 1 en " + (System.currentTimeMillis() - start1) + " ms");
        long start2 = System.currentTimeMillis();
        part2();
        System.out.println("Execution part 2 en " + (System.currentTimeMillis() - start2) + " ms");
    }

    // 786240
    // Execution part 1 en 959224 ms
    private static void part1() {
        int seuil = 1000000;
        int nseuil = 1;
        int houseNumber = 0;
        while (true) {
            houseNumber++;
            int nombreGift = 0;
            for (int i = 1; i <= houseNumber; i++) {
                if (houseNumber % i == 0) {
                    nombreGift += i * 10;
                }
            }
            if (nombreGift > nseuil * seuil) {
                System.out.println(nombreGift);
                nseuil++;
            }
            if (nombreGift >= input) {
                System.out.println(houseNumber);
                break;
            }
        }
    }

    // 831600
    // Execution part 2 en 1127820 ms
    private static void part2() {
        int seuil = 1000000;
        int nseuil = 1;
        int houseNumber = 0;
        while (true) {
            houseNumber++;
            int nombreGift = 0;
            for (int i = 1; i <= houseNumber; i++) {
                if (houseNumber % i == 0 && houseNumber <= 50 * i) {
                    nombreGift += i * 11;
                }
            }
            if (nombreGift > nseuil * seuil) {
                System.out.println(nombreGift);
                nseuil++;
            }
            if (nombreGift >= input) {
                System.out.println(houseNumber);
                break;
            }
        }
    }

}
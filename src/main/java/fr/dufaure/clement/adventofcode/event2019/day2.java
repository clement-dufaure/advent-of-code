package fr.dufaure.clement.adventofcode.event2019;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day2 {

    public static void main(String[] args) {
        part1();
        part2();
    }

    public static void part1() {
        System.out.println(runProgramme(12, 2));
    }

    public static void part2() {
        for (int noun = 0; noun <= 99; noun++) {
            for (int verb = 0; verb <= 99; verb++) {
                if (runProgramme(noun, verb) == 19690720) {
                    System.out.println(100 * noun + verb);
                }
            }
        }
    }

    public static int runProgramme(int noun, int verb) {
        String[] liste = ImportUtils.getString("./src/main/resources/2019/day2").split(",");
        int[] listeCode = new int[liste.length];
        for (int i = 0; i < liste.length; i++) {
            listeCode[i] = Integer.valueOf(liste[i]);
        }

        // modif
        listeCode[1] = noun;
        listeCode[2] = verb;

        int pointeur = 0;
        mainLoop: while (true) {
            switch (listeCode[pointeur]) {
            case 1:
                listeCode[listeCode[pointeur + 3]] = listeCode[listeCode[pointeur + 1]]
                        + listeCode[listeCode[pointeur + 2]];
                break;
            case 2:
                listeCode[listeCode[pointeur + 3]] = listeCode[listeCode[pointeur + 1]]
                        * listeCode[listeCode[pointeur + 2]];
                break;
            case 99:
                break mainLoop;
            default:
                System.err.println("Pas normal");
                break;
            }
            pointeur += 4;
        }
        return listeCode[0];
    }

}
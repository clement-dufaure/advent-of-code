package fr.dufaure.clement.adventofcode.event2019;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day7 {

    public static void main(String[] args) {
        part1();
        part2();
    }

    public static void part1() {
        List<List<Integer>> perms = generatePerm(
                IntStream.range(0, 5).mapToObj(i -> Integer.valueOf(i)).collect(Collectors.toList()));
        int maxOutput = 0;
        for (List<Integer> list : perms) {
            // create ampl
            List<Amplifier> amplifiers = new ArrayList<>(5);
            for (int i = 0; i < 5; i++) {
                amplifiers.add(new Amplifier(list.get(i)));
            }
            int input = 0;
            for (int i = 0; i < 5; i++) {
                input = amplifiers.get(i).runProgramme(input);
            }
            if (input > maxOutput) {
                maxOutput = input;
            }
        }
        System.out.println(maxOutput);
    }

    public static void part2() {
        List<List<Integer>> perms = generatePerm(
                IntStream.range(5, 10).mapToObj(i -> Integer.valueOf(i)).collect(Collectors.toList()));
        int maxOutput = 0;
        for (List<Integer> list : perms) {
            // create ampl
            List<Amplifier> amplifiers = new ArrayList<>(5);
            for (int i = 0; i < 5; i++) {
                amplifiers.add(new Amplifier(list.get(i)));
            }
            // run loop
            int input = 0;
            while (!amplifiers.get(0).stopped) {
                for (int i = 0; i < 5; i++) {
                    input = amplifiers.get(i).runProgramme(input);
                }
            }
            if (input > maxOutput) {
                maxOutput = input;
            }
        }
        System.out.println(maxOutput);
    }

    // thx to stackoverflow :)
    public static <T> List<List<T>> generatePerm(List<T> original) {
        if (original.size() == 0) {
            List<List<T>> result = new ArrayList<List<T>>();
            result.add(new ArrayList<T>());
            return result;
        }
        T firstElement = original.remove(0);
        List<List<T>> returnValue = new ArrayList<List<T>>();
        List<List<T>> permutations = generatePerm(original);
        for (List<T> smallerPermutated : permutations) {
            for (int index = 0; index <= smallerPermutated.size(); index++) {
                List<T> temp = new ArrayList<T>(smallerPermutated);
                temp.add(index, firstElement);
                returnValue.add(temp);
            }
        }
        return returnValue;
    }

    public static class Amplifier {
        String[] liste = ImportUtils.getString("./src/main/resources/2019/day7").split(",");
        boolean stopped = false;
        int pointeur = 0;
        int phase;
        boolean phaseSet = false;

        Amplifier(int phase) {
            this.phase = phase;
        }

        public int runProgramme(int input) {
            boolean inputSet = false;
            int output = 0;
            ArrayList<Integer> listeCode = new ArrayList<>();
            for (int i = 0; i < liste.length; i++) {
                listeCode.add(Integer.valueOf(liste[i]));
            }
            mainLoop: while (true) {
                int parameter1;
                int parameter2;
                int parameter3;
                switch (listeCode.get(pointeur) % 100) {
                case 1:
                    parameter1 = (listeCode.get(pointeur) % 1000) / 100 == 1 ? listeCode.get(pointeur + 1)
                            : listeCode.get(listeCode.get(pointeur + 1));
                    parameter2 = (listeCode.get(pointeur) % 10000) / 1000 == 1 ? listeCode.get(pointeur + 2)
                            : listeCode.get(listeCode.get(pointeur + 2));
                    parameter3 = listeCode.get(pointeur + 3);
                    listeCode.set(parameter3, parameter1 + parameter2);
                    pointeur += 4;
                    break;
                case 2:
                    parameter1 = (listeCode.get(pointeur) % 1000) / 100 == 1 ? listeCode.get(pointeur + 1)
                            : listeCode.get(listeCode.get(pointeur + 1));
                    parameter2 = (listeCode.get(pointeur) % 10000) / 1000 == 1 ? listeCode.get(pointeur + 2)
                            : listeCode.get(listeCode.get(pointeur + 2));
                    parameter3 = listeCode.get(pointeur + 3);
                    listeCode.set(parameter3, parameter1 * parameter2);
                    pointeur += 4;
                    break;
                case 3:
                    if (!phaseSet) {
                        // global first time : phase
                        listeCode.set(listeCode.get(pointeur + 1), phase);
                        phaseSet = true;
                    } else if (!inputSet) {
                        // first time of each call : input
                        listeCode.set(listeCode.get(pointeur + 1), input);
                        inputSet = true;
                    } else {
                        // waiting for next input
                        // => return current output and do not change pointer
                        return output;
                    }
                    pointeur += 2;
                    break;
                case 4:
                    output = listeCode.get(listeCode.get(pointeur + 1));
                    pointeur += 2;
                    break;
                case 5:
                    parameter1 = (listeCode.get(pointeur) % 1000) / 100 == 1 ? listeCode.get(pointeur + 1)
                            : listeCode.get(listeCode.get(pointeur + 1));
                    parameter2 = (listeCode.get(pointeur) % 10000) / 1000 == 1 ? listeCode.get(pointeur + 2)
                            : listeCode.get(listeCode.get(pointeur + 2));
                    if (parameter1 != 0) {
                        pointeur = parameter2;
                    } else {
                        pointeur += 3;
                    }
                    break;
                case 6:
                    parameter1 = (listeCode.get(pointeur) % 1000) / 100 == 1 ? listeCode.get(pointeur + 1)
                            : listeCode.get(listeCode.get(pointeur + 1));
                    parameter2 = (listeCode.get(pointeur) % 10000) / 1000 == 1 ? listeCode.get(pointeur + 2)
                            : listeCode.get(listeCode.get(pointeur + 2));
                    parameter3 = listeCode.get(pointeur + 3);
                    if (parameter1 == 0) {
                        pointeur = parameter2;
                    } else {
                        pointeur += 3;
                    }
                    break;
                case 7:
                    parameter1 = (listeCode.get(pointeur) % 1000) / 100 == 1 ? listeCode.get(pointeur + 1)
                            : listeCode.get(listeCode.get(pointeur + 1));
                    parameter2 = (listeCode.get(pointeur) % 10000) / 1000 == 1 ? listeCode.get(pointeur + 2)
                            : listeCode.get(listeCode.get(pointeur + 2));
                    parameter3 = listeCode.get(pointeur + 3);
                    listeCode.set(parameter3, parameter1 < parameter2 ? 1 : 0);
                    pointeur += 4;
                    break;
                case 8:
                    parameter1 = (listeCode.get(pointeur) % 1000) / 100 == 1 ? listeCode.get(pointeur + 1)
                            : listeCode.get(listeCode.get(pointeur + 1));
                    parameter2 = (listeCode.get(pointeur) % 10000) / 1000 == 1 ? listeCode.get(pointeur + 2)
                            : listeCode.get(listeCode.get(pointeur + 2));
                    parameter3 = listeCode.get(pointeur + 3);
                    listeCode.set(parameter3, parameter1 == parameter2 ? 1 : 0);
                    pointeur += 4;
                    break;
                case 99:
                    break mainLoop;
                default:
                    System.err.println("Pas normal");
                    break;
                }
            }
            // machine has stopped
            stopped = true;
            return output;
        }
    }

}
package fr.dufaure.clement.adventofcode.event2019;

import java.util.ArrayList;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day5 {

    public static void main(String[] args) {
        part1();
        part2();
    }

    public static void part1() {
        System.out.println(runProgramme(1));
    }

    public static void part2() {
        System.out.println(runProgramme(5));
    }

    public static String runProgramme(int id) {
        String[] liste = ImportUtils.getString("./src/main/resources/2019/day5").split(",");
        StringBuffer output = new StringBuffer();
        ArrayList<Integer> listeCode = new ArrayList<>();
        for (int i = 0; i < liste.length; i++) {
            listeCode.add(Integer.valueOf(liste[i]));
        }

        int pointeur = 0;
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
                listeCode.set(listeCode.get(pointeur + 1), id);
                pointeur += 2;
                break;
            case 4:
                output.append(listeCode.get(listeCode.get(pointeur + 1)) + "\n");
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
        return output.toString();
    }

}
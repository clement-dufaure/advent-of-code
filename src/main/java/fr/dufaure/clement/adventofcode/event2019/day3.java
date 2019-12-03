package fr.dufaure.clement.adventofcode.event2019;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day3 {

    public static void main(String[] args) {
        long start1 = System.currentTimeMillis();
        part1();
        System.out.println("Execution part 1 en " + (System.currentTimeMillis() - start1) + " ms");
        long start2 = System.currentTimeMillis();
        part2();
        System.out.println("Execution part 2 en " + (System.currentTimeMillis() - start2) + " ms");
    }

    public static void part1() {
        List<String> liste = ImportUtils.getListStringUnParLigne("./src/main/resources/2019/day3");
        List<Coord> wire1 = drawWire(liste.get(0).split(","));
        List<Coord> wire2 = drawWire(liste.get(1).split(","));
        wire1.retainAll(wire2);
        Collections.sort(wire1);
        System.out.println(Math.abs(wire1.get(0).x) + Math.abs(wire1.get(0).y));
    }

    public static void part2() {
        List<String> liste = ImportUtils.getListStringUnParLigne("./src/main/resources/2019/day3");
        List<Coord> wire1 = drawWire(liste.get(0).split(","));
        List<Coord> wire2 = drawWire(liste.get(1).split(","));
        List<Coord> wire1Copy = new ArrayList<>(wire1.size());
        wire1Copy.addAll(wire1);
        wire1.retainAll(wire2);
        wire2.retainAll(wire1Copy);
        int minimum = Integer.MAX_VALUE;
        for (Coord coord1 : wire1) {
            for (Coord coord2 : wire2) {
                if (coord1.equals(coord2)) {
                    int numberTotalOfSteps = coord1.numberOfSteps + coord2.numberOfSteps;
                    if (numberTotalOfSteps < minimum) {
                        minimum = numberTotalOfSteps;
                    }
                }
            }
        }
        System.out.println(minimum);

    }

    static List<Coord> drawWire(String[] parcours) {
        List<Coord> coords = new ArrayList<>();
        Coord positionCourante = new Coord(0, 0, 0);
        int numberOfSteps = 0;
        // coords.add(new Coord(0, 0, numberOfSteps));
        for (String step : parcours) {
            int distance = Integer.valueOf(step.substring(1));
            for (int i = 0; i < distance; i++) {
                numberOfSteps++;
                switch (step.charAt(0)) {
                case 'U':
                    positionCourante.y++;
                    break;
                case 'D':
                    positionCourante.y--;
                    break;
                case 'L':
                    positionCourante.x--;
                    break;
                case 'R':
                    positionCourante.x++;
                    break;
                default:
                    System.err.println("Problem");
                }

                coords.add(new Coord(positionCourante.x, positionCourante.y, numberOfSteps));
            }
        }
        return coords;
    }

    static class Coord implements Comparable<Coord> {
        int x;
        int y;
        int numberOfSteps;

        // Coord(int x, int y) {
        // this.x = x;
        // this.y = y;
        // }

        Coord(int x, int y, int numberOfSteps) {
            this.x = x;
            this.y = y;
            this.numberOfSteps = numberOfSteps;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && obj instanceof Coord) {
                Coord castObj = (Coord) obj;
                return this.x == castObj.x && this.y == castObj.y;
            }
            return false;
        }

        // @Override
        // public int compareTo(Object obj) {

        // if (obj != null && obj instanceof Coord) {
        // Coord castObj = (Coord) obj;
        // if (this.x < castObj.x) {
        // return -1;
        // } else if (this.x > castObj.x) {
        // return 1;
        // } else {
        // if (this.y < castObj.y) {
        // return -1;
        // } else if (this.y > castObj.y) {
        // return 1;
        // } else {
        // return 0;
        // }
        // }
        // }
        // throw new UnsupportedOperationException();
        // }

        @Override
        public int compareTo(Coord obj) {

            if (obj != null) {
                Coord castObj = (Coord) obj;
                if (Math.abs(this.x) + Math.abs(this.y) < Math.abs(castObj.x) + Math.abs(castObj.y)) {
                    return -1;
                }
                if (Math.abs(this.x) + Math.abs(this.y) > Math.abs(castObj.x) + Math.abs(castObj.y)) {
                    return 1;
                }
                if (Math.abs(this.x) + Math.abs(this.y) == Math.abs(castObj.x) + Math.abs(castObj.y)) {
                    return 0;
                }
            }
            throw new UnsupportedOperationException();
        }

        @Override
        public String toString() {
            return this.x + "-" + this.y;
        }
    }

}
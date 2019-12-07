package fr.dufaure.clement.adventofcode.event2016;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

public class day8 {

    public static void main(String[] args) throws Exception {
        long start1 = System.currentTimeMillis();
        part1();
        System.out.println("Execution part 1 en " + (System.currentTimeMillis() - start1) + " ms");
        long start2 = System.currentTimeMillis();
        part2();
        System.out.println("Execution part 2 en " + (System.currentTimeMillis() - start2) + " ms");
    }

    static boolean[][] screen = new boolean[6][50];

    static void part1() throws IOException {
        Files.lines(Paths.get("./src/main/resources/2016/day8")).forEach(day8::apply);
        System.out.println(count());
    }

    static void apply(String instruction) {
        String[] instructions = instruction.split(" ");
        switch (instructions[0]) {
        case "rect":
            int[] dimensions = Arrays.asList(instructions[1].split("x")).stream().mapToInt(s -> Integer.parseInt(s))
                    .toArray();
            applyRect(dimensions[0], dimensions[1]);
            break;
        case "rotate":
            applyRotate(instructions[1], Integer.parseInt(instructions[2].split("=")[1]),
                    Integer.parseInt(instructions[4]));
            break;
        default:
            break;
        }
        display();
    }

    static void applyRect(int wide, int tall) {
        for (int x = 0; x < wide; x++) {
            for (int y = 0; y < tall; y++) {
                screen[y][x] = true;
            }
        }
    }

    static void applyRotate(String type, int rowOrColum, int by) {
        switch (type) {
        case "row":
            applyRotateRow(rowOrColum, by);
            break;
        case "column":
            applyRotateColumn(rowOrColum, by);
            break;
        default:
            break;
        }
    }

    static void applyRotateRow(int row, int by) {
        boolean[] line = screen[row].clone();
        for (int i = 0; i < screen[row].length; i++) {
            screen[row][i] = line[(i - by + 2 * line.length) % line.length];
        }
    }

    static void applyRotateColumn(int column, int by) {
        Object[] colonneObject = Arrays.asList(screen).stream().map(row -> row[column]).collect(Collectors.toList())
                .toArray();
        Boolean[] colonne = Arrays.copyOf(colonneObject, colonneObject.length, Boolean[].class);
        for (int i = 0; i < screen.length; i++) {
            screen[i][column] = colonne[(i - by + 2 * colonne.length) % colonne.length];
        }
    }

    static int count() {
        int count = 0;
        for (boolean[] ligne : screen) {
            for (boolean pixel : ligne) {
                if (pixel) {
                    count++;
                }
            }
        }
        return count;
    }

    static void display() {
        for (boolean[] ligne : screen) {
            for (boolean pixel : ligne) {
                System.out.print(pixel ? "#" : ".");
            }
            System.out.println();
        }
        System.out.println();
    }

    static void part2() {

    }

}
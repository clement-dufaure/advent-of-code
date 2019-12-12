package fr.dufaure.clement.adventofcode.event2015;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day23 {

    public static void main(String[] args) {
        long start1 = System.currentTimeMillis();
        part1();
        System.out.println("Execution part 1 en " + (System.currentTimeMillis() - start1) + " ms");
        long start2 = System.currentTimeMillis();
        part2();
        System.out.println("Execution part 2 en " + (System.currentTimeMillis() - start2) + " ms");
    }

    static int registerA = 0;
    static int registerB = 0;

    static List<Instruction> programme = new ArrayList<>();

    static void initialize() {
        List<String> liste = ImportUtils.getListStringUnParLigne("./src/main/resources/2015/day23");
        Pattern p = Pattern.compile("([a-z]{3}) ([ab]|[+-]\\d+),? ?([+-]\\d+)?");
        for (String ligne : liste) {
            Matcher matcher = p.matcher(ligne);
            if (matcher.matches()) {
                if (matcher.groupCount() == 2) {
                    if (matcher.group(2).equals("a") || matcher.group(2).equals("b")) {
                        programme.add(new Instruction(TypeInstruction.valueOf(matcher.group(1)),
                                Register.valueOf(matcher.group(2))));
                    } else {
                        programme.add(new Instruction(TypeInstruction.valueOf(matcher.group(1)),
                                Integer.parseInt(matcher.group(2))));
                    }
                }

                else if (matcher.groupCount() == 3) {
                    programme.add(new Instruction(TypeInstruction.valueOf(matcher.group(1)),
                            Register.valueOf(matcher.group(2)), Integer.parseInt(matcher.group(3))));
                } else {
                    System.err.println("Problem");
                }
            } else {
                System.err.println("Ligne non lue");
            }
        }

    }

    private static void part1() {
    }

    private static void part2() {
    }

    static enum Register {
        a, b;

        int value = 0;
    }

    static class Instruction {

        TypeInstruction typeInstruction;
        Register register;
        int offset;

        Instruction(TypeInstruction typeInstruction, Register r) {
            this.typeInstruction = typeInstruction;
            this.register = r;
        }

        Instruction(TypeInstruction typeInstruction, int offset) {
            this.typeInstruction = typeInstruction;
            this.offset = offset;
        }

        Instruction(TypeInstruction typeInstruction, Register r, int offset) {
            this.typeInstruction = typeInstruction;
            this.register = r;
            this.offset = offset;
        }

    }

    static enum TypeInstruction {
        hlf, tpl, inc, jmp, jie, jio;
    }

}
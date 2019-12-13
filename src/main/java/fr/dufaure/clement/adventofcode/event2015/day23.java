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

    // static int registerA = 0;
    // static int registerB = 0;

    static List<Instruction> programme = new ArrayList<>();

    static {
        initialize();
    }

    static void initialize() {
        List<String> liste = ImportUtils.getListStringUnParLigne("./src/main/resources/2015/day23");
        Pattern p = Pattern.compile("([a-z]{3}) ([ab]|[+-]\\d+),? ?([+-]\\d+)?");
        for (String ligne : liste) {
            Matcher matcher = p.matcher(ligne);
            if (matcher.matches()) {
                if (matcher.group(3) == null) {
                    if (matcher.group(2).equals("a") || matcher.group(2).equals("b")) {
                        programme.add(new Instruction(TypeInstruction.valueOf(matcher.group(1)),
                                Register.valueOf(matcher.group(2))));
                    } else {
                        programme.add(new Instruction(TypeInstruction.valueOf(matcher.group(1)),
                                Integer.parseInt(matcher.group(2))));
                    }
                }

                else {
                    programme.add(new Instruction(TypeInstruction.valueOf(matcher.group(1)),
                            Register.valueOf(matcher.group(2)), Integer.parseInt(matcher.group(3))));
                }
            } else {
                System.err.println("Ligne non lue");
            }
        }

    }

    private static void part1() {
        int pointeur = 0;
        while (pointeur >= 0 && pointeur < programme.size()) {
            switch (programme.get(pointeur).typeInstruction) {
            case hlf:
                programme.get(pointeur).register.value = programme.get(pointeur).register.value / 2;
                pointeur++;
                break;
            case tpl:
                programme.get(pointeur).register.value = programme.get(pointeur).register.value * 3;
                pointeur++;
                break;
            case inc:
                programme.get(pointeur).register.value++;
                pointeur++;
                break;
            case jmp:
                pointeur += programme.get(pointeur).offset;
                break;
            case jie:
                if (programme.get(pointeur).register.value % 2 == 0) {
                    pointeur += programme.get(pointeur).offset;
                } else {
                    pointeur++;
                }
                break;
            case jio:
                if (programme.get(pointeur).register.value == 1) {
                    pointeur += programme.get(pointeur).offset;
                } else {
                    pointeur++;
                }
                break;
            default:
                break;
            }
        }
        System.out.println(Register.b.value);
    }

    private static void part2() {
        Register.a.value = 1;
        Register.b.value = 0;
        part1();
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
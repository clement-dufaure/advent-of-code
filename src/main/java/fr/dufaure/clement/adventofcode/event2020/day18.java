package fr.dufaure.clement.adventofcode.event2020;

import java.util.ArrayList;
import java.util.List;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day18 {
    public static void main(String[] args) {
        part1();
        part2();
    }

    private static void part1() {
        List<String> liste = ImportUtils.getListStringUnParLigne("./src/main/resources/2020/day18");
        System.out.println(liste.stream().map(s -> s.replace(" ", "")).mapToLong(day18::calculateParenthesis).sum());
    }

    private static void part2() {
        List<String> liste = ImportUtils.getListStringUnParLigne("./src/main/resources/2020/day18");
        System.out.println(
                liste.stream().map(s -> s.replace(" ", "")).mapToLong(day18::calculateParenthesisAddPriority).sum());
    }

    static long calculateParenthesis(String str) {
        int i = 0;
        long total = 0;
        char operation = '+';
        while (i < str.length()) {
            if (str.charAt(i) == '(') {
                StringBuffer inParenthesis = new StringBuffer();
                int count = 1;
                i++;
                while (true) {
                    char c = str.charAt(i);
                    if (c == '(') {
                        count++;
                    }
                    if (c == ')') {
                        count--;
                        if (count == 0) {
                            i++;
                            break;
                        }
                    }
                    inParenthesis.append(c);
                    i++;
                }
                long v = calculateParenthesis(inParenthesis.toString());
                total = operation == '+' ? total + v : total * v;

            } else if (Character.isDigit(str.charAt(i))) {
                total = operation == '+' ? total + Long.parseLong("" + str.charAt(i))
                        : total * Long.parseLong("" + str.charAt(i));
                i++;
            } else {
                if (str.charAt(i) != '+' && str.charAt(i) != '*') {
                    System.err.println(str.charAt(i));
                    throw new UnsupportedOperationException();
                }
                operation = str.charAt(i);
                i++;
            }
        }
        return total;
    }

    static long calculateParenthesisAddPriority(String str) {
        int i = 0;
        List<Character> operations = new ArrayList<>();
        List<Long> operandes = new ArrayList<>();
        while (i < str.length()) {
            if (str.charAt(i) == '(') {
                StringBuffer inParenthesis = new StringBuffer();
                int count = 1;
                i++;
                while (true) {
                    char c = str.charAt(i);
                    if (c == '(') {
                        count++;
                    }
                    if (c == ')') {
                        count--;
                        if (count == 0) {
                            i++;
                            break;
                        }
                    }
                    inParenthesis.append(c);
                    i++;
                }
                operandes.add(calculateParenthesisAddPriority(inParenthesis.toString()));
            } else if (Character.isDigit(str.charAt(i))) {
                operandes.add(Long.parseLong("" + str.charAt(i)));
                i++;
            } else {
                if (str.charAt(i) != '+' && str.charAt(i) != '*') {
                    System.err.println(str.charAt(i));
                    throw new UnsupportedOperationException();
                }
                operations.add(str.charAt(i));
                i++;
            }
        }

        // caculate with preference for +
        while (true) {
            if (operations.contains('+')) {
                int where = operations.indexOf('+');
                long sum = operandes.get(where) + operandes.get(where + 1);
                operations.remove(where);
                operandes.set(where, sum);
                operandes.remove(where + 1);
            } else {
                break;
            }
        }

        // multiply the rest
        return operandes.stream().reduce(1L, (a, b) -> a * b);
    }

}

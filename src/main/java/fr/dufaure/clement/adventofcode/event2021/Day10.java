package fr.dufaure.clement.adventofcode.event2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import fr.dufaure.clement.adventofcode.utils.Day;
import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class Day10 implements Day {

    public static final List<Character> OUVRANTS = Arrays.asList('(', '{', '[', '<');
    public static final List<Character> FERMANTS = Arrays.asList(')', '}', ']', '>');

    @Override
    public String part1(String inputPath) {
        var lignes = ImportUtils.getListStringUnParLigne(inputPath);
        var errors = new HashMap<Character, Integer>();
        for (var ligne : lignes) {
            LinkedList<Character> sequence = new LinkedList<Character>();
            loop: for (char c : ligne.toCharArray()) {
                if (OUVRANTS.contains(c)) {
                    sequence.add(c);
                } else if (FERMANTS.contains(c)) {
                    char last = sequence.removeLast();
                    var lastAttendu = switch (c) {
                        case '}' -> '{';
                        case ']' -> '[';
                        case ')' -> '(';
                        case '>' -> '<';
                        default -> throw new UnsupportedOperationException();
                    };
                    if (last != lastAttendu) {
                        errors.putIfAbsent(c, 0);
                        errors.computeIfPresent(c, (k, i) -> i + 1);
                        break loop;
                    }
                } else {
                    throw new UnsupportedOperationException();
                }
            }
        }

        // ): 3 points.
        // ]: 57 points.
        // }: 1197 points.
        // >: 25137 points.

        var errors1 = errors.get(')') * 3;
        var errors2 = errors.get(']') * 57;
        var errors3 = errors.get('}') * 1197;
        var errors4 = errors.get('>') * 25137;
        return String.valueOf(errors1 + errors2 + errors3 + errors4);
    }

    @Override
    public String part2(String inputPath) {
        var lignes = ImportUtils.getListStringUnParLigne(inputPath);
        var scores = new ArrayList<Long>();
        loop: for (var ligne : lignes) {
            LinkedList<Character> sequence = new LinkedList<Character>();
            for (char c : ligne.toCharArray()) {
                if (OUVRANTS.contains(c)) {
                    sequence.add(c);
                } else if (FERMANTS.contains(c)) {
                    char last = sequence.removeLast();
                    var lastAttendu = switch (c) {
                        case '}' -> '{';
                        case ']' -> '[';
                        case ')' -> '(';
                        case '>' -> '<';
                        default -> throw new UnsupportedOperationException();
                    };
                    if (last != lastAttendu) {
                        continue loop;
                    }
                } else {
                    throw new UnsupportedOperationException();
                }
            }
            // la ligne est bonne, on complete
            if (sequence.size() > 0) {
                var aRajouter = new LinkedList<>(sequence.stream().map(c -> getComplement(c)).toList());
                long count = 0L;
                while (!aRajouter.isEmpty()) {
                    count *= 5L;
                    count += aRajouter.removeLast();
                }
                scores.add(count);
            }
        }
        var scoresTries = scores.stream().mapToLong(i -> i).sorted().boxed().toList();
        return String.valueOf(scoresTries.get(scoresTries.size() / 2));
    }

    // ): 1 point.
    // ]: 2 points.
    // }: 3 points.
    // >: 4 points.

    private int getComplement(char c) {
        return switch (c) {
            case '{' -> 3;
            case '(' -> 1;
            case '[' -> 2;
            case '<' -> 4;
            default -> throw new UnsupportedOperationException();
        };
    }

}
package fr.dufaure.clement.adventofcode.event2021;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import fr.dufaure.clement.adventofcode.utils.Day;
import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class Day8 implements Day {

    private DigitsEntry parse(String str) {
        var parts = str.split("\\|");
        return new DigitsEntry(Arrays.asList(parts[0].trim().split(" ")), Arrays.asList(parts[1].trim().split(" ")));
    }

    private boolean hasSpecificSize(String s) {
        return switch (s.length()) {
            case 2, 3, 4, 7 -> true;
            case 5, 6 -> false;
            default -> throw new UnsupportedOperationException();
        };
    }

    @Override
    public String part1(String inputPath) {
        var listDigitsEntry = ImportUtils.getListStringUnParLigne(inputPath).stream().map(s -> parse(s)).toList();
        var c = listDigitsEntry.stream().map(de -> de.display).flatMap(l -> l.stream()).filter(s -> hasSpecificSize(s))
                .count();
        return String.valueOf(c);
    }

    @Override
    public String part2(String inputPath) {
        var listDigitsEntry = ImportUtils.getListStringUnParLigne(inputPath).stream().map(s -> parse(s)).toList();
        var sum = 0;
        for (var digitsEntry : listDigitsEntry) {
            var m = getConfigurationMapping(digitsEntry);
            var sb = new StringBuffer();
            for (var s : digitsEntry.display) {
                sb.append(Digit.getValue(s, m));
            }
            sum += Integer.valueOf(sb.toString());
        }
        return String.valueOf(sum);
    }

    private record DigitsEntry(List<String> digits, List<String> display) {
    }

    // .dddd
    // e....a
    // e....a
    // .ffff
    // g....b
    // g....b
    // .cccc

    private Map<Character, Character> getConfigurationMapping(DigitsEntry digitsEntry) {
        var sept = digitsEntry.digits.stream().filter(s -> s.length() == 3).findAny().get();
        var un = digitsEntry.digits.stream().filter(s -> s.length() == 2).findAny().get();
        var quatre = digitsEntry.digits.stream().filter(s -> s.length() == 4).findAny().get();

        // find a
        var a = removeString(sept, un);

        // find neuf
        var withSix = digitsEntry.digits.stream().filter(s -> s.length() == 6).toList();
        var neuf = thatIncludeString(withSix, quatre);

        // find g
        var g = removeString(neuf, quatre + a);

        // find e
        var e = removeString("abcdefg", neuf);

        // find trois
        var withCinq = digitsEntry.digits.stream().filter(s -> s.length() == 5).toList();
        var trois = thatIncludeString(withCinq, un);

        // find d
        var d = removeString(trois, un + a + g);

        // find b
        var b = removeString(quatre, un + d);

        // find cinq
        var cinq = thatIncludeString(withCinq, b);

        // find f
        var f = removeString(cinq, a + b + d + g);

        // finally find c
        var c = removeString("abcdefg", a + b + d + e + f + g);

        var m = new HashMap<Character, Character>();
        m.put(a.charAt(0), 'a');
        m.put(b.charAt(0), 'b');
        m.put(c.charAt(0), 'c');
        m.put(d.charAt(0), 'd');
        m.put(e.charAt(0), 'e');
        m.put(f.charAt(0), 'f');
        m.put(g.charAt(0), 'g');
        return m;
    }

    private String removeString(String source, String toRemove) {
        var l = toRemove.chars().mapToObj(i -> (char) i).collect(Collectors.toList());
        var sourceArray = source.chars().mapToObj(i -> (char) i).collect(Collectors.toList());
        sourceArray.removeAll(l);
        return sourceArray.stream().map(c -> String.valueOf(c)).collect(Collectors.joining());
    }

    private String thatIncludeString(List<String> sources, String subStringToFind) {
        var l = subStringToFind.chars().mapToObj(i -> (char) i).collect(Collectors.toList());
        for (var s : sources) {
            var sourceArray = s.chars().mapToObj(i -> (char) i).collect(Collectors.toList());
            if (sourceArray.containsAll(l)) {
                return s;
            }
        }
        throw new UnsupportedOperationException();
    }

    private enum Digit {
        ZERO("abcefg", 0), UN("cf", 1), DEUX("acdeg", 2), TROIS("acdfg", 3), QUATRE("bcdf", 4), CINQ("abdfg", 5),
        SIX("abdefg", 6),
        SEPT("acf", 7), HUIT("abcdefg", 8), NEUF("abcdfg", 9);

        private String chaine;
        private int chiffre;

        private Digit(String chaine, int chiffre) {
            this.chaine = chaine;
            this.chiffre = chiffre;
        }

        public static int getValue(String affichage, Map<Character, Character> mapping) {
            var sb = new StringBuffer();
            for (var c : affichage.toCharArray()) {
                sb.append(mapping.get(c));
            }
            return getValue(sb.toString());
        }

        public static int getValue(String chaine) {
            var chaineTriee = chaine.chars().mapToObj(i -> (char) i).sorted().map(c -> String.valueOf(c))
                    .collect(Collectors.joining());

            for (var digit : values()) {
                if (chaineTriee.equals(digit.chaine)) {
                    return digit.chiffre;
                }
            }
            throw new UnsupportedOperationException();
        }
    }

}

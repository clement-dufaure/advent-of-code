package fr.dufaure.clement.adventofcode.event2021;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import fr.dufaure.clement.adventofcode.utils.Day;
import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class Day14 implements Day {

    @Override
    public String part1(String inputPath) {
        return doTheJob(inputPath, 10);
    }

    @Override
    public String part2(String inputPath) {
        return doTheJob(inputPath, 40);
    }

    private String doTheJob(String inputPath, int numberOfIteration) {
        var initialState = ImportUtils.getListStringUnParLigne(inputPath).get(0);
        var reactions = readReactions(ImportUtils.getStringWithNewLine(inputPath));

        var couples = new HashMap<String, Long>();
        for (int i = 0; i < initialState.length() - 1; i++) {
            couples.put("" + initialState.charAt(i) + initialState.charAt(i + 1), 1L);
        }

        for (var i = 0; i < numberOfIteration; i++) {
            var couplesFigees = new HashMap<>(couples);
            for (var c : couplesFigees.keySet()) {
                var nombre = couplesFigees.get(c);
                String result = reactions.get(c);
                couples.compute(c, (k, v) -> v - nombre);
                couples.computeIfPresent(c.charAt(0) + result, (k, v) -> v + nombre);
                couples.computeIfAbsent(c.charAt(0) + result, k -> nombre);
                couples.computeIfPresent(result + c.charAt(1), (k, v) -> v + nombre);
                couples.computeIfAbsent(result + c.charAt(1), k -> nombre);
            }
        }

        var lettres = couples.entrySet().stream().flatMap(e -> {
            var l = new HashMap<Character, Long>();
            l.put(e.getKey().charAt(0), e.getValue());
            // l.put(e.getKey().charAt(1), e.getValue());
            return l.entrySet().stream();
        }).collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue(), (v, vnew) -> v + vnew));
        lettres.computeIfPresent(initialState.charAt(initialState.length() - 1), (k, v) -> v + 1);
        lettres.computeIfAbsent(initialState.charAt(initialState.length() - 1), k -> 1L);
        var min = lettres.values().stream().mapToLong(i -> i).min().getAsLong();
        var max = lettres.values().stream().mapToLong(i -> i).max().getAsLong();
        return String.valueOf(max - min);
    }

    private Map<String, String> readReactions(String s) {
        var map = new HashMap<String, String>();
        var p = Pattern.compile("([A-Z]{2}) -> ([A-Z])");
        var m = p.matcher(s);
        while (m.find()) {
            map.put(m.group(1), m.group(2));
        }
        return map;
    }

}

package fr.dufaure.clement.adventofcode.event2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day14 {

    public static void main(String[] args) {
        part1();
        part2();
    }

    static Map<Long, Long> memory = new HashMap<>();

    static Pattern patternMask = Pattern.compile("mask = ([01X]*)");
    static Pattern patternAffectation = Pattern.compile("mem\\[([0-9]*)\\] = ([0-9]*)");

    public static void part1() {
        List<String> data = ImportUtils.getListStringUnParLigne("./src/main/resources/2020/day14");
        String maskApplied = "";
        for (String line : data) {
            Matcher m = patternMask.matcher(line);
            if (m.matches()) {
                maskApplied = m.group(1);
            } else {
                m = patternAffectation.matcher(line);
                m.matches();
                memory.put(Long.valueOf(m.group(1)), applyMask(Long.valueOf(m.group(2)), maskApplied));
            }
        }
        System.out.println(memory.values().stream().mapToLong(l -> l).sum());

    }

    public static void part2() {
        memory = new HashMap<>();
        List<String> data = ImportUtils.getListStringUnParLigne("./src/main/resources/2020/day14");
        String maskApplied = "";
        for (String line : data) {
            Matcher m = patternMask.matcher(line);
            if (m.matches()) {
                maskApplied = m.group(1);
            } else {
                m = patternAffectation.matcher(line);
                m.matches();
                String floatingAdress = applyMaskAdress(Long.valueOf(m.group(1)), maskApplied);
                List<Integer> indexesOfX = findX(floatingAdress);
                int numberOfX = indexesOfX.size();
                for (long i = 0; i < (1 << numberOfX); i++) {
                    String lString = Long.toBinaryString(i);
                    // padding
                    lString = "0".repeat(numberOfX - lString.length()) + lString;
                    StringBuffer address = new StringBuffer(floatingAdress);
                    for (int k = 0; k < indexesOfX.size(); k++) {
                        address.setCharAt(indexesOfX.get(k), lString.charAt(k));
                    }
                    memory.put(Long.valueOf(address.toString(), 2), Long.valueOf(m.group(2)));
                }
            }
        }
        System.out.println(memory.values().stream().mapToLong(l -> l).sum());

    }

    static long applyMask(long l, String mask) {
        StringBuffer lString = new StringBuffer(Long.toBinaryString(l));
        // add 0 padding front
        lString.insert(0, "0".repeat(mask.length() - lString.length()));
        for (int i = 0; i < mask.length(); i++) {
            char c = mask.charAt(i);
            switch (c) {
                case 'X':
                    continue;
                case '1':
                    lString.setCharAt(i, '1');
                    break;
                case '0':
                    lString.setCharAt(i, '0');
                    break;
                default:
                    throw new UnsupportedOperationException();
            }
        }
        return Long.valueOf(lString.toString(), 2);
    }

    static String applyMaskAdress(long l, String mask) {
        StringBuffer lString = new StringBuffer(Long.toBinaryString(l));
        // add 0 padding front
        lString.insert(0, "0".repeat(mask.length() - lString.length()));
        for (int i = 0; i < mask.length(); i++) {
            char c = mask.charAt(i);
            switch (c) {
                case 'X':
                    lString.setCharAt(i, 'X');
                    break;
                case '1':
                    lString.setCharAt(i, '1');
                    break;
                case '0':
                    continue;
                default:
                    throw new UnsupportedOperationException();
            }
        }
        return lString.toString();
    }

    static List<Integer> findX(String str) {
        List<Integer> indexes = new ArrayList<>();
        int ref = 0;
        while (true) {
            int index = str.indexOf('X', ref);
            if (index == -1) {
                break;
            } else {
                indexes.add(index);
                ref = index + 1;
            }
        }
        return indexes;
    }

}

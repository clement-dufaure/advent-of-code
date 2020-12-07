package fr.dufaure.clement.adventofcode.event2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day7 {

    public static void main(String[] args) {
        part1();
        part2();
    }

    public static void part1() {
        List<String> liste = ImportUtils.getListStringUnParLigne("./src/main/resources/2020/day7");
        liste.stream().forEach(day7::addInformation);
        System.out.println(reverseInclusionAggregation("shiny gold").stream().distinct().count());
    }

    public static void part2() {
        List<String> liste = ImportUtils.getListStringUnParLigne("./src/main/resources/2020/day7");
        liste.stream().forEach(day7::addInformation);
        System.out.println(countTotalBagInside("shiny gold"));
    }

    // impossible to find a global regex because of unknown number of capturing
    // group
    // static String regexPattern = "([a-z]* [a-z]*) bags contain (?:(?:([0-9]*
    // [a-z]* [a-z]*) bags?[,.] ?)|(?:no other bags))*";

    // list of bag in a bag
    static Map<String, List<Inclusion>> inclusions = new HashMap<>();
    // list of bags can contain a bag
    static Map<String, List<String>> reverseInclusions = new HashMap<>();;

    static void addInformation(String information) {
        String[] informations = information.split(" bags contain ");
        if (informations[1].equals("no other bags.")) {
            // do nothing ?
        } else {
            inclusions.put(informations[0], new ArrayList<>());
            String bagsContainedWithNumber[] = informations[1].split(" bags?[,.] ?");
            for (String s : bagsContainedWithNumber) {
                Inclusion i = new Inclusion();
                i.number = Integer.parseInt(s.split(" ", 2)[0]);
                i.bagIncluded = s.split(" ", 2)[1];
                inclusions.get(informations[0]).add(i);
                reverseInclusions.computeIfAbsent(i.bagIncluded, noimportant -> new ArrayList<>());
                reverseInclusions.get(i.bagIncluded).add(informations[0]);
            }
        }
    }

    static class Inclusion {
        String bagIncluded;
        long number;

    }

    static List<String> reverseInclusionAggregation(String color) {
        List<String> result = new ArrayList<>();
        List<String> parents = reverseInclusions.get(color);
        if (parents != null) {
            result.addAll(parents);
            result.addAll(parents.stream().map(day7::reverseInclusionAggregation).flatMap(List::stream)
                    .collect(Collectors.toList()));
        }
        return result;
    }

    static long countTotalBagInside(String color) {
        List<Inclusion> liste = inclusions.get(color);
        return liste == null ? 0
                : liste.stream().mapToLong(incl -> incl.number * (1 + countTotalBagInside(incl.bagIncluded))).sum();
    }

}

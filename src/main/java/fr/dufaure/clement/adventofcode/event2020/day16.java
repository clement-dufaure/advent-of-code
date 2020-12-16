package fr.dufaure.clement.adventofcode.event2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day16 {

    public static void main(String[] args) {
        part1();
        // part2(); included in part1
    }

    public static void part1() {
        List<String> liste = ImportUtils.getListStringUnParLigne("./src/main/resources/2020/day16");

        List<Rule> rules = new ArrayList<>();
        List<Plausible> myTicket = new ArrayList<>();
        List<List<Plausible>> tickets = new ArrayList<>();

        int i = 0;
        Pattern rulePattern = Pattern.compile("([a-z ]*): ([0-9]*)-([0-9]*) or ([0-9]*)-([0-9]*)");
        while (!liste.get(i).equals("")) {
            Matcher m = rulePattern.matcher(liste.get(i));
            m.matches();
            Rule r = new Rule();
            r.field = m.group(1);
            r.firstMin = Integer.parseInt(m.group(2));
            r.firstMax = Integer.parseInt(m.group(3));
            r.secondMin = Integer.parseInt(m.group(4));
            r.secondMax = Integer.parseInt(m.group(5));
            rules.add(r);
            i++;
        }
        i += 2;
        myTicket = Arrays.asList(liste.get(i).split(",")).stream().map(s -> Integer.parseInt(s)).map(Plausible::new)
                .collect(Collectors.toList());
        i += 3;
        while (i < liste.size()) {
            tickets.add(Arrays.asList(liste.get(i).split(",")).stream().map(s -> Integer.parseInt(s))
                    .map(Plausible::new).collect(Collectors.toList()));
            i++;
        }

        tickets.stream().flatMap(l -> l.stream()).forEach(p -> checkField(p, rules));

        // check
        System.out.println(tickets.stream().flatMap(l -> l.stream()).filter(p -> p.plausibleFields.isEmpty())
                .mapToInt(p -> p.fieldValue).sum());

        // remove bad tickets
        tickets = tickets.stream().filter(day16::checkTicket).collect(Collectors.toList());

        // init
        Map<Integer, List<String>> plausiblesFieldsByNumber = new HashMap<>();
        List<String> fields = rules.stream().map(r -> r.field).collect(Collectors.toList());
        for (int fieldNumber = 0; fieldNumber < rules.size(); fieldNumber++) {
            plausiblesFieldsByNumber.put(fieldNumber, new ArrayList<>(fields));
        }

        Map<Integer, String> fieldsByNumber = new HashMap<>();
        while (true) {
            for (List<Plausible> ticket : tickets) {
                for (int fieldNumber = 0; fieldNumber < rules.size(); fieldNumber++) {
                    for (String field : fields) {
                        if (plausiblesFieldsByNumber.containsKey(fieldNumber)
                                && !ticket.get(fieldNumber).plausibleFields.contains(field)) {
                            plausiblesFieldsByNumber.get(fieldNumber).remove(field);
                        }
                    }
                }
            }

            for (int fieldNumber = 0; fieldNumber < rules.size(); fieldNumber++) {
                if (plausiblesFieldsByNumber.containsKey(fieldNumber)
                        && plausiblesFieldsByNumber.get(fieldNumber).size() == 1) {
                    String fieldFind = plausiblesFieldsByNumber.get(fieldNumber).get(0);
                    fieldsByNumber.put(fieldNumber, fieldFind);
                    plausiblesFieldsByNumber.remove(fieldNumber);
                    tickets.stream().flatMap(l -> l.stream()).forEach(p -> p.plausibleFields.remove(fieldFind));
                }
            }

            if (plausiblesFieldsByNumber.entrySet().size() == 0) {
                break;
            }

        }
        System.out.println(fieldsByNumber);

        long result = 1L;
        for (int fieldNumber = 0; fieldNumber < rules.size(); fieldNumber++) {
            if (fieldsByNumber.get(fieldNumber).startsWith("departure")) {
                result *= myTicket.get(fieldNumber).fieldValue;
            }
        }

        System.out.println(result);
    }

    static void checkField(Plausible p, List<Rule> rules) {
        List<String> plausibleFields = new ArrayList<>();
        for (Rule r : rules) {
            if ((p.fieldValue >= r.firstMin && p.fieldValue <= r.firstMax)
                    || (p.fieldValue >= r.secondMin && p.fieldValue <= r.secondMax)) {
                plausibleFields.add(r.field);
            }
            p.plausibleFields = plausibleFields;
        }
    }

    static boolean checkTicket(List<Plausible> ticket) {
        return ticket.stream().filter(p -> p.plausibleFields.isEmpty()).count() == 0;
    }

    static class Rule {
        String field;
        int firstMin;
        int firstMax;
        int secondMin;
        int secondMax;
    }

    static class Plausible {
        int fieldValue;
        List<String> plausibleFields = new ArrayList<>();

        Plausible(int fieldValue) {
            this.fieldValue = fieldValue;
        }
    }
}

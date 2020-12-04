package fr.dufaure.clement.adventofcode.event2020;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day2 {

    static String regex = "([0-9]*)-([0-9]*) ([a-z]): ([a-z]*)";

    public static void main(String[] args) {
        part1();
        part2();
    }

    private static void part1() {
        System.out.println(ImportUtils.getListStringUnParLigne("./src/main/resources/2020/day2").stream()
                .map(day2::extract).filter(PasswordChecker::isOk).count());
    }

    private static void part2() {
        System.out.println(ImportUtils.getListStringUnParLigne("./src/main/resources/2020/day2").stream()
                .map(day2::extract).filter(PasswordChecker::isOk2).count());
    }

    static PasswordChecker extract(String str) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        m.matches();
        return new PasswordChecker(
                new Rule(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)), m.group(3).charAt(0)), m.group(4));
    }

    static class PasswordChecker {
        Rule rule;
        String password;

        PasswordChecker(Rule rule, String password) {
            this.rule = rule;
            this.password = password;
        }

        boolean isOk() {
            int count = 0;
            for (char c : password.toCharArray()) {
                if (c == rule.letter) {
                    count++;
                }
            }
            return count >= rule.min && count <= rule.max;
        }

        boolean isOk2() {
            return password.charAt(rule.min - 1) == rule.letter ^ password.charAt(rule.max - 1) == rule.letter;
        }
    }

    static class Rule {
        int min;
        int max;
        char letter;

        Rule(int min, int max, char letter) {
            this.min = min;
            this.max = max;
            this.letter = letter;
        }
    }

}

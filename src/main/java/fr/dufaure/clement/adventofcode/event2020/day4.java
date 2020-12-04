package fr.dufaure.clement.adventofcode.event2020;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day4 {

    public static void main(String[] args) {
        part1();
        part2();
    }

    public static void part1() {
        String data = ImportUtils.getStringWithNewLine("./src/main/resources/2020/day4");
        String[] passports = data.split("\\n\\n");
        System.out.println(Arrays.asList(passports).stream().map(day4::createPassport).filter(Passport::check).count());
    }

    // not 12
    public static void part2() {
        String data = ImportUtils.getStringWithNewLine("./src/main/resources/2020/day4");
        String[] passports = data.split("\\n\\n");
        System.out.println(
                Arrays.asList(passports).stream().map(day4::createPassport).filter(Passport::checkBetter).count());
    }

    static Passport createPassport(String str) {
        try {
            Passport p = new Passport();
            for (String fieldValue : str.replace("\n", " ").split(" ")) {
                Field field = Passport.class.getDeclaredField(fieldValue.split(":")[0]);
                field.set(p, fieldValue.split(":")[1]);
            }
            return p;
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    static class Passport {

        @Criteria(regex = "([0-9]{4})", numericCheck = true, min = 1920, max = 2002)
        String byr;// (Birth Year)

        @Criteria(regex = "([0-9]{4})", numericCheck = true, min = 2010, max = 2020)
        String iyr;// (Issue Year)

        @Criteria(regex = "([0-9]{4})", numericCheck = true, min = 2020, max = 2030)
        String eyr;// (Expiration Year)

        @CriteriaChoice({ @Criteria(regex = "([0-9]{2})in", numericCheck = true, min = 59, max = 76),
                @Criteria(regex = "([0-9]{3})cm", numericCheck = true, min = 150, max = 193) })
        String hgt;// (Height)

        @Criteria(regex = "#[0-9a-f]{6}")
        String hcl;// (Hair Color)

        @Criteria(regex = "(amb|blu|brn|gry|grn|hzl|oth)")
        String ecl;// (Eye Color)

        @Criteria(regex = "[0-9]{9}")
        String pid;// (Passport ID)

        String cid;// (Country ID)

        boolean check() {
            try {
                for (Field f : Passport.class.getDeclaredFields()) {
                    if (!((f.getDeclaredAnnotation(Criteria.class) == null
                            && f.getDeclaredAnnotation(CriteriaChoice.class) == null)
                            || (f.getDeclaredAnnotation(Criteria.class) != null && f.get(this) != null)
                            || (f.getDeclaredAnnotation(CriteriaChoice.class) != null && f.get(this) != null))) {
                        return false;
                    }
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
            return true;
        }

        /**
         * 
         * byr (Birth Year) - four digits; at least 1920 and at most 2002.
         * 
         * iyr (Issue Year) - four digits; at least 2010 and at most 2020.
         * 
         * eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
         * 
         * hgt (Height) - a number followed by either cm or in:
         * 
         * - If cm, the number must be at least 150 and at most 193.
         * 
         * - If in, the number must be at least 59 and at most 76.
         * 
         * hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
         * 
         * ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
         * 
         * pid (Passport ID) - a nine-digit number, including leading zeroes.
         * 
         * cid (Country ID) - ignored, missing or not.
         * 
         */
        boolean checkBetter() {
            try {
                for (Field f : Passport.class.getDeclaredFields()) {
                    if (!((f.getDeclaredAnnotation(Criteria.class) == null
                            && f.getDeclaredAnnotation(CriteriaChoice.class) == null)
                            || (f.getDeclaredAnnotation(Criteria.class) != null && f.get(this) != null
                                    && checkValue(f.get(this).toString(), f.getDeclaredAnnotation(Criteria.class)))
                            || (f.getDeclaredAnnotation(CriteriaChoice.class) != null && f.get(this) != null
                                    && checkValue(f.get(this).toString(),
                                            f.getDeclaredAnnotation(CriteriaChoice.class))))) {
                        return false;
                    }
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
            return true;
        }

        static boolean checkValue(String value, Criteria criteria) {
            Pattern p = Pattern.compile(criteria.regex());
            Matcher m = p.matcher(value);
            if (m.matches()) {
                if (criteria.numericCheck()) {
                    int numericValue = Integer.parseInt(m.group(1));
                    if (numericValue < criteria.min() || numericValue > criteria.max()) {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }

        static boolean checkValue(String value, CriteriaChoice criterias) {
            for (Criteria c : criterias.value()) {
                if (checkValue(value, c)) {
                    return true;
                }
            }
            return false;
        }

    }

    @Retention(RetentionPolicy.RUNTIME)
    static @interface CriteriaChoice {
        Criteria[] value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    static @interface Criteria {
        /**
         * with capturing groupe for min/max test
         */
        String regex();

        boolean numericCheck() default false;

        int min() default 0;

        int max() default 0;

    }

}

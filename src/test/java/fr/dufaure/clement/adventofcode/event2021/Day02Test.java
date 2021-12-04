package fr.dufaure.clement.adventofcode.event2021;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Day02Test {

    @Test
    public void part101Test() {
        assertEquals("150", (new Day2()).part1("./src/test/resources/2021/day2-01"));
        ;
    }

    @Test
    public void part1Test() {
        assertEquals("2036120", (new Day2()).part1("./src/main/resources/2021/day2"));
        ;
    }
    @Test
    public void part201Test() {
        assertEquals("900", (new Day2()).part2("./src/test/resources/2021/day2-01"));
        ;
    }

    @Test
    public void part2Test() {
        assertEquals("2015547716", (new Day2()).part2("./src/main/resources/2021/day2"));
        ;
    }

}

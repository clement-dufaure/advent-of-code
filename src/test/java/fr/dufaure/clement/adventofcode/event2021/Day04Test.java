package fr.dufaure.clement.adventofcode.event2021;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Day04Test {

    @Test
    public void part101Test() {
        assertEquals("4512", (new Day4()).part1("./src/test/resources/2021/day4-01"));
        ;
    }

    @Test
    public void part1Test() {
        assertEquals("35711", (new Day4()).part1("./src/main/resources/2021/day4"));
        ;
    }

    @Test
    public void part201Test() {
        assertEquals("1924", (new Day4()).part2("./src/test/resources/2021/day4-01"));
        ;
    }

    @Test
    public void part2Test() {
        assertEquals("5586", (new Day4()).part2("./src/main/resources/2021/day4"));
        ;
    }

}

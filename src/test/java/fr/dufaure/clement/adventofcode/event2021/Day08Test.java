package fr.dufaure.clement.adventofcode.event2021;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Day08Test {

    @Test
    public void part101Test() {
        assertEquals("26", (new Day8()).part1("./src/test/resources/2021/day8-01"));
    }

    @Test
    public void part1Test() {
        assertEquals("369", (new Day8()).part1("./src/main/resources/2021/day8"));
    }

    @Test
    public void part201Test() {
        assertEquals("5353", (new Day8()).part2("./src/test/resources/2021/day8-00"));
    }

    @Test
    public void part202Test() {
        assertEquals("61229", (new Day8()).part2("./src/test/resources/2021/day8-01"));
    }

    @Test
    public void part2Test() {
        assertEquals("1031553", (new Day8()).part2("./src/main/resources/2021/day8"));
    }

}

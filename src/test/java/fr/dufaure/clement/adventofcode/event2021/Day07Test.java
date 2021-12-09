package fr.dufaure.clement.adventofcode.event2021;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Day07Test {

    @Test
    public void part101Test() {
        assertEquals("37", (new Day7()).part1("./src/test/resources/2021/day7-01"));
    }

    @Test
    public void part1Test() {
        assertEquals("348996", (new Day7()).part1("./src/main/resources/2021/day7"));
    }

    @Test
    public void part201Test() {
        assertEquals("168", (new Day7()).part2("./src/test/resources/2021/day7-01"));
    }

    @Test
    public void part2Test() {
        assertEquals("98231647", (new Day7()).part2("./src/main/resources/2021/day7"));
    }

}

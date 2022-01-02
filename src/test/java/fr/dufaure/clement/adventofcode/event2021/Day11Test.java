package fr.dufaure.clement.adventofcode.event2021;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Day11Test {

    @Test
    public void part101Test() {
        assertEquals("1656", (new Day11()).part1("./src/test/resources/2021/day11-01"));
    }

    @Test
    public void part1Test() {
        assertEquals("1613", (new Day11()).part1("./src/main/resources/2021/day11"));
    }

    @Test
    public void part202Test() {
        assertEquals("195", (new Day11()).part2("./src/test/resources/2021/day11-01"));
    }

    @Test
    public void part2Test() {
        assertEquals("510", (new Day11()).part2("./src/main/resources/2021/day11"));
    }

}

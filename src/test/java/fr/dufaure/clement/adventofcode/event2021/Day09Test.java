package fr.dufaure.clement.adventofcode.event2021;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Day09Test {

    @Test
    public void part101Test() {
        assertEquals("15", (new Day9()).part1("./src/test/resources/2021/day9-01"));
    }

    @Test
    public void part1Test() {
        assertEquals("528", (new Day9()).part1("./src/main/resources/2021/day9"));
    }

    @Test
    public void part202Test() {
        assertEquals("1134", (new Day9()).part2("./src/test/resources/2021/day9-01"));
    }

    @Test
    public void part2Test() {
        assertEquals("920448", (new Day9()).part2("./src/main/resources/2021/day9"));
    }

}

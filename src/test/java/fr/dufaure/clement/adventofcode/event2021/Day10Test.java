package fr.dufaure.clement.adventofcode.event2021;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Day10Test {

    @Test
    public void part101Test() {
        assertEquals("26397", (new Day10()).part1("./src/test/resources/2021/day10-01"));
    }

    @Test
    public void part1Test() {
        assertEquals("462693", (new Day10()).part1("./src/main/resources/2021/day10"));
    }

    @Test
    public void part202Test() {
        assertEquals("288957", (new Day10()).part2("./src/test/resources/2021/day10-01"));
    }

    @Test
    public void part2Test() {
        assertEquals("3094671161", (new Day10()).part2("./src/main/resources/2021/day10"));
    }

}

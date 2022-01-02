package fr.dufaure.clement.adventofcode.event2021;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Day14Test {

    @Test
    public void part101Test() {
        assertEquals("1588", (new Day14()).part1("./src/test/resources/2021/day14-01"));
    }

    @Test
    public void part1Test() {
        assertEquals("2435", (new Day14()).part1("./src/main/resources/2021/day14"));
    }

    @Test
    public void part201Test() {
        assertEquals("2188189693529", (new Day14()).part2("./src/test/resources/2021/day14-01"));
    }

    @Test
    public void part2Test() {
        assertEquals("2587447599164", (new Day14()).part2("./src/main/resources/2021/day14"));
    }

}

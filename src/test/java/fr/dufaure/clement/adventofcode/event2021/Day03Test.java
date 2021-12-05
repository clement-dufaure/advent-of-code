package fr.dufaure.clement.adventofcode.event2021;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Day03Test {

    @Test
    public void part101Test() {
        assertEquals("198", (new Day3()).part1("./src/test/resources/2021/day3-01"));
    }

    @Test
    public void part1Test() {
        assertEquals("3549854", (new Day3()).part1("./src/main/resources/2021/day3"));
    }

    @Test
    public void part201Test() {
        assertEquals("230", (new Day3()).part2("./src/test/resources/2021/day3-01"));
    }

    @Test
    public void part2Test() {
        assertEquals("3765399", (new Day3()).part2("./src/main/resources/2021/day3"));
    }

}

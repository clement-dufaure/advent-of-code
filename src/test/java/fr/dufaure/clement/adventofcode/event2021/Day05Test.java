package fr.dufaure.clement.adventofcode.event2021;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Day05Test {

    @Test
    public void part101Test() {
        assertEquals("5", (new Day5()).part1("./src/test/resources/2021/day5-01"));
    }

    @Test
    public void part1Test() {
        assertEquals("5774", (new Day5()).part1("./src/main/resources/2021/day5"));
    }

    @Test
    public void part201Test() {
        assertEquals("12", (new Day5()).part2("./src/test/resources/2021/day5-01"));
    }

    @Test
    public void part2Test() {
        assertEquals("18423", (new Day5()).part2("./src/main/resources/2021/day5"));
    }

}

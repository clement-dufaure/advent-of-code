package fr.dufaure.clement.adventofcode.event2021;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Day06Test {

    @Test
    public void part101Test() {
        assertEquals("5934", (new Day6()).part1("./src/test/resources/2021/day6-01"));
    }

    @Test
    public void part1Test() {
        assertEquals("358214", (new Day6()).part1("./src/main/resources/2021/day6"));
    }

    @Test
    public void part201Test() {
        assertEquals("26984457539", (new Day6()).part2("./src/test/resources/2021/day6-01"));
    }

    @Test
    public void part2Test() {
        assertEquals("1622533344325", (new Day6()).part2("./src/main/resources/2021/day6"));
    }

}

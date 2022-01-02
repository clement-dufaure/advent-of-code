package fr.dufaure.clement.adventofcode.event2021;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Day12Test {

    @Test
    public void part101Test() {
        assertEquals("10", (new Day12()).part1("./src/test/resources/2021/day12-01"));
    }

    @Test
    public void part102Test() {
        assertEquals("19", (new Day12()).part1("./src/test/resources/2021/day12-02"));
    }

    @Test
    public void part103Test() {
        assertEquals("226", (new Day12()).part1("./src/test/resources/2021/day12-03"));
    }

    @Test
    public void part1Test() {
        assertEquals("3738", (new Day12()).part1("./src/main/resources/2021/day12"));
    }

    @Test
    public void part201Test() {
        assertEquals("36", (new Day12()).part2("./src/test/resources/2021/day12-01"));
    }

    @Test
    public void part202Test() {
        assertEquals("103", (new Day12()).part2("./src/test/resources/2021/day12-02"));
    }

    @Test
    public void part203Test() {
        assertEquals("3509", (new Day12()).part2("./src/test/resources/2021/day12-03"));
    }

    @Test
    public void part2Test() {
        assertEquals("120506", (new Day12()).part2("./src/main/resources/2021/day12"));
    }

}

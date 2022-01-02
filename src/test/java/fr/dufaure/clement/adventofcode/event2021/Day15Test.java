package fr.dufaure.clement.adventofcode.event2021;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Day15Test {

    @Test
    public void part101Test() {
        assertEquals("40", (new Day15()).part1("./src/test/resources/2021/day15-01"));
    }

    @Test
    public void part1Test() {
        assertEquals("523", (new Day15()).part1("./src/main/resources/2021/day15"));
    }

    @Test
    public void part201Test() {
        assertEquals("315", (new Day15()).part2("./src/test/resources/2021/day15-01"));
    }

    @Test
    public void part2Test() {
        // 25s
        var t = System.currentTimeMillis();
        assertEquals("2876", (new Day15()).part2("./src/main/resources/2021/day15"));
        var t1 = System.currentTimeMillis();
        System.out.println((t1-t)/1000);
    }

}

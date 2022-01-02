package fr.dufaure.clement.adventofcode.event2021;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Day13Test {

    @Test
    public void part101Test() {
        assertEquals("17", (new Day13()).part1("./src/test/resources/2021/day13-01"));
    }

    @Test
    public void part1Test() {
        assertEquals("827", (new Day13()).part1("./src/main/resources/2021/day13"));
    }

    @Test
    public void part201Test() {
        assertEquals("""
                #####
                #...#
                #...#
                #...#
                #####
                """, (new Day13()).part2("./src/test/resources/2021/day13-01"));
    }

    @Test
    public void part2Test() {
        assertEquals("""
                ####..##..#..#.#..#.###..####..##..###.
                #....#..#.#..#.#.#..#..#.#....#..#.#..#
                ###..#..#.####.##...#..#.###..#....#..#
                #....####.#..#.#.#..###..#....#....###.
                #....#..#.#..#.#.#..#.#..#....#..#.#...
                ####.#..#.#..#.#..#.#..#.####..##..#...
                        """, (new Day13()).part2("./src/main/resources/2021/day13"));
    }

}

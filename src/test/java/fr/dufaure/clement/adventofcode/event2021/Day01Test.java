package fr.dufaure.clement.adventofcode.event2021;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Day01Test {

    @Test
    public void part1Test() {
        assertEquals("1446", (new Day1()).part1("./src/main/resources/2021/day1"));
        ;
    }

    @Test
    public void part2Test() {
        assertEquals("1486", (new Day1()).part2("./src/main/resources/2021/day1"));
        ;
    }

}

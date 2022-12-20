package fr.dufaure.clement.adventofcode.event2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day01Test {
    
    @Test
    public void part1Test01() {
        assertEquals("24000", (new Day1()).part1("./src/test/resources/2022" +
                "/day1-01"));
    }
    
    @Test
    public void part1Test() {
        assertEquals("68923", (new Day1()).part1("./src/main/resources/2022" +
                "/day1"));
    }
    
    @Test
    public void part2Test01() {
        assertEquals("45000", (new Day1()).part2("./src/test/resources/2022" +
                "/day1-01"));
    }
    
    @Test
    public void part2Test() {
        assertEquals("200044", (new Day1()).part2("./src/main/resources/2022" +
                "/day1"));
    }
}

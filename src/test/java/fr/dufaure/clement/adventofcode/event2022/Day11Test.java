package fr.dufaure.clement.adventofcode.event2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day11Test {
	@Test
	void part1Test01() {
		assertEquals("10605", (new Day11()).part1("./src/test/resources/2022/day11-01"));
	}
	
	@Test
	void part1() {
		assertEquals("78678", (new Day11()).part1("./src/main/resources/2022/day11"));
	}
	
	@Test
	void part2Test01() {
		assertEquals("2713310158", (new Day11()).part2("./src/test/resources/2022/day11-01"));
	}
	
	@Test
	void part2() {
		assertEquals("15333249714", (new Day11()).part2("./src/main/resources/2022/day11"));
	}
	
	
}

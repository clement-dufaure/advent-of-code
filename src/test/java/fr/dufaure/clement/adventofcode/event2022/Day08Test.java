package fr.dufaure.clement.adventofcode.event2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day08Test {
	
	@Test
	void part1Test01() {
		assertEquals("21", (new Day8()).part1("./src/test/resources/2022" + "/day8-01"));
	}
	
	@Test
	void part1() {
		assertEquals("", (new Day8()).part1("./src/main/resources/2022" + "/day8"));
	}
	
	@Test
	void part2Test01() {
		assertEquals("8", (new Day8()).part2("./src/test/resources/2022" + "/day8-01"));
	}
	
	@Test
	void part2() {
		assertEquals("", (new Day8()).part2("./src/main/resources/2022" + "/day8"));
	}
	
	
}

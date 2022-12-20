package fr.dufaure.clement.adventofcode.event2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day09Test {
	
	@Test
	void part1Test01() {
		assertEquals("13", (new Day9()).part1("./src/test/resources/2022/day9-01"));
	}
	
	
	@Test
	void part1() {
		assertEquals("6026", (new Day9()).part1("./src/main/resources/2022/day9"));
	}
	
	@Test
	void part2Test01() {
		assertEquals("1", (new Day9()).part2("./src/test/resources/2022/day9-01"));
	}
	
	@Test
	void part2Test02() {
		assertEquals("36", (new Day9()).part2("./src/test/resources/2022/day9-02"));
	}
	
	
	@Test
	void part2() {
		assertEquals("2273", (new Day9()).part2("./src/main/resources/2022/day9"));
	}
	
	
}

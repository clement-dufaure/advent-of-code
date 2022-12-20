package fr.dufaure.clement.adventofcode.event2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day04Test {

		@Test
	public void part1Test01() {
		assertEquals("2", (new Day4()).part1("./src/test/resources/2022" + "/day4-01"));
	}
	
	@Test
	public void part1Test() {
		assertEquals("530", (new Day4()).part1("./src/main/resources/2022" + "/day4"));
	}
	
	@Test
	public void part2Test01() {
		assertEquals("4", (new Day4()).part2("./src/test/resources/2022" + "/day4-01"));
	}
	
	@Test
	public void part2Test() {
		assertEquals("903", (new Day4()).part2("./src/main/resources/2022" + "/day4"));
	}
}

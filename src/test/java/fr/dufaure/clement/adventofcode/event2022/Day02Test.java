package fr.dufaure.clement.adventofcode.event2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day02Test {
	
	@Test
	public void part1Test01() {
		assertEquals("15", (new Day2()).part1("./src/test/resources/2022" + "/day2-01"));
	}
	
	@Test
	public void part1Test() {
		assertEquals("9241", (new Day2()).part1("./src/main/resources/2022" + "/day2"));
	}
	
	@Test
	public void part2Test01() {
		assertEquals("12", (new Day2()).part2("./src/test/resources/2022" + "/day2-01"));
	}
	
	@Test
	public void part2Test() {
		assertEquals("14610", (new Day2()).part2("./src/main/resources/2022" + "/day2"));
	}
}

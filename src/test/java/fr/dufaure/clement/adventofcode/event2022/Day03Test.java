package fr.dufaure.clement.adventofcode.event2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day03Test {

		@Test
	public void part1Test01() {
		assertEquals("157", (new Day3()).part1("./src/test/resources/2022" + "/day3-01"));
	}
	
	@Test
	public void part1Test() {
		assertEquals("8252", (new Day3()).part1("./src/main/resources/2022" + "/day3"));
	}
	
	@Test
	public void part2Test01() {
		assertEquals("70", (new Day3()).part2("./src/test/resources/2022" + "/day3-01"));
	}
	
	@Test
	public void part2Test() {
		assertEquals("2828", (new Day3()).part2("./src/main/resources/2022" + "/day3"));
	}
}

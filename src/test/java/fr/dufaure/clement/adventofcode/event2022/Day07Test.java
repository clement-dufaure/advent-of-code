package fr.dufaure.clement.adventofcode.event2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day07Test {
	
	@Test
	public void part1Test01() {
		assertEquals("95437", (new Day7()).part1("./src/test/resources/2022" + "/day7-01"));
	}
	
	@Test
	public void part1() {
		assertEquals("1118405", (new Day7()).part1("./src/main/resources/2022" + "/day7"));
	}
	
	@Test
	public void part2Test01() {
		assertEquals("24933642", (new Day7()).part2("./src/test/resources/2022" + "/day7-01"));
	}
	@Test
	public void part2() {
		assertEquals("1118405", (new Day7()).part2("./src/main/resources/2022" + "/day7"));
	}
	
	
}

package fr.dufaure.clement.adventofcode.event2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day06Test {
	
	@Test
	public void part1Test01() {
		assertEquals("7", (new Day6()).part1("./src/test/resources/2022" + "/day6-01"));
	}
	
	@Test
	public void part1Test02() {
		assertEquals("5", (new Day6()).part1("./src/test/resources/2022" + "/day6-02"));
	}
	
	@Test
	public void part1Test03() {
		assertEquals("6", (new Day6()).part1("./src/test/resources/2022" + "/day6-03"));
	}
	
	@Test
	public void part1Test04() {
		assertEquals("10", (new Day6()).part1("./src/test/resources/2022" + "/day6-04"));
	}
	
	@Test
	public void part1Test05() {
		assertEquals("11", (new Day6()).part1("./src/test/resources/2022" + "/day6-05"));
	}
	
	@Test
	public void part1() {
		assertEquals("1155", (new Day6()).part1("./src/main/resources/2022" + "/day6"));
	}
	
	@Test
	public void part2Test01() {
		assertEquals("19", (new Day6()).part2("./src/test/resources/2022" + "/day6-01"));
	}
	
	@Test
	public void part2Test02() {
		assertEquals("23", (new Day6()).part2("./src/test/resources/2022" + "/day6-02"));
	}
	
	@Test
	public void part2Test03() {
		assertEquals("23", (new Day6()).part2("./src/test/resources/2022" + "/day6-03"));
	}
	
	@Test
	public void part2Test04() {
		assertEquals("29", (new Day6()).part2("./src/test/resources/2022" + "/day6-04"));
	}
	
	@Test
	public void part2Test05() {
		assertEquals("26", (new Day6()).part2("./src/test/resources/2022" + "/day6-05"));
	}
	
	@Test
	public void part2() {
		assertEquals("2789", (new Day6()).part2("./src/main/resources/2022" + "/day6"));
	}
	
	
}

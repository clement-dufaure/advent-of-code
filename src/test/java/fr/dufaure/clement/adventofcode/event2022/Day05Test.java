package fr.dufaure.clement.adventofcode.event2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day05Test {

		@Test
	public void part1Test01() {
		assertEquals("CMZ", (new Day5()).part1("./src/test/resources/2022" + "/day5-01"));
	}
	
	@Test
	public void part1Test() {
		assertEquals("JRVNHHCSJ", (new Day5()).part1("./src/main/resources/2022" + "/day5"));
	}
	
	@Test
	public void part2Test01() {
		assertEquals("MCD", (new Day5()).part2("./src/test/resources/2022" + "/day5-01"));
	}
	
	@Test
	public void part2Test() {
		assertEquals("GNFBSBJLH", (new Day5()).part2("./src/main/resources/2022" + "/day5"));
	}
}

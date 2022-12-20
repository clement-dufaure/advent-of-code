package fr.dufaure.clement.adventofcode.event2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day10Test {
	
	@Test
	void part1Test01() {
		assertEquals("13140", (new Day10()).part1("./src/test/resources/2022/day10-01"));
	}
	
	@Test
	void part1() {
		assertEquals("14060", (new Day10()).part1("./src/main/resources/2022/day10"));
	}
	
	@Test
	void part2Test01() {
		assertEquals("""
				  ##..##..##..##..##..##..##..##..##..##..
				  ###...###...###...###...###...###...###.
				  ####....####....####....####....####....
				  #####.....#####.....#####.....#####.....
				  ######......######......######......####
				  #######.......#######.......#######.....
				    """, (new Day10()).part2("./src/test/resources/2022/day10-01"));
	}
	
	@Test
	void part2() {
		assertEquals("""
				  ###...##..###..#..#.####.#..#.####...##.
				  #..#.#..#.#..#.#.#..#....#.#..#.......#.
				  #..#.#..#.#..#.##...###..##...###.....#.
				  ###..####.###..#.#..#....#.#..#.......#.
				  #....#..#.#....#.#..#....#.#..#....#..#.
				  #....#..#.#....#..#.#....#..#.####..##..
				  """, (new Day10()).part2("./src/main/resources/2022/day10"));
	}
	
	
}

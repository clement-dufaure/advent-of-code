package fr.dufaure.clement.adventofcode.event2021;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Day16Test {

    @Test
    public void part101Test() {
        assertEquals("6", (new Day16()).part1("./src/test/resources/2021/day16-01"));
    }

    @Test
    public void part102Test() {
        assertEquals("9", (new Day16()).part1("./src/test/resources/2021/day16-02"));
    }

    @Test
    public void part103Test() {
        assertEquals("14", (new Day16()).part1("./src/test/resources/2021/day16-03"));
    }

    @Test
    public void part104Test() {
        assertEquals("16", (new Day16()).part1("./src/test/resources/2021/day16-04"));
    }

    @Test
    public void part105Test() {
        assertEquals("12", (new Day16()).part1("./src/test/resources/2021/day16-05"));
    }

    @Test
    public void part106Test() {
        assertEquals("23", (new Day16()).part1("./src/test/resources/2021/day16-06"));
    }

    @Test
    public void part107Test() {
        assertEquals("31", (new Day16()).part1("./src/test/resources/2021/day16-07"));
    }

    @Test
    public void part1Test() {
        assertEquals("967", (new Day16()).part1("./src/main/resources/2021/day16"));
    }

    @Test
    public void part208Test() {
        assertEquals("3", (new Day16()).part2("./src/test/resources/2021/day16-08"));
    }

    @Test
    public void part209Test() {
        assertEquals("54", (new Day16()).part2("./src/test/resources/2021/day16-09"));
    }

    @Test
    public void part210Test() {
        assertEquals("7", (new Day16()).part2("./src/test/resources/2021/day16-10"));
    }

    @Test
    public void part211Test() {
        assertEquals("9", (new Day16()).part2("./src/test/resources/2021/day16-11"));
    }

    @Test
    public void part212Test() {
        assertEquals("1", (new Day16()).part2("./src/test/resources/2021/day16-12"));
    }

    @Test
    public void part213Test() {
        assertEquals("0", (new Day16()).part2("./src/test/resources/2021/day16-13"));
    }

    @Test
    public void part214Test() {
        assertEquals("0", (new Day16()).part2("./src/test/resources/2021/day16-14"));
    }

    @Test
    public void part215Test() {
        assertEquals("1", (new Day16()).part2("./src/test/resources/2021/day16-15"));
    }

    @Test
    public void part2Test() {
        // 12883228771528 too high if large usage of greater or equals and lower or
        // equals
        assertEquals("12883091136209", (new Day16()).part2("./src/main/resources/2021/day16"));
    }

}

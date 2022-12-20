package fr.dufaure.clement.adventofcode.utils;

public record Coord(int x, int y) {
	
	public Coord avancer(Direction d) {
		return new Coord(x + d.diffX, y + d.diffY);
	}
}

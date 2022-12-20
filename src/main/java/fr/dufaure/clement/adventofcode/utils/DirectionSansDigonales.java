package fr.dufaure.clement.adventofcode.utils;

public enum DirectionSansDigonales implements AbstractDirection {
	N(0, 1), E(1, 0), S(0, -1), O(-1, 0);
	
	public int diffX;
	public int diffY;
	
	DirectionSansDigonales(int diffX, int diffY) {
		this.diffX = diffX;
		this.diffY = diffY;
	}
	
}
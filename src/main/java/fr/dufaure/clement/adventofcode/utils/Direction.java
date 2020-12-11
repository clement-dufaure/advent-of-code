package fr.dufaure.clement.adventofcode.utils;

public enum Direction {
    N(0, 1), NE(1, 1), E(1, 0), SE(1, -1), S(0, -1), SO(-1, -1), O(-1, 0), NO(-1, 1);

    public int diffX;
    public int diffY;

    Direction(int diffX, int diffY) {
        this.diffX = diffX;
        this.diffY = diffY;
    }
}
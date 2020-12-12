package fr.dufaure.clement.adventofcode.utils;

public enum Direction {
    N(0, 1), NE(1, 1), E(1, 0), SE(1, -1), S(0, -1), SO(-1, -1), O(-1, 0), NO(-1, 1);

    public int diffX;
    public int diffY;

    Direction(int diffX, int diffY) {
        this.diffX = diffX;
        this.diffY = diffY;
    }

    public static Direction turnLeft45(Direction d) {
        switch (d) {
            case N:
                return NO;
            case NO:
                return O;
            case O:
                return SO;
            case SO:
                return S;
            case S:
                return SE;
            case SE:
                return E;
            case E:
                return NE;
            case NE:
                return N;
        }
        // unused
        throw new RuntimeException();
    }

    public static Direction turnLeft(Direction d, int angle) {
        if (angle % 45 != 0) {
            throw new UnsupportedOperationException();
        }
        int numberStep = angle / 45;
        for (int i = 0; i < numberStep; i++) {
            d = turnLeft45(d);
        }
        return d;
    }

    public static Direction turnRight45(Direction d) {
        switch (d) {
            case N:
                return NE;
            case NE:
                return E;
            case E:
                return SE;
            case SE:
                return S;
            case S:
                return SO;
            case SO:
                return O;
            case O:
                return NO;
            case NO:
                return N;
        }
        // unused
        throw new RuntimeException();
    }

    public static Direction turnRight(Direction d, int angle) {
        if (angle % 45 != 0) {
            throw new UnsupportedOperationException();
        }
        int numberStep = angle / 45;
        for (int i = 0; i < numberStep; i++) {
            d = turnRight45(d);
        }
        return d;
    }

}
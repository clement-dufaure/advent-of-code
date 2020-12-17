package fr.dufaure.clement.adventofcode.utils;

public enum Direction3D {
    // z=0
    N(0, 1, 0), NE(1, 1, 0), E(1, 0, 0), SE(1, -1, 0), S(0, -1, 0), SO(-1, -1, 0), O(-1, 0, 0), NO(-1, 1, 0),
    // z=+1
    UP(0, 0, 1), N_UP(0, 1, 1), NE_UP(1, 1, 1), E_UP(1, 0, 1), SE_UP(1, -1, 1), S_UP(0, -1, 1), SO_UP(-1, -1, 1),
    O_UP(-1, 0, 1), NO_UP(-1, 1, 1),
    // z=-1
    DOWN(0, 0, -1), N_DOWN(0, 1, -1), NE_DOWN(1, 1, -1), E_DOWN(1, 0, -1), SE_DOWN(1, -1, -1), S_DOWN(0, -1, -1),
    SO_DOWN(-1, -1, -1), O_DOWN(-1, 0, -1), NO_DOWN(-1, 1, -1);

    public int diffX;
    public int diffY;
    public int diffZ;

    Direction3D(int diffX, int diffY, int diffZ) {
        this.diffX = diffX;
        this.diffY = diffY;
        this.diffZ = diffZ;
    }

    Direction3D getOpposite() {
        for (Direction3D d : Direction3D.values()) {
            if (d.diffX == -diffX && d.diffY == -diffY && d.diffZ == -diffZ) {
                return d;
            }
        }
        throw new UnsupportedOperationException();
    }

}
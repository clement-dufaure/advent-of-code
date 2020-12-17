package fr.dufaure.clement.adventofcode.utils;

public class Direction4D {

    static Direction4D[] values = new Direction4D[80];

    static {
        int i = 0;
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    for (int w = -1; w <= 1; w++) {
                        if (!(x == 0 && y == 0 && z == 0 && w == 0)) {
                            values[i] = new Direction4D(x, y, z, w);
                            i++;
                        }
                    }
                }
            }

        }
    }

    public int diffX;
    public int diffY;
    public int diffZ;
    public int diffW;

    private Direction4D(int diffX, int diffY, int diffZ, int diffW) {
        this.diffX = diffX;
        this.diffY = diffY;
        this.diffZ = diffZ;
        this.diffW = diffW;
    }

    Direction4D getOpposite() {
        for (Direction4D d : values) {
            if (d.diffX == -diffX && d.diffY == -diffY && d.diffZ == -diffZ && d.diffW == -diffW) {
                return d;
            }
        }
        throw new UnsupportedOperationException();
    }

}
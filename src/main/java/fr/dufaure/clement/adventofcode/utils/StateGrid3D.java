package fr.dufaure.clement.adventofcode.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class StateGrid3D<T> {

    public Map<Coord3D, Case<T>> gridMap = new HashMap<>();
    public List<Case<T>> gridList = new ArrayList<>();
    T defaultValue = null;

    public StateGrid3D() {
    }

    public StateGrid3D(T defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void add(int x, int y, int z, T t) {
        Case<T> maCase = Case.of(Coord3D.of(x, y, z), t);
        gridList.add(maCase);
        gridMap.put(Coord3D.of(x, y, z), maCase);
    }

    public void consolider() {
        List<Case<T>> toAdd = new ArrayList<>();
        for (Case<T> c : gridList) {
            if (c.casesAdjacentes.size() < Direction3D.values().length) {
                for (Direction3D d : Direction3D.values()) {
                    if (!gridMap.containsKey(sum(c.coord, d))) {
                        Case<T> newCase = Case.of(sum(c.coord, d), defaultValue);
                        gridMap.put(sum(c.coord, d), newCase);
                        toAdd.add(newCase);
                    }
                    if (!gridMap.get(sum(c.coord, d)).casesAdjacentes.containsValue(c)) {
                        gridMap.get(sum(c.coord, d)).casesAdjacentes.put(d.getOpposite(), c);
                    }
                    c.casesAdjacentes.put(d, gridMap.get(sum(c.coord, d)));
                }
            }
        }
        gridList.addAll(toAdd);
    }

    static Coord3D sum(Coord3D c, Direction3D d) {
        return Coord3D.of(c.x + d.diffX, c.y + d.diffY, c.z + d.diffZ);
    }

    public T get(int x, int y, int z) {
        return gridMap.getOrDefault(Coord3D.of(x, y, z), Case.of(null, defaultValue)).value;
    }

    public int getMinX() {
        return gridList.stream().map(c -> c.coord).mapToInt(c -> c.x).min().getAsInt();
    }

    public int getMaxX() {
        return gridList.stream().map(c -> c.coord).mapToInt(c -> c.x).max().getAsInt();
    }

    public int getMinY() {
        return gridList.stream().map(c -> c.coord).mapToInt(c -> c.y).min().getAsInt();
    }

    public int getMaxY() {
        return gridList.stream().map(c -> c.coord).mapToInt(c -> c.y).max().getAsInt();
    }

    public int getMinZ() {
        return gridList.stream().map(c -> c.coord).mapToInt(c -> c.z).min().getAsInt();
    }

    public int getMaxZ() {
        return gridList.stream().map(c -> c.coord).mapToInt(c -> c.z).max().getAsInt();
    }

    public List<T> getValues() {
        return gridList.stream().map(c -> c.value).collect(Collectors.toList());
    }

    public List<Case<T>> getCases() {
        return gridList;
    }

    public void apply() {
        gridList.forEach(Case::apply);
    }

    public boolean hasChanged() {
        return gridList.stream().filter(c -> !c.value.equals(c.newValue)).count() != 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof StateGrid3D) {
            StateGrid3D<?> g = (StateGrid3D<?>) obj;
            return g.gridList.equals(gridList);
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuffer sysout = new StringBuffer();
        for (int z = getMinZ(); z <= getMaxZ(); z++) {
            sysout.append("z=" + z + "\n");
            for (int y = getMinY(); y <= getMaxY(); y++) {
                for (int x = getMinX(); x <= getMaxY(); x++) {
                    sysout.append(get(x, y, z));
                }
                sysout.append("\n");
            }
            sysout.append("\n\n");
        }
        return sysout.toString();
    }

    public static class Case<T> {
        public Coord3D coord;
        public T value;
        public T newValue;
        public Map<Direction3D, Case<T>> casesAdjacentes = new HashMap<>();

        static <T> Case<T> of(Coord3D coord, T value) {
            Case<T> c = new Case<>();
            c.coord = coord;
            c.value = value;
            return c;
        }

        void apply() {
            value = newValue;
            newValue = null;
        }

        @Override
        public String toString() {
            return value.toString();
        }

        @Override
        public int hashCode() {
            return Objects.hash(coord, value);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj != null && obj instanceof Case) {
                Case<?> c = (Case<?>) obj;
                return c.coord.equals(coord) && c.value.equals(value);
            }
            return false;
        }
    }

    public static class Coord3D {
        public int x;
        public int y;
        public int z;

        public static Coord3D of(int x, int y, int z) {
            Coord3D c = new Coord3D();
            c.x = x;
            c.y = y;
            c.z = z;
            return c;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj != null && obj instanceof Coord3D) {
                Coord3D c = (Coord3D) obj;
                return c.x == x && c.y == y && c.z == z;
            }
            return false;
        }

        @Override
        public String toString() {
            return "(" + x + "," + y + "," + z + ")";
        }
    }

}

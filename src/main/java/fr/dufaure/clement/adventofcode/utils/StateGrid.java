package fr.dufaure.clement.adventofcode.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class StateGrid<T> {

    public Map<Coord, Case<T>> gridMap = new HashMap<>();
    public List<Case<T>> gridList = new ArrayList<>();
    T defaultValue = null;

    public StateGrid() {
    }

    public StateGrid(T defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void add(int x, int y, T t) {
        Case<T> maCase = Case.of(Coord.of(x, y), t)
        gridList.add(maCase);
        gridMap.put(Coord.of(x, y), maCase);
    }

    public void consolider() {
        for (Case<T> c : gridList) {
            c.casesAdjacentes = gridList.stream().filter(myCase -> filterArround(c.coord, myCase.coord))
                    .collect(Collectors.toList());
        }
    }

    static boolean filterArround(Coord center, Coord around) {
        return ((around.x == center.x + 1)
                && (around.y == center.y + 1 || around.y == center.y || around.y == center.y - 1)
                || ((around.x == center.x) && (around.y == center.y + 1 || around.y == center.y - 1))
                || ((around.x == center.x - 1)
                        && (around.y == center.y + 1 || around.y == center.y || around.y == center.y - 1)));
    }

    public T get(int x, int y) {
        return gridList.stream().filter(c -> c.coord.equals(Coord.of(x, y))).findFirst()
                .orElseThrow(RuntimeException::new).value;
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

    // public List<T> getAround(int x, int y) {
    // List<T> around = new ArrayList<>();
    // around.add(get(x + 1, y + 1));
    // around.add(get(x + 1, y));
    // around.add(get(x + 1, y - 1));
    // around.add(get(x, y + 1));
    // around.add(get(x, y - 1));
    // around.add(get(x - 1, y + 1));
    // around.add(get(x - 1, y));
    // around.add(get(x - 1, y - 1));
    // return around;
    // }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof StateGrid) {
            StateGrid<?> g = (StateGrid<?>) obj;
            return g.gridList.equals(gridList);
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuffer sysout = new StringBuffer();
        for (int y = getMinX(); y <= getMaxY(); y++) {
            for (int x = getMinX(); x <= getMaxY(); x++) {
                sysout.append(get(x, y));
            }
            sysout.append("\n");
        }
        return sysout.toString();
    }

    public static class Case<T> {
        Coord coord;
        public T value;
        public T newValue;
        public List<Case<T>> casesAdjacentes = new ArrayList<>();

        static <T> Case<T> of(Coord coord, T value) {
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

    public static class Coord {
        int x;
        int y;

        static Coord of(int x, int y) {
            Coord c = new Coord();
            c.x = x;
            c.y = y;
            return c;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj != null && obj instanceof Coord) {
                Coord c = (Coord) obj;
                return c.x == x && c.y == y;
            }
            return false;
        }
    }

}

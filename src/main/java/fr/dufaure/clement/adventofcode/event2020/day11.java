package fr.dufaure.clement.adventofcode.event2020;

import java.util.List;

import fr.dufaure.clement.adventofcode.utils.Direction;
import fr.dufaure.clement.adventofcode.utils.ImportUtils;
import fr.dufaure.clement.adventofcode.utils.StateGrid;
import fr.dufaure.clement.adventofcode.utils.StateGrid.Case;

public class day11 {

    public static void main(String[] args) {
        part1();
        part2();
    }

    static void part1() {
        List<String> list = ImportUtils.getListStringUnParLigne("./src/main/resources/2020/day11");
        StateGrid<Status> grid = createGrid(list);
        while (true) {
            prepareTurn(grid, 4L);
            if (!grid.hasChanged()) {
                break;
            } else {
                grid.apply();
            }
        }
        System.out.println(grid.getValues().stream().filter(s -> s.equals(Status.OCCUPIED)).count());
    }

    static void part2() {
        List<String> list = ImportUtils.getListStringUnParLigne("./src/main/resources/2020/day11");
        StateGridBis grid = createGridBis(list);
        while (true) {
            prepareTurn(grid, 5L);
            if (!grid.hasChanged()) {
                break;
            } else {
                grid.apply();
            }
        }
        System.out.println(grid.getValues().stream().filter(s -> s.equals(Status.OCCUPIED)).count());
    }

    static StateGrid<Status> createGrid(List<String> list) {
        StateGrid<Status> g = new StateGrid<>(Status.FLOOR);
        for (int y = 0; y < list.size(); y++) {
            for (int x = 0; x < list.get(0).length(); x++) {
                g.add(x, y, Status.get(list.get(y).charAt(x)));
            }
        }
        g.consolider();
        return g;
    }

    static StateGridBis createGridBis(List<String> list) {
        StateGridBis g = new StateGridBis();
        for (int y = 0; y < list.size(); y++) {
            for (int x = 0; x < list.get(0).length(); x++) {
                g.add(x, y, Status.get(list.get(y).charAt(x)));
            }
        }
        g.consolider();
        return g;
    }

    static void prepareTurn(StateGrid<Status> grid, long maxNumberOccupied) {
        grid.getCases().forEach(c -> calculateValue(c, maxNumberOccupied));
    }

    static void calculateValue(Case<Status> c, long maxNumberOccupied) {
        if (c.value.equals(Status.EMPTY)
                && c.casesAdjacentes.values().stream().filter(s -> s.value.equals(Status.OCCUPIED)).count() == 0L) {
            c.newValue = Status.OCCUPIED;
        } else if (c.value.equals(Status.OCCUPIED) && c.casesAdjacentes.values().stream()
                .filter(s -> s.value.equals(Status.OCCUPIED)).count() >= maxNumberOccupied) {
            c.newValue = Status.EMPTY;
        } else {
            c.newValue = c.value;
        }
    }

    static enum Status {
        FLOOR("."), EMPTY("L"), OCCUPIED("#");

        String print;

        Status(String print) {
            this.print = print;
        }

        static Status get(char c) {
            switch (c) {
                case '.':
                    return FLOOR;
                case 'L':
                    return EMPTY;
                case '#':
                    return OCCUPIED;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        @Override
        public String toString() {
            return print;
        }
    }

    static class StateGridBis extends StateGrid<Status> {

        @Override
        public void consolider() {

            for (Case<Status> c : gridList) {
                for (Direction d : Direction.values()) {
                    Coord coord = Coord.of(c.coord.x, c.coord.y);
                    while (true) {
                        coord = sum(coord, d);
                        if (gridMap.containsKey(coord)) {
                            if (!gridMap.get(coord).value.equals(Status.FLOOR)) {
                                // first seat seen
                                c.casesAdjacentes.put(d, gridMap.get(coord));
                                break;
                            }
                            continue;
                        }
                        // We are outside, no seat on this direction
                        break;
                    }
                }
            }
        }

        static Coord sum(Coord c, Direction d) {
            return Coord.of(c.x + d.diffX, c.y + d.diffY);
        }

    }

}

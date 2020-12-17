package fr.dufaure.clement.adventofcode.event2020;

import java.util.List;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;
import fr.dufaure.clement.adventofcode.utils.StateGrid3D;
import fr.dufaure.clement.adventofcode.utils.StateGrid3D.Case;
import fr.dufaure.clement.adventofcode.utils.StateGrid4D;

public class day17 {

    public static void main(String[] args) {
        part1();
        part2();
    }

    static void part1() {
        List<String> liste = ImportUtils.getListStringUnParLigne("./src/main/resources/2020/day17");
        StateGrid3D<Status> grid = createGrid3D(liste);
        grid.consolider();
        for (int i = 0; i < 6; i++) {
            runTurn(grid);
        }
        System.out.println(grid.getValues().stream().filter(s -> s.equals(Status.ACTIVE)).count());
    }

    static void part2() {
        List<String> liste = ImportUtils.getListStringUnParLigne("./src/main/resources/2020/day17");
        StateGrid4D<Status> grid = createGrid4D(liste);
        grid.consolider();
        for (int i = 0; i < 6; i++) {
            runTurn(grid);
        }
        System.out.println(grid.getValues().stream().filter(s -> s.equals(Status.ACTIVE)).count());
    }

    private static void runTurn(StateGrid3D<Status> grid) {
        for (Case<Status> myCase : grid.getCases()) {
            long numberOfNeighborsActive = myCase.casesAdjacentes.values().stream()
                    .filter(c -> c.value.equals(Status.ACTIVE)).count();
            if (myCase.value.equals(Status.ACTIVE) && (numberOfNeighborsActive != 2 && numberOfNeighborsActive != 3)) {
                myCase.newValue = Status.INACTIVE;
            } else if (myCase.value.equals(Status.INACTIVE) && (numberOfNeighborsActive == 3)) {
                myCase.newValue = Status.ACTIVE;
            } else {
                myCase.newValue = myCase.value;
            }
        }
        grid.apply();
        grid.consolider();
    }

    private static void runTurn(StateGrid4D<Status> grid) {
        for (StateGrid4D.Case<Status> myCase : grid.getCases()) {
            long numberOfNeighborsActive = myCase.casesAdjacentes.values().stream()
                    .filter(c -> c.value.equals(Status.ACTIVE)).count();
            if (myCase.value.equals(Status.ACTIVE) && (numberOfNeighborsActive != 2 && numberOfNeighborsActive != 3)) {
                myCase.newValue = Status.INACTIVE;
            } else if (myCase.value.equals(Status.INACTIVE) && (numberOfNeighborsActive == 3)) {
                myCase.newValue = Status.ACTIVE;
            } else {
                myCase.newValue = myCase.value;
            }
        }
        grid.apply();
        grid.consolider();
    }

    static StateGrid3D<Status> createGrid3D(List<String> list) {
        StateGrid3D<Status> grid = new StateGrid3D<Status>(Status.INACTIVE);
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(0).length(); j++) {
                grid.add(j, i, 0, Status.of(list.get(i).charAt(j)));
            }
        }
        return grid;
    }

    static StateGrid4D<Status> createGrid4D(List<String> list) {
        StateGrid4D<Status> grid = new StateGrid4D<Status>(Status.INACTIVE);
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(0).length(); j++) {
                grid.add(j, i, 0, 0, Status.of(list.get(i).charAt(j)));
            }
        }
        return grid;
    }

    static enum Status {
        ACTIVE("#"), INACTIVE(".");

        String print;

        Status(String print) {
            this.print = print;
        }

        @Override
        public String toString() {
            return print;
        }

        static Status of(char c) {
            switch (c) {
                case '#':
                    return ACTIVE;
                case '.':
                    return INACTIVE;
                default:
                    throw new UnsupportedOperationException();
            }
        }
    }

}

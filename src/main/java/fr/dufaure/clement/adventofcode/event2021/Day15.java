package fr.dufaure.clement.adventofcode.event2021;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import fr.dufaure.clement.adventofcode.utils.Day;
import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class Day15 implements Day {

    @Override
    public String part1(String inputPath) {
        var input = ImportUtils.getListStringUnParLigne(inputPath);
        var grid = readInput(input);
        var coordOfEnd = new Coord(input.size() - 1, input.get(0).length() - 1);
        return String.valueOf(doDijkstra(grid, coordOfEnd));
    }

    @Override
    public String part2(String inputPath) {
        var input = ImportUtils.getListStringUnParLigne(inputPath);
        var grid = readInputFive(input);
        var coordOfEnd = new Coord(input.size() * 5 - 1, input.get(0).length() * 5 - 1);
        return String.valueOf(doDijkstra(grid, coordOfEnd));
    }

    private int doDijkstra(Map<Coord, Integer> grid, Coord coordOfEnd) {
        // Dijkstra algotithm

        final var minAccessToCase = new HashMap<Coord, Case>();
        final var alreadyComputed = new HashSet<Coord>();
        minAccessToCase.put(new Coord(0, 0), new Case(0));
        while (true) {

            // find minimum value in known distances and non already compute
            var entryToCheck = minAccessToCase.entrySet().stream()./* filter(e -> !e.getValue().dijkstraComputed). */
                    sorted((e1, e2) -> e1.getValue().risk.compareTo(e2.getValue().risk)).findFirst().get();

            var coordToCheck = entryToCheck.getKey();
            var caseToCheck = entryToCheck.getValue();

            if (coordToCheck.equals(coordOfEnd)) {
                // stop if current min is finish lane
                break;
            }

            // compute values for all neighbourgs non already compute
            for (var d : Direction.values()) {
                var newCase = new Coord(coordToCheck.x + d.diffX, coordToCheck.y + d.diffY);
                var riskOfNewCase = grid.get(newCase);
                var currentMinNewCase = minAccessToCase.get(newCase);
                // if (riskOfNewCase != null && !(currentMinNewCase != null &&
                // currentMinNewCase.dijkstraComputed)) {
                if (riskOfNewCase != null && !alreadyComputed.contains(newCase)) {

                    // case exists ans never computed
                    if (currentMinNewCase == null) {
                        minAccessToCase.put(newCase, new Case(caseToCheck.risk + riskOfNewCase));
                    } else {
                        // replace value if < current value
                        if (caseToCheck.risk + riskOfNewCase < currentMinNewCase.risk) {
                            currentMinNewCase.risk = caseToCheck.risk + riskOfNewCase;
                        }
                    }
                }
            }

            // caseToCheck.dijkstraComputed = true;
            alreadyComputed.add(coordToCheck);
            minAccessToCase.remove(coordToCheck);

            // if (++numberLoops % 1000 == 0)
            // System.out.println(numberLoops);

        }
        return minAccessToCase.get(coordOfEnd).risk;
    }

    private Map<Coord, Integer> readInput(List<String> input) {
        var m = new HashMap<Coord, Integer>();
        for (var i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(0).length(); j++) {
                m.put(new Coord(i, j), Integer.parseInt("" + input.get(i).charAt(j)));
            }
        }
        return m;
    }

    private Map<Coord, Integer> readInputFive(List<String> input) {
        var m = new HashMap<Coord, Integer>();
        for (var i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(0).length(); j++) {
                for (var k = 0; k < 5; k++) {
                    for (var l = 0; l < 5; l++) {
                        var valueRisk = Integer.parseInt("" + input.get(i).charAt(j)) + k + l;
                        if (valueRisk >= 10) {
                            valueRisk -= 9;
                        }
                        m.put(new Coord(i + k * input.size(), j + l * input.size()),
                                valueRisk);
                    }
                }
            }
        }
        return m;
    }

    private enum Direction {
        N(0, 1), E(1, 0), S(0, -1), O(-1, 0);

        public int diffX;
        public int diffY;

        Direction(int diffX, int diffY) {
            this.diffX = diffX;
            this.diffY = diffY;
        }

    }

    private record Coord(int x, int y) {
    }

    private class Case {
        Integer risk;
        // boolean dijkstraComputed = false;

        public Case(int risk) {
            this.risk = risk;
        }
    }

}

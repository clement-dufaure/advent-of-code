package fr.dufaure.clement.adventofcode.event2021;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

import fr.dufaure.clement.adventofcode.utils.Day;
import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class Day9 implements Day {

    @Override
    public String part1(String inputPath) {
        var data = ImportUtils.getListStringUnParLigne(inputPath);
        final var grid = new HashMap<Coord, Integer>();
        for (var i = 0; i < data.size(); i++) {
            for (var j = 0; j < data.get(0).length(); j++) {
                grid.put(new Coord(i, j), Integer.parseInt("" + data.get(i).charAt(j)));
            }
        }
        return String.valueOf(
                grid.entrySet().stream().filter(e -> isLowThanNeighbours(e, grid)).mapToInt(e -> e.getValue() + 1)
                        .sum());
    }

    private boolean isLowThanNeighbours(Entry<Coord, Integer> e, HashMap<Coord, Integer> grid) {
        for (var c : e.getKey().getCasesAdjacentes()) {
            if (grid.getOrDefault(c, Integer.MAX_VALUE) <= e.getValue()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String part2(String inputPath) {
        var data = ImportUtils.getListStringUnParLigne(inputPath);
        final var grid = new HashMap<Coord, Integer>();
        for (var i = 0; i < data.size(); i++) {
            for (var j = 0; j < data.get(0).length(); j++) {
                grid.put(new Coord(i, j), Integer.parseInt("" + data.get(i).charAt(j)));
            }
        }
        var lowerPoints = grid.entrySet().stream().filter(e -> isLowThanNeighbours(e, grid)).map(e -> e.getKey())
                .toList();
        var sizesOfBasin = new ArrayList<Integer>();
        for (var p : lowerPoints) {
            var coordsOfBasin = new HashSet<Coord>();
            coordsOfBasin.add(p);
            var sizeOfBasin = 0;
            do {
                sizeOfBasin = coordsOfBasin.size();
                coordsOfBasin.addAll(coordsOfBasin.stream().map(c -> c.getCasesAdjacentes()).flatMap(l -> l.stream())
                        .filter(c -> !grid.getOrDefault(c, 9).equals(9)).toList());
            } while (sizeOfBasin != coordsOfBasin.size());
            sizesOfBasin.add(sizeOfBasin);
        }
        var topBasin = sizesOfBasin.stream().sorted().toList().subList(sizesOfBasin.size() - 3, sizesOfBasin.size());
        return String.valueOf(topBasin.get(0) * topBasin.get(1) * topBasin.get(2));
    }

    private record Coord(int x, int y) {

        public List<Coord> getCasesAdjacentes() {
            var l = new ArrayList<Coord>();
            for (var d : Direction.values()) {
                l.add(new Coord(this.x + d.diffX, this.y + d.diffY));
            }
            return l;
        }

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

}

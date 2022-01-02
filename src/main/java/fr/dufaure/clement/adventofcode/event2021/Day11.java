package fr.dufaure.clement.adventofcode.event2021;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.dufaure.clement.adventofcode.utils.Day;
import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class Day11 implements Day {

    @Override
    public String part1(String inputPath) {
        var data = ImportUtils.getListStringUnParLigne(inputPath);
        final var grid = new HashMap<Coord, Octopus>();
        for (var i = 0; i < data.size(); i++) {
            for (var j = 0; j < data.get(0).length(); j++) {
                grid.put(new Coord(i, j), new Octopus(Integer.parseInt("" + data.get(i).charAt(j))));
            }
        }

        for (var i = 0; i < 100; i++) {
            // increment 1
            grid.entrySet().forEach(e -> e.getValue().incrementEnergy());

            // check for flash
            while (grid.entrySet().stream().filter(e -> (e.getValue().energy > 9 && !e.getValue().hasFlashed))
                    .count() > 0) {
                grid.entrySet().stream().filter(e -> (e.getValue().energy > 9 && !e.getValue().hasFlashed))
                        .forEach(e -> {
                            e.getValue().flash();
                            e.getKey().getCasesAdjacentes()
                                    .forEach(c -> grid.getOrDefault(c, new Octopus(-1)).incrementEnergy());
                        });
            }

            // clean for next turn
            grid.entrySet().stream().filter(e -> e.getValue().hasFlashed).forEach(e -> e.getValue().reset());
        }

        return String.valueOf(Octopus.numberOfFlashes);
    }

    @Override
    public String part2(String inputPath) {
        var data = ImportUtils.getListStringUnParLigne(inputPath);
        final var grid = new HashMap<Coord, Octopus>();
        for (var i = 0; i < data.size(); i++) {
            for (var j = 0; j < data.get(0).length(); j++) {
                grid.put(new Coord(i, j), new Octopus(Integer.parseInt("" + data.get(i).charAt(j))));
            }
        }

        for (var i = 0; i < 10000; i++) {
            // increment 1
            grid.entrySet().forEach(e -> e.getValue().incrementEnergy());

            // check for flash
            while (grid.entrySet().stream().filter(e -> (e.getValue().energy > 9 && !e.getValue().hasFlashed))
                    .count() > 0) {
                grid.entrySet().stream().filter(e -> (e.getValue().energy > 9 && !e.getValue().hasFlashed))
                        .forEach(e -> {
                            e.getValue().flash();
                            e.getKey().getCasesAdjacentes()
                                    .forEach(c -> grid.getOrDefault(c, new Octopus(-1)).incrementEnergy());
                        });
            }

            // clean for next turn
            if (grid.entrySet().stream().filter(e -> e.getValue().hasFlashed).count() == grid.keySet().size()) {
                return String.valueOf(i + 1);
            }

            grid.entrySet().stream().filter(e -> e.getValue().hasFlashed).forEach(e -> e.getValue().reset());
        }
        throw new UnsupportedOperationException();
    }

    private class Octopus {
        private int energy;
        private boolean hasFlashed = false;

        public static int numberOfFlashes = 0;

        public Octopus(int energy) {
            this.energy = energy;
        }

        public void reset() {
            energy = 0;
            hasFlashed = false;
        }

        public void flash() {
            numberOfFlashes++;
            hasFlashed = true;
        }

        public void incrementEnergy() {
            energy++;
        }
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
        N(0, 1), NE(1, 1), E(1, 0), SE(1, -1), S(0, -1), SO(-1, -1), O(-1, 0), NO(-1, 1);

        public int diffX;
        public int diffY;

        Direction(int diffX, int diffY) {
            this.diffX = diffX;
            this.diffY = diffY;
        }
    }

}

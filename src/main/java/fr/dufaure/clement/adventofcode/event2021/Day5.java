package fr.dufaure.clement.adventofcode.event2021;

import java.util.HashMap;
import java.util.regex.Pattern;

import fr.dufaure.clement.adventofcode.utils.Day;
import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class Day5 implements Day {

    private Line parseLine(String line) {
        var p = Pattern.compile("([0-9]+),([0-9]+) -> ([0-9]+),([0-9]+)");
        var m = p.matcher(line);
        if (m.matches()) {
            return new Line(new Coord(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2))),
                    new Coord(Integer.parseInt(m.group(3)), Integer.parseInt(m.group(4))));
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public String part1(String inputPath) {
        var lines = ImportUtils.getListStringUnParLigne(inputPath).stream().map(l -> parseLine(l)).toList();
        var grid = new HashMap<Coord, Integer>();
        for (var line : lines) {
            if (line.start.x == line.end.x) {
                for (var y0 = Math.min(line.start.y, line.end.y); y0 <= Math.max(line.start.y, line.end.y); y0++) {
                    grid.putIfAbsent(new Coord(line.start.x, y0), 0);
                    grid.compute(new Coord(line.start.x, y0), (k, v) -> v + 1);
                }
            } else if (line.start.y == line.end.y) {
                for (var x0 = Math.min(line.start.x, line.end.x); x0 <= Math.max(line.start.x, line.end.x); x0++) {
                    grid.putIfAbsent(new Coord(x0, line.start.y), 0);
                    grid.compute(new Coord(x0, line.start.y), (k, v) -> v + 1);
                }
            }
        }
        return String.valueOf(grid.values().stream().filter(i -> i > 1).count());
    }

    @Override
    public String part2(String inputPath) {
        var lines = ImportUtils.getListStringUnParLigne(inputPath).stream().map(l -> parseLine(l)).toList();
        var grid = new HashMap<Coord, Integer>();
        for (var line : lines) {
            var gapX = -Integer.compare(line.start.x, line.end.x);
            var gapY = -Integer.compare(line.start.y, line.end.y);
            var x0 = line.start.x;
            var y0 = line.start.y;
            while (true) {
                grid.putIfAbsent(new Coord(x0, y0), 0);
                grid.compute(new Coord(x0, y0), (k, v) -> v + 1);
                x0 += gapX;
                y0 += gapY;
                if (x0 == line.end.x && y0 == line.end.y) {
                    grid.putIfAbsent(new Coord(x0, y0), 0);
                    grid.compute(new Coord(x0, y0), (k, v) -> v + 1);
                    break;
                }
            }
        }
        return String.valueOf(grid.values().stream().filter(i -> i > 1).count());
    }

    private record Coord(int x, int y) {
    }

    private record Line(Coord start, Coord end) {
    }

}

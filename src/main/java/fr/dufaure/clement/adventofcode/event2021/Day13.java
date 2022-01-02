package fr.dufaure.clement.adventofcode.event2021;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import fr.dufaure.clement.adventofcode.utils.Day;
import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class Day13 implements Day {

    private Page readInput(String input) {
        var pCoord = Pattern.compile("([0-9]+),([0-9]+)");
        var pInstruction = Pattern.compile("fold along ([xy])=([0-9]+)");
        var mCoord = pCoord.matcher(input);
        var mInstruction = pInstruction.matcher(input);
        Set<Coord> s = new HashSet<>();
        List<Instruction> i = new ArrayList<>();
        while (mCoord.find()) {
            s.add(new Coord(Integer.parseInt(mCoord.group(1)), Integer.parseInt(mCoord.group(2))));
        }
        while (mInstruction.find()) {
            i.add(new Instruction(mInstruction.group(1).charAt(0), Integer.parseInt(mInstruction.group(2))));
        }
        return new Page(s, i);
    }

    private record Coord(int x, int y) {
    }

    private record Instruction(char axe, int value) {
        public Instruction {
            if (axe != 'x' && axe != 'y') {
                throw new UnsupportedOperationException();
            }
        }
    }

    private record Page(Set<Coord> dots, List<Instruction> instructions) {
    }

    @Override
    public String part1(String inputPath) {
        var p = readInput(ImportUtils.getStringWithNewLine(inputPath));
        var grid = p.dots;
        var i = p.instructions.get(0);
        grid = computeFold(grid, i);
        return String.valueOf(grid.size());
    }

    private Set<Coord> computeFold(Set<Coord> grid, Instruction i) {
        var tempGrid = new HashSet<Coord>();
        if (i.axe() == 'x') {
            for (var d : grid) {
                if (d.x() < i.value()) {
                    tempGrid.add(d);
                } else {
                    tempGrid.add(new Coord(i.value() - (d.x() - i.value()), d.y()));
                }
            }
        } else {
            for (var d : grid) {
                if (d.y() < i.value()) {
                    tempGrid.add(d);
                } else {
                    tempGrid.add(new Coord(d.x(), i.value() - (d.y() - i.value())));
                }
            }
        }
        return tempGrid;
    }

    private String printGrid(Set<Coord> grid) {
        var sb = new StringBuffer();
        var xMax = grid.stream().mapToInt(d -> d.x()).max().getAsInt();
        var yMax = grid.stream().mapToInt(d -> d.y()).max().getAsInt();
        for (var y = 0; y <= yMax; y++) {
            for (var x = 0; x <= xMax; x++) {
                sb.append(grid.contains(new Coord(x, y)) ? "#" : ".");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public String part2(String inputPath) {
        var p = readInput(ImportUtils.getStringWithNewLine(inputPath));
        var grid = p.dots;
        for (var i : p.instructions) {
            grid = computeFold(grid, i);
        }
        System.out.println(printGrid(grid));
        return printGrid(grid);
    }

}

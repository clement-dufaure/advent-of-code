package fr.dufaure.clement.adventofcode.event2021;

import java.util.regex.Pattern;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class Day2 {

    private Command parseCommand(String command) {
        var p = Pattern.compile("([a-z]*) ([0-9]*)");
        var m = p.matcher(command);
        if (m.matches()) {
            return new Command(Direction.valueOf(m.group(1).toUpperCase()), Integer.valueOf(m.group(2)));
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public String part1(String input) {
        var commands = ImportUtils.getListStringUnParLigne(input).stream()
                .map(s -> parseCommand(s)).toList();
        var submarinePosition = new Position();
        commands.forEach(submarinePosition::apply);
        return String.valueOf(submarinePosition.depth * submarinePosition.horizontalPosition);
    }

    public String part2(String input) {
        var commands = ImportUtils.getListStringUnParLigne(input).stream()
                .map(s -> parseCommand(s)).toList();
        var submarinePosition = new AimPosition();
        commands.forEach(submarinePosition::apply);
        return String.valueOf(submarinePosition.depth * submarinePosition.horizontalPosition);
    }

    private record Command(Direction direction, int units) {
    };

    private enum Direction {
        FORWARD, DOWN, UP;
    }

    private class Position {
        int depth;
        int horizontalPosition;

        public void apply(Command command) {
            switch (command.direction) {
                case FORWARD -> horizontalPosition += command.units;
                case DOWN -> depth += command.units;
                case UP -> depth -= command.units;
            }
        }
    }

    private class AimPosition {
        int aim;
        int depth;
        int horizontalPosition;

        public void apply(Command command) {
            switch (command.direction) {
                case FORWARD -> {
                    horizontalPosition += command.units;
                    depth += aim * command.units;
                }
                case DOWN -> aim += command.units;
                case UP -> aim -= command.units;
            }
        }
    }

}

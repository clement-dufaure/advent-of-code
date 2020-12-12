package fr.dufaure.clement.adventofcode.event2020;

import fr.dufaure.clement.adventofcode.utils.Direction;
import fr.dufaure.clement.adventofcode.utils.ImportUtils;
import fr.dufaure.clement.adventofcode.utils.StateGrid.Coord;

public class day12 {

    public static void main(String[] args) {
        part1();
        part2();
    }

    public static void part1() {
        State state = new State();
        state.coord = Coord.of(0, 0);
        state.directionLooked = Direction.E;
        ImportUtils.getListStringUnParLigne("./src/main/resources/2020/day12").stream().map(day12::parseStep)
                .forEach(step -> apply(state, step));
        System.out.println(Math.abs(state.coord.x) + Math.abs(state.coord.y));
    }

    public static void part2() {
        State state = new State();
        state.coord = Coord.of(0, 0);
        state.coordWayPoint = Coord.of(10, 1);
        ImportUtils.getListStringUnParLigne("./src/main/resources/2020/day12").stream().map(day12::parseStep)
                .forEach(step -> applyByWaypoint(state, step));
        System.out.println(Math.abs(state.coord.x) + Math.abs(state.coord.y));

    }

    static Step parseStep(String str) {
        Step s = new Step();
        s.instruction = Instruction.valueOf("" + str.charAt(0));
        s.value = Integer.valueOf(str.substring(1));
        return s;
    }

    static void apply(State state, Step step) {
        switch (step.instruction) {
            case N:
                state.coord.y += step.value;
                break;
            case S:
                state.coord.y -= step.value;
                break;
            case E:
                state.coord.x += step.value;
                break;
            case W:
                state.coord.x -= step.value;
                break;
            case R:
                state.directionLooked = Direction.turnRight(state.directionLooked, step.value);
                break;
            case L:
                state.directionLooked = Direction.turnLeft(state.directionLooked, step.value);
                break;
            case F:
                state.coord.x += step.value * state.directionLooked.diffX;
                state.coord.y += step.value * state.directionLooked.diffY;
                break;
        }
    }

    static void applyByWaypoint(State state, Step step) {
        switch (step.instruction) {
            case N:
                state.coordWayPoint.y += step.value;
                break;
            case S:
                state.coordWayPoint.y -= step.value;
                break;
            case E:
                state.coordWayPoint.x += step.value;
                break;
            case W:
                state.coordWayPoint.x -= step.value;
                break;
            case R:
                state.coordWayPoint = turnWaypointRight(state.coordWayPoint, step.value);
                break;
            case L:
                state.coordWayPoint = turnWaypointLeft(state.coordWayPoint, step.value);
                break;
            case F:
                state.coord.x += step.value * state.coordWayPoint.x;
                state.coord.y += step.value * state.coordWayPoint.y;
                break;
        }
    }

    public static Coord turnWaypointLeft90(Coord c) {
        Coord cNew = new Coord();
        cNew.x = -c.y;
        cNew.y = c.x;
        return cNew;
    }

    public static Coord turnWaypointLeft(Coord c, int angle) {
        if (angle % 90 != 0) {
            throw new UnsupportedOperationException();
        }
        int numberStep = angle / 90;
        for (int i = 0; i < numberStep; i++) {
            c = turnWaypointLeft90(c);
        }
        return c;
    }

    public static Coord turnWaypointRight90(Coord c) {
        Coord cNew = new Coord();
        cNew.y = -c.x;
        cNew.x = c.y;
        return cNew;
    }

    public static Coord turnWaypointRight(Coord c, int angle) {
        if (angle % 90 != 0) {
            throw new UnsupportedOperationException();
        }
        int numberStep = angle / 90;
        for (int i = 0; i < numberStep; i++) {
            c = turnWaypointRight90(c);
        }
        return c;
    }

    static class State {
        Coord coord;
        Direction directionLooked;
        Coord coordWayPoint;

        @Override
        public String toString() {
            return coord.toString() + "--" + coordWayPoint;
        }
    }

    static class Step {
        Instruction instruction;
        int value;

    }

    static enum Instruction {
        N, S, E, W, L, R, F;
    }

}

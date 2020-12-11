package fr.dufaure.clement.adventofcode.event2020;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day8 {

    public static void main(String[] args) {
        part1();
        part2();
    }

    private static void part1() {
        List<Instruction> program = ImportUtils.getListStringUnParLigne("./src/main/resources/2020/day8").stream()
                .map(Instruction::of).collect(Collectors.toList());
        try {
            tryRun(program);
        } catch (InfiniteLoopException e) {
            System.out.println(e.value);
        }

    }

    private static void part2() {
        List<Instruction> program = ImportUtils.getListStringUnParLigne("./src/main/resources/2020/day8").stream()
                .map(Instruction::of).collect(Collectors.toList());
        for (int positionToChange = 0; positionToChange < program.size(); positionToChange++) {
            if (program.get(positionToChange).operation.equals(Operation.JMP)) {
                program.get(positionToChange).operation = Operation.NOP;
            } else if (program.get(positionToChange).operation.equals(Operation.NOP)) {
                program.get(positionToChange).operation = Operation.JMP;
            } else {
                continue;
            }
            try {
                System.out.println(tryRun(program));
                return;
            } catch (InfiniteLoopException e) {
                // rollback
                if (program.get(positionToChange).operation.equals(Operation.JMP)) {
                    program.get(positionToChange).operation = Operation.NOP;
                } else if (program.get(positionToChange).operation.equals(Operation.NOP)) {
                    program.get(positionToChange).operation = Operation.JMP;
                }
                continue;
            }

        }
    }

    static long tryRun(List<Instruction> program) throws InfiniteLoopException {
        List<Integer> alreadyRun = new ArrayList<>();
        long accumulator = 0;
        int position = 0;
        while (true) {
            alreadyRun.add(position);
            Instruction i = program.get(position);
            switch (i.operation) {
                case ACC:
                    accumulator += i.value;
                    position++;
                    break;
                case JMP:
                    position += i.value;
                    break;
                case NOP:
                    position++;
                    break;
            }
            if (alreadyRun.contains(position)) {
                throw new InfiniteLoopException(accumulator);
            }
            if (position == program.size()) {
                return accumulator;
            }
        }
    }

    static class Instruction {
        Operation operation;
        int value;

        static Instruction of(String str) {
            Instruction i = new Instruction();
            i.operation = Operation.valueOf(str.split(" ")[0].toUpperCase());
            i.value = Integer.parseInt(str.split(" ")[1]);
            return i;
        }
    }

    static enum Operation {
        ACC, JMP, NOP;
    }

    @SuppressWarnings("serial")
    static class InfiniteLoopException extends Exception {
        long value;

        InfiniteLoopException(long value) {
            super("The program is an infinite loop");
            this.value = value;
        }

    }
}
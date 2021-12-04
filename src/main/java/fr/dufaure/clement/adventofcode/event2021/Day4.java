package fr.dufaure.clement.adventofcode.event2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class Day4 {

    private Game parseInput(String input) {
        var draw = new ArrayList<Integer>();
        var boards = new ArrayList<Board>();
        var pDraw = Pattern.compile("([0-9]*,?)*");
        var mDraw = pDraw.matcher(input);
        var pBoard = Pattern
                .compile("( *[0-9]+ *){5}\\n( *[0-9]+ *){5}\\n( *[0-9]+ *){5}\\n( *[0-9]+ *){5}\\n( *[0-9]+ *){5}");
        var mBoard = pBoard.matcher(input);

        if (mDraw.find()) {
            draw.addAll(Arrays.stream(mDraw.group(0).split(",")).map(s -> Integer.parseInt(s)).toList());
        }

        while (mBoard.find()) {
            var lines = new ArrayList<List<Case>>();
            for (var line : mBoard.group(0).split("\\n")) {
                lines.add(Arrays.stream(line.split(" +")).filter(s -> !s.isBlank())
                        .map(s -> new Case(Integer.parseInt(s))).toList());
            }
            boards.add(new Board(lines));
        }
        return new Game(draw, boards);
    }

    public String part1(String inputPath) {
        Game g = parseInput(ImportUtils.getStringWithNewLine(inputPath));
        Board winnerBoard = null;
        int winnerNumberDraw = 0;
        mainloop: for (var numberdraw : g.draw) {
            // mark new draw
            g.boards.stream().flatMap(b -> b.content.stream()).flatMap(l -> l.stream())
                    .filter(c -> c.number == numberdraw).forEach(c -> c.isMarked = true);

            // check for complete line
            for (var board : g.boards) {
                if (board.isWin()) {
                    winnerBoard = board;
                    winnerNumberDraw = numberdraw;
                    break mainloop;
                }
            }
        }

        // compute result
        var score = winnerBoard.content.stream().flatMap(l -> l.stream()).filter(c -> !c.isMarked)
                .mapToInt(c -> c.number).sum() * winnerNumberDraw;

        return String.valueOf(score);
    }

    public String part2(String inputPath) {
        Game g = parseInput(ImportUtils.getStringWithNewLine(inputPath));
        var remainingBoards = new ArrayList<Board>();
        var lastNumber = 0;
        remainingBoards.addAll(g.boards);
        mainloop: for (var numberdraw : g.draw) {
            // mark new draw
            g.boards.stream().flatMap(b -> b.content.stream()).flatMap(l -> l.stream())
                    .filter(c -> c.number == numberdraw).forEach(c -> c.isMarked = true);

            var localFinishBoards = new ArrayList<Board>();

            // check for complete line
            for (var board : remainingBoards) {
                if (board.isWin()) {
                    localFinishBoards.add(board);
                }
            }

            // if only one remaining don't remove and just wait it win
            if (remainingBoards.size() != 1) {
                remainingBoards.removeAll(localFinishBoards);
            } else if (remainingBoards.size() == 1 && remainingBoards.get(0).isWin()) {
                lastNumber = numberdraw;
                break mainloop;
            }

        }

        // compute result
        var score = remainingBoards.get(0).content.stream().flatMap(l -> l.stream()).filter(c -> !c.isMarked)
                .mapToInt(c -> c.number).sum() * lastNumber;

        return String.valueOf(score);
    }

    private record Game(List<Integer> draw, List<Board> boards) {
    }

    private record Board(List<List<Case>> content) {

        private boolean isWin() {
            for (var line = 0; line < this.content.size(); line++) {
                if (this.content.get(line).stream().filter(c -> !c.isMarked).count() == 0) {
                    return true;
                }
            }
            for (var column = 0; column < this.content.size(); column++) {
                final var ccolumn = column;
                if (this.content.stream().map(l -> l.get(ccolumn)).filter(c -> !c.isMarked).count() == 0) {
                    return true;
                }
            }
            return false;
        }

    }

    private class Case {
        final int number;
        boolean isMarked = false;

        public Case(int number) {
            this.number = number;
        }
    }

}

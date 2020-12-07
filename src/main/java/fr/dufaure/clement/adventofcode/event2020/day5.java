package fr.dufaure.clement.adventofcode.event2020;

import java.util.List;
import java.util.stream.Collectors;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day5 {

    public static void main(String[] args) {
        part1();
        part2();
    }

    private static void part1() {
        System.out.println(ImportUtils.getListStringUnParLigne("./src/main/resources/2020/day5").stream()
                .mapToInt(day5::findSeat).max().getAsInt());
    }

    private static void part2() {
        List<Integer> seats = ImportUtils.getListStringUnParLigne("./src/main/resources/2020/day5").stream()
                .mapToInt(day5::findSeat).sorted().boxed().collect(Collectors.toList());
        for (int i = 0; i < seats.size(); i++) {
            if (seats.get(i) + 2 == seats.get(i + 1)) {
                System.out.println(seats.get(i) + 1);
                return;
            }
        }
    }

    static int findSeat(String boardingPass) {
        int row = 0;
        int power = 1;
        for (int i = 0; i < 7; i++) {
            if (boardingPass.charAt(6 - i) == 'B') {
                row += power;
            }
            power *= 2;
        }

        int column = 0;
        power = 1;
        for (int i = 0; i < 3; i++) {
            if (boardingPass.charAt(9 - i) == 'R') {
                column += power;
            }
            power *= 2;
        }
        return row * 8 + column;
    }

}

package fr.dufaure.clement.adventofcode.event2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day13 {

    public static void main(String[] args) {
        part1();
        part2();
    }

    private static void part1() {
        List<String> data = ImportUtils.getListStringUnParLigne("./src/main/resources/2020/day13");
        int arrivalTime = Integer.parseInt(data.get(0));
        List<Integer> frequencies = Arrays.asList(data.get(1).split(",")).stream().filter(s -> !s.equals("x"))
                .map(Integer::parseInt).collect(Collectors.toList());
        int minWaitTime = Integer.MAX_VALUE;
        int idOfMinWaitTime = -1;
        for (int frequency : frequencies) {
            int waitTime = 0;
            if (arrivalTime % frequency != 0) {
                waitTime = frequency - arrivalTime % frequency;
            }
            if (waitTime < minWaitTime) {
                idOfMinWaitTime = frequency;
                minWaitTime = waitTime;
            }
        }
        System.out.println(idOfMinWaitTime * minWaitTime);
    }

    private static void part2() {
        List<String> data = ImportUtils.getListStringUnParLigne("./src/main/resources/2020/day13");
        List<Bus> buses = getFrom(data.get(1).split(",")).stream().collect(Collectors.toList());
        long timestamp = 0L;
        long timestampStepSize = buses.get(0).frequecy;
        for (int i = 1; i < buses.size(); i++) {
            while (true) {
                timestamp += timestampStepSize;
                if ((timestamp + buses.get(i).offsetWanted) % buses.get(i).frequecy == 0) {
                    timestampStepSize *= buses.get(i).frequecy;
                    break;
                }
            }
        }
        System.out.println(timestamp);
    }

    static List<Bus> getFrom(String[] buses) {
        List<Bus> list = new ArrayList<>();
        int offset = 0;
        for (String b : buses) {
            if (!b.equals("x")) {
                Bus bus = new Bus();
                bus.frequecy = Integer.parseInt(b);
                bus.offsetWanted = offset;
                list.add(bus);
            }
            offset++;
        }
        return list;
    }

    static class Bus {
        int frequecy;
        int offsetWanted;
    }

}

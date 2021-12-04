package fr.dufaure.clement.adventofcode.event2021;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class Day3 {

    public String part1(String input) {
        var binaryNumbers = ImportUtils.getListStringUnParLigne(input);
        var lineLength = binaryNumbers.get(0).length();
        var gammaRateBuffer = new StringBuffer();
        var epsilonRateBuffer = new StringBuffer();
        for (var i = 0; i < lineLength; i++) {
            final var ii = i;
            var numberOfOne = binaryNumbers.stream().map(s -> s.charAt(ii)).filter(c -> c.equals('1')).count();
            if (numberOfOne > binaryNumbers.size() - numberOfOne) {
                gammaRateBuffer.append("1");
                epsilonRateBuffer.append("0");
            } else {
                gammaRateBuffer.append("0");
                epsilonRateBuffer.append("1");
            }
        }
        var gammaRate = Integer.parseInt(gammaRateBuffer.toString(), 2);
        var epsiloRate = Integer.parseInt(epsilonRateBuffer.toString(), 2);
        return String.valueOf(gammaRate * epsiloRate);
    }

    public String part2(String input) {
        var binaryNumbers = ImportUtils.getListStringUnParLigne(input);
        var lineLength = binaryNumbers.get(0).length();
        var oxygenGeneratorRating = 0;
        var co2ScrubberRating = 0;
        var binaryNumbersFiltered = binaryNumbers;
        for (var i = 0; i < lineLength; i++) {
            final var ii = i;
            var numberOfOne = binaryNumbersFiltered.stream().map(s -> s.charAt(ii)).filter(c -> c.equals('1'))
                    .count();
            final var bitCriteria = numberOfOne >= binaryNumbersFiltered.size() - numberOfOne ? '1' : '0';
            binaryNumbersFiltered = binaryNumbersFiltered.stream().filter(s -> s.charAt(ii) == bitCriteria).toList();
            if (binaryNumbersFiltered.size() == 1) {
                oxygenGeneratorRating = Integer.parseInt(binaryNumbersFiltered.get(0), 2);
                break;
            }
        }
        binaryNumbersFiltered = binaryNumbers;
        for (var i = 0; i < lineLength; i++) {
            final var ii = i;
            var numberOfOne = binaryNumbersFiltered.stream().map(s -> s.charAt(ii)).filter(c -> c.equals('1'))
                    .count();
            final var bitCriteria = numberOfOne >= binaryNumbersFiltered.size() - numberOfOne ? '0' : '1';
            binaryNumbersFiltered = binaryNumbersFiltered.stream().filter(s -> s.charAt(ii) == bitCriteria).toList();
            if (binaryNumbersFiltered.size() == 1) {
                co2ScrubberRating = Integer.parseInt(binaryNumbersFiltered.get(0), 2);
                break;
            }
        }
        return String.valueOf(oxygenGeneratorRating * co2ScrubberRating);
    }

}

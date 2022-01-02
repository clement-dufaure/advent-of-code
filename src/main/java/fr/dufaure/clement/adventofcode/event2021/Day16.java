package fr.dufaure.clement.adventofcode.event2021;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

import fr.dufaure.clement.adventofcode.utils.Day;
import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class Day16 implements Day {

    @Override
    public String part1(String inputPath) {
        var transmission = readInput(ImportUtils.getString(inputPath));
        var p = readPacket(transmission);
        return String.valueOf(computeSumOfPacketVersion(p));
    }

    @Override
    public String part2(String inputPath) {
        var transmission = readInput(ImportUtils.getString(inputPath));
        var p = readPacket(transmission);
        return String.valueOf(computePacket(p));
    }

    private long computePacket(Packet p) {
        if (p instanceof Litteral l) {
            return l.value;
        } else if (p instanceof Operator o) {
            return switch (o.typeID) {
                case 0 -> o.subPackets.stream().mapToLong(subp -> computePacket(subp)).sum();
                case 1 -> o.subPackets.stream().mapToLong(subp -> computePacket(subp)).reduce(1, (a, b) -> a * b);
                case 2 -> o.subPackets.stream().mapToLong(subp -> computePacket(subp)).min().getAsLong();
                case 3 -> o.subPackets.stream().mapToLong(subp -> computePacket(subp)).max().getAsLong();
                case 5 -> computePacket(o.subPackets.get(0)) > computePacket(o.subPackets.get(1)) ? 1L : 0L;
                case 6 -> computePacket(o.subPackets.get(0)) < computePacket(o.subPackets.get(1)) ? 1L : 0L;
                case 7 -> computePacket(o.subPackets.get(0)) == computePacket(o.subPackets.get(1)) ? 1L : 0L;
                default -> throw new UnsupportedOperationException();
            };
        }
        throw new UnsupportedOperationException();
    }

    private int computeSumOfPacketVersion(Packet p) {
        if (p instanceof Litteral l) {
            return l.version;
        } else if (p instanceof Operator o) {
            return o.version + o.subPackets.stream().mapToInt(subp -> computeSumOfPacketVersion(subp)).sum();
        }
        throw new UnsupportedOperationException();
    }

    private Packet readPacket(String packet) {
        var version = Integer.parseInt(packet.substring(0, 3), 2);
        var typeID = Integer.parseInt(packet.substring(3, 6), 2);
        if (typeID == 4) {
            var i = 0;
            var stringValue = new StringBuffer();
            var effectiveLength = 6;
            while (true) {
                effectiveLength += 5;
                var next = packet.substring(6 + 5 * i, 11 + 5 * i);
                stringValue.append(next.substring(1));
                if (next.charAt(0) == '1') {
                    i++;
                } else if (next.charAt(0) == '0') {
                    break;
                } else {
                    throw new UnsupportedOperationException();
                }
            }
            var p = new Litteral();
            p.version = version;
            p.typeID = typeID;
            p.value = Long.parseLong(stringValue.toString(), 2);
            p.effectiveLength = effectiveLength;
            return p;
        } else {
            // operator
            var lengthTypeID = packet.substring(6, 7);
            var nextStartPacket = -1;
            OptionalInt numberOfSubPackets = OptionalInt.empty();
            OptionalInt totalLengthOfSubPackets = OptionalInt.empty();
            if (lengthTypeID.equals("0")) {
                totalLengthOfSubPackets = OptionalInt.of(Integer.parseInt(packet.substring(7, 7 + 15), 2));
                nextStartPacket = 7 + 15;
            } else if (lengthTypeID.equals("1")) {
                numberOfSubPackets = OptionalInt.of(Integer.parseInt(packet.substring(7, 7 + 11), 2));
                nextStartPacket = 7 + 11;
            } else {
                throw new UnsupportedOperationException();
            }
            var p = new Operator();
            p.version = version;
            p.typeID = typeID;
            p.subPackets = new ArrayList<>();
            var currentNumberOfSubPacket = 0;
            var currentLengthOfSubPackets = 0;
            var headerLength = nextStartPacket;
            while (true) {
                var subp = readPacket(packet.substring(nextStartPacket));
                p.subPackets.add(subp);
                nextStartPacket += subp.effectiveLength;
                currentLengthOfSubPackets += subp.effectiveLength;
                currentNumberOfSubPacket++;
                if (numberOfSubPackets.isPresent()) {
                    if (currentNumberOfSubPacket == numberOfSubPackets.getAsInt()) {
                        break;
                    }
                } else if (totalLengthOfSubPackets.isPresent()) {
                    if (currentLengthOfSubPackets == totalLengthOfSubPackets.getAsInt()) {
                        break;
                    }
                    if (currentLengthOfSubPackets > totalLengthOfSubPackets.getAsInt()) {
                        throw new UnsupportedOperationException();
                    }
                }
            }
            p.effectiveLength = headerLength + currentLengthOfSubPackets;
            return p;
        }
    }

    private sealed abstract class Packet permits Litteral,Operator {
        int version;
        int typeID;
        int effectiveLength;
    }

    private final class Litteral extends Packet {
        long value;
    }

    private final class Operator extends Packet {
        List<Packet> subPackets;
    }

    private String readInput(String input) {
        var sb = new StringBuffer();
        for (var c : input.toCharArray()) {
            sb.append(HexToBin(c));
        }
        return sb.toString();
    }

    private String HexToBin(char c) {
        return switch (c) {
            case '0' -> "0000";
            case '1' -> "0001";
            case '2' -> "0010";
            case '3' -> "0011";
            case '4' -> "0100";
            case '5' -> "0101";
            case '6' -> "0110";
            case '7' -> "0111";
            case '8' -> "1000";
            case '9' -> "1001";
            case 'A' -> "1010";
            case 'B' -> "1011";
            case 'C' -> "1100";
            case 'D' -> "1101";
            case 'E' -> "1110";
            case 'F' -> "1111";
            default -> throw new UnsupportedOperationException();
        };
    }

}

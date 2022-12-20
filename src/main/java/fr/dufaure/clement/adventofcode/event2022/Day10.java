package fr.dufaure.clement.adventofcode.event2022;

import fr.dufaure.clement.adventofcode.utils.Day;
import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class Day10 implements Day {
	@Override
	public String part1(String inputPath) {
		var instructions = ImportUtils.getListStringUnParLigne(inputPath);
		var clockCircuit = ClockCircuit.get();
		clockCircuit.execute(ClockCircuit.Program.fromInputString(instructions));
		var sumSignalLength = clockCircuit.getSignalStrength().stream().mapToInt(i -> i).sum();
		return String.valueOf(sumSignalLength);
	}
	
	@Override
	public String part2(String inputPath) {
		var instructions = ImportUtils.getListStringUnParLigne(inputPath);
		var clockCircuit = ClockCircuit.get();
		clockCircuit.execute(ClockCircuit.Program.fromInputString(instructions));
		var screen = clockCircuit.getScreen();
		System.out.println(screen);
		return screen;
	}
}

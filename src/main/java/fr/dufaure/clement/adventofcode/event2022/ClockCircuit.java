package fr.dufaure.clement.adventofcode.event2022;

import java.util.ArrayList;
import java.util.List;

public class ClockCircuit {
	
	private static ClockCircuit clockCircuit;
	private final List<Integer> signalStrength = new ArrayList<>();
	private final List<Pixel> screen = new ArrayList<>();
	private int cycle = 0;
	private int registryX = 1;
	
	private ClockCircuit() {
		for (int i = 0; i < 240; i++) {
			screen.add(Pixel.DARK);
		}
	}
	
	public static ClockCircuit get() {
		if (clockCircuit == null) {
			clockCircuit = new ClockCircuit();
		}
		return clockCircuit;
	}
	
	public void execute(Program program) {
		program.instructions().forEach(this::apply);
	}
	
	public List<Integer> getSignalStrength() {
		return signalStrength;
	}
	
	public String getScreen() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 40; j++) {
				sb.append(screen.get(i * 40 + j));
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	private void apply(Instruction instruction) {
		switch (instruction) {
			case Noop noop -> {
				increaseCycle();
			}
			case AddX addX -> {
				increaseCycle();
				increaseCycle();
				registryX += addX.value;
			}
			default -> throw new UnsupportedOperationException();
		}
	}
	
	private void increaseCycle() {
		cycle++;
		writeScreen();
		if ((cycle + 20) % 40 == 0) {
			signalStrength.add(cycle * registryX);
		}
	}
	
	private void writeScreen() {
		var crtPosition = cycle - 1;
		var crtPositionLine = crtPosition % 40;
		if (crtPosition < screen.size() &&
				  (crtPositionLine == registryX || crtPositionLine == registryX - 1 || crtPositionLine == registryX + 1)) {
			screen.set(crtPosition, Pixel.LIGHT);
		}
	}
	
	private enum Pixel {
		DARK("."), LIGHT("#");
		
		String stringRepresentation;
		
		private Pixel(String stringRepresentation) {
			this.stringRepresentation = stringRepresentation;
		}
		
		@Override
		public String toString() {
			return stringRepresentation;
		}
	}
	
	public record Program(List<Instruction> instructions) {
		
		static Program fromInputString(List<String> input) {
			List<Instruction> instructions = new ArrayList<>();
			for (var str : input) {
				var ligne = str.split(" ");
				switch (ligne[0]) {
					case "noop" -> instructions.add(new Noop());
					case "addx" -> instructions.add(new AddX(Integer.parseInt(ligne[1])));
					default -> throw new UnsupportedOperationException();
				}
			}
			return new Program(instructions);
		}
	}
	
	public static sealed class Instruction permits AddX, Noop {
	}
	
	public static final class AddX extends Instruction {
		private int value;
		
		AddX(int value) {
			this.value = value;
		}
		
	}
	
	public static final class Noop extends Instruction {
	
	}
	
}

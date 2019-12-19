package fr.dufaure.clement.adventofcode.event2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day17 {

	static final boolean displayMode = false;

	public static void main(String[] args) {
		long start1 = System.currentTimeMillis();
		part1();
		System.out.println("Execution part 1 en " + (System.currentTimeMillis() - start1) + " ms");
		long start2 = System.currentTimeMillis();
		part2();
		System.out.println("Execution part 2 en " + (System.currentTimeMillis() - start2) + " ms");
	}

	static Map<Coord, String> map = new HashMap<>();

	// 6687 too high
	static void part1() {
		map = new HashMap<>();
		Ascii ascii = new Ascii(false);
		readOutput(ascii.runProgramme(null));
		afficherScreen();
		System.out.println(map.keySet().stream().filter(day17::isCroisement).mapToInt(c -> c.x * c.y).sum());

	}

	static void part2() {

		// find the path scratch
		Coord botPosition = map.keySet().stream().filter(day17::isBotPosition).findFirst().get();
		Direction botDirection = getBotDirection(botPosition);

		List<String> instructions = new ArrayList<String>();

		while (true) {
			int nombrePas = 0;
			if (map.getOrDefault(getRight(botPosition, botDirection), ".").equals("#")) {
				botDirection = turnRight(botDirection);
				instructions.add("R");
			} else if (map.getOrDefault(getLeft(botPosition, botDirection), ".").equals("#")) {
				botDirection = turnLeft(botDirection);
				instructions.add("L");
			} else {
				// on est au bout
				break;
			}

			while (true) {
				Coord coordDevant = avancer(botPosition, botDirection);
				if (map.getOrDefault(coordDevant, "").equals("#")) {
					botPosition = coordDevant;
					nombrePas++;
				} else {
					instructions.add(String.valueOf(nombrePas));
					break;
				}
			}
		}

		// System.out.println(instructions);
		// System.out.println(instructions.size());// 72
		// Solution a la main...
		List<String> A = instructions.subList(0, 8);
		List<String> B = instructions.subList(8, 14);
		List<String> C = instructions.subList(28, 36);

//		A R, 4, R, 10, R, 8, R, 4, 
//		B R, 10, R, 6, R, 4
//		A R, 4, R, 10, R, 8, R, 4, 
//		B R, 10, R, 6, R, 4,
//		C R, 4, L, 12, R, 6, L, 12, 
//		B R, 10, R, 6, R, 4,
//		C R, 4, L, 12, R, 6, L, 12,
//		A R, 4, R, 10, R, 8, R, 4, 
//		B R, 10, R, 6, R, 4,
//		C R, 4, L, 12, R, 6, L, 12

		// check
		List<String> reconstitue = new ArrayList<>();
		reconstitue.addAll(A);
		reconstitue.addAll(B);
		reconstitue.addAll(A);
		reconstitue.addAll(B);
		reconstitue.addAll(C);
		reconstitue.addAll(B);
		reconstitue.addAll(C);
		reconstitue.addAll(A);
		reconstitue.addAll(B);
		reconstitue.addAll(C);

		System.out.println(reconstitue.equals(instructions));

		List<Long> sequence = Arrays.asList('A', ',', 'B', ',', 'A', ',', 'B', ',', 'C', ',', 'B', ',', 'C', ',', 'A',
				',', 'B', ',', 'C', '\n').stream().map(s -> (long) (int) s).collect(Collectors.toList());

		sequence.addAll(getListLongFromListString(A));
		sequence.addAll(getListLongFromListString(B));
		sequence.addAll(getListLongFromListString(C));
		// say no to display
		sequence.add((long) (int) 'n');
		sequence.add((long) (int) '\n');

		Ascii ascii = new Ascii(true);
		List<Long> out = ascii.runProgramme(sequence);
		System.out.println(out.get(out.size() - 1));

	}

	static List<Long> getListLongFromListString(List<String> liste) {
		List<Long> listeAscii = new ArrayList<>();
		for (int i = 0; i < liste.size() - 1; i++) {
			for (Character c : liste.get(i).toCharArray()) {
				listeAscii.add((long) (int) c);
			}
			listeAscii.add((long) (int) ',');
		}
		for (Character c : liste.get(liste.size() - 1).toCharArray()) {
			listeAscii.add((long) (int) c);
		}
		listeAscii.add((long) (int) '\n');
		return listeAscii;
	}

	static boolean isBotPosition(Coord c) {
		return map.get(c).equals("<") || map.get(c).equals(">") || map.get(c).equals("^") || map.get(c).equals("v");
	}

	static Coord avancer(Coord botPosition, Direction botDirection) {
		switch (botDirection) {
		case UP:
			return new Coord(botPosition.x, botPosition.y - 1);
		case DOWN:
			return new Coord(botPosition.x, botPosition.y + 1);
		case RIGHT:
			return new Coord(botPosition.x + 1, botPosition.y);
		case LEFT:
			return new Coord(botPosition.x - 1, botPosition.y);
		default:
			throw new UnsupportedOperationException("error");
		}
	}

	static Coord getRight(Coord botPosition, Direction botDirection) {
		switch (botDirection) {
		case UP:
			return new Coord(botPosition.x + 1, botPosition.y);
		case DOWN:
			return new Coord(botPosition.x - 1, botPosition.y);
		case RIGHT:
			return new Coord(botPosition.x, botPosition.y + 1);
		case LEFT:
			return new Coord(botPosition.x, botPosition.y - 1);
		default:
			throw new UnsupportedOperationException("error");
		}
	}

	static Coord getLeft(Coord botPosition, Direction botDirection) {
		switch (botDirection) {
		case UP:
			return new Coord(botPosition.x - 1, botPosition.y);
		case DOWN:
			return new Coord(botPosition.x + 1, botPosition.y);
		case RIGHT:
			return new Coord(botPosition.x, botPosition.y - 1);
		case LEFT:
			return new Coord(botPosition.x, botPosition.y + 1);
		default:
			throw new UnsupportedOperationException("error");
		}
	}

	static Direction getBotDirection(Coord c) {
		switch (map.get(c)) {
		case "^":
			return Direction.UP;
		case "v":
			return Direction.DOWN;
		case ">":
			return Direction.RIGHT;
		case "<":
			return Direction.LEFT;
		default:
			throw new UnsupportedOperationException("error");
		}
	}

	static Direction turnRight(Direction botDirection) {
		switch (botDirection) {
		case UP:
			return Direction.RIGHT;
		case DOWN:
			return Direction.LEFT;
		case RIGHT:
			return Direction.DOWN;
		case LEFT:
			return Direction.UP;
		default:
			throw new UnsupportedOperationException("error");
		}
	}

	static Direction turnLeft(Direction botDirection) {
		switch (botDirection) {
		case UP:
			return Direction.LEFT;
		case DOWN:
			return Direction.RIGHT;
		case RIGHT:
			return Direction.UP;
		case LEFT:
			return Direction.DOWN;
		default:
			throw new UnsupportedOperationException("error");
		}
	}

	static enum Direction {
		UP, DOWN, LEFT, RIGHT;
	}

	static boolean isCroisement(Coord c) {
		return map.get(c).equals("#") && map.getOrDefault((new Coord(c.x - 1, c.y)), "Z").equals("#")
				&& map.getOrDefault(new Coord(c.x + 1, c.y), "").equals("#")
				&& map.getOrDefault(new Coord(c.x, c.y - 1), "").equals("#")
				&& map.getOrDefault(new Coord(c.x, c.y + 1), "").equals("#");
	}

	static void readOutput(List<Long> output) {
		int i = 0;
		int j = 0;
		for (long element : output) {
			if (element != 10) {
				map.put(new Coord(j, i), String.valueOf((char) element));
				j++;
			} else {
				j = 0;
				i++;
			}
		}
	}

	static void afficherScreen() {
		int maxX = map.keySet().stream().mapToInt(c -> c.x).max().orElseThrow(UnsupportedOperationException::new);
		int maxY = map.keySet().stream().mapToInt(c -> c.y).max().orElseThrow(UnsupportedOperationException::new);

		for (int i = 0; i <= Math.abs(maxY); i++) {
			for (int j = 0; j < Math.abs(maxX) + 1; j++) {
				System.out.print(map.get(new Coord(j, i)));
			}
			System.out.println();
		}
	}

	static class Coord {
		int x;
		int y;

		Coord(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Coord other = (Coord) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "(" + x + "," + y + ")";
		}

	}

	public static class Ascii {
		boolean stopped = false;
		long relativeBase = 0;
		int pointeur = 0;
		ArrayList<Long> listeCode = new ArrayList<>();
		Coord position = new Coord(0, 0);

		Ascii(boolean mvt) {
			String[] liste = ImportUtils.getString("./src/main/resources/2019/day17").split(",");

			for (int i = 0; i < liste.length; i++) {
				listeCode.add(Long.valueOf(liste[i]));
			}
			// on ajoute des 0 a la fin
			for (int i = 0; i < 2000; i++) {
				listeCode.add(0L);
			}
			if (mvt) {
				listeCode.set(0, 2L);
			}
		}

		public List<Long> runProgramme(List<Long> inputs) {
			int inputSet = 0;
			List<Long> output = new ArrayList<>();

			mainLoop: while (true) {
				long parameter1;
				long parameter2;
				switch (String.valueOf(listeCode.get(pointeur) % 100)) {
				case "1":
					listeCode.set(
							getWhereToWrite(listeCode, (listeCode.get(pointeur) % 100000) / 10000,
									listeCode.get(pointeur + 3), relativeBase).intValue(),
							getParameter(listeCode, (listeCode.get(pointeur) % 1000) / 100, listeCode.get(pointeur + 1),
									relativeBase)
									+ getParameter(listeCode, (listeCode.get(pointeur) % 10000) / 1000,
											listeCode.get(pointeur + 2), relativeBase));
					pointeur += 4;
					break;
				case "2":
					listeCode.set(
							getWhereToWrite(listeCode, (listeCode.get(pointeur) % 100000) / 10000,
									listeCode.get(pointeur + 3), relativeBase).intValue(),
							getParameter(listeCode, (listeCode.get(pointeur) % 1000) / 100, listeCode.get(pointeur + 1),
									relativeBase)
									* getParameter(listeCode, (listeCode.get(pointeur) % 10000) / 1000,
											listeCode.get(pointeur + 2), relativeBase));
					pointeur += 4;
					break;
				case "3":
					// if (input != null && !inputSet) {
					listeCode.set(getWhereToWrite(listeCode, (listeCode.get(pointeur) % 1000) / 100,
							listeCode.get(pointeur + 1), relativeBase).intValue(), inputs.get(inputSet));
					inputSet++;
					// pointeur += 2;
					// inputSet = true;
					// } else {
					// return output;
					// }
					// break;
				case "4":
					output.add(getParameter(listeCode, (listeCode.get(pointeur) % 1000) / 100,
							listeCode.get(pointeur + 1), relativeBase));
					pointeur += 2;
					break;
				case "5":
					parameter1 = getParameter(listeCode, (listeCode.get(pointeur) % 1000) / 100,
							listeCode.get(pointeur + 1), relativeBase);
					parameter2 = getParameter(listeCode, (listeCode.get(pointeur) % 10000) / 1000,
							listeCode.get(pointeur + 2), relativeBase);
					if (parameter1 != 0) {
						pointeur = (int) parameter2;
					} else {
						pointeur += 3;
					}
					break;
				case "6":
					parameter1 = getParameter(listeCode, (listeCode.get(pointeur) % 1000) / 100,
							listeCode.get(pointeur + 1), relativeBase);
					parameter2 = getParameter(listeCode, (listeCode.get(pointeur) % 10000) / 1000,
							listeCode.get(pointeur + 2), relativeBase);
					if (parameter1 == 0) {
						pointeur = (int) parameter2;
					} else {
						pointeur += 3;
					}
					break;
				case "7":
					listeCode.set(
							getWhereToWrite(listeCode, (listeCode.get(pointeur) % 100000) / 10000,
									listeCode.get(pointeur + 3), relativeBase).intValue(),
							getParameter(listeCode, (listeCode.get(pointeur) % 1000) / 100, listeCode.get(pointeur + 1),
									relativeBase) < getParameter(listeCode, (listeCode.get(pointeur) % 10000) / 1000,
											listeCode.get(pointeur + 2), relativeBase) ? 1L : 0L);
					pointeur += 4;
					break;
				case "8":
					listeCode.set(
							getWhereToWrite(listeCode, (listeCode.get(pointeur) % 100000) / 10000,
									listeCode.get(pointeur + 3), relativeBase).intValue(),
							getParameter(listeCode, (listeCode.get(pointeur) % 1000) / 100, listeCode.get(pointeur + 1),
									relativeBase)
											.equals(getParameter(listeCode, (listeCode.get(pointeur) % 10000) / 1000,
													listeCode.get(pointeur + 2), relativeBase)) ? 1L : 0L);
					pointeur += 4;
					break;
				case "9":
					relativeBase += getParameter(listeCode, (listeCode.get(pointeur) % 1000) / 100,
							listeCode.get(pointeur + 1), relativeBase);
					pointeur += 2;
					break;
				case "99":
					break mainLoop;
				default:
					System.err.println("Pas normal, opcode : " + listeCode.get(pointeur));
					throw new UnsupportedOperationException();
				}
			}
			stopped = true;
			return output;
		}

		static Long getParameter(List<Long> programme, long parameterMode, Long value, Long relativeBase) {
			switch (String.valueOf(parameterMode)) {
			case "0":
				return programme.get(value.intValue());
			case "1":
				return value;
			case "2":
				return programme.get(relativeBase.intValue() + value.intValue());
			default:
				System.err.println("Invalide parameter mode : " + String.valueOf(parameterMode));
				throw new UnsupportedOperationException();
			}
		}

		static Long getWhereToWrite(List<Long> programme, long parameterMode, Long value, Long relativeBase) {
			switch (String.valueOf(parameterMode)) {
			case "0":
				return value;
			// "1":
			// return value;
			case "2":
				return relativeBase + value;
			default:
				System.err.println("Invalide parameter mode : " + String.valueOf(parameterMode));
				throw new UnsupportedOperationException();
			}
		}
	}

}

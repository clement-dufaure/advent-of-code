package fr.dufaure.clement.adventofcode.event2019;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day13 {

	public static void main(String[] args) {
		long start1 = System.currentTimeMillis();
		part1();
		System.out.println("Execution part 1 en " + (System.currentTimeMillis() - start1) + " ms");
		long start2 = System.currentTimeMillis();
		part2();
		System.out.println("Execution part 2 en " + (System.currentTimeMillis() - start2) + " ms");
	}

	static void part1() {
		Arcade a = new Arcade(false);
		String[] output = a.runProgramme(null).split("\\n");
		int i = 0;
		while (i < output.length) {
			screen.put(new Coord(Integer.parseInt(output[i]), Integer.parseInt(output[i + 1])),
					Integer.parseInt(output[i + 2]));
			i += 3;
		}
		System.out.println(screen.values().stream().filter(tileId -> tileId == 2).count());
	}

	static void part2() {
		screen = new HashMap<>();
		Arcade a = new Arcade(true);
		// init
		adjustScreen(a.runProgramme(null));
		afficherScreen();
		Random r = new Random();
		while (!a.stopped) {
			long mvt = (long) r.nextInt(3) - 1;
			adjustScreen(a.runProgramme(mvt));
			afficherScreen();
		}

	}

	static void adjustScreen(String outputStr) {
		String[] output = outputStr.split("\\n");
		int i = 0;
		while (i < output.length) {
			if (Integer.parseInt(output[i]) >= 0) {
				screen.put(new Coord(Integer.parseInt(output[i]), Integer.parseInt(output[i + 1])),
						Integer.parseInt(output[i + 2]));
			} else if (Integer.parseInt(output[i]) == -1 && Integer.parseInt(output[i + 1]) == 0) {
				segmentDisplay = Integer.parseInt(output[i + 2]);
			} else {
				throw new UnsupportedOperationException("output impossible");
			}
			i += 3;
		}
	}

	static void afficherScreen() {
		System.out.println("SCORE : " + segmentDisplay + "  BLOCKS RESTANTS : "
				+ screen.values().stream().filter(tileId -> tileId == 2).count());
		int maxX = screen.keySet().stream().mapToInt(c -> c.x).max().orElseThrow(UnsupportedOperationException::new);
		int maxY = screen.keySet().stream().mapToInt(c -> c.y).max().orElseThrow(UnsupportedOperationException::new);

		for (int i = 0; i <= Math.abs(maxY); i++) {
			for (int j = 0; j <= Math.abs(maxX); j++) {
				if (screen.containsKey(new Coord(j, i))) {
					switch (screen.get(new Coord(j, i))) {
					case 0:
						System.out.print(".");
						break;
					case 1:
						System.out.print("#");
						break;
					case 2:
						System.out.print("B");
						break;
					case 3:
						System.out.print("-");
						break;
					case 4:
						System.out.print("O");
						break;
					default:
						break;
					}
				} else {
					System.out.print(".");
				}
			}
			System.out.println();
		}
	}

	static Map<Coord, Integer> screen = new HashMap<>();
	static int segmentDisplay = 0;

	static enum Tile {
		EMPTY(0), WALL(1), BLOCK(2), PADDLE(3), BALL(4);

		int id;

		Tile(int id) {
			this.id = id;
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

	}

	public static class Arcade {

		boolean stopped = false;
		long relativeBase = 0;
		int pointeur = 0;
		ArrayList<Long> listeCode = new ArrayList<>();

		Arcade(boolean playForFree) {
			String[] liste = ImportUtils.getString("./src/main/resources/2019/day13").split(",");

			for (int i = 0; i < liste.length; i++) {
				listeCode.add(Long.valueOf(liste[i]));
			}
			// on ajoute des 0 a la fin
			for (int i = 0; i < 1000; i++) {
				listeCode.add(0L);
			}
			if (playForFree) {
				listeCode.set(0, 2L);
			}
		}

		public String runProgramme(Long input) {
			boolean inputSet = false;
			StringBuffer output = new StringBuffer();

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
					// if (playForFree) {
					if (input != null && !inputSet) {
						listeCode.set(getWhereToWrite(listeCode, (listeCode.get(pointeur) % 1000) / 100,
								listeCode.get(pointeur + 1), relativeBase).intValue(), (long) input);
						pointeur += 2;
						inputSet = true;
					} else {
						return output.toString();
					}
					break;
				// } else {
				// throw new UnsupportedOperationException("Pas d'input");
				// }
				case "4":
					output.append(getParameter(listeCode, (listeCode.get(pointeur) % 1000) / 100,
							listeCode.get(pointeur + 1), relativeBase) + "\n");
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
			return output.toString();
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

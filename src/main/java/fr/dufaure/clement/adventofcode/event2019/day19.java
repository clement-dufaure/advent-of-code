package fr.dufaure.clement.adventofcode.event2019;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day19 {

	static final boolean displayMode = false;

	public static void main(String[] args) {
		long start1 = System.currentTimeMillis();
		// part1();
		System.out.println("Execution part 1 en " + (System.currentTimeMillis() - start1) + " ms");
		long start2 = System.currentTimeMillis();
		part2();
		System.out.println("Execution part 2 en " + (System.currentTimeMillis() - start2) + " ms");
	}

	static Map<Coord, Integer> map = new HashMap<>();

	static void part1() {
		map = new HashMap<>();
		Drone d = new Drone();
		for (int i = 0; i < 50; i++) {
			for (int j = i - i / 4; j <= i; j++) {
				d = new Drone();
				map.put(new Coord(j, i), d.runProgramme(j, i));
			}
		}
		System.out.println();
		afficherScreen();
		System.out.println(map.values().stream().filter(i -> i == 1).count());
	}

	static void part2() {
		map = new HashMap<>();
		Drone d = new Drone();
		int i = -1;
		do {
			i++;
			if (i % 1000 == 0) {
				System.out.println("step " + i);
			}
			for (int j = i - i / 4; j <= i; j++) {
				d = new Drone();
				map.put(new Coord(j, i), d.runProgramme(j, i));
			}
		} while (!isSquareOk(i, 100));
		// afficherScreen();
		// System.out.println(i);
		System.out.println(angleSquareLePlusProche);
	}

	static Coord angleSquareLePlusProche;

	static boolean isSquareOk(int ligneMax, int squareSize) {
		// dernier ligne de la map , premier #, voir si hauteur au moins squareSize et
		// voir si
		// largeur a squareSize = squareSize
		Coord premierDieseDernierLigne = map.entrySet().stream().filter(e -> e.getKey().y == ligneMax)
				.filter(e -> e.getValue() == 1).map(Entry::getKey).sorted().findFirst().orElse(null);
		if (premierDieseDernierLigne != null) {
			Coord c = new Coord(premierDieseDernierLigne.x, premierDieseDernierLigne.y);
			boolean ok = true;
			int hauteur = 1;
			while (true) {
				c.y--;
				if (map.getOrDefault(new Coord(c.x, c.y), -1) == 1) {
					hauteur++;
				} else {
					ok = false;
					break;
				}

				if (hauteur == squareSize) {
					break;
				}
			}
			if (ok) {
				angleSquareLePlusProche = new Coord(c.x, c.y);
				int largeur = 1;
				while (true) {
					c.x++;
					if (map.getOrDefault(new Coord(c.x, c.y), -1) == 1) {
						largeur++;
					} else {
						ok = false;
						break;
					}

					if (largeur == squareSize) {
						break;
					}
				}
			}
			return ok;
		}
		return false;
	}

	static void afficherScreen() {
		int maxX = map.keySet().stream().mapToInt(c -> c.x).max().orElseThrow(UnsupportedOperationException::new);
		int maxY = map.keySet().stream().mapToInt(c -> c.y).max().orElseThrow(UnsupportedOperationException::new);

		for (int i = 0; i <= Math.abs(maxY); i++) {
			System.out.print(i);
			for (int j = 0; j < Math.abs(maxX) + 1; j++) {
				if (map.containsKey(new Coord(j, i))) {
					System.out.print(map.get(new Coord(j, i)) == 1 ? '#' : '.');
				} else {
					System.out.print('.');
				}
			}
			System.out.println();
		}
	}

	static class Coord implements Comparable<Coord> {
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

		@Override
		public int compareTo(Coord o) {
			return o.x < this.x ? 1 : -1;
		}

	}

	public static class Drone {
		boolean stopped = false;
		long relativeBase = 0;
		int pointeur = 0;
		ArrayList<Long> listeCode = new ArrayList<>();

		Drone() {
			String[] liste = ImportUtils.getString("./src/main/resources/2019/day19").split(",");

			for (int i = 0; i < liste.length; i++) {
				listeCode.add(Long.valueOf(liste[i]));
			}
			// on ajoute des 0 a la fin
			for (int i = 0; i < 2000; i++) {
				listeCode.add(0L);
			}
		}

		public int runProgramme(long inputx, long inputy) {
			boolean inputxSet = false;
			boolean inputySet = false;
			long output = -1;

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
					if (!inputxSet) {
						listeCode.set(getWhereToWrite(listeCode, (listeCode.get(pointeur) % 1000) / 100,
								listeCode.get(pointeur + 1), relativeBase).intValue(), inputx);
						inputxSet = true;
						pointeur += 2;
					} else if (!inputySet) {
						listeCode.set(getWhereToWrite(listeCode, (listeCode.get(pointeur) % 1000) / 100,
								listeCode.get(pointeur + 1), relativeBase).intValue(), inputy);
						inputySet = true;
						pointeur += 2;
					} else {
						throw new UnsupportedOperationException();
					}
					break;
				case "4":
					output = getParameter(listeCode, (listeCode.get(pointeur) % 1000) / 100,
							listeCode.get(pointeur + 1), relativeBase);
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
			return (int) output;
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

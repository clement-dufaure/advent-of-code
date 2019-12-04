package fr.dufaure.clement.adventofcode.event2019;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day3 {

	public static void main(String[] args) {
		long start1 = System.currentTimeMillis();
		part1();
		System.out.println("Execution part 1 en " + (System.currentTimeMillis() - start1) + " ms");
		long start2 = System.currentTimeMillis();
		part2();
		System.out.println("Execution part 2 en " + (System.currentTimeMillis() - start2) + " ms");
	}

	static Map<Coord, Boolean> casesParcourues = new HashMap<>();
	static Map<Coord, Integer> casesParcouruesAvecNombrePas = new HashMap<>();

	public static void part1() {
		List<String> liste = ImportUtils.getListStringUnParLigne("./src/main/resources/2019/day3");
		drawWire(liste.get(0).split(","), true);
		drawWire(liste.get(1).split(","), false);
		Coord min = casesParcourues.entrySet().stream().filter(e -> e.getValue()).map(e -> e.getKey()).sorted()
				.findFirst().get();
		System.out.println(Math.abs(min.x) + Math.abs(min.y));
	}

	public static void part2() {
		System.out.println(casesParcouruesAvecNombrePas.entrySet().stream().filter(e -> casesParcourues.get(e.getKey()))
				.map(e -> e.getValue()).sorted().findFirst().get());
	}

	static void drawWire(String[] parcours, boolean init) {
		Coord positionCourante = new Coord(0, 0);
		int numberOfSteps = 0;
		for (String step : parcours) {
			int distance = Integer.valueOf(step.substring(1));
			for (int i = 0; i < distance; i++) {
				numberOfSteps++;
				switch (step.charAt(0)) {
				case 'U':
					positionCourante.y++;
					break;
				case 'D':
					positionCourante.y--;
					break;
				case 'L':
					positionCourante.x--;
					break;
				case 'R':
					positionCourante.x++;
					break;
				default:
					System.err.println("Problem");
				}
				if (init) {
					casesParcourues.put(new Coord(positionCourante.x, positionCourante.y), false);
					casesParcouruesAvecNombrePas.put(new Coord(positionCourante.x, positionCourante.y), numberOfSteps);
				} else {
					Coord coordCourante = new Coord(positionCourante.x, positionCourante.y);
					if (casesParcourues.containsKey(coordCourante)) {
						// uniquement si on est pas encore passé
						if (!casesParcourues.get(coordCourante)) {
							casesParcourues.put(coordCourante, true);
							casesParcouruesAvecNombrePas.put(coordCourante,
									casesParcouruesAvecNombrePas.get(coordCourante) + numberOfSteps);
						}
					}
				}
			}
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
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj != null && obj instanceof Coord) {
				Coord castObj = (Coord) obj;
				return this.x == castObj.x && this.y == castObj.y;
			}
			return false;
		}

		// @Override
		// public int compareTo(Object obj) {

		// if (obj != null && obj instanceof Coord) {
		// Coord castObj = (Coord) obj;
		// if (this.x < castObj.x) {
		// return -1;
		// } else if (this.x > castObj.x) {
		// return 1;
		// } else {
		// if (this.y < castObj.y) {
		// return -1;
		// } else if (this.y > castObj.y) {
		// return 1;
		// } else {
		// return 0;
		// }
		// }
		// }
		// throw new UnsupportedOperationException();
		// }

		@Override
		public int hashCode() {
			return this.x * 100000 + this.y;
		}

		@Override
		public int compareTo(Coord obj) {

			if (obj != null) {
				Coord castObj = (Coord) obj;
				if (Math.abs(this.x) + Math.abs(this.y) < Math.abs(castObj.x) + Math.abs(castObj.y)) {
					return -1;
				}
				if (Math.abs(this.x) + Math.abs(this.y) > Math.abs(castObj.x) + Math.abs(castObj.y)) {
					return 1;
				}
				if (Math.abs(this.x) + Math.abs(this.y) == Math.abs(castObj.x) + Math.abs(castObj.y)) {
					return 0;
				}
			}
			throw new UnsupportedOperationException();
		}

		@Override
		public String toString() {
			return this.x + "-" + this.y;
		}
	}

}
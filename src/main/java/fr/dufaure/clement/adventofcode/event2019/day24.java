package fr.dufaure.clement.adventofcode.event2019;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day24 {

	public static void main(String[] args) {
		long start1 = System.currentTimeMillis();
		part1();
		System.out.println("Execution part 1 en " + (System.currentTimeMillis() - start1) + " ms");
		long start2 = System.currentTimeMillis();
		part2();
		System.out.println("Execution part 2 en " + (System.currentTimeMillis() - start2) + " ms");
	}

	static List<Long> listeDesRatingsDejaVus = new ArrayList<>();
	static int X;
	static int Y;
	static Map<Coord, Character> state = new HashMap<>();
	static Map<Coord, Character> newState = new HashMap<>();

	static void initialize() {
		state = new HashMap<>();
		List<String> liste = ImportUtils.getListStringUnParLigne("./src/main/resources/2019/day24");
		Y = liste.size();
		X = liste.get(0).length();
		for (int i = 0; i < liste.size(); i++) {
			for (int j = 0; j < liste.get(i).length(); j++) {
				state.put(new Coord(j, i), liste.get(i).charAt(j));
			}
		}
	}

	static void part1() {
		initialize();

		while (true) {
			newState = new HashMap<>();
			for (int x = 0; x < X; x++) {
				for (int y = 0; y < Y; y++) {
					char currentState = state.get(new Coord(x, y));
					int nombreBugsAutour = 0;
					nombreBugsAutour += state.getOrDefault(new Coord(x + 1, y), '.') == '#' ? 1 : 0;
					nombreBugsAutour += state.getOrDefault(new Coord(x - 1, y), '.') == '#' ? 1 : 0;
					nombreBugsAutour += state.getOrDefault(new Coord(x, y + 1), '.') == '#' ? 1 : 0;
					nombreBugsAutour += state.getOrDefault(new Coord(x, y - 1), '.') == '#' ? 1 : 0;

					if (currentState == '.' && (nombreBugsAutour == 1 || nombreBugsAutour == 2)) {
						newState.put(new Coord(x, y), '#');
					} else if (currentState == '#' && nombreBugsAutour != 1) {
						newState.put(new Coord(x, y), '.');
					} else {
						newState.put(new Coord(x, y), currentState);
					}
				}
			}
			state = newState;
			// calcul du rating
			int puissance = 1;
			long total = 0;
			for (int y = 0; y < Y; y++) {
				for (int x = 0; x < X; x++) {
					total += state.getOrDefault(new Coord(x, y), '.') == '#' ? puissance : 0;
					puissance *= 2;
				}
			}

			if (listeDesRatingsDejaVus.contains(total)) {
				System.out.println(total);
				break;
			} else {
				listeDesRatingsDejaVus.add(total);
			}

		}
	}

	static void part2() {
		initialize();
		Levels levels = new Levels();
		levels.put(0, state);

		for (int i = 0; i < 200; i++) {
			Levels newLevels = new Levels();

			int levelMax = levels.keySet().stream().max(Comparator.naturalOrder()).get();
			int levelMin = levels.keySet().stream().min(Comparator.naturalOrder()).get();
			levels.putNewLevel(levelMax + 1);
			levels.putNewLevel(levelMin - 1);

			for (int levelNumber : levels.keySet()) {
				Map<Coord, Character> levelCourant = levels.get(levelNumber);
				Map<Coord, Character> newLevel = new HashMap<>();

				for (int x = 0; x < X; x++) {
					for (int y = 0; y < Y; y++) {
						if (x == 2 && y == 2) {
							// niveau inferieur
							continue;
						}
						char currentState = levelCourant.get(new Coord(x, y));
						int nombreBugsAutour = 0;
						nombreBugsAutour += levelCourant.getOrDefault(new Coord(x + 1, y), '.') == '#' ? 1 : 0;
						nombreBugsAutour += levelCourant.getOrDefault(new Coord(x - 1, y), '.') == '#' ? 1 : 0;
						nombreBugsAutour += levelCourant.getOrDefault(new Coord(x, y + 1), '.') == '#' ? 1 : 0;
						nombreBugsAutour += levelCourant.getOrDefault(new Coord(x, y - 1), '.') == '#' ? 1 : 0;

						// possible niveau superieur
						if (x == 0) {
							nombreBugsAutour += levels.get(levelNumber + 1).get(new Coord(1, 2)) == '#' ? 1 : 0;
						}
						if (x == X - 1) {
							nombreBugsAutour += levels.get(levelNumber + 1).get(new Coord(3, 2)) == '#' ? 1 : 0;
						}
						if (y == 0) {
							nombreBugsAutour += levels.get(levelNumber + 1).get(new Coord(2, 1)) == '#' ? 1 : 0;
						}
						if (y == Y - 1) {
							nombreBugsAutour += levels.get(levelNumber + 1).get(new Coord(2, 3)) == '#' ? 1 : 0;
						}

						// possible niveau inferieur
						if (x == 1 && y == 2) {
							for (int y1 = 0; y1 < Y; y1++) {
								nombreBugsAutour += levels.get(levelNumber - 1).get(new Coord(0, y1)) == '#' ? 1 : 0;
							}
						}
						if (x == 3 && y == 2) {
							for (int y1 = 0; y1 < Y; y1++) {
								nombreBugsAutour += levels.get(levelNumber - 1).get(new Coord(X - 1, y1)) == '#' ? 1
										: 0;
							}
						}
						if (x == 2 && y == 1) {
							for (int x1 = 0; x1 < X; x1++) {
								nombreBugsAutour += levels.get(levelNumber - 1).get(new Coord(x1, 0)) == '#' ? 1 : 0;
							}
						}
						if (x == 2 && y == 3) {
							for (int x1 = 0; x1 < X; x1++) {
								nombreBugsAutour += levels.get(levelNumber - 1).get(new Coord(x1, Y - 1)) == '#' ? 1
										: 0;
							}
						}

						if (currentState == '.' && (nombreBugsAutour == 1 || nombreBugsAutour == 2)) {
							newLevel.put(new Coord(x, y), '#');
						} else if (currentState == '#' && nombreBugsAutour != 1) {
							newLevel.put(new Coord(x, y), '.');
						} else {
							newLevel.put(new Coord(x, y), currentState);
						}
					}
				}
				newLevels.put(levelNumber, newLevel);
			}
			levels = newLevels;
		}
		// count bug
		// System.out.println(levels);
		System.out.println(levels.values().stream().flatMap(e -> e.values().stream()).filter(c -> c == '#').count());
	}

	static class Levels extends HashMap<Integer, Map<Coord, Character>> {

		public void putNewLevel(int key) {
			if (!this.containsKey(key)) {
				Map<Coord, Character> level = new HashMap<>();
				for (int x = 0; x < X; x++) {
					for (int y = 0; y < Y; y++) {
						level.put(new Coord(x, y), '.');
					}
				}
				this.put(key, level);
			}
		}

		@Override
		public Map<Coord, Character> get(Object key) {
			if (!this.containsKey(key)) {
				Map<Coord, Character> level = new HashMap<>();
				for (int x = 0; x < X; x++) {
					for (int y = 0; y < Y; y++) {
						level.put(new Coord(x, y), '.');
					}
				}
				return level;
			}
			return super.get(key);
		}

		@Override
		public String toString() {
			StringBuffer sb = new StringBuffer();
			for (int level : this.keySet()) {
				System.out.println("LEVEL " + level);
				for (int y = 0; y < Y; y++) {
					for (int x = 0; x < X; x++) {
						System.out.print(this.get(level).getOrDefault(new Coord(x, y), '?'));
					}
					System.out.println();
				}
			}
			return sb.toString();
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

}

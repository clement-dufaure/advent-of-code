package fr.dufaure.clement.adventofcode.event2018;

public class day22 {

	public static void main(String[] args) {
		long start1 = System.currentTimeMillis();
		part1();
		System.out.println("Execution part 1 en " + (System.currentTimeMillis() - start1) + " ms");
		long start2 = System.currentTimeMillis();
		part2();
		System.out.println("Execution part 2 en " + (System.currentTimeMillis() - start2) + " ms");
	}

	// INPUT
//	static int X_DESTINATION = 15;
//	static int Y_DESTINATION = 740;
//	static long PROFONDEUR = 3558;
	static int X_DESTINATION = 10;
	static int Y_DESTINATION = 10;
	static long PROFONDEUR = 510;

	static long[][] grille = new long[X_DESTINATION][Y_DESTINATION];

	public static void part1() {
		for (int x = 0; x < X_DESTINATION; x++) {
			for (int y = 0; y < Y_DESTINATION; y++) {
				if ((x == 0 && y == 0) || (x == X_DESTINATION && y == Y_DESTINATION)) {
					grille[x][y] = 0;
				} else if (x == 0) {
					grille[x][y] = (y * 48271) % 20183;
				} else if (y == 0) {
					grille[x][y] = (x * 16807) % 20183;
				} else {
					grille[x][y] = grille[x - 1][y] * grille[x][y - 1] % 20183;
					// System.out.println(grille[x][y]);
				}
			}
		}

		long totalRisque = 0;
		for (int x = 0; x < X_DESTINATION; x++) {
			for (int y = 0; y < Y_DESTINATION; y++) {
				System.out.println((grille[x][y] + PROFONDEUR) % 20183 % 3);
				totalRisque += (grille[x][y] + PROFONDEUR) % 20183 % 3;
			}
		}
		System.out.println(totalRisque);
	}

	public static void part2() {

	}

}

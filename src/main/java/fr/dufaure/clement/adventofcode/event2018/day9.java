package fr.dufaure.clement.adventofcode.event2018;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class day9 {
	public static void main(String[] args) {
		long start1 = System.currentTimeMillis();
		part1();
		System.out.println("Execution part 1 en " + (System.currentTimeMillis() - start1) + " ms");
	}

	// input
	static final int NB_ELFE = 405;
	// input 1
	// static final int MAX_VALEUR_BILLES = 71700;
	// input 2
	static final int MAX_VALEUR_BILLES = 7170000;

	// la solution est trop grande pour integer :)
	static Long[] scores = new Long[NB_ELFE];
	static {
		for (int i = 0; i < scores.length; i++) {
			scores[i] = new Long(0);
		}
	}
	static List<Integer> billesEnPlace = new ArrayList<>();

	static void part1() {
		int currentPosition = 0;
		int joueurEncours = 0;

		// tour 0
		billesEnPlace.add(0);

		// tour 1
		joueurEncours = 0;
		billesEnPlace.add(1);
		currentPosition = 1;

		for (int billeEnCours = 2; billeEnCours <= MAX_VALEUR_BILLES; billeEnCours++) {
			if (billeEnCours % 10000 == 0) {
				System.out.println(billeEnCours);
			}
			joueurEncours = (joueurEncours + 1) % NB_ELFE;
			if (billeEnCours % 23 == 0) {
				scores[joueurEncours] = scores[joueurEncours] + billeEnCours;
				currentPosition = (currentPosition - 7 + billesEnPlace.size()) % billesEnPlace.size();
				scores[joueurEncours] = scores[joueurEncours] + billesEnPlace.get(currentPosition);
				billesEnPlace.remove(currentPosition);

			} else {
				currentPosition = (currentPosition + 2) % billesEnPlace.size();
				billesEnPlace.add(currentPosition, billeEnCours);
			}

		}
		System.out.println(Collections.max(Arrays.asList(scores)));

		// input 1
		// 428690

		// input 2 : environ 1h de calcul
		// 3628143500
	}

	// tentative autre methode -> plus long
//	static void part2() {
//		int joueurEncours = 0;
//
//		billesEnPlace.add(0);
//
//		for (int billeEnCours = 1; billeEnCours <= MAX_VALEUR_BILLES; billeEnCours++) {
//			joueurEncours = (joueurEncours + 1) % NB_ELFE;
//			if (billeEnCours % 10000 == 0) {
//				System.out.println(billeEnCours);
//			}
//			if (billeEnCours % 23 == 0) {
//				Collections.rotate(billesEnPlace, 7);
//
//				scores[joueurEncours] = scores[joueurEncours] + billeEnCours
//						+ billesEnPlace.remove(billesEnPlace.size() - 1);
//				Collections.rotate(billesEnPlace, -1);
//			} else {
//				Collections.rotate(billesEnPlace, -1);
//				billesEnPlace.add(billeEnCours);
//			}
//
//		}
//		System.out.println(Collections.max(Arrays.asList(scores)));
//	}

}

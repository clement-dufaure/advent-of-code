package fr.dufaure.clement.adventofcode.event2017;

import java.util.List;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day5 {

	public static void main(String[] args) {
		day4Part1();
		day4Part2();
	}

	public static void day4Part1() {
		List<Integer> data = ImportUtils.getListeEntierUnParLigne("./src/main/resources/2017/day5");
		int nbOperations = 0;
		int emplacementCourant = 0;
		int nouvelEmplacementCourant = 0;
		while (emplacementCourant >= 0 && emplacementCourant < data.size()) {
			nouvelEmplacementCourant = emplacementCourant + data.get(emplacementCourant);
			data.set(emplacementCourant, data.get(emplacementCourant) + 1);
			emplacementCourant = nouvelEmplacementCourant;
			nbOperations++;
		}
		System.out.println(nbOperations);
	}

	public static void day4Part2() {
		List<Integer> data = ImportUtils.getListeEntierUnParLigne("./src/main/resources/2017/day5");
		int nbOperations = 0;
		int emplacementCourant = 0;
		int nouvelEmplacementCourant = 0;
		while (emplacementCourant >= 0 && emplacementCourant < data.size()) {
			nouvelEmplacementCourant = emplacementCourant + data.get(emplacementCourant);
			if (data.get(emplacementCourant) >= 3) {
				data.set(emplacementCourant, data.get(emplacementCourant) - 1);
			} else {
				data.set(emplacementCourant, data.get(emplacementCourant) + 1);
			}
			emplacementCourant = nouvelEmplacementCourant;
			nbOperations++;
		}
		System.out.println(nbOperations);
	}

}

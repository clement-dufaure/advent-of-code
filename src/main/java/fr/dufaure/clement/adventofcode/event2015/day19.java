package fr.dufaure.clement.adventofcode.event2015;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day19 {

	public static void main(String[] args) {
		long start1 = System.currentTimeMillis();
		part1();
		System.out.println("Execution part 1 en " + (System.currentTimeMillis() - start1) + " ms");
		long start2 = System.currentTimeMillis();
		part2();
		System.out.println("Execution part 2 en " + (System.currentTimeMillis() - start2) + " ms");
	}

	static Map<String, List<String>> combinaisons = new HashMap<>();

	public static void part1() {
		List<String> liste = ImportUtils.getListStringUnParLigne("./src/main/resources/2015/day19test");
		String molecule = liste.get(liste.size() - 1);
		liste.remove(liste.size() - 1);
		liste.remove(liste.size() - 1);
		liste.forEach(day19::ajouterCombinaison);
		Set<String> moleculesModifiees = new HashSet<>();
		for (int i = 0; i < molecule.length(); i++) {
			if (combinaisons.containsKey("" + molecule.charAt(i))) {
				for (String possible : combinaisons.get("" + molecule.charAt(i))) {
					moleculesModifiees
							.add(molecule.substring(0, i) + possible + molecule.substring(i + 1, molecule.length()));
				}
			}
			if (i + 1 < molecule.length()
					&& combinaisons.containsKey(molecule.charAt(i) + "" + molecule.charAt(i + 1))) {
				for (String possible : combinaisons.get(molecule.charAt(i) + "" + molecule.charAt(i + 1))) {
					moleculesModifiees
							.add(molecule.substring(0, i) + possible + molecule.substring(i + 1, molecule.length()));
				}
			}
		}
		System.out.println(moleculesModifiees.size());
	}

	static void ajouterCombinaison(String s) {
		String[] elements = s.split(" => ");
		if (!combinaisons.containsKey(elements[0])) {
			combinaisons.put(elements[0], new ArrayList<>());
		}
		combinaisons.get(elements[0]).add(elements[1]);
	}

	public static void part2() {
	}

}
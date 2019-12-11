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
		part2try();
		System.out.println("Execution part 2 en " + (System.currentTimeMillis() - start2) + " ms");
	}

	static Map<String, List<String>> combinaisons = new HashMap<>();
	static Map<String, String> reverseCombinaisons = new HashMap<>();
	static List<String> reverseCombinaisonsOfElectron;
	static String molecule;

	static {
		List<String> liste = ImportUtils.getListStringUnParLigne("./src/main/resources/2015/day19");
		molecule = liste.get(liste.size() - 1);
		liste.remove(liste.size() - 1);
		liste.remove(liste.size() - 1);
		liste.forEach(day19::ajouterCombinaison);
		liste.forEach(day19::ajouterReverseCombinaison);
		reverseCombinaisonsOfElectron = combinaisons.get("e");

	}

	public static void part1() {
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
							.add(molecule.substring(0, i) + possible + molecule.substring(i + 2, molecule.length()));
				}
			}
		}
		System.out.println(moleculesModifiees.size());
	}

	public static void part2() {
		Set<String> moleculesReduites = new HashSet<>();
		moleculesReduites.add(molecule);
		int nombreSteps = 0;
		System.out.println(reverseCombinaisons);
		mainloop: while (true) {
			Set<String> nouvellesMoleculesReduites = new HashSet<>();
			for (String mol : moleculesReduites) {
				for (String comb : reverseCombinaisons.keySet()) {
					for (int i = 0; i < mol.length(); i++) {
						if (i + comb.length() <= mol.length() && mol.substring(i, i + comb.length()).equals(comb)) {
							String newmolreduite = mol.substring(0, i) + reverseCombinaisons.get(comb)
									+ mol.substring(i + comb.length(), mol.length());
							if (reverseCombinaisonsOfElectron.contains(newmolreduite)) {
								break mainloop;
							}
							nouvellesMoleculesReduites.add(newmolreduite);
						}
					}
				}
			}
			nombreSteps++;
			moleculesReduites = nouvellesMoleculesReduites;
			System.out.println(nombreSteps);
			System.out.println(moleculesReduites.size());
		}
		System.out.println(nombreSteps + 2);
	}

	// si on enleve une seule fois chaque combinaison a chaque boucle ca converge
	// si on part du string et qu'on reduit la molecule des qu'on trouve une
	// combinaison à réduire ca ne converge pas
	public static void part2try() {
		int nombreSteps = 0;
		String mol = molecule;
		mainloop: while (true) {
			for (String comb : reverseCombinaisons.keySet()) {
				if (mol.contains(comb)) {
					// Commencer par droite ou gauche revient au meme
					int position = mol.indexOf(comb);
					// int position = mol.lastIndexOf(comb);
					mol = mol.substring(0, position) + reverseCombinaisons.get(comb)
							+ mol.substring(position + comb.length(), mol.length());
					nombreSteps++;
					if (reverseCombinaisonsOfElectron.contains(mol)) {
						break mainloop;
					}
				}
			}
			System.out.println(nombreSteps);
			System.out.println("taille = " + mol.length());
		}
		System.out.println(nombreSteps + 1);
	}

	static void ajouterCombinaison(String s) {
		String[] elements = s.split(" => ");
		if (!combinaisons.containsKey(elements[0])) {
			combinaisons.put(elements[0], new ArrayList<>());
		}
		combinaisons.get(elements[0]).add(elements[1]);
	}

	static void ajouterReverseCombinaison(String s) {
		String[] elements = s.split(" => ");
		if (!elements[0].equals("e")) {
			reverseCombinaisons.put(elements[1], elements[0]);
		}
	}

}
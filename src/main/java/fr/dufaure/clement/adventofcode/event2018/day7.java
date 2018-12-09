package fr.dufaure.clement.adventofcode.event2018;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day7 {

	public static void main(String[] args) {
		long start1 = System.currentTimeMillis();
		part1();
		System.out.println("Execution part 1 en " + (System.currentTimeMillis() - start1) + " ms");
		long start2 = System.currentTimeMillis();
		part2();
		System.out.println("Execution part 2 en " + (System.currentTimeMillis() - start2) + " ms");
	}

	public static TreeMap<String, List<String>> etapes = new TreeMap<>();

	public static void parseLigne(String ligne) {
		Matcher matcher = Pattern.compile("Step (.) must be finished before step (.) can begin.").matcher(ligne);
		matcher.find();

		if (!etapes.containsKey(matcher.group(1))) {
			etapes.put(matcher.group(1), new ArrayList<>());
		}
		if (!etapes.containsKey(matcher.group(2))) {
			etapes.put(matcher.group(2), new ArrayList<>());
		}

		etapes.get(matcher.group(2)).add(matcher.group(1));

	}

	public static void part1() {
		List<String> data = ImportUtils.getListStringUnParLigne("./src/main/resources/2018/day7");
		data.forEach(day7::parseLigne);
		StringBuffer buff = new StringBuffer();

		while (etapes.keySet().size() > 0) {
			String etapeAFaire = etapes.entrySet().stream().filter(e -> e.getValue().size() == 0).findFirst().get()
					.getKey();
			buff.append(etapeAFaire);
			etapes.remove(etapeAFaire);
			for (String key : etapes.keySet()) {
				etapes.get(key).remove(etapeAFaire);
			}

		}

		System.out.println(buff.toString());

	}

	public static TreeMap<String, List<String>> etapesPart2 = new TreeMap<>();
	public static TreeMap<String, Integer> temps = new TreeMap<>();
	public static String[] etapesOuvreeParElfe = { ".", ".", ".", ".", "." };
	public static Integer[] tempsTravailRestantElfe = { 0, 0, 0, 0, 0 };

	public static boolean auMoinsUnElfeAuTravail() {
		for (String etape : etapesOuvreeParElfe) {
			if (!etape.equals(".")) {
				return true;
			}
		}
		return false;
	}

	public static boolean unElfeEstSurLecoup(String etape) {
		for (String s : etapesOuvreeParElfe) {
			if (s.equals(etape)) {
				return true;
			}
		}
		return false;
	}

	public static void donnerTravailElfes() {
		List<String> etapesAFaire = etapes.entrySet().stream().filter(e -> e.getValue().size() == 0)
				.map(e -> e.getKey()).collect(Collectors.toList());
		for (String etape : etapesAFaire) {
			if (!unElfeEstSurLecoup(etape)) {
				for (int i = 0; i < etapesOuvreeParElfe.length; i++) {
					if (etapesOuvreeParElfe[i].equals(".")) {
						etapesOuvreeParElfe[i] = etape;
						tempsTravailRestantElfe[i] = temps.get(etape);
						break;
					}
				}
			}
		}
	}

	public static void avancerDansTravail() {
		for (int i = 0; i < tempsTravailRestantElfe.length; i++) {
			if (tempsTravailRestantElfe[i] != 0) {
				tempsTravailRestantElfe[i] = tempsTravailRestantElfe[i] - 1;
			}
		}
	}

	public static void verifierTravailTermine() {
		for (int i = 0; i < tempsTravailRestantElfe.length; i++) {
			if (!etapesOuvreeParElfe[i].equals(".") && tempsTravailRestantElfe[i] == 0) {
				assembles.append(etapesOuvreeParElfe[i]);

				for (String key : etapes.keySet()) {
					etapes.get(key).remove(etapesOuvreeParElfe[i]);
				}
				etapes.remove(etapesOuvreeParElfe[i]);
				etapesOuvreeParElfe[i] = ".";
			}
		}
	}

	static StringBuffer assembles = new StringBuffer();

	public static void part2() {
		List<String> data = ImportUtils.getListStringUnParLigne("./src/main/resources/2018/day7");
		data.forEach(day7::parseLigne);

		int i = 1;
		char[] lettres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
		for (char c : lettres) {
			temps.put("" + c, 60 + i);
			i++;
		}

		int nbSecondes = 0;
		donnerTravailElfes();
		while (etapes.size() != 0) {
			avancerDansTravail();
			verifierTravailTermine();
			donnerTravailElfes();
			nbSecondes++;
		}
		System.out.println(nbSecondes);
	}

}

package fr.dufaure.clement.adventofcode.event2016;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day7 {

	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		long start1 = System.currentTimeMillis();
		part1();
		// 120 yoo high
		System.out.println("Execution part 1 en " + (System.currentTimeMillis() - start1) + " ms");
		long start2 = System.currentTimeMillis();
		part2();
		System.out.println("Execution part 2 en " + (System.currentTimeMillis() - start2) + " ms");
	}

	static void part1() {
		System.out.println(ImportUtils.getListStringUnParLigne("./src/main/resources/2016/day7").stream()
				.filter(day7::isTlsCompatible).count());
	}

	static void part2() {
		System.out.println(ImportUtils.getListStringUnParLigne("./src/main/resources/2016/day7").stream()
				.filter(day7::isSslCompatible).count());
	}

	static boolean isTlsCompatible(String ipv7) {
		Matcher m = Pattern.compile("\\[([a-z]*)\\]").matcher(ipv7);
		while (m.find()) {
			// verification absence chaine type abba dans les crochets
			if (contientABBA(m.group(1))) {
				return false;
			}
		}
		// verification presence chaine type abba hors des crochets
		for (String chaine : ipv7.split("\\[[a-z]*\\]")) {
			if (contientABBA(chaine)) {
				return true;
			}
		}
		return false;
	}

	static boolean contientABBA(String chaine) {
		if (chaine.length() < 4) {
			return false;
		}
		for (int index = 0; index <= chaine.length() - 4; index++) {
			if (isABBA(chaine.substring(index, index + 4))) {
				return true;
			}
		}
		return false;
	}

	static boolean isABBA(String chaine) {
		return chaine.length() == 4 && chaine.charAt(0) == chaine.charAt(3) && chaine.charAt(1) == chaine.charAt(2)
				&& chaine.charAt(0) != chaine.charAt(1);
	}

	static boolean isSslCompatible(String ipv7) {
		List<String> abas = new ArrayList<>();
		Matcher m = Pattern.compile("\\[([a-z]*)\\]").matcher(ipv7);
		while (m.find()) {
			// recuperation de tous les abas dans le crochet
			abas.addAll(getAllABAs(m.group(1)));
		}
		// inversion des abas en babs pour comparaison
		abas = abas.stream().map(day7::changeABAtoBAB).collect(Collectors.toList());
		// recherche de bab hors crochets
		for (String chaine : ipv7.split("\\[[a-z]*\\]")) {
			if (!Collections.disjoint(getAllABAs(chaine), abas)) {
				return true;
			}
		}
		return false;
	}

	static List<String> getAllABAs(String chaine) {
		List<String> maListe = new ArrayList<>();
		if (chaine.length() < 3) {
			return maListe;
		}
		for (int index = 0; index <= chaine.length() - 3; index++) {
			if (isABA(chaine.substring(index, index + 3))) {
				maListe.add(chaine.substring(index, index + 3));
			}
		}
		return maListe;
	}

	static boolean isABA(String chaine) {
		return chaine.length() == 3 && chaine.charAt(0) == chaine.charAt(2) && chaine.charAt(0) != chaine.charAt(1);
	}

	static String changeABAtoBAB(String aba) {
		return "" + aba.charAt(1) + aba.charAt(0) + aba.charAt(1);
	}

}
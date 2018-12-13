package fr.dufaure.clement.adventofcode.event2018;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day13 {
	public static void main(String[] args) {
		long start1 = System.currentTimeMillis();
		part1();
		System.out.println("Execution part 1 en " + (System.currentTimeMillis() - start1) + " ms");
	}

	static List<List<Rail>> reseau;
	static List<Wagonnet> wagonnets;

	static {
		// creation du reseau
		reseau = ImportUtils.getListStringUnParLigne("./src/main/resources/2018/day13").stream().map(day13::parseLigne)
				.collect(Collectors.toList());

		wagonnets = ImportUtils.getListStringUnParLigne("./src/main/resources/2018/day13").stream()
				.map(day13::rechercheWagonnetsLigne).flatMap(List::stream).collect(Collectors.toList());
	}

	static List<Rail> parseLigne(String ligne) {
		List<Rail> rails = new ArrayList<>();
		// on s'interesse au reseau :remplacement des wagonnets par des rails
		// correspondants
		ligne = ligne.replaceAll("[<>]", "-");
		ligne = ligne.replaceAll("[v^]", "|");
		for (char c : ligne.toCharArray()) {
			rails.add(Rail.getTypeRailFromChar(c));
		}
		return rails;
	}

	static List<Wagonnet> rechercheWagonnetsLigne(String ligne) {
		List<Wagonnet> wagonnets = new ArrayList<>();
		for (char c : ligne.toCharArray()) {
			if (c == '<' || c == '>' || c == '^' || c == 'v') {
				Wagonnet w = new Wagonnet();
				// w.x;
			}

		}
		return wagonnets;
	}

	static void part1() {

	}

	public static enum Rail {
		HORIZONTAL('|'), VERTICAL('-'), DIAGONAL('/'), DIAGONALBACK('\\'), CROISEMENT('+'), RIEN(' ');

		private char charactere;

		private Rail(char c) {
			charactere = c;
		}

		public static Rail getTypeRailFromChar(char c) {
			for (Rail r : Rail.values()) {
				if (r.charactere == c) {
					return r;
				}
			}
			System.err.println("problem");
			return Rail.RIEN;
		}
	}

	public static enum Orientation {
		UP, DOWN, LEFT, RIGHT;
	}

	public static class Wagonnet {
		int x;
		int y;
		Orientation orientation;
	}

}

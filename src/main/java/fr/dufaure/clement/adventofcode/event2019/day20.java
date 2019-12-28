package fr.dufaure.clement.adventofcode.event2019;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day20 {

	public static void main(String[] args) {
		long start1 = System.currentTimeMillis();
		part1();
		System.out.println("Execution part 1 en " + (System.currentTimeMillis() - start1) + " ms");
		long start2 = System.currentTimeMillis();
		part2();
		System.out.println("Execution part 2 en " + (System.currentTimeMillis() - start2) + " ms");
	}

	static Map<Coord, Character> maze = new HashMap<>();
	static Coord start;
	static Coord end;

	static int largeurLab;
	static int hauteurLab;

	static Map<String, List<Coord>> portailsInternes = new HashMap<>();
	static Map<String, List<Coord>> portailsExterne = new HashMap<>();
	static Map<Coord, Coord> liasonsDeep = new HashMap<>();
	static Map<Coord, Coord> liasonsOut = new HashMap<>();

	static void initialize() {
		List<String> liste = ImportUtils.getListStringUnParLigne("./src/main/resources/2019/day20");
		hauteurLab = liste.size();
		largeurLab = liste.stream().mapToInt(e -> e.toCharArray().length).max().getAsInt();

		for (int i = 0; i < liste.size(); i++) {
			for (int j = 0; j < liste.get(i).length(); j++) {
				if (('A' <= liste.get(i).charAt(j) && liste.get(i).charAt(j) <= 'Z') || liste.get(i).charAt(j) == '.') {
					maze.put(new Coord(j, i), liste.get(i).charAt(j));
				}
			}
		}

		for (Entry<Coord, Character> e : maze.entrySet().stream().filter(e -> e.getValue() == '.')
				.collect(Collectors.toSet())) {
			Coord coord = e.getKey();
			char caseGauche = maze.getOrDefault(new Coord(coord.x - 1, coord.y), '#');
			char caseDroite = maze.getOrDefault(new Coord(coord.x + 1, coord.y), '#');
			char caseHaut = maze.getOrDefault(new Coord(coord.x, coord.y - 1), '#');
			char caseBas = maze.getOrDefault(new Coord(coord.x, coord.y + 1), '#');

			if (caseGauche >= 'A' && caseGauche <= 'Z') {
				// portail a gauche
				String nomPortail = maze.get(new Coord(coord.x - 2, coord.y)) + "" + caseGauche;
				if (coord.x - 2 == 0) {
					if (!portailsExterne.containsKey(nomPortail)) {
						portailsExterne.put(nomPortail, new ArrayList<>());
					}
					portailsExterne.get(nomPortail).add(e.getKey());
				} else {
					if (!portailsInternes.containsKey(nomPortail)) {
						portailsInternes.put(nomPortail, new ArrayList<>());
					}
					portailsInternes.get(nomPortail).add(e.getKey());
				}
			}
			if (caseDroite >= 'A' && caseDroite <= 'Z') {
				// portail a droite
				String nomPortail = caseDroite + "" + maze.get(new Coord(coord.x + 2, coord.y));
				if (coord.x + 2 == largeurLab - 1) {
					if (!portailsExterne.containsKey(nomPortail)) {
						portailsExterne.put(nomPortail, new ArrayList<>());
					}
					portailsExterne.get(nomPortail).add(e.getKey());
				} else {
					if (!portailsInternes.containsKey(nomPortail)) {
						portailsInternes.put(nomPortail, new ArrayList<>());
					}
					portailsInternes.get(nomPortail).add(e.getKey());
				}
			}
			if (caseHaut >= 'A' && caseHaut <= 'Z') {
				// portail en haut
				String nomPortail = maze.get(new Coord(coord.x, coord.y - 2)) + "" + caseHaut;
				if (coord.y - 2 == 0) {
					if (!portailsExterne.containsKey(nomPortail)) {
						portailsExterne.put(nomPortail, new ArrayList<>());
					}
					portailsExterne.get(nomPortail).add(e.getKey());
				} else {
					if (!portailsInternes.containsKey(nomPortail)) {
						portailsInternes.put(nomPortail, new ArrayList<>());
					}
					portailsInternes.get(nomPortail).add(e.getKey());
				}
			}
			if (caseBas >= 'A' && caseBas <= 'Z') {
				// portail en bas
				String nomPortail = caseBas + "" + maze.get(new Coord(coord.x, coord.y + 2));
				if (coord.y + 2 == hauteurLab - 1) {
					if (!portailsExterne.containsKey(nomPortail)) {
						portailsExterne.put(nomPortail, new ArrayList<>());
					}
					portailsExterne.get(nomPortail).add(e.getKey());
				} else {
					if (!portailsInternes.containsKey(nomPortail)) {
						portailsInternes.put(nomPortail, new ArrayList<>());
					}
					portailsInternes.get(nomPortail).add(e.getKey());
				}
			}
		}

		// plus besoin des portails
		maze = maze.entrySet().stream().filter(e -> e.getValue() == '.')
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue));

		portailsExterne.forEach((p, l) -> {
			if (p.equals("AA")) {
				start = l.get(0);
			} else if (p.equals("ZZ")) {
				end = l.get(0);
			} else {
				liasonsOut.put(l.get(0), portailsInternes.get(p).get(0));
			}
		});

		portailsInternes.forEach((p, l) -> {
			liasonsDeep.put(l.get(0), portailsExterne.get(p).get(0));
		});

	}

	static void part1() {
		initialize();
		System.out.println(pathFinding(start, end));
	}

	// 6706 en 8 min (le set final fait plus de 368000 entrees)
	static void part2() {
		System.out.println(pathFindingWithLevel(start, end));
	}

	static int pathFinding(Coord start, Coord target) {
		Set<Coord> coordAtteintes = new HashSet<>();
		coordAtteintes.add(start);
		int i = 0;
		while (!coordAtteintes.contains(target)) {
			Set<Coord> newSet = new HashSet<>();
			for (Coord coord : coordAtteintes) {
				if (maze.containsKey(new Coord(coord.x, coord.y + 1))) {
					newSet.add(new Coord(coord.x, coord.y + 1));
				}
				if (maze.containsKey(new Coord(coord.x, coord.y - 1))) {
					newSet.add(new Coord(coord.x, coord.y - 1));
				}
				if (maze.containsKey(new Coord(coord.x + 1, coord.y))) {
					newSet.add(new Coord(coord.x + 1, coord.y));
				}
				if (maze.containsKey(new Coord(coord.x - 1, coord.y))) {
					newSet.add(new Coord(coord.x - 1, coord.y));
				}
				// ajouter les liasons dues aux portails
				if (liasonsDeep.containsKey(coord)) {
					newSet.add(liasonsDeep.get(coord));
				}
				if (liasonsOut.containsKey(coord)) {
					newSet.add(liasonsOut.get(coord));
				}
			}
			coordAtteintes.addAll(newSet);
			i++;
		}
		return i;
	}

	static int pathFindingWithLevel(Coord start, Coord target) {
		Set<CoordWithLevel> coordAtteintes = new HashSet<>();
		coordAtteintes.add(new CoordWithLevel(start.x, start.y, 0));
		CoordWithLevel realtarget = new CoordWithLevel(target.x, target.y, 0);
		int i = 0;
		while (!coordAtteintes.contains(realtarget)) {
			Set<CoordWithLevel> newSet = new HashSet<>();
			for (CoordWithLevel coord : coordAtteintes) {
				if (maze.containsKey(new Coord(coord.x, coord.y + 1))) {
					newSet.add(new CoordWithLevel(coord.x, coord.y + 1, coord.level));
				}
				if (maze.containsKey(new Coord(coord.x, coord.y - 1))) {
					newSet.add(new CoordWithLevel(coord.x, coord.y - 1, coord.level));
				}
				if (maze.containsKey(new Coord(coord.x + 1, coord.y))) {
					newSet.add(new CoordWithLevel(coord.x + 1, coord.y, coord.level));
				}
				if (maze.containsKey(new Coord(coord.x - 1, coord.y))) {
					newSet.add(new CoordWithLevel(coord.x - 1, coord.y, coord.level));
				}
				// On s'enfonce
				if (liasonsDeep.containsKey(coord.toCoord())) {
					newSet.add(new CoordWithLevel(liasonsDeep.get(coord.toCoord()).x,
							liasonsDeep.get(coord.toCoord()).y, coord.level + 1));
				}
				// On remonte si on n'est pas deja au 0
				if (coord.level != 0 && liasonsOut.containsKey(coord.toCoord())) {
					newSet.add(new CoordWithLevel(liasonsOut.get(coord.toCoord()).x, liasonsOut.get(coord.toCoord()).y,
							coord.level - 1));
				}
			}
			coordAtteintes.addAll(newSet);
			if (i % 1000 == 0) {
				System.out.println(i);
				System.out.println(coordAtteintes.size());
			}
			i++;
		}
		return i;
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

		@Override
		public String toString() {
			return "(" + x + "," + y + ")";
		}

	}

	static class CoordWithLevel {
		int x;
		int y;
		int level;

		public CoordWithLevel(int x, int y, int level) {
			this.x = x;
			this.y = y;
			this.level = level;
		}

		public Coord toCoord() {
			return new Coord(this.x, this.y);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + level;
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
			CoordWithLevel other = (CoordWithLevel) obj;
			if (level != other.level)
				return false;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "(" + x + "," + y + "," + level + ")";
		}

	}

}

package fr.dufaure.clement.adventofcode.event2022;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

import java.util.ArrayList;
import java.util.List;

public class Day3 {
	
	static int OFFSET_ASCII_LOWERCASE = 97;
	static int OFFSET_ASCII_UPPERCASE = 65;
	static int PRIORITY_OF_LOWERCASE_A = 1;
	static int PRIORITY_OF_UPPERCASE_A = 27;
	
	public String part1(String input) {
		var liste = ImportUtils.getListStringUnParLigne(input);
		var total = liste.stream().map(s -> Rucksack.fromOneString(s)).map(Rucksack::findCommonChar)
				  .mapToInt(this::getPriority).sum();
		return String.valueOf(total);
	}
	
	
	public String part2(String input) {
		var liste = ImportUtils.getListStringUnParLigne(input);
		var listeGroups = new ArrayList<Group>();
		Group g = new Group();
		int i = 1;
		for (var s : liste) {
			g.add(s);
			if (i == 3) {
				listeGroups.add(g);
				g = new Group();
				i = 1;
			} else {
				i++;
			}
		}
		var total = listeGroups.stream().map(Group::findCommonChar).mapToInt(this::getPriority).sum();
		return String.valueOf(total);
	}
	
	int getPriority(char c) {
		if (Character.isLowerCase(c)) {
			return Integer.valueOf(c) - OFFSET_ASCII_LOWERCASE + PRIORITY_OF_LOWERCASE_A;
		} else {
			return Integer.valueOf(c) - OFFSET_ASCII_UPPERCASE + PRIORITY_OF_UPPERCASE_A;
		}
	}
	
	private record Rucksack(String part1, String part2) {
		
		static Rucksack fromOneString(String all) {
			var c1 = all.substring(0, all.length() / 2);
			var c2 = all.substring(all.length() / 2, all.length());
			return new Rucksack(c1, c2);
		}
		
		char findCommonChar() {
			for (char c1 : part1.toCharArray()) {
				for (char c2 : part2.toCharArray()) {
					if (c1 == c2) {
						return c1;
					}
				}
			}
			throw new UnsupportedOperationException();
		}
		
	}
	
	private record Group(List<String> rucksacks) {
		
		public Group() {
			this(new ArrayList<>());
		}
		
		void add(String rucksack) {
			rucksacks.add(rucksack);
		}
		
		char findCommonChar() {
			for (char c1 : rucksacks.get(0).toCharArray()) {
				for (char c2 : rucksacks.get(1).toCharArray()) {
					if (c1 == c2) {
						for (char c3 : rucksacks.get(2).toCharArray()) {
							if (c1 == c3) {
								return c1;
							}
						}
					}
				}
			}
			throw new UnsupportedOperationException();
		}
		
	}
	
	
}

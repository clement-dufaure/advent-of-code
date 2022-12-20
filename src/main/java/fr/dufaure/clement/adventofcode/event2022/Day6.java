package fr.dufaure.clement.adventofcode.event2022;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class Day6 {
	
	public String part1(String input) {
		var chaine = ImportUtils.getString(input);
		return String.valueOf(indexMarker(chaine,4));
	}
	
	public String part2(String input) {
		var chaine = ImportUtils.getString(input);
		return String.valueOf(indexMarker(chaine,14));
	}
	
	int indexMarker(String chaine, int numberOfDistinct){
		var i=0;
		packetTester:
		while (true) {
			for (int j = i; j < i + numberOfDistinct; j++) {
				for (int k = j + 1; k < i + numberOfDistinct; k++) {
					if (chaine.charAt(j) == chaine.charAt(k)) {
						i++;
						continue packetTester;
					}
				}
			}
			break;
		}
		return i+ numberOfDistinct;
	}
	
}

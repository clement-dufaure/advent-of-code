package fr.dufaure.clement.adventofcode.event2017;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day1 {

	public static void main(String[] args) {
		day1Part1();
		day1Part2();
	}

	public static void day1Part1() {
		String data = ImportUtils.getString("./src/main/resources/2017/day1");
		int somme = 0;
		for (int i = 0; i < data.length(); i++) {
			if (data.charAt(i) == data.charAt((i + 1) % data.length())) {
				somme += Character.getNumericValue(data.charAt(i));
			}
		}
		System.out.println(somme);
	}

	public static void day1Part2() {
		String data = ImportUtils.getString("./src/main/resources/2017/day1");
		int decalage = data.length() / 2;
		int somme = 0;
		for (int i = 0; i < data.length(); i++) {
			if (data.charAt(i) == data.charAt((i + decalage) % data.length())) {
				somme += Character.getNumericValue(data.charAt(i));
			}
		}
		System.out.println(somme);
	}

}

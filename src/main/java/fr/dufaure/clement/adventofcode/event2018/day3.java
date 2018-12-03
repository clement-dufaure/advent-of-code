package fr.dufaure.clement.adventofcode.event2018;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day3 {

	public static void main(String[] args) {
		day1Part1();
		day1Part2();
	}

	public static Claim parseLigne(String ligne) {
		String[] parts = ligne.split("[#@:]");
		Claim c = new Claim();
		c.id = Integer.parseInt(parts[1].trim());
		c.leftOffset = Integer.parseInt(parts[2].trim().split(",")[0]);
		c.topOffset = Integer.parseInt(parts[2].trim().split(",")[1]);
		c.width = Integer.parseInt(parts[3].trim().split("x")[0]);
		c.height = Integer.parseInt(parts[3].trim().split("x")[1]);
		return c;
	}

	// liste de 1000 lignes de 1000 caracteres "."
	public static List<List<Character>> entrepot = new ArrayList<>();
	static {
		for (int i = 0; i < 1000; i++) {
			List<Character> places = new ArrayList<>();
			for (int j = 0; j < 1000; j++) {
				places.add('.');
			}
			entrepot.add(places);
		}
	}

	public static void day1Part1() {
		List<String> data = ImportUtils.getListStringUnParLigne("./src/main/resources/2018/day3");
		List<Claim> claims = data.stream().map(s -> parseLigne(s)).collect(Collectors.toList());
	}

	public static void day1Part2() {
		List<String> data = ImportUtils.getListStringUnParLigne("./src/main/resources/2018/day3");

	}

	public static class Claim {
		public int id;
		public int leftOffset;
		public int topOffset;
		public int width;
		public int height;
	}

}

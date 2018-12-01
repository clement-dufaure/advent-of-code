package fr.dufaure.clement.adventofcode.event2018;

import java.util.ArrayList;
import java.util.List;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day1 {

	public static void main(String[] args) {
		day1Part1();
		day1Part2();
	}

	public static void day1Part1() {
		List<Integer> data = ImportUtils.getListeEntierUnParLigne("./src/main/resources/2018/day1");
		int sum = data.stream().mapToInt(i -> i).sum();
		System.out.println(sum);
	}

	public static List<Integer> entiersDejaVus = new ArrayList<>();

	public static void day1Part2() {
		List<Integer> data = ImportUtils.getListeEntierUnParLigne("./src/main/resources/2018/day1");
		entiersDejaVus.add(0);
		int sommeEnCours = 0;
		overloop: while (true) {
			for (int i : data) {
				sommeEnCours += i;
				if (entiersDejaVus.contains(sommeEnCours)) {
					System.out.println(sommeEnCours);
					break overloop;
				} else {
					entiersDejaVus.add(sommeEnCours);
				}
			}
		}
	}

}

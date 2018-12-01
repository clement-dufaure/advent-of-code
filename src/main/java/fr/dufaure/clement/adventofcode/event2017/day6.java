package fr.dufaure.clement.adventofcode.event2017;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day6 {

	public static void main(String[] args) {
		day6Part1();
		day6Part2();
	}

	public static List<Integer> parseligne(String ligne) {
		return Arrays.asList(ligne.split("\t")).stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
	}

	public static List<List<Integer>> combinaisonsObservees = new ArrayList<>();

	public static void day6Part1() {
		String data = ImportUtils.getString("./src/main/resources/2017/day6");
		List<Integer> bancs = parseligne(data);
		int nbOperations = 0;
		do {
			combinaisonsObservees.add(new ArrayList<>(bancs));
			int max = Collections.max(bancs);
			int index = bancs.indexOf(max);
			bancs.set(index, 0);
			for (int i = max; i > 0; i--) {
				index = (index + 1) % bancs.size();
				bancs.set(index, bancs.get(index) + 1);
			}
			nbOperations++;
		} while (!combinaisonsObservees.contains(bancs));
		System.out.println(nbOperations);
		System.out.println(combinaisonsObservees.size() - combinaisonsObservees.indexOf(bancs));
	}

	public static void day6Part2() {

	}

}

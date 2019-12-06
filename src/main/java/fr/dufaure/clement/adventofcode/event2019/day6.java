package fr.dufaure.clement.adventofcode.event2019;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day6 {

	public static void main(String[] args) {
		part1();
		part2();
	}

	// key orbits aroud value
	static Map<String, String> orbites = ImportUtils.getListStringUnParLigne("./src/main/resources/2019/day6").stream()
			.map(s -> s.split("\\)")).collect(Collectors.toMap(a -> a[1], a -> a[0]));

	private static void part1() {
		int totalOrbites = 0;
		for (String planet : orbites.keySet()) {
			String centre = planet;
			while (true) {
				totalOrbites++;
				centre = orbites.get(centre);
				if (centre.equals("COM")) {
					break;
				}

			}
		}
		System.out.println(totalOrbites);
	}

	private static void part2() {
		List<String> pathMe = getPath("YOU");
		List<String> pathSanta = getPath("SAN");
		for (int i = 0; i < pathMe.size(); i++) {
			for (int j = 0; j < pathSanta.size(); j++) {
				if (pathMe.get(i).equals(pathSanta.get(j))) {
					System.out.println(i + j);
					return;
				}
			}
		}

	}

	static List<String> getPath(String object) {
		List<String> path = new ArrayList<>();
		String centre = object;
		while (true) {
			centre = orbites.get(centre);
			path.add(centre);
			if (centre.equals("COM")) {
				break;
			}
		}
		return path;
	}

}

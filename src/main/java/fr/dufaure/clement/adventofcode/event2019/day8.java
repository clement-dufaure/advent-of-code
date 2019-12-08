package fr.dufaure.clement.adventofcode.event2019;

import java.util.ArrayList;
import java.util.List;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day8 {

	public static void main(String[] args) {
		part1();
		part2();
	}

	private static void part1() {
		String data = ImportUtils.getString("./src/main/resources/2019/day8");
		int numberOfLayers = data.length() / (25 * 6);
		List<String> layers = new ArrayList<>();
		for (int i = 0; i < data.length(); i += 25 * 6) {
			String layer = data.substring(i, i + 25 * 6);
			layers.add(layer);
		}
		int min = 25 * 6 + 1;
		int indexOfMin = -1;
		for (int i = 0; i < layers.size(); i++) {
			int numberOfzero = countChar(layers.get(i), '0');
			if (numberOfzero < min) {
				min = numberOfzero;
				indexOfMin = i;
			}
		}
		System.out.println(countChar(layers.get(indexOfMin), '1') * countChar(layers.get(indexOfMin), '2'));
	}

	static int countChar(String layer, char number) {
		int count = 0;
		for (char c : layer.toCharArray()) {
			if (c == number) {
				count++;
			}
		}
		return count;
	}

	private static void part2() {
		String data = ImportUtils.getString("./src/main/resources/2019/day8");
		int numberOfLayers = data.length() / (25 * 6);
		List<String> layers = new ArrayList<>();
		for (int i = 0; i < data.length(); i += 25 * 6) {
			String layer = data.substring(i, i + 25 * 6);
			layers.add(layer);
		}
		StringBuffer imageFinale = new StringBuffer();
		for (int i = 0; i < 25 * 6; i++) {
			loop: for (String lay : layers) {
				switch (lay.charAt(i)) {
				case '0':
					imageFinale.append('.');
					break loop;
				case '1':
					imageFinale.append('#');
					break loop;
				case '2':
					break;
				default:
					System.err.println("Problem");
					break;
				}
			}
		}
		for (int i = 0; i < imageFinale.length(); i += 25) {
			String line = imageFinale.substring(i, i + 25);
			System.out.println(line);
		}

	}

}

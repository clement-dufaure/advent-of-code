package fr.dufaure.clement.adventofcode.event2022;

import fr.dufaure.clement.adventofcode.utils.Day;
import fr.dufaure.clement.adventofcode.utils.DirectionSansDigonales;
import fr.dufaure.clement.adventofcode.utils.Grid;
import fr.dufaure.clement.adventofcode.utils.ImportUtils;

import java.util.List;

public class Day8 implements Day {
	
	@Override
	public String part1(String inputPath) {
		var liste = ImportUtils.getListStringUnParLigne(inputPath);
		var grid = readInput(liste);
		grid.consolider();
		var count = grid.gridList().stream().filter(this::isVisible).count();
		return String.valueOf(count);
	}
	
	@Override
	public String part2(String inputPath) {
		var liste = ImportUtils.getListStringUnParLigne(inputPath);
		var grid = readInput(liste);
		grid.consolider();
		var max = grid.gridList().stream().mapToInt(this::getScenicSore).max().getAsInt();
		return String.valueOf(max);
	}
	
	Grid<Integer> readInput(List<String> input) {
		var grid = new Grid<Integer>();
		for (int i = 0; i < input.size(); i++) {
			for (int j = 0; j < input.get(i).length(); j++) {
				grid.add(j, i, Integer.valueOf("" + input.get(i).charAt(j)));
			}
		}
		return grid;
	}
	
	boolean isVisible(Grid.Case<Integer> c) {
		for (DirectionSansDigonales d : DirectionSansDigonales.values()) {
			var current = c;
			while (true) {
				var next = current.casesAdjacentes().get(d);
				if (next == null) {
					// visible depuis un bord
					return true;
				}
				if (next.value() < c.value()) {
					current = next;
					continue;
				} else {
					break;
				}
			}
		}
		return false;
	}
	
	int getScenicSore(Grid.Case<Integer> c) {
		int score = 1;
		for (DirectionSansDigonales d : DirectionSansDigonales.values()) {
			var current = c;
			int view = 0;
			while (true) {
				var next = current.casesAdjacentes().get(d);
				if (next == null) {
					score *= view;
					break;
				}
				if (next.value() >= c.value()) {
					view++;
					score *= view;
					break;
				} else {
					view++;
					current = next;
				}
			}
		}
		return score;
	}
	
	
}

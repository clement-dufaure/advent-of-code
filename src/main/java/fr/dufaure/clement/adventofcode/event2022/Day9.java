package fr.dufaure.clement.adventofcode.event2022;

import fr.dufaure.clement.adventofcode.utils.Coord;
import fr.dufaure.clement.adventofcode.utils.Day;
import fr.dufaure.clement.adventofcode.utils.Direction;
import fr.dufaure.clement.adventofcode.utils.ImportUtils;

import java.util.ArrayList;
import java.util.List;

public class Day9 implements Day {
	@Override
	public String part1(String inputPath) {
		var input = ImportUtils.getListStringUnParLigne(inputPath);
		return String.valueOf(play(2, input));
	}
	
	@Override
	public String part2(String inputPath) {
		var input = ImportUtils.getListStringUnParLigne(inputPath);
		return String.valueOf(play(10, input));
	}
	
	long play(int size, List<String> input) {
		var rope = new Rope(size);
		List<Coord> tailPositions = new ArrayList<>();
		tailPositions.add(rope.getTailPosition());
		for (var str : input) {
			var ligne = str.split(" ");
			var d = fromLRUB(ligne[0]);
			for (int i = 0; i < Integer.parseInt(ligne[1]); i++) {
				rope.avancerHead(d);
				tailPositions.add(rope.getTailPosition());
			}
		}
		return tailPositions.stream().distinct().count();
	}
	
	private Direction fromLRUB(String lrub) {
		return switch (lrub) {
			case "L" -> Direction.O;
			case "R" -> Direction.E;
			case "U" -> Direction.N;
			case "D" -> Direction.S;
			default -> throw new UnsupportedOperationException();
		};
	}
	
	private record Rope(int size, List<Coord> knots) {
		
		Rope(int size) {
			this(size, new ArrayList<>(size));
			for (int i = 0; i < size; i++) {
				knots.add(new Coord(0, 0));
			}
		}
		
		Coord getTailPosition() {
			return knots.get(size - 1);
		}
		
		void avancerHead(Direction d) {
			// avancer head
			knots.set(0, knots.get(0).avancer(d));
			// avancer noeuds successifs if needed
			for (int i = 1; i < knots.size(); i++) {
				knots.set(i, rectifier(knots.get(i - 1), knots.get(i)));
			}
		}
		
		
		private Coord rectifier(Coord h, Coord t) {
			Coord newT = null;
			//check distance
			if (Math.abs(h.x() - t.x()) <= 1 && Math.abs(h.y() - t.y()) <= 1) {
				//no change for T
				newT = t;
			} else {
				// same column
				if (h.x() == t.x()) {
					if (h.y() < t.y()) {
						newT = new Coord(t.x(), t.y() - 1);
					} else {
						newT = new Coord(t.x(), t.y() + 1);
					}
				}
				// same row
				if (h.y() == t.y()) {
					if (h.x() < t.x()) {
						newT = new Coord(t.x() - 1, t.y());
					} else {
						newT = new Coord(t.x() + 1, t.y());
					}
				} else {
					if (h.x() < t.x() && h.y() < t.y()) {
						newT = new Coord(t.x() - 1, t.y() - 1);
					}
					if (h.x() < t.x() && h.y() > t.y()) {
						newT = new Coord(t.x() - 1, t.y() + 1);
					}
					if (h.x() > t.x() && h.y() < t.y()) {
						newT = new Coord(t.x() + 1, t.y() - 1);
					}
					if (h.x() > t.x() && h.y() > t.y()) {
						newT = new Coord(t.x() + 1, t.y() + 1);
					}
				}
			}
			return newT;
		}
		
		
	}
	
}

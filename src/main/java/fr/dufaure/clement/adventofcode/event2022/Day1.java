package fr.dufaure.clement.adventofcode.event2022;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day1 {
	
	public String part1(String input) {
		var liste = ImportUtils.getListStringUnParLigne(input);
		var listeElf = new ArrayList<Elf>();
		Elf e = new Elf();
		for (var s : liste) {
			if (s.isBlank()) {
				listeElf.add(e);
				e = new Elf();
			} else {
				e.add(Integer.parseInt(s));
			}
		}
		listeElf.add(e);
		var max = listeElf.stream().mapToInt(e1 -> e1.sum()).max();
		return String.valueOf(max.getAsInt());
	}
	
	public String part2(String input) {
		var liste = ImportUtils.getListStringUnParLigne(input);
		var listeElf = new ArrayList<Elf>();
		Elf e = new Elf();
		for (var s : liste) {
			if (s.isBlank()) {
				listeElf.add(e);
				e = new Elf();
			} else {
				e.add(Integer.parseInt(s));
			}
		}
		listeElf.add(e);
		var somme3Max =
				  listeElf.stream().map(e1 -> e1.sum()).sorted(Collections.reverseOrder()).limit(3).mapToInt(i -> i).sum();
		return String.valueOf(somme3Max);
	}
	
	private record Elf(List<Integer> snacks) {
		
		public Elf() {
			this(new ArrayList<>());
		}
		
		void add(int i) {
			snacks().add(i);
		}
		
		int sum() {
			return snacks().stream().mapToInt(i -> i).sum();
		}
		
	}
	
}

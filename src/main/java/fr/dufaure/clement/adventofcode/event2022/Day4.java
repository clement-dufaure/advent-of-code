package fr.dufaure.clement.adventofcode.event2022;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class Day4 {
	
	public String part1(String input) {
		var liste = ImportUtils.getListStringUnParLigne(input);
		var count = liste.stream().map(Pair::fromString).filter(Pair::oneAssignmentFullyContainsOther).count();
		return String.valueOf(count);
	}
	
	public String part2(String input) {
		var liste = ImportUtils.getListStringUnParLigne(input);
		var count = liste.stream().map(Pair::fromString).filter(Pair::assignmentsOverlap).count();
		return String.valueOf(count);
	}
	
	private record Elf(int min, int max) {
	}
	
	private record Pair(Elf elf1, Elf elf2) {
		
		static Pair fromString(String s) {
			var elfs = s.split(",");
			var elf1 = elfs[0].split("-");
			var elf2 = elfs[1].split("-");
			return new Pair(new Elf(Integer.parseInt(elf1[0]), Integer.parseInt(elf1[1])),
					  new Elf(Integer.parseInt(elf2[0]), Integer.parseInt(elf2[1])));
		}
		
		boolean oneAssignmentFullyContainsOther() {
			if (elf1.min == elf2.min || elf1.max == elf2().max) {
				return true;
			}
			if (elf1.min < elf2.min) {
				if (elf1.max > elf2().max) {
					return true;
				} else {
					return false;
				}
			} else {
				if (elf1.max < elf2.max) {
					return true;
				} else {
					return false;
				}
			}
		}
		
		boolean assignmentsOverlap() {
			return ((elf1.min >= elf2.min && elf1.min <= elf2.max || elf1.max >= elf2.min && elf1.max <= elf2.max) ||
					  (elf2.min >= elf1.min && elf2.min <= elf1.max || elf2.max >= elf1.min && elf2.max <= elf1.max));
		}
	}
	
}

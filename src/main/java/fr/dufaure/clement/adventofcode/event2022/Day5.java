package fr.dufaure.clement.adventofcode.event2022;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;

public class Day5 {
	public String part1(String input) {
		var liste = ImportUtils.getListStringUnParLigne(input);
		Game g = readInput(liste);
		
		for (var i : g.instructions) {
			for (int j = 0; j < i.numberCrates; j++) {
				g.stacks.get(i.to - 1).push(g.stacks.get(i.from - 1).pop());
			}
		}
		
		var front = g.stacks.stream().map(s -> s.pop()).map(c -> c.mark).collect(
				  Collector.of(StringBuilder::new, StringBuilder::append, StringBuilder::append, StringBuilder::toString));
		
		return String.valueOf(front);
	}
	
	public String part2(String input) {
		var liste = ImportUtils.getListStringUnParLigne(input);
		Game g = readInput(liste);
		
		for (var i : g.instructions) {
			Stack<Crate> temp = new Stack<>();
			for (int j = 0; j < i.numberCrates; j++) {
				temp.push(g.stacks.get(i.from - 1).pop());
			}
			for (int j = 0; j < i.numberCrates; j++) {
				g.stacks.get(i.to - 1).push(temp.pop());
			}
		}
		
		var front = g.stacks.stream().map(s -> s.pop()).map(c -> c.mark).collect(
				  Collector.of(StringBuilder::new, StringBuilder::append, StringBuilder::append, StringBuilder::toString));
		
		return String.valueOf(front);
	}
	
	
	Game readInput(List<String> input) {
		var i = 0;
		String line;
		List<String> rowStacks = new ArrayList<>();
		while (!(line = input.get(i)).isBlank()) {
			// stacks
			rowStacks.add(line);
			i++;
		}
		Collections.reverse(rowStacks);
		List<Stack<Crate>> stacks = new ArrayList<>();
		for (int j = 0; j < rowStacks.get(0).length(); j++) {
			if (!Character.isWhitespace(rowStacks.get(0).charAt(j))) {
				Stack<Crate> s = new Stack<>();
				for (int k = 1; k < rowStacks.size(); k++) {
					if (rowStacks.get(k).length() > j && Character.isAlphabetic(rowStacks.get(k).charAt(j))) {
						s.push(new Crate(rowStacks.get(k).charAt(j)));
					} else {
						break;
					}
				}
				stacks.add(s);
			}
		}
		i++;
		List<Instruction> instructions = new ArrayList<>();
		Pattern p = Pattern.compile("move (\\d+) from (\\d+) to (\\d+)");
		for (int j = i; j < input.size(); j++) {
			Matcher m = p.matcher(input.get(j));
			if (m.matches()) {
				instructions.add(new Instruction(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)),
						  Integer.parseInt(m.group(3))));
			} else {
				throw new UnsupportedOperationException();
			}
		}
		
		return new Game(stacks, instructions);
		
	}
	
	
	private record Instruction(int numberCrates, int from, int to) {
	}
	
	private record Crate(char mark) {
	}
	
	private record Game(List<Stack<Crate>> stacks, List<Instruction> instructions) {
	}
	
}

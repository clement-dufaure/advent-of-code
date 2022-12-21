package fr.dufaure.clement.adventofcode.event2022;

import fr.dufaure.clement.adventofcode.utils.Day;
import fr.dufaure.clement.adventofcode.utils.ImportUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day11 implements Day {
	
	@Override
	public String part1(String inputPath) {
		var input = ImportUtils.getStringWithNewLine(inputPath);
		var monkeys = readInput(input);
		for (int i = 0; i < 20; i++) {
			for (var monkey : monkeys) {
				monkey.play(monkeys);
			}
		}
		var top2 = monkeys.stream().map(m -> m.numberOfInspection).sorted(Comparator.reverseOrder()).limit(2).toList();
		var result = top2.get(0) * top2.get(1);
		return String.valueOf(result);
	}
	
	@Override
	public String part2(String inputPath) {
		var input = ImportUtils.getStringWithNewLine(inputPath);
		var monkeys = readInput(input);
		var ppcm = monkeys.stream().mapToLong(m -> m.testDivisible).reduce(1, (a, b) -> a * b);
		for (int i = 0; i < 10_000; i++) {
			for (var monkey : monkeys) {
				monkey.playBis(monkeys, ppcm);
			}
		}
		var top2 = monkeys.stream().map(m -> m.numberOfInspection).sorted(Comparator.reverseOrder()).limit(2).toList();
		var result = top2.get(0) * top2.get(1);
		return String.valueOf(result);
	}
	
	private List<Monkey> readInput(String input) {
		var monkeys = new ArrayList<Monkey>();
		Pattern p = Pattern.compile("""
				  Monkey (\\d+):
				    Starting items: ([\\d ,]+)
				    Operation: ([a-z= +*\\d]+)
				    Test: divisible by (\\d+)
				      If true: throw to monkey (\\d+)
				      If false: throw to monkey (\\d+)
				  """);
		Matcher m = p.matcher(input);
		while (m.find()) {
			Monkey monkey = new Monkey(Operation.fromString(m.group(3)), Integer.parseInt(m.group(4)),
					  Integer.parseInt(m.group(5)), Integer.parseInt(m.group(6)));
			Arrays.stream(m.group(2).replaceAll(" ", "").split(",")).forEach(i -> monkey.items.offer(Long.parseLong(i)));
			monkeys.add(monkey);
		}
		return monkeys;
	}
	
	private static abstract sealed class Operation permits Add, Multiply, Square {
		public static Operation fromString(String s) {
			Matcher m;
			if ((m = Add.pattern.matcher(s)).matches()) {
				return new Add(Integer.parseInt(m.group(1)));
			}
			if ((m = Multiply.pattern.matcher(s)).matches()) {
				return new Multiply(Integer.parseInt(m.group(1)));
			}
			if (Square.pattern.matcher(s).matches()) {
				return new Square();
			}
			throw new UnsupportedOperationException();
		}
		
		public abstract long computeFromOld(long old);
	}
	
	private static final class Add extends Operation {
		public static Pattern pattern = Pattern.compile("new = old \\+ (\\d+)");
		private int value;
		
		public Add(int value) {
			this.value = value;
		}
		
		@Override
		public long computeFromOld(long old) {
			return old + value;
		}
	}
	
	private static final class Multiply extends Operation {
		
		public static Pattern pattern = Pattern.compile("new = old \\* (\\d+)");
		private int value;
		
		public Multiply(int value) {
			this.value = value;
		}
		
		@Override
		public long computeFromOld(long old) {
			return old * value;
		}
		
	}
	
	private static final class Square extends Operation {
		
		public static Pattern pattern = Pattern.compile("new = old \\* old");
		
		@Override
		public long computeFromOld(long old) {
			return old * old;
		}
	}
	
	private class Monkey {
		
		private final Queue<Long> items = new LinkedList<>();
		private final Operation operation;
		private final int testDivisible;
		private final int monkeyIfTrue;
		private final int monkeyIfFalse;
		private long numberOfInspection = 0L;
		
		public Monkey(Operation operation, int testDivisible, int monkeyIfTrue, int monkeyIfFalse) {
			this.operation = operation;
			this.testDivisible = testDivisible;
			this.monkeyIfTrue = monkeyIfTrue;
			this.monkeyIfFalse = monkeyIfFalse;
		}
		
		public void play(List<Monkey> monkeys) {
			while (!items.isEmpty()) {
				inspectAndThrow(monkeys);
			}
		}
		
		private void inspectAndThrow(List<Monkey> monkeys) {
			numberOfInspection++;
			var itemToInspect = items.poll();
			var newItemWorryValue = operation.computeFromOld(itemToInspect);
			newItemWorryValue /= 3;
			if (newItemWorryValue % testDivisible == 0) {
				monkeys.get(monkeyIfTrue).items.offer(newItemWorryValue);
			} else {
				monkeys.get(monkeyIfFalse).items.offer(newItemWorryValue);
			}
		}
		
		
		public void playBis(List<Monkey> monkeys, long ppcm) {
			while (!items.isEmpty()) {
				inspectAndThrowBis(monkeys, ppcm);
			}
		}
		
		private void inspectAndThrowBis(List<Monkey> monkeys, long ppcm) {
			numberOfInspection++;
			var itemToInspect = items.poll();
			var newItemWorryValue = operation.computeFromOld(itemToInspect);
			newItemWorryValue %= ppcm;
			if (newItemWorryValue % testDivisible == 0) {
				monkeys.get(monkeyIfTrue).items.offer(newItemWorryValue);
			} else {
				monkeys.get(monkeyIfFalse).items.offer(newItemWorryValue);
			}
		}
		
		
	}
	
}

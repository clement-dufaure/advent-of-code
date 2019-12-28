package fr.dufaure.clement.adventofcode.event2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day22 {

	public static void main(String[] args) throws InterruptedException {
		long start1 = System.currentTimeMillis();
		part1();
		System.out.println("Execution part 1 en " + (System.currentTimeMillis() - start1) + " ms");
		long start2 = System.currentTimeMillis();
		part2();
		System.out.println("Execution part 2 en " + (System.currentTimeMillis() - start2) + " ms");
	}

	static void part1() throws InterruptedException {
		List<String> liste = ImportUtils.getListStringUnParLigne("./src/main/resources/2019/day22");
		List<Integer> deck = IntStream.rangeClosed(0, 10006).boxed().collect(Collectors.toList());

		Pattern patternDealNewStack = Pattern.compile("deal into new stack");
		Pattern patternCut = Pattern.compile("cut (-?\\d+)");
		Pattern patternDealIncrement = Pattern.compile("deal with increment (\\d+)");

		for (String instruction : liste) {
			Matcher m;
			if ((m = patternDealNewStack.matcher(instruction)).matches()) {
				dealNewStack(deck);
			} else if ((m = patternCut.matcher(instruction)).matches()) {
				cut(deck, Integer.parseInt(m.group(1)));
			} else if ((m = patternDealIncrement.matcher(instruction)).matches()) {
				dealWithIncrement(deck, Integer.parseInt(m.group(1)));
			} else {
				System.err.println("Ligne non matchee " + instruction);
			}
		}

		System.out.println(deck.indexOf(2019));

	}

	static void dealNewStack(List<Integer> deck) {
		Collections.reverse(deck);
	}

	static void cut(List<Integer> deck, int cut) {
		List<Integer> newDeck = new ArrayList<>(deck);
		deck.clear();
		if (cut > 0) {
			deck.addAll(Stream.concat(newDeck.subList(cut, newDeck.size()).stream(), newDeck.subList(0, cut).stream())
					.collect(Collectors.toList()));
		} else {
			deck.addAll(Stream.concat(newDeck.subList(newDeck.size() + cut, newDeck.size()).stream(),
					newDeck.subList(0, newDeck.size() + cut).stream()).collect(Collectors.toList()));
		}
	}

	static void dealWithIncrement(List<Integer> deck, int increment) {
		Integer[] newDeck = new Integer[deck.size()];
		int position = 0;
		for (Integer card : deck) {
			newDeck[position] = card;
			position = (position + increment) % deck.size();
		}
		deck.clear();
		deck.addAll(Arrays.asList(newDeck));
	}

	static void part2() {

	}

}

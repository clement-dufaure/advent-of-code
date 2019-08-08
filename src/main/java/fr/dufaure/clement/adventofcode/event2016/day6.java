package fr.dufaure.clement.adventofcode.event2016;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day6 {

	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		long start1 = System.currentTimeMillis();
		part1();
		System.out.println("Execution part 1 en " + (System.currentTimeMillis() - start1) + " ms");
		long start2 = System.currentTimeMillis();
		part2();
		System.out.println("Execution part 2 en " + (System.currentTimeMillis() - start2) + " ms");
	}

	static void part1() {
		List<String> messages = ImportUtils.getListStringUnParLigne("./src/main/resources/2016/day6");
		String str = messages.stream().collect(new MyCollector()).stream()
				.map(liste -> liste.stream().collect(Collectors.groupingBy(a -> a, Collectors.counting())))
				.map(hashMap -> hashMap.entrySet().stream().max((a, b) -> a.getValue() > b.getValue() ? 1 : -1).get()
						.getKey())
				.collect(Collectors.joining(""));
		System.out.println(str);
	}

	static class MyCollector implements Collector<String, ArrayList<ArrayList<String>>, ArrayList<ArrayList<String>>> {

		@Override
		public BiConsumer<ArrayList<ArrayList<String>>, String> accumulator() {
			return (a, b) -> {
				if (a.isEmpty()) {
					for (int i = 0; i < b.length(); i++) {
						a.add(new ArrayList<>());
					}
				}
				for (int i = 0; i < b.length(); i++) {
					a.get(i).add(b.charAt(i) + "");
				}
			};
		}

		@Override
		public Set<Characteristics> characteristics() {
			Collector.Characteristics[] a = { Collector.Characteristics.CONCURRENT,
					Collector.Characteristics.IDENTITY_FINISH, Collector.Characteristics.UNORDERED };
			return new HashSet<Characteristics>(Arrays.asList(a));
		}

		@Override
		public BinaryOperator<ArrayList<ArrayList<String>>> combiner() {
			return (a, b) -> {
				for (int i = 0; i < a.size(); i++) {
					a.get(i).addAll(b.get(i));
				}
				return a;
			};
		}

		@Override
		public Function<ArrayList<ArrayList<String>>, ArrayList<ArrayList<String>>> finisher() {
			return Function.identity();
		}

		@Override
		public Supplier<ArrayList<ArrayList<String>>> supplier() {
			return () -> new ArrayList<ArrayList<String>>();
		}

	}

	static void part2() {
		List<String> messages = ImportUtils.getListStringUnParLigne("./src/main/resources/2016/day6");
		String str = messages.stream().collect(new MyCollector()).stream()
				.map(liste -> liste.stream().collect(Collectors.groupingBy(a -> a, Collectors.counting())))
				.map(hashMap -> hashMap.entrySet().stream().min((a, b) -> a.getValue() > b.getValue() ? 1 : -1).get()
						.getKey())
				.collect(Collectors.joining(""));
		System.out.println(str);
	}

}

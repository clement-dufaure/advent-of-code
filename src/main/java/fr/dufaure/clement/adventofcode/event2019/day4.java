package fr.dufaure.clement.adventofcode.event2019;

import java.util.stream.IntStream;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day4 {

	public static void main(String[] args) {
		part1();
		part2();
	}

	public static void part1() {
		String input = ImportUtils.getString("./src/main/resources/2019/day4");
		int rangeInf = Integer.valueOf(input.split("-")[0]);
		int rangeSup = Integer.valueOf(input.split("-")[1]);
		System.out.println(IntStream.rangeClosed(rangeInf, rangeSup).filter(day4::checkDoubleDigit)
				.filter(day4::checkAlwaysIncrease).count());
	}

	public static void part2() {
		String input = ImportUtils.getString("./src/main/resources/2019/day4");
		int rangeInf = Integer.valueOf(input.split("-")[0]);
		int rangeSup = Integer.valueOf(input.split("-")[1]);
		System.out.println(IntStream.rangeClosed(rangeInf, rangeSup).filter(day4::checkExactDoubleDigit)
				.filter(day4::checkAlwaysIncrease).count());
	}

	static boolean checkDoubleDigit(int password) {
		String passwordString = String.valueOf(password);
		for (int i = 0; i < passwordString.length() - 1; i++) {
			if (passwordString.charAt(i) == passwordString.charAt(i + 1)) {
				return true;
			}
		}
		return false;
	}

	static boolean checkExactDoubleDigit(int password) {
		String passwordString = String.valueOf(password);
		for (int i = 0; i < passwordString.length() - 1; i++) {
			if (passwordString.charAt(i) == passwordString.charAt(i + 1)
					&& (i + 2 == passwordString.length() || passwordString.charAt(i) != passwordString.charAt(i + 2))
					&& (i == 0 || passwordString.charAt(i) != passwordString.charAt(i - 1))) {
				return true;
			}
		}
		return false;
	}

	static boolean checkAlwaysIncrease(int password) {
		String passwordString = String.valueOf(password);
		for (int i = 0; i < passwordString.length() - 1; i++) {
			if (passwordString.charAt(i) > passwordString.charAt(i + 1)) {
				return false;
			}
		}
		return true;
	}

}
package fr.dufaure.clement.adventofcode.event2019;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day25 {

	static final boolean displayMode = false;

	public static void main(String[] args) {
		long start1 = System.currentTimeMillis();
		part1();
		System.out.println("Execution part 1 en " + (System.currentTimeMillis() - start1) + " ms");
		long start2 = System.currentTimeMillis();
		part2();
		System.out.println("Execution part 2 en " + (System.currentTimeMillis() - start2) + " ms");
	}

	static void part1() {
		Ascii ascii = new Ascii();
		Scanner s = new Scanner(System.in);
		List<Long> cmdAscii = new ArrayList<>();
		System.out.println(getStringFromListLong(ascii.runProgramme(cmdAscii)));
		String[] cmds = { "west", "take semiconductor", "west", "take planetoid", "west", "take food ration", "west",
				"take fixed point", "west", "take klein bottle", "east", "south", "west", "take weather machine",
				"east", "north", "east", "east", "south", "south", "south", "take pointer", "north", "north", "east",
				"take coin", "east", "north", "east", "north", "inv" };
		for (String cmd : cmds) {
			System.out.println(getStringFromListLong(ascii.runProgramme(getListLongFromString(cmd + '\n'))));
		}
		dropAll(ascii);
		for (Set<String> subset : getSubSets()) {
			takeSubset(subset, ascii);
			cmdAscii = getListLongFromString("north" + '\n');
			System.out.println(getStringFromListLong(ascii.runProgramme(cmdAscii)));
			dropAll(ascii);
		}
		while (!ascii.stopped) {
			System.out.println(getStringFromListLong(ascii.runProgramme(cmdAscii)));
			cmdAscii = getListLongFromString(s.nextLine() + '\n');
		}
	}

	static void dropAll(Ascii ascii) {
		String[] inventory = { "food ration", "fixed point", "weather machine", "semiconductor", "planetoid", "coin",
				"pointer", "klein bottle" };
		List<String> cmds = new ArrayList<>();
		for (String item : inventory) {
			cmds.add("drop " + item);
		}
		for (String cmd : cmds) {
			System.out.println(getStringFromListLong(ascii.runProgramme(getListLongFromString(cmd + '\n'))));
		}
	}

	static void takeSubset(Set<String> subset, Ascii ascii) {
		List<String> cmds = new ArrayList<>();
		for (String item : subset) {
			cmds.add("take " + item);
		}
		for (String cmd : cmds) {
			System.out.println(getStringFromListLong(ascii.runProgramme(getListLongFromString(cmd + '\n'))));
		}
	}

	static List<Set<String>> getSubSets() {
		List<Set<String>> liste = new ArrayList<>();
		String[] inventory = { "food ration", "fixed point", "weather machine", "semiconductor", "planetoid", "coin",
				"pointer", "klein bottle" };
		int n = inventory.length;
		for (int i = 0; i < (1 << n); i++) {
			Set<String> subset = new HashSet<>();
			for (int j = 0; j < n; j++) {
				if ((i & (1 << j)) > 0) {
					subset.add(inventory[j]);
				}
			}
			liste.add(subset);
		}
		return liste;
	}

	static void part2() {
	}

	static List<Long> getListLongFromString(String chaine) {
		List<Long> listeAscii = new ArrayList<>();
		for (Character c : chaine.toCharArray()) {
			listeAscii.add((long) (int) c);
		}
		return listeAscii;
	}

	static String getStringFromListLong(List<Long> liste) {
		StringBuffer sb = new StringBuffer();
		for (long l : liste) {
			if (l < 128L) {
				sb.append((char) (int) l);
			} else {
				sb.append(l);
			}
		}
		return sb.toString();
	}

	public static class Ascii {
		boolean stopped = false;
		long relativeBase = 0;
		int pointeur = 0;
		ArrayList<Long> listeCode = new ArrayList<>();

		Ascii() {
			String[] liste = ImportUtils.getString("./src/main/resources/2019/day25").split(",");

			for (int i = 0; i < liste.length; i++) {
				listeCode.add(Long.valueOf(liste[i]));
			}
			// on ajoute des 0 a la fin
			for (int i = 0; i < 2000; i++) {
				listeCode.add(0L);
			}
		}

		public List<Long> runProgramme(List<Long> inputs) {
			int inputSet = 0;
			List<Long> output = new ArrayList<>();

			mainLoop: while (true) {
				long parameter1;
				long parameter2;
				switch (String.valueOf(listeCode.get(pointeur) % 100)) {
				case "1":
					listeCode.set(
							getWhereToWrite(listeCode, (listeCode.get(pointeur) % 100000) / 10000,
									listeCode.get(pointeur + 3), relativeBase).intValue(),
							getParameter(listeCode, (listeCode.get(pointeur) % 1000) / 100, listeCode.get(pointeur + 1),
									relativeBase)
									+ getParameter(listeCode, (listeCode.get(pointeur) % 10000) / 1000,
											listeCode.get(pointeur + 2), relativeBase));
					pointeur += 4;
					break;
				case "2":
					listeCode.set(
							getWhereToWrite(listeCode, (listeCode.get(pointeur) % 100000) / 10000,
									listeCode.get(pointeur + 3), relativeBase).intValue(),
							getParameter(listeCode, (listeCode.get(pointeur) % 1000) / 100, listeCode.get(pointeur + 1),
									relativeBase)
									* getParameter(listeCode, (listeCode.get(pointeur) % 10000) / 1000,
											listeCode.get(pointeur + 2), relativeBase));
					pointeur += 4;
					break;
				case "3":
					if (inputSet < inputs.size()) {
						listeCode.set(getWhereToWrite(listeCode, (listeCode.get(pointeur) % 1000) / 100,
								listeCode.get(pointeur + 1), relativeBase).intValue(), inputs.get(inputSet));
						inputSet++;
						pointeur += 2;
					} else {
						return output;
					}
					break;
				case "4":
					output.add(getParameter(listeCode, (listeCode.get(pointeur) % 1000) / 100,
							listeCode.get(pointeur + 1), relativeBase));
					pointeur += 2;
					break;
				case "5":
					parameter1 = getParameter(listeCode, (listeCode.get(pointeur) % 1000) / 100,
							listeCode.get(pointeur + 1), relativeBase);
					parameter2 = getParameter(listeCode, (listeCode.get(pointeur) % 10000) / 1000,
							listeCode.get(pointeur + 2), relativeBase);
					if (parameter1 != 0) {
						pointeur = (int) parameter2;
					} else {
						pointeur += 3;
					}
					break;
				case "6":
					parameter1 = getParameter(listeCode, (listeCode.get(pointeur) % 1000) / 100,
							listeCode.get(pointeur + 1), relativeBase);
					parameter2 = getParameter(listeCode, (listeCode.get(pointeur) % 10000) / 1000,
							listeCode.get(pointeur + 2), relativeBase);
					if (parameter1 == 0) {
						pointeur = (int) parameter2;
					} else {
						pointeur += 3;
					}
					break;
				case "7":
					listeCode.set(
							getWhereToWrite(listeCode, (listeCode.get(pointeur) % 100000) / 10000,
									listeCode.get(pointeur + 3), relativeBase).intValue(),
							getParameter(listeCode, (listeCode.get(pointeur) % 1000) / 100, listeCode.get(pointeur + 1),
									relativeBase) < getParameter(listeCode, (listeCode.get(pointeur) % 10000) / 1000,
											listeCode.get(pointeur + 2), relativeBase) ? 1L : 0L);
					pointeur += 4;
					break;
				case "8":
					listeCode.set(
							getWhereToWrite(listeCode, (listeCode.get(pointeur) % 100000) / 10000,
									listeCode.get(pointeur + 3), relativeBase).intValue(),
							getParameter(listeCode, (listeCode.get(pointeur) % 1000) / 100, listeCode.get(pointeur + 1),
									relativeBase)
											.equals(getParameter(listeCode, (listeCode.get(pointeur) % 10000) / 1000,
													listeCode.get(pointeur + 2), relativeBase)) ? 1L : 0L);
					pointeur += 4;
					break;
				case "9":
					relativeBase += getParameter(listeCode, (listeCode.get(pointeur) % 1000) / 100,
							listeCode.get(pointeur + 1), relativeBase);
					pointeur += 2;
					break;
				case "99":
					break mainLoop;
				default:
					System.err.println("Pas normal, opcode : " + listeCode.get(pointeur));
					throw new UnsupportedOperationException();
				}
			}
			stopped = true;
			return output;
		}

		static Long getParameter(List<Long> programme, long parameterMode, Long value, Long relativeBase) {
			switch (String.valueOf(parameterMode)) {
			case "0":
				return programme.get(value.intValue());
			case "1":
				return value;
			case "2":
				return programme.get(relativeBase.intValue() + value.intValue());
			default:
				System.err.println("Invalide parameter mode : " + String.valueOf(parameterMode));
				throw new UnsupportedOperationException();
			}
		}

		static Long getWhereToWrite(List<Long> programme, long parameterMode, Long value, Long relativeBase) {
			switch (String.valueOf(parameterMode)) {
			case "0":
				return value;
			// "1":
			// return value;
			case "2":
				return relativeBase + value;
			default:
				System.err.println("Invalide parameter mode : " + String.valueOf(parameterMode));
				throw new UnsupportedOperationException();
			}
		}
	}

}

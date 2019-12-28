package fr.dufaure.clement.adventofcode.event2019;

import java.util.ArrayList;
import java.util.List;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day21 {

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
		String programme =
				//
				"NOT A T\n" +
				"OR T J\n" +
				"NOT B T\n" +
				"OR T J\n" +
				"NOT C T\n" +
				"OR T J\n" +
				"AND D J\n" +
				"WALK\n";
		List<Long> programmeAscii = getListLongFromString(programme);
		Ascii ascii = new Ascii();
		System.out.println(getStringFromListLong(ascii.runProgramme(programmeAscii)));
	}

	static void part2() {
		String programme =
				//
				"NOT A T\n" +
				"OR T J\n" +
				"NOT B T\n" +
				"AND A T\n"+
				"OR T J\n" +
				"NOT C T\n" +
				"AND H T\n" +
				"OR T J\n" +
				"AND D J\n" +
				"RUN\n";
		List<Long> programmeAscii = getListLongFromString(programme);
		Ascii ascii = new Ascii();
		System.out.println(getStringFromListLong(ascii.runProgramme(programmeAscii)));
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
			String[] liste = ImportUtils.getString("./src/main/resources/2019/day21").split(",");

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
					listeCode.set(getWhereToWrite(listeCode, (listeCode.get(pointeur) % 1000) / 100,
							listeCode.get(pointeur + 1), relativeBase).intValue(), inputs.get(inputSet));
					inputSet++;
					pointeur += 2;
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

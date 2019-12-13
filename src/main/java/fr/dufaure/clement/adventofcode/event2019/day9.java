package fr.dufaure.clement.adventofcode.event2019;

import java.util.ArrayList;
import java.util.List;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day9 {

	public static void main(String[] args) {
		long start1 = System.currentTimeMillis();
		part1();
		System.out.println("Execution part 1 en " + (System.currentTimeMillis() - start1) + " ms");
		long start2 = System.currentTimeMillis();
		part2();
		System.out.println("Execution part 2 en " + (System.currentTimeMillis() - start2) + " ms");
	}

	private static void part1() {
		System.out.println(runProgramme(1));
	}

	private static void part2() {
		System.out.println(runProgramme(2));
	}

	public static String runProgramme(int input) {
		String[] liste = ImportUtils.getString("./src/main/resources/2019/day9").split(",");
		long relativeBase = 0;
		StringBuffer output = new StringBuffer();
		ArrayList<Long> listeCode = new ArrayList<>();
		for (int i = 0; i < liste.length; i++) {
			listeCode.add(Long.valueOf(liste[i]));
		}
		// on ajoute des 0 a la fin
		for (int i = 0; i < 1000; i++) {
			listeCode.add(0L);
		}

		int pointeur = 0;
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
						listeCode.get(pointeur + 1), relativeBase).intValue(), (long) input);
				pointeur += 2;
				break;
			case "4":
				output.append(getParameter(listeCode, (listeCode.get(pointeur) % 1000) / 100,
						listeCode.get(pointeur + 1), relativeBase) + "\n");
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
		return output.toString();
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

package fr.dufaure.clement.adventofcode.event2019;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day15 {

	static final boolean displayMode = false;

	/**
	 * WALL(0), PATH(1), OXYGEN(2);
	 */

	public static void main(String[] args) {
		long start1 = System.currentTimeMillis();
		part1();
		System.out.println("Execution part 1 en " + (System.currentTimeMillis() - start1) + " ms");
		long start2 = System.currentTimeMillis();
		part2();
		System.out.println("Execution part 2 en " + (System.currentTimeMillis() - start2) + " ms");
	}

	// WARN : Should be launched many times, eventually converge ! In most case
	// 1000000 steps cover all the tank
	static void part1() {
		tank = new HashMap<>();
		Bot b = new Bot();
		tank.put(new Coord(0, 0), 1);
		Random r = new Random();

		int i = 0;

		while (!b.stopped) {
			long mvtChoisi = (long) r.nextInt(4) + 1;
			long output = b.runProgramme(mvtChoisi);
			adjustScreen(mvtChoisi, (int) output, b);
			i++;
			if (i > 1000000) {
				// we hope we find oxygen after 1000000 try
				break;
			}
		}
		afficherScreen();
		Coord target = tank.entrySet().stream().filter(e -> e.getValue() == 2).findFirst().get().getKey();
		System.out.println(pathFinding(new Coord(0, 0), target));

	}

	// WARN !! verify tank is complete (no "?" next to ".")
	static void part2() {
		Coord target = tank.entrySet().stream().filter(e -> e.getValue() == 2).findFirst().get().getKey();
		Set<Coord> casesOxygene = new HashSet<>();
		casesOxygene.add(target);
		Set<Coord> casesARemplirOxygen = tank.entrySet().stream().filter(e -> e.getValue() == 1).map(e -> e.getKey())
				.collect(Collectors.toSet());
		int nombreMinutes = 0;
		while (casesARemplirOxygen.size() > 0) {
			Set<Coord> nouvellesCasesOxygenees = new HashSet<>();
			for (Coord coord : casesOxygene) {
				if (casesARemplirOxygen.contains(new Coord(coord.x, coord.y - 1))) {
					casesARemplirOxygen.remove(new Coord(coord.x, coord.y - 1));
					nouvellesCasesOxygenees.add(new Coord(coord.x, coord.y - 1));
				}
				if (casesARemplirOxygen.contains(new Coord(coord.x, coord.y + 1))) {
					casesARemplirOxygen.remove(new Coord(coord.x, coord.y + 1));
					nouvellesCasesOxygenees.add(new Coord(coord.x, coord.y + 1));
				}
				if (casesARemplirOxygen.contains(new Coord(coord.x - 1, coord.y))) {
					casesARemplirOxygen.remove(new Coord(coord.x - 1, coord.y));
					nouvellesCasesOxygenees.add(new Coord(coord.x - 1, coord.y));
				}
				if (casesARemplirOxygen.contains(new Coord(coord.x + 1, coord.y))) {
					casesARemplirOxygen.remove(new Coord(coord.x + 1, coord.y));
					nouvellesCasesOxygenees.add(new Coord(coord.x + 1, coord.y));
				}
			}
			casesOxygene.addAll(nouvellesCasesOxygenees);
			nombreMinutes++;
		}
		System.out.println(nombreMinutes);
	}

	static int pathFinding(Coord start, Coord target) {
		Set<Coord> coordAtteintes = new HashSet<>();
		coordAtteintes.add(start);
		int i = 0;
		while (!coordAtteintes.contains(target)) {
			Set<Coord> newSet = new HashSet<>();
			for (Coord coord : coordAtteintes) {
				if (tank.getOrDefault(new Coord(coord.x, coord.y + 1), 0) != 0) {
					newSet.add(new Coord(coord.x, coord.y + 1));
				}
				if (tank.getOrDefault(new Coord(coord.x, coord.y - 1), 0) != 0) {
					newSet.add(new Coord(coord.x, coord.y - 1));
				}
				if (tank.getOrDefault(new Coord(coord.x + 1, coord.y), 0) != 0) {
					newSet.add(new Coord(coord.x + 1, coord.y));
				}
				if (tank.getOrDefault(new Coord(coord.x - 1, coord.y), 0) != 0) {
					newSet.add(new Coord(coord.x - 1, coord.y));
				}
			}
			coordAtteintes.addAll(newSet);
			i++;
		}
		return i;
	}

	static void adjustScreen(long mvtChoisi, int resultat, Bot bot) {
		switch ((int) mvtChoisi) {
		case 1:// NORTH
			tank.put(new Coord(bot.position.x, bot.position.y + 1), resultat);
			if (resultat == 0) {
				// et on ne bouge pas le bot
			} else {
				bot.position.y++;
			}
			break;
		case 2:// SOUTH
			tank.put(new Coord(bot.position.x, bot.position.y - 1), resultat);
			if (resultat == 0) {
				// et on ne bouge pas le bot
			} else {
				bot.position.y--;
			}
			break;
		case 3:// WEST
			tank.put(new Coord(bot.position.x - 1, bot.position.y), resultat);
			if (resultat == 0) {
				// et on ne bouge pas le bot
			} else {
				bot.position.x--;
			}
			break;
		case 4:// EAST
			tank.put(new Coord(bot.position.x + 1, bot.position.y), resultat);
			if (resultat == 0) {
				// et on ne bouge pas le bot
			} else {
				bot.position.x++;
			}
			break;
		default:
			System.err.println("Bad input");
			break;
		}
	}

	static void afficherScreen() {
		int minX = tank.keySet().stream().mapToInt(c -> c.x).min().orElseThrow(UnsupportedOperationException::new);
		int minY = tank.keySet().stream().mapToInt(c -> c.y).min().orElseThrow(UnsupportedOperationException::new);
		int maxX = tank.keySet().stream().mapToInt(c -> c.x).max().orElseThrow(UnsupportedOperationException::new);
		int maxY = tank.keySet().stream().mapToInt(c -> c.y).max().orElseThrow(UnsupportedOperationException::new);

		for (int i = Math.abs(minY) + Math.abs(maxY); i >= 0; i--) {
			for (int j = 0; j < Math.abs(minX) + Math.abs(maxX) + 1; j++) {
				if (tank.containsKey(new Coord(j - Math.abs(minX), i - Math.abs(minY)))) {
					if (i == Math.abs(minY) && j == Math.abs(minX)) {
						System.out.print("X");
					} else if (tank.get(new Coord(j - Math.abs(minX), i - Math.abs(minY))) == 0) {
						System.out.print("#");
					} else if (tank.get(new Coord(j - Math.abs(minX), i - Math.abs(minY))) == 1) {
						System.out.print(".");
					} else if (tank.get(new Coord(j - Math.abs(minX), i - Math.abs(minY))) == 2) {
						System.out.print("O");
					} else {
						System.err.println("Error");
					}
				} else {
					System.out.print("?");
				}
			}
			System.out.println();
		}
	}

	static Map<Coord, Integer> tank = new HashMap<>();
	static int minMovementToOxygen = 0;

	static class Coord {
		int x;
		int y;

		Coord(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Coord other = (Coord) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}

	}

	public static class Bot {
		boolean stopped = false;
		long relativeBase = 0;
		int pointeur = 0;
		ArrayList<Long> listeCode = new ArrayList<>();
		Coord position = new Coord(0, 0);

		Bot() {
			String[] liste = ImportUtils.getString("./src/main/resources/2019/day15").split(",");

			for (int i = 0; i < liste.length; i++) {
				listeCode.add(Long.valueOf(liste[i]));
			}
			// on ajoute des 0 a la fin
			for (int i = 0; i < 1000; i++) {
				listeCode.add(0L);
			}
		}

		public long runProgramme(Long input) {
			boolean inputSet = false;
			long output = 0;

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
					if (input != null && !inputSet) {
						listeCode.set(getWhereToWrite(listeCode, (listeCode.get(pointeur) % 1000) / 100,
								listeCode.get(pointeur + 1), relativeBase).intValue(), (long) input);
						pointeur += 2;
						inputSet = true;
					} else {
						return output;
					}
					break;
				case "4":
					output = getParameter(listeCode, (listeCode.get(pointeur) % 1000) / 100,
							listeCode.get(pointeur + 1), relativeBase);
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

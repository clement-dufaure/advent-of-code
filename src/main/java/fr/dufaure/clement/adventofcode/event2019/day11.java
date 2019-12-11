package fr.dufaure.clement.adventofcode.event2019;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day11 {

	public static void main(String[] args) {
		long start1 = System.currentTimeMillis();
		part1();
		System.out.println("Execution part 1 en " + (System.currentTimeMillis() - start1) + " ms");
		long start2 = System.currentTimeMillis();
		part2();
		System.out.println("Execution part 2 en " + (System.currentTimeMillis() - start2) + " ms");
	}

	private static void part1() {
		Robot bot = new Robot();
		bot.execute();
		System.out.println(bot.positionPeintes.size());
	}

	private static void part2() {
		Robot bot = new Robot();
		bot.positionPeintes.put(new Coord(0, 0), 1);
		bot.execute();
		int minX = bot.positionPeintes.keySet().stream().mapToInt(c -> c.x).min()
				.orElseThrow(UnsupportedOperationException::new);
		int minY = bot.positionPeintes.keySet().stream().mapToInt(c -> c.y).min()
				.orElseThrow(UnsupportedOperationException::new);
		int maxX = bot.positionPeintes.keySet().stream().mapToInt(c -> c.x).max()
				.orElseThrow(UnsupportedOperationException::new);
		int maxY = bot.positionPeintes.keySet().stream().mapToInt(c -> c.y).max()
				.orElseThrow(UnsupportedOperationException::new);

		for (int i = Math.abs(minY) + Math.abs(maxY); i >= 0; i--) {
			for (int j = 0; j < Math.abs(minX) + Math.abs(maxX); j++) {
				if (bot.positionPeintes.containsKey(new Coord(j - Math.abs(minX), i - Math.abs(minY)))) {
					if (bot.positionPeintes.get(new Coord(j - Math.abs(minX), i - Math.abs(minY))) == 1) {
						System.out.print("#");
					} else {
						System.out.print(".");
					}
				} else {
					System.out.print(".");
				}
			}
			System.out.println();
		}
	}

	static class Robot {
		RobotBrain brain = new RobotBrain();
		Coord position = new Coord(0, 0);
		Direction direction = Direction.UP;

		Map<Coord, Integer> positionPeintes = new HashMap<>();

		void execute() {
			while (!brain.stopped) {
				int[] output;
				if (positionPeintes.containsKey(position)) {
					output = brain.runProgramme(positionPeintes.get(position));
				} else {
					// noir si on est pas encore passé
					output = brain.runProgramme(0);
				}

				// peinture
				positionPeintes.put(new Coord(position.x, position.y), output[0]);

				// on tourne
				if (output[1] == 0) {
					direction = Direction.turnLeft(direction);
				} else if (output[1] == 1) {
					direction = Direction.turnRight(direction);
				} else {
					System.err.println("Le robot n'a pas tourné");
				}

				// on avance
				switch (direction) {
				case UP:
					position.y++;
					break;
				case DOWN:
					position.y--;
					break;
				case LEFT:
					position.x--;
					break;
				case RIGHT:
					position.x++;
					break;
				default:
					System.err.println("Le robot n'a pas avancé");
					break;
				}

			}
		}

	}

	static enum Direction {
		UP, DOWN, LEFT, RIGHT;

		static Direction turnRight(Direction direction) {
			switch (direction) {
			case UP:
				return RIGHT;
			case DOWN:
				return LEFT;
			case LEFT:
				return UP;
			case RIGHT:
				return DOWN;
			}
			throw new UnsupportedOperationException();
		}

		static Direction turnLeft(Direction direction) {
			switch (direction) {
			case UP:
				return LEFT;
			case DOWN:
				return RIGHT;
			case LEFT:
				return DOWN;
			case RIGHT:
				return UP;
			}
			throw new UnsupportedOperationException();
		}
	}

	static class Coord {
		int x;
		int y;

		public Coord(int x, int y) {
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

	static class RobotBrain {
		int pointeur = 0;
		ArrayList<Long> listeCode = new ArrayList<>();
		long relativeBase = 0;
		boolean stopped = false;

		RobotBrain() {
			String[] liste = ImportUtils.getString("./src/main/resources/2019/day11").split(",");
			for (int i = 0; i < liste.length; i++) {
				listeCode.add(Long.valueOf(liste[i]));
			}
		}

		public int[] runProgramme(int input) {
			int[] output = new int[2];
			boolean inputRead = false;
			boolean outputFirstValue = false;
			boolean outputSecondValue = false;

			// on ajoute des 0 a la fin
			for (int i = 0; i < 1000; i++) {
				listeCode.add(0L);
			}

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
					if (outputFirstValue && outputSecondValue) {
						// wait next input
						return output;
					}

					if (inputRead) {
						throw new UnsupportedOperationException("input already read");
					}
					inputRead = true;
					listeCode.set(getWhereToWrite(listeCode, (listeCode.get(pointeur) % 1000) / 100,
							listeCode.get(pointeur + 1), relativeBase).intValue(), (long) input);
					pointeur += 2;
					break;
				case "4":
					if (!outputFirstValue) {
						outputFirstValue = true;
						output[0] = (int) (long) getParameter(listeCode, (listeCode.get(pointeur) % 1000) / 100,
								listeCode.get(pointeur + 1), relativeBase);
					} else if (!outputSecondValue) {
						outputSecondValue = true;
						output[1] = (int) (long) getParameter(listeCode, (listeCode.get(pointeur) % 1000) / 100,
								listeCode.get(pointeur + 1), relativeBase);
					} else {
						throw new UnsupportedOperationException("Too many outputs !!!");
					}
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
									relativeBase) == getParameter(listeCode, (listeCode.get(pointeur) % 10000) / 1000,
											listeCode.get(pointeur + 2), relativeBase) ? 1L : 0L);
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
			// endOfProgram
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

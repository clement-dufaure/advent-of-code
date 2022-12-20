package fr.dufaure.clement.adventofcode.event2022;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class Day2 {
	
	public String part1(String input) {
		var liste = ImportUtils.getListStringUnParLigne(input);
		var score = liste.stream().map(s -> s.split(" ")).mapToInt(a -> calculerScore(a[0], a[1])).sum();
		return String.valueOf(score);
	}
	
	public String part2(String input) {
		var liste = ImportUtils.getListStringUnParLigne(input);
		var score = liste.stream().map(s -> s.split(" ")).mapToInt(a -> calculerScoreBis(a[0], a[1])).sum();
		return String.valueOf(score);
	}
	
	int calculerScore(String them, String me) {
		return Game.fromXYZ(me).value + Game.fight(Game.fromABC(them), Game.fromXYZ(me));
	}
	
	int calculerScoreBis(String them, String action) {
		return switch (action) {
			case "X" -> Game.fromABC(them).looseAgainst().value;
			case "Y" -> 3 + Game.fromABC(them).value;
			case "Z" -> 6 + Game.fromABC(them).winAgainst().value;
			default -> throw new UnsupportedOperationException();
		};
	}
	
	private enum Game {
		ROCK(1), PAPER(2), SCISSORS(3);
		
		int value;
		
		Game(int value) {
			this.value = value;
		}
		
		static Game fromABC(String abc) {
			return switch (abc) {
				case "A" -> ROCK;
				case "B" -> PAPER;
				case "C" -> SCISSORS;
				default -> throw new UnsupportedOperationException();
			};
		}
		
		static Game fromXYZ(String xyz) {
			return switch (xyz) {
				case "X" -> ROCK;
				case "Y" -> PAPER;
				case "Z" -> SCISSORS;
				default -> throw new UnsupportedOperationException();
			};
		}
		
		static int fight(Game them, Game me) {
			if (them.equals(me)) {
				return 3;
			}
			if (them.winAgainst().equals(me)) {
				return 6;
			}
			return 0;
		}
		
		Game winAgainst() {
			return switch (this) {
				case ROCK -> PAPER;
				case PAPER -> SCISSORS;
				case SCISSORS -> ROCK;
			};
		}
		
		Game looseAgainst() {
			return switch (this) {
				case ROCK -> SCISSORS;
				case SCISSORS -> PAPER;
				case PAPER -> ROCK;
			};
		}
	}
}

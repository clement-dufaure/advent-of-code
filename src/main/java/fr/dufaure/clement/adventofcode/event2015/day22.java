package fr.dufaure.clement.adventofcode.event2015;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class day22 {

	public static void main(String[] args) {
		long start1 = System.currentTimeMillis();
		part1();
		System.out.println("Execution part 1 en " + (System.currentTimeMillis() - start1) + " ms");
		long start2 = System.currentTimeMillis();
		part2();
		System.out.println("Execution part 2 en " + (System.currentTimeMillis() - start2) + " ms");
	}

	// INPUT
	public static final int HIT_POINTS = 51;
	public static final int DAMAGE = 9;
	public static final boolean HARD_MODE = true;

	static int minMana = Integer.MAX_VALUE;

	// soft 900
	// hard moins de
	// 1309 too high
	private static void part1() {
		for (int i = 0; i < 1000000; i++) {
			play();
		}
		System.out.println(minMana);
	}

	private static void part2() {
	}

	static void play() {
		Player you = new Player(50, 0, 0, 500);
		Player boss = new Player(HIT_POINTS, DAMAGE, 0, 0);
		Random r = new SecureRandom();
		int manaDepense = 0;
		while (you.hitPoints > 0 && boss.hitPoints > 0) {
			List<Spell> sortPossibles = Arrays.asList(Spell.values()).stream().filter(s -> s.cost < you.mana)
					.collect(Collectors.toList());
			// autorise au dernier tour d'effet
			if (you.effetsEnCoursEtNombreToursRestants.get(Effect.SHIELD) > 1) {
				sortPossibles.remove(Spell.SHIELD);
			}
			// autorise au dernier tour d'effet
			if (you.effetsEnCoursEtNombreToursRestants.get(Effect.RECHARGE) > 1) {
				sortPossibles.remove(Spell.RECHARGE);
			}
			if (boss.effetsEnCoursEtNombreToursRestants.get(Effect.POISON) > 1) {
				sortPossibles.remove(Spell.POISON);
			}
			if (sortPossibles.size() == 0) {
				you.hitPoints = 0;
				break;
			}
			Spell sortJete = sortPossibles.get(r.nextInt(sortPossibles.size()));
			manaDepense += sortJete.cost;
			makeTurn(you, sortJete, boss);
		}
		if (you.hitPoints > 0 && manaDepense < minMana) {
			minMana = manaDepense;
		}
	}

	static void makeTurn(Player you, Spell sortChoisi, Player boss) {
		// YOUR TURN
		if (HARD_MODE) {
			you.hitPoints--;
			if (you.hitPoints <= 0) {
				return;
			}
		}
		if (boss.effetsEnCoursEtNombreToursRestants.get(Effect.POISON) > 0) {
			boss.hitPoints -= 3;
			boss.effetsEnCoursEtNombreToursRestants.put(Effect.POISON,
					boss.effetsEnCoursEtNombreToursRestants.get(Effect.POISON) - 1);
		}
		if (boss.hitPoints <= 0) {
			return;
		}
		if (you.effetsEnCoursEtNombreToursRestants.get(Effect.SHIELD) > 0) {
			you.effetsEnCoursEtNombreToursRestants.put(Effect.SHIELD,
					you.effetsEnCoursEtNombreToursRestants.get(Effect.SHIELD) - 1);
			if (you.effetsEnCoursEtNombreToursRestants.get(Effect.SHIELD) == 0) {
				you.armor = 0;
			}
		}
		if (you.effetsEnCoursEtNombreToursRestants.get(Effect.RECHARGE) > 0) {
			you.mana += 101;
			you.effetsEnCoursEtNombreToursRestants.put(Effect.RECHARGE,
					you.effetsEnCoursEtNombreToursRestants.get(Effect.RECHARGE) - 1);
		}
		you.mana -= sortChoisi.cost;
		switch (sortChoisi) {
		case MAGIC_MISSILE:
			boss.hitPoints -= 4;
			break;
		case DRAIN:
			you.hitPoints += 2;
			boss.hitPoints -= 2;
			break;
		case SHIELD:
			if (you.effetsEnCoursEtNombreToursRestants.get(Effect.SHIELD) == 0) {
				you.armor = 7;
				you.effetsEnCoursEtNombreToursRestants.put(Effect.SHIELD, 6);
			}
			break;
		case POISON:
			if (boss.effetsEnCoursEtNombreToursRestants.get(Effect.POISON) == 0) {
				boss.effetsEnCoursEtNombreToursRestants.put(Effect.POISON, 6);
			}
			break;
		case RECHARGE:
			if (you.effetsEnCoursEtNombreToursRestants.get(Effect.RECHARGE) == 0) {
				you.effetsEnCoursEtNombreToursRestants.put(Effect.RECHARGE, 5);
			}
			break;
		}

		// BOSS TURN
		if (HARD_MODE) {
			you.hitPoints--;
			if (you.hitPoints <= 0) {
				return;
			}
		}
		if (boss.effetsEnCoursEtNombreToursRestants.get(Effect.POISON) > 0) {
			boss.hitPoints -= 3;
			boss.effetsEnCoursEtNombreToursRestants.put(Effect.POISON,
					boss.effetsEnCoursEtNombreToursRestants.get(Effect.POISON) - 1);
		}
		if (boss.hitPoints <= 0) {
			return;
		}
		if (you.effetsEnCoursEtNombreToursRestants.get(Effect.SHIELD) > 0) {
			you.effetsEnCoursEtNombreToursRestants.put(Effect.SHIELD,
					you.effetsEnCoursEtNombreToursRestants.get(Effect.SHIELD) - 1);
			if (you.effetsEnCoursEtNombreToursRestants.get(Effect.SHIELD) == 0) {
				you.armor = 0;
			}
		}
		if (you.effetsEnCoursEtNombreToursRestants.get(Effect.RECHARGE) > 0) {
			you.mana += 101;
			you.effetsEnCoursEtNombreToursRestants.put(Effect.RECHARGE,
					you.effetsEnCoursEtNombreToursRestants.get(Effect.RECHARGE) - 1);
		}
		// ATTAQUE
		int hitByBoss = boss.damage - you.armor > 1 ? boss.damage - you.armor : 1;
		you.hitPoints -= hitByBoss;
	}

	static enum Spell {
		MAGIC_MISSILE(53), DRAIN(73), SHIELD(113), POISON(173), RECHARGE(229);

		int cost;

		private Spell(int cost) {
			this.cost = cost;
		}
	}

	static enum Effect {
		SHIELD, POISON, RECHARGE;
	}

	static class Player {
		int hitPoints;
		int damage;
		int armor;
		int mana;
		Map<Effect, Integer> effetsEnCoursEtNombreToursRestants = new HashMap<>();

		Player(int hitPointsMax, int damage, int armor, int mana) {
			this.hitPoints = hitPointsMax;
			this.damage = damage;
			this.armor = armor;
			this.mana = mana;
			effetsEnCoursEtNombreToursRestants.put(Effect.SHIELD, 0);
			effetsEnCoursEtNombreToursRestants.put(Effect.POISON, 0);
			effetsEnCoursEtNombreToursRestants.put(Effect.RECHARGE, 0);

		}
	}

}
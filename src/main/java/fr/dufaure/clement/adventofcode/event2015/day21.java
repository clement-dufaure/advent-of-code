package fr.dufaure.clement.adventofcode.event2015;

import java.util.ArrayList;
import java.util.List;

public class day21 {

  public static void main(String[] args) {
    long start1 = System.currentTimeMillis();
    part1();
    System.out.println("Execution part 1 en " + (System.currentTimeMillis() - start1) + " ms");
    long start2 = System.currentTimeMillis();
    part2();
    System.out.println("Execution part 2 en " + (System.currentTimeMillis() - start2) + " ms");
  }

  // INPUT
  public static final int HIT_POINTS = 109;
  public static final int DAMAGE = 8;
  public static final int ARMOR = 2;

  static List<Item> weapons = new ArrayList<>();
  static List<Item> armors = new ArrayList<>();
  static List<Item> rings = new ArrayList<>();

  static {
    // creation boutique
    // Dagger
    weapons.add(new Item(8, 4, 0));
    // Shortsword
    weapons.add(new Item(10, 5, 0));
    // Warhammer
    weapons.add(new Item(25, 6, 0));
    // Longsword
    weapons.add(new Item(40, 7, 0));
    // Greataxe
    weapons.add(new Item(74, 8, 0));

    // pas d'armure
    armors.add(new Item(0, 0, 0));
    // Leather
    armors.add(new Item(13, 0, 1));
    // Chainmail
    armors.add(new Item(31, 0, 2));
    // Splintmail
    armors.add(new Item(53, 0, 3));
    // Bandedmail
    armors.add(new Item(75, 0, 4));
    // Platemail
    armors.add(new Item(102, 0, 5));

    rings.add(new Item(0, 0, 0));
    rings.add(new Item(0, 0, 0));
    // Damage +1
    rings.add(new Item(25, 1, 0));
    // Damage +2
    rings.add(new Item(50, 2, 0));
    // Damage +3
    rings.add(new Item(100, 3, 0));
    // Defense +1
    rings.add(new Item(20, 0, 1));
    // Defense +2
    rings.add(new Item(40, 0, 2));
    // Defense +3
    rings.add(new Item(80, 0, 3));
  }

  private static void part1() {
    int minCoutsSuffisantpourGagner = Integer.MAX_VALUE;
    // Choix de l'arme
    for (Item weapon : weapons) {
      // Choix de l'armure eventuellement vide (géré par une armure gratos dans la
      // liste)
      for (Item armor : armors) {
        // Choix de 0 1 ou 2 anneaux, impossible de prendre 2 fois le meme anneau
        // pas d'annau géré par un anneau gratos
        for (int rgAnneau1 = 0; rgAnneau1 < rings.size(); rgAnneau1++) {
          for (int rgAnneau2 = rgAnneau1 + 1; rgAnneau2 < rings.size(); rgAnneau2++) {
            Item anneau1 = rings.get(rgAnneau1);
            Item anneau2 = rings.get(rgAnneau2);
            Player theBoss = new Player(HIT_POINTS, DAMAGE, ARMOR);
            Player you = new Player(100, weapon.damage + anneau1.damage + anneau2.damage,
                armor.armor + anneau1.armor + anneau2.armor);
            if (play(you, theBoss)) {
              int totalCost = weapon.cost + armor.cost + anneau1.cost + anneau2.cost;
              if (totalCost < minCoutsSuffisantpourGagner) {
                minCoutsSuffisantpourGagner = totalCost;
              }
            }
          }
        }
      }
    }
    System.out.println(minCoutsSuffisantpourGagner);
  }

  private static void part2() {
    int maxCoutsSuffisantpourPerdre = 0;
    // Choix de l'arme
    for (Item weapon : weapons) {
      // Choix de l'armure eventuellement vide (géré par une armure gratos dans la
      // liste)
      for (Item armor : armors) {
        // Choix de 0 1 ou 2 anneaux, impossible de prendre 2 fois le meme anneau
        // pas d'annau géré par un anneau gratos
        for (int rgAnneau1 = 0; rgAnneau1 < rings.size(); rgAnneau1++) {
          for (int rgAnneau2 = rgAnneau1 + 1; rgAnneau2 < rings.size(); rgAnneau2++) {
            Item anneau1 = rings.get(rgAnneau1);
            Item anneau2 = rings.get(rgAnneau2);
            Player theBoss = new Player(HIT_POINTS, DAMAGE, ARMOR);
            Player you = new Player(100, weapon.damage + anneau1.damage + anneau2.damage,
                armor.armor + anneau1.armor + anneau2.armor);
            if (!play(you, theBoss)) {
              int totalCost = weapon.cost + armor.cost + anneau1.cost + anneau2.cost;
              if (totalCost > maxCoutsSuffisantpourPerdre) {
                maxCoutsSuffisantpourPerdre = totalCost;
              }
            }
          }
        }
      }
    }
    System.out.println(maxCoutsSuffisantpourPerdre);
  }

  // true if you win
  static boolean play(Player you, Player boss) {
    int hitsEncaisseParYouAtTurn = boss.damage - you.armor > 1 ? boss.damage - you.armor : 1;
    int hitsEncaisseParBossAtTurn = you.damage - boss.armor > 1 ? you.damage - boss.armor : 1;
    int nombreDeTourPourTuerYou = you.hitPoints / hitsEncaisseParYouAtTurn;
    int nombreDeTourPourTuerBoss = boss.hitPoints / hitsEncaisseParBossAtTurn;
    // geq car you commence, donc a egalite de tour c'est you qui abat en premier
    return nombreDeTourPourTuerYou >= nombreDeTourPourTuerBoss;
  }

  static class Player {
    int hitPoints;
    final int damage;
    final int armor;

    Player(int hitPointsMax, int damage, int armor) {
      this.hitPoints = hitPointsMax;
      this.damage = damage;
      this.armor = armor;
    }
  }

  static class Item {
    final int cost;
    final int damage;
    final int armor;

    Item(int cost, int damage, int armor) {
      this.cost = cost;
      this.damage = damage;
      this.armor = armor;
    }
  }

}
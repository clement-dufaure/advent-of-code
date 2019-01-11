package fr.dufaure.clement.adventofcode.event2018;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day23 {

  public static void main(String[] args) {
    parseData();
    // Bot strongest = part1();
    long start = System.currentTimeMillis();
    // part2(bots.get(0));
    part2();
    System.out.println("Resolu en " + (System.currentTimeMillis() - start) + " ms");
    // 156823889 too low (max=807)
  }

  static Bot part1() {
    Bot strongest = findStrongestBot();
    System.out.println(strongest.r);
    System.out.println(howManyBotsInRange(strongest));
    return strongest;
  }

  // Un peu blasé que cette methode fonctionne ... j'abandonne (provisoirement) mon gradient
  static void part2() {
    TreeMap<Integer, Integer> ranges = new TreeMap<>();
    for (Bot b : bots) {
      ranges.put(Math.max(0, b.distanceOrigine() - b.r), 1);
      ranges.put(b.distanceOrigine() + b.r, -1);
    }
    int count = 0;
    int result = 0;
    int maxCount = 0;
    for (Map.Entry<Integer, Integer> e : ranges.entrySet()) {
      count += e.getValue();
      if (count > maxCount) {
        result = e.getKey();
        maxCount = count;
      }
    }
    System.out.println(result);
  }

  static void part2(Bot b) {
    // Set<Coord> maximumsLocaux =
    // getCoinsRange().stream().map(day23::findMaxLocal).collect(Collectors.toSet());
    // int max = maximumsLocaux.stream().mapToInt(c -> c.nbRobot).max().getAsInt();
    Coord c = new Coord(b.x + b.r, b.y + b.r, b.z + b.r);
    Coord maximumPotentiel = findMaxLocal(c);
    System.out.println(maximumPotentiel.nbRobot);
    // Coord pointrecherche = maximumsLocaux.stream().filter(c -> c.nbRobot == max)
    // .sorted((c1, c2) -> c1.distanceOrigine < c2.distanceOrigine ? -1 :
    // 1).findFirst().get();
    // System.out.println(pointrecherche.distanceOrigine);
    System.out.println(maximumPotentiel.distanceOrigine);
  }

  // departs "pas mal" de monte carlo : points qui ont le plus de chance d'etre
  // des maximums
  static List<Coord> getCoinsRange() {
    List<Coord> coins = new ArrayList<>();
    for (Bot bot : bots) {
      coins.add(new Coord(bot.x + bot.r, bot.y + bot.r, bot.z + bot.r));
      coins.add(new Coord(bot.x + bot.r, bot.y + bot.r, bot.z - bot.r));
      coins.add(new Coord(bot.x + bot.r, bot.y - bot.r, bot.z + bot.r));
      coins.add(new Coord(bot.x + bot.r, bot.y - bot.r, bot.z - bot.r));
      coins.add(new Coord(bot.x - bot.r, bot.y + bot.r, bot.z + bot.r));
      coins.add(new Coord(bot.x - bot.r, bot.y + bot.r, bot.z - bot.r));
      coins.add(new Coord(bot.x - bot.r, bot.y - bot.r, bot.z + bot.r));
      coins.add(new Coord(bot.x - bot.r, bot.y - bot.r, bot.z - bot.r));
    }
    return coins;
  }

  // methode gradient applique a une fonction discrete : il y a des plages fixes
  // - soit trouver le plage fixe et avancer sur les bords : comlique a coder
  // - soit trouver le max local sur un cube autour du point initial

  static final int INTERVALE_RECHERCHE_GRADIENT = 10;

  static int compteur = 0;

  static Coord findMaxLocal(Coord c) {
    // if (compteur++ % 100 == 0) {
    System.out.println(compteur++);
    // }
    // System.out.println("traitement");
    // int nbRobotRef = howManyBotsInRange(x, y, z);
    // int distanceOrigineRef = Math.abs(x) + Math.abs(y) + Math.abs(z);
    Coord meilleureCoord = c;
    // boolean hasChange = true;
    loop: while (true) {
      Coord nouvelleMeilleureCoord = null;
      // System.out.println("loop");
      for (int xr = meilleureCoord.x - INTERVALE_RECHERCHE_GRADIENT; xr < meilleureCoord.x
          + INTERVALE_RECHERCHE_GRADIENT; xr++) {
        for (int yr = meilleureCoord.y - INTERVALE_RECHERCHE_GRADIENT; yr < meilleureCoord.y
            + INTERVALE_RECHERCHE_GRADIENT; yr++) {
          for (int zr = meilleureCoord.z - INTERVALE_RECHERCHE_GRADIENT; zr < meilleureCoord.z
              + INTERVALE_RECHERCHE_GRADIENT; zr++) {
            // System.out.println(xr + " " + yr + " " + zr);
            Coord coordTest = new Coord(xr, yr, zr);
            if (coordTest.nbRobot > meilleureCoord.nbRobot
                || (coordTest.nbRobot == meilleureCoord.nbRobot
                    && coordTest.distanceOrigine < meilleureCoord.distanceOrigine)) {
              nouvelleMeilleureCoord = coordTest;
              // System.out.println(meilleureCoord.nbRobot);
              // System.out.println(meilleureCoord.distanceOrigine);
              System.out.println(nouvelleMeilleureCoord.nbRobot);
              System.out.println(nouvelleMeilleureCoord.distanceOrigine);
              // System.out.println("new coord : " + nouvelleMeilleureCoord.x + ","
              // + nouvelleMeilleureCoord.y + "," + nouvelleMeilleureCoord.z);
              // hasChange = true;
              meilleureCoord = nouvelleMeilleureCoord;
              continue loop;
            }
          }
        }
      }
      // if (nouvelleMeilleureCoord != null) {
      // meilleureCoord = nouvelleMeilleureCoord;
      // } else {
      break;
      // }
    }
    return meilleureCoord;
  }

  static List<Bot> bots = new ArrayList<>();

  static class Coord {
    Coord(int x, int y, int z) {
      this.x = x;
      this.y = y;
      this.z = z;
      this.nbRobot = howManyBotsInRange(x, y, z);
      this.distanceOrigine = Math.abs(x) + Math.abs(y) + Math.abs(z);
    }

    int x;
    int y;
    int z;
    int nbRobot;
    int distanceOrigine;
  }

  static void parseData() {
    List<String> lignes = ImportUtils.getListStringUnParLigne("./src/main/resources/2018/day23");
    for (String ligne : lignes) {
      Matcher matcher =
          Pattern.compile("pos=<(-?[0-9]*),(-?[0-9]*),(-?[0-9]*)>, r=([0-9]*)").matcher(ligne);
      matcher.find();
      Bot b = new Bot();
      b.x = Integer.parseInt(matcher.group(1));
      b.y = Integer.parseInt(matcher.group(2));
      b.z = Integer.parseInt(matcher.group(3));
      b.r = Integer.parseInt(matcher.group(4));
      bots.add(b);
    }
  }

  static Bot findStrongestBot() {
    return bots.stream().sorted((b1, b2) -> b1.r < b2.r ? 1 : -1).findFirst().get();
  }

  static int howManyBotsInRange(Bot bot) {
    int nbBotInRange = 0;
    for (Bot b : bots) {
      if (Math.abs(b.x - bot.x) + Math.abs(b.y - bot.y) + Math.abs(b.z - bot.z) <= bot.r) {
        nbBotInRange++;
      }
    }
    return nbBotInRange;
  }

  static int howManyBotsInRange(int x, int y, int z) {
    int nbBotInRange = 0;
    for (Bot b : bots) {
      if (Math.abs(b.x - x) + Math.abs(b.y - y) + Math.abs(b.z - z) <= b.r) {
        nbBotInRange++;
      }
    }
    return nbBotInRange;
  }

  static class Bot {
    int x;
    int y;
    int z;
    int r;

    int distanceOrigine() {
      return Math.abs(x) + Math.abs(y) + Math.abs(z);
    }
  }

}

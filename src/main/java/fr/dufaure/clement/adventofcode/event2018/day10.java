package fr.dufaure.clement.adventofcode.event2018;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day10 {
  public static void main(String[] args) {
    long start1 = System.currentTimeMillis();
    part1();
    System.out.println("Execution part 1 en " + (System.currentTimeMillis() - start1) + " ms");
  }

  static List<Star> stars = ImportUtils.getListStringUnParLigne("./src/main/resources/2018/day10")
      .stream().map(day10::parseLigne).collect(Collectors.toList());

  static int minHauteurGrille;
  static int minlargeurGrille;


  public static Star parseLigne(String ligne) {
    Matcher matcher = Pattern
        .compile("position=<([- ][0-9]+), ([- ][0-9]+)> velocity=<([- ][0-9]+), ([- ][0-9]+)>")
        .matcher(ligne);
    matcher.find();
    Star s = new Star();
    s.x = Integer.parseInt(matcher.group(1).trim());
    s.y = Integer.parseInt(matcher.group(2).trim());
    s.velocityX = Integer.parseInt(matcher.group(3).trim());
    s.velocityY = Integer.parseInt(matcher.group(4).trim());
    return s;
  }

  public static void bougerEtoiles() {
    for (Star s : stars) {
      s.x = s.x + s.velocityX;
      s.y = s.y + s.velocityY;
    }
  }

  public static boolean afficherGrille() {
    System.out.flush();
    int maxX = stars.stream().mapToInt(s -> s.x).max().getAsInt();
    int maxY = stars.stream().mapToInt(s -> s.y).max().getAsInt();
    int minX = stars.stream().mapToInt(s -> s.x).min().getAsInt();
    int minY = stars.stream().mapToInt(s -> s.y).min().getAsInt();

    // System.out.println((maxX - minX) + " " + (maxY - minY));

    // RECHERCHE DU MINIMUM "A la main"
    if ((maxX - minX) == minlargeurGrille && (maxY - minY) == minHauteurGrille) {
      List<List<String>> grille = new ArrayList<>();
      for (int i = 0; i < Math.abs(maxY) + Math.abs(minY) + 1; i++) {
        List<String> ligne = new ArrayList<>();
        for (int j = 0; j < Math.abs(maxX) + Math.abs(minX) + 1; j++) {
          ligne.add(".");
        }
        grille.add(ligne);
      }

      for (Star s : stars) {
        grille.get(s.y + Math.abs(minY)).set(s.x + Math.abs(minX), "#");
      }

      for (List<String> ligne : grille) {
        System.out.println(ligne);
      }
      return true;
    }
    return false;
  }

  public static void part1() {

    // Recherche de la plus petite grille
    int maxX = stars.stream().mapToInt(s -> s.x).max().getAsInt();
    int maxY = stars.stream().mapToInt(s -> s.y).max().getAsInt();
    int minX = stars.stream().mapToInt(s -> s.x).min().getAsInt();
    int minY = stars.stream().mapToInt(s -> s.y).min().getAsInt();
    minHauteurGrille = maxY - minY;
    minlargeurGrille = maxX - minX;
    boolean largeurReaugmente = false;
    boolean hauteurReaugmente = false;
    for (int i = 0; i < 25000; i++) {
      bougerEtoiles();
      maxX = stars.stream().mapToInt(s -> s.x).max().getAsInt();
      maxY = stars.stream().mapToInt(s -> s.y).max().getAsInt();
      minX = stars.stream().mapToInt(s -> s.x).min().getAsInt();
      minY = stars.stream().mapToInt(s -> s.y).min().getAsInt();
      if (minHauteurGrille > maxY - minY) {
        minHauteurGrille = maxY - minY;
      } else {
        hauteurReaugmente = true;
      }
      if (minlargeurGrille > maxX - minX) {
        minlargeurGrille = maxX - minX;
      } else {
        largeurReaugmente = true;
      }
      if (largeurReaugmente && hauteurReaugmente) {
        break;
      }
    }

    // reinitialisation grille
    stars = ImportUtils.getListStringUnParLigne("./src/main/resources/2018/day10").stream()
        .map(day10::parseLigne).collect(Collectors.toList());

    int i = 1;
    while (true) {
      bougerEtoiles();
      if (afficherGrille()) {
        System.out.println(i);
        break;
      }
      i++;
    }
  }


  public static class Star {
    int x;
    int y;
    int velocityX;
    int velocityY;
  }

}

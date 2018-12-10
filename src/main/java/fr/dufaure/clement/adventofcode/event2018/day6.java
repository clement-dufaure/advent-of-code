package fr.dufaure.clement.adventofcode.event2018;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day6 {

  public static void main(String[] args) {
    long start1 = System.currentTimeMillis();
    part1();
    System.out.println("Execution part 1 en " + (System.currentTimeMillis() - start1) + " ms");
    long start2 = System.currentTimeMillis();
    part2();
    System.out.println("Execution part 2 en " + (System.currentTimeMillis() - start2) + " ms");
  }

  static int refCoord = 0;

  public static Coordinate parseLigne(String ligne) {
    Matcher matcher = Pattern.compile("([0-9]*), ([0-9]*)").matcher(ligne);
    matcher.find();
    refCoord++;
    return new Coordinate("" + refCoord, Integer.parseInt(matcher.group(1)),
        Integer.parseInt(matcher.group(2)));
  }

  public static List<String> coordinatesString =
      ImportUtils.getListStringUnParLigne("./src/main/resources/2018/day6");
  public static List<Coordinate> coordinates =
      coordinatesString.stream().map(day6::parseLigne).collect(Collectors.toList());
  public static int maxX = coordinates.stream().mapToInt(Coordinate::getX).max().getAsInt();
  public static int maxY = coordinates.stream().mapToInt(Coordinate::getY).max().getAsInt();

  public static List<List<String>> grid = new ArrayList<>();
  static {
    // on construit une grille de taille maxX+1 et maxY+1 (coordonnes poitives)
    for (int i = 0; i < maxX + 1; i++) {
      List<String> ligne = new ArrayList<>();
      for (int j = 0; j < maxY + 1; j++) {
        ligne.add(".");
      }
      grid.add(ligne);
    }
    for (Coordinate c : coordinates) {
      grid.get(c.x).set(c.y, c.id);
    }
  }


  public static String manhattanProche(int x, int y) {
    int minActuel = maxX + maxY;
    String idDuMin = "";
    int nombreDeCoordAtteignantLeMin = -2000;
    for (Coordinate c : coordinates) {
      int distance = Math.abs(c.x - x) + Math.abs(c.y - y);
      if (distance < minActuel) {
        minActuel = distance;
        idDuMin = c.id;
        nombreDeCoordAtteignantLeMin = 1;
      } else if (distance == minActuel) {
        nombreDeCoordAtteignantLeMin++;
      }
    }

    if (nombreDeCoordAtteignantLeMin > 1) {
      return ".";
    } else if (nombreDeCoordAtteignantLeMin == 1) {
      return idDuMin;
    } else {
      System.err.println("ERROR");
      return "X";
    }
  }

  public static int sommeDistancesManhattan(int x, int y) {
    return coordinates.stream().mapToInt(c -> Math.abs(c.x - x) + Math.abs(c.y - y)).sum();
  }

  // si l'aire touche le bord de la grid elle est infinie
  public static boolean faitPartieDuCadre(String id) {
    for (String ident : grid.get(0)) {
      if (ident.equals(id)) {
        return true;
      }
    }

    for (String ident : grid.get(grid.size() - 1)) {
      if (ident.equals(id)) {
        return true;
      }
    }

    for (List<String> listeIdent : grid.subList(1, grid.size() - 1)) {
      if (listeIdent.get(0).equals(id) || listeIdent.get(listeIdent.size() - 1).equals(id)) {
        return true;
      }
    }

    return false;
  }

  public static Map<String, Integer> tailleAires = new HashMap<>();

  public static void part1() {
    // Pour chaque case calcul du point le plus proche
    for (int i = 0; i < maxX + 1; i++) {
      for (int j = 0; j < maxY + 1; j++) {
        String manhattan = manhattanProche(i, j);
        grid.get(i).set(j, manhattan);
        tailleAires.put(manhattan, tailleAires.getOrDefault(manhattan, 0) + 1);
      }
    }

    System.out.println(tailleAires.entrySet().stream().filter(e -> !e.getKey().equals("."))
        .filter(e -> !faitPartieDuCadre(e.getKey())).max(Comparator.comparing(e -> e.getValue()))
        .get().getValue());

  }

  public static void part2() {
    int nbTotalCasesInf10000 = 0;
    for (int i = 0; i < maxX + 1; i++) {
      for (int j = 0; j < maxY + 1; j++) {
        if (sommeDistancesManhattan(i, j) < 10000) {
          nbTotalCasesInf10000++;
        }
      }
    }
    System.out.println(nbTotalCasesInf10000);
  }

  public static class Coordinate {
    String id;
    int x;
    int y;

    public Coordinate(int x, int y) {
      this.x = x;
      this.y = y;
    }

    public Coordinate(String id, int x, int y) {
      this.id = id;
      this.x = x;
      this.y = y;
    }

    int getX() {
      return x;
    }

    int getY() {
      return y;
    }
  }
}

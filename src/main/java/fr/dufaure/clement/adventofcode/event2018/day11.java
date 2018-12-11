package fr.dufaure.clement.adventofcode.event2018;

import java.util.ArrayList;
import java.util.List;

public class day11 {
  public static void main(String[] args) {
    long start1 = System.currentTimeMillis();
    part1();
    System.out.println("Execution part 1 en " + (System.currentTimeMillis() - start1) + " ms");
    long start2 = System.currentTimeMillis();
    part2();
    System.out.println("Execution part 1 en " + (System.currentTimeMillis() - start2) + " ms");
  }


  public static final int serialNumber = 1723;
  // public static final int serialNumber = 18;

  static List<List<Integer>> grille;


  public static void remplirTotauxPowerCell() {
    grille = new ArrayList<>();
    for (int i = 0; i < 300; i++) {
      List<Integer> ligne = new ArrayList<>();
      for (int j = 0; j < 300; j++) {
        ligne.add(calculCase(i + 1, j + 1));
      }
      grille.add(ligne);
    }
  }

  public static int calculCase(int x, int y) {
    int rackId = x + 10;
    int powerLevel = rackId * y;
    powerLevel += serialNumber;
    powerLevel *= rackId;
    if (powerLevel < 100) {
      powerLevel = 0;
    } else {
      String powerLevelString = "" + powerLevel;
      powerLevel = Integer.parseInt(
          powerLevelString.substring(powerLevelString.length() - 3, powerLevelString.length() - 2));
    }
    powerLevel -= 5;
    return powerLevel;
  }


  public static int valeurSquareTopLeft(int x, int y, int size) {
    int valeur = 0;
    for (int i = x; i <= x + size - 1; i++) {
      for (int j = y; j <= y + size - 1; j++) {
        valeur += grille.get(i).get(j);
      }
    }
    return valeur;
  }

  public static boolean afficherGrille() {


    for (List<Integer> ligne : grille) {
      System.out.println(ligne);
    }
    return true;

  }

  public static void part1() {
    remplirTotauxPowerCell();
    int maximum = 0;
    int xDuMaximum = 0;
    int yDuMaximum = 0;

    for (int i = 0; i < 298; i++) {
      for (int j = 0; j < 298; j++) {
        int valeurSquare = valeurSquareTopLeft(i, j, 3);
        if (valeurSquare > maximum) {
          maximum = valeurSquare;
          xDuMaximum = i + 1;
          yDuMaximum = j + 1;

        }

      }
    }
    System.out.println(xDuMaximum + "," + yDuMaximum);
  }

  public static void part2() {
    remplirTotauxPowerCell();
    int maximum = 0;
    int xDuMaximum = 0;
    int yDuMaximum = 0;
    int sizeDuMaximum = 0;
    for (int i = 0; i < 300; i++) {
      System.out.println(i);
      for (int j = 0; j < 300; j++) {
        for (int size = 1; size <= (Math.min(300 - i, 300 - j)); size++) {
          int valeurSquare = valeurSquareTopLeft(i, j, size);
          if (valeurSquare > maximum) {
            maximum = valeurSquare;
            xDuMaximum = i + 1;
            yDuMaximum = j + 1;
            sizeDuMaximum = size;
          }
        }
      }
    }
    System.out.println(xDuMaximum + "," + yDuMaximum + "," + sizeDuMaximum);
  }


  public static class Star {
    int x;
    int y;
    int velocityX;
    int velocityY;
  }

}

package fr.dufaure.clement.adventofcode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class day2 {

  public static void main(String[] args) {
    day2Part1();
    day2Part2();
  }


  public static List<String> importFile(String path) {
    BufferedReader buffer;
    List<String> lignes = new ArrayList<>();
    try {
      buffer = new BufferedReader(new FileReader(path));
      String line;
      while ((line = buffer.readLine()) != null) {
        StringBuilder input = new StringBuilder();
        input.append(line);
        lignes.add(input.toString());
      }
      buffer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return lignes;
  }

  public static List<Integer> parseligne(String ligne) {
    List<Integer> liste = new ArrayList<>();
    StringBuilder entierEnCours = new StringBuilder();
    for (int i = 0; i < ligne.length(); i++) {
      if (ligne.charAt(i) != '\t') {
        entierEnCours.append(ligne.charAt(i));
      } else {
        liste.add(Integer.parseInt(entierEnCours.toString()));
        entierEnCours = new StringBuilder();
      }
    }
    liste.add(Integer.parseInt(entierEnCours.toString()));
    return liste;
  }

  public static void day2Part1() {
    List<String> data = importFile("./src/main/resources/day2");
    int somme = 0;
    for (String ligne : data) {
      List<Integer> entiers = parseligne(ligne);
      int max = Collections.max(entiers);
      int min = Collections.min(entiers);
      somme += max - min;
    }
    System.out.println(somme);
  }

  public static void day2Part2() {
    List<String> data = importFile("./src/main/resources/day2");
    int somme = 0;
    for (String ligne : data) {
      List<Integer> entiers = parseligne(ligne);
      outerloop: for (int i = 0; i < entiers.size(); i++) {
        for (int j = i + 1; j < entiers.size(); j++) {
          int ii = entiers.get(i);
          int jj = entiers.get(j);
          if (ii % jj == 0) {
            somme += ii / jj;
            break outerloop;
          }
          if (jj % ii == 0) {
            somme += jj / ii;
            break outerloop;
          }
        }
      }
    }
    System.out.println(somme);
  }


}

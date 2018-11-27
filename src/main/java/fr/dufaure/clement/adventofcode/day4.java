package fr.dufaure.clement.adventofcode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class day4 {

  public static void main(String[] args) {
    day3Part1();
    day3Part2();
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

  public static List<String> parseligne(String ligne) {
    List<String> liste = new ArrayList<>();
    StringBuilder stringEnCours = new StringBuilder();
    for (int i = 0; i < ligne.length(); i++) {
      if (ligne.charAt(i) != ' ') {
        stringEnCours.append(ligne.charAt(i));
      } else {
        liste.add(stringEnCours.toString());
        stringEnCours = new StringBuilder();
      }
    }
    liste.add(stringEnCours.toString());
    return liste;
  }

  public static boolean sontDesAnagrammes(String str1, String str2) {
    if (str1.length() != str2.length()) {
      return false;
    } else {
      Map<Character, Integer> lettresStr1 = new HashMap<>();
      for (char c : str1.toCharArray()) {
        if (lettresStr1.containsKey(c)) {
          lettresStr1.put(c, lettresStr1.get(c) + 1);
        } else {
          lettresStr1.put(c, 1);
        }
      }
      Map<Character, Integer> lettresStr2 = new HashMap<>();
      for (char c : str2.toCharArray()) {
        if (lettresStr2.containsKey(c)) {
          lettresStr2.put(c, lettresStr2.get(c) + 1);
        } else {
          lettresStr2.put(c, 1);
        }
      }
      return lettresStr1.equals(lettresStr2);
    }
  }

  public static void day3Part1() {
    List<String> data = importFile("./src/main/resources/day3");
    int nbOk = 0;
    for (String ligne : data) {
      List<String> mots = parseligne(ligne);
      boolean estOk = true;
      outerloop: for (int i = 0; i < mots.size(); i++) {
        for (int j = i + 1; j < mots.size(); j++) {
          String ii = mots.get(i);
          String jj = mots.get(j);
          if (ii.equals(jj)) {
            estOk = false;
            break outerloop;
          }
        }
      }
      if (estOk) {
        nbOk++;
      }
    }
    System.out.println(nbOk);
  }

  public static void day3Part2() {
    List<String> data = importFile("./src/main/resources/day3");
    int nbOk = 0;
    for (String ligne : data) {
      List<String> mots = parseligne(ligne);
      boolean estOk = true;
      outerloop: for (int i = 0; i < mots.size(); i++) {
        for (int j = i + 1; j < mots.size(); j++) {
          String ii = mots.get(i);
          String jj = mots.get(j);
          if (sontDesAnagrammes(ii, jj)) {
            estOk = false;
            break outerloop;
          }
        }
      }
      if (estOk) {
        nbOk++;
      }
    }
    System.out.println(nbOk);
  }


}

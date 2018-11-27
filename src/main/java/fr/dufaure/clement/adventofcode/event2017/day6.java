package fr.dufaure.clement.adventofcode.event2017;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class day6 {

  public static void main(String[] args) {
    day6Part1();
    day6Part2();
  }


  public static String importFile(String path) {
    BufferedReader buffer;
    String bancs = "";
    try {
      buffer = new BufferedReader(new FileReader(path));
      String line;
      while ((line = buffer.readLine()) != null) {
        bancs = line;
      }
      buffer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return bancs;
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

  public static List<List<Integer>> combinaisonsObservees = new ArrayList<>();

  public static void day6Part1() {
    String data = importFile("./src/main/resources/day6");
    List<Integer> bancs = parseligne(data);
    int nbOperations = 0;
    do {
      combinaisonsObservees.add(new ArrayList<>(bancs));
      int max = Collections.max(bancs);
      int index = bancs.indexOf(max);
      bancs.set(index, 0);
      for (int i = max; i > 0; i--) {
        index = (index + 1) % bancs.size();
        bancs.set(index, bancs.get(index) + 1);
      }
      nbOperations++;
    } while (!combinaisonsObservees.contains(bancs));
    System.out.println(nbOperations);
    System.out.println(combinaisonsObservees.size() - combinaisonsObservees.indexOf(bancs));
  }

  public static void day6Part2() {

  }


}

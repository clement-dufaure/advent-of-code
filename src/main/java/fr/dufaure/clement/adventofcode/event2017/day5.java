package fr.dufaure.clement.adventofcode.event2017;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class day5 {

  public static void main(String[] args) {
    day4Part1();
    day4Part2();
  }


  public static List<Integer> importFile(String path) {
    BufferedReader buffer;
    List<Integer> operations = new ArrayList<>();
    try {
      buffer = new BufferedReader(new FileReader(path));
      String line;
      while ((line = buffer.readLine()) != null) {
        operations.add(Integer.parseInt(line));
      }
      buffer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return operations;
  }

  public static void day4Part1() {
    List<Integer> data = importFile("./src/main/resources/day4");
    int nbOperations = 0;
    int emplacementCourant = 0;
    int nouvelEmplacementCourant = 0;
    while (emplacementCourant >= 0 && emplacementCourant < data.size()) {
      nouvelEmplacementCourant = emplacementCourant + data.get(emplacementCourant);
      data.set(emplacementCourant, data.get(emplacementCourant) + 1);
      emplacementCourant = nouvelEmplacementCourant;
      nbOperations++;
    }
    System.out.println(nbOperations);
  }

  public static void day4Part2() {
    List<Integer> data = importFile("./src/main/resources/day4");
    int nbOperations = 0;
    int emplacementCourant = 0;
    int nouvelEmplacementCourant = 0;
    while (emplacementCourant >= 0 && emplacementCourant < data.size()) {
      nouvelEmplacementCourant = emplacementCourant + data.get(emplacementCourant);
      if (data.get(emplacementCourant) >= 3) {
        data.set(emplacementCourant, data.get(emplacementCourant) - 1);
      } else {
        data.set(emplacementCourant, data.get(emplacementCourant) + 1);
      }
      emplacementCourant = nouvelEmplacementCourant;
      nbOperations++;
    }
    System.out.println(nbOperations);
  }


}

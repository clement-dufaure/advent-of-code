package fr.dufaure.clement.adventofcode.event2019;

import java.util.ArrayList;
import java.util.List;
import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day16 {

  public static void main(String[] args) {
    long start1 = System.currentTimeMillis();
    part1();
    System.out.println("Execution part 1 en " + (System.currentTimeMillis() - start1) + " ms");
    long start2 = System.currentTimeMillis();
    part2();
    System.out.println("Execution part 2 en " + (System.currentTimeMillis() - start2) + " ms");
  }

  static List<List<Integer>> patterns = new ArrayList<>();

  static void part1() {

    String input = ImportUtils.getString("./src/main/resources/2019/day16");
    List<Integer> liste = new ArrayList<>();
    for (char c : input.toCharArray()) {
      liste.add(Character.getNumericValue(c));
    }

    // generates patterns
    for (int i = 0; i < liste.size(); i++) {
      List<Integer> base = new ArrayList<>(liste.size());
      for (int j = 0; j < i; j++) {
        base.add(0);
      }
      while (base.size() < liste.size()) {
        for (int j = 0; j < i + 1; j++) {
          base.add(1);
        }
        for (int j = 0; j < i + 1; j++) {
          base.add(0);
        }
        for (int j = 0; j < i + 1; j++) {
          base.add(-1);
        }
        for (int j = 0; j < i + 1; j++) {
          base.add(0);
        }
      }
      patterns.add(base);
    }

    for (int i = 0; i < 100; i++) {
      List<Integer> newStep = new ArrayList<>(liste.size());
      for (int j = 0; j < liste.size(); j++) {
        int newValue = 0;
        for (int k = 0; k < liste.size(); k++) {
          newValue += liste.get(k) * patterns.get(j).get(k);
        }
        String newValueStr = String.valueOf(newValue);
        newValueStr = newValueStr.substring(newValueStr.length() - 1);
        newStep.add(Integer.parseInt(newValueStr));
      }
      liste = newStep;

    }
    for (int i : liste.subList(0, 8)) {
      System.out.print(i);
    }
    System.out.println();
  }

  static void part2() {

    String input = ImportUtils.getString("./src/main/resources/2019/day16");
    int offset = Integer.parseInt(input.substring(0, 7));

    List<Integer> listeInput = new ArrayList<>();
    for (char c : input.toCharArray()) {
      listeInput.add(Character.getNumericValue(c));
    }

    List<Integer> liste = new ArrayList<>(listeInput.size() * 10000);
    for (int i = 0; i < 10000; i++) {
      for (int j : listeInput) {
        liste.add(j);
      }
    }
    int tailleUtilise = liste.size() - offset;
    // we dont care before offset (factor 0)
    liste = liste.subList(offset, liste.size());
    System.out.println(liste.size());
    // list size < offset => only factor 1 after

    for (int i = 0; i < 100; i++) {
      int newValue = 0;
      for (int j = liste.size() - 1; j >= 0; j--) {
        newValue += liste.get(j);
        newValue %= 10;
        liste.set(j, newValue);
      }
    }

    // list begins at offset
    for (int i : liste.subList(0, 0 + 8)) {
      System.out.print(i);
    }
    System.out.println();
  }

  static int getPattern(int step, int emplacement) {
    // 000 step-1 fois 111 step fois 000 step fois -1-1-1 step fois 000 step
    // fois etc...
    // [0]00011110000-1-1-1-1000011110000
    // congruence modulo step
    int modulo = ((emplacement % (step * 4)) + 1) % (step * 4);
    if (modulo >= 0 && modulo < step) {
      return 0;
    }
    if (modulo >= step && modulo < step * 2) {
      return 1;
    }
    if (modulo >= step * 2 && modulo < step * 3) {
      return 0;
    }
    if (modulo >= step * 3 && modulo < step * 4) {
      return -1;
    }
    System.err.println("erreur");
    throw new UnsupportedOperationException();
  }

}

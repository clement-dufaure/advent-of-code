package fr.dufaure.clement.adventofcode.event2018;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day2 {

  public static void main(String[] args) {
    long start1 = System.currentTimeMillis();
    part1();
    System.out.println("Execution part 1 en " + (System.currentTimeMillis() - start1) + " ms");
    long start2 = System.currentTimeMillis();
    part2();
    System.out.println("Execution part 2 en " + (System.currentTimeMillis() - start2) + " ms");
  }

  public static void part1() {
    List<String> data = ImportUtils.getListStringUnParLigne("./src/main/resources/2018/day2");
    int nbLignesAvecLettreDouble = 0;
    int nbLignesAvecLettreTriple = 0;
    for (String reference : data) {
      Map<Character, Integer> comptageDesLettres = new HashMap<>();
      for (int i = 0; i < reference.length(); i++) {
        char lettre = reference.charAt(i);
        if (comptageDesLettres.containsKey(lettre)) {
          comptageDesLettres.put(lettre, comptageDesLettres.get(lettre) + 1);
        } else {
          comptageDesLettres.put(lettre, 1);
        }
      }
      if (comptageDesLettres.values().contains(2)) {
        nbLignesAvecLettreDouble++;
      }
      if (comptageDesLettres.values().contains(3)) {
        nbLignesAvecLettreTriple++;
      }
    }
    System.out.println(nbLignesAvecLettreDouble * nbLignesAvecLettreTriple);
  }

  public static void part2() {
    List<String> data = ImportUtils.getListStringUnParLigne("./src/main/resources/2018/day2");
    bouclePremierString: for (int i = 0; i < data.size(); i++) {
      for (int j = i; j < data.size(); j++) {
        int nbLettresDifferentes = 0;
        int emplacementDeLaLettreDifferente = 0;
        for (int k = 0; k < data.get(i).length(); k++) {
          if (!(data.get(i).charAt(k) == data.get(j).charAt(k))) {
            emplacementDeLaLettreDifferente = k;
            nbLettresDifferentes++;
            if (nbLettresDifferentes >= 2) {
              break;
            }
          }
        }
        if (nbLettresDifferentes == 1) {
          System.out.println(data.get(i).substring(0, emplacementDeLaLettreDifferente)
              + data.get(i).substring(emplacementDeLaLettreDifferente + 1));
          break bouclePremierString;
        }
      }
    }
  }

}

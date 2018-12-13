package fr.dufaure.clement.adventofcode.event2018;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day12 {
  public static void main(String[] args) {
    long start1 = System.currentTimeMillis();
  //  for (int i = 1; i < 1000; i++) {
      part1(20);
   // }

    System.out.println("Execution part 1 en " + (System.currentTimeMillis() - start1) + " ms");
    System.out.println(1168L + 42L * 50000000000L);
  }

  static Map<String, String> combinaisons = new HashMap<>();

  public static void parse(String combinaison) {
    Matcher matcher = Pattern.compile("([.#]{5}) => ([.#])").matcher(combinaison);
    matcher.find();
    combinaisons.put(matcher.group(1), matcher.group(2));
  }

  public static String nouvelEtat(String initialState) {
    String initialStateModifie = "...." + initialState + "....";
    StringBuffer buff = new StringBuffer();
    for (int i = 2; i < initialStateModifie.length() - 2; i++) {
      String environnement = initialStateModifie.substring(i - 2, i + 3);
      if (combinaisons.containsKey(environnement)) {
        buff.append(combinaisons.get(environnement));
      } else {
        buff.append('.');
      }
    }
    return buff.toString();
  }

  public static void part1(int nbGeneration) {
    List<String> data = ImportUtils.getListStringUnParLigne("./src/main/resources/2018/day12");
    String initialState = data.remove(0).replace("initial state: ", "");
    data.remove(0);
    data.forEach(day12::parse);
    for (int i = 0; i < nbGeneration; i++) {
      initialState = nouvelEtat(initialState);
    }

    int total = 0;
    for (int i = 0; i < initialState.length(); i++) {
      if (initialState.charAt(i) == '#') {
        // decalage de 2* nombre de genration selon la construction
        total += i - nbGeneration * 2;
      }
    }
    int d = 1168 + 42 * nbGeneration;
    System.out.println(nbGeneration + ";" + total + " : " + d);
  }

  public static void part2() {

  }


  public static class Star {
    int x;
    int y;
    int velocityX;
    int velocityY;
  }

}

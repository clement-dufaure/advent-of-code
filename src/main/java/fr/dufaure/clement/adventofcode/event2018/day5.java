package fr.dufaure.clement.adventofcode.event2018;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day5 {

  public static void main(String[] args) {
    part1();
    part2();
  }

  public static boolean sontCaracteresOpposes(char c1, char c2) {
    if (c1 == c2) {
      return false;
    } else if (c1 == Character.toUpperCase(c2) || c2 == Character.toUpperCase(c1)) {
      return true;
    } else
      return false;
  }

  public static String polymereApresReactions(String polymere) {
    StringBuffer buff = new StringBuffer();
    int i = 0;
    while (i < polymere.length()) {
      if (i < polymere.length() - 1
          && sontCaracteresOpposes(polymere.charAt(i), polymere.charAt(i + 1))) {
        i += 2;
      } else {
        buff.append(polymere.charAt(i));
        i++;
      }
    }
    return buff.toString();
  }

  public static String fullreactOfPolymer(String polymere) {
    String nouveauPolymere = polymereApresReactions(polymere);
    while (polymere.length() != nouveauPolymere.length()) {
      polymere = nouveauPolymere;
      nouveauPolymere = polymereApresReactions(polymere);
    }
    return nouveauPolymere;
  }

  public static String polymere = ImportUtils.getString("./src/main/resources/2018/day5");

  public static void part1() {
    System.out.println(fullreactOfPolymer(polymere).length());
  }

  public static char[] alphabetMinuscule = "abcdefghijklmnopqrstuvwxyz".toCharArray();
  public static char[] alphabetMajuscule = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

  public static void part2() {
    int minimumEnCours = polymere.length();
    for (int i = 0; i < alphabetMajuscule.length; i++) {
      String polymereEpure =
          polymere.replaceAll("[" + alphabetMinuscule[i] + alphabetMajuscule[i] + "]", "");
      int longueurReduite = fullreactOfPolymer(polymereEpure).length();
      if (longueurReduite < minimumEnCours) {
        minimumEnCours = longueurReduite;
      }
    }
    System.out.println(minimumEnCours);
  }

}

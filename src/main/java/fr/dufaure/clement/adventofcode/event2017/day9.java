package fr.dufaure.clement.adventofcode.event2017;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day9 {

  public static void main(String[] args) {
    part1();
    part2();
  }


  public static String removeEscapedCharacter(String data) {
    return data.replaceAll("!.", "");
  }

  public static String removeVirgules(String data) {
    return data.replaceAll(",", "");
  }

  public static String removeGarbage(String data) {
    return data.replaceAll("<[^>]*>", "");
  }

  public static int CountInterieurGarbage(String data) {
    int nbGarbage = 0;
    Matcher matcher = Pattern.compile("<([^>]*)>").matcher(data);
    while (matcher.find()) {
      nbGarbage += matcher.group(1).length();
    }
    return nbGarbage;
  }

  public static Group calculerGroup(String groupeString, int niveauEncours) {
    if (groupeString.equals("{}")) {
      return new Group(niveauEncours + 1);
    } else {
      Group g = new Group(niveauEncours + 1);
      String interieur = groupeString.substring(1, groupeString.length() - 1);
      List<String> sousGroupesString = new ArrayList<>();
      int compte = 0;
      StringBuffer sousGroupeString = new StringBuffer();
      for (int i = 0; i < interieur.length(); i++) {
        if (interieur.charAt(i) == '{') {
          compte++;
        } else {
          compte--;
        }

        sousGroupeString.append(interieur.charAt(i));

        // Si on termine un bloc soit nb { == nb }
        if (compte == 0) {
          sousGroupesString.add(sousGroupeString.toString());
          sousGroupeString = new StringBuffer();
        }
      }
      for (String sousGroupeStr : sousGroupesString) {
        g.sousGroupes.add(calculerGroup(sousGroupeStr, niveauEncours + 1));
      }
      return g;
    }
  }

  public static int calculScore(Group g) {
    if (g.sousGroupes.size() == 0) {
      return g.level;
    } else {
      int score = 0;
      for (Group sousGroupe : g.sousGroupes) {
        score += calculScore(sousGroupe);
      }
      return score + g.level;
    }
  }

  public static void part1() {
    String data = ImportUtils.getString("./src/main/resources/2017/day9");
    String strEpure = removeVirgules(removeGarbage((removeEscapedCharacter(data))));
    Group racine = calculerGroup(strEpure, 0);
    System.out.println(calculScore(racine));
  }

  public static void part2() {
    String data = ImportUtils.getString("./src/main/resources/2017/day9");
    System.out.println(CountInterieurGarbage(removeEscapedCharacter(data)));
  }

  public static class Group {
    public Group(int level) {
      this.level = level;
    }

    int level;
    List<Group> sousGroupes = new ArrayList<>();
  }

}

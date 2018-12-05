package fr.dufaure.clement.adventofcode.event2018;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day3 {

  public static void main(String[] args) {
    part1();
    part2();
  }

  public static Claim parseLigne(String ligne) {
    String[] parts = ligne.split("[#@:]");
    Claim c = new Claim();
    c.id = Integer.parseInt(parts[1].trim());
    c.leftOffset = Integer.parseInt(parts[2].trim().split(",")[0]);
    c.topOffset = Integer.parseInt(parts[2].trim().split(",")[1]);
    c.width = Integer.parseInt(parts[3].trim().split("x")[0]);
    c.height = Integer.parseInt(parts[3].trim().split("x")[1]);
    return c;
  }

  // liste de 1000 lignes de 1000 caracteres "."
  public static List<List<Integer>> entrepot = new ArrayList<>();
  public static List<Claim> claims;

  static {
    // Creation de l'entrepot
    for (int i = 0; i < 1000; i++) {
      List<Integer> places = new ArrayList<>();
      for (int j = 0; j < 1000; j++) {
        places.add(0);
      }
      entrepot.add(places);
    }

    claims = ImportUtils.getListStringUnParLigne("./src/main/resources/2018/day3").stream()
        .map(s -> parseLigne(s)).collect(Collectors.toList());

    // Placement des claims
    for (Claim claim : claims) {
      for (int i = claim.topOffset; i < claim.topOffset + claim.height; i++) {
        for (int j = claim.leftOffset; j < claim.leftOffset + claim.width; j++) {
          entrepot.get(i).set(j, entrepot.get(i).get(j) + 1);
        }
      }
    }
  }

  public static void part1() {
    // verification des cases >=2
    int nbCasesDemandesParPlusUnElfe = 0;
    for (int i = 0; i < 1000; i++) {
      for (int j = 0; j < 1000; j++) {
        if (entrepot.get(i).get(j) >= 2) {
          nbCasesDemandesParPlusUnElfe++;
        }
      }
    }
    System.out.println(nbCasesDemandesParPlusUnElfe);
  }

  public static void part2() {
    // Verification des claims non superposees
    loopClaim: for (Claim claim : claims) {
      for (int i = claim.topOffset; i < claim.topOffset + claim.height; i++) {
        for (int j = claim.leftOffset; j < claim.leftOffset + claim.width; j++) {
          if (entrepot.get(i).get(j) != 1) {
            continue loopClaim;
          }
        }
      }
      System.out.println(claim.id);
      break;
    }
  }

  public static class Claim {
    public int id;
    public int leftOffset;
    public int topOffset;
    public int width;
    public int height;
  }

}

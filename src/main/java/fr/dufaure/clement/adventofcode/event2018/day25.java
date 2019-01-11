package fr.dufaure.clement.adventofcode.event2018;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day25 {

  public static void main(String[] args) {
    parseData();
    reunirConstellations();
    System.out.println(constellations.size());
  }

  static void reunirConstellations() {
    while (etoiles.size() > 0) {
      Etoile ref = etoiles.get(0);
      Set<Etoile> constellation = new HashSet<>();
      constellation.add(ref);
      etoiles.remove(ref);
      List<Etoile> listeTravail = new ArrayList<>(etoiles);
      int tailleConstellation;
      while (true) {
        tailleConstellation = constellation.size();
        Set<Etoile> etoilesAAjouter = new HashSet<>();
        for (Etoile e1 : constellation) {
          for (Etoile e2 : listeTravail) {
            if (e1.distanceManhattan(e2) <= 3) {
              etoilesAAjouter.add(e2);
              etoiles.remove(e2);
            }
          }
        }
        constellation.addAll(etoilesAAjouter);
        if (tailleConstellation == constellation.size()) {
          constellations.add(constellation);
          break;
        }
      }
    }
  }

  static List<Etoile> etoiles = new ArrayList<>();
  static List<Set<Etoile>> constellations = new ArrayList<>();

  static class Etoile {
    int x;
    int y;
    int z;
    int t;

    int distanceManhattan(Etoile other) {
      return Math.abs(x - other.x) + Math.abs(y - other.y) + Math.abs(z - other.z)
          + Math.abs(t - other.t);
    }
  }

  static void parseData() {
    List<String> lignes = ImportUtils.getListStringUnParLigne("src/main/resources/2018/day25");
    for (String ligne : lignes) {
      String[] coord = ligne.split(",");
      Etoile e = new Etoile();
      e.x = Integer.parseInt(coord[0]);
      e.y = Integer.parseInt(coord[1]);
      e.z = Integer.parseInt(coord[2]);
      e.t = Integer.parseInt(coord[3]);
      etoiles.add(e);
    }

  }

}

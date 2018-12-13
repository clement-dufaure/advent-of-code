package fr.dufaure.clement.adventofcode.event2018;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day13part2 {
  public static void main(String[] args) {
    long start1 = System.currentTimeMillis();
    part2();
    System.out.println("Execution part 1 en " + (System.currentTimeMillis() - start1) + " ms");
  }

  static List<List<Rail>> reseau;
  static List<Wagonnet> wagonnets = new ArrayList<>();

  static {
    // creation du reseau
    reseau = ImportUtils.getListStringUnParLigne("./src/main/resources/2018/day13").stream()
        .map(day13part2::parseLigne).collect(Collectors.toList());

    List<String> lignes = ImportUtils.getListStringUnParLigne("./src/main/resources/2018/day13");
    for (int i = 0; i < lignes.size(); i++) {
      wagonnets.addAll(rechercheWagonnetsLigne(lignes.get(i), i));
    }
  }

  static List<Rail> parseLigne(String ligne) {
    List<Rail> rails = new ArrayList<>();
    // on s'interesse au reseau :remplacement des wagonnets par des rails
    // correspondants
    ligne = ligne.replaceAll("[<>]", "-");
    ligne = ligne.replaceAll("[v^]", "|");
    for (char c : ligne.toCharArray()) {
      rails.add(Rail.getTypeRailFromChar(c));
    }
    return rails;
  }

  static List<Wagonnet> rechercheWagonnetsLigne(String ligne, int index) {
    List<Wagonnet> wagonnets = new ArrayList<>();
    for (int i = 0; i < ligne.length(); i++) {
      Orientation o = Orientation.getTypeOrientationFromChar(ligne.charAt(i));
      if (o != null) {
        Wagonnet w = new Wagonnet();
        w.x = i;
        w.y = index;
        w.orientation = o;
        wagonnets.add(w);
      }
    }
    return wagonnets;
  }

  // true si collision
  static boolean avancerLesWagonets() {
    Collections.sort(wagonnets);
    List<Wagonnet> listeDeTravail = new ArrayList<>(wagonnets);
    for (Wagonnet w : listeDeTravail) {
      avancerWagonnet(w);
    }
    if (wagonnets.size() == 1) {
      System.out.println("Dernier wagonnet : " + wagonnets.get(0).x + "," + wagonnets.get(0).y);
      return true;
    }
    return false;
  }

  static boolean avancerWagonnet(Wagonnet w) {
    switch (w.orientation) {
      case UP:
        w.y--;
        switch (reseau.get(w.y).get(w.x)) {
          case DIAGONAL:
            w.turnRight();
            break;
          case DIAGONALBACK:
            w.turnleft();
            break;
          case CROISEMENT:
            w.cross();
            break;
          case RIEN:
            System.err.println("ERROR");
            break;
          default:
            break;
        }
        break;
      case DOWN:
        w.y++;
        switch (reseau.get(w.y).get(w.x)) {
          case DIAGONAL:
            w.turnRight();
            break;
          case DIAGONALBACK:
            w.turnleft();
            break;
          case CROISEMENT:
            w.cross();
            break;
          case RIEN:
            System.err.println("ERROR");
            break;
          default:
            break;
        }
        break;
      case LEFT:
        w.x--;
        switch (reseau.get(w.y).get(w.x)) {
          case DIAGONAL:
            w.turnleft();
            break;
          case DIAGONALBACK:
            w.turnRight();
            break;
          case CROISEMENT:
            w.cross();
            break;
          case RIEN:
            System.err.println("ERROR");
            break;
          default:
            break;
        }
        break;
      case RIGHT:
        w.x++;
        switch (reseau.get(w.y).get(w.x)) {
          case DIAGONAL:
            w.turnleft();
            break;
          case DIAGONALBACK:
            w.turnRight();
            break;
          case CROISEMENT:
            w.cross();
            break;
          case RIEN:
            System.err.println("ERROR");
            break;
          default:
            break;
        }
        break;
      default:
        break;
    }
    if (wagonsEnCollision()) {
      System.out.println("Collision : " + xEnCollision + "," + yEnCollision);
      // remove wagon en colisions
      List<Wagonnet> wagonsEnCollision = new ArrayList<>();
      for (Wagonnet wagon : wagonnets) {
        if ((wagon.x == xEnCollision) && (wagon.y == yEnCollision)) {
          wagonsEnCollision.add(wagon);
        }
      }
      if (wagonsEnCollision.size() != 2) {
        System.err.println("ERROR : " + wagonsEnCollision.size());
      } else {
        wagonnets.remove(wagonsEnCollision.get(0));
        wagonnets.remove(wagonsEnCollision.get(1));
      }
    }
    return false;
  }

  public static boolean wagonsEnCollision() {
    for (int i = 0; i < wagonnets.size(); i++) {
      for (int j = i + 1; j < wagonnets.size(); j++) {
        if (wagonnets.get(i).compareTo(wagonnets.get(j)) == 0) {
          xEnCollision = wagonnets.get(i).x;
          yEnCollision = wagonnets.get(i).y;
          return true;
        }
      }
    }
    return false;
  }

  static int xEnCollision;
  static int yEnCollision;

  static void part2() {
    while (!avancerLesWagonets()) {
    }
  }

  public static enum Rail {
    VERTICAL('|'), HORIZONTAL('-'), DIAGONAL('/'), DIAGONALBACK('\\'), CROISEMENT('+'), RIEN(' ');

    private char charactere;

    private Rail(char c) {
      charactere = c;
    }

    public static Rail getTypeRailFromChar(char c) {
      for (Rail r : Rail.values()) {
        if (r.charactere == c) {
          return r;
        }
      }
      System.err.println("problem");
      return null;
    }
  }

  public static enum Orientation {
    UP('^'), DOWN('v'), LEFT('<'), RIGHT('>'), STRAIGHT('0');

    private char charactere;

    private Orientation(char c) {
      charactere = c;
    }

    public static Orientation getTypeOrientationFromChar(char c) {
      for (Orientation o : Orientation.values()) {
        if (o.charactere == c) {
          return o;
        }
      }
      return null;
    }
  }

  public static class Wagonnet implements Comparable<Wagonnet> {
    static int compteur = 0;
    int id;
    int x;
    int y;
    Orientation orientation;
    Orientation nextCross = Orientation.LEFT;

    public Wagonnet() {
      id = compteur++;
    }

    public void cross() {
      switch (nextCross) {
        case LEFT:
          this.turnleft();
          nextCross = Orientation.STRAIGHT;
          break;
        case STRAIGHT:
          // pas de changement d'orientation
          nextCross = Orientation.RIGHT;
          break;
        case RIGHT:
          this.turnRight();
          nextCross = Orientation.LEFT;
          break;
        default:
          break;
      }
    }

    public void turnleft() {
      switch (orientation) {
        case UP:
          orientation = Orientation.LEFT;
          break;
        case DOWN:
          orientation = Orientation.RIGHT;
          break;
        case LEFT:
          orientation = Orientation.DOWN;
          break;
        case RIGHT:
          orientation = Orientation.UP;
          break;
        default:
          break;
      }
    }

    public void turnRight() {
      switch (orientation) {
        case UP:
          orientation = Orientation.RIGHT;
          break;
        case DOWN:
          orientation = Orientation.LEFT;
          break;
        case LEFT:
          orientation = Orientation.UP;
          break;
        case RIGHT:
          orientation = Orientation.DOWN;
          break;
        default:
          break;
      }
    }

    @Override
    public int compareTo(Wagonnet o) {
      if (o.y > y) {
        return -11;
      } else if (o.y < y) {
        return 1;
      } else {
        if (o.x > x) {
          return -11;
        } else if (o.x < x) {
          return 1;
        }
        return 0;
      }
    }
  }

}

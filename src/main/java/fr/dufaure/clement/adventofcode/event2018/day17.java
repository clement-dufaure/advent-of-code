package fr.dufaure.clement.adventofcode.event2018;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day17 {

  public static void main(String[] ag0) {
    long start1 = System.currentTimeMillis();
    // part1
    Case source = creerGrilleEtGetSource();
    System.out.println(faireAvancerLeau(source, Direction.UP));
    afficherGrille();
    System.out.println(grille.stream().flatMap(List::stream).filter(c -> !c.estAvantPremierX)
        .filter(c -> c.etat == Etat.HUMIDE).count());
    // 89917 too high

    // part2
    System.out.println(grille.stream().flatMap(List::stream).filter(c -> !c.estAvantPremierX)
        .filter(c -> vaRester(c)).count());
    // System.out.println(grille.stream().flatMap(List::stream).filter(c -> !c.estAvantPremierX)
    // .filter(c -> vaRester(c)).collect(Collectors.toList()));
    System.out.println("Execution part 1 en " + (System.currentTimeMillis() - start1) + " ms");
  }

  static List<List<Case>> grille = new ArrayList<>();

  static void afficherGrille() {
    for (List<Case> ligne : grille) {
      for (int j = xMin - 1; j <= xMax + 1; j++) {
        if (ligne.get(j).type == Type.ARGILE) {
          System.out.print("#");
        } else {
          if (ligne.get(j).etat == Etat.SEC) {
            System.out.print(".");
          } else {
            System.out.print("~");
          }
        }
      }
      System.out.println();
    }
  }

  static int yMin;
  static int yMax;
  static int xMin;
  static int xMax;

  static Case creerGrilleEtGetSource() {
    List<String> input = ImportUtils.getListStringUnParLigne("./src/main/resources/2018/day17");
    for (String ligne : input) {
      TraceArgile trace = new TraceArgile();
      Matcher matcher =
          Pattern.compile("([xy])=([0-9]*), ([xy])=([0-9]*)\\.\\.([0-9]*)").matcher(ligne);
      matcher.find();
      if (matcher.group(1).equals("x")) {
        trace.xStart = Integer.parseInt(matcher.group(2));
        trace.xFin = trace.xStart;
        trace.yStart = Integer.parseInt(matcher.group(4));
        trace.yFin = Integer.parseInt(matcher.group(5));
      } else {
        trace.yStart = Integer.parseInt(matcher.group(2));
        trace.yFin = trace.yStart;
        trace.xStart = Integer.parseInt(matcher.group(4));
        trace.xFin = Integer.parseInt(matcher.group(5));
      }
      traces.add(trace);
    }

    // max et min pour taille grille
    yMin = traces.stream().mapToInt(t -> t.yStart).min().getAsInt();
    yMax = traces.stream().mapToInt(t -> t.yFin).max().getAsInt();
    xMin = traces.stream().mapToInt(t -> t.xStart).min().getAsInt();
    xMax = traces.stream().mapToInt(t -> t.xFin).max().getAsInt();

    for (int i = 0; i < yMax + 1; i++) {
      List<Case> ligne = new ArrayList<>();
      for (int j = 0; j <= xMax + 1; j++) {
        Case c = new Case();
        if (i < yMin) {
          c.estAvantPremierX = true;
        }
        c.x = j;
        c.y = i;
        c.type = Type.SABLE;
        c.etat = Etat.SEC;
        ligne.add(c);
      }
      grille.add(ligne);
    }

    for (TraceArgile trace : traces) {
      for (int i = trace.yStart; i <= trace.yFin; i++) {
        for (int j = trace.xStart; j <= trace.xFin; j++) {
          grille.get(i).get(j).type = Type.ARGILE;
        }
      }
    }

    for (int i = 0; i <= yMax; i++) {
      for (int j = 0; j <= xMax + 1; j++) {
        Case laCase = grille.get(i).get(j);
        if (j > 0) {
          laCase.caseLeft = grille.get(i).get(j - 1);
        }
        if (j <= xMax) {
          laCase.caseRight = grille.get(i).get(j + 1);
        }
        if (i < yMax) {
          laCase.caseDown = grille.get(i + 1).get(j);
        }
      }
    }

    return grille.get(0).get(500);
  }

  static List<TraceArgile> traces = new ArrayList<>();

  // retourne vrai si l'eau s'est ecoule, faux si l'on aboutit a un coin
  static boolean faireAvancerLeau(Case maCase, Direction douLonVient) {
    // afficherGrille();
    System.out.println(maCase);
    boolean vaSecouler = false;
    if (maCase.etat == Etat.HUMIDE) {
      throw new UnsupportedOperationException();
    }
    maCase.etat = Etat.HUMIDE;
    if (maCase.caseDown == null) {
      // arrivee en bas de tableau
      vaSecouler = true;
    } else if (maCase.caseDown.type == Type.SABLE && maCase.caseDown.etat == Etat.SEC) {
      if (faireAvancerLeau(maCase.caseDown, Direction.UP)) {
        vaSecouler = true;
      } else {
        if (douLonVient == Direction.UP && maCase.caseLeft != null
            && maCase.caseLeft.type == Type.SABLE && maCase.caseLeft.etat == Etat.SEC
            && maCase.caseRight != null && maCase.caseRight.type == Type.SABLE
            && maCase.caseRight.etat == Etat.SEC) {
          boolean right = faireAvancerLeau(maCase.caseRight, Direction.LEFT);
          boolean left = faireAvancerLeau(maCase.caseLeft, Direction.RIGHT);
          vaSecouler = right || left;
        } else if ((douLonVient == Direction.RIGHT || douLonVient == Direction.UP)
            && maCase.caseLeft != null && maCase.caseLeft.type == Type.SABLE
            && maCase.caseLeft.etat == Etat.SEC) {
          vaSecouler = faireAvancerLeau(maCase.caseLeft, Direction.RIGHT);
        } else if ((douLonVient == Direction.LEFT || douLonVient == Direction.UP)
            && maCase.caseRight != null && maCase.caseRight.type == Type.SABLE
            && maCase.caseRight.etat == Etat.SEC) {
          vaSecouler = faireAvancerLeau(maCase.caseRight, Direction.LEFT);
        } else {
          vaSecouler = false;
        }
      }
    } else if (maCase.caseDown.type == Type.SABLE && maCase.caseDown.etat == Etat.HUMIDE
        && maCase.caseDown.aPuSecouler) {
      vaSecouler = true;
    } else /*
            * if(maCase.caseDown.type == Type.SABLE && maCase.caseDown.etat == Etat.HUMIDE &&
            * !maCase.aPuSecouler) ou argile
            */
    {
      if (douLonVient == Direction.UP && maCase.caseLeft != null
          && maCase.caseLeft.type == Type.SABLE && maCase.caseLeft.etat == Etat.SEC
          && maCase.caseRight != null && maCase.caseRight.type == Type.SABLE
          && maCase.caseRight.etat == Etat.SEC) {
        boolean right = faireAvancerLeau(maCase.caseRight, Direction.LEFT);
        boolean left = faireAvancerLeau(maCase.caseLeft, Direction.RIGHT);
        vaSecouler = right || left;
      } else if ((douLonVient == Direction.RIGHT || douLonVient == Direction.UP)
          && maCase.caseLeft != null && maCase.caseLeft.type == Type.SABLE
          && maCase.caseLeft.etat == Etat.SEC) {
        vaSecouler = faireAvancerLeau(maCase.caseLeft, Direction.RIGHT);
      } else if ((douLonVient == Direction.LEFT || douLonVient == Direction.UP)
          && maCase.caseRight != null && maCase.caseRight.type == Type.SABLE
          && maCase.caseRight.etat == Etat.SEC) {
        vaSecouler = faireAvancerLeau(maCase.caseRight, Direction.LEFT);
      } else {
        vaSecouler = false;
      }
    }
    if (vaSecouler) {
      maCase.aPuSecouler = true;;
      maCase.transmettreInfoOk();
    }

    return vaSecouler;
  }


  static boolean vaRester(Case c) {
    if (c.type == Type.SABLE && c.etat == Etat.HUMIDE) {
      if (c.caseLeft != null && c.caseLeft.type == Type.ARGILE && c.caseRight != null
          && c.caseRight.type == Type.ARGILE) {
        return true;
      } else if (c.caseLeft != null && c.caseLeft.type == Type.ARGILE && c.caseRight != null
          && c.caseRight.type == Type.SABLE && c.caseRight.etat == Etat.HUMIDE) {
        return vaRester(c.caseRight, Direction.LEFT);
      } else if (c.caseRight != null && c.caseRight.type == Type.ARGILE && c.caseLeft != null
          && c.caseLeft.type == Type.SABLE && c.caseLeft.etat == Etat.HUMIDE) {
        return vaRester(c.caseLeft, Direction.RIGHT);
      } else if (c.caseRight != null && c.caseRight.type == Type.SABLE
          && c.caseRight.etat == Etat.HUMIDE && c.caseLeft != null && c.caseLeft.type == Type.SABLE
          && c.caseLeft.etat == Etat.HUMIDE) {
        return vaRester(c.caseLeft, Direction.RIGHT) && vaRester(c.caseRight, Direction.LEFT);
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  static boolean vaRester(Case c, Direction doulonvient) {
    if (doulonvient == Direction.RIGHT) {
      if (c.caseLeft != null && c.caseLeft.type == Type.ARGILE) {
        return true;
      } else if (c.caseLeft != null && c.caseLeft.type == Type.SABLE
          && c.caseLeft.etat == Etat.HUMIDE) {
        return vaRester(c.caseLeft, Direction.RIGHT);
      } else {
        return false;
      }
    } else {
      if (c.caseRight != null && c.caseRight.type == Type.ARGILE) {
        return true;
      } else if (c.caseRight != null && c.caseRight.type == Type.SABLE
          && c.caseRight.etat == Etat.HUMIDE) {
        return vaRester(c.caseRight, Direction.LEFT);
      } else {
        return false;
      }
    }
  }

  static enum Direction {
    UP, LEFT, RIGHT;
  }

  static class TraceArgile {
    int xStart;
    int xFin;
    int yStart;
    int yFin;
  }

  static enum Type {
    SABLE, ARGILE;
  }

  static enum Etat {
    SEC, HUMIDE;
  }

  static class Case {
    boolean estAvantPremierX;

    boolean aPuSecouler = false;

    int x;
    int y;

    Type type;
    Etat etat;

    Case caseLeft;
    Case caseRight;
    Case caseDown;

    void transmettreInfoOk() {
      if (this.caseLeft != null && this.caseLeft.type == Type.SABLE
          && this.caseLeft.etat == Etat.HUMIDE && !this.caseLeft.aPuSecouler) {
        this.caseLeft.aPuSecouler = true;
        this.caseLeft.transmettreInfoOk();
      }
      if (this.caseRight != null && this.caseRight.type == Type.SABLE
          && this.caseRight.etat == Etat.HUMIDE && !this.caseRight.aPuSecouler) {
        this.caseRight.aPuSecouler = true;
        this.caseRight.transmettreInfoOk();
      }
    }

    @Override
    public String toString() {
      return "[" + type + etat + " " + x + "," + y + "--"
          + (caseLeft != null ? (caseLeft.x + "," + caseLeft.y) : "") + "--"
          + (caseRight != null ? (caseRight.x + "," + caseRight.y) : "") + "]";
    }
  }

}


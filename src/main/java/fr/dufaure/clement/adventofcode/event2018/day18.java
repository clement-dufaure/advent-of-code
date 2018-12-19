package fr.dufaure.clement.adventofcode.event2018;

import java.util.ArrayList;
import java.util.List;
import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day18 {

  public static void main(String[] ag0) {
    long start1 = System.currentTimeMillis();
    // for (int nbIteration = 0; nbIteration < 1000; nbIteration++) {
    int nbIteration = 10;
    List<List<Case>> grille = creerGrille();
    // afficherGrille(grille);
    for (int i = 0; i < nbIteration; i++) {
      grille = appliquerJeuDeLaVie(grille);
      // afficherGrille(grille);
    }
    long nbArbre = grille.stream().flatMap(List::stream).filter(c -> c.etat == Etat.ARBRE).count();
    long nbScierie =
        grille.stream().flatMap(List::stream).filter(c -> c.etat == Etat.SCIERIE).count();

    System.out.println(nbIteration + ";" + nbArbre + ";" + nbScierie + ";" + nbArbre * nbScierie);
    // }
    System.out.println("Execution part 1 en " + (System.currentTimeMillis() - start1) + " ms");

    System.out.println(1000000000 % 28);

    // A partir d'un certain rang
    // selon la congruence modulo 28 :
    // 211236 0
    // 213918 1
    // 213710 2
    // 211904 3
    // 210848 4
    // 212058 5
    // 206382 6
    // 200448 7
    // 195014 8
    // 189954 9
    // 183505 10
    // 172983 11
    // 163897 12
    // 161175 13
    // 151122 14
    // 151497 15
    // 151242 16
    // 153360 17
    // 156020 18
    // 162876 19
    // 169234 20
    // 175423 21
    // 183000 22
    // 190030 23
    // 194934 24
    // 197999 25
    // 204408 26
    // 208010 27


  }

  static void afficherGrille(List<List<Case>> grille) {
    for (List<Case> ligne : grille) {
      for (Case c : ligne) {
        switch (c.etat) {
          case VIDE:
            System.out.print(".");
            break;
          case ARBRE:
            System.out.print("|");
            break;
          case SCIERIE:
            System.out.print("#");
            break;
          default:
            break;
        }
      }
      System.out.println();
    }
    System.out.println();
  }

  static List<List<Case>> appliquerJeuDeLaVie(List<List<Case>> grille) {
    for (List<Case> liste : grille) {
      for (Case c : liste) {
        if (c.etat == Etat.VIDE) {
          if (c.casesAdjacentes.stream().filter(ca -> ca.etat == Etat.ARBRE).count() >= 3) {
            c.nouvelEtat = Etat.ARBRE;
          }
        }
        if (c.etat == Etat.ARBRE) {
          if (c.casesAdjacentes.stream().filter(ca -> ca.etat == Etat.SCIERIE).count() >= 3) {
            c.nouvelEtat = Etat.SCIERIE;
          }
        }
        if (c.etat == Etat.SCIERIE) {
          if (!(c.casesAdjacentes.stream().filter(ca -> ca.etat == Etat.SCIERIE).count() > 0
              && c.casesAdjacentes.stream().filter(ca -> ca.etat == Etat.ARBRE).count() > 0)) {
            c.nouvelEtat = Etat.VIDE;
          }
        }
      }
    }

    for (List<Case> liste : grille) {
      for (Case c : liste) {
        if (c.nouvelEtat != null) {
          c.etat = c.nouvelEtat;
          c.nouvelEtat = null;
        }
      }
    }

    return grille;
  }

  static List<List<Case>> creerGrille() {
    List<String> input = ImportUtils.getListStringUnParLigne("./src/main/resources/2018/day18");
    List<List<Case>> grille = new ArrayList<>();

    for (String ligneStr : input) {
      List<Case> ligne = new ArrayList<>();
      for (char ch : ligneStr.toCharArray()) {
        Case c = new Case();
        switch (ch) {
          case '.':
            c.etat = Etat.VIDE;
            break;
          case '|':
            c.etat = Etat.ARBRE;
            break;
          case '#':
            c.etat = Etat.SCIERIE;
            break;
          default:
            break;
        }
        ligne.add(c);
      }
      grille.add(ligne);
    }



    for (int i = 0; i < grille.size(); i++) {
      for (int j = 0; j < grille.get(i).size(); j++) {
        Case laCase = grille.get(i).get(j);
        if (i > 0) {
          laCase.casesAdjacentes.add(grille.get(i - 1).get(j));
        }
        if (i > 0 && j > 0) {
          laCase.casesAdjacentes.add(grille.get(i - 1).get(j - 1));
        }
        if (j > 0) {
          laCase.casesAdjacentes.add(grille.get(i).get(j - 1));
        }
        if (i < grille.size() - 1 && j > 0) {
          laCase.casesAdjacentes.add(grille.get(i + 1).get(j - 1));
        }
        if (i < grille.size() - 1) {
          laCase.casesAdjacentes.add(grille.get(i + 1).get(j));
        }
        if (i < grille.size() - 1 && j < grille.get(i).size() - 1) {
          laCase.casesAdjacentes.add(grille.get(i + 1).get(j + 1));
        }
        if (j < grille.get(i).size() - 1) {
          laCase.casesAdjacentes.add(grille.get(i).get(j + 1));
        }
        if (i > 0 && j < grille.get(i).size() - 1) {
          laCase.casesAdjacentes.add(grille.get(i - 1).get(j + 1));
        }
      }
    }

    return grille;
  }


  static enum Etat {
    VIDE, ARBRE, SCIERIE;
  }

  static class Case {
    Etat etat;
    Etat nouvelEtat;

    List<Case> casesAdjacentes = new ArrayList<>();

  }



}


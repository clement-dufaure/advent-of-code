package fr.dufaure.clement.adventofcode.event2018;

public class day22 {

  public static void main(String[] args) {
    calculGrille();
    System.out.println(calculrisque());

    while (testerAvancees())
    // imprimerAvancees();
    ;
    int tempsMiniTarget = meilleurTemps[X_DESTINATION][Y_DESTINATION];
    System.out.println(tempsMiniTarget);
  }

  // static final int PROFONDEUR = 510;
  // static final int X_DESTINATION = 10;
  // static final int Y_DESTINATION = 10;
  static final int PROFONDEUR = 3558;
  static final int X_DESTINATION = 15;
  static final int Y_DESTINATION = 740;

  static final int OFFSET = 100;

  static final int MAX_X_TEST = X_DESTINATION + OFFSET;
  static final int MAX_Y_TEST = Y_DESTINATION + OFFSET;

  static long[][] index = new long[MAX_X_TEST + 1][MAX_Y_TEST + 1];
  static int[][] coeff = new int[MAX_Y_TEST + OFFSET + 1][MAX_Y_TEST + 1];

  static void calculGrille() {
    for (int x = 0; x <= X_DESTINATION + OFFSET; x++) {
      for (int y = 0; y <= Y_DESTINATION + OFFSET; y++) {
        if ((x == 0 && y == 0) || (x == X_DESTINATION && y == Y_DESTINATION)) {
          index[x][y] = 0L;
        } else if (x == 0) {
          index[x][y] = (y * 48271 + PROFONDEUR) % 20183;
        } else if (y == 0) {
          index[x][y] = (x * 16807 + PROFONDEUR) % 20183;
        } else {
          index[x][y] = (index[x - 1][y] * index[x][y - 1] + PROFONDEUR) % 20183;
        }
        coeff[x][y] = (int) (index[x][y] % 3);
        if (coeff[x][y] == 0) {
          typeCase[x][y] = Type.Rock;
        } else if (coeff[x][y] == 1) {
          typeCase[x][y] = Type.Wet;
        } else {
          typeCase[x][y] = Type.Narrow;
        }
      }
    }
  }

  static int calculrisque() {
    int risque = 0;
    for (int x = 0; x <= X_DESTINATION; x++) {
      for (int y = 0; y <= Y_DESTINATION; y++) {
        risque += coeff[x][y];
      }
    }
    return risque;
  }

  static void imprimerAvancees() {
    for (int y = 0; y <= Y_DESTINATION + OFFSET; y++) {
      for (int x = 0; x <= X_DESTINATION + OFFSET; x++) {
        System.out.print(meilleurTemps[x][y] + " ");
      }
      System.out.println();
    }
  }

  static boolean testerAvancees() {
    boolean hasChanged = false;
    for (int x = 0; x <= X_DESTINATION + OFFSET; x++) {
      for (int y = 0; y <= Y_DESTINATION + OFFSET; y++) {
        if (testerAvancees(x, y)) {
          hasChanged = true;
        }
      }
    }
    return hasChanged;
  }

  static boolean testerAvancees(int x, int y) {
    boolean hasChanged = false;
    if (x > 0) {
      if (testerAvancee(x, y, -1, 0)) {
        hasChanged = true;
      }
    }
    if (y > 0) {
      if (testerAvancee(x, y, 0, -1)) {
        hasChanged = true;
      }
    }
    if (x < MAX_X_TEST) {
      if (testerAvancee(x, y, 1, 0)) {
        hasChanged = true;
      }
    }
    if (y < MAX_Y_TEST) {
      if (testerAvancee(x, y, 0, 1)) {
        hasChanged = true;
      }
    }
    return hasChanged;
  }

  static boolean testerAvancee(int x, int y, int offsetX, int offsetY) {
    int tempsATester = meilleurTemps[x][y];
    Changement changement =
        Changement.calculerChangement(typeCase[x][y], typeCase[x + offsetX][y + offsetY]);

    // Cas du premier changement : s'il est different de rock/narrow on doit sortir
    // la torche
    if (verifierPasEncoreDeChangementDansTableau(dernierChangementAvant[x][y])
        && changement != null) {
      if (changement != Changement.RockNarrow) {
        tempsATester += 7;
      }
    } else if (changement != null
        && !verifierPresenceDansTableau(changement, dernierChangementAvant[x][y])) {
      tempsATester += 7;
    }

    // Cas du dernier changement si ce n'est pas rock/narrow on doit reprendre la
    // torche
    if (x + offsetX == X_DESTINATION && y + offsetY == Y_DESTINATION) {
      if (changement != null) {
        if (changement != Changement.RockNarrow) {
          tempsATester += 7;
        }
      } else if (!verifierPresenceDansTableau(Changement.RockNarrow,
          dernierChangementAvant[x][y])) {
        tempsATester += 7;
      }
    }
    tempsATester++;
    if (meilleurTemps[x + offsetX][y + offsetY] == null
        || meilleurTemps[x + offsetX][y + offsetY].equals(tempsATester)) {
      if (changement != null) {
        verifierEtAjouterNouveauChangement(changement,
            dernierChangementAvant[x + offsetX][y + offsetY]);
      } else {
        verifierEtAjouterNouveauChangement(dernierChangementAvant[x][y],
            dernierChangementAvant[x + offsetX][y + offsetY]);
      }

    }
    if (meilleurTemps[x + offsetX][y + offsetY] == null
        || tempsATester < meilleurTemps[x + offsetX][y + offsetY]) {
      meilleurTemps[x + offsetX][y + offsetY] = tempsATester;
      if (changement != null) {
        Changement[] newArray = {changement, null, null};
        dernierChangementAvant[x + offsetX][y + offsetY] = newArray;
      } else {
        dernierChangementAvant[x + offsetX][y + offsetY] = dernierChangementAvant[x][y];
      }
      return true;
    }
    return false;
  }

  static boolean verifierPasEncoreDeChangementDansTableau(Changement[] array) {
    for (Changement c : array) {
      if (c != null) {
        return false;
      }
    }
    return true;
  }

  static boolean verifierPresenceDansTableau(Changement changement, Changement[] array) {
    for (Changement c : array) {
      if (c == changement) {
        return true;
      }
    }
    return false;
  }

  static void verifierEtAjouterNouveauChangement(Changement changement, Changement[] array) {
    for (Changement c : array) {
      if (c == changement) {
        return;
      }
    }
    if (array[0] == null) {
      array[0] = changement;
    } else if (array[1] == null) {
      array[1] = changement;
    } else if (array[2] == null) {
      array[2] = changement;
    }
  }

  static void verifierEtAjouterNouveauChangement(Changement[] changement, Changement[] array) {
    for (Changement c : changement) {
      verifierEtAjouterNouveauChangement(c, array);
    }
  }

  static Type[][] typeCase = new Type[MAX_X_TEST + 1][MAX_Y_TEST + 1];
  static Integer[][] meilleurTemps = new Integer[MAX_X_TEST + 1][MAX_Y_TEST + 1];
  static Changement[][][] dernierChangementAvant =
      new Changement[MAX_X_TEST + 1][MAX_Y_TEST + 1][3];

  static {
    meilleurTemps[0][0] = 0;
  }

  static enum Type {
    Rock, Wet, Narrow;
  }

  static enum Changement {
    RockWet, RockNarrow, NarrowWet;

    static Changement calculerChangement(Type type1, Type type2) {
      if ((type1 == Type.Rock && type2 == Type.Wet) || (type1 == Type.Wet && type2 == Type.Rock)) {
        return RockWet;
      }
      if ((type1 == Type.Rock && type2 == Type.Narrow)
          || (type1 == Type.Narrow && type2 == Type.Rock)) {
        return RockNarrow;
      }
      if ((type1 == Type.Narrow && type2 == Type.Wet)
          || (type1 == Type.Wet && type2 == Type.Narrow)) {
        return NarrowWet;
      }
      return null;
    }
  }

}

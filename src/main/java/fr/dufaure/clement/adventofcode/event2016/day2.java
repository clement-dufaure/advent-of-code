package fr.dufaure.clement.adventofcode.event2016;

import java.util.List;
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


  // 1 2 3
  // 4 5 6
  // 7 8 9
  public static char[][] cadran = {{'1', '2', '3'}, {'4', '5', '6'}, {'7', '8', '9'}};

  // 0 0 1 0 0
  // 0 2 3 4 0
  // 5 6 7 8 9
  // 0 A B C 0
  // 0 0 D 0 0
  public static char[][] cadran2 = {{'0', '0', '1', '0', '0'}, {'0', '2', '3', '4', '0'},
      {'5', '6', '7', '8', '9'}, {'0', 'A', 'B', 'C', '0'}, {'0', '0', 'D', '0', '0'}};

  public static void part1() {
    List<String> instructions =
        ImportUtils.getListStringUnParLigne("./src/main/resources/2016/day2");
    System.out.println(rechercheCode(instructions, cadran, 1, 1));
  }

  private static String rechercheCode(List<String> instructions, char[][] cadran, int ligneStart,
      int colonneStart) {
    StringBuffer buff = new StringBuffer();
    Emplacement emplacementCourant = new Emplacement(cadran, ligneStart, colonneStart);
    for (String instruction : instructions) {
      for (char c : instruction.toCharArray()) {
        switch (c) {
          case 'U':
            emplacementCourant.up();
            break;
          case 'D':
            emplacementCourant.down();
            break;
          case 'L':
            emplacementCourant.left();
            break;
          case 'R':
            emplacementCourant.right();
            break;
          default:
            break;
        }
      }
      buff.append(cadran[emplacementCourant.ligne][emplacementCourant.colonne]);
    }
    return buff.toString();
  }


  public static void part2() {
    List<String> instructions =
        ImportUtils.getListStringUnParLigne("./src/main/resources/2016/day2");

    System.out.println(rechercheCode(instructions, cadran2, 2, 0));
  }


  public static class Emplacement {
    char[][] cadran;
    int ligne;
    int colonne;

    Emplacement(char[][] cadran, int ligneStart, int colonneStart) {
      this.cadran = cadran;
      this.ligne = ligneStart;
      this.colonne = colonneStart;
    }

    void up() {
      if (ligne > 0 && cadran[ligne - 1][colonne] != '0') {
        ligne--;
      }
    }

    void down() {
      if (ligne < cadran.length - 1 && cadran[ligne + 1][colonne] != '0') {
        ligne++;
      }
    }

    void left() {
      if (colonne > 0 && cadran[ligne][colonne - 1] != '0') {
        colonne--;
      }
    }

    void right() {
      if (colonne < cadran.length - 1 && cadran[ligne][colonne + 1] != '0') {
        colonne++;
      }
    }
  }



}

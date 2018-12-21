package fr.dufaure.clement.adventofcode.event2018;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day20 {

  public static void main(String[] args) {
    long start1 = System.currentTimeMillis();
    part1();
    System.out.println("Execution part 1 en " + (System.currentTimeMillis() - start1) + " ms");
  }

  public static void part1() {
    // construirePlanEtGetStart();
    // System.out.println(carte);
    Room start = construirePlanEtGetStart();
    System.out.println("Step 1 OK");
    System.out.println(chercherDistanceRoomPlusLoin(start));
  }

  static Room[][] carte = new Room[2000][2000];

  static Room construirePlanEtGetStart() {
    // on vire le debut et la fin
    String regex =
        ImportUtils.getString("./src/main/resources/2018/day20").replace("^", "").replace("$", "");

    Room start = new Room();
    int x = 1000;
    int y = 1000;

    carte[x][y] = start;

    avancerPath(x, y, start, regex, 0);

    return start;

  }

  // retourner le nombre de caracter avances
  static void avancerPath(int xInitial, int yInitial, Room roomInitiale, String str, int iInitial) {
   // System.out.println(str);
    Room room = roomInitiale;
    int x = xInitial;
    int y = yInitial;
    int i = iInitial;
    // System.out.println(str);

    while (i < str.length()) {
      // si NWSE : on avance, on verifie si la case existe sinon on la cree, on l'affecte a la
      // prcedente selon la direction
      // si ( on demande la decomposition des path et on execute la methode pour chaque path
      // ) et | sont traites completement dans l'extraction des parenthese

      Room r;
      switch (str.charAt(i)) {
        case 'N':
          y--;
          if (carte[x][y] != null) {
            r = carte[x][y];
          } else {
            r = new Room();
            carte[x][y] = r;
          }
          r.roomSouth = room;
          room.roomNorth = r;
          room = r;
          i++;
          break;
        case 'W':
          x--;
          if (carte[x][y] != null) {
            r = carte[x][y];
          } else {
            r = new Room();
            carte[x][y] = r;
          }
          r.roomEast = room;
          room.roomWest = r;
          room = r;
          i++;
          break;
        case 'E':
          x++;
          if (carte[x][y] != null) {
            r = carte[x][y];
          } else {
            r = new Room();
            carte[x][y] = r;
          }
          r.roomWest = room;
          room.roomEast = r;
          room = r;
          i++;
          break;
        case 'S':
          y++;
          if (carte[x][y] != null) {
            r = carte[x][y];
          } else {
            r = new Room();
            carte[x][y] = r;
          }
          r.roomNorth = room;
          room.roomSouth = r;
          room = r;
          i++;
          break;
        case '(':
          i++;
          List<String> paths = new ArrayList<>();
          int compteur = 1;
          StringBuffer pathEnCours = new StringBuffer();
          while (compteur > 0) {
            switch (str.charAt(i)) {
              case 'N':
                pathEnCours.append("N");
                break;
              case 'W':
                pathEnCours.append("W");
                break;
              case 'E':
                pathEnCours.append("E");
                break;
              case 'S':
                pathEnCours.append("S");
                break;
              case '(':
                compteur++;
                pathEnCours.append("(");
                break;
              case ')':
                compteur--;
                if (compteur != 0) {
                  pathEnCours.append(")");
                }
                break;
              case '|':
                if (compteur == 1) {
                  paths.add(pathEnCours.toString());
                  pathEnCours = new StringBuffer();
                } else {
                  pathEnCours.append("|");
                }
                break;
              default:
                break;
            }
            i++;
          }
          paths.add(pathEnCours.toString());
          for (String path : paths) {
            avancerPath(x, y, room, path + str.substring(i), 0);
          }
          break;

        default:
          break;
      }
    }

  }

  // // on commence apres la premiere parenthese
  // static List<String> extrairePathParenthese(String str, int i) {
  // List<String> paths = new ArrayList<>();
  // int compteur = 1;
  // StringBuffer pathEnCours = new StringBuffer();
  // while (compteur > 0) {
  // switch (str.charAt(i)) {
  // case 'N':
  // pathEnCours.append("N");
  // break;
  // case 'W':
  // pathEnCours.append("W");
  // break;
  // case 'E':
  // pathEnCours.append("E");
  // break;
  // case 'S':
  // pathEnCours.append("S");
  // break;
  // case '(':
  // compteur++;
  // pathEnCours.append("(");
  // break;
  // case ')':
  // compteur--;
  // if (compteur != 0) {
  // pathEnCours.append(")");
  // }
  // break;
  // case '|':
  // if (compteur == 1) {
  // paths.add(pathEnCours.toString());
  // pathEnCours = new StringBuffer();
  // } else {
  // pathEnCours.append("|");
  // }
  // break;
  // default:
  // break;
  // }
  // }
  // paths.add(pathEnCours.toString());
  // return paths;
  // }

  static int chercherDistanceRoomPlusLoin(Room room) {

    int distanceMax = -1;
    int sizeEtapePrecedente = 0;
    Set<Room> ensembleRoomsAccessibles = new HashSet<>();
    ensembleRoomsAccessibles.add(room);
    while (ensembleRoomsAccessibles.size() != sizeEtapePrecedente) {
      distanceMax++;

      // potentiel evolution des paths disponibles
      sizeEtapePrecedente = ensembleRoomsAccessibles.size();
      Set<Room> ensembleNouvellesRoomsAccessibles = new HashSet<>();
      for (Room roomLocal : ensembleRoomsAccessibles) {
        ensembleNouvellesRoomsAccessibles.addAll(roomLocal.getRoomsAccessibles());
      }
      ensembleRoomsAccessibles.addAll(ensembleNouvellesRoomsAccessibles);
    }

    return distanceMax;
  }

  static class Room {

    Room roomNorth;
    Room roomEast;
    Room roomWest;
    Room roomSouth;

    List<Room> getRoomsAccessibles() {
      List<Room> rooms = new ArrayList<>();
      if (roomNorth != null) {
        rooms.add(roomNorth);
      }
      if (roomWest != null) {
        rooms.add(roomWest);
      }
      if (roomEast != null) {
        rooms.add(roomEast);
      }
      if (roomSouth != null) {
        rooms.add(roomSouth);
      }
      return rooms;
    }


  }

}

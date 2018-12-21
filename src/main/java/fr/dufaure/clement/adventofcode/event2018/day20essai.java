package fr.dufaure.clement.adventofcode.event2018;

import java.util.HashMap;
import java.util.LinkedList;
import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day20essai {

  public static void main(String[] args) {
    long start1 = System.currentTimeMillis();
    part1();
    System.out.println("Execution part 1 en " + (System.currentTimeMillis() - start1) + " ms");
  }

  public static void part1() {
    String regex =
        ImportUtils.getString("./src/main/resources/2018/day20").replace("^", "").replace("$", "");
    Room roomEnCours = new Room(0, 0);
    HashMap<Room, Integer> distances = new HashMap<>();
    distances.put(roomEnCours, 0);
    LinkedList<Room> croisements = new LinkedList<>();
    for (char c : regex.toCharArray()) {
      switch (c) {
        case '(':
          croisements.add(roomEnCours);
          break;
        case ')':
          croisements.removeLast();
          break;
        case '|':
          roomEnCours = croisements.peekLast();
          break;
        default:
          Room prochaineRoom = roomEnCours.move(c);
          if (!distances.containsKey(prochaineRoom)) {
            distances.put(prochaineRoom, distances.get(roomEnCours) + 1);
          }
          else {
            distances.put(prochaineRoom,
                Math.min(distances.get(roomEnCours) + 1, distances.get(prochaineRoom)));
          }
          roomEnCours = prochaineRoom;
          break;
      }
    }

    int max = distances.entrySet().stream().mapToInt(e -> e.getValue()).max().getAsInt();
    System.out.println(max);

    System.out.println(distances.entrySet().stream().count());
    System.out.println(distances.entrySet().stream().filter(e -> e.getValue() >= 1000).count());
    // 9603 too high
  }


  static class Room {
    Room(int x, int y) {
      this.x = x;
      this.y = y;
    }

    int x;
    int y;

    Room move(char c) {
      switch (c) {
        case 'N':
          return new Room(x, y - 1);
        case 'E':
          return new Room(x - 1, y);
        case 'W':
          return new Room(x + 1, y);
        case 'S':
          return new Room(x, y + 1);
        default:
          return null;
      }
    }
  }
  
  // etrange ; solution a comparer avec https://github.com/darrenmossman/AdventOfCode

}

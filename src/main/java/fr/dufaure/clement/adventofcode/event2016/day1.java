package fr.dufaure.clement.adventofcode.event2016;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day1 {

  public static void main(String[] args) {
    long start1 = System.currentTimeMillis();
    part1();
    System.out.println("Execution part 1 en " + (System.currentTimeMillis() - start1) + " ms");
    long start2 = System.currentTimeMillis();
    part2();
    System.out.println("Execution part 2 en " + (System.currentTimeMillis() - start2) + " ms");
  }

  public static Instruction parse(String instructionString) {
    Instruction i = new Instruction();
    switch (instructionString.charAt(0)) {
      case 'L':
        i.direction = Direction.LEFT;
        break;
      case 'R':
        i.direction = Direction.RIGHT;
        break;
      default:
        break;
    }
    i.distance = Integer.parseInt(instructionString.substring(1));
    return i;
  }

  public static Orientation orientationActuelle = Orientation.UP;

  public static void tourner(Direction direction) {
    switch (direction) {
      case LEFT:
        switch (orientationActuelle) {
          case UP:
            orientationActuelle = Orientation.LEFT;
            break;
          case DOWN:
            orientationActuelle = Orientation.RIGHT;
            break;
          case LEFT:
            orientationActuelle = Orientation.DOWN;
            break;
          case RIGHT:
            orientationActuelle = Orientation.UP;
            break;
          default:
            break;
        }
        break;
      case RIGHT:
        switch (orientationActuelle) {
          case UP:
            orientationActuelle = Orientation.RIGHT;
            break;
          case DOWN:
            orientationActuelle = Orientation.LEFT;
            break;
          case LEFT:
            orientationActuelle = Orientation.UP;
            break;
          case RIGHT:
            orientationActuelle = Orientation.DOWN;
            break;
          default:
            break;
        }
        break;
      default:
        break;
    }
  }

  public static void part1() {
    List<Instruction> instructions =
        Arrays.asList(ImportUtils.getString("./src/main/resources/2016/day1").split(", ")).stream()
            .map(day1::parse).collect(Collectors.toList());
    int x = 0;
    int y = 0;
    for (Instruction instruction : instructions) {
      tourner(instruction.direction);
      switch (orientationActuelle) {
        case UP:
          x += instruction.distance;
          break;
        case DOWN:
          x -= instruction.distance;
          break;
        case LEFT:
          y -= instruction.distance;
          break;
        case RIGHT:
          y += instruction.distance;
          break;
        default:
          break;
      }
    }
    System.out.println(x + y);
  }

  public static Map<Integer, List<Integer>> pointsParcourus = new HashMap<>();
  public static int xActuel = 0;
  public static int yActuel = 0;

  public static boolean avancerCheckRecord(Direction direction, int distance) {
    for (int i = 0; i < distance; i++) {
      switch (orientationActuelle) {
        case UP:
          xActuel++;
          break;
        case DOWN:
          xActuel--;
          break;
        case LEFT:
          yActuel--;
          break;
        case RIGHT:
          yActuel++;
          break;
        default:
          break;
      }
      if (pointsParcourus.containsKey(xActuel)) {
        if (pointsParcourus.get(xActuel).contains(yActuel)) {
          return true;
        }
        pointsParcourus.get(xActuel).add(yActuel);
      } else {
        List<Integer> liste = new ArrayList<>();
        liste.add(yActuel);
        pointsParcourus.put(xActuel, liste);
      }
    }
    return false;
  }

  public static void part2() {
    // RESET
    orientationActuelle = Orientation.UP;
    List<Instruction> instructions =
        Arrays.asList(ImportUtils.getString("./src/main/resources/2016/day1").split(", ")).stream()
            .map(day1::parse).collect(Collectors.toList());
    for (Instruction instruction : instructions) {
      tourner(instruction.direction);
      if (avancerCheckRecord(instruction.direction, instruction.distance)) {
        break;
      }
    }
    System.out.println(xActuel + yActuel);
  }


  public static enum Direction {
    LEFT, RIGHT;
  }


  public static enum Orientation {
    UP, DOWN, LEFT, RIGHT;
  }


  public static class Instruction {
    Direction direction;
    int distance;
  }

}

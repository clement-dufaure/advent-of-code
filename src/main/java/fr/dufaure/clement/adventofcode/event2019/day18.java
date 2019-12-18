package fr.dufaure.clement.adventofcode.event2019;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day18 {

  public static void main(String[] args) {
    long start1 = System.currentTimeMillis();
    part1();
    System.out.println("Execution part 1 en " + (System.currentTimeMillis() - start1) + " ms");
    long start2 = System.currentTimeMillis();
    part2();
    System.out.println("Execution part 2 en " + (System.currentTimeMillis() - start2) + " ms");
  }

  static Map<Coord, Character> map = new HashMap<>();
  static Coord start = null;
  // eventuel "forum" au milieu du laby
  static boolean casesGenantes = false;
  static Map<Character, Tree> keys = new HashMap<>();
  static Map<Character, List<Character>> keysDoorsBloquantes = new HashMap<>();
  static Map<Character, List<Character>> doorDoorsBloquantes = new HashMap<>();
  static Map<Character, Tree> doors = new HashMap<>();
  static Set<Character> doorsUtiles = new HashSet<>();
  static Set<Character> clesObtenues = new HashSet<>();

  static void initialize() {
    List<String> input = ImportUtils.getListStringUnParLigne("./src/main/resources/2019/day18test2");
    for (int i = 0; i < input.size(); i++) {
      for (int j = 0; j < input.get(i).length(); j++) {
        if (input.get(i).charAt(j) != '#') {
          // dont care about walls
          map.put(new Coord(j, i), input.get(i).charAt(j));
        }
      }
    }

    start = map.entrySet().stream().filter(e -> e.getValue() == '@').findFirst().get().getKey();

    // for unicity of paths
    if (map.containsKey(new Coord(start.x - 1, start.y - 1)) && map.containsKey(new Coord(start.x, start.y - 1))
        && map.containsKey(new Coord(start.x + 1, start.y - 1)) && map.containsKey(new Coord(start.x - 1, start.y))
        && map.containsKey(new Coord(start.x + 1, start.y)) && map.containsKey(new Coord(start.x + 1, start.y + 1))
        && map.containsKey(new Coord(start.x, start.y + 1)) && map.containsKey(new Coord(start.x + 1, start.y + 1))) {
      casesGenantes = true;
    }
  }

  static void part1() {
    initialize();
    Tree labyrinth = generateTree(start);
    System.out.println(labyrinth.subTree.size());

    for (Entry<Character, Tree> key : keys.entrySet()) {
      List<Character> doorsBloquantes = new ArrayList<>();
      Tree tree = key.getValue();
      while (tree.parent != null) {
        tree = tree.parent;
        if (tree.value >= 'A' && tree.value <= 'Z') {
          doorsBloquantes.add(tree.value);
          doorsUtiles.add(tree.value);
        }
      }
      keysDoorsBloquantes.put(key.getKey(), doorsBloquantes);
    }

    System.out.println(keysDoorsBloquantes);
    System.out.println(doorDoorsBloquantes);
    // System.out.println(doorsUtiles);
    Coord position = start;

    // while (keys.size() != clesObtenues.size()) {
    List<Character> clesvisibles;
    List<Character> doorsVisibles;
    Tree treeCourant = generateTree(position);
    clesvisibles = keysDoorsBloquantes.entrySet().stream().filter(e -> e.getValue().size() == 0).map(Entry::getKey)
        .collect(Collectors.toList());
    doorsVisibles = doorDoorsBloquantes.entrySet().stream().filter(e -> e.getValue().size() == 0).map(Entry::getKey)
        .collect(Collectors.toList());

    System.out.println(clesvisibles);
    System.out.println(doorsVisibles);
    // }
  }

  static List<Coord> casesAOmmettre = new ArrayList<>();
  static boolean casesAOmmettreChecked = false;

  static Tree generateTree(Coord root) {
    casesAOmmettre = new ArrayList<>();
    casesAOmmettreChecked = false;
    Tree tree = generateBranch(null, root, null);

    for (Entry<Character, Tree> key : keys.entrySet()) {
      List<Character> doorsBloquantes = new ArrayList<>();
      Tree treek = key.getValue();
      while (treek.parent != null) {
        treek = treek.parent;
        if (treek.value >= 'A' && treek.value <= 'Z') {
          doorsBloquantes.add(treek.value);
          // doorsUtiles.add(treek.value);
        }
      }
      keysDoorsBloquantes.put(key.getKey(), doorsBloquantes);
    }

    for (Entry<Character, Tree> door : doors.entrySet()) {
      List<Character> doorsBloquantes = new ArrayList<>();
      Tree treed = door.getValue();
      while (treed.parent != null) {
        treed = treed.parent;
        if (treed.value >= 'A' && treed.value <= 'Z') {
          doorsBloquantes.add(treed.value);
        }
      }
      doorDoorsBloquantes.put(door.getKey(), doorsBloquantes);
    }

    // on oublie les portes pas a ouvrir
    doors = doors.entrySet().stream().filter(e -> doorsUtiles.contains(e.getKey()))
        .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
    return tree;
  }

  static void checkCasesGenantes(Coord coord) {
    if (casesGenantes && !casesAOmmettreChecked) {
      casesAOmmettreChecked = true;
      if (coord.equals(start)) {
        casesAOmmettre.add(new Coord(start.x - 1, start.y));
        casesAOmmettre.add(new Coord(start.x + 1, start.y));
      }
      if (coord.equals(new Coord(start.x - 1, start.y - 1))) {
        casesAOmmettre.add(new Coord(start.x + 1, start.y));
        casesAOmmettre.add(start);
      }
      if (coord.equals(new Coord(start.x + 1, start.y - 1))) {
        casesAOmmettre.add(new Coord(start.x - 1, start.y));
        casesAOmmettre.add(start);
      }
      if (coord.equals(new Coord(start.x - 1, start.y + 1))) {
        casesAOmmettre.add(new Coord(start.x + 1, start.y));
        casesAOmmettre.add(start);
      }
      if (coord.equals(new Coord(start.x + 1, start.y + 1))) {
        casesAOmmettre.add(new Coord(start.x + 1, start.y));
        casesAOmmettre.add(start);
      }
    }
  }

  // construct branch => dont see root side
  static Tree generateBranch(Tree parent, Coord root, Direction from) {
    checkCasesGenantes(root);
    Tree branch = new Tree();
    branch.value = map.get(root);
    branch.parent = parent;
    if (map.get(root) >= 'a' && map.get(root) <= 'z') {
      keys.put(map.get(root), branch);
    }
    if (map.get(root) >= 'A' && map.get(root) <= 'Z') {
      doors.put(map.get(root), branch);
    }
    if (from != Direction.LEFT && map.containsKey(new Coord(root.x - 1, root.y))
        && !casesAOmmettre.contains(new Coord(root.x - 1, root.y))) {
      branch.subTree.add(generateBranch(branch, new Coord(root.x - 1, root.y), Direction.RIGHT));
    }
    if (from != Direction.RIGHT && map.containsKey(new Coord(root.x + 1, root.y))
        && !casesAOmmettre.contains(new Coord(root.x + 1, root.y))) {
      branch.subTree.add(generateBranch(branch, new Coord(root.x + 1, root.y), Direction.LEFT));
    }
    if (from != Direction.UP && map.containsKey(new Coord(root.x, root.y - 1))
        && !casesAOmmettre.contains(new Coord(root.x, root.y - 1))) {
      branch.subTree.add(generateBranch(branch, new Coord(root.x, root.y - 1), Direction.DOWN));
    }
    if (from != Direction.DOWN && map.containsKey(new Coord(root.x, root.y + 1))
        && !casesAOmmettre.contains(new Coord(root.x, root.y + 1))) {
      branch.subTree.add(generateBranch(branch, new Coord(root.x, root.y + 1), Direction.UP));
    }
    return branch;
  }

  static enum Direction {
    UP, DOWN, LEFT, RIGHT;
  }

  static void part2() {

  }

  static class Tree {
    char value = '.';
    Tree parent = null;
    List<Tree> subTree = new ArrayList<>();
  }

  static class Coord {
    int x;
    int y;

    Coord(int x, int y) {
      this.x = x;
      this.y = y;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + x;
      result = prime * result + y;
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      Coord other = (Coord) obj;
      if (x != other.x)
        return false;
      if (y != other.y)
        return false;
      return true;
    }

  }

}

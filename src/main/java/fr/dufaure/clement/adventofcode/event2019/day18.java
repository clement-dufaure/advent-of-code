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

  static int totalKeys = 0;
  static int totalStep = 0;

  // step
  // cles associees a l'arbre pour remonter a la position
  static Map<Character, Tree> keys = new HashMap<>();
  // doors associees a l'arbre pour remonter a la position
  static Map<Character, Tree> doors = new HashMap<>();
  // portes bloquant les cles
  static Map<Character, List<Character>> keysDoorsBloquantes = new HashMap<>();
  // portes bloquant les portes
  static Map<Character, List<Character>> doorDoorsBloquantes = new HashMap<>();
  static List<Character> clesObtenues = new ArrayList<>();
  //

  static void initialize() {
    List<String> input = ImportUtils.getListStringUnParLigne("./src/main/resources/2019/day18");
    for (int i = 0; i < input.size(); i++) {
      for (int j = 0; j < input.get(i).length(); j++) {
        if (input.get(i).charAt(j) != '#') {
          // dont care about walls
          map.put(new Coord(j, i), input.get(i).charAt(j));
        }
        if (input.get(i).charAt(j) >= 'a' && input.get(i).charAt(j) <= 'z') {
          totalKeys++;
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
    generateTree(start);

    Set<Character> doorsUtiles = new HashSet<>();
    for (Entry<Character, Tree> key : keys.entrySet()) {
      // List<Character> doorsBloquantes = new ArrayList<>();
      Tree tree = key.getValue();
      while (tree.parent != null) {
        tree = tree.parent;
        if (tree.value >= 'A' && tree.value <= 'Z') {
          doorsUtiles.add(tree.value);
        }
      }
    }

    // suppression des portes inutiles
    for (Entry<Coord, Character> element : map.entrySet()) {
      if (element.getValue() >= 'A' && element.getValue() <= 'Z' && !doorsUtiles.contains(element.getValue())) {
        element.setValue('.');
      }
    }

    // System.out.println(doorsUtiles);
    Coord position = start;

    // for (int i = 0; i < 10; i++) {
    while (totalKeys != clesObtenues.size()) {
      List<Character> clesvisibles;
      List<Character> doorsVisibles;
      generateTree(position);
      clesvisibles = keysDoorsBloquantes.entrySet().stream().filter(e -> e.getValue().size() == 0).map(Entry::getKey)
          .collect(Collectors.toList());
      doorsVisibles = doorDoorsBloquantes.entrySet().stream().filter(e -> e.getValue().size() == 0).map(Entry::getKey)
          .collect(Collectors.toList());

      System.out.println("ClesVisibles" + clesvisibles);
      System.out.println("DoorsVisibles" + doorsVisibles);

      // si cle en direct avec rien deriere, on va chercher
      List<Character> premiereSelection = clesvisibles.stream().filter(c -> !keys.get(c).hasOtherThings)
          .collect(Collectors.toList());
      System.out.println("premierSelection" + premiereSelection);
      // choix des cles dispo qui ouvrent les portes dispo
      List<Character> clesARecuperer = clesvisibles.stream().filter(c -> doorsVisibles.contains((char) (c - 32)))
          .collect(Collectors.toList());
      System.out.println("ClesARecuperer" + clesARecuperer);
      // On prend la premiere
      Character cleChoisie = null;
      if (premiereSelection.size() > 0) {
        cleChoisie = premiereSelection.get(0);
      } else if (clesARecuperer.size() > 0) {
        cleChoisie = clesARecuperer.get(0);
      } else {
        cleChoisie = clesvisibles.get(0);
      }
      Tree treeKeyARecupere = keys.get(cleChoisie);
      // on oublie la cle et la porte
      map.put(treeKeyARecupere.coord, '.');
      if (doors.containsKey((char) (cleChoisie - 32))) {
        map.put(doors.get((char) (cleChoisie - 32)).coord, '.');
      }
      position = treeKeyARecupere.coord;
      Tree treek = treeKeyARecupere;
      while (treek.parent != null) {
        totalStep++;
        treek = treek.parent;
        if (treek.value >= 'a' && treek.value <= 'z') {
          clesObtenues.add(treek.value);
          // on oublie la cle et la porte
          map.put(treek.coord, '.');
          if (doors.containsKey((char) (treek.value - 32))) {
            map.put(doors.get((char) (treek.value - 32)).coord, '.');
          }
        }
      }
      clesObtenues.add(cleChoisie);
      System.out.println("clesObtenus" + clesObtenues);
    }

    System.out.println(totalStep);
  }

  static List<Coord> casesAOmmettre = new ArrayList<>();
  static boolean casesAOmmettreChecked = false;

  static Tree generateTree(Coord root) {
    // reinit calcul des cases penibles
    casesAOmmettre = new ArrayList<>();
    casesAOmmettreChecked = false;

    // reinit state
    keys = new HashMap<>();
    doors = new HashMap<>();
    keysDoorsBloquantes = new HashMap<>();
    doorDoorsBloquantes = new HashMap<>();

    // calcul arbre
    Tree tree = generateBranch(null, root, null);

    for (Entry<Character, Tree> key : keys.entrySet()) {
      List<Character> doorsBloquantes = new ArrayList<>();
      Tree treek = key.getValue();
      while (treek.parent != null) {
        treek = treek.parent;
        if (treek.value >= 'A' && treek.value <= 'Z') {
          doorsBloquantes.add(treek.value);
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
    // doors = doors.entrySet().stream().filter(e ->
    // doorsUtiles.contains(e.getKey()))
    // .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
    return tree;
  }

  static void checkCasesGenantes(Coord coord) {
    if (casesGenantes && !casesAOmmettreChecked) {
      if (coord.equals(start)) {
        casesAOmmettreChecked = true;
        casesAOmmettre.add(new Coord(start.x - 1, start.y));
        casesAOmmettre.add(new Coord(start.x + 1, start.y));
      }
      if (coord.equals(new Coord(start.x - 1, start.y - 1))) {
        casesAOmmettreChecked = true;
        casesAOmmettre.add(new Coord(start.x + 1, start.y));
        casesAOmmettre.add(start);
      }
      if (coord.equals(new Coord(start.x + 1, start.y - 1))) {
        casesAOmmettreChecked = true;
        casesAOmmettre.add(new Coord(start.x - 1, start.y));
        casesAOmmettre.add(start);
      }
      if (coord.equals(new Coord(start.x - 1, start.y + 1))) {
        casesAOmmettreChecked = true;
        casesAOmmettre.add(new Coord(start.x + 1, start.y));
        casesAOmmettre.add(start);
      }
      if (coord.equals(new Coord(start.x + 1, start.y + 1))) {
        casesAOmmettreChecked = true;
        casesAOmmettre.add(new Coord(start.x + 1, start.y));
        casesAOmmettre.add(start);
      }
    }
  }

  // construct branch => dont see root side
  static Tree generateBranch(Tree parent, Coord root, Direction from) {
    checkCasesGenantes(root);
    Tree branch = new Tree();
    branch.coord = root;
    branch.value = map.get(root);
    branch.parent = parent;
    if (map.get(root) >= 'a' && map.get(root) <= 'z') {
      branch.isKey = true;
      keys.put(map.get(root), branch);
    }
    if (map.get(root) >= 'A' && map.get(root) <= 'Z') {
      branch.isDoor = true;
      doors.put(map.get(root), branch);
    }
    if (from != Direction.LEFT && map.containsKey(new Coord(root.x - 1, root.y))
        && !casesAOmmettre.contains(new Coord(root.x - 1, root.y))) {
      Tree t = generateBranch(branch, new Coord(root.x - 1, root.y), Direction.RIGHT);
      if (t.isKey || t.isDoor || t.hasOtherThings) {
        branch.hasOtherThings = true;
      }
      branch.subTree.add(t);
    }
    if (from != Direction.RIGHT && map.containsKey(new Coord(root.x + 1, root.y))
        && !casesAOmmettre.contains(new Coord(root.x + 1, root.y))) {
      Tree t = generateBranch(branch, new Coord(root.x + 1, root.y), Direction.LEFT);
      if (t.isKey || t.isDoor || t.hasOtherThings) {
        branch.hasOtherThings = true;
      }
      branch.subTree.add(t);
    }
    if (from != Direction.UP && map.containsKey(new Coord(root.x, root.y - 1))
        && !casesAOmmettre.contains(new Coord(root.x, root.y - 1))) {
      Tree t = generateBranch(branch, new Coord(root.x, root.y - 1), Direction.DOWN);
      if (t.isKey || t.isDoor || t.hasOtherThings) {
        branch.hasOtherThings = true;
      }
      branch.subTree.add(t);
    }
    if (from != Direction.DOWN && map.containsKey(new Coord(root.x, root.y + 1))
        && !casesAOmmettre.contains(new Coord(root.x, root.y + 1))) {
      Tree t = generateBranch(branch, new Coord(root.x, root.y + 1), Direction.UP);
      if (t.isKey || t.isDoor || t.hasOtherThings) {
        branch.hasOtherThings = true;
      }
      branch.subTree.add(t);
    }
    return branch;
  }

  static enum Direction {
    UP, DOWN, LEFT, RIGHT;
  }

  static void part2() {

  }

  static class Tree {
    boolean isKey = false;
    boolean isDoor = false;
    boolean hasOtherThings = false;
    Coord coord;
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

    @Override
    public String toString() {
      return "(" + x + "," + y + ")";
    }

  }

}

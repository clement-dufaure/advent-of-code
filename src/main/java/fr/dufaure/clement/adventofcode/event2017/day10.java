package fr.dufaure.clement.adventofcode.event2017;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day10 {

  public static void main(String[] args) {
    part1();
    part2();
  }


  public static List<Integer> reverseSubList(List<Integer> list, int start, int lenght) {
    if (lenght > 1) {
      int indexDeFin = (start + lenght - 1) % list.size();
      List<Integer> listeDeTravail = new ArrayList<>();
      // si la liste a inverser ne fait pas revenir au debut
      if (indexDeFin > start) {
        listeDeTravail.addAll(list.subList(0, start));
        List<Integer> listeAinverser = list.subList(start, indexDeFin + 1);
        Collections.reverse(listeAinverser);
        listeDeTravail.addAll(listeAinverser);
        if (indexDeFin + 1 < list.size()) {
          listeDeTravail.addAll(list.subList(indexDeFin + 1, list.size()));
        }
      } else {
        List<Integer> listeAinverser = list.subList(start, list.size());
        listeAinverser.addAll(list.subList(0, indexDeFin + 1));
        Collections.reverse(listeAinverser);
        listeDeTravail.addAll(
            listeAinverser.subList(listeAinverser.size() - indexDeFin - 1, listeAinverser.size()));
        listeDeTravail.addAll(list.subList(indexDeFin + 1, start));
        listeDeTravail.addAll(listeAinverser.subList(0, listeAinverser.size() - indexDeFin - 1));
      }
      return listeDeTravail;
    } else
      return list;
  }

  public static void part1() {
    List<Integer> longueurs =
        Arrays.asList(ImportUtils.getString("./src/main/resources/2017/day10").split(",")).stream()
            .map(s -> Integer.parseInt(s)).collect(Collectors.toList());
    List<Integer> liste = IntStream.rangeClosed(0, 255).boxed().collect(Collectors.toList());
    int skipSize = 0;
    int currentPosition = 0;
    for (int longueur : longueurs) {
      liste = reverseSubList(liste, currentPosition, longueur);
      currentPosition = (currentPosition + longueur + skipSize) % liste.size();
      skipSize++;
    }
    System.out.println(liste.get(0) * liste.get(1));
  }

  public static void part2() {
    String input = ImportUtils.getString("./src/main/resources/2017/day10");
    List<Integer> longueurs = new ArrayList<>();
    List<Integer> liste = IntStream.rangeClosed(0, 255).boxed().collect(Collectors.toList());

    // Convert ASCII
    for (char c : input.toCharArray()) {
      longueurs.add((int) c);
    }

    // add 17, 31, 73, 47, 23
    longueurs.addAll(Arrays.asList(17, 31, 73, 47, 23));

    // 64 rounds (preserve skip size and currentposition) => sparse hash
    int skipSize = 0;
    int currentPosition = 0;
    for (int i = 0; i < 64; i++) {
      for (int longueur : longueurs) {
        liste = reverseSubList(liste, currentPosition, longueur);
        currentPosition = (currentPosition + longueur + skipSize) % liste.size();
        skipSize++;
      }
    }

    // XOR(each group of 16) => dense hsh
    List<Integer> denseHash = new ArrayList<>();
    for (int i = 0; i < liste.size(); i += 16) {
      denseHash.add(liste.subList(i, i + 16).stream().reduce((a, b) -> a ^ b).get());
    }

    // concat(toHex(each XOR)) => Knot Hash
    StringBuffer knotHash = new StringBuffer();
    for (int i : denseHash) {
      String hex = Integer.toHexString(i);
      if (hex.length() == 1) {
        hex = "0" + hex;
      }
      knotHash.append(hex);
    }

    System.out.println(knotHash.toString());
  }



}

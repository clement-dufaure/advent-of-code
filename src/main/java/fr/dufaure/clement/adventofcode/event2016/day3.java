package fr.dufaure.clement.adventofcode.event2016;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day3 {

  public static void main(String[] args) {
    long start1 = System.currentTimeMillis();
    part1();
    System.out.println("Execution part 1 en " + (System.currentTimeMillis() - start1) + " ms");
    long start2 = System.currentTimeMillis();
    part2();
    System.out.println("Execution part 2 en " + (System.currentTimeMillis() - start2) + " ms");
  }


  public static List<Integer> parse(String ligne) {
    List<Integer> cotes = new ArrayList<>();
    Matcher matcher = Pattern.compile(" *([0-9]+) *([0-9]+) *([0-9]+)").matcher(ligne);
    matcher.find();
    cotes.add(Integer.parseInt(matcher.group(1)));
    cotes.add(Integer.parseInt(matcher.group(2)));
    cotes.add(Integer.parseInt(matcher.group(3)));
    return cotes;
  }

  public static List<List<Integer>> parseByColumn(String lignes) {
    List<List<Integer>> liste = new ArrayList<>();
    List<Integer> cotes1 = new ArrayList<>();
    List<Integer> cotes2 = new ArrayList<>();
    List<Integer> cotes3 = new ArrayList<>();
    Matcher matcher = Pattern.compile(" *([0-9]+) *([0-9]+) *([0-9]+)").matcher(lignes);
    while (matcher.find()) {
      cotes1.add(Integer.parseInt(matcher.group(1)));
      cotes2.add(Integer.parseInt(matcher.group(2)));
      cotes3.add(Integer.parseInt(matcher.group(3)));
      if (cotes1.size() == 3) {
        liste.add(cotes1);
        liste.add(cotes2);
        liste.add(cotes3);
        cotes1 = new ArrayList<>();
        cotes2 = new ArrayList<>();
        cotes3 = new ArrayList<>();
      }
    }
    return liste;
  }

  public static Boolean estUnTriangle(List<Integer> cotes) {
    if (cotes.get(0) >= cotes.get(1) + cotes.get(2)) {
      return false;
    }
    if (cotes.get(1) >= cotes.get(0) + cotes.get(2)) {
      return false;
    }
    if (cotes.get(2) >= cotes.get(1) + cotes.get(0)) {
      return false;
    }
    return true;
  }

  public static void part1() {
    System.out.println(ImportUtils.getListStringUnParLigne("./src/main/resources/2016/day3")
        .stream().map(day3::parse).filter(day3::estUnTriangle).count());
  }


  public static void part2() {
    System.out.println(parseByColumn(ImportUtils.getString("./src/main/resources/2016/day3"))
        .stream().filter(day3::estUnTriangle).count());

  }



}

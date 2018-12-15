package fr.dufaure.clement.adventofcode.event2016;

import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day4 {

  public static void main(String[] args) {
    long start1 = System.currentTimeMillis();
    part1();
    System.out.println("Execution part 1 en " + (System.currentTimeMillis() - start1) + " ms");
    long start2 = System.currentTimeMillis();
    part2();
    System.out.println("Execution part 2 en " + (System.currentTimeMillis() - start2) + " ms");
  }


  public static Room parse(String ligne) {
    Room r = new Room();
    Matcher matcher = Pattern.compile("([a-z-]+)-([0-9]+)\\[([a-z]{5})\\]").matcher(ligne);
    matcher.find();
    r.encryptedName = matcher.group(1);
    r.id = Integer.parseInt(matcher.group(2));
    r.checksum = matcher.group(3);
    return r;
  }

  public static int compare(Entry<Character, Long> o1, Entry<Character, Long> o2) {
    if (o1.getValue() < o2.getValue()) {
      return 1;
    } else if (o1.getValue() > o2.getValue()) {
      return -1;
    } else {
      if (o1.getKey() < o2.getKey()) {
        return -1;
      } else if (o1.getKey() < o2.getKey()) {
        return 1;
      } else {
        return 0;
      }
    }
  }

  public static boolean estVraiRoom(Room r) {
    // Map<Character, Long> counters = r.encryptedName.chars().mapToObj(c -> (char) c)
    // .collect(Collectors.groupingBy(c -> c, Collectors.counting()));
    String checksumToCompare = r.encryptedName.chars().mapToObj(c -> (char) c).filter(c -> c != '-')
        .collect(Collectors.groupingBy(c -> c, Collectors.counting())).entrySet().stream()
        .sorted(day4::compare).limit(5).map(e -> e.getKey().toString())
        .collect(Collectors.joining(""));
    return checksumToCompare.equals(r.checksum);
  }



  public static void part1() {
    // System.out.println(estVraiRoom(parse("aaaaa-bbb-z-y-x-123[abxyz]")));
    // System.out.println(estVraiRoom(parse("a-b-c-d-e-f-g-h-987[abcde]")));
    // System.out.println(estVraiRoom(parse("not-a-real-room-404[oarel]")));
    // System.out.println(estVraiRoom(parse("totally-real-room-200[decoy]")));
    System.out.println(ImportUtils.getListStringUnParLigne("./src/main/resources/2016/day4")
        .stream().map(day4::parse).filter(day4::estVraiRoom).mapToInt(r -> r.id).sum());
  }


  public static void part2() {


  }

  public static class Room {
    String encryptedName;
    int id;
    String checksum;
  }


}

package fr.dufaure.clement.adventofcode.event2018;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class day1 {

  public static void main(String[] args) {
    day1Part1();
    day1Part2();
  }


  public static String importFile(String path) {
    BufferedReader buffer;
    StringBuilder input = new StringBuilder();
    try {
      buffer = new BufferedReader(new FileReader(path));
      String line;
      while ((line = buffer.readLine()) != null) {
        input.append(line);
      }
      buffer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return input.toString();
  }

  public static void day1Part1() {
    String data = importFile("./src/main/resources/2018/day1");
  }

  public static void day1Part2() {
    String data = importFile("./src/main/resources/2018/day1");
  }


}

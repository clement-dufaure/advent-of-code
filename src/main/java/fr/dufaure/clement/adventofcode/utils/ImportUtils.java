package fr.dufaure.clement.adventofcode.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImportUtils {

  public static List<Integer> getListeEntierUnParLigne(String path) {
    BufferedReader buffer;
    List<Integer> entiers = new ArrayList<>();
    try {
      buffer = new BufferedReader(new FileReader(path));
      String line;
      while ((line = buffer.readLine()) != null) {
        entiers.add(Integer.parseInt(line));
      }
      buffer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return entiers;
  }

  public static List<Long> getListeLongUnParLigne(String path) {
    BufferedReader buffer;
    List<Long> entiers = new ArrayList<>();
    try {
      buffer = new BufferedReader(new FileReader(path));
      String line;
      while ((line = buffer.readLine()) != null) {
        entiers.add(Long.parseLong(line));
      }
      buffer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return entiers;
  }

  public static String getString(String path) {
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

  public static String getStringWithNewLine(String path) {
    BufferedReader buffer;
    StringBuilder input = new StringBuilder();
    try {
      buffer = new BufferedReader(new FileReader(path));
      String line;
      while ((line = buffer.readLine()) != null) {
        input.append(line);
        input.append("\n");
      }
      buffer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return input.toString();
  }

  public static List<String> getListStringUnParLigne(String path) {
    BufferedReader buffer;
    List<String> lignes = new ArrayList<>();
    try {
      buffer = new BufferedReader(new FileReader(path));
      String line;
      while ((line = buffer.readLine()) != null) {
        lignes.add(line);
      }
      buffer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return lignes;
  }

}

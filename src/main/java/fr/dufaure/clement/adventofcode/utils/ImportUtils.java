package fr.dufaure.clement.adventofcode.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImportUtils {

  public static List<Integer> getListeEntierUnParLigne(String path) {
    List<Integer> entiers = new ArrayList<>();
    try (var buffer = new BufferedReader(new FileReader(path))) {
      String line;
      while ((line = buffer.readLine()) != null) {
        entiers.add(Integer.parseInt(line));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return entiers;
  }

  public static List<Long> getListeLongUnParLigne(String path) {
    List<Long> entiers = new ArrayList<>();
    try (var buffer = new BufferedReader(new FileReader(path))) {
      String line;
      while ((line = buffer.readLine()) != null) {
        entiers.add(Long.parseLong(line));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return entiers;
  }

  public static String getString(String path) {
    StringBuilder input = new StringBuilder();
    try (var buffer = new BufferedReader(new FileReader(path))) {
      String line;
      while ((line = buffer.readLine()) != null) {
        input.append(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return input.toString();
  }

  public static String getStringWithNewLine(String path) {
    StringBuilder input = new StringBuilder();
    try (var buffer = new BufferedReader(new FileReader(path))) {
      String line;
      while ((line = buffer.readLine()) != null) {
        input.append(line);
        input.append("\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return input.toString();
  }

  public static List<String> getListStringUnParLigne(String path) {
    List<String> lignes = new ArrayList<>();
    try (var buffer = new BufferedReader(new FileReader(path))) {
      String line;
      while ((line = buffer.readLine()) != null) {
        lignes.add(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return lignes;
  }

}

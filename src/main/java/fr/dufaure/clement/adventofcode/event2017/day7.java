package fr.dufaure.clement.adventofcode.event2017;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class day7 {

  public static void main(String[] args) {
    day7Part1();
    day7Part2();
  }


  public static List<String> importFile(String path) {
    BufferedReader buffer;
    List<String> programmes = new ArrayList<>();
    try {
      buffer = new BufferedReader(new FileReader(path));
      String line;
      while ((line = buffer.readLine()) != null) {
        programmes.add(line);
      }
      buffer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return programmes;
  }

  public static Program parseligne(String ligne) {
    Program program = new Program();
    StringBuilder lecture = new StringBuilder();
    int rang = 0;

    // lecture du nom
    while (rang < ligne.length()) {
      if (ligne.charAt(rang) != ' ') {
        lecture.append(ligne.charAt(rang));
        rang++;
      } else {
        rang++;
        break;
      }
    }
    program.setName(lecture.toString());

    lecture = new StringBuilder();

    // lecture du poids
    if (ligne.charAt(rang) != '(') {
      System.err.println("PAS NORMAL");
    } else {
      rang++;
    }
    while (rang < ligne.length()) {
      if (ligne.charAt(rang) != ')') {
        lecture.append(ligne.charAt(rang));
        rang++;
      } else {
        rang++;
        break;
      }
    }
    program.setWeight(Integer.parseInt(lecture.toString()));

    lecture = new StringBuilder();

    // lecture des fils
    if (ligne.length() >= rang + 4 && ligne.substring(rang, rang + 4).equals(" -> ")) {
      rang += 4;
      while (rang < ligne.length()) {
        if (ligne.charAt(rang) != ',') {
          lecture.append(ligne.charAt(rang));
          rang++;
        } else {
          rang += 2;
          program.getSubProgramsString().add(lecture.toString());
          lecture = new StringBuilder();
        }
      }
      program.getSubProgramsString().add(lecture.toString());
    }

    return program;
  }

  public static boolean affecterProgramAsonPere(Program program, List<Program> listeDesProgrammes) {
    if (listeDesProgrammes.isEmpty()) {
      return false;
    } else {
      for (Program programmePotentiel : listeDesProgrammes) {
        if (programmePotentiel.getSubProgramsString().contains(program.getName())) {
          programmePotentiel.getSubPrograms().add(program);
          return true;
        }
        if (affecterProgramAsonPere(program, programmePotentiel.getSubPrograms())) {
          return true;
        }
      }
      return false;
    }
  }

  public static void ajouterPoidsTotaux(Program base) {
    for (Program program : base.getSubPrograms()) {
      ajouterPoidsTotaux(program);
    }
    base.setTotalWeight(
        base.getWeight() + base.getSubPrograms().stream().mapToInt(p -> p.getTotalWeight()).sum());
  }

  public static void verifierEquilibre(Program base) {
    if (!base.getSubPrograms().isEmpty()) {
      int poidsAComparer = base.getSubPrograms().get(0).getTotalWeight();
      for (Program program : base.getSubPrograms()) {
        if (poidsAComparer != program.getTotalWeight()) {
          System.out.println("déséquilibre de " + base);
        }
      }
      for (Program program : base.getSubPrograms()) {
        verifierEquilibre(program);
      }
    }

    base.setTotalWeight(
        base.getWeight() + base.getSubPrograms().stream().mapToInt(p -> p.getTotalWeight()).sum());
  }


  public static void day7Part1() {
    List<String> data = importFile("./src/main/resources/2017/day7");
    List<Program> programmes1 = new ArrayList<>();
    List<Program> programmes2 = new ArrayList<>();
    for (String ligne : data) {
      programmes1.add(parseligne(ligne));
      programmes2.add(parseligne(ligne));
    }
    for (Program program : programmes1) {
      if (affecterProgramAsonPere(programmes2.get(programmes2.indexOf(program)), programmes2)) {
        programmes2.remove(program);
        // System.out.println(programmes2);
      } else {
        System.out.println(program.getName() + " n'as pas de pere");
      }
    }
    ajouterPoidsTotaux(programmes2.get(0));
    verifierEquilibre(programmes2.get(0));
    System.out.println(programmes2);
  }

  public static void day7Part2() {

  }

  public static class Program {
    private String name;
    private int weight;
    private int totalWeight;
    private List<String> subProgramsString = new ArrayList<>();
    private List<Program> subPrograms = new ArrayList<>();

    String getName() {
      return name;
    }

    void setName(String name) {
      this.name = name;
    }

    int getWeight() {
      return weight;
    }

    void setWeight(int weight) {
      this.weight = weight;
    }

    int getTotalWeight() {
      return totalWeight;
    }

    void setTotalWeight(int totalWeight) {
      this.totalWeight = totalWeight;
    }

    List<Program> getSubPrograms() {
      return subPrograms;
    }

    List<String> getSubProgramsString() {
      return subProgramsString;
    }

    @Override
    public String toString() {
      if (this.subPrograms.isEmpty()) {
        return "" + this.weight;
      } else {
        return "" + this.totalWeight + "(" + this.weight + ")" + this.subPrograms;
      }
    }

    @Override
    public boolean equals(Object object) {
      if (object instanceof Program && ((Program) object).getName().equals(this.name)) {
        return true;
      } else {
        return false;
      }
    }

  }

}

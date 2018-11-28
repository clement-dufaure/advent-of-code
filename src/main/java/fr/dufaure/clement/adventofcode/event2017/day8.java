package fr.dufaure.clement.adventofcode.event2017;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class day8 {

  public static void main(String[] args) {
    day8Part1();
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

  public static Condition parseCondition(String comparaisonTexte) {
    String[] elements = comparaisonTexte.split(" ");
    Condition condition = new Condition();
    condition.variable = elements[0];
    switch (elements[1]) {
      case "==":
        condition.typeCondition = TypeCondition.EQUALS;
        break;
      case ">=":
        condition.typeCondition = TypeCondition.GREATER_THAN_OR_EQUALS;
        break;
      case "<=":
        condition.typeCondition = TypeCondition.LESSER_THAN_OR_EQUALS;
        break;
      case ">":
        condition.typeCondition = TypeCondition.GREATER_THAN_STRICTLY;
        break;
      case "<":
        condition.typeCondition = TypeCondition.LESSER_THAN_STRICTLY;
        break;
      case "!=":
        condition.typeCondition = TypeCondition.DIFFERENT;
        break;
      default:
        System.err.println("CONDITION NON PREVUE !");
    }
    condition.valeur = Integer.parseInt(elements[2]);
    return condition;
  }

  public static Operation parseOperation(String operationTexte) {
    String[] elements = operationTexte.split(" ");
    Operation operation = new Operation();
    operation.variable = elements[0];
    switch (elements[1]) {
      case "inc":
        operation.typeOperation = TypeOperation.INC;
        break;
      case "dec":
        operation.typeOperation = TypeOperation.DEC;
        break;
      default:
        System.err.println("OPERATION NON PREVUE !");
    }
    operation.valeur = Integer.parseInt(elements[2]);
    return operation;
  }

  public static boolean conditionRespectee(Condition condition) {
    switch (condition.typeCondition) {
      case EQUALS:
        return regedit.get(condition.variable) == condition.valeur;
      case LESSER_THAN_OR_EQUALS:
        return regedit.get(condition.variable) <= condition.valeur;
      case GREATER_THAN_OR_EQUALS:
        return regedit.get(condition.variable) >= condition.valeur;
      case LESSER_THAN_STRICTLY:
        return regedit.get(condition.variable) < condition.valeur;
      case GREATER_THAN_STRICTLY:
        return regedit.get(condition.variable) > condition.valeur;
      case DIFFERENT:
        return regedit.get(condition.variable) != condition.valeur;
      default:
        System.err.println("ERROR");
        return false;
    }
  }

  public static void appliquerOperation(Operation operation) {
    switch (operation.typeOperation) {
      case INC:
        regedit.put(operation.variable, regedit.get(operation.variable) + operation.valeur);
        break;
      case DEC:
        regedit.put(operation.variable, regedit.get(operation.variable) - operation.valeur);
        break;
    }
  }

  public static Regedit regedit = new Regedit();
  public static int maxGlobal = 0;

  public static void day8Part1() {
    List<String> data = importFile("./src/main/resources/2017/day8");
    for (String ligne : data) {
      if (conditionRespectee(parseCondition(ligne.split(" if ")[1]))) {
        appliquerOperation(parseOperation(ligne.split(" if ")[0]));
      }
      int maxLocal = Collections.max(regedit.values());
      if (maxLocal > maxGlobal) {
        maxGlobal = maxLocal;
      }
    }
    System.out.println(regedit);
    System.out.println(Collections.max(regedit.values()));
    System.out.println(maxGlobal);
  }



  public static enum TypeOperation {
    DEC, INC;
  }

  public static enum TypeCondition {
    EQUALS, GREATER_THAN_OR_EQUALS, LESSER_THAN_OR_EQUALS, GREATER_THAN_STRICTLY, LESSER_THAN_STRICTLY, DIFFERENT;
  }

  public static class Operation {
    String variable;
    TypeOperation typeOperation;
    int valeur;

    @Override
    public String toString() {
      return "Operation [variable=" + variable + ", typeOperation=" + typeOperation + ", valeur="
          + valeur + "]";
    }

  }

  public static class Condition {
    String variable;
    TypeCondition typeCondition;
    int valeur;

    @Override
    public String toString() {
      return "Condition [variable=" + variable + ", typeCondition=" + typeCondition + ", valeur="
          + valeur + "]";
    }

  }

  @SuppressWarnings("serial")
  public static class Regedit extends HashMap<String, Integer> {

    public Regedit() {
      super();
    }

    @Override
    public Integer get(Object key) {
      if (!this.containsKey(key) && key instanceof String) {
        this.put((String) key, 0);
      }
      return super.get(key);
    }

  }

}

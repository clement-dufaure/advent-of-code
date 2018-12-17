package fr.dufaure.clement.adventofcode.event2018;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day16 {

  public static void main(String[] args) {
    long start1 = System.currentTimeMillis();
    part1();
    System.out.println("Execution part 1 en " + (System.currentTimeMillis() - start1) + " ms");
    long start2 = System.currentTimeMillis();
    part2();
    System.out.println("Execution part 2 en " + (System.currentTimeMillis() - start2) + " ms");
  }

  public static void part1() {
    parseInstruction();

    for (Instruction i : instructions) {
      for (Operation o : Operation.values()) {
        Integer opcodeAtester = i.operandes[0];
        Integer[] registreAtester =
            o.apply(i.operandes[1], i.operandes[2], i.operandes[3], i.registreAvant);
        if (registreAtester[i.operandes[3]] == i.registreApres[i.operandes[3]]) {
          i.nombreOperationOK++;
        } else {
          valeursPossibles.get(o).remove(opcodeAtester);
        }
      }
    }

    System.out.println(valeursPossibles);
    System.out.println(instructions.stream().filter(i -> i.nombreOperationOK >= 3).count());

  }


  public static void part2() {
    // determination des opcodes
    while (tableOpCode.keySet().size() != 16) {
      for (Operation o : Operation.values()) {
        if (valeursPossibles.containsKey(o) && valeursPossibles.get(o).size() == 1) {
          Integer valeurTrouvee = valeursPossibles.get(o).get(0);
          tableOpCode.put(valeurTrouvee, o);
          valeursPossibles.remove(o);
          for (List<Integer> liste : valeursPossibles.values()) {
            liste.remove(valeurTrouvee);
          }
        }
      }
    }

    // application jeu de test
    Integer[] registre = {0, 0, 0, 0};
    List<Integer[]> instructions = parseinstructionJeuDeTest();
    for (Integer[] instruction : instructions) {
      registre = tableOpCode.get(instruction[0]).apply(instruction[1], instruction[2],
          instruction[3], registre);
    }
    System.out.println(registre[0]);

  }

  public static void parseInstruction() {
    String str = ImportUtils.getString("./src/main/resources/2018/day16");
    Matcher matcher = Pattern.compile("Before: \\[([0-9]*), ([0-9]*), ([0-9]*), ([0-9]*)\\]"
        + "([0-9]*) ([0-9]*) ([0-9]*) ([0-9]*)"
        + "After:  \\[([0-9]*), ([0-9]*), ([0-9]*), ([0-9]*)\\]").matcher(str);
    while (matcher.find()) {
      Instruction i = new Instruction();
      i.registreAvant = new Integer[4];
      i.registreAvant[0] = Integer.parseInt(matcher.group(1));
      i.registreAvant[1] = Integer.parseInt(matcher.group(2));
      i.registreAvant[2] = Integer.parseInt(matcher.group(3));
      i.registreAvant[3] = Integer.parseInt(matcher.group(4));
      i.operandes = new Integer[4];
      i.operandes[0] = Integer.parseInt(matcher.group(5));
      i.operandes[1] = Integer.parseInt(matcher.group(6));
      i.operandes[2] = Integer.parseInt(matcher.group(7));
      i.operandes[3] = Integer.parseInt(matcher.group(8));
      i.registreApres = new Integer[4];
      i.registreApres[0] = Integer.parseInt(matcher.group(9));
      i.registreApres[1] = Integer.parseInt(matcher.group(10));
      i.registreApres[2] = Integer.parseInt(matcher.group(11));
      i.registreApres[3] = Integer.parseInt(matcher.group(12));
      instructions.add(i);
    }
  }

  public static List<Integer[]> parseinstructionJeuDeTest() {
    List<Integer[]> instructions = new ArrayList<>();
    List<String> tests = ImportUtils.getListStringUnParLigne("./src/main/resources/2018/day16test");
    for (String str : tests) {
      Matcher matcher = Pattern.compile("([0-9]*) ([0-9]*) ([0-9]*) ([0-9]*)").matcher(str);
      matcher.find();
      Integer[] operandes = new Integer[4];
      operandes[0] = Integer.parseInt(matcher.group(1));
      operandes[1] = Integer.parseInt(matcher.group(2));
      operandes[2] = Integer.parseInt(matcher.group(3));
      operandes[3] = Integer.parseInt(matcher.group(4));
      instructions.add(operandes);
    }
    return instructions;
  }


  static List<Instruction> instructions = new ArrayList<>();
  static HashMap<Operation, List<Integer>> valeursPossibles = new HashMap<>();
  static HashMap<Integer, Operation> tableOpCode = new HashMap<>();

  static {
    for (Operation operation : Operation.values()) {
      valeursPossibles.put(operation,
          IntStream.rangeClosed(0, 15).boxed().collect(Collectors.toList()));
    }
  }

  public static class Instruction {
    Integer[] registreAvant;
    Integer[] operandes;
    Integer[] registreApres;

    int nombreOperationOK = 0;
  }

  public static enum Operation {
    ADDR(TypeOperation.ADD, ValueToRead.REGISTER, ValueToRead.REGISTER),

    ADDI(TypeOperation.ADD, ValueToRead.REGISTER, ValueToRead.LITTERAL),

    MULR(TypeOperation.MUL, ValueToRead.REGISTER, ValueToRead.REGISTER),

    MULI(TypeOperation.MUL, ValueToRead.REGISTER, ValueToRead.LITTERAL),

    BANR(TypeOperation.BAN, ValueToRead.REGISTER, ValueToRead.REGISTER),

    BANI(TypeOperation.BAN, ValueToRead.REGISTER, ValueToRead.LITTERAL),

    BORR(TypeOperation.BOR, ValueToRead.REGISTER, ValueToRead.REGISTER),

    BORI(TypeOperation.BOR, ValueToRead.REGISTER, ValueToRead.LITTERAL),

    SETR(TypeOperation.SET, ValueToRead.REGISTER, ValueToRead.LITTERAL),

    SETI(TypeOperation.SET, ValueToRead.LITTERAL, ValueToRead.LITTERAL),

    GTIR(TypeOperation.GT, ValueToRead.LITTERAL, ValueToRead.REGISTER),

    GTRI(TypeOperation.GT, ValueToRead.REGISTER, ValueToRead.LITTERAL),

    GTRR(TypeOperation.GT, ValueToRead.REGISTER, ValueToRead.REGISTER),

    EQIR(TypeOperation.EQ, ValueToRead.LITTERAL, ValueToRead.REGISTER),

    EQRI(TypeOperation.EQ, ValueToRead.REGISTER, ValueToRead.LITTERAL),

    EQRR(TypeOperation.EQ, ValueToRead.REGISTER, ValueToRead.REGISTER)

    ;

    private Operation(TypeOperation typeOperation, ValueToRead lectureValeur1,
        ValueToRead lectureValeur2) {
      this.typeOperation = typeOperation;
      this.lectureValeur1 = lectureValeur1;
      this.lectureValeur2 = lectureValeur2;
    }

    TypeOperation typeOperation;
    ValueToRead lectureValeur1;
    ValueToRead lectureValeur2;

    Integer[] apply(int operande1, int operande2, int operande3, Integer[] registre) {
      Integer[] registreApres = Arrays.copyOf(registre, 4);
      registreApres[operande3] =
          typeOperation.fonction.apply(lectureValeur1.fonction.apply(operande1, registre),
              lectureValeur2.fonction.apply(operande2, registre));
      return registreApres;
    }
  }

  public static enum ValueToRead {
    LITTERAL((i, r) -> i), REGISTER((i, r) -> r[i]);

    BiFunction<Integer, Integer[], Integer> fonction;

    private ValueToRead(BiFunction<Integer, Integer[], Integer> fonction) {
      this.fonction = fonction;
    }
  }

  public static enum TypeOperation {
    ADD((a, b) -> a + b), MUL((a, b) -> a * b), BAN((a, b) -> a & b), BOR((a, b) -> a | b), SET(
        (a, b) -> a), GT((a, b) -> a > b ? 1 : 0), EQ((a, b) -> a == b ? 1 : 0);

    BiFunction<Integer, Integer, Integer> fonction;

    private TypeOperation(BiFunction<Integer, Integer, Integer> fonction) {
      this.fonction = fonction;
    }

  }

}

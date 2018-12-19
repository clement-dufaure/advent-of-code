package fr.dufaure.clement.adventofcode.event2018;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day19 {

  public static void main(String[] args) {
    long start1 = System.currentTimeMillis();
    // part1();
    System.out.println("Execution part 1 en " + (System.currentTimeMillis() - start1) + " ms");
    long start2 = System.currentTimeMillis();
    part2();
    System.out.println("Execution part 2 en " + (System.currentTimeMillis() - start2) + " ms");
  }

  public static void part1() {
    parseInstruction();
     Integer[] registre = {1, 0, 0, 0, 0, 0};
    // Integer[] registre = {0, 1, 10551275, 10551275, 3, 10551276};
    // Integer[] registre = {1,2,10551277,1,12,10551276};
    // Integer[] registre = {1, 2, 5275637, 10551274, 3, 10551276};
    // Integer[] registre = {3, 2, 5275638, 1, 7, 10551276};
    // Integer[] registre = {3,3,10551277,1,12,10551276};
    //Integer[] registre = {10, 5, 10551274, 42205096, 3, 10551276};

    // aret si mulr 4 4 4 joué soit si reg[0]>10551276

    int instructionAlire = 0;
    int i = 0;
    int refZero = 0;
    int refUn = 0;
    while (instructionAlire >= 0 && instructionAlire < instructions.size()) {
      Instruction instructionLue = instructions.get(instructionAlire);
      registre[ipRegister] = instructionAlire;
      instructionLue.operation.apply(instructionLue.value1, instructionLue.value2,
          instructionLue.value3, registre);

//      System.out.println(i + ": " + registre[0] + "," + registre[1] + "," + registre[2] + ","
//          + registre[3] + "," + registre[4] + "," + registre[5]);

      instructionAlire = registre[ipRegister];
      instructionAlire++;
      i++;
//      if (i > 1000) {
//        break;
//      }


      if (registre[0] != refZero || registre[1] != refUn) {
        System.out.println(registre[0] + " -- " + registre[1]);
        refZero = registre[0];
        refUn = registre[1];
      }
      // if (registre[1] != 2) {
      // break;
      // }
    }
    System.out.println(i + ": " + registre[0] + "," + registre[1] + "," + registre[2] + ","
        + registre[3] + "," + registre[4] + "," + registre[5]);
    System.out.println(registre[0]);
  }


  public static void part2() {
    int ref = 10551276;
    int zero = 0;
    int un = 1;
    while (true) {
      if (ref % un == 0) {
        zero += un;
        un++;
      } else {
        un++;
      }
//      if (zero > 120) {
//        break;
//      }
      if (un > ref) {
        break;
      }
      //System.out.println(zero + "--" + un);
    }

    System.out.println(zero);
    // 11751966 too low
    // = 27578880 !!!
  }

  public static void parseInstruction() {
    List<String> instructionsStr =
        ImportUtils.getListStringUnParLigne("./src/main/resources/2018/day19");

    String informationIp = instructionsStr.remove(0);
    ipRegister = Integer.parseInt(informationIp.replace("#ip ", ""));

    for (String ligne : instructionsStr) {
      Matcher matcher = Pattern.compile("(.{4}) ([0-9]*) ([0-9]*) ([0-9]*)").matcher(ligne);
      matcher.find();
      Instruction i = new Instruction();
      i.operation = Operation.getOperation(matcher.group(1));
      i.value1 = Integer.parseInt(matcher.group(2));
      i.value2 = Integer.parseInt(matcher.group(3));
      i.value3 = Integer.parseInt(matcher.group(4));
      instructions.add(i);
    }
  }


  static int ipRegister;
  static List<Instruction> instructions = new ArrayList<>();

  public static class Instruction {
    Operation operation;
    int value1;
    int value2;
    int value3;
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

    void apply(int operande1, int operande2, int operande3, Integer[] registre) {
      registre[operande3] =
          typeOperation.fonction.apply(lectureValeur1.fonction.apply(operande1, registre),
              lectureValeur2.fonction.apply(operande2, registre));
    }

    static Operation getOperation(String opStr) {
      for (Operation op : Operation.values()) {
        if (opStr.toUpperCase().equals(op.toString())) {
          return op;
        }
      }
      System.err.println("ERRORS");
      return null;
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
        (a, b) -> a), GT((a, b) -> a > b ? 1 : 0), EQ((a, b) -> a.equals(b) ? 1 : 0);

    BiFunction<Integer, Integer, Integer> fonction;

    private TypeOperation(BiFunction<Integer, Integer, Integer> fonction) {
      this.fonction = fonction;
    }

  }

}

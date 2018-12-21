package fr.dufaure.clement.adventofcode.event2018;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day21 {

  public static void main(String[] args) {
    parseInstruction();
    long start1 = System.currentTimeMillis();
    part1();
    System.out.println("Execution part 1 en " + (System.currentTimeMillis() - start1) + " ms");
    long start2 = System.currentTimeMillis();
    part2();
    System.out.println("Execution part 2 en " + (System.currentTimeMillis() - start2) + " ms");
  }

  public static void part1() {
    long x3Ref = 0;
    Long[] registre = {0L, 0L, 0L, 0L, 0L, 0L};
    long instructionAlire = 0;
    while (instructionAlire >= 0 && instructionAlire < instructions.size()) {
      Instruction instructionLue = instructions.get((int) instructionAlire);
      registre[ipRegister] = instructionAlire;
      instructionLue.operation.apply(instructionLue.value1, instructionLue.value2,
          instructionLue.value3, registre);
      if (registre[3] != x3Ref) {
        // System.out.println(registre[3]);
        x3Ref = registre[3];
        registre[0] = x3Ref;
      }
      instructionAlire = registre[ipRegister];
      instructionAlire++;
    }
    System.out.println(x3Ref);
  }

  // Apres observation, une boucle apparait (de taille environ 6000)
  // La bonne valeur est donc la dernier valeur de la boucle
  public static void part2() {
    LinkedList<Long> longTrouves = new LinkedList<>();
    Long[] registre = {0L, 0L, 0L, 0L, 0L, 0L};
    loop: while (true) {
      long instructionAlire = 0;
      while (instructionAlire >= 0 && instructionAlire < instructions.size()) {
        Instruction instructionLue = instructions.get((int) instructionAlire);
        registre[ipRegister] = instructionAlire;
        instructionLue.operation.apply(instructionLue.value1, instructionLue.value2,
            instructionLue.value3, registre);
        if (instructionLue.operation == Operation.EQRR) {
          if (!longTrouves.contains(registre[3])) {
            longTrouves.add(registre[3]);
          } else {
            break loop;
          }
        }
        instructionAlire = registre[ipRegister];
        instructionAlire++;
      }
    }
    System.out.println(longTrouves.peekLast());
  }



  public static void parseInstruction() {
    List<String> instructionsStr =
        ImportUtils.getListStringUnParLigne("./src/main/resources/2018/day21");

    String informationIp = instructionsStr.remove(0);
    ipRegister = Integer.parseInt(informationIp.replace("#ip ", ""));

    for (String ligne : instructionsStr) {
      Matcher matcher = Pattern.compile("(.{4}) ([0-9]*) ([0-9]*) ([0-9]*)").matcher(ligne);
      matcher.find();
      Instruction i = new Instruction();
      i.operation = Operation.getOperation(matcher.group(1));
      i.value1 = Long.parseLong(matcher.group(2));
      i.value2 = Long.parseLong(matcher.group(3));
      i.value3 = Integer.parseInt(matcher.group(4));
      instructions.add(i);
    }
  }


  static int ipRegister;
  static List<Instruction> instructions = new ArrayList<>();

  public static class Instruction {
    Operation operation;
    Long value1;
    Long value2;
    long value3;
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

    void apply(Long operande1, Long operande2, long operande3, Long[] registre) {
      registre[(int) operande3] =
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
    LITTERAL((i, r) -> i), REGISTER((i, r) -> r[(int) i.longValue()]);

    BiFunction<Long, Long[], Long> fonction;

    private ValueToRead(BiFunction<Long, Long[], Long> fonction) {
      this.fonction = fonction;
    }
  }

  public static enum TypeOperation {
    ADD((a, b) -> a + b), MUL((a, b) -> a * b), BAN((a, b) -> a & b), BOR((a, b) -> a | b), SET(
        (a, b) -> a), GT((a, b) -> a > b ? 1L : 0), EQ((a, b) -> a.equals(b) ? 1L : 0);

    BiFunction<Long, Long, Long> fonction;

    private TypeOperation(BiFunction<Long, Long, Long> fonction) {
      this.fonction = fonction;
    }

  }


  void test() {
    int x0 = 0;
    int x1 = 0;
    int x2 = 0;
    int x3 = 0;
    // pointeur int x4 = 0;
    int x5 = 0;

    x3 = 123;
    x3 = x3 & 456;
    if (x3 == 72) {
      x3 = 0;
      x5 = x3 | 65536;
      x3 = 5557974;
      while (true) {
        x2 = x5 & 255;
        x3 = x3 + x2;
        x3 = x3 & 16777215;
        x3 = x3 * 65899;
        x3 = x3 & 16777215;
        if (256 > x5) {
          x2 = 0;
          // x1 = x2 + 1;
          // x1 = x1 * 256;
          x1 = 256;
          if (x1 > x5) {
            // go to 26
          }
        } else {
          // goto 28
          if (x3 == x0) {
            return;
          }
        }
      }
    }



  }

}

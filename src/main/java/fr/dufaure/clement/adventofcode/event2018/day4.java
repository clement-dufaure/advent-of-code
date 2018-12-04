package fr.dufaure.clement.adventofcode.event2018;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day4 {

  public static void main(String[] args) {
    part1();
    part2();
  }

  public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm");
  // Map triee dans l'ordre du temps
  public static TreeMap<LocalDateTime, String> inscriptions = new TreeMap<>();
  public static List<Night> nights = new ArrayList<>();

  public static void parseLigneEtAjouterInscription(String ligne) {
    Matcher matcher = Pattern.compile("\\[(.*)\\] (.*)").matcher(ligne);
    matcher.find();
    inscriptions.put(LocalDateTime.parse(matcher.group(1), dtf), matcher.group(2));
  }

  public static void peuplerLesNuits() {
    Pattern shiftGuard = Pattern.compile("Guard #([0-9]*) begins shift");
    Night nuitEnCours = new Night();
    for (LocalDateTime timestamp : inscriptions.keySet()) {
      Matcher shift = shiftGuard.matcher(inscriptions.get(timestamp));
      // nouveau garde = nouvelle nuit
      if (shift.find()) {
        completerNuit(nuitEnCours);
        nights.add(nuitEnCours);
        nuitEnCours = new Night();
        nuitEnCours.IDofGuard = Integer.parseInt(shift.group(1));
        if (timestamp.getHour() == 23) {
          nuitEnCours.date = LocalDate.from(timestamp).plusDays(1);
        } else if (timestamp.getHour() == 0) {
          nuitEnCours.date = LocalDate.from(timestamp);
        } else {
          System.err.println("ERROR");
        }
      } else {
        completerNuit(nuitEnCours, timestamp.getMinute());
        if (inscriptions.get(timestamp).equals("falls asleep")) {
          nuitEnCours.etatsDuGardeParMinute.add(State.ASLEEP);
        }
        if (inscriptions.get(timestamp).equals("wakes up")) {
          nuitEnCours.etatsDuGardeParMinute.add(State.AWAKE);
        }
      }
    }
    nights.add(nuitEnCours);
    nights.remove(0);
  }


  public static void completerNuit(Night nuit, int jusqueIndex) {
    State etatACompleter;
    if (nuit.etatsDuGardeParMinute.size() > 0) {
      etatACompleter = nuit.etatsDuGardeParMinute.get(nuit.etatsDuGardeParMinute.size() - 1);
    } else {
      etatACompleter = State.AWAKE;
    }
    for (int i = nuit.etatsDuGardeParMinute.size(); i < jusqueIndex; i++) {
      nuit.etatsDuGardeParMinute.add(etatACompleter);
    }
  }

  public static void completerNuit(Night nuit) {
    if (nuit.etatsDuGardeParMinute.size() < 60) {
      State etatACompleter;
      if (nuit.etatsDuGardeParMinute.size() > 0) {
        etatACompleter = nuit.etatsDuGardeParMinute.get(nuit.etatsDuGardeParMinute.size() - 1);
      } else {
        etatACompleter = State.AWAKE;
      }
      for (int i = nuit.etatsDuGardeParMinute.size(); i < 60; i++) {
        nuit.etatsDuGardeParMinute.add(etatACompleter);
      }
    }
  }

  public static Map<Integer, List<Integer>> indexMinutesOuLeGardeDort = new HashMap<>();

  static {
    ImportUtils.getListStringUnParLigne("./src/main/resources/2018/day4")
        .forEach(s -> parseLigneEtAjouterInscription(s));
    peuplerLesNuits();
    for (Night nuit : nights) {
      int idDuGarde = nuit.IDofGuard;
      if (!indexMinutesOuLeGardeDort.containsKey(idDuGarde)) {
        indexMinutesOuLeGardeDort.put(idDuGarde, new ArrayList<>());
      }
      for (int i = 0; i < 60; i++) {
        if (nuit.etatsDuGardeParMinute.get(i).equals(State.ASLEEP)) {
          indexMinutesOuLeGardeDort.get(idDuGarde).add(i);
        }
      }
    }
  }

  public static void part1() {
    int maxCourant = 0;
    int guardeDuMaxCourant = -1;

    for (int guarde : indexMinutesOuLeGardeDort.keySet()) {
      if (indexMinutesOuLeGardeDort.get(guarde).size() > maxCourant) {
        maxCourant = indexMinutesOuLeGardeDort.get(guarde).size();
        guardeDuMaxCourant = guarde;
      }
    }


    int maxFrequecyMinute = 0;
    int minuteMaxfrequecy = -1;

    for (int i = 0; i < 60; i++) {
      int frequeceDeI = Collections.frequency(indexMinutesOuLeGardeDort.get(guardeDuMaxCourant), i);
      if (frequeceDeI > maxFrequecyMinute) {
        maxFrequecyMinute = frequeceDeI;
        minuteMaxfrequecy = i;
      }
    }

    System.out.println(guardeDuMaxCourant * minuteMaxfrequecy);

  }

  public static void part2() {
    int maxSameMinuteAsleepCourant = 0;
    int guardeDuMaxCourant = -1;
    int minuteDuMax = -1;

    for (int guarde : indexMinutesOuLeGardeDort.keySet()) {
      for (int i = 0; i < 60; i++) {
        int frequeceDeI = Collections.frequency(indexMinutesOuLeGardeDort.get(guarde), i);
        if (frequeceDeI > maxSameMinuteAsleepCourant) {
          maxSameMinuteAsleepCourant = frequeceDeI;
          guardeDuMaxCourant = guarde;
          minuteDuMax = i;
        }
      }
    }

    System.out.println(guardeDuMaxCourant * minuteDuMax);
  }

  public static class Night {
    public LocalDate date;
    public int IDofGuard;
    public List<State> etatsDuGardeParMinute = new ArrayList<>();
  }

  public static enum State {
    ASLEEP, AWAKE;
  }

}

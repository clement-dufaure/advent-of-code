package fr.dufaure.clement.adventofcode.event2018;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day24 {

	public static void main(String[] args) {
		// part 1
		parseData(0);
		long start = System.currentTimeMillis();
		while (true) {
			attaque(targetSelection());
			if (armeeImmune.size() == 0 || armeeInfection.size() == 0) {
				break;
			}
		}
		if (armeeImmune.size() == 0) {
			System.out.println("L'armee Infection a gagne avec "
					+ armeeInfection.stream().mapToInt(g -> g.effectif).sum() + " unites restantes");
		}
		if (armeeInfection.size() == 0) {
			System.out.println("L'armee Immune a gagne avec " + armeeImmune.stream().mapToInt(g -> g.effectif).sum()
					+ " unit�s restantes");
		}
		System.out.println("Resolu en " + (System.currentTimeMillis() - start) + " ms");

		// part 2
		long start2 = System.currentTimeMillis();
		int boost = 0;
		while (true) {
			parseData(++boost);
			while (true) {
				if (!attaque(targetSelection())) {
					break;
				}
				if (armeeImmune.size() == 0 || armeeInfection.size() == 0) {
					break;
				}
			}
			if (armeeImmune.size() == 0) {
				continue;
			}
			if (armeeInfection.size() == 0) {
				System.out.println("L'armee Immune aidee d'un boost de " + boost + " a gagne avec "
						+ armeeImmune.stream().mapToInt(g -> g.effectif).sum() + " unites restantes");
				break;
			}
		}
		System.out.println("Resolu en " + (System.currentTimeMillis() - start2) + " ms");
	}

	static List<Group> armeeImmune = new ArrayList<>();
	static List<Group> armeeInfection = new ArrayList<>();
	static List<Group> armees = new ArrayList<>();

	static TreeMap<Group, Group> targetSelection() {
		TreeMap<Group, Group> targets = new TreeMap<>(day24::ordreAttaque);
		List<Group> targetImmunesDisponibles = new ArrayList<>(armeeImmune);
		List<Group> targetInfectionDisponibles = new ArrayList<>(armeeInfection);
		armees.sort(day24::ordreChoixCible);
		for (Group attaquant : armees) {
			List<Group> listeDeTravail;
			if (attaquant.armee == Armee.IMMUNE) {
				listeDeTravail = targetInfectionDisponibles;
			} else {
				listeDeTravail = targetImmunesDisponibles;
			}
			int maxDegats = 0;
			Group groupeAattaquer = null;
			for (Group ennemi : listeDeTravail) {
				int degatsPotentiels = degatsPotentiels(attaquant, ennemi);
				if (degatsPotentiels > maxDegats) {
					groupeAattaquer = ennemi;
					maxDegats = degatsPotentiels;
				} else if (degatsPotentiels == maxDegats) {
					if (groupeAattaquer != null && ennemi.getEffectivepower() > groupeAattaquer.getEffectivepower()) {
						groupeAattaquer = ennemi;
					} else if (groupeAattaquer != null
							&& ennemi.getEffectivepower() == groupeAattaquer.getEffectivepower()) {
						if (ennemi.pointInitiative > groupeAattaquer.pointInitiative) {
							groupeAattaquer = ennemi;
						}
					}
				}
			}
			if (groupeAattaquer != null) {
				targets.put(attaquant, groupeAattaquer);
				listeDeTravail.remove(groupeAattaquer);
			}
		}
		return targets;
	}

	// si retourne faux, plus aucune armee n'est en mesure du tuer une unite de
	// l'autre armee
	static boolean attaque(TreeMap<Group, Group> targets) {
		boolean auMoinsUnePerte = false;
		for (Group attaquant : targets.keySet()) {
			if (attaquant.effectif > 0) {
				targets.get(attaquant).effectif -= degatsPotentiels(attaquant, targets.get(attaquant))
						/ targets.get(attaquant).pointDeVie;
				if (degatsPotentiels(attaquant, targets.get(attaquant)) / targets.get(attaquant).pointDeVie > 0) {
					auMoinsUnePerte = true;
				}
				if (targets.get(attaquant).effectif <= 0) {
					armees.remove(targets.get(attaquant));
					armeeImmune.remove(targets.get(attaquant));
					armeeInfection.remove(targets.get(attaquant));
				}
			}
		}
		return auMoinsUnePerte;
	}

	static int degatsPotentiels(Group attaquant, Group ennemi) {
		if (ennemi.faiblesses.contains(attaquant.typeAttaque)) {
			return attaquant.getEffectivepower() * 2;
		}
		if (ennemi.resistances.contains(attaquant.typeAttaque)) {
			return 0;
		}
		return attaquant.getEffectivepower();
	}

	static int ordreChoixCible(Group g1, Group g2) {
		if (g1.getEffectivepower() > g2.getEffectivepower()) {
			return -1;
		} else if (g1.getEffectivepower() < g2.getEffectivepower()) {
			return 1;
		} else {
			if (g1.pointInitiative > g2.pointInitiative) {
				return -1;
			} else if (g1.pointInitiative < g2.pointInitiative) {
				return 1;
			} else {
				return 0;
			}
		}
	}

	static int ordreAttaque(Group g1, Group g2) {
		if (g1.pointInitiative > g2.pointInitiative) {
			return -1;
		} else if (g1.pointInitiative < g2.pointInitiative) {
			return 1;
		} else {
			return 0;
		}
	}

	static class Group {
		Armee armee;
		List<TypeAttaque> faiblesses = new ArrayList<>();
		List<TypeAttaque> resistances = new ArrayList<>();
		int effectif;
		int pointDeVie;
		int pointDattaque;
		TypeAttaque typeAttaque;
		int pointInitiative;

		int getEffectivepower() {
			return effectif * pointDattaque;
		}

		@Override
		public String toString() {
			return "Group [armee=" + armee + ", faiblesses=" + faiblesses + ", resistances=" + resistances
					+ ", effectif=" + effectif + ", pointDeVie=" + pointDeVie + ", pointDattaque=" + pointDattaque
					+ ", typeAttaque=" + typeAttaque + ", pointInitiative=" + pointInitiative + "]";
		}

	}

	static enum Armee {
		IMMUNE, INFECTION;
	}

	static enum TypeAttaque {
		RADIATION, FIRE, BLUDGEONING, SLASHING, COLD;
	}

	static void parseData(int boost) {
		// RESET
		armeeImmune = new ArrayList<>();
		armeeInfection = new ArrayList<>();
		armees = new ArrayList<>();

		List<String> lignes = ImportUtils.getListStringUnParLigne("src/main/resources/2018/day24");
		List<Group> listeEnCours = armeeImmune;
		Armee armeeEnCours = Armee.IMMUNE;
		for (String ligne : lignes) {
			if (ligne.equals("Immune System:") || ligne.equals("Infection:")) {
				// on passe
			} else if (ligne.equals("")) {
				listeEnCours = armeeInfection;
				armeeEnCours = Armee.INFECTION;
			} else {
				Group group = parseGroup(ligne);
				group.armee = armeeEnCours;
				if (armeeEnCours == Armee.IMMUNE) {
					group.pointDattaque += boost;
				}
				listeEnCours.add(group);
				armees.add(group);
			}
		}

	}

	static Group parseGroup(String ligne) {
		Group g = new Group();
		Matcher matcher = Pattern.compile("([0-9]*) units each with ([0-9]*) hit points (\\([a-z ,;]*\\))?"
				+ " ?with an attack that does ([0-9]*) ([a-z]*) damage at initiative ([0-9]*)").matcher(ligne);
		matcher.find();
		g.effectif = Integer.parseInt(matcher.group(1));
		g.pointDeVie = Integer.parseInt(matcher.group(2));
		if (matcher.group(3) != null) {
			Matcher matchFaiblesses = Pattern.compile("weak to (([a-z]*,? ?)*)").matcher(matcher.group(3));
			if (matchFaiblesses.find()) {
				for (String s : matchFaiblesses.group(1).replace(" ", "").split(",")) {
					g.faiblesses.add(TypeAttaque.valueOf(s.toUpperCase()));
				}
			}
			Matcher matchResistances = Pattern.compile("immune to (([a-z]*,? ?)*)").matcher(matcher.group(3));
			if (matchResistances.find()) {
				for (String s : matchResistances.group(1).replace(" ", "").split(",")) {
					g.resistances.add(TypeAttaque.valueOf(s.toUpperCase()));
				}
			}
		}
		g.pointDattaque = Integer.parseInt(matcher.group(4));
		g.typeAttaque = TypeAttaque.valueOf(matcher.group(5).toUpperCase());
		g.pointInitiative = Integer.parseInt(matcher.group(6));
		return g;
	}

}

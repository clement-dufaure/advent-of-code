package fr.dufaure.clement.adventofcode.event2018;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day15 {

	public static void main(String[] args) {
		part1();

	}

	public static void part1() {
		int nombreDeTour = 0;
		System.out.println(nombreDeTour + ":" + tousLesJoueurs);
		loop: while (true) {
			// ordre de tour selon lecture
			List<Joueur> listeDeTravail = new ArrayList<>();
			listeDeTravail.addAll(tousLesJoueurs);
			Collections.sort(listeDeTravail);
			System.out.println(nombreDeTour + ":" + listeDeTravail);
			for (Joueur j : listeDeTravail) {
				if (elfes.size() == 0 || goblins.size() == 0) {
					break loop;
				}
				if (tousLesJoueurs.contains(j)) {
					j.seDeplacer();
					j.attack();
				}
			}
			nombreDeTour++;
			// System.out.println(nombreDeTour + ":" + tousLesJoueurs);
		}
		System.out.println(nombreDeTour + ":" + tousLesJoueurs);
		int total = elfes.stream().mapToInt(e -> e.heartPoint).sum()
				+ goblins.stream().mapToInt(e -> e.heartPoint).sum();
		System.out.println(nombreDeTour);
		System.out.println(total);
		System.out.println(nombreDeTour * total);
		// 204512 false
	}

	static List<Joueur> elfes = new ArrayList<>();
	static List<Joueur> goblins = new ArrayList<>();
	static List<Joueur> tousLesJoueurs = new ArrayList<>();
	static List<Case> plateau = new ArrayList<>();

	static {
		List<String> lignes = ImportUtils.getListStringUnParLigne("src/advent/day15test6.txt");

		List<List<Case>> grilleBrute = new ArrayList<>();
		for (int i = 0; i < lignes.size(); i++) {
			List<Case> casesLigne = new ArrayList<>();
			for (int j = 0; j < lignes.get(i).length(); j++) {
				Case c = new Case();
				c.x = j;
				c.y = i;
				switch (lignes.get(i).charAt(j)) {
				case '#':
					c.typeCase = TypeCase.WALL;
					break;
				case '.':
					c.typeCase = TypeCase.PATH;
					break;
				case 'E':
					c.typeCase = TypeCase.PATH;
					Joueur e = new Joueur();
					e.emplacement = c;
					e.typeJoueur = TypeJoueur.ELFE;
					c.occupant = e;
					tousLesJoueurs.add(e);
					elfes.add(e);
					break;
				case 'G':
					c.typeCase = TypeCase.PATH;
					Joueur g = new Joueur();
					g.emplacement = c;
					g.typeJoueur = TypeJoueur.GOBLIN;
					c.occupant = g;
					tousLesJoueurs.add(g);
					goblins.add(g);
					break;
				default:
					break;
				}
				casesLigne.add(c);
			}
			grilleBrute.add(casesLigne);
		}

		for (int i = 0; i < grilleBrute.size(); i++) {
			for (int j = 0; j < grilleBrute.get(i).size(); j++) {
				if (grilleBrute.get(i).get(j).typeCase == TypeCase.PATH) {
					if (i > 0 && grilleBrute.get(i - 1).get(j).typeCase == TypeCase.PATH) {
						grilleBrute.get(i).get(j).caseUp = grilleBrute.get(i - 1).get(j);
					}
					if (i < grilleBrute.size() - 1 && grilleBrute.get(i + 1).get(j).typeCase == TypeCase.PATH) {
						grilleBrute.get(i).get(j).caseDown = grilleBrute.get(i + 1).get(j);
					}
					if (j > 0 && grilleBrute.get(i).get(j - 1).typeCase == TypeCase.PATH) {
						grilleBrute.get(i).get(j).caseLeft = grilleBrute.get(i).get(j - 1);
					}
					if (j < grilleBrute.get(i).size() - 1 && grilleBrute.get(i).get(j + 1).typeCase == TypeCase.PATH) {
						grilleBrute.get(i).get(j).caseRight = grilleBrute.get(i).get(j + 1);
					}
					plateau.add(grilleBrute.get(i).get(j));
				}
			}
		}

	}

	static Joueur getPotentielEnnemiAdjacent(Joueur j) {
		List<Joueur> ennemisPotentiels = new ArrayList<>();
		for (Case caseAdj : j.emplacement.getCasesAutour()) {
			if (caseAdj != null && caseAdj.occupant != null && caseAdj.occupant.typeJoueur != j.typeJoueur) {
				ennemisPotentiels.add(caseAdj.occupant);
			}
		}
		if (ennemisPotentiels.isEmpty()) {
			return null;
		} else {
			// attaque sur le plus faible
			int minimumHP = ennemisPotentiels.stream().mapToInt(ennemi -> ennemi.heartPoint).min().getAsInt();
			List<Joueur> ennemisPotentielsFiltres = ennemisPotentiels.stream().filter(e -> e.heartPoint == minimumHP)
					.collect(Collectors.toList());
			// les cases sont retournes par ordre de lecture donc on admet que le premier
			// ennemi de la liste est le premier par ordre de lecture
			return ennemisPotentielsFiltres.get(0);
		}
	}

	static Case chercherCaseVersEnnemiLePlusProche(Joueur j) {
		// partir de chaque ennemi, prendre les cases accessibles puis les cases
		// accessibles de cases accessibles jusqu'� arriver � la case du joueur
		List<Joueur> listeDesEnnemis;
		HashMap<Joueur, Integer> distanceDesEnnemis = new HashMap<>();
		HashMap<Joueur, Case> caseDuMonvementSiSelection = new HashMap<>();
		if (j.typeJoueur == TypeJoueur.ELFE) {
			listeDesEnnemis = goblins;
		} else {
			listeDesEnnemis = elfes;
		}
		for (Joueur ennemi : listeDesEnnemis) {
			int proximite = 0;
			int sizeEtapePrecedente = 0;
			Set<Case> ensembleCasesAccessibles = new HashSet<>();
			ensembleCasesAccessibles.addAll(casesAccessibles(ennemi.emplacement));
			loop: while (ensembleCasesAccessibles.size() != sizeEtapePrecedente) {
				proximite++;
				// path trouv� ?
				for (Case caseAdj : j.emplacement.getCasesAutour()) {
					// si l'ennemi est selectionne la case a avancer ser celle dans l'ordre de
					// lecture par construction
					if (ensembleCasesAccessibles.contains(caseAdj)) {
						distanceDesEnnemis.put(ennemi, proximite);
						caseDuMonvementSiSelection.put(ennemi, caseAdj);
						break loop;
					}
				}
				// potentiel evolution des paths disponibles
				sizeEtapePrecedente = ensembleCasesAccessibles.size();
				Set<Case> nouvellesCasesAccessibles = new HashSet<>();
				for (Case maCase : ensembleCasesAccessibles) {
					nouvellesCasesAccessibles.addAll(casesAccessibles(maCase));
				}
				ensembleCasesAccessibles.addAll(nouvellesCasesAccessibles);
			}
		}
		if (distanceDesEnnemis.isEmpty()) {
			return null;
		} else {
			Integer plusPetiteDistance = Collections.min(distanceDesEnnemis.values());
			List<Joueur> ennemiPlusProches = distanceDesEnnemis.entrySet().stream()
					.filter(e -> e.getValue().equals(plusPetiteDistance)).map(e -> e.getKey())
					.collect(Collectors.toList());
			Collections.sort(ennemiPlusProches);
			return caseDuMonvementSiSelection.get(ennemiPlusProches.get(0));
		}
	}

	static List<Case> casesAccessibles(Case maCase) {
		List<Case> caseAccesibles = new ArrayList<>();
		for (Case caseAdj : maCase.getCasesAutour()) {
			if (caseAdj != null && caseAdj.typeCase != TypeCase.WALL && caseAdj.occupant == null) {
				caseAccesibles.add(caseAdj);
			}
		}
		return caseAccesibles;
	}

	static class Joueur implements Comparable<Joueur> {
		TypeJoueur typeJoueur;
		Case emplacement;
		int heartPoint = 200;
		int attackPoint = 3;

		void attack() {
			Joueur aAttaquer = getPotentielEnnemiAdjacent(this);
			if (aAttaquer != null) {
				aAttaquer.heartPoint -= attackPoint;
				// verif mort
				if (aAttaquer.heartPoint <= 0) {
					aAttaquer.emplacement.occupant = null;
					aAttaquer.emplacement = null;
					tousLesJoueurs.remove(aAttaquer);
					if (aAttaquer.typeJoueur == TypeJoueur.ELFE) {
						elfes.remove(aAttaquer);
					} else {
						goblins.remove(aAttaquer);
					}
				}
			}
		}

		void seDeplacer() {
			if (getPotentielEnnemiAdjacent(this) != null) {
				// si ennemi adjacent on ne fait rien
				return;
			} else {
				Case c = chercherCaseVersEnnemiLePlusProche(this);
				if (c != null) {
					c.occupant = this;
					this.emplacement.occupant = null;
					this.emplacement = c;
				}
			}
		}

		@Override
		public int compareTo(Joueur arg0) {
			if (this.emplacement.y > arg0.emplacement.y) {
				return 1;
			} else if (this.emplacement.y < arg0.emplacement.y) {
				return -1;
			} else {
				if (this.emplacement.x > arg0.emplacement.x) {
					return 1;
				} else if (this.emplacement.x < arg0.emplacement.x) {
					return -1;
				} else {
					return 0;
				}
			}
		}

		@Override
		public String toString() {
			return "Joueur [typeJoueur=" + typeJoueur + ", heartPoint=" + heartPoint + ", coord=" + emplacement.x + ","
					+ emplacement.y + "]";
		}

	}

	static enum TypeJoueur {
		ELFE, GOBLIN;
	}

	static class Case {
		// pour la relation d'ordre et identification au cas ou
		int x;
		int y;

		TypeCase typeCase;
		Case caseUp;
		Case caseDown;
		Case caseLeft;
		Case caseRight;
		Joueur occupant;

		Case[] getCasesAutour() {
			Case[] cases = { caseUp, caseLeft, caseRight, caseDown };
			return cases;
		}

		@Override
		public String toString() {
			return "Case [x=" + x + ", y=" + y + "]";
		}

	}

	static enum TypeCase {
		WALL, PATH;
	}

}

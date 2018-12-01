package fr.dufaure.clement.adventofcode.event2017;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day7 {

	public static void main(String[] args) {
		day7Part1();
		day7Part2();
	}

	public static Program parseligne(String ligne) {
		Program program = new Program();
		program.setName(ligne.split("\\(")[0].trim());
		program.setWeight(Integer.parseInt(ligne.split("[()]")[1]));
		if (ligne.contains("->")) {
			program.getSubProgramsString().addAll(Arrays.asList(ligne.split("->")[1].trim().split(", ")));
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
		base.setTotalWeight(base.getWeight() + base.getSubPrograms().stream().mapToInt(p -> p.getTotalWeight()).sum());
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

		base.setTotalWeight(base.getWeight() + base.getSubPrograms().stream().mapToInt(p -> p.getTotalWeight()).sum());
	}

	public static void day7Part1() {
		List<String> data = ImportUtils.getListStringUnParLigne("./src/main/resources/2017/day7");
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

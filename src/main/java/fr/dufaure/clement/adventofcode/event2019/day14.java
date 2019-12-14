package fr.dufaure.clement.adventofcode.event2019;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day14 {

	public static void main(String[] args) {
		long start1 = System.currentTimeMillis();
		part1();
		System.out.println("Execution part 1 en " + (System.currentTimeMillis() - start1) + " ms");
		long start2 = System.currentTimeMillis();
		part2();
		System.out.println("Execution part 2 en " + (System.currentTimeMillis() - start2) + " ms");
	}

	static class DescriptionReaction {
		int quantiteProduite;
		Map<String, Integer> reactifsQuantifie = new HashMap<>();

		@Override
		public String toString() {
			return quantiteProduite + " avec " + reactifsQuantifie;
		}
	}

	static Map<String, DescriptionReaction> reactions = new HashMap<>();

	static {
		List<String> liste = ImportUtils.getListStringUnParLigne("./src/main/resources/2019/day14");
		for (String reaction : liste) {
			DescriptionReaction desc = new DescriptionReaction();
			String[] operandes = reaction.split(" => ");
			desc.quantiteProduite = Integer.parseInt(operandes[1].split(" ")[0]);
			String[] reactifs = operandes[0].split(", ");
			for (String reactif : reactifs) {
				desc.reactifsQuantifie.put(reactif.split(" ")[1], Integer.parseInt(reactif.split(" ")[0]));
			}
			reactions.put(operandes[1].split(" ")[1], desc);
		}
	}

	static void part1() {
		System.out.println(oreNecessaire("FUEL", 1));
		System.out.println(residus);
	}

	// around 1,8 minutes when around 2.7 milions of FUEL
	static void part2() {
		// reinit residus, qui sera conserve entre 2 prod de fuel
		residus = new HashMap<>();
		long quantFuel = 0;
		long quantOre = 1000000000000L;
		while (quantOre > 0) {
			quantOre -= (long) oreNecessaire("FUEL", 1);
			quantFuel++;
		}
		System.out.println(quantFuel - 1L);
	}

	static Map<String, Integer> residus = new HashMap<>();

	static int oreNecessaire(String reactif, int nombre) {
		int oreNecessaire = 0;
		int nombreReactionNecessaires = nombre % reactions.get(reactif).quantiteProduite == 0
				? nombre / reactions.get(reactif).quantiteProduite
				: (nombre / reactions.get(reactif).quantiteProduite) + 1;
		// surplus produit
		if (reactions.get(reactif).quantiteProduite * nombreReactionNecessaires > nombre) {
			residus.put(reactif, reactions.get(reactif).quantiteProduite * nombreReactionNecessaires - nombre);
		}
		for (Entry<String, Integer> newReactif : reactions.get(reactif).reactifsQuantifie.entrySet()) {
			int quantiteDeja = 0;
			if (residus.containsKey(newReactif.getKey())) {
				quantiteDeja = residus.get(newReactif.getKey());
				residus.put(newReactif.getKey(), 0);
			}
			int quantiteNecessaireAProduire = nombreReactionNecessaires * newReactif.getValue() - quantiteDeja;
			if (quantiteNecessaireAProduire <= 0) {
				residus.put(newReactif.getKey(), quantiteDeja - nombreReactionNecessaires * newReactif.getValue());
			} else if (newReactif.getKey().equals("ORE")) {
				oreNecessaire += quantiteNecessaireAProduire;
			} else {
				oreNecessaire += oreNecessaire(newReactif.getKey(), quantiteNecessaireAProduire);
			}
		}
		return oreNecessaire;
	}

}

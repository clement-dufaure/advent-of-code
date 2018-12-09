package fr.dufaure.clement.adventofcode.event2018;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day8 {

	public static void main(String[] args) {
		long start1 = System.currentTimeMillis();
		part1();
		System.out.println("Execution part 1 en " + (System.currentTimeMillis() - start1) + " ms");
		long start2 = System.currentTimeMillis();
		part2();
		System.out.println("Execution part 2 en " + (System.currentTimeMillis() - start2) + " ms");
	}

	public static List<Integer> listeEntiers = Arrays.asList(ImportUtils.getString("./src/main/resources/2018/day8").split(" "))
			.stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
	public static Node racine = extractNode();

	public static Node extractNode() {
		Node n = new Node();
		int nbFils = listeEntiers.remove(0);
		int nbMetadatas = listeEntiers.remove(0);
		for (int i = 0; i < nbFils; i++) {
			n.fils.add(extractNode());
		}
		for (int i = 0; i < nbMetadatas; i++) {
			n.metadatas.add(listeEntiers.remove(0));
		}
		return n;
	}

	public static int totalMetadatas(Node n) {
		int total = 0;
		for (Node node : n.fils) {
			total += totalMetadatas(node);
		}
		for (int metadata : n.metadatas) {
			total += metadata;
		}
		return total;
	}

	public static int totalMetadatasVersion2(Node n) {
		int total = 0;
		if (n.fils.size() == 0) {
			for (int metadata : n.metadatas) {
				total += metadata;
			}
		} else {
			for (int metadata : n.metadatas) {
				if (metadata > 0 && metadata <= n.fils.size()) {
					total += totalMetadatasVersion2(n.fils.get(metadata - 1));
				}
			}
		}
		return total;
	}

	public static void part1() {
		System.out.println(totalMetadatas(racine));
	}

	public static void part2() {
		System.out.println(totalMetadatasVersion2(racine));
	}

	public static class Node {
		public List<Integer> metadatas = new ArrayList<>();
		public List<Node> fils = new ArrayList<>();
	}

}

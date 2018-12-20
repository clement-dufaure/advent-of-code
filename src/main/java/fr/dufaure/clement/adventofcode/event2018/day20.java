package fr.dufaure.clement.adventofcode.event2018;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day20 {

	public static void main(String[] args) {
		long start1 = System.currentTimeMillis();
		part1();
		System.out.println("Execution part 1 en " + (System.currentTimeMillis() - start1) + " ms");
	}

	public static void part1() {
		System.out.println(chercherDistanceRoomPlusLoin(construirePlanEtGetStart()));
	}

	static List<Room> carte = new ArrayList<>();

	static Room construirePlanEtGetStart() {
		StringBuffer regex = new StringBuffer(ImportUtils.getString("./src/main/resources/2018/day20"));
		// on vire le debut et la fin
		regex.deleteCharAt(0).deleteCharAt(regex.length() - 1);

		Room start = new Room();
		start.x = 0;
		start.y = 0;

		avancerPath(start, regex);
		
//		for (char c : regex.toCharArray()) {
//			switch (c) {
//			case 'N':
//
//				break;
//			case 'W':
//
//				break;
//			case 'E':
//
//				break;
//			case 'S':
//
//				break;
//			case '(':
//
//				break;
//
//			default:
//				break;
//			}
//		}

		return null;

	}

	static void avancerPath(Room r, StringBuffer strRestant) {
		char charAanalyser = strRestant.toString().charAt(0);
		// si NWSE :  on avance, on verifie si la case existe sinon on la cree, on l'affecte a la prcedente selon la direction
		// si ( on demande la decomposition des path et on execute la methode pour chaque path
		// ) et | sont traites completement dans l'extraction des parenthese
		
		strRestant.deleteCharAt(0);

	}

	List<String> extrairePathParenthese(StringBuffer strRestant) {
		if (strRestant.toString().charAt(0) != '(') {
			System.err.println("ERROR");
			return null;
		}
		List<String> paths = new ArrayList<>();
		int i = 1;
		int charEnCours = 1;
		StringBuffer pathEnCours = new StringBuffer();
		while (i > 0) {
			char charAanalyser = strRestant.toString().charAt(0);
			strRestant.deleteCharAt(0);
			switch (charAanalyser) {
			case 'N':
				pathEnCours.append("N");
				break;
			case 'W':
				pathEnCours.append("W");
				break;
			case 'E':
				pathEnCours.append("E");
				break;
			case 'S':
				pathEnCours.append("S");
				break;
			case '(':
				i++;
				pathEnCours.append("(");
				break;
			case ')':
				i--;
				if (i != 0) {
					pathEnCours.append(")");
				}
				break;
			case '|':
				if (i == 1) {
					paths.add(pathEnCours.toString());
					pathEnCours = new StringBuffer();
				} else {
					pathEnCours.append("|");
				}
				break;
			default:
				break;
			}

		}
		return paths;
	}

	static int chercherDistanceRoomPlusLoin(Room room) {

		int distanceMax = 0;
		int sizeEtapePrecedente = 0;
		Set<Room> ensembleRoomsAccessibles = new HashSet<>();
		ensembleRoomsAccessibles.add(room);
		while (ensembleRoomsAccessibles.size() != sizeEtapePrecedente) {
			distanceMax++;

			// potentiel evolution des paths disponibles
			sizeEtapePrecedente = ensembleRoomsAccessibles.size();
			for (Room roomLocal : ensembleRoomsAccessibles) {
				ensembleRoomsAccessibles.addAll(roomLocal.getRoomsAccessibles());
			}
		}

		return distanceMax;
	}

	static class Room {
		// pour la relation d'ordre et identification au cas ou
		int x;
		int y;

		Room roomNorth;
		Room roomEast;
		Room roomWest;
		Room roomSouth;

		List<Room> getRoomsAccessibles() {
			List<Room> rooms = new ArrayList<>();
			if (roomNorth != null) {
				rooms.add(roomNorth);
			}
			if (roomWest != null) {
				rooms.add(roomWest);
			}
			if (roomEast != null) {
				rooms.add(roomEast);
			}
			if (roomSouth != null) {
				rooms.add(roomSouth);
			}
			return rooms;
		}

		@Override
		public String toString() {
			return "Case [x=" + x + ", y=" + y + "]";
		}

	}

}

package fr.dufaure.clement.adventofcode.utils;

import java.util.*;
import java.util.stream.Collectors;

public record Grid<T>(Map<Coord, Case<T>> gridMap, List<Case<T>> gridList, T defaultValue) {
	
	public Grid() {
		this(new HashMap<>(), new ArrayList<>(), null);
	}
	
	public Grid(T defaultValue) {
		this(new HashMap<>(), new ArrayList<>(), defaultValue);
	}
	
	static Coord sum(Coord c, DirectionSansDigonales d) {
		return new Coord(c.x + d.diffX, c.y + d.diffY);
	}
	
	public void add(int x, int y, T t) {
		Case<T> maCase = new Case(new Coord(x, y), t);
		gridList.add(maCase);
		gridMap.put(new Coord(x, y), maCase);
	}
	
	public void consolider() {
		for (Case<T> c : gridList) {
			for (DirectionSansDigonales d : DirectionSansDigonales.values()) {
				if (gridMap.containsKey(sum(c.coord, d))) {
					c.casesAdjacentes.put(d, gridMap.get(sum(c.coord, d)));
				}
			}
		}
	}
	
	public T get(int x, int y) {
		return gridMap.getOrDefault(new Coord(x, y), new Case<>(null, defaultValue)).value();
	}
	
	public int getMinX() {
		return gridList.stream().map(c -> c.coord).mapToInt(c -> c.x).min().getAsInt();
	}
	
	public int getMaxX() {
		return gridList.stream().map(c -> c.coord).mapToInt(c -> c.x).max().getAsInt();
	}
	
	public int getMinY() {
		return gridList.stream().map(c -> c.coord).mapToInt(c -> c.y).min().getAsInt();
	}
	
	public int getMaxY() {
		return gridList.stream().map(c -> c.coord).mapToInt(c -> c.y).max().getAsInt();
	}
	
	public List<T> getValues() {
		return gridList.stream().map(c -> c.value).collect(Collectors.toList());
	}
	
	public List<Case<T>> getCases() {
		return gridList;
	}
	
	@Override
	public String toString() {
		StringBuffer sysout = new StringBuffer();
		for (int y = getMinY(); y <= getMaxY(); y++) {
			for (int x = getMinX(); x <= getMaxX(); x++) {
				sysout.append(get(x, y));
			}
			sysout.append("\n");
		}
		return sysout.toString();
	}
	
	public record Case<T>(Coord coord, T value, Map<DirectionSansDigonales, Case<T>> casesAdjacentes) {
		
		public Case(Coord coord, T value) {
			this(coord, value, new EnumMap<>(DirectionSansDigonales.class));
		}
		
		@Override
		public String toString() {
			return value.toString();
		}
	}
	
	private record Coord(int x, int y) {
		
		@Override
		public String toString() {
			return "(" + x + "," + y + ")";
		}
	}
	
}

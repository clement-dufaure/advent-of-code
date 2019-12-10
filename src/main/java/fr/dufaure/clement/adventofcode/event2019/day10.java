package fr.dufaure.clement.adventofcode.event2019;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day10 {

	public static void main(String[] args) {
		part1();
		part2();
	}

	static List<Asteroid> asteroids = new ArrayList<>();
	static Asteroid newBase = null;

	private static void part1() {
		List<String> data = ImportUtils.getListStringUnParLigne("./src/main/resources/2019/day10");
		for (int i = 0; i < data.size(); i++) {
			for (int j = 0; j < data.get(i).length(); j++) {
				if (data.get(i).charAt(j) == '#') {
					asteroids.add(new Asteroid(j, i));
				}
			}
		}

		for (Asteroid base : asteroids) {
			for (Asteroid inView : asteroids) {
				if (!inView.equals(base) && isVisible(base, inView)) {
					base.numberOfAsteroidInView++;
				}
			}
		}

		Asteroid meilleurBase = asteroids.stream().max(Comparator.comparing(Asteroid::getNumberOfAsteroidInView))
				.orElseThrow(UnsupportedOperationException::new);
		newBase = meilleurBase;
		System.out.println(meilleurBase);

	}

	private static void part2() {
		final Asteroid laBase = newBase;
		List<Asteroid> asteroidsAdetruire = new ArrayList<>();
		asteroidsAdetruire.addAll(asteroids.stream().filter(a -> !a.equals(laBase)).collect(Collectors.toList()));
		int asteroidDestroyed = 0;
		while (asteroidDestroyed <= 200) {
			List<Asteroid> astInViewAtThisTurn = asteroidsAdetruire.stream().filter(a -> isVisible(laBase, a))
					.collect(Collectors.toList());
			if (astInViewAtThisTurn.size() + asteroidDestroyed <= 200) {
				asteroidsAdetruire.removeAll(astInViewAtThisTurn);
			} else {
				Collections.sort(astInViewAtThisTurn);
				System.out.println(astInViewAtThisTurn);
				System.out.println(astInViewAtThisTurn.get(200 + asteroidDestroyed - 1));
				break;
			}

		}
	}

	static boolean isVisible(Asteroid base, Asteroid inView) {
		if (base.equals(inView)) {
			return false;
		}

		for (Asteroid obstacle : asteroids) {

			if (
			// parmi les autres points
			!obstacle.equals(inView) && !obstacle.equals(base)
			// obstacle moins loin que inview et du meme coté
					&& (Math.abs((obstacle.x - base.x)) <= Math.abs((inView.x - base.x)))
					&& (Math.abs(obstacle.y - base.y) <= Math.abs(inView.y - base.y))
					&& (((inView.y <= obstacle.y) && (obstacle.y <= base.y))
							|| ((inView.y >= obstacle.y) && (obstacle.y >= base.y)))
					&& (((inView.x <= obstacle.x) && (obstacle.x <= base.x))
							|| ((inView.x >= obstacle.x) && (obstacle.x >= base.x)))) {
				// alignes cas pour eviter div par 0
				if ((obstacle.x - base.x) == 0 && (inView.x - base.x) == 0) {
					return false;
				}
				// et alignés
				if (// pour eviter div par 0
				((obstacle.x - base.x) != 0 && (inView.x - base.x) != 0)
						&& (obstacle.y - base.y) / (obstacle.x - base.x) == (inView.y - base.y) / (inView.x - base.x)) {
					return false;
				}
			}
		}
		return true;
	}

	private static class Asteroid implements Comparable<Asteroid> {
		private double x;
		private double y;
		private int numberOfAsteroidInView = 0;

		public Asteroid(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int getNumberOfAsteroidInView() {
			return numberOfAsteroidInView;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			long temp;
			temp = Double.doubleToLongBits(x);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			temp = Double.doubleToLongBits(y);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Asteroid other = (Asteroid) obj;
			if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
				return false;
			if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "Asteroid [x=" + x + ", y=" + y + ", numberOfAsteroidInView=" + numberOfAsteroidInView + "]";
		}

		@Override
		// compute polar coordianates
		// WARN : ref is "UP" so in this case it's y<0
		// WARN : starts on axis y- instead of x- in classical calculus
		public int compareTo(Asteroid arg0) {
			double thisy = (this.y - newBase.y);
			double thisx = (this.x - newBase.x);
			double arg0y = (arg0.y - newBase.y);
			double arg0x = (arg0.x - newBase.x);

			// cas juste au dessus
			if (thisx == 0 && thisy < 0) {
				return -1;
			}
			if (arg0x == 0 && arg0y < 0) {
				return 1;
			}

			// calcul avec -y car espace a l'envers vis a vis des y
			double angleThis = thisy == 0 ? (thisx > 0 ? 0 : Math.PI)
					: 2 * Math.atan(-thisy / (thisx + Math.sqrt(thisx * thisx + thisy * thisy)));
			if (angleThis < Math.PI / 2) {
				// decalage si dans le cadran
				angleThis += 2 * Math.PI;
			}
			double angleThat = arg0y == 0 ? (arg0x > 0 ? 0 : Math.PI)
					: 2 * Math.atan(-arg0y / (arg0x + Math.sqrt(arg0x * arg0x + arg0y * arg0y)));
			if (angleThat < Math.PI / 2) {
				angleThat += 2 * Math.PI;
			}

			if (angleThis < angleThat) {
				return 1;
			} else if (angleThis > angleThat) {
				return -1;
			} else {
				return 0;
			}

		}

	}
}

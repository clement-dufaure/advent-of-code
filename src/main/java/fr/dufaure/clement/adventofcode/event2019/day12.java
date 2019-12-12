package fr.dufaure.clement.adventofcode.event2019;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day12 {

	public static void main(String[] args) {
		long start1 = System.currentTimeMillis();
		part1();
		System.out.println("Execution part 1 en " + (System.currentTimeMillis() - start1) + " ms");
		long start2 = System.currentTimeMillis();
		part2();
		System.out.println("Execution part 2 en " + (System.currentTimeMillis() - start2) + " ms");
	}

	static List<Planet> moons = new ArrayList<>();

	static void initialize() {
		moons = new ArrayList<>();
		List<String> liste = ImportUtils.getListStringUnParLigne("./src/main/resources/2019/day12");
		Pattern p = Pattern.compile("<x=(-?\\d+), y=(-?\\d+), z=(-?\\d+)>");
		for (String coord : liste) {
			Matcher m = p.matcher(coord);
			if (m.matches()) {
				moons.add(new Planet(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)),
						Integer.parseInt(m.group(3))));
			} else {
				System.err.println(coord + " ne matche pas");
			}
		}
	}

	static void part1() {
		initialize();
		for (int i = 0; i < 1000; i++) {
			avancerDansLeTemps();
		}
		System.out.println(moons.stream().mapToInt(m -> m.getEnergy()).sum());
	}

	static void part2() {
		initialize();
		long i = 1;
		long tourResetX = 0;
		long tourResetY = 0;
		long tourResetZ = 0;

		while (true) {
			avancerDansLeTemps();
			boolean initialStateX = true;
			boolean initialStateY = true;
			boolean initialStateZ = true;
			for (Planet moon : moons) {
				boolean initialStateMoonX = moon.isAtInitialStateX();
				boolean initialStateMoonY = moon.isAtInitialStateY();
				boolean initialStateMoonZ = moon.isAtInitialStateZ();
				if (!initialStateMoonX) {
					initialStateX = false;
				}
				if (!initialStateMoonY) {
					initialStateY = false;
				}
				if (!initialStateMoonZ) {
					initialStateZ = false;
				}
			}
			// on ne prend que la premiere valeur trouvee
			if (initialStateX && tourResetX == 0) {
				System.out.println("All moons at initial state X at turn " + i);
				tourResetX = i;
			}
			if (initialStateY && tourResetY == 0) {
				System.out.println("All moons at initial state Y at turn " + i);
				tourResetY = i;
			}
			if (initialStateZ && tourResetZ == 0) {
				System.out.println("All moons at initial state Z at turn " + i);
				tourResetZ = i;
			}

			if (tourResetX > 0 && tourResetY > 0 && tourResetZ > 0) {
				break;
			}
			i++;
		}

		List<Long> cycles = new ArrayList<>();
		cycles.add(tourResetX);
		cycles.add(tourResetY);
		cycles.add(tourResetZ);

		System.out.println(cycles);
		System.out.println(lcm_of_array_elements(cycles.toArray(new Long[cycles.size()])));

	}

	static void avancerDansLeTemps() {
		// gravity
		for (int j = 0; j < moons.size(); j++) {
			for (int k = j + 1; k < moons.size(); k++) {
				if (moons.get(j).x < moons.get(k).x) {
					moons.get(j).velx++;
					moons.get(k).velx--;
				} else if (moons.get(j).x > moons.get(k).x) {
					moons.get(j).velx--;
					moons.get(k).velx++;
				}

				if (moons.get(j).y < moons.get(k).y) {
					moons.get(j).vely++;
					moons.get(k).vely--;
				} else if (moons.get(j).y > moons.get(k).y) {
					moons.get(j).vely--;
					moons.get(k).vely++;
				}

				if (moons.get(j).z < moons.get(k).z) {
					moons.get(j).velz++;
					moons.get(k).velz--;
				} else if (moons.get(j).z > moons.get(k).z) {
					moons.get(j).velz--;
					moons.get(k).velz++;
				}
			}
		}
		// velocity
		moons.forEach(m -> m.applyVelocity());
	}

	static class Planet {
		int xinitial;
		int yinitial;
		int zinitial;
		int x;
		int y;
		int z;
		int velx = 0;
		int vely = 0;
		int velz = 0;

		public Planet(int x, int y, int z) {
			this.xinitial = x;
			this.yinitial = y;
			this.zinitial = z;
			this.x = x;
			this.y = y;
			this.z = z;
		}

		void applyVelocity() {
			x += velx;
			y += vely;
			z += velz;
		}

		int getEnergy() {
			return (Math.abs(x) + Math.abs(y) + Math.abs(z)) * (Math.abs(velx) + Math.abs(vely) + Math.abs(velz));
		}

		boolean isAtInitialStateX() {
			return x == xinitial && velx == 0;
		}

		boolean isAtInitialStateY() {
			return y == yinitial && vely == 0;
		}

		boolean isAtInitialStateZ() {
			return z == zinitial && velz == 0;
		}

	}

	// PPCM : thx to StackOverflow :)
	public static BigInteger lcm_of_array_elements(Long[] element_array) {
		BigInteger lcm_of_array_elements = BigInteger.ONE;
		BigInteger divisor = new BigInteger("2");

		while (true) {
			int counter = 0;
			boolean divisible = false;

			for (int i = 0; i < element_array.length; i++) {

				// lcm_of_array_elements (n1, n2, ... 0) = 0.
				// For negative number we convert into
				// positive and calculate lcm_of_array_elements.

				if (element_array[i] == 0) {
					return BigInteger.ZERO;
				} else if (element_array[i] < 0) {
					element_array[i] = element_array[i] * (-1);
				}
				if (element_array[i] == 1) {
					counter++;
				}

				// Divide element_array by devisor if complete
				// division i.e. without remainder then replace
				// number with quotient; used for find next factor
				if (element_array[i] % divisor.longValue() == 0) {
					divisible = true;
					element_array[i] = element_array[i] / divisor.longValue();
				}
			}

			// If divisor able to completely divide any number
			// from array multiply with lcm_of_array_elements
			// and store into lcm_of_array_elements and continue
			// to same divisor for next factor finding.
			// else increment divisor
			if (divisible) {
				lcm_of_array_elements = lcm_of_array_elements.multiply(divisor);
			} else {
				divisor = divisor.add(BigInteger.ONE);
			}

			// Check if all element_array is 1 indicate
			// we found all factors and terminate while loop.
			if (counter == element_array.length) {
				return lcm_of_array_elements;
			}
		}
	}

}

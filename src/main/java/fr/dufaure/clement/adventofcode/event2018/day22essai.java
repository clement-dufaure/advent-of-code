package fr.dufaure.clement.adventofcode.event2018;

import java.io.IOException;

public class day22essai {
	public static void main(String[] args) throws IOException {

		int x = 16;
		int y = 741;
		long[][] cave = new long[x * 2][y * 2];
		int[][] cave2 = new int[x * 2][y * 2];
		int[][][] times = new int[x * 2][y * 2][3];
		for (int i = 0; i < times.length; i++) {
			for (int j = 0; j < times[0].length; j++) {
				for (int k = 0; k < times[0][0].length; k++) {
					if (i != 0 || j != 0 || k != 0) {
						times[i][j][k] = Integer.MAX_VALUE / 2;
					}
				}
			}
		}
		int sum = 0;
		int depth = 3558;
		System.out.println(depth);
		for (int i = 0; i < cave.length; i++) {
			for (int j = 0; j < cave[0].length; j++) {
				cave[i][j] = (((i == x - 1 && j == y - 1) ? 0
						: (i == 0) ? (j * 48271) : (j == 0) ? (i * 16807) : cave[i][j - 1] * cave[i - 1][j]) + depth)
						% 20183;
				cave2[i][j] = (int) (cave[i][j] % 3);
				if ((i != (x - 1) || j != (y - 1)) && i < x && j < y) {
					sum += (cave[i][j] % 3);
				}
			}
		}
		System.out.println("Part 1: " + sum);
		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			boolean flag = false;
			for (int j = 0; j < 2 * x; j++) {
				for (int k = 0; k < 2 * y; k++) {
					for (int l = 0; l < 3; l++) {
						int min = times[j][k][l];
						min = Math.min(min, check(j - 1, k, l, times, cave2));
						min = Math.min(min, check(j, k + 1, l, times, cave2));
						min = Math.min(min, check(j + 1, k, l, times, cave2));
						min = Math.min(min, check(j, k - 1, l, times, cave2));
						for (int m = 0; m < 3; m++) {
							if (m != l && (times[j][k][m] + 7 < min) && m != (cave2[j][k] + 2) % 3) {
								min = times[j][k][m] + 7;
							}
						}
						if (times[j][k][l] != min) {
							flag = true;
						}
						times[j][k][l] = min;
					}
				}
			}
			if (!flag) {
				break;
			}
		}
		int result = times[x - 1][y - 1][0];
		System.out.println("Part2: " + result);
	}

	private static int check(int j, int k, int l, int[][][] times, int[][] cave2) {
		if (j < 0 || j >= times.length || k < 0 || k >= times[0].length) {
			return Integer.MAX_VALUE;
		}
		int min = (l == (cave2[j][k] + 2) % 3) ? 100000 : times[j][k][l];
		for (int m = 0; m < 3; m++) {
			if (m != l && (times[j][k][m] + 7 < min) && m != (cave2[j][k] + 2) % 3) {
				min = times[j][k][m] + 7;
			}
		}
		return (l == (cave2[j][k] + 2) % 3) ? 100000 : min + 1;
	}
}
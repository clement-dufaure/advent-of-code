package fr.dufaure.clement.adventofcode.event2019;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class day23 {

	static final boolean displayMode = false;

	public static void main(String[] args) {
		long start1 = System.currentTimeMillis();
		part1();
		System.out.println("Execution part 1 en " + (System.currentTimeMillis() - start1) + " ms");
		long start2 = System.currentTimeMillis();
		part2();
		System.out.println("Execution part 2 en " + (System.currentTimeMillis() - start2) + " ms");
	}

	static List<NIC> computers = new ArrayList<>();
	static List<Boolean> computerActivity = new ArrayList<>();
	static Packet NAT;

	static void part1() {
		for (long i = 0; i < 50; i++) {
			computers.add(new NIC(i));
		}

		for (int i = 0; i < 50; i++) {
			computerActivity.add(false);
			Thread t = new Thread(computers.get(i));
			t.start();
		}
		Thread t = new Thread(new NatThread());
		t.start();
	}

	static void part2() {
		// 14257 twice
		// Packet 1151:14257 send by NAT
	}

	static class Packet {
		long X;
		long Y;

		public Packet(long x, long y) {
			X = x;
			Y = y;
		}

		@Override
		public String toString() {
			return X + ":" + Y;
		}

	}

	public static class NatThread implements Runnable {

		long lastNatY;

		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				boolean isOneComputer = computerActivity.stream().filter(a -> a).count() > 0;
				if (isOneComputer) {
					for (int i = 0; i < computerActivity.size(); i++) {
						computerActivity.set(i, false);
					}
				} else {
					System.out.println("Packet " + NAT + " send by NAT");
					if (NAT.Y == lastNatY) {
						System.err.println(lastNatY + " twice");
						System.exit(0);
					} else {
						lastNatY = NAT.Y;
						computers.get(0).file.add(NAT);
					}
				}
			}

		}

	}

	public static class NIC implements Runnable {
		long IP;
		boolean IPset = false;
		boolean stopped = false;
		long relativeBase = 0;
		int pointeur = 0;
		Queue<Packet> file = new ConcurrentLinkedQueue<>();
		ArrayList<Long> listeCode = new ArrayList<>();

		NIC(long IP) {
			this.IP = IP;

			String[] liste = ImportUtils.getString("./src/main/resources/2019/day23").split(",");

			for (int i = 0; i < liste.length; i++) {
				listeCode.add(Long.valueOf(liste[i]));
			}
			// on ajoute des 0 a la fin
			for (int i = 0; i < 2000; i++) {
				listeCode.add(0L);
			}
		}

		@Override
		public void run() {
			Packet c = null;
			List<Long> output = new ArrayList<>();

			mainLoop: while (true) {
				long parameter1;
				long parameter2;
				switch (String.valueOf(listeCode.get(pointeur) % 100)) {
				case "1":
					listeCode.set(
							getWhereToWrite(listeCode, (listeCode.get(pointeur) % 100000) / 10000,
									listeCode.get(pointeur + 3), relativeBase).intValue(),
							getParameter(listeCode, (listeCode.get(pointeur) % 1000) / 100, listeCode.get(pointeur + 1),
									relativeBase)
									+ getParameter(listeCode, (listeCode.get(pointeur) % 10000) / 1000,
											listeCode.get(pointeur + 2), relativeBase));
					pointeur += 4;
					break;
				case "2":
					listeCode.set(
							getWhereToWrite(listeCode, (listeCode.get(pointeur) % 100000) / 10000,
									listeCode.get(pointeur + 3), relativeBase).intValue(),
							getParameter(listeCode, (listeCode.get(pointeur) % 1000) / 100, listeCode.get(pointeur + 1),
									relativeBase)
									* getParameter(listeCode, (listeCode.get(pointeur) % 10000) / 1000,
											listeCode.get(pointeur + 2), relativeBase));
					pointeur += 4;
					break;
				case "3":
					if (IPset) {
						if (c != null) {
							listeCode.set(getWhereToWrite(listeCode, (listeCode.get(pointeur) % 1000) / 100,
									listeCode.get(pointeur + 1), relativeBase).intValue(), c.Y);
							c = null;
						} else if (!file.isEmpty()) {
							System.out.println("in");
							c = file.poll();
							listeCode.set(getWhereToWrite(listeCode, (listeCode.get(pointeur) % 1000) / 100,
									listeCode.get(pointeur + 1), relativeBase).intValue(), c.X);
						} else {
							listeCode.set(getWhereToWrite(listeCode, (listeCode.get(pointeur) % 1000) / 100,
									listeCode.get(pointeur + 1), relativeBase).intValue(), -1L);
						}
					} else {
						listeCode.set(getWhereToWrite(listeCode, (listeCode.get(pointeur) % 1000) / 100,
								listeCode.get(pointeur + 1), relativeBase).intValue(), IP);
						IPset = true;
					}
					pointeur += 2;
					break;
				case "4":
					output.add(getParameter(listeCode, (listeCode.get(pointeur) % 1000) / 100,
							listeCode.get(pointeur + 1), relativeBase));
					if (output.size() == 3) {
						computerActivity.set((int) IP, true);
						System.out.println(IP + " out to " + output.get(0));
						if (output.get(0).equals(255L)) {
							System.out.println(output);
							NAT = new Packet(output.get(1), output.get(2));
							output = new ArrayList<Long>();
						} else {
							computers.get((int) (long) output.get(0)).file
									.add(new Packet(output.get(1), output.get(2)));
							output = new ArrayList<Long>();
						}
					}
					pointeur += 2;
					break;
				case "5":
					parameter1 = getParameter(listeCode, (listeCode.get(pointeur) % 1000) / 100,
							listeCode.get(pointeur + 1), relativeBase);
					parameter2 = getParameter(listeCode, (listeCode.get(pointeur) % 10000) / 1000,
							listeCode.get(pointeur + 2), relativeBase);
					if (parameter1 != 0) {
						pointeur = (int) parameter2;
					} else {
						pointeur += 3;
					}
					break;
				case "6":
					parameter1 = getParameter(listeCode, (listeCode.get(pointeur) % 1000) / 100,
							listeCode.get(pointeur + 1), relativeBase);
					parameter2 = getParameter(listeCode, (listeCode.get(pointeur) % 10000) / 1000,
							listeCode.get(pointeur + 2), relativeBase);
					if (parameter1 == 0) {
						pointeur = (int) parameter2;
					} else {
						pointeur += 3;
					}
					break;
				case "7":
					listeCode.set(
							getWhereToWrite(listeCode, (listeCode.get(pointeur) % 100000) / 10000,
									listeCode.get(pointeur + 3), relativeBase).intValue(),
							getParameter(listeCode, (listeCode.get(pointeur) % 1000) / 100, listeCode.get(pointeur + 1),
									relativeBase) < getParameter(listeCode, (listeCode.get(pointeur) % 10000) / 1000,
											listeCode.get(pointeur + 2), relativeBase) ? 1L : 0L);
					pointeur += 4;
					break;
				case "8":
					listeCode.set(
							getWhereToWrite(listeCode, (listeCode.get(pointeur) % 100000) / 10000,
									listeCode.get(pointeur + 3), relativeBase).intValue(),
							getParameter(listeCode, (listeCode.get(pointeur) % 1000) / 100, listeCode.get(pointeur + 1),
									relativeBase)
											.equals(getParameter(listeCode, (listeCode.get(pointeur) % 10000) / 1000,
													listeCode.get(pointeur + 2), relativeBase)) ? 1L : 0L);
					pointeur += 4;
					break;
				case "9":
					relativeBase += getParameter(listeCode, (listeCode.get(pointeur) % 1000) / 100,
							listeCode.get(pointeur + 1), relativeBase);
					pointeur += 2;
					break;
				case "99":
					break mainLoop;
				default:
					System.err.println("Pas normal, opcode : " + listeCode.get(pointeur));
					throw new UnsupportedOperationException();
				}
			}
			stopped = true;
		}

		static Long getParameter(List<Long> programme, long parameterMode, Long value, Long relativeBase) {
			switch (String.valueOf(parameterMode)) {
			case "0":
				return programme.get(value.intValue());
			case "1":
				return value;
			case "2":
				return programme.get(relativeBase.intValue() + value.intValue());
			default:
				System.err.println("Invalide parameter mode : " + String.valueOf(parameterMode));
				throw new UnsupportedOperationException();
			}
		}

		static Long getWhereToWrite(List<Long> programme, long parameterMode, Long value, Long relativeBase) {
			switch (String.valueOf(parameterMode)) {
			case "0":
				return value;
			// "1":
			// return value;
			case "2":
				return relativeBase + value;
			default:
				System.err.println("Invalide parameter mode : " + String.valueOf(parameterMode));
				throw new UnsupportedOperationException();
			}
		}
	}

}

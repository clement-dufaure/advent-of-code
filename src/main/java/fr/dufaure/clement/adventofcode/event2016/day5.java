package fr.dufaure.clement.adventofcode.event2016;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.DatatypeConverter;

public class day5 {

	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		long start1 = System.currentTimeMillis();
		// part1();
		System.out.println("Execution part 1 en " + (System.currentTimeMillis() - start1) + " ms");
		long start2 = System.currentTimeMillis();
		part2();
		System.out.println("Execution part 2 en " + (System.currentTimeMillis() - start2) + " ms");
	}

	static final String INPUT = "cxdnnyjw";
	// static final String INPUT = "abc";

	static void part1() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		int index = 0;
		StringBuilder sb = new StringBuilder();
		while (sb.toString().length() < 8) {
			byte[] byteChaine = (INPUT + index).getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("MD5");
			String hashHex = DatatypeConverter.printHexBinary(md.digest(byteChaine)).toLowerCase();
			if (hashHex.subSequence(0, 5).equals("00000")) {
				sb.append(hashHex.charAt(5));
			}
			index++;
		}
		System.out.println(sb.toString());
	}

	static void part2() throws UnsupportedEncodingException, NoSuchAlgorithmException {
		int index = 0;
		Character[] passwordArray = { null, null, null, null, null, null, null, null };
		List<Character> passwordList = Arrays.asList(passwordArray);
		while (passwordList.contains(null)) {
			byte[] byteChaine = (INPUT + index).getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("MD5");
			String hashHex = DatatypeConverter.printHexBinary(md.digest(byteChaine)).toLowerCase();
			if (hashHex.subSequence(0, 5).equals("00000")) {
				try {
					int emplacementChar = Integer.parseInt("" + hashHex.charAt(5));
					// On ne remplace pas un charactere deja trouve
					if (emplacementChar < 8 && passwordList.get(emplacementChar) == null) {
						passwordList.set(emplacementChar, hashHex.charAt(6));
					}
				} catch (NumberFormatException e) {
					index++;
					continue;
				}
			}
			index++;
		}
		System.out.println(String.join("", passwordList.stream().map(c -> c.toString()).collect(Collectors.toList())));
	}

}

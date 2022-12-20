package fr.dufaure.clement.adventofcode.event2022;

import fr.dufaure.clement.adventofcode.utils.ImportUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day7 {
	public String part1(String input) {
		var liste = ImportUtils.getListStringUnParLigne(input);
		var racine = generateFileSystem(liste);
		var sum = getFolderWithAtMost(racine, 100_000).stream().mapToLong(AbstractFile::size).sum();
		return String.valueOf(sum);
	}
	
	public String part2(String input) {
		var liste = ImportUtils.getListStringUnParLigne(input);
		var racine = generateFileSystem(liste);
		var spaceAvailable = 70000000L - racine.size();
		for (var d : racine.getAllFolders().stream().sorted(Comparator.comparingLong(Directory::size)).toList()) {
			if (spaceAvailable + d.size >= 30000000L) {
				return String.valueOf(d.size);
			}
		}
		return null;
	}
	
	
	Directory generateFileSystem(List<String> input) {
		Directory racine = new Directory("/");
		Directory currentDirectory = racine;
		Pattern p = Pattern.compile("\\$ ([a-z]+) ?([a-z./]+)?");
		int i = 0;
		while (i < input.size()) {
			Matcher m = p.matcher(input.get(i));
			if (m.matches()) {
				switch (m.group(1)) {
					case "cd" -> {
						switch (m.group(2)) {
							case "/" -> currentDirectory = racine;
							case ".." -> currentDirectory = currentDirectory.cdParent();
							default -> currentDirectory = (Directory) currentDirectory.cd(m.group(2));
						}
						i++;
						continue;
					}
					case "ls" -> {
						i++;
						while (i < input.size() && !input.get(i).startsWith("$")) {
							var element = input.get(i).split(" ");
							if (element[0].equals("dir")) {
								currentDirectory.touch(new Directory(element[1]));
							} else {
								currentDirectory.touch(new File(Integer.parseInt(element[0]), element[1]));
							}
							i++;
						}
						continue;
					}
				}
			} else {
				throw new UnsupportedOperationException();
			}
		}
		return racine;
	}
	
	
	List<Directory> getFolderWithAtMost(Directory racine, int atMost) {
		return racine.getAllFolders().stream().filter(d -> d.size() <= atMost).toList();
	}
	
	private abstract sealed class AbstractFile permits File, Directory {
		
		Directory parent;
		String name;
		
		abstract long size();
		
		abstract AbstractFile cd(String s);
		
		abstract void touch(AbstractFile file);
		
		Directory cdParent() {
			return parent;
		}
		
	}
	
	private final class File extends AbstractFile {
		
		int size;
		
		public File(int size, String name) {
			this.size = size;
			this.name = name;
		}
		
		@Override
		long size() {
			return size;
		}
		
		@Override
		AbstractFile cd(String s) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		void touch(AbstractFile file) {
			throw new UnsupportedOperationException();
		}
		
	}
	
	private final class Directory extends AbstractFile {
		
		Map<String, AbstractFile> ls = new HashMap<>();
		Long size = null;
		
		Directory(String name) {
			this.name = name;
		}
		
		@Override
		long size() {
			if (size == null) {
				size = ls.values().stream().mapToLong(AbstractFile::size).sum();
			}
			return size;
		}
		
		@Override
		AbstractFile cd(String s) {
			return ls.get(s);
		}
		
		@Override
		void touch(AbstractFile file) {
			file.parent = this;
			ls.put(file.name, file);
		}
		
		List<Directory> getAllFolders() {
			List<Directory> liste = new ArrayList<>();
			for (var f : ls.values()) {
				if (f instanceof Directory) {
					liste.add((Directory) f);
					liste.addAll(((Directory) f).getAllFolders());
				}
			}
			return liste;
		}
		
	}
	
}

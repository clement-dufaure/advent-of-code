package fr.dufaure.clement.adventofcode.event2021;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import fr.dufaure.clement.adventofcode.utils.Day;
import fr.dufaure.clement.adventofcode.utils.ImportUtils;

public class Day12 implements Day {

    @Override
    public String part1(String inputPath) {
        var paths = new HashMap<String, List<String>>();
        ImportUtils.getListStringUnParLigne(inputPath).stream().map(s -> s.split("-")).forEach(sa -> {
            paths.computeIfAbsent(sa[0], k -> new ArrayList<String>());
            paths.computeIfAbsent(sa[1], k -> new ArrayList<String>());
            if (!sa[1].equals("start") && !sa[0].equals("end")) {
                paths.get(sa[0]).add(sa[1]);
            }
            if (!sa[0].equals("start") && !sa[1].equals("end")) {
                paths.get(sa[1]).add(sa[0]);
            }
        });
        var possiblePaths = new ArrayList<Path>();
        possiblePaths.add(Path.of(new Path(), "start").get());

        do {
            var pathsToComplete = possiblePaths.stream().filter(p -> !p.hasFinish).toList();
            var newPaths = pathsToComplete.stream().flatMap(p -> {
                var l = new ArrayList<Optional<Path>>();
                for (var cave : paths.get(p.caves.get(p.caves.size() - 1))) {
                    l.add(Path.of(p, cave));
                }
                return l.stream().filter(op -> op.isPresent()).map(op -> op.get());
            }).toList();
            possiblePaths.removeAll(pathsToComplete);
            possiblePaths.addAll(newPaths);
        } while (possiblePaths.stream().filter(p -> !p.hasFinish).count() != 0);

        return String.valueOf(possiblePaths.size());
    }

    private static class Path {
        List<String> caves = new ArrayList<String>();
        boolean hasFinish = false;

        public static Optional<Path> of(Path parentPath, String newCave) {
            var path = new Path();
            if (Character.isLowerCase(newCave.charAt(0)) && parentPath.caves.contains(newCave)) {
                // dontpass a second time in this small cave
                return Optional.empty();
            }
            if (newCave.equals("end")) {
                path.hasFinish = true;
            }
            path.caves = new ArrayList<>(parentPath.caves);
            path.caves.add(newCave);
            return Optional.of(path);
        }
    }

    @Override
    public String part2(String inputPath) {
        var paths = new HashMap<String, List<String>>();
        ImportUtils.getListStringUnParLigne(inputPath).stream().map(s -> s.split("-")).forEach(sa -> {
            paths.computeIfAbsent(sa[0], k -> new ArrayList<String>());
            paths.computeIfAbsent(sa[1], k -> new ArrayList<String>());
            if (!sa[1].equals("start") && !sa[0].equals("end")) {
                paths.get(sa[0]).add(sa[1]);
            }
            if (!sa[0].equals("start") && !sa[1].equals("end")) {
                paths.get(sa[1]).add(sa[0]);
            }
        });
        List<PathBis> possiblePaths = new ArrayList<PathBis>();
        var count = 0L;
        possiblePaths.add(PathBis.of(new PathBis(), "start").get());
        do {
            count += possiblePaths.stream().filter(p -> p.hasFinish).count();
            possiblePaths = possiblePaths.stream().filter(p -> !p.hasFinish).toList();
            possiblePaths = possiblePaths.stream().flatMap(p -> {
                var l = new ArrayList<Optional<PathBis>>();
                for (var cave : paths.get(p.lastCave)) {
                    l.add(PathBis.of(p, cave));
                }
                return l.stream().filter(op -> op.isPresent()).map(op -> op.get());
            }).collect(Collectors.toList());
        } while (possiblePaths.size() != 0);
        return String.valueOf(count);
    }

    private static class PathBis {
        Set<String> smallCaves = new HashSet<String>();
        String lastCave;
        boolean hasAlreadyPassedTwiceInASmallCave = false;
        boolean hasFinish = false;

        public static Optional<PathBis> of(PathBis parentPath, String newCave) {
            var path = new PathBis();
            path.lastCave = newCave;
            path.hasAlreadyPassedTwiceInASmallCave = parentPath.hasAlreadyPassedTwiceInASmallCave;
            path.smallCaves = new HashSet<>(parentPath.smallCaves);
            if (Character.isLowerCase(newCave.charAt(0))) {
                path.smallCaves.add(newCave);
                if (parentPath.smallCaves.contains(newCave)) {
                    if (path.hasAlreadyPassedTwiceInASmallCave) {
                        // dont pass a second time in this small cave
                        return Optional.empty();
                    } else {
                        // allow the first twice
                        path.hasAlreadyPassedTwiceInASmallCave = true;
                    }
                }
            }
            if (newCave.equals("end")) {
                path.hasFinish = true;
            }
            return Optional.of(path);
        }
    }

}

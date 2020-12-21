package level_21;

import common.Level;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Level21 extends Level {
    static final boolean VERBOSE = false;
    Pattern ingLine = Pattern.compile("^(?<ingredients>[^(]+)\\(contains (?<allergens>[^)]+)\\)$");
    Map<String, Set<String>> allergenToIngredient;
    Set<String> uniqueIngredients;
    List<List<String>> almostRaw;

    public Level21(String filename) {
        allergenToIngredient = new TreeMap<>();
        uniqueIngredients = new HashSet<>();
        almostRaw = new ArrayList<>();

        for (String line : readResources(filename)) {
            Matcher m = ingLine.matcher(line);
            if (!m.matches()) {
                throw new RuntimeException("Can't parse: " + line);
            }
            List<String> iList = Arrays.asList(m.group("ingredients").split(" "));
            almostRaw.add(iList);
            Set<String> ingredientsSet = new HashSet<>(iList);
            uniqueIngredients.addAll(ingredientsSet);
            Set<String> allergensSet = new HashSet<>(Arrays.asList(m.group("allergens").split(", ")));
            if (VERBOSE) System.out.println(allergensSet + " => " + ingredientsSet);
            for (String a : allergensSet) {
                Set<String> reduced = allergenToIngredient.getOrDefault(a, new HashSet<>(ingredientsSet));
                reduced.retainAll(ingredientsSet);
                allergenToIngredient.put(a, reduced);
            }
        }

        if (VERBOSE) System.out.println("Allergens, roughly: " + allergenToIngredient);

        // cleanup allergens
        for (boolean allClean = false; !allClean; ) {
            allClean = true;
            for (Map.Entry<String, Set<String>> entry : allergenToIngredient.entrySet()) {
                if (entry.getValue().size() == 1) {
                    for (Map.Entry<String, Set<String>> subEntry : allergenToIngredient.entrySet()) {
                        if (subEntry.getKey().equals(entry.getKey())) {
                            continue;
                        }
                        subEntry.getValue().removeAll(entry.getValue());
                    }
                } else {
                    allClean = false;
                }
            }
        }

        if (VERBOSE) System.out.println("Allergens, precise: " + allergenToIngredient);
    }

    int p1() {
        Set<String> hypoallergenic = new HashSet<>(uniqueIngredients);
        for (Set<String> v : allergenToIngredient.values()) {
            hypoallergenic.removeAll(v);
        }
        if (VERBOSE) System.out.println("Hypos: " + hypoallergenic);

        int result = 0;
        for (List<String> rl : almostRaw) {
            for (String i : rl) {
                if (hypoallergenic.contains(i)) {
                    result++;
                }
            }
        }
        return result;
    }

    String p2() {
        return allergenToIngredient.values().stream().flatMap(Collection::stream).collect(Collectors.joining(","));
    }

    public static void main(String[] args) {
        Level21 l = new Level21("input");
        System.out.println("Part1: " + l.p1());
        System.out.println("Part2: " + l.p2());
    }

}
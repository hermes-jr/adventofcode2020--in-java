package level_15;

import common.Level;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Level15 extends Level {
    static final boolean VERBOSE = false;

    int p1(String seed, int steps) {
        Map<Integer, ImmutablePair<Integer, Integer>> memory = new HashMap<>();
        List<Integer> seeds = Arrays.stream(seed.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        int lastSpoken = -1;
        for (int turn = 1; turn <= steps; turn++) {
            if (turn <= seeds.size()) {
                lastSpoken = sayNumber(memory, seeds.get(turn - 1), turn);
            } else {
                if (VERBOSE) System.out.println(memory);
                if (!memory.containsKey(lastSpoken)) {
                    lastSpoken = 0;
                } else {
                    ImmutablePair<Integer, Integer> seenTimes = memory.get(lastSpoken);
                    lastSpoken = sayNumber(memory, seenTimes.left - seenTimes.right, turn);
                }
            }
        }
        return lastSpoken;
    }

    private int sayNumber(Map<Integer, ImmutablePair<Integer, Integer>> memory, int number, int turn) {
        if (VERBOSE) System.out.printf("turn %4d, num: %d%n", turn, number);
        memory.put(number, ImmutablePair.of(turn, memory.getOrDefault(number, ImmutablePair.of(turn, turn)).left));
        return number;
    }

    public static void main(String[] args) {
        Level15 l = new Level15();
        String seed = "2,0,1,7,4,14,18";
        System.out.println("Part1: " + l.p1(seed, 2020));
        System.out.println("Part2: " + l.p1(seed, 30_000_000));
    }

}
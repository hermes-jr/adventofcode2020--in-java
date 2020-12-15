package level_15;

import common.Level;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Level15 extends Level {
    static final boolean VERBOSE = false;
    int[] seenA = new int[30_000_001];
    int[] seenB = new int[30_000_001];

    int p1(String seed, int steps) {
        Arrays.fill(seenA, -1);
        List<Integer> seeds = Arrays.stream(seed.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        int lastSpoken = -1;
        for (int turn = 1; turn <= steps; turn++) {
            if (turn <= seeds.size()) {
                lastSpoken = sayNumber(seeds.get(turn - 1), turn);
            } else {
                int v = seenB[lastSpoken];
                if (v == 0) {
                    lastSpoken = 0;
                } else {
                    lastSpoken = sayNumber(seenA[lastSpoken] - seenB[lastSpoken], turn);
                }
            }
        }
        return lastSpoken;
    }

    private int sayNumber(int number, int turn) {
        if (VERBOSE) System.out.printf("turn %4d, num: %d%n", turn, number);
        if (seenA[number] == -1) {
            seenA[number] = turn;
        }
        seenB[number] = seenA[number];
        seenA[number] = turn;
        return number;
    }

    public static void main(String[] args) {
        Level15 l = new Level15();
        String seed = "2,0,1,7,4,14,18";
        System.out.println("Part1: " + l.p1(seed, 2020));
        System.out.println("Part2: " + l.p1(seed, 30_000_000));
    }

}
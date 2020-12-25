package level_25;

import common.Level;

public class Level25 extends Level {
    static final boolean VERBOSE = false;
    static final long REM = 20201227;

    long p1(long a, long b) {
        long bls = bruteLoopSize(b);
        long z = 1;
        if (VERBOSE) System.out.println("A original: " + a + " B loops: " + bls);
        for (long i = 0; i < bls; i++) {
            z *= a;
            z %= REM;
        }
        if (VERBOSE) System.out.println("A transformed: " + z);
        return z;
    }

    /*
    Surprisingly, this turned out to be enough to solve in adequate time,
    no factorization voodoo involved.
    Well, I guess 2020 was shitty enough to complicate things even more,
    so thanks Eric for making the puzzles relatively easy this time :)
    */
    long bruteLoopSize(long a) {
        long z = 1;
        for (long i = 0; i < REM; i++) {
            z *= 7;
            z %= REM;
            if (z == a) {
                return i + 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        Level25 l = new Level25();
        System.out.println("Part1: " + l.p1(11404017L, 13768789L));
    }

}
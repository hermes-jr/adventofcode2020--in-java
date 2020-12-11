package level_11;

import common.Level;
import common.Point2D;

import java.util.*;

public class Level11 extends Level {
    final private int W;
    final private int H;
    final List<String> in;
    boolean[] hallMap;
    final Set<Integer> seats = new HashSet<>();
    final private static boolean VERBOSE = false;

    public Level11(String filename) {
        in = readResources(filename);
        H = in.size();
        W = in.get(0).length();
        parseInput();
    }

    private void parseInput() {
        hallMap = new boolean[H * W];
        for (int y = 0; y < H; y++) {
            char[] s = in.get(y).toCharArray();
            for (int x = 0; x < W; x++) {
                if (s[x] == '.') {
                    continue;
                }
                int idx = coordinatesToIdx(x, y);
                seats.add(idx);
                if (s[x] == '#') {
                    hallMap[idx] = true;
                }
            }
        }
    }

    private int coordinatesToIdx(int x, int y) {
        return x + y * W;
    }

    long p1() {
        for (; ; ) {
            if (VERBOSE) printHall();
            boolean[] nextFrame = new boolean[hallMap.length];
            for (int y = 0; y < H; y++) {
                for (int x = 0; x < W; x++) {
                    int idx = coordinatesToIdx(x, y);
                    if (!seats.contains(idx)) {
                        continue;
                    }
                    int n = countNeighborsP1(x, y);
                    if (!hallMap[idx] && n == 0) {
                        nextFrame[idx] = true;
                    } else if (hallMap[idx] && n >= 4) {
                        nextFrame[idx] = false;
                    } else {
                        nextFrame[idx] = hallMap[idx];
                    }
                }
            }
            if (Arrays.compare(nextFrame, hallMap) == 0) {
                return countOccupiedSeats();
            }
            hallMap = nextFrame;
        }
    }

    long p2() {
        for (; ; ) {
            if (VERBOSE) printHall();
            boolean[] nextFrame = new boolean[hallMap.length];
            for (int y = 0; y < H; y++) {
                for (int x = 0; x < W; x++) {
                    int idx = coordinatesToIdx(x, y);
                    if (!seats.contains(idx)) {
                        continue;
                    }
                    int n = countNeighborsP2(x, y);
                    if (!hallMap[idx] && n == 0) {
                        nextFrame[idx] = true;
                    } else if (hallMap[idx] && n >= 5) {
                        nextFrame[idx] = false;
                    } else {
                        nextFrame[idx] = hallMap[idx];
                    }
                }
            }
            if (Arrays.compare(nextFrame, hallMap) == 0) {
                return countOccupiedSeats();
            }
            hallMap = nextFrame;
        }
    }

    private int countNeighborsP1(int x, int y) {
        List<Point2D> n = new ArrayList<>();
        addNeighbor(n, x - 1, y - 1);
        addNeighbor(n, x, y - 1);
        addNeighbor(n, x + 1, y - 1);
        addNeighbor(n, x - 1, y);
        addNeighbor(n, x + 1, y);
        addNeighbor(n, x - 1, y + 1);
        addNeighbor(n, x, y + 1);
        addNeighbor(n, x + 1, y + 1);

        return countOccupiedInCoordinates(n);
    }

    private int countNeighborsP2(int x, int y) {
        List<Point2D> n = new ArrayList<>();
        addExistingNeighbor(n, x, y, -1, -1);
        addExistingNeighbor(n, x, y, 0, -1);
        addExistingNeighbor(n, x, y, 1, -1);
        addExistingNeighbor(n, x, y, -1, 0);
        addExistingNeighbor(n, x, y, 1, 0);
        addExistingNeighbor(n, x, y, -1, 1);
        addExistingNeighbor(n, x, y, 0, 1);
        addExistingNeighbor(n, x, y, 1, 1);

        return countOccupiedInCoordinates(n);
    }

    private int bToI(boolean b) {
        return b ? 1 : 0;
    }

    private void addNeighbor(List<Point2D> neighbors, int x, int y) {
        if (x < 0 || x >= W || y < 0 || y >= H) {
            return;
        }
        neighbors.add(new Point2D(x, y));
    }

    private void addExistingNeighbor(List<Point2D> n, int x, int y, int mx, int my) {
        for (int d = 1; ; d++) {
            int nx = x + d * mx;
            int ny = y + d * my;
            if (nx < 0 || nx >= W || ny < 0 || ny >= H) {
                return; // No seats in range
            }
            if (seats.contains(coordinatesToIdx(nx, ny))) {
                addNeighbor(n, nx, ny);
                return;
            }
        }
    }

    private void printHall() {
        System.out.print(System.lineSeparator());
        for (int y = 0; y < H; y++) {
            for (int x = 0; x < W; x++) {
                char p = '.';
                int idx = coordinatesToIdx(x, y);
                if (seats.contains(idx)) {
                    p = hallMap[idx] ? '#' : 'L';
                }
                System.out.print(p);
            }
            System.out.print(System.lineSeparator());
        }
        System.out.print(System.lineSeparator());
    }

    private int countOccupiedInCoordinates(Collection<Point2D> neighbors) {
        int result = 0;
        for (Point2D p : neighbors) {
            result += bToI(hallMap[coordinatesToIdx(p.getX(), p.getY())]);
        }
        return result;
    }

    long countOccupiedSeats() {
        long result = 0;
        for (int y = 0; y < H; y++) {
            for (int x = 0; x < W; x++) {
                if (hallMap[coordinatesToIdx(x, y)]) {
                    result++;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Level11 l = new Level11("input");
        System.out.println("Part1: " + l.p1());
        l.parseInput();
        System.out.println("Part2: " + l.p2());
    }
}

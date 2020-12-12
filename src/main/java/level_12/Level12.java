package level_12;

import common.Level;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.LinkedList;
import java.util.List;

public class Level12 extends Level {
    static final boolean VERBOSE = false;
    static final List<ImmutablePair<Integer, Integer>> directions = new LinkedList<>();
    int curDirection = 0;

    private static void initDirections() {
        directions.clear();
        directions.add(ImmutablePair.of(1, 0));
        directions.add(ImmutablePair.of(0, 1));
        directions.add(ImmutablePair.of(-1, 0));
        directions.add(ImmutablePair.of(0, -1));
    }

    public Level12() {
        initDirections();
    }

    int p1(String fileName) {
        List<String> in = readResources(fileName);

        curDirection = 0;
        int x = 0;
        int y = 0;
        for (String line : in) {
            if (VERBOSE) System.out.print(line + "\n\t");
            char direction = line.charAt(0);
            int param = Integer.parseInt(line.substring(1));
            int dx = 0, dy = 0;
            switch (direction) {
                case 'W':
                    dx = -param;
                    break;
                case 'E':
                    dx = param;
                    break;
                case 'S':
                    dy = param;
                    break;
                case 'N':
                    dy = -param;
                    break;
                case 'R':
                    curDirection = (curDirection + (param / 90)) % 4;
                    break;
                case 'L':
                    curDirection = (4 + curDirection - (param / 90)) % 4;
                    break;
                case 'F':
                    ImmutablePair<Integer, Integer> cd = directions.get(curDirection);
                    dx = cd.getLeft() * param;
                    dy = cd.getRight() * param;
                    break;
            }
            x += dx;
            y += dy;
            if (VERBOSE) System.out.println(x + ":" + y + " heading " + directions.get(curDirection));

        }
        return Math.abs(x) + Math.abs(y);
    }

    int p2(String fileName) {
        List<String> in = readResources(fileName);

        int x = 0;
        int y = 0;
        int wpX = 10;
        int wpY = -1;
        for (String line : in) {
            if (VERBOSE) System.out.print(line + "\n\t");
            char direction = line.charAt(0);
            int param = Integer.parseInt(line.substring(1));
            switch (direction) {
                case 'W':
                    wpX -= param;
                    break;
                case 'E':
                    wpX += param;
                    break;
                case 'S':
                    wpY += param;
                    break;
                case 'N':
                    wpY -= param;
                    break;
                case 'R':
                    for (int i = 0; i < param / 90; i++) {
                        int tmp = wpY;
                        //noinspection SuspiciousNameCombination
                        wpY = wpX;
                        wpX = -tmp;
                    }
                    break;
                case 'L':
                    for (int i = 0; i < param / 90; i++) {
                        int tmp = wpX;
                        //noinspection SuspiciousNameCombination
                        wpX = wpY;
                        wpY = -tmp;
                    }
                    break;
                case 'F':
                    x += wpX * param;
                    y += wpY * param;
                    break;
            }
            if (VERBOSE) System.out.println(x + ":" + y + " wp " + wpX + ":" + wpY);

        }
        return Math.abs(x) + Math.abs(y);
    }

    public static void main(String[] args) {
        Level12 l = new Level12();
        System.out.println("Part1: " + l.p1("input"));
        System.out.println("Part2: " + l.p2("input"));
    }
}

package level_24;

import common.Level;
import common.Point3D;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Level24 extends Level {
    static final boolean VERBOSE = false;
    static final Map<String, Point3D> directions = new HashMap<>();
    Set<Point3D> tiles;

    static {
        directions.put("nw", new Point3D(0, 1, -1));
        directions.put("ne", new Point3D(1, 0, -1));
        directions.put("e", new Point3D(1, -1, 0));
        directions.put("se", new Point3D(0, -1, 1));
        directions.put("sw", new Point3D(-1, 0, 1));
        directions.put("w", new Point3D(-1, 1, 0));
    }

    public Level24(String filename) {
        tiles = new HashSet<>();

        for (String line : readResources(filename)) {
            Point3D currentCoordinate = Point3D.ZERO;
            char[] asChars = line.toCharArray();
            for (int i = 0; i < asChars.length; i++) {
                String direction = String.valueOf(asChars[i]);
                if (asChars[i] == 's' || asChars[i] == 'n') {
                    direction += String.valueOf(asChars[++i]);
                }
                currentCoordinate = currentCoordinate.add(directions.get(direction));
            }
            if (VERBOSE) System.out.println("Identified a tile: " + currentCoordinate);
            if (tiles.contains(currentCoordinate)) {
                tiles.remove(currentCoordinate);
            } else {
                tiles.add(currentCoordinate);
            }
        }

        if (VERBOSE) System.out.println(tiles);
    }

    int p1() {
        return tiles.size();
    }

    int p2() {
        for (int turns = 1; turns <= 100; turns++) {
            Set<Point3D> nextTurnTiles = new HashSet<>();
            Set<Point3D> potential = getPotentialTiles(tiles);
            for (Point3D p : potential) {
                int neighborsCount = countAliveNeighbors(p, tiles);
                if (tiles.contains(p)) {
                    if (neighborsCount == 1 || neighborsCount == 2) {
                        nextTurnTiles.add(p);
                    }
                } else {
                    if (neighborsCount == 2) {
                        nextTurnTiles.add(p);
                    }
                }
            }
            tiles = nextTurnTiles;
        }
        return tiles.size();
    }

    private Set<Point3D> getPotentialTiles(Set<Point3D> tiles) {
        Set<Point3D> result = new HashSet<>();
        for (Point3D p : tiles) {
            result.add(p);
            for (Point3D offset : directions.values()) {
                result.add(p.add(offset));
            }
        }
        return result;
    }

    private int countAliveNeighbors(Point3D p, Set<Point3D> tiles) {
        int result = 0;
        for (Point3D offset : directions.values()) {
            if (tiles.contains(p.add(offset))) {
                result++;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Level24 l = new Level24("input");
        System.out.println("Part1: " + l.p1());
        System.out.println("Part2: " + l.p2());
    }

}
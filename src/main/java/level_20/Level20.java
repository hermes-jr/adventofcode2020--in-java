package level_20;

import common.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Level20 extends Level {
    static final boolean VERBOSE = true;
    static final int TILE_SIDE = 10;
    List<Tile> knownTiles = new ArrayList<>();

    public Level20(String filename) {
        List<String> lines = readResources(filename);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i++);
            int tileId = Integer.parseInt(line.replaceAll("[^\\p{N}]", ""));

            Tile t = new Tile(tileId);
            for (int j = 0; j < TILE_SIDE; j++, i++) {
                line = lines.get(i);
                t.getPixels()[j] = line.toCharArray();
            }
            t.calcCompat();
            knownTiles.add(t);
        }
        if (VERBOSE) System.out.println(knownTiles);
    }

    long p1() {
        Map<Integer, Integer> seen = new HashMap<>();
        for (int i = 0; i < knownTiles.size() - 1; i++) {
            jIter:
            for (int j = i + 1; j < knownTiles.size(); j++) {
                Tile t1 = knownTiles.get(i);
                Tile t2 = knownTiles.get(j);
                for (char[] ec : t1.edgesCompat) {
                    if (t2.isCompatible(ec) != -1) {
                        seen.put(t1.tileId, seen.getOrDefault(t1.tileId, 0) + 1);
                        seen.put(t2.tileId, seen.getOrDefault(t2.tileId, 0) + 1);
                        continue jIter;
                    }
                }
            }
        }


        if (VERBOSE) System.out.println(seen);

        long result = 1;
        for (Map.Entry<Integer, Integer> e : seen.entrySet()) {
            if (e.getValue().equals(2)) {
                result *= e.getKey();
            }
        }
        return result;
    }

    int p2() {
        return -1;
    }

    public static void main(String[] args) {
        Level20 l = new Level20("input");
        System.out.println("Part1: " + l.p1());
//        System.out.println("Part2: " + l.p2());
    }
}
package level_03;

import common.Level;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class Level03 extends Level {
    ArrayList<BitSet> mapAsBits = new ArrayList<>();
    int w = 0;

    public Level03(String filename) {
        List<String> map = readResources(filename);
        for (String line : map) {
            w = line.length();
            BitSet cbs = new BitSet(w);
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == '#') {
                    cbs.set(i);
                }
            }
            mapAsBits.add(cbs);
        }
        System.out.println("Result1: " + p1(3, 1));
        System.out.println("Result2: " + p2());
    }

    public int p1(int dx, int dy) {
        int treesCollected = 0;
        for (int x = 0, y = 0; y < mapAsBits.size(); x += dx, y += dy) {
            BitSet line = mapAsBits.get(y);
            if (line.get(x % w)) {
                treesCollected++;
            }
        }
        return treesCollected;
    }

    public long p2() {
        return ((long) p1(1, 1)) * p1(3, 1) * p1(5, 1) * p1(7, 1) * p1(1, 2);
    }


    public static void main(String[] args) {
        new Level03("input");
    }
}

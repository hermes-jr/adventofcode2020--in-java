package level_05;

import common.Level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Level05 extends Level {
    private static final boolean VERBOSE = false;

    public Level05() {
        List<String> rawSeats = readResources("input");

        List<Integer> asNums = new ArrayList<>();
        for (String s : rawSeats) {
            Integer asInt = toInt(s);
            asNums.add(asInt);
        }
        Collections.sort(asNums);
        System.out.println("Result1: " + asNums.get(asNums.size() - 1));

        int mySeat = -1;
        for (int i = 1; i < asNums.size(); i++) {
            if (VERBOSE) System.out.println(asNums.get(i));
            if (!asNums.get(i).equals(asNums.get(i - 1) + 1)) {
                mySeat = asNums.get(i - 1) + 1;
                break;
            }
        }
        System.out.println("Result2: " + mySeat);
    }

    Integer toInt(String in) {
        Integer r = Integer.parseInt(in.replace("F", "0").replace("L", "0").replace("B", "1").replace("R", "1"), 2);
        if (VERBOSE) System.out.println(in + " -> " + r);
        return r;

    }

    public static void main(String[] args) {
        new Level05();
    }

}

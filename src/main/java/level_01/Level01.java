package level_01;

import common.Level;

import java.util.List;
import java.util.stream.Collectors;

public class Level01 extends Level {
    public Level01(String filename) {
        int sum = 2020;

        List<Integer> data = readResources(filename).stream().map(Integer::parseInt).collect(Collectors.toList());
        int[] seen = new int[sum + 1];

        for (Integer i : data) {
            if (i >= 0 && i <= sum) {
                seen[i]++;
            }
        }

        for (Integer cmp : data) {
            if (seen[sum - cmp] > 0) {
                System.out.println("Result1: " + cmp * (sum - cmp));
                break;
            }
        }

        outer:
        for (Integer term0 : data) {
            int subSum = sum - term0;
            // Find the other two compounds
            for (Integer cmp : data) {
                if (subSum - cmp >= 0 && !term0.equals(cmp) && seen[subSum - cmp] > 0) {
                    System.out.println("Result2: " + term0 * cmp * (subSum - cmp));
                    break outer;
                }
            }
        }

    }

    public static void main(String[] args) {
        new Level01("input");
    }
}

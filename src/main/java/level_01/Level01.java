package level_01;

import common.Level;

import java.util.List;
import java.util.stream.Collectors;

public class Level01 extends Level {
    public Level01(String filename) {
        int sum = 2020;

        List<Integer> data = readResources(filename)
                .stream()
                .map(Integer::parseInt)
                .sorted()
                .collect(Collectors.toList());

        for (int i = 0, j = data.size() - 1; i < j; ) {
            Integer v1 = data.get(i);
            Integer v2 = data.get(j);
            if (v1 + v2 < sum) {
                i++;
            } else if (v1 + v2 > sum) {
                j--;
            } else {
                System.out.println("Result1: " + v1 * v2);
                break;
            }
        }

        outer:
        for (int i = 0; i < data.size(); i++) {
            Integer v1 = data.get(i);

            int j = i + 1;
            int k = data.size() - 1;
            while (j < k) {
                Integer v2 = data.get(j);
                Integer v3 = data.get(k);
                int cs = v1 + v2 + v3;
                if (cs < sum) {
                    j++;
                } else if (cs > sum) {
                    k--;
                } else {
                    System.out.println("Result2: " + v1 * v2 * v3);
                    break outer;
                }
            }
        }
    }

    public static void main(String[] args) {
        new Level01("input");
    }
}

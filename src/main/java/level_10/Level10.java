package level_10;

import common.Level;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Level10 extends Level {

    private static final boolean VERBOSE = false;

    public List<Integer> readResourcesAsInts(String filename) {
        return super.readResources(filename).stream().map(Integer::parseUnsignedInt).collect(Collectors.toList());
    }

    public int p1(List<Integer> list) {
        int d1 = 0;
        int d3 = 0;
        list.add(0);
        Collections.sort(list);
        list.add(list.get(list.size() - 1) + 3);
        for (int i = 1; i < list.size(); i++) {
            int d = list.get(i) - list.get(i - 1);
            if (d > 3) {
                System.out.println("the difference is too big. weird");
            } else if (d == 1) {
                d1++;
            } else if (d == 3) {
                d3++;
            }
        }
        return d1 * d3;
    }

    public long p2(List<Integer> list) {
        list.add(0);
        Collections.sort(list);
        list.add(list.get(list.size() - 1) + 3);

        long[] options = new long[list.size()];
        Arrays.fill(options, 1L);
        for (int i = 1; i < list.size(); i++) {
            options[i] = options[i - 1];
            if (i - 3 >= 0 && list.get(i - 3) >= list.get(i) - 3) {
                options[i] += options[i - 3];
            }
            if (i - 2 >= 0 && list.get(i - 2) >= list.get(i) - 3) {
                options[i] += options[i - 2];
            }
        }

        if (VERBOSE) System.out.println(Arrays.toString(options));
        return options[options.length - 1];
    }

    public static void main(String[] args) {
        Level10 l = new Level10();
        List<Integer> adapters = l.readResourcesAsInts("input");
        System.out.println("Part1: " + l.p1(adapters));

        adapters = l.readResourcesAsInts("input");
        System.out.println("Part2: " + l.p2(adapters));
    }

}

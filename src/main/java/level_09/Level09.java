package level_09;

import common.Level;

import java.util.HashSet;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Set;
import java.util.stream.Collectors;

public class Level09 extends Level {

    private static final boolean VERBOSE = false;

    public List<Long> readResourcesAsInts(String filename) {
        return super.readResources(filename).stream().map(Long::parseUnsignedLong).collect(Collectors.toList());
    }

    public Long p1(List<Long> list, int preambleLength) {
        outer:
        for (int i = preambleLength; i < list.size(); i++) {
            Long sum = list.get(i);
            Set<Long> complements = new HashSet<>();
            for (int j = i - preambleLength; j < i; j++) {
                Long preambleItem = list.get(j);
                if (complements.contains(preambleItem)) {
                    continue outer;
                }
                complements.add(sum - preambleItem);
            }
            return sum;
        }
        return -1L;
    }

    // Takes less than 300ms, too lazy to optimize
    public Long p2(List<Long> list, Long tgtSum) {
        for (int preambleLength = list.size(); preambleLength >= 2; preambleLength--) {
            for (int i = 0; i < list.size() - preambleLength + 1; i++) {
                Long sum = 0L;
                for (int j = i; j < i + preambleLength; j++) {
                    sum += list.get(j);
                    if (sum.equals(tgtSum)) {
                        if (VERBOSE) System.out.println(sum + " i=" + i + " j=" + j);
                        if (VERBOSE) System.out.println("[i]=" + list.get(i) + " [j]=" + list.get(j));
                        LongSummaryStatistics subLStats = list.subList(i, j).stream().collect(Collectors.summarizingLong(Long::longValue));
                        if (VERBOSE) System.out.println("min=" + subLStats.getMin() + " max=" + subLStats.getMax());
                        return subLStats.getMax() + subLStats.getMin();
                    }
                }
            }
        }
        return -1L;
    }

    public static void main(String[] args) {
        Level09 l = new Level09();
        List<Long> nums = l.readResourcesAsInts("input");

        Long result1 = l.p1(nums, 25);
        System.out.println("Part1: " + result1);
        System.out.println("Part2: " + l.p2(nums, result1));
    }

}

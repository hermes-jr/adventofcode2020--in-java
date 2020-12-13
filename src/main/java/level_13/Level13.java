package level_13;

import common.Level;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Level13 extends Level {
    static final boolean VERBOSE = false;
    long arrival;
    List<Long> busIds = new ArrayList<>();
    List<Integer> timeDiffs = new ArrayList<>();

    public Level13(String fileName) {
        arrival = Long.parseUnsignedLong(readResourcesFirstLine(fileName));
        String[] ids = readResources(fileName).get(1).split(",");
        for (int i = 0; i < ids.length; i++) {
            String id = ids[i];
            if ("x".equals(id)) {
                continue;
            }
            timeDiffs.add(i);
            busIds.add(Long.parseLong(id));
        }
    }

    long p1() {
        long minDiff = Long.MAX_VALUE;
        long minBus = busIds.get(0);
        for (Long b : busIds) {
            long td = (long) (b * Math.ceil((double) arrival / b) - arrival);
            if (td < minDiff) {
                minDiff = td;
                minBus = b;
            }
        }
        return minBus * minDiff;
    }

    BigDecimal p2() {
        // Chinese remainder
        BigDecimal N = BigDecimal.ONE;
        for (Long z : busIds) {
            N = N.multiply(BigDecimal.valueOf(z));
        }
        if (VERBOSE) System.out.println(N);
        BigDecimal x = BigDecimal.ZERO;
        for (int i = 0; i < busIds.size(); i++) {
            BigDecimal busTiming = BigDecimal.valueOf(busIds.get(i));
            x = x.add(
                    BigDecimal.valueOf(timeDiffs.get(i))
                            .multiply(N.divide(busTiming, RoundingMode.FLOOR))
                            .multiply(ecld((N.divide(busTiming, RoundingMode.FLOOR)), busTiming)
                            )
            );
        }
        return N.subtract(x.remainder(N));
    }

    private BigDecimal ecld(BigDecimal n1, BigDecimal n2) {
        // Extended Euclidean
        BigDecimal result = BigDecimal.ZERO;
        if (!n1.equals(BigDecimal.ZERO)) {
            if (n2.remainder(n1).equals(BigDecimal.ZERO)) {
                result = BigDecimal.ONE;
            } else {
                result = n2.subtract(ecld(n2.remainder(n1), n1).multiply(n2).divide(n1, RoundingMode.FLOOR));
            }
        }
        if (VERBOSE) System.out.println("ecld " + n1 + " | " + n2 + " : " + result);
        return result;
    }


    public static void main(String[] args) {
        Level13 l = new Level13("input");
        System.out.println("Part1: " + l.p1());
        System.out.println("Part2: " + l.p2());
    }
}

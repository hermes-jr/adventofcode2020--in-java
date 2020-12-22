package level_14;

import common.Level;

import java.util.*;

public class Level14 extends Level {
    static final boolean VERBOSE = false;

    long p1(List<String> commands) {
        Map<Long, Long> memory = new HashMap<>();
        long orMask = 0L;
        long andMask = Long.MAX_VALUE;
        for (String c : commands) {
            String[] cSplit = c.split(" = ");
            String cmdLeft = cSplit[0];
            String cmdRight = cSplit[1];
            if ("mask".equals(cmdLeft)) {
                orMask = 0L;
                andMask = Long.MAX_VALUE;
                char[] maskAsChars = cmdRight.toCharArray();
                for (int i = 0; i < cmdRight.length(); i++) {
                    if (VERBOSE) System.out.println(orMask + " " + andMask);
                    long modBit = 1L << (long) i;
                    if (maskAsChars[35 - i] == '1') {
                        orMask |= modBit;
                    } else if (maskAsChars[35 - i] == '0') {
                        andMask &= ~modBit;
                    }
                }
            } else {
                long addr = Long.parseLong(cmdLeft.substring(4, cmdLeft.length() - 1));
                long val = Long.parseLong(cmdRight);

                if (VERBOSE) System.out.println("toup: " + val);
                val &= andMask;
                val |= orMask;
                if (VERBOSE) System.out.println("masked: " + val);
                memory.put(addr, val);
            }
        }

        return memory.values().stream().reduce(Long::sum).orElseThrow(RuntimeException::new);
    }

    long p2(List<String> commands) {

        Map<Long, Long> memory = new HashMap<>();
        long orMask = 0L;
        boolean[] floats = new boolean[0];
        for (String c : commands) {
            String[] cSplit = c.split(" = ");
            String cmdLeft = cSplit[0];
            String cmdRight = cSplit[1];
            if ("mask".equals(cmdLeft)) {
                floats = new boolean[36];
                char[] maskAsChars = cmdRight.toCharArray();
                orMask = 0L;
                for (int i = 0; i < cmdRight.length(); i++) {
                    long modBit = 1L << (long) i;
                    if (maskAsChars[35 - i] == '1') {
                        orMask |= modBit;
                    } else if (maskAsChars[35 - i] == 'X') {
                        floats[i] = true;
                    }
                }
            } else {
                long addr = Long.parseLong(cmdLeft.substring(4, cmdLeft.length() - 1));
                long val = Long.parseLong(cmdRight);

                addr |= orMask; // apply ones
                // apply floats:
                Set<Long> floatingAddresses = new HashSet<>();
                genAddresses(addr, floatingAddresses, floats, 0);
                if (VERBOSE) System.out.println(floatingAddresses);
                for (Long a : floatingAddresses) {
                    memory.put(a, val);
                }
            }
        }

        return memory.values().stream().mapToLong(Long::longValue).sum();
    }

    void genAddresses(long address, Set<Long> result, boolean[] floatingBits, int id) {
        for (; id < 36; id++) {

            long modBit = 1L << (long) id;

            if (floatingBits[id]) {
                long a1 = address;
                a1 |= modBit;
                genAddresses(a1, result, floatingBits, id + 1);
                result.add(a1);

                long a2 = address;
                a2 &= ~modBit;
                result.add(a2);
                genAddresses(a2, result, floatingBits, id + 1);
                break;
            }
        }
    }

    public static void main(String[] args) {
        Level14 l = new Level14();
        List<String> commands = l.readResources("input");
        System.out.println("Part1: " + l.p1(commands));
        System.out.println("Part2: " + l.p2(commands));
    }

}
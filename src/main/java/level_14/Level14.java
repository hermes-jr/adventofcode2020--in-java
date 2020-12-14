package level_14;

import common.Level;

import java.util.*;

public class Level14 extends Level {
    static final boolean VERBOSE = false;

    long p1(List<String> commands) {
        Map<Long, BitSet> memory = new HashMap<>();
        BitSet orMask = new BitSet(36);
        BitSet andMask = new BitSet(36);
        for (String c : commands) {
            String[] cSplit = c.split(" = ");
            String cmdLeft = cSplit[0];
            String cmdRight = cSplit[1];
            if ("mask".equals(cmdLeft)) {
                char[] maskAsChars = cmdRight.toCharArray();
                for (int i = 0; i < cmdRight.length(); i++) {
                    orMask.set(i, false);
                    andMask.set(i, true);
                    if (maskAsChars[35 - i] == '1') {
                        orMask.set(i, true);
                    } else if (maskAsChars[35 - i] == '0') {
                        andMask.set(i, false);
                    }
                }
            } else {
                long addr = Long.parseLong(cmdLeft.substring(4, cmdLeft.length() - 1));
                long val = Long.parseLong(cmdRight);

                BitSet toUpdate = BitSet.valueOf(new long[]{val});
                if (VERBOSE) System.out.println("toup: " + toUpdate);
                toUpdate.and(andMask);
                toUpdate.or(orMask);
                if (VERBOSE) System.out.println("masked: " + toUpdate.toLongArray()[0]);
                memory.put(addr, toUpdate);
            }
        }

        return memory.values().stream().mapToLong(z -> z.toLongArray()[0]).sum();
    }

    long p2(List<String> commands) {

        Map<Long, Long> memory = new HashMap<>();
        BitSet orMask = new BitSet(36);
        boolean[] floats = new boolean[0];
        for (String c : commands) {
            String[] cSplit = c.split(" = ");
            String cmdLeft = cSplit[0];
            String cmdRight = cSplit[1];
            if ("mask".equals(cmdLeft)) {
                floats = new boolean[36];
                char[] maskAsChars = cmdRight.toCharArray();
                for (int i = 0; i < cmdRight.length(); i++) {
                    orMask.set(i, false);
                    if (maskAsChars[35 - i] == '1') {
                        orMask.set(i, true);
                    } else if (maskAsChars[35 - i] == 'X') {
                        floats[i] = true;
                    }
                }
            } else {
                long addr = Long.parseLong(cmdLeft.substring(4, cmdLeft.length() - 1));
                long val = Long.parseLong(cmdRight);

                BitSet toUpdate = BitSet.valueOf(new long[]{addr});
                toUpdate.or(orMask); // apply ones
                // apply floats:
                Set<Long> floatingAddresses = new HashSet<>();
                genAddresses(toUpdate, floatingAddresses, floats, 0);
                if (VERBOSE) System.out.println(floatingAddresses);
                for (Long a : floatingAddresses) {
                    memory.put(a, val);
                }
            }
        }

        return memory.values().stream().mapToLong(Long::longValue).sum();
    }

    void genAddresses(BitSet address, Set<Long> result, boolean[] floatingBits, int id) {
        for (; id < 36; id++) {
            if (floatingBits[id]) {
                BitSet a1 = (BitSet) address.clone();
                a1.set(id, true);
                genAddresses(a1, result, floatingBits, id + 1);
                result.add(a1.toLongArray()[0]);

                BitSet a2 = (BitSet) address.clone();
                a2.set(id, false);
                result.add(a2.toLongArray()[0]);
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
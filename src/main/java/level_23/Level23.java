package level_23;

import common.Level;

public class Level23 extends Level {
    static final boolean VERBOSE = false;

    String p1(String seed, int turns) {
        CrabsRing ring = new CrabsRing();

        if (VERBOSE) System.out.println(ring);

        // test: 389125467
        for (String c : seed.split("")) {
            ring.add(new CrabsRing.Node(Integer.parseUnsignedInt(c)));
        }
        ring.head = ring.head.next; // Scroll back to first added number

        for (int turn = 1; turn <= turns; turn++) {
            if (VERBOSE) System.out.printf("-- move %d --%n", turn);
            Integer destination = ring.head.value;

            CrabsRing.Node pick1 = ring.pick(ring.head.next);
            CrabsRing.Node pick2 = ring.pick(ring.head.next);
            CrabsRing.Node pick3 = ring.pick(ring.head.next);
            if (VERBOSE) {
                System.out.printf("Pick up: %s, %s, %s%n", pick1, pick2, pick3);
                System.out.println(ring);
            }

            do {
                destination--;
                if (destination == 0) {
                    destination = 9;
                }
            } while (destination.equals(pick1.value) || destination.equals(pick2.value) || destination.equals(pick3.value));
            if (VERBOSE) System.out.println("Destination: " + destination);
            CrabsRing.Node tmpHead = ring.head;
            ring.add(pick1, destination);
            ring.add(pick2);
            ring.add(pick3);
            ring.head = tmpHead.next;
            if (VERBOSE) System.out.println("Move ends: " + ring + System.lineSeparator());
        }

        StringBuilder result = new StringBuilder();
        CrabsRing.Node n = ring.head;
        while (!n.value.equals(1)) {
            n = n.next;
        }
        n = n.next;
        while (!n.value.equals(1)) {
            result.append(n.value);
            n = n.next;
        }
        return result.toString();
    }

    String p2(String seed) {
        CrabsRing ring = new CrabsRing();

        if (VERBOSE) System.out.println(ring);

        for (String c : seed.split("")) {
            ring.add(new CrabsRing.Node(Integer.parseUnsignedInt(c)));
        }
        for (int i = 10; i <= 1_000_000; i++) {
            ring.add(new CrabsRing.Node(i));
        }
        ring.head = ring.head.next; // Scroll back to first added number

        for (int turn = 1; turn <= 10_000_000; turn++) {
            Integer destination = ring.head.value;

            CrabsRing.Node pick1 = ring.pick(ring.head.next);
            CrabsRing.Node pick2 = ring.pick(ring.head.next);
            CrabsRing.Node pick3 = ring.pick(ring.head.next);

            do {
                destination--;
                if (destination == 0) {
                    destination = 1_000_000;
                }
            } while (destination.equals(pick1.value) || destination.equals(pick2.value) || destination.equals(pick3.value));
            CrabsRing.Node tmpHead = ring.head;
            ring.add(pick1, destination);
            ring.add(pick2);
            ring.add(pick3);
            ring.head = tmpHead.next;
        }

        CrabsRing.Node n = ring.head;
        while (!n.value.equals(1)) {
            n = n.next;
        }
        return String.valueOf((long) n.next.value * (long) n.next.next.value);
    }

    public static void main(String[] args) {
        Level23 l = new Level23();
        String seed = "459672813";
        System.out.println("Part1: " + l.p1(seed, 100));
        System.out.println("Part2: " + l.p2(seed));
    }

}
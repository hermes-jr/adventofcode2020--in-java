package level_23;

import common.Level;

public class Level23 extends Level {
    static final boolean VERBOSE = false;

    private void play(int turns, CrabsRing ring) {
        for (int turn = 1; turn <= turns; turn++) {
            if (VERBOSE) System.out.printf("-- move %d --%n", turn);
            int destination = ring.head.value;

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
                    destination = ring.length + 3; // lol =_=
                }
            } while (destination == pick1.value || destination == pick2.value || destination == pick3.value);
            if (VERBOSE) System.out.println("Destination: " + destination);
            CrabsRing.Node tmpHead = ring.head;
            ring.add(pick1, destination);
            ring.add(pick2);
            ring.add(pick3);
            ring.head = tmpHead.next;
            if (VERBOSE) System.out.println("Move ends: " + ring + System.lineSeparator());
        }
    }

    String p1(String seed, int turns) {
        CrabsRing ring = new CrabsRing(10);

        if (VERBOSE) System.out.println(ring);

        // test: 389125467
        for (String c : seed.split("")) {
            ring.add(new CrabsRing.Node(Integer.parseUnsignedInt(c)));
        }
        ring.head = ring.head.next; // Scroll back to first added number

        play(turns, ring);

        StringBuilder result = new StringBuilder();
        CrabsRing.Node n = ring.lookup[1].next;
        for (int i = 0; i < 8; i++) {
            result.append(n.value);
            n = n.next;
        }
        return result.toString();
    }

    String p2(String seed) {
        CrabsRing ring = new CrabsRing(1_000_000);

        if (VERBOSE) System.out.println(ring);

        int maxSoFar = 0;
        for (String c : seed.split("")) {
            int cv = Integer.parseUnsignedInt(c);
            if (cv > maxSoFar) {
                maxSoFar = cv;
            }
            ring.add(new CrabsRing.Node(cv));
        }

        while (ring.length < 1_000_000) {
            ring.add(new CrabsRing.Node(++maxSoFar));
        }
        ring.head = ring.head.next;

        if (VERBOSE) System.out.println(ring);

        play(10_000_000, ring);

        CrabsRing.Node n = ring.lookup[1];

        return String.valueOf((long) n.next.value * (long) n.next.next.value);
    }

    public static void main(String[] args) {
        Level23 l = new Level23();
        String seed = "459672813";
        System.out.println("Part1: " + l.p1(seed, 100));
        System.out.println("Part2: " + l.p2(seed));
    }

}
package level_22;

import common.Level;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.*;

public class Level22 extends Level {
    static final boolean VERBOSE = false;
    List<String> input;
    static int maxGame = 1;

    public Level22(String filename) {
        input = readResources(filename);
    }

    ImmutablePair<Deque<Integer>, Deque<Integer>> deal() {
        final Deque<Integer> deck1 = new LinkedList<>();
        final Deque<Integer> deck2 = new LinkedList<>();

        int player = 1;
        for (int i = 1; i < input.size(); i++) {
            String line = input.get(i);
            if (StringUtils.isBlank(line)) {
                player++;
                i++;
                continue;
            }
            Deque<Integer> deck = player == 1 ? deck1 : deck2;
            deck.add(Integer.parseInt(line));
        }

        if (VERBOSE) System.out.println("Decs " + deck1 + " " + deck2);

        return ImmutablePair.of(deck1, deck2);
    }

    long p1() {
        ImmutablePair<Deque<Integer>, Deque<Integer>> deal = deal();
        Deque<Integer> deck1 = deal.getLeft();
        Deque<Integer> deck2 = deal.getRight();

        while (!deck1.isEmpty() && !deck2.isEmpty()) {
            // part1 round
            Integer c1 = deck1.poll();
            Integer c2 = deck2.poll();
            assert c1 != null && c2 != null;

            if (VERBOSE) System.out.println("Draw: " + c1 + " " + c2);
            if (c1.compareTo(c2) > 0) {
                deck1.addLast(c1);
                deck1.addLast(c2);
            } else {
                deck2.addLast(c2);
                deck2.addLast(c1);
            }
            if (VERBOSE) System.out.println("Decks " + deck1 + " " + deck2);
        }

        return getScore(deck1, deck2);
    }

    private long getScore(Deque<Integer> d1, Deque<Integer> d2) {
        Deque<Integer> winner = d2.isEmpty() ? new LinkedList<>(d1) : new LinkedList<>(d2);

        long result = 0;
        long mpl = 1;
        while (!winner.isEmpty()) {
            int card = winner.pollLast();
            if (VERBOSE) System.out.println(mpl + " * " + card);
            result += mpl * card;
            mpl++;
        }
        return result;
    }

    int game(Deque<Integer> deck1, Deque<Integer> deck2, int gameId) {
        if (VERBOSE) System.out.printf("%n=== Game %d ===%n", gameId);
        Set<ImmutablePair<Integer, Integer>> seenStates = new HashSet<>();
        int roundWinner;

        for (int roundId = 1; !deck1.isEmpty() && !deck2.isEmpty(); roundId++) {
            if (VERBOSE) {
                System.out.printf("-- Round %d (Game %d) --%n", roundId, gameId);
                System.out.println("Player 1's deck: " + deck1);
                System.out.println("Player 2's deck: " + deck2);
            }

            ImmutablePair<Integer, Integer> roundHash = ImmutablePair.of(deck1.hashCode(), deck2.hashCode());
            if (seenStates.contains(roundHash)) {
                return 1; // Loop detected, player 1 wins
            }
            seenStates.add(roundHash);

            Integer c1 = deck1.poll();
            Integer c2 = deck2.poll();
            assert c1 != null && c2 != null;

            if (VERBOSE) {
                System.out.println("Player 1 plays: " + c1);
                System.out.println("Player 2 plays: " + c2);
            }

            if (c1 <= deck1.size() && c2 <= deck2.size()) {
                Deque<Integer> newDeck1 = new LinkedList<>(deck1);
                int skip = newDeck1.size() - c1;
                while (skip-- > 0) {
                    newDeck1.pollLast();
                }
                Deque<Integer> newDeck2 = new LinkedList<>(deck2);
                skip = newDeck2.size() - c2;
                while (skip-- > 0) {
                    newDeck2.pollLast();
                }
                if (VERBOSE) System.out.println("Playing a sub-game to determine the winner...");

                roundWinner = game(newDeck1, newDeck2, maxGame++);
            } else {
                roundWinner = c1 > c2 ? 1 : 2;
            }

            if (VERBOSE) System.out.printf("Player %d wins round %d of game %d!%n%n", roundWinner, roundId, gameId);
            if (roundWinner == 1) {
                deck1.addLast(c1);
                deck1.addLast(c2);
            } else {
                deck2.addLast(c2);
                deck2.addLast(c1);
            }
        }

        if (deck1.isEmpty()) {
            return 2;
        } else {
            return 1;
        }
    }

    long p2() {
        ImmutablePair<Deque<Integer>, Deque<Integer>> deal = deal();
        Deque<Integer> deck1 = deal.getLeft();
        Deque<Integer> deck2 = deal.getRight();

        game(deck1, deck2, maxGame++);

        return getScore(deck1, deck2);
    }

    public static void main(String[] args) {
        Level22 l = new Level22("input");
        System.out.println("Part1: " + l.p1());
        System.out.println("Part2: " + l.p2());
    }

}
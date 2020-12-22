package level_22;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.jupiter.api.Test;

import java.util.Deque;

import static org.assertj.core.api.Assertions.assertThat;

class Level22Test {

    @Test
    void partOneExampleShouldWork() {
        Level22 l = new Level22("in1");
        assertThat(l.p1()).isEqualTo(306);
    }

    @Test
    void partTwoExampleShouldWork() {
        Level22 l = new Level22("in1");
        assertThat(l.p2()).isEqualTo(291);
    }

    @Test
    void infiniteGamesShouldBeAborted() {
        Level22 l = new Level22("in2");
        ImmutablePair<Deque<Integer>, Deque<Integer>> deal = l.deal();
        assertThat(l.game(deal.getLeft(), deal.getRight(), Level22.maxGame++)).isEqualTo(1);
    }

}
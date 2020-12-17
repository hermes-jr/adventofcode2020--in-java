package level_17;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Level17Test {

    @Test
    void partOneExampleShouldWork() {
        Level17 l = new Level17("in1");
        assertThat(l.p1()).isEqualTo(112);
    }

    @Test
    void partTwoExampleShouldWork() {
        Level17 l = new Level17("in1");
        assertThat(l.p2()).isEqualTo(848);
    }

}
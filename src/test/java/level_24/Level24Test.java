package level_24;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Level24Test {

    @Test
    void partOneExampleShouldWork() {
        Level24 l = new Level24("in1");
        assertThat(l.p1()).isEqualTo(10);
    }

    @Test
    void partTwoExampleShouldWork() {
        Level24 l = new Level24("in1");
        assertThat(l.p2()).isEqualTo(2208);
    }

}
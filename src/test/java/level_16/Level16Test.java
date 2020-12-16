package level_16;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Level16Test {

    @Test
    void partOneExampleShouldWork() {
        Level16 l = new Level16("in1");
        assertThat(l.p1()).isEqualTo(71);
    }

    @Test
    void partTwoTrigger() {
        Level16 l = new Level16("in1");
        l.p1(); // populate defective tickets
        assertThat(l.p2()).isNotEqualTo(-1);
    }

}
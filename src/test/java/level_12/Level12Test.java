package level_12;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Level12Test {

    @Test
    void partOneExampleShouldWork() {
        Level12 l = new Level12();
        assertThat(l.p1("in1")).isEqualTo(25);
    }

    @Test
    void partTwoExampleShouldWork() {
        Level12 l = new Level12();
        assertThat(l.p2("in1")).isEqualTo(286);
    }


}
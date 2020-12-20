package level_20;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Level20Test {

    @Test
    void partOneExampleShouldWork() {
        Level20 l = new Level20("in1");
        assertThat(l.p1()).isEqualTo(20899048083289L);
    }

    @Test
    void partTwoExampleShouldWork() {
        Level20 l = new Level20("in1");
        assertThat(l.p2()).isEqualTo(273);
    }

}
package level_14;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Level14Test {

    @Test
    void partOneExampleShouldWork() {
        Level14 l = new Level14();
        assertThat(l.p1(l.readResources("in1"))).isEqualTo(165L);
    }

    @Test
    void partTwoExampleShouldWork() {
        Level14 l = new Level14();
        assertThat(l.p2(l.readResources("in2"))).isEqualTo(208L);
    }

}
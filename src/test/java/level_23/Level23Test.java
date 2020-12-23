package level_23;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Level23Test {
    static Level23 l;

    @BeforeAll
    static void prepare() {
        l = new Level23();
    }

    @Test
    void partOneExamplesShouldWork() {
        String seed = "389125467";
        assertThat(l.p1(seed, 10)).isEqualTo("92658374");
        assertThat(l.p1(seed, 100)).isEqualTo("67384529");
    }

    @Test
    void partTwoExampleShouldWork() {
        String seed = "389125467";
        assertThat(l.p2(seed)).isEqualTo("149245887792");
    }

}
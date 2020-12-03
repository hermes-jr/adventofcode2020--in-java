package level_03;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Level03Test {
    static Level03 l;

    @BeforeAll
    static void before() {
        l = new Level03("input");
    }

    @Test
    void partOneFormulaShouldWork() {
        assertThat(l.p1(3, 1)).isEqualTo(7);
    }

    @Test
    void partTwoFormulaShouldWork() {
        assertThat(l.p2()).isEqualTo(336);
    }

}
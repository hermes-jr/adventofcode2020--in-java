package level_25;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Level25Test {

    @Test
    void partOneExampleShouldWork() {
        Level25 l = new Level25();
        assertThat(l.bruteLoopSize(5764801)).isEqualTo(8);
        assertThat(l.bruteLoopSize(17807724)).isEqualTo(11);
        assertThat(l.p1(5764801, 17807724)).isEqualTo(14897079);
    }

}
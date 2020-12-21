package level_21;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Level21Test {

    @Test
    void partOneExampleShouldWork() {
        Level21 l = new Level21("in1");
        assertThat(l.p1()).isEqualTo(5);
    }

    @Test
    void partTwoExampleShouldWork() {
        Level21 l = new Level21("in1");
        assertThat(l.p2()).isEqualTo("mxmxvkd,sqjhc,fvjkl");
    }

}
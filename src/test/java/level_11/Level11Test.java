package level_11;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Level11Test {

    @Test
    void placesCalculation() {
        Level11 l = new Level11("in2");
        assertThat(l.countOccupiedSeats()).isEqualTo(37);
    }

    @Test
    void givenExampleShouldWork() {
        Level11 l = new Level11("in1");
        assertThat(l.p1()).isEqualTo(37);
    }

    @Test
    void givenExampleForPartTroShouldWork() {
        Level11 l = new Level11("in1");
        assertThat(l.p2()).isEqualTo(26);
    }

}
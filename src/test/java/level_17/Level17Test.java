package level_17;

import level_17.Level17;
import org.junit.jupiter.api.Test;

class Level17Test {

    @Test
    void partOneExampleShouldWork() {
        Level17 l = new Level17("in1");
        assertThat(l.p1(6)).isEqualTo(112);
    }

}
package level_09;

import common.Level;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class Level09Test extends Level {

    @Test
    void firstPartExample() {
        Level09 l = new Level09();
        List<Long> l1 = l.readResourcesAsInts("in1");
        assertThat(l.p1(l1, 5)).isEqualTo(127);
    }

    @Test
    void secondPartExample() {
        Level09 l = new Level09();
        List<Long> l1 = l.readResourcesAsInts("in1");
        assertThat(l.p2(l1, 127L)).isEqualTo(62L);
    }

}
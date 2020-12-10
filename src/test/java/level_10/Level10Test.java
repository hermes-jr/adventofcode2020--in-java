package level_10;

import common.Level;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class Level10Test extends Level {

    @Test
    void firstPartExample() {
        Level10 l = new Level10();
        List<Integer> l1 = l.readResourcesAsInts("in1");
        assertThat(l.p1(l1)).isEqualTo(35);

        List<Integer> l2 = l.readResourcesAsInts("in2");
        assertThat(l.p1(l2)).isEqualTo(220);
    }

    @Test
    void secondPartFirstExample() {
        Level10 l = new Level10();
        List<Integer> l1 = l.readResourcesAsInts("in1");
        assertThat(l.p2(l1)).isEqualTo(8);
    }

    @Test
    void secondPartSecondExample() {
        Level10 l = new Level10();
        List<Integer> l2 = l.readResourcesAsInts("in2");
        assertThat(l.p2(l2)).isEqualTo(19208L);
    }

}
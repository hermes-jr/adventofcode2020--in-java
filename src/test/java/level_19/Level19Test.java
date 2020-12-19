package level_19;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Level19Test {

    @Test
    void regexConverterWorks() {
        Level19 l = new Level19("in1");
        String asRegex = l.rulesAsRegex();
        assertThat(asRegex).isEqualTo("^a(?:(?:aa|bb)(?:ab|ba)|(?:ab|ba)(?:aa|bb))b$");
    }

    @Test
    void partOneExamplesShouldWork() {
        Level19 l = new Level19("in1");
        assertThat(l.p1()).isEqualTo(2);
    }

    @Test
    void partTwoExamplesShouldWork() {
        Level19 l = new Level19("in2");
        assertThat(l.p2()).isEqualTo(12);
    }

}
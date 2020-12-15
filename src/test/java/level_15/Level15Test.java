package level_15;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class Level15Test {

    @ParameterizedTest(name = "Part one examples should work")
    @CsvSource(value = {
            "0,3,6;436",
            "1,3,2;1",
            "2,1,3;10",
            "1,2,3;27",
            "2,3,1;78",
            "3,2,1;438",
            "3,1,2;1836"
    }, delimiter = ';')
    void partOneExamplesShouldWork(String starting, int result) {
        Level15 l = new Level15();
        assertThat(l.p1(starting, 2020)).isEqualTo(result);
    }

    @Test
    void partOneFastExample() {
        Level15 l = new Level15();
        assertThat(l.p1("0,3,6", 2020)).isEqualTo(436);
    }

    @ParameterizedTest(name = "Part two examples should work")
    @CsvSource(value = {
            "0,3,6;175594",
            "1,3,2;2578",
            "2,1,3;3544142",
            "1,2,3;261214",
            "2,3,1;6895259",
            "3,2,1;18",
            "3,1,2;362",
    }, delimiter = ';')
    void partTwoExamplesShouldWork(String starting, int result) {
        Level15 l = new Level15();
        assertThat(l.p1(starting, 30_000_000)).isEqualTo(result);
    }

}
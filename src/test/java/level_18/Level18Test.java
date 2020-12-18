package level_18;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class Level18Test {

    @ParameterizedTest(name = "Part one examples should work")
    @CsvSource(value = {
            "1 + 2 * 3 + 4 * 5 + 6;71",
            "1 + (2 * 3) + (4 * (5 + 6));51",
            "2 * 3 + (4 * 5);26",
            "5 + (8 * 3 + 9 + 3 * 4 * 3);437",
            "5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4));12240",
            "((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2;13632",
    }, delimiter = ';')
    void partOneExamplesShouldWork(String expression, long result) {
        Level18 l = new Level18();
        assertThat(l.eval(expression, true)).isEqualTo(result);
    }

    @ParameterizedTest(name = "Part two examples should work")
    @CsvSource(value = {
            "1 + 2 * 3 + 4 * 5 + 6;231",
            "1 + (2 * 3) + (4 * (5 + 6));51",
            "2 * 3 + (4 * 5);46",
            "5 + (8 * 3 + 9 + 3 * 4 * 3);1445",
            "5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4));669060",
            "((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2;23340",
    }, delimiter = ';')
    void partTwoExamplesShouldWork(String expression, long result) {
        Level18 l = new Level18();
        assertThat(l.eval(expression, false)).isEqualTo(result);
    }
}
package level_13;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class Level13Test {

    @Test
    void partOneExampleShouldWork() {
        Level13 l = new Level13("in1");
        assertThat(l.p1()).isEqualTo(295L);
    }

    @ParameterizedTest(name = "Part two examples should work")
    @CsvSource(value = {
            "in1;1068781",
            "in2;3417",
            "in3;754018",
            "in4;779210",
            "in5;1261476",
            "in6;1202161486"
    }, delimiter = ';')
    void partTwoExamplesShouldWork(String fileName, long result) {
        Level13 l = new Level13(fileName);
        assertThat(l.p2()).isEqualTo(BigDecimal.valueOf(result));
    }


}
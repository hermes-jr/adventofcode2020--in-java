package level_04;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Level04Test {
    static Level04 l;

    @BeforeAll
    static void before() {
        l = new Level04();
    }

    @Test
    void inputCleanupStringsMustBeGluedProperly() {
        assertThat(l.getCleanedUpInput("in1")).hasSize(4);
    }

    @Test
    void part1LogicShouldWork() {
        List<String> inClean = l.getCleanedUpInput("in1");
        assertThat(inClean).hasSize(4);
        assertThat(inClean.get(0)).endsWith(" ");
        assertThat(inClean.get(inClean.size() - 1)).endsWith(" ");
    }


    @ParameterizedTest(name = "Passport validation works as expected")
    @CsvSource(value = {
            "ecl:gry pid:860033327 eyr:2020 hcl:#fffffd byr:1937 iyr:2017 cid:147 hgt:183cm ;true",
            "iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884 hcl:#cfa07d byr:1929 ;false",
            "hcl:#ae17e1 iyr:2013 eyr:2024 ecl:brn pid:760753108 byr:1931 hgt:179cm ;true",
            "hcl:#cfa07d eyr:2025 pid:166559648 iyr:2011 ecl:brn hgt:59in ;false"
    }, delimiter = ';')
    void testFirstPart(String s, boolean result) {
        Passport data = new Passport(s);
        assertThat(data.areAllFieldsPresent()).isEqualTo(result);
    }

    @Test
    void firstPartTestCase() {
        assertThat(l.p1(l.getCleanedUpInput("in1"))).isEqualTo(2);
    }

    @Test
    void secondPartInvalidPassports() {
        assertThat(l.p2(l.getCleanedUpInput("in2"))).isEqualTo(0);
    }

    @Test
    void secondPartValidPassports() {
        assertThat(l.p2(l.getCleanedUpInput("in3"))).isEqualTo(4);
    }

}
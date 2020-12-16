package level_04;

import lombok.Data;
import lombok.ToString;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@ToString
class Passport {
    private String byr;
    private String iyr;
    private String eyr;
    private String hgt;
    private String hcl;
    private String ecl;
    private String pid;
    private String cid;

    public Passport(String s) {
        Matcher m;
        m = Pattern.compile(".*byr:([^\\s]+).*").matcher(s);
        if (m.matches()) {
            byr = m.group(1);
        }

        m = Pattern.compile(".*iyr:([^\\s]+).*").matcher(s);
        if (m.matches()) {
            iyr = m.group(1);
        }

        m = Pattern.compile(".*eyr:([^\\s]+).*").matcher(s);
        if (m.matches()) {
            eyr = m.group(1);
        }
        m = Pattern.compile(".*hgt:([^\\s]+).*").matcher(s);
        if (m.matches()) {
            hgt = m.group(1);
        }
        m = Pattern.compile(".*hcl:([^\\s]+).*").matcher(s);
        if (m.matches()) {
            hcl = m.group(1);
        }
        m = Pattern.compile(".*ecl:([^\\s]+).*").matcher(s);
        if (m.matches()) {
            ecl = m.group(1);
        }
        m = Pattern.compile(".*pid:([^\\s]+).*").matcher(s);
        if (m.matches()) {
            pid = m.group(1);
        }
        m = Pattern.compile(".*cid:([^\\s]+).*").matcher(s);
        if (m.matches()) {
            cid = m.group(1);
        }

    }

    boolean areAllFieldsPresent() {
        boolean r = byr != null && iyr != null && eyr != null && hgt != null && hcl != null && ecl != null && pid != null;
        if (Level04.VERBOSE) System.out.println(this + ": " + r);
        return r;
    }

    boolean isValid() {
        // Massive null-pointer check, no need to verify later
        if (!areAllFieldsPresent()) {
            return false;
        }

        if (isBadYear(byr, 1920, 2002)) {
            if (Level04.VERBOSE) System.out.println("Bad byr: " + byr);
            return false;
        }
        if (isBadYear(iyr, 2010, 2020)) {
            if (Level04.VERBOSE) System.out.println("Bad iyr: " + iyr);
            return false;
        }
        if (isBadYear(eyr, 2020, 2030)) {
            if (Level04.VERBOSE) System.out.println("Bad eyr: " + eyr);
            return false;
        }

        if (!(hgt.endsWith("cm") || hgt.endsWith("in"))) {
            if (Level04.VERBOSE) System.out.println("Bad height: " + hgt);
            return false;
        }
        int h = Integer.parseInt(hgt.substring(0, hgt.length() - 2));
        if (hgt.endsWith("cm") && (h < 150 || h > 193)) {
            if (Level04.VERBOSE) System.out.println("Bad height range: " + hgt);
            return false;
        } else if (hgt.endsWith("in") && (h < 59 || h > 76)) {
            if (Level04.VERBOSE) System.out.println("Bad height range: " + hgt);
            return false;
        }

        if (!hcl.matches("^#[0-9a-f]{6}$")) {
            if (Level04.VERBOSE) System.out.println("Bad color: " + hcl);
            return false;
        }

        Set<String> validEyes = new HashSet<>(Arrays.asList("amb", "blu", "brn", "gry", "grn", "hzl", "oth"));
        if (!validEyes.contains(ecl)) {
            if (Level04.VERBOSE) System.out.println("Bad ecl: " + ecl);
            return false;
        }

        if (!pid.matches("^[0-9]{9}$")) {
            if (Level04.VERBOSE) System.out.println("Bad pid: " + pid);
            return false;
        }

        return true;
    }

    boolean isBadYear(String yr, int min, int max) {
        if (yr.matches("[0-9]{4}")) {
            int parsed = Integer.parseInt(yr);
            return parsed < min || parsed > max;
        }
        return true;
    }
}

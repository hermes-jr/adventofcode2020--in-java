package level_04;

import common.Level;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Level04 extends Level {
    private static final boolean VERBOSE = false;

    public Level04() {
        List<String> rawPassports = getCleanedUpInput("input");
        System.out.println("Result1: " + p1(rawPassports));
        System.out.println("Result2: " + p2(rawPassports));
    }

    int p1(List<String> rawPassports) {
        int result = 0;
        for (String s : rawPassports) {
            Passport p = new Passport(s);
            if (p.areAllFieldsPresent()) {
                result++;
            }
        }
        return result;
    }

    int p2(List<String> rawPassports) {
        int result = 0;
        for (String s : rawPassports) {
            Passport p = new Passport(s);
            if (p.areAllFieldsPresent() && p.isValid()) {
                result++;
            }
        }
        return result;
    }

    List<String> getCleanedUpInput(String filename) {
        List<String> result = new ArrayList<>();
        StringBuilder nextLine = new StringBuilder();
        List<String> fileContents = readResources(filename);
        for (int i = 0; i < fileContents.size(); i++) {
            String l = fileContents.get(i);
            nextLine.append(l).append(" ");
            if (StringUtils.isBlank(l) || i == fileContents.size() - 1) {
                result.add(nextLine.toString());
                nextLine = new StringBuilder();
            }
        }
        return result;
    }


    public static void main(String[] args) {
        new Level04();
    }

    @Data
    @ToString
    static class Passport {
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
            if (VERBOSE) System.out.println(this + ": " + r);
            return r;
        }

        boolean isValid() {
            // Massive null-pointer check, no need to verify later
            if (!areAllFieldsPresent()) {
                return false;
            }

            if (isBadYear(byr, 1920, 2002)) {
                if (VERBOSE) System.out.println("Bad byr: " + byr);
                return false;
            }
            if (isBadYear(iyr, 2010, 2020)) {
                if (VERBOSE) System.out.println("Bad iyr: " + iyr);
                return false;
            }
            if (isBadYear(eyr, 2020, 2030)) {
                if (VERBOSE) System.out.println("Bad eyr: " + eyr);
                return false;
            }

            if (!(hgt.endsWith("cm") || hgt.endsWith("in"))) {
                if (VERBOSE) System.out.println("Bad height: " + hgt);
                return false;
            }
            int h = Integer.parseInt(hgt.substring(0, hgt.length() - 2));
            if (hgt.endsWith("cm") && (h < 150 || h > 193)) {
                if (VERBOSE) System.out.println("Bad height range: " + hgt);
                return false;
            } else if (hgt.endsWith("in") && (h < 59 || h > 76)) {
                if (VERBOSE) System.out.println("Bad height range: " + hgt);
                return false;
            }

            if (!hcl.matches("^#[0-9a-f]{6}$")) {
                if (VERBOSE) System.out.println("Bad color: " + hcl);
                return false;
            }

            Set<String> validEyes = new HashSet<>(Arrays.asList("amb", "blu", "brn", "gry", "grn", "hzl", "oth"));
            if (!validEyes.contains(ecl)) {
                if (VERBOSE) System.out.println("Bad ecl: " + ecl);
                return false;
            }

            if (!pid.matches("^[0-9]{9}$")) {
                if (VERBOSE) System.out.println("Bad pid: " + pid);
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
}

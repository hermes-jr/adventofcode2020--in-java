package level_02;

import common.Level;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Level02 extends Level {

    public Level02(String filename) {
        List<String> data = readResources(filename);
        Pattern mp = Pattern.compile("^([0-9]+)-([0-9]+) (\\p{L}): (.*)$");

        int result1 = 0;
        int result2 = 0;
        for (String line : data) {
            Matcher inMatcher = mp.matcher(line);
            if (!inMatcher.matches()) {
                throw new RuntimeException("Unable to parse input: " + line);
            }
            int min = Integer.parseInt(inMatcher.group(1));
            int max = Integer.parseInt(inMatcher.group(2));
            char tgt = inMatcher.group(3).charAt(0);
            char[] pass = inMatcher.group(4).toCharArray();

            int cCount = 0;
            for (char c : pass) {
                if (c == tgt) {
                    cCount++;
                }
            }
            if (cCount >= min && cCount <= max) {
                result1++;
            }

            if (pass[min - 1] == tgt ^ pass[max - 1] == tgt) {
                result2++;
            }
        }

        System.out.println("Part1: " + result1);
        System.out.println("Part2: " + result2);

    }

    public static void main(String[] args) {
        new Level02("input");
    }
}

package level_06;

import common.Level;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Level06 extends Level {
    private static final boolean VERBOSE = false;

    public Level06() {
        List<String> answers = readResources("input");
        int result1 = 0;
        int result2 = 0;
        int gsize = 0;
        Map<Character, Integer> groupAnswers = new HashMap<>();
        for (String a : answers) {
            if (StringUtils.isBlank(a)) {
                result1 += groupAnswers.size();
                for (Integer count : groupAnswers.values()) {
                    if (count == gsize) {
                        result2++;
                    }
                }
                groupAnswers = new HashMap<>();
                gsize = 0;
                continue;
            }

            gsize++;
            for (Character c : a.toCharArray()) {
                groupAnswers.put(c, groupAnswers.getOrDefault(c, 0) + 1);
            }
        }

        result1 += groupAnswers.size();
        for (Integer count : groupAnswers.values()) {
            if (count == gsize) {
                result2++;
            }
        }

        System.out.println("Part1: " + result1);
        System.out.println("Part2: " + result2);

    }

    public static void main(String[] args) {
        new Level06();
    }

}

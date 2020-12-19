package level_19;

import common.Level;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Level19 extends Level {
    static final boolean VERBOSE = false;
    Map<String, String> rules;
    List<String> lines;

    public Level19(String filename) {
        parseRules(filename);
        boolean rules = true;

        lines = new ArrayList<>();
        List<String> inLines = readResources(filename);
        for (String line : inLines) {
            if (!rules) {
                lines.add(line);
            }
            if (StringUtils.isBlank(line)) {
                rules = false; // rules section ends here
            }
        }
    }

    void parseRules(String filename) {
        rules = new HashMap<>();
        List<String> input = readResources(filename);
        for (String line : input) {
            if (StringUtils.isBlank(line)) {
                break;
            }
            String[] tokens = line.split(": ");
            String ruleNum = tokens[0];
            line = tokens[1];
            if (line.contains("|")) {
                line = "(?: " + line + " )";
            } else if ("\"a\"".equals(line) || "\"b\"".equals(line)) {
                line = line.substring(1, 2);
            }
            rules.put(ruleNum, line);
        }
    }

    int p1() {
        Pattern p = Pattern.compile(rulesAsRegex());
        int result = 0;
        for (String line : lines) {
            if (p.matcher(line).matches()) {
                result++;
            }
        }
        return result;
    }

    /*
    Seems like there's no support for recursive \g group references in this regex engine,
    so I just generated the regex and ran in here:
    https://regex101.com/r/0gFvnL/1
     */
    int p2() {
        rules.put("8", " 42 +");
        rules.put("11", "(?<yyy> 42 (\\g<yyy>)? 31 )");
        String regex = rulesAsRegex();
        if (VERBOSE) System.out.println(regex);
        return -1;
/*
        Pattern p = Pattern.compile(regex);
        int result = 0;
        for (String line : lines) {
            if (p.matcher(line).matches()) {
                result++;
            }
        }
        return result;
*/
    }

    public static void main(String[] args) {
        Level19 l = new Level19("input");
        System.out.println("Part1: " + l.p1());
        System.out.println("Part2: " + l.p2());
    }

    private String dig(String in) {
        if ("a".equals(in) || "b".equals(in)) {
            return in;
        }
        StringBuilder sb = new StringBuilder("");
        for (String token : in.split(" ")) {
            if (StringUtils.isNumeric(token)) {
                sb.append(dig(rules.get(token)));
            } else {
                sb.append(token);
            }
        }
        return sb.toString();
    }

    public String rulesAsRegex() {
        String result = "^" + dig(rules.get("0")).replaceAll("\\s+", "") + "$";
        if (VERBOSE) System.out.println(result);
        return result;
    }
}
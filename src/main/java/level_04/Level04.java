package level_04;

import common.Level;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Level04 extends Level {
    static final boolean VERBOSE = false;

    public Level04() {
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
        Level04 l = new Level04();
        List<String> rawPassports = l.getCleanedUpInput("input");
        System.out.println("Part1: " + l.p1(rawPassports));
        System.out.println("Part2: " + l.p2(rawPassports));
    }

}

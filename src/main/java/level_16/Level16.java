package level_16;

import common.Level;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class Level16 extends Level {
    static final boolean VERBOSE = false;
    int[][] tickets;
    int[] own;
    List<Field> fields;
    Set<Integer> discarded = new HashSet<>();

    public Level16(String input) {
        List<String> in = readResources(input);
        fields = new ArrayList<>();

        int i = 0;
        for (; !StringUtils.isBlank(in.get(i)); i++) {
            fields.add(new Field(in.get(i)));
        }
        i += 2; // 'your ticket'
        own = Arrays.stream(in.get(i).split(",")).mapToInt(Integer::parseInt).toArray();
        i += 3; // 'nearby tickets'
        tickets = new int[in.size() - i][fields.size()];
        for (int j = 0; i < in.size(); i++, j++) {
            tickets[j] = Arrays.stream(in.get(i).split(",")).mapToInt(Integer::parseInt).toArray();
        }

        if (VERBOSE) System.out.println(fields);
        if (VERBOSE) System.out.println(Arrays.toString(own));
        if (VERBOSE) System.out.println(Arrays.deepToString(tickets));
    }

    int p1() {
        int errorRate = 0;
        for (int i = 0; i < tickets.length; i++) {
            int[] ticket = tickets[i];
            val:
            for (int j = 0; j < tickets[0].length; j++) {
                for (Field f : fields) {
                    if (VERBOSE) System.out.printf("%4d. Validating %4d against %s%n", i, ticket[j],

                            f.toString());
                    if (f.validate(ticket[j])) {
                        continue val;
                    }
                }
                errorRate += ticket[j];
                discarded.add(i);
            }
        }
        return errorRate;
    }

    long p2() {
        List<List<Field>> puzzleFields = new ArrayList<>();
        for (int i = 0; i < fields.size(); i++) {
            puzzleFields.add(new ArrayList<>(fields));
        }

        for (int i = 0; i < puzzleFields.size(); i++) {
            ListIterator<Field> validatorsIterator = puzzleFields.get(i).listIterator();
            while (validatorsIterator.hasNext()) {
                Field fv = validatorsIterator.next();
                for (int j = 0; j < tickets.length; j++) {
                    if (discarded.contains(j)) {
                        continue; // ignore broken passwords
                    }
                    if (!fv.validate(tickets[j][i])) {
                        validatorsIterator.remove();
                    }
                }
            }
        }

        for (boolean allClean = false; !allClean; ) {
            allClean = true;
            for (int i = 0; i < puzzleFields.size(); i++) {
                List<Field> cpf = puzzleFields.get(i);
                if (cpf.size() == 1) {
                    for (int j = 0; j < puzzleFields.size(); j++) {
                        if (i == j) {
                            continue;
                        }
                        puzzleFields.get(j).removeIf(z -> z.getName().equals(cpf.get(0).getName()));
                    }
                } else {
                    allClean = false;
                }
            }
        }

        if (VERBOSE) System.out.println("Filtered: " + puzzleFields);

        long result = 1;
        for (int i = 0; i < puzzleFields.size(); i++) {
            String fieldName = puzzleFields.get(i).get(0).getName();
            if (VERBOSE) System.out.printf("%s: %d, ", fieldName, own[i]);
            if (fieldName.contains("departure")) {
                result *= own[i];
            }
        }
        if (VERBOSE) System.out.println();
        return result;
    }

    public static void main(String[] args) {
        Level16 l = new Level16("input");
        System.out.println("Part1: " + l.p1());
        System.out.println("Part2: " + l.p2());
    }

}
package level_08;

import common.Level;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Level08 extends Level {
    ProgResult p1(List<String> program) {
        int ip = 0;
        int acc = 0;
        Set<Integer> seen = new HashSet<>();

        while (true) {
            if (ip >= program.size()) {
                return new ProgResult(acc, false);
            }
            String line = program.get(ip);
            String[] spl = line.split(" ");
            String cmd = spl[0];
            int arg = Integer.parseInt(spl[1]);

            if (seen.contains(ip)) {
                break;
            } else {
                seen.add(ip);
            }
            switch (cmd) {
                case "acc":
                    acc += arg;
                    ip++;
                    break;
                case "jmp":
                    ip += arg;
                    break;
                case "nop":
                default:
                    ip++;
            }
        }
        return new ProgResult(acc, true);
    }

    private int p2(List<String> in) {
        for (int i = 0; i < in.size(); i++) {
            if (in.get(i).startsWith("acc")) {
                continue;
            }
            List<String> modProg = new ArrayList<>(in);
            String removed = modProg.remove(i);
            if (removed.startsWith("nop")) {
                removed = removed.replace("nop", "jmp");
            } else {
                removed = removed.replace("jmp", "nop");
            }
            modProg.add(i, removed);
            ProgResult pr = p1(modProg);
            if (!pr.infinite) {
                return pr.acc;
            }
        }
        return -1;
    }


    public static void main(String[] args) {
        Level08 l = new Level08();
        List<String> in = l.readResources("input");
        System.out.println("Part1: " + l.p1(in).acc);
        System.out.println("Part2: " + l.p2(in));
        l.p2(in);
    }

}

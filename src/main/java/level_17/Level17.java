package level_17;

import common.Level;
import common.Point3D;
import common.Point4D;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Level17 extends Level {
    Set<Point3D> map = new HashSet<>();
    Set<Point4D> map2 = new HashSet<>();

    public Level17(String input) {
        List<String> in = readResources(input);
        for (int y = 0; y < in.size(); y++) {
            char[] line = in.get(y).toCharArray();
            for (int x = 0; x < line.length; x++) {
                if (line[x] == '#') {
                    map.add(new Point3D(x, y, 0));
                    map2.add(new Point4D(x, y, 0, 0));
                }
            }
        }
//        System.out.println(map);
    }

    int p1() {
        int steps = 6;
        while (steps-- > 0) {
            Set<Point3D> nextStep = new HashSet<>();
            Set<Point3D> toExamine = new HashSet<>();
            for (Point3D p : map) {
                toExamine.add(p);
                toExamine.addAll(getAllNeighbors(p));
            }

            for (Point3D p : toExamine) {
                Set<Point3D> liveNbrs = getAllNeighbors(p);
                liveNbrs.retainAll(map);
                int alive = liveNbrs.size();
                if (map.contains(p) /* active */) {
                    if (alive == 3 || alive == 2) {
                        nextStep.add(p);
                    }
                } else {
                    if (alive == 3) {
                        nextStep.add(p);
                    }
                }
            }
            map = nextStep;
        }
        return map.size();
    }

    int p2() {
        int steps = 6;
        while (steps-- > 0) {
            Set<Point4D> nextStep = new HashSet<>();
            Set<Point4D> toExamine = new HashSet<>();
            for (Point4D p : map2) {
                toExamine.add(p);
                toExamine.addAll(getAllNeighbors(p));
            }

            for (Point4D p : toExamine) {
                Set<Point4D> liveNbrs = getAllNeighbors(p);
                liveNbrs.retainAll(map2);
                int alive = liveNbrs.size();
                if (map2.contains(p) /* active */) {
                    if (alive == 3 || alive == 2) {
                        nextStep.add(p);
                    }
                } else {
                    if (alive == 3) {
                        nextStep.add(p);
                    }
                }
            }
            map2 = nextStep;
        }
        return map2.size();
    }

    private Set<Point3D> getAllNeighbors(Point3D p) {
        Set<Point3D> result = new HashSet<>(27, 1.0f);
        for (int dx = -1; dx < 2; dx++) {
            for (int dy = -1; dy < 2; dy++) {
                for (int dz = -1; dz < 2; dz++) {
                    if (dx == 0 && dy == 0 && dz == 0) {
                        continue;
                    }
                    result.add(new Point3D(p.getX() + dx, p.getY() + dy, p.getZ() + dz));
                }
            }
        }
        return result;
    }

    private Set<Point4D> getAllNeighbors(Point4D p) {
        Set<Point4D> result = new HashSet<>(81, 1.0f);
        for (int dx = -1; dx < 2; dx++) {
            for (int dy = -1; dy < 2; dy++) {
                for (int dz = -1; dz < 2; dz++) {
                    for (int dw = -1; dw < 2; dw++) {
                        if (dx == 0 && dy == 0 && dz == 0 && dw == 0) {
                            continue;
                        }
                        result.add(new Point4D(p.getX() + dx, p.getY() + dy, p.getZ() + dz, p.getW() + dw));
                    }
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Level17 l = new Level17("input");
        System.out.println("Part1: " + l.p1());
        System.out.println("Part2: " + l.p2());
    }

}
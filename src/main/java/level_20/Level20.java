package level_20;

import common.Level;
import common.Point2D;
import org.apache.commons.lang3.ArrayUtils;

import java.util.*;
import java.util.stream.Collectors;

public class Level20 extends Level {
    static final boolean VERBOSE = false;
    static final int TILE_SIDE = 10;
    List<Tile> knownTiles = new ArrayList<>();
    Map<Integer, Set<Tile>> connectivity;

    public Level20(String filename) {
        connectivity = new HashMap<>();

        List<String> lines = readResources(filename);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i++);
            int tileId = Integer.parseInt(line.replaceAll("[^\\p{N}]", ""));

            Tile t = new Tile(tileId);
            for (int j = 0; j < TILE_SIDE; j++, i++) {
                line = lines.get(i);
                t.getPixels()[j] = line.toCharArray();
            }
            t.refreshEdgesCache();
            knownTiles.add(t);
        }

        if (VERBOSE) System.out.println(knownTiles);
    }

    void calculateConnectivity() {
        for (int i = 0; i < knownTiles.size() - 1; i++) {
            jIter:
            for (int j = i + 1; j < knownTiles.size(); j++) {
                Tile t1 = knownTiles.get(i);
                Tile t2 = knownTiles.get(j);
                for (int tMode = 0; tMode < t1.edgesCompat.length; tMode++) {
                    char[] ec = t1.edgesCompat[tMode];
                    int compatMode = t2.isCompatible(ec);
                    if (compatMode != -1) {
                        if (VERBOSE) {
                            System.out.printf("%d in mode %d is compatible with %d in orientation %d%n",
                                    t1.tileId, tMode, t2.tileId, compatMode);
                        }
                        Set<Tile> conn1 = connectivity.getOrDefault(t1.tileId, new HashSet<>());
                        conn1.add(t2);
                        Set<Tile> conn2 = connectivity.getOrDefault(t2.tileId, new HashSet<>());
                        conn2.add(t1);
                        connectivity.put(t1.tileId, conn1);
                        connectivity.put(t2.tileId, conn2);

                        continue jIter;
                    }
                }
            }
        }

        if (VERBOSE) {
            System.out.println("Connectivity: ");
            for (Map.Entry<Integer, Set<Tile>> e : connectivity.entrySet()) {
                System.out.println(e.getKey() + " <= " + e.getValue().stream().map(z -> String.valueOf(z.getTileId())).collect(Collectors.joining(", ")));
            }
        }

    }

    char[][] rotate(char[][] tile) {
        int side = tile.length;
        char[][] result = new char[side][side];

        for (int i = 0; i < side; ++i) {
            for (int j = 0; j < side; ++j) {
                result[i][j] = tile[side - j - 1][i];
            }
        }

        return result;
    }

    void flip(char[][] tile) {
        for (char[] line : tile) {
            ArrayUtils.reverse(line);
        }
    }

    // Find edge pieces
    long p1() {
        calculateConnectivity();

        long result = 1;
        for (Map.Entry<Integer, Set<Tile>> e : connectivity.entrySet()) {
            if (e.getValue().size() == 2) {
                result *= e.getKey();
            }
        }
        return result;
    }

    int p2() {
        calculateConnectivity();
        char[][] refinedImage = createRefinedImage();

        int allPixels = 0;
        for (int i = 0; i < refinedImage.length; i++) {
            for (int j = 0; j < refinedImage[0].length; j++) {
                if (refinedImage[j][i] == '#') {
                    allPixels++;
                }
            }
        }

        for (int i = 0; i < 8; i++) {
            if (i == 4) {
                flip(refinedImage);
            }
            int monsterPixels = monsterPixels(refinedImage);
            if (monsterPixels > 0) {
                return allPixels - monsterPixels;
            }
            refinedImage = rotate(refinedImage);
        }

        return -1;
    }

    /*
                      #
    #    ##    ##    ###
     #  #  #  #  #  #
     */
    int monsterPixels(char[][] map) {
        int monsterPixels = 0;

        int[][] monster = new int[][]{{18, 0}, {0, 1},
                {5, 1}, {6, 1}, {11, 1}, {12, 1}, {17, 1}, {18, 1}, {19, 1},
                {1, 2}, {4, 2}, {7, 2}, {10, 2}, {13, 2}, {16, 2}};

        for (int y = 0; y < map.length - 3; y++) {
            locLoop:
            for (int x = 0; x < map[0].length - 20; x++) {
                for (int[] mps : monster) {
                    if (map[y + mps[1]][x + mps[0]] != '#') {
                        continue locLoop;
                    }
                }
                monsterPixels += monster.length; // monster found
            }
        }

        if (VERBOSE) {
            StringBuilder sb = new StringBuilder("Searching monsters");
            sb.append(System.lineSeparator());
            printMatrix(map, sb);
            System.out.println(sb);
        }

        return monsterPixels;
    }

    static void printMatrix(char[][] map, StringBuilder sb) {
        for (char[] pixel : map) {
            for (int j = 0; j < map[0].length; j++) {
                sb.append(" ").append(pixel[j]);
            }
            sb.append(System.lineSeparator());
        }
        sb.append(System.lineSeparator());
    }

    /**
     * Start with any corner tile, BFS traverse all connected tiles, rotating and flipping each
     * one of them until it fits perfectly to one of the sides of current tile. Remember the correct
     * cartesian coordinate for each tile to reconstruct everything later.
     * Puzzle assembled here might be rotated or flipped as the whole, we'll deal with it later
     */
    Map<Tile, Point2D> assembleJigsaw() {
        Tile startCorner = null;
        for (Map.Entry<Integer, Set<Tile>> e : connectivity.entrySet()) {
            if (e.getValue().size() == 2) {
                startCorner = knownTiles.stream().filter(t -> t.getTileId() == e.getKey()).findAny().orElseThrow(RuntimeException::new);
                break;
            }
        }
        if (VERBOSE) System.out.println("Start building jigsaw with " + startCorner);

        if (startCorner == null) {
            throw new RuntimeException("Unable to build jigsaw, no start corner found");
        }

        Point2D[] directions = new Point2D[]{new Point2D(0, -1), new Point2D(0, 1), new Point2D(-1, 0), new Point2D(1, 0)};

        Map<Tile, Point2D> wholeMap = new HashMap<>();

        Queue<Tile> queue = new LinkedList<>();
        wholeMap.put(startCorner, Point2D.ZERO);
        queue.add(startCorner);
        while (!queue.isEmpty()) {
            Tile currentTile = queue.poll();
            Point2D currentLocation = wholeMap.get(currentTile);
            if (VERBOSE) System.out.println("Visiting " + currentTile.getTileId() + ", " + currentLocation);

            for (Tile neighbor : connectivity.get(currentTile.getTileId())) {
                if (!wholeMap.containsKey(neighbor)) { // try wholeMap.containsValue(neighbor), might be linear time and slower
                    char[][] currentTileEdgesStraight = currentTile.getEdgesCompat();
                    for (int cm1 = 0; cm1 < 8; cm1 += 2) {
                        // Try connecting to every side
                        int cm2 = neighbor.isCompatible(currentTileEdgesStraight[cm1]);
                        if (cm2 != -1) {
                            // Rotate neighbor as necessary and recalculate
                            // This can probably be derived from cm2, but I'm too lazy to do that, hence, bruteforce
                            for (int pos = 0; pos < 8; pos++) {
                                if (pos == 4) {
                                    // rotated 360 in normal, now another 360 in flipped
                                    flip(neighbor.getPixels());
                                    neighbor.refreshEdgesCache();
                                }
                                if (isInCorrectPosition(cm1, cm2)) {
                                    break;
                                }
                                neighbor.setPixels(rotate(neighbor.getPixels())); // Rotate 90
                                neighbor.refreshEdgesCache();
                                cm2 = neighbor.isCompatible(currentTileEdgesStraight[cm1]);
                            }

                            Point2D neighborLocation = new Point2D(currentLocation.getX() + directions[cm1 / 2].getX(), currentLocation.getY() + directions[cm1 / 2].getY());
                            wholeMap.put(neighbor, neighborLocation);

                            if (VERBOSE) {
                                System.out.println("Connecting tiles: " + currentLocation + " to " + directions[cm1 / 2] + " => " + neighborLocation);
                                System.out.println("Current: " + currentTile);
                                System.out.println("Rotated neighbor: " + neighbor);
                            }
                            break;
                        }
                    }

                    queue.add(neighbor);
                }
            }
        }
        return wholeMap;
    }

    /**
     * Traverse the result of {@link #assembleJigsaw()} cutting out borders.
     * Translates coordinates if the puzzle is flipped and there are negative Xs or Ys.
     */
    public char[][] createRefinedImage() {
        Map<Tile, Point2D> jigsaw = assembleJigsaw();

        IntSummaryStatistics xStats = jigsaw.values().stream().collect(Collectors.summarizingInt(Point2D::getX));
        IntSummaryStatistics yStats = jigsaw.values().stream().collect(Collectors.summarizingInt(Point2D::getY));

        int xTranslation = xStats.getMax() == 0 ? -xStats.getMin() : 0;
        int yTranslation = yStats.getMax() == 0 ? -yStats.getMin() : 0;

        if (VERBOSE) {
            System.out.printf("X: %d - %d, adjust: %d; Y: %d - %d, adjust: %d%n",
                    xStats.getMin(), xStats.getMax(), xTranslation,
                    yStats.getMin(), yStats.getMax(), yTranslation);
        }

        int w = (Math.abs(xStats.getMax() - xStats.getMin()) + 1) * (TILE_SIDE - 2);
        int h = (Math.abs(yStats.getMax() - yStats.getMin()) + 1) * (TILE_SIDE - 2);
        char[][] refinedImage = new char[h][w];

        for (Map.Entry<Tile, Point2D> e : jigsaw.entrySet()) {
            Point2D tileCoordinate = e.getValue();
            char[][] tilePixels = e.getKey().getPixels();
            for (int dy = 1; dy < tilePixels.length - 1; dy++) {
                for (int dx = 1; dx < tilePixels[0].length - 1; dx++) {
                    int x = (TILE_SIDE - 2) * (xTranslation + tileCoordinate.getX()) + dx - 1;
                    int y = (TILE_SIDE - 2) * (yTranslation + tileCoordinate.getY()) + dy - 1;
                    refinedImage[y][x] = tilePixels[dy][dx];
                }
            }
        }

        return refinedImage;
    }

    private boolean isInCorrectPosition(int cm1, int cm2) {
        return (cm1 == 0 && cm2 == 2) || (cm1 == 2 && cm2 == 0) || (cm1 == 4 && cm2 == 6) || (cm1 == 6 && cm2 == 4);
    }

    public static void main(String[] args) {
        Level20 l = new Level20("input");
        System.out.println("Part1: " + l.p1());
        System.out.println("Part2: " + l.p2());
    }

}
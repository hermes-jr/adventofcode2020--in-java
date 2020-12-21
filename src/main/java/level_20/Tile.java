package level_20;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;

import static level_20.Level20.TILE_SIDE;

@Data
class Tile {
    int tileId;
    @EqualsAndHashCode.Exclude
    char[][] pixels = new char[TILE_SIDE][TILE_SIDE];
    @EqualsAndHashCode.Exclude
    char[][] edgesCompat = new char[8][TILE_SIDE];

    Tile(int tileId) {
        this.tileId = tileId;
    }

    void refreshEdgesCache() {
        // top
        edgesCompat[0] = ArrayUtils.clone(pixels[0]);
        edgesCompat[1] = ArrayUtils.clone(pixels[0]);
        ArrayUtils.reverse(edgesCompat[1]);

        // bottom
        edgesCompat[2] = ArrayUtils.clone(pixels[9]);
        edgesCompat[3] = ArrayUtils.clone(pixels[9]);
        ArrayUtils.reverse(edgesCompat[3]);

        for (int i = 0; i < TILE_SIDE; i++) {
            edgesCompat[4][i] = pixels[i][0];
            edgesCompat[5][i] = pixels[i][0];
            edgesCompat[6][i] = pixels[i][9];
            edgesCompat[7][i] = pixels[i][9];
        }
        ArrayUtils.reverse(edgesCompat[5]);
        ArrayUtils.reverse(edgesCompat[7]);
    }

    int isCompatible(char[] edge) {
        for (int i = 0; i < edgesCompat.length; i++) {
            if (Arrays.equals(edgesCompat[i], edge)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Tile ");
        sb.append(tileId).append(System.lineSeparator());
        Level20.printMatrix(pixels, sb);
        return sb.toString();
    }
}

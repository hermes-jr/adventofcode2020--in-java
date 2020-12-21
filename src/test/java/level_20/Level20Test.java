package level_20;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Level20Test {

    @Test
    void partOneExampleShouldWork() {
        Level20 l = new Level20("in1");
        assertThat(l.p1()).isEqualTo(20899048083289L);
    }

    @Test
    void partTwoExampleShouldWork() {
        Level20 l = new Level20("in1");
        assertThat(l.p2()).isEqualTo(273);
    }

    @Test
    void partTwoShouldProduceProperRefinedImage() {
        Level20 l = new Level20("in1");
        List<String> referenceImage = l.readResources("in2");
        char[][] referenceImageMatrix = new char[referenceImage.size()][referenceImage.size()];
        int i = 0;
        for (String line : referenceImage) {
            referenceImageMatrix[i++] = line.toCharArray();
        }
        l.calculateConnectivity();
        char[][] toTest = l.createRefinedImage();

        /*
        {@link Level20#createRefinedImage()} could've given flipped or rotated result,
        but if all done correctly, one variation should work. Try until it fits
         */
        boolean fit = false;
        for (i = 0; i < 8; i++) {
            if (i == 4) {
                l.flip(toTest);
            }
            if (Arrays.deepEquals(toTest, referenceImageMatrix)) {
                fit = true;
                break;
            }
            l.rotate(toTest);
        }

        assertThat(fit).isTrue();
    }

    @Test
    void monstersShouldBeCountedProperly() {
        Level20 l = new Level20("in1");

        List<String> referenceImage = l.readResources("in2");
        char[][] referenceImageMatrix = new char[referenceImage.size()][referenceImage.size()];
        int i = 0;
        for (String line : referenceImage) {
            referenceImageMatrix[i++] = line.toCharArray();
        }
        referenceImageMatrix = l.rotate(referenceImageMatrix);
        l.flip(referenceImageMatrix);

        assertThat(l.monsterPixels(referenceImageMatrix)).isEqualTo(30);
    }
}
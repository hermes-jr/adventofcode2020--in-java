package level_07;

import common.Level;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Level07Test extends Level {

    @Test
    void firstPartExample() {
        Level07 l = new Level07();
        SimpleDirectedWeightedGraph<String, DefaultEdge> g1 = l.readResources("in1");
        assertThat(l.p1(g1)).isEqualTo(4);
    }

    @Test
    void secondPartExample() {
        Level07 l = new Level07();
        SimpleDirectedWeightedGraph<String, DefaultEdge> g1 = l.readResources("in1");
        assertThat(l.p2(g1)).isEqualTo(32);

        SimpleDirectedWeightedGraph<String, DefaultEdge> g2 = l.readResources("in2");
        assertThat(l.p2(g2)).isEqualTo(126);
    }

}
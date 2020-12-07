package level_07;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.traverse.DepthFirstIterator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Level07 {

    private static final boolean VERBOSE = false;
    private static final String origin = "shiny gold";

    SimpleDirectedWeightedGraph<String, DefaultEdge> readResources(String filename) {
        SimpleDirectedWeightedGraph<String, DefaultEdge> g = new SimpleDirectedWeightedGraph<>(DefaultEdge.class);

        try (BufferedReader br
                     = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("level_07/" + filename))))) {
            String line;
            //light red bags contain 1 bright white bag, 2 muted yellow bags.
            Pattern p = Pattern.compile("([0-9]+) ([\\p{L} ]+) bags?");
            while ((line = br.readLine()) != null) {
                String[] split = line.split(" contain ");
                String tgt = split[0].split(" bags")[0].trim();

                if (split[1].equals("no other bags.")) {
                    continue;
                }
                g.addVertex(tgt);

                String[] insides = split[1].substring(0, split[1].length() - 1).split(",");
                for (String i : insides) {
                    Matcher matcher = p.matcher(i.trim());
                    if (!matcher.matches()) {
                        System.out.println("Can't parse: " + i);
                    }
                    int weight = Integer.parseInt(matcher.group(1));
                    String color = matcher.group(2);
                    if (VERBOSE) System.out.println(weight + " of " + color);

                    g.addVertex(color);
                    DefaultEdge newEdge = g.addEdge(color, tgt);
                    g.setEdgeWeight(newEdge, weight);
                }
            }
            return g;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't read resources", e);
        }
    }

    int p1(SimpleDirectedWeightedGraph<String, DefaultEdge> g) {
        Iterator<String> iterator = new DepthFirstIterator<>(g, origin);

        int reachable = 0;
        while (iterator.hasNext()) {
            String node = iterator.next();
            if (VERBOSE) System.out.println(node);
            if (!origin.equals(node)) {
                reachable++;
            }
        }
        return reachable;
    }

    int p2(SimpleDirectedWeightedGraph<String, DefaultEdge> g) {
        return bagWeight(g, origin) - 1;
    }

    private int bagWeight(SimpleDirectedWeightedGraph<String, DefaultEdge> g, String root) {
        Set<DefaultEdge> edges = g.incomingEdgesOf(root);
        if (VERBOSE) System.out.print("visiting " + root + "\n\t");
        if (edges.isEmpty()) {
            if (VERBOSE) System.out.println("No neighbors, weight 1");
            return 1;
        }
        int sum = 1;
        for (DefaultEdge e : edges) {
            String neighbor = g.getEdgeSource(e);
            int nbw = bagWeight(g, neighbor);
            sum += g.getEdgeWeight(e) * nbw;
            if (VERBOSE) {
                System.out.println(root + " w: " + g.getEdgeWeight(e) + " of " + neighbor + " that is " + nbw);
            }
        }
        if (VERBOSE) System.out.println("Total weight of " + root + " is " + sum);
        return sum;
    }

    public static void main(String[] args) {
        Level07 l = new Level07();
        SimpleDirectedWeightedGraph<String, DefaultEdge> g = l.readResources("input");

        System.out.println("Result1: " + l.p1(g));
        System.out.println("Result2: " + l.p2(g));
    }
}

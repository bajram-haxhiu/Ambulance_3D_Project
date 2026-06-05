package algorithms;

import model.Edge;
import model.Graph;
import model.Node;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * AStar.java
 *
 * A* shortest path algorithm.
 * Uses real node coordinates as the heuristic, so it is perfect for this 3D city map.
 *
 * f(n) = g(n) + h(n)
 * g(n) = distance from start to n
 * h(n) = estimated Euclidean distance from n to target
 *
 * Complexity: O((V + E) log V) with Java PriorityQueue.
 */
public class AStar {

    private final Graph graph;
    private final List<Node> visitedOrder = new ArrayList<>();

    public AStar(Graph graph) {
        this.graph = graph;
    }

    public List<Node> findShortestPath(Node start, Node target) {
        graph.resetAlgorithmState();
        visitedOrder.clear();

        Map<Node, Double> fScore = new HashMap<>();
        PriorityQueue<Node> open = new PriorityQueue<>(Comparator.comparingDouble(n -> fScore.getOrDefault(n, Double.POSITIVE_INFINITY)));

        start.setDistance(0);
        fScore.put(start, heuristic(start, target));
        open.add(start);

        while (!open.isEmpty()) {
            Node current = open.poll();
            if (current.isVisited()) continue;

            current.setVisited(true);
            visitedOrder.add(current);

            if (current.equals(target)) break;

            for (Edge edge : graph.getNeighbors(current)) {
                Node neighbor = edge.getOther(current);
                if (neighbor.isVisited()) continue;

                double tentativeG = current.getDistance() + edge.getWeight();
                if (tentativeG < neighbor.getDistance()) {
                    neighbor.setDistance(tentativeG);
                    neighbor.setPrevious(current);
                    fScore.put(neighbor, tentativeG + heuristic(neighbor, target));
                    open.add(neighbor);
                }
            }
        }

        return PathFinder.reconstructPath(target);
    }

    private double heuristic(Node a, Node b) {
        double dx = a.getX() - b.getX();
        double dz = a.getZ() - b.getZ();
        return Math.sqrt(dx * dx + dz * dz);
    }

    public List<Node> getVisitedOrder() {
        return visitedOrder;
    }
}

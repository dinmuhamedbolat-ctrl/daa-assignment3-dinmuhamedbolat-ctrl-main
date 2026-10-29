import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Problem03 {
    public int minCostConnectPoints(int[][] points) {
        int n = points.length;

        List<Edge> edges = new ArrayList<>();
        for (int first = 0; first < n; first++) {
            for (int second = first + 1; second < n; second++) {
                int cost = Math.abs(points[first][0] - points[second][0])
                        + Math.abs(points[first][1] - points[second][1]);
                edges.add(new Edge(first, second, cost));
            }
        }
        edges.sort(Comparator.comparingInt(edge -> edge.cost));

        int[] parent = new int[n];
        int[] rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }

        int totalCost = 0;
        int edgesUsed = 0;
        for (Edge edge : edges) {
            int firstRoot = find(parent, edge.first);
            int secondRoot = find(parent, edge.second);
            if (firstRoot != secondRoot) {
                union(parent, rank, firstRoot, secondRoot);
                totalCost += edge.cost;
                edgesUsed++;
                if (edgesUsed == n - 1) {
                    break;
                }
            }
        }

        return totalCost;
    }

    private int find(int[] parent, int point) {
        if (parent[point] != point) {
            parent[point] = find(parent, parent[point]);
        }
        return parent[point];
    }

    private void union(int[] parent, int[] rank, int firstRoot, int secondRoot) {
        if (rank[firstRoot] < rank[secondRoot]) {
            parent[firstRoot] = secondRoot;
        } else if (rank[firstRoot] > rank[secondRoot]) {
            parent[secondRoot] = firstRoot;
        } else {
            parent[secondRoot] = firstRoot;
            rank[firstRoot]++;
        }
    }

    private static class Edge {
        private final int first;
        private final int second;
        private final int cost;

        private Edge(int first, int second, int cost) {
            this.first = first;
            this.second = second;
            this.cost = cost;
        }
    }
}

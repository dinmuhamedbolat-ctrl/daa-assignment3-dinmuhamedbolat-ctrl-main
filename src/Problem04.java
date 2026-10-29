import java.util.Arrays;

public class Problem04 {
    public int minCostConnectPoints(int[][] points) {
        int n = points.length;

        int[] minCost = new int[n];
        Arrays.fill(minCost, Integer.MAX_VALUE);
        boolean[] included = new boolean[n];
        minCost[0] = 0;
        int totalCost = 0;

        for (int count = 0; count < n; count++) {
            int point = -1;
            for (int candidate = 0; candidate < n; candidate++) {
                if (!included[candidate]
                        && (point == -1 || minCost[candidate] < minCost[point])) {
                    point = candidate;
                }
            }

            included[point] = true;
            totalCost += minCost[point];

            for (int candidate = 0; candidate < n; candidate++) {
                if (!included[candidate]) {
                    int cost = Math.abs(points[point][0] - points[candidate][0])
                            + Math.abs(points[point][1] - points[candidate][1]);
                    minCost[candidate] = Math.min(minCost[candidate], cost);
                }
            }
        }

        return totalCost;
    }
}

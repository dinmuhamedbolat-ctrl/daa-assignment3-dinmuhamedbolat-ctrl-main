public class Problem02 {
    public int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;
        int[] parent = new int[n];
        int[] rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }

        int provinces = n;
        for (int city = 0; city < n; city++) {
            for (int neighbor = city + 1; neighbor < n; neighbor++) {
                if (isConnected[city][neighbor] == 1) {
                    int rootCity = find(parent, city);
                    int rootNeighbor = find(parent, neighbor);
                    if (rootCity != rootNeighbor) {
                        if (rank[rootCity] < rank[rootNeighbor]) {
                            parent[rootCity] = rootNeighbor;
                        } else if (rank[rootCity] > rank[rootNeighbor]) {
                            parent[rootNeighbor] = rootCity;
                        } else {
                            parent[rootNeighbor] = rootCity;
                            rank[rootCity]++;
                        }
                        provinces--;
                    }
                }
            }
        }

        return provinces;
    }

    private int find(int[] parent, int city) {
        if (parent[city] != city) {
            parent[city] = find(parent, parent[city]);
        }
        return parent[city];
    }
}

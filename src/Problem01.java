public class Problem01{
    public int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;
        boolean[] visited = new boolean[n];
        int[] stack = new int[n];
        int provinces = 0;

        for (int start = 0; start < n; start++) {
            if (visited[start]) {
                continue;
            }

            provinces++;
            int top = 0;
            stack[top++] = start;
            visited[start] = true;

            while (top > 0) {
                int city = stack[--top];
                for (int neighbor = 0; neighbor < n; neighbor++) {
                    if (isConnected[city][neighbor] == 1 && !visited[neighbor]) {
                        visited[neighbor] = true;
                        stack[top++] = neighbor;
                    }
                }
            }
        }

        return provinces;
    }
}

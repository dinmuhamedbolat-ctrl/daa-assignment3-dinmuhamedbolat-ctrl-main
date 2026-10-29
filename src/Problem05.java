import java.util.ArrayList;
import java.util.List;

public class Problem05 {
    public int countDependencyGroups(int n, int[][] dependencies){
        List<Integer>[] graph = createGraph(n);
        List<Integer>[] reverseGraph = createGraph(n);
        for (int[] dependency : dependencies) {
            graph[dependency[0]].add(dependency[1]);
            reverseGraph[dependency[1]].add(dependency[0]);
        }

        boolean[] visited = new boolean[n];
        int[] nextNeighbor = new int[n];
        int[] stack = new int[n];
        int[] finishingOrder = new int[n];
        int orderSize = 0;

        for (int start = 0; start < n; start++) {
            if (visited[start]) {
                continue;
            }

            int top = 0;
            stack[top++] = start;
            visited[start] = true;
            while (top > 0) {
                int module = stack[top - 1];
                if (nextNeighbor[module] < graph[module].size()) {
                    int neighbor = graph[module].get(nextNeighbor[module]++);
                    if (!visited[neighbor]) {
                        visited[neighbor] = true;
                        stack[top++] = neighbor;
                    }
                } else {
                    top--;
                    finishingOrder[orderSize++] = module;
                }
            }
        }

        visited = new boolean[n];
        int groups = 0;
        for (int i = orderSize - 1; i >= 0; i--) {
            int start = finishingOrder[i];
            if (visited[start]) {
                continue;
            }

            groups++;
            int top = 0;
            stack[top++] = start;
            visited[start] = true;
            while (top > 0) {
                int module = stack[--top];
                for (int neighbor : reverseGraph[module]) {
                    if (!visited[neighbor]) {
                        visited[neighbor] = true;
                        stack[top++] = neighbor;
                    }
                }
            }
        }

        return groups;
    }

    @SuppressWarnings("unchecked")
    private List<Integer>[] createGraph(int n) {
        List<Integer>[] graph = (List<Integer>[]) new List<?>[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        return graph;
    }
}

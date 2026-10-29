import java.util.ArrayList;
import java.util.List;

public class Problem06 {
    public int countDependencyGroups(int n, int[][] dependencies){
        List<Integer>[] graph = createGraph(n);
        for (int[] dependency : dependencies) {
            graph[dependency[0]].add(dependency[1]);
        }

        int[] discovery = new int[n];
        int[] low = new int[n];
        boolean[] onStack = new boolean[n];
        int[] componentStack = new int[n];
        int componentTop = 0;
        int[] dfsNodes = new int[n];
        int[] nextNeighbor = new int[n];
        int time = 0;
        int groups = 0;

        for (int start = 0; start < n; start++) {
            if (discovery[start] != 0) {
                continue;
            }

            int dfsTop = 0;
            dfsNodes[dfsTop++] = start;
            discovery[start] = ++time;
            low[start] = discovery[start];
            componentStack[componentTop++] = start;
            onStack[start] = true;

            while (dfsTop > 0) {
                int module = dfsNodes[dfsTop - 1];
                if (nextNeighbor[module] < graph[module].size()) {
                    int neighbor = graph[module].get(nextNeighbor[module]++);
                    if (discovery[neighbor] == 0) {
                        dfsNodes[dfsTop++] = neighbor;
                        discovery[neighbor] = ++time;
                        low[neighbor] = discovery[neighbor];
                        componentStack[componentTop++] = neighbor;
                        onStack[neighbor] = true;
                    } else if (onStack[neighbor]) {
                        low[module] = Math.min(low[module], discovery[neighbor]);
                    }
                } else {
                    dfsTop--;
                    if (dfsTop > 0) {
                        int parent = dfsNodes[dfsTop - 1];
                        low[parent] = Math.min(low[parent], low[module]);
                    }

                    if (low[module] == discovery[module]) {
                        while (true) {
                            int member = componentStack[--componentTop];
                            onStack[member] = false;
                            if (member == module) {
                                break;
                            }
                        }
                        groups++;
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

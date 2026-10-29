import java.util.ArrayList;
import java.util.List;

public class Problem09 {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<Integer>[] graph = createGraph(numCourses);
        for (int[] prerequisite : prerequisites) {
            graph[prerequisite[1]].add(prerequisite[0]);
        }

        byte[] state = new byte[numCourses];
        int[] nextNeighbor = new int[numCourses];
        int[] stack = new int[numCourses];
        int[] order = new int[numCourses];
        int orderSize = 0;

        for (int start = 0; start < numCourses; start++) {
            if (state[start] != 0) {
                continue;
            }

            int top = 0;
            stack[top++] = start;
            state[start] = 1;
            while (top > 0) {
                int course = stack[top - 1];
                if (nextNeighbor[course] < graph[course].size()) {
                    int dependent = graph[course].get(nextNeighbor[course]++);
                    if (state[dependent] == 1) {
                        return new int[0];
                    }
                    if (state[dependent] == 0) {
                        state[dependent] = 1;
                        stack[top++] = dependent;
                    }
                } else {
                    state[course] = 2;
                    order[orderSize++] = course;
                    top--;
                }
            }
        }

        int[] result = new int[numCourses];
        for (int i = 0; i < numCourses; i++) {
            result[i] = order[numCourses - 1 - i];
        }
        return result;
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

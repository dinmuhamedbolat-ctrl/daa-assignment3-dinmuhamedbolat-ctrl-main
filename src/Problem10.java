import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Problem10 {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<Integer>[] graph = createGraph(numCourses);
        int[] indegree = new int[numCourses];
        for (int[] prerequisite : prerequisites) {
            graph[prerequisite[1]].add(prerequisite[0]);
            indegree[prerequisite[0]]++;
        }

        Queue<Integer> ready = new ArrayDeque<>();
        for (int course = 0; course < numCourses; course++) {
            if (indegree[course] == 0) {
                ready.add(course);
            }
        }

        int[] order = new int[numCourses];
        int orderSize = 0;
        while (!ready.isEmpty()) {
            int course = ready.remove();
            order[orderSize++] = course;
            for (int dependent : graph[course]) {
                indegree[dependent]--;
                if (indegree[dependent] == 0) {
                    ready.add(dependent);
                }
            }
        }

        return orderSize == numCourses ? order : new int[0];
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

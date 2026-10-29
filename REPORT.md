### DAA Assignment 3 - Report and Defense

#### Problems 1 and 2 - Number of Provinces

The input is an `n x n` adjacency matrix: a value of `1` means that two cities are directly connected. Since the graph is undirected, cities belong to the same province when there is a path between them.

**Problem 1 (DFS).** I examine each city in turn. When I find an unvisited city, I increment the province count and start an iterative depth-first search from it. The search follows every matrix entry marked `1` and marks all reachable cities as visited. Therefore, one search visits exactly the cities in one connected component, and starting a search only for an unvisited city counts each province once. An explicit stack avoids relying on the Java call stack. Time complexity is **O(n^2)** because the matrix has `n^2` entries; extra space complexity is **O(n)** for the visited array and stack.

**Problem 2 (DSU).** I begin with each city in its own set and initialize the province count to `n`. For each pair of cities connected in the matrix, I find their set representatives. If the representatives differ, the cities belong to distinct components, so I merge the sets and reduce the count by one. Path compression makes future `find` operations faster, while union by rank keeps the sets shallow. Skipping pairs below the diagonal is sufficient because the matrix represents an undirected graph. The matrix scan takes **O(n^2)** and DSU operations take **O(n^2 α(n))** in total; extra space complexity is **O(n)**. Here, `α(n)` is the inverse Ackermann function, which grows extremely slowly.

#### Problems 3 and 4 - Minimum Cost to Connect All Points

Each point is a vertex, and every pair of points can be joined by an edge. The edge weight is the Manhattan distance: `|x1 - x2| + |y1 - y2|`. Connecting every point at minimum total cost is the minimum spanning tree problem.

**Problem 3 (Kruskal).** I generate an edge for every pair of points and sort all edges by increasing cost. I then process them in that order, using DSU to check whether the endpoints are already connected. An edge is added only when it joins two different components; adding an edge within one component would create a cycle without helping connect the points. By Kruskal's algorithm, choosing the cheapest available edge that joins components preserves a minimum-cost solution. When `n - 1` edges have been added, the spanning tree is complete. There are `O(n^2)` edges, so sorting takes **O(n^2 log n)**; extra space complexity is **O(n^2)** for the edge list.

**Problem 4 (Prim).** I start with point `0` in the tree. For each point not yet included, I store its cheapest known edge to the current tree. At each step, I add the point with the smallest such cost, add that cost to the total, and update the remaining points' best costs using Manhattan distance. Each step adds the cheapest edge crossing from the current tree to an outside point, which is the key choice in Prim's minimum-spanning-tree algorithm. The implementation searches arrays rather than storing every edge: time complexity is **O(n^2)** and extra space complexity is **O(n)**.

#### Problems 5 and 6 - Software Dependency Cycles

In both solutions, dependency `[a, b]` is represented as a directed edge from module `a` to module `b`. A circular dependency group is a strongly connected component (SCC): every module in it can reach every other module by following directed edges. A module with no such links is still a one-module SCC and is counted.

**Problem 5 (Kosaraju).** I build both the dependency graph and its reversed graph. In the first pass, iterative DFS visits the original graph and records each module after all of its outgoing neighbors have been processed. In the second pass, I consider modules in reverse finishing order and run DFS on the reversed graph. Each of these second-pass searches visits exactly one SCC, so the number of searches is the answer. The first-pass finishing order determines a safe order for separating SCCs in the reversed graph. Both passes and graph construction take **O(n + m)** time; the two adjacency lists and traversal arrays use **O(n + m)** extra space, where `m` is the number of dependencies.

**Problem 6 (Tarjan).** I use one iterative depth-first traversal and keep a discovery index and low-link value for each module. The discovery index records when a module is first visited. A low-link value records the earliest discovered module reachable through the DFS subtree and an edge back to a module still on the component stack. When a module's low-link value equals its discovery index, it is the root of an SCC: I pop modules from the component stack through that root and increment the group count. This identifies each SCC once without needing a reversed graph. The traversal processes every vertex and edge a constant number of times, so time complexity is **O(n + m)**; the graph, stacks, and index arrays require **O(n + m)** extra space.

#### Problems 7 and 8 - Course Schedule

For prerequisite `[course, prerequisite]`, I add a directed edge from `prerequisite` to `course`. This direction means that the prerequisite must be completed before the course. All courses can be completed exactly when this directed graph has no cycle.

**Problem 7 (DFS).** Each course has one of three states: unvisited, currently being explored, or finished. I start an iterative DFS from each unvisited course. If an edge leads to a course currently being explored, it points back into the active DFS path and forms a cycle; in that case, the answer is `false`. Edges to finished courses do not form a new cycle. If every search finishes without finding a back edge, the graph is acyclic and the answer is `true`. Each vertex and edge is processed a constant number of times, so time complexity is **O(V + E)** and extra space complexity is **O(V + E)** for the graph and traversal data.

**Problem 8 (Kahn).** I calculate the indegree of every course, which is the number of its prerequisites, and enqueue courses with indegree zero. Each time I remove a course from the queue, I count it as completed and reduce the indegree of each dependent course. A dependent enters the queue once all of its prerequisites have been removed. If all courses are removed, the answer is `true`; if some remain, they are blocked by a cycle and the answer is `false`. The algorithm processes each course and prerequisite edge once: time complexity is **O(V + E)**, with **O(V + E)** extra space for the graph, queue, and indegrees.

#### Problems 9 and 10 - Course Schedule II

These problems use the same graph direction as Problems 7 and 8. The result must list every course so that each prerequisite appears before the course that depends on it. If the graph contains a cycle, no valid order exists, and both solutions return an empty array.

**Problem 9 (DFS).** I use the three DFS states from Problem 7 to detect cycles. A cycle causes an immediate return of an empty array. Otherwise, each course is added to a finishing-order array only after all of its dependent courses have been explored. Reversing this array produces a topological order: for every edge from a prerequisite to a course, the prerequisite appears first. Each vertex and edge is processed once, giving **O(V + E)** time and **O(V + E)** extra space.

**Problem 10 (Kahn).** I use the indegrees and queue from Problem 8, appending each removed course to the result array. This order respects every prerequisite edge because a course is added only after all of its prerequisites have been processed. If the result contains all `V` courses, I return it; otherwise, the remaining courses form or depend on a cycle, so I return an empty array. Time complexity is **O(V + E)** and extra space complexity is **O(V + E)**.

In the course-schedule sections, `V` is the number of courses and `E` is the number of prerequisite pairs.

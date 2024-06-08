import java.util.*;

class Pair {
    int first, second;

    Pair(int f, int s) {
        first = f;
        second = s;
    }
}

public class water_jug {
    static void BFS(int a, int b, int target) {
        int m[][] = new int[1000][1000];
        for (int[] i : m) {
            Arrays.fill(i, -1);
        }
        boolean isSolvable = false;
        Vector<Pair> path = new Vector<Pair>();
        Queue<Pair> q = new LinkedList<Pair>();
        q.add(new Pair(0, 0));
        while (!q.isEmpty()) {
            Pair u = q.peek();
            q.poll();
            if ((u.first > a || u.second > b ||
                    u.first < 0 || u.second < 0))
                continue;
            if (m[u.first][u.second] > -1)
                continue;
            path.add(new Pair(u.first, u.second));
            m[u.first][u.second] = 1;
            if (u.first == target || u.second == target) {
                isSolvable = true;
                if (u.first == target) {
                    if (u.second != 0)
                        path.add(new Pair(u.first, 0));
                } else {
                    if (u.first != 0)
                        path.add(new Pair(0, u.second));
                }
                int sz = path.size();
                for (int i = 0; i < sz; i++)
                    System.out.println("(" + path.get(i).first +
                            ", " + path.get(i).second + ")");
                System.out.println("(" + target + ", " + 0 + ")");
                break;
            }
            q.add(new Pair(u.first, b));
            q.add(new Pair(a, u.second));
            for (int ap = 0; ap <= Math.max(a, b); ap++) {
                int c = u.first + ap;
                int d = u.second - ap;
                if (c == a || d == 0)
                    q.add(new Pair(c, d));
                c = u.first - ap;
                d = u.second + ap;
                if (c == 0 || d == b)
                    q.add(new Pair(c, d));
            }
            q.add(new Pair(a, 0));
            q.add(new Pair(0, b));
        }
        if (!isSolvable)
            System.out.print("No solution");
    }

    static void DFS(int a, int b, int target) {
        int m[][] = new int[1000][1000];
        Pair parent[][] = new Pair[1000][1000];
        for (int[] i : m) {
            Arrays.fill(i, -1);
        }
        Stack<Pair> stack = new Stack<>();
        Pair initialState = new Pair(0, 0);
        stack.push(initialState);
        while (!stack.isEmpty()) {
            Pair u = stack.pop();
            int j1 = u.first;
            int j2 = u.second;
            if (j1 == target || j2 == target) {
                List<Pair> path = new ArrayList<>();
                Pair lastStep = null;
                while (u != null) {
                    path.add(u);
                    lastStep = u;
                    u = parent[u.first][u.second];
                }
                Collections.reverse(path);
                for (Pair step : path) {
                    System.out.println("(" + step.first + ", " + step.second + ")");
                }
                if (lastStep != null && (lastStep.first == 0 || lastStep.second == 0)) {
                    int remaining = lastStep.first == 0 ? lastStep.second : lastStep.first;
                    System.out.println("(" + remaining + ", " + (target - remaining) + ")");
                    System.out.println("(" + target + ", " + 0 + ")");
                }
                return;
            }
            if (m[j1][j2] > -1) {
                continue;
            }
            m[j1][j2] = 1;
            if (m[a][j2] == -1) {
                stack.push(new Pair(a, j2));
                parent[a][j2] = u;
            }
            if (m[j1][b] == -1) {
                stack.push(new Pair(j1, b));
                parent[j1][b] = u;
            }
            if (m[0][j2] == -1) {
                stack.push(new Pair(0, j2));
                parent[0][j2] = u;
            }
            if (m[j1][0] == -1) {
                stack.push(new Pair(j1, 0));
                parent[j1][0] = u;
            }
            int amount = Math.min(j1, b - j2);
            if (m[j1 - amount][j2 + amount] == -1) {
                stack.push(new Pair(j1 - amount, j2 + amount));
                parent[j1 - amount][j2 + amount] = u;
            }
            amount = Math.min(j2, a - j1);
            if (m[j1 + amount][j2 - amount] == -1) {
                stack.push(new Pair(j1 + amount, j2 - amount));
                parent[j1 + amount][j2 - amount] = u;
            }
        }
    }

    static int heuristic(int x, int targetX) {
        return x * targetX;
    }

    static void heuristicSearch(int a, int b, int targetX) {
        int[][] visited = new int[a + 1][b + 1];
        Pair[][] parent = new Pair[a + 1][b + 1];
        PriorityQueue<Pair> pq = new PriorityQueue<>(new Comparator<Pair>() {
            public int compare(Pair p1, Pair p2) {
                int h1 = heuristic(p1.first, targetX);
                int h2 = heuristic(p2.first, targetX);
                return h1 - h2;
            }
        });
        pq.add(new Pair(0, 0));
        visited[0][0] = 1;
        while (!pq.isEmpty()) {
            Pair u = pq.poll();
            int x = u.first;
            int y = u.second;
            if (x == targetX && y == 0) {
                // Print the solution path
                List<Pair> path = new ArrayList<>();
                while (u != null) {
                    path.add(u);
                    u = parent[u.first][u.second];
                }
                Collections.reverse(path);
                for (Pair step : path) {
                    System.out.println("(" + step.first + ", " + step.second + ")");
                }
                return;
            }
            // Valid actions: Fill jug 1, fill jug 2, empty jug 1, empty jug 2, pour jug 1
            // to jug 2, pour jug 2 to jug 1
            int[][] actions = { { a, y }, { x, b }, { 0, y }, { x, 0 }, { Math.min(a, x + y), y - Math.min(a - x, y) },
                    { x - Math.min(x, b - y), Math.min(y + x, b) } };
            for (int[] action : actions) {
                int newX = action[0];
                int newY = action[1];
                if (newX >= 0 && newY >= 0 && newX <= a && newY <= b && visited[newX][newY] == 0) {
                    pq.add(new Pair(newX, newY));
                    visited[newX][newY] = 1;
                    parent[newX][newY] = u;
                }
            }
        }
    }

    public static void main(String[] args) {
        int jug1Capacity = 4; // Capacity of first jug
        int jug2Capacity = 3; // Capacity of second jug
        int targetCapacity = 2; // Target capacity to achieve
        System.out.println("Possible steps to achieve target capacity using BFS:");
        BFS(jug1Capacity, jug2Capacity, targetCapacity);
        System.out.println("Possible steps to achieve target capacity using DFS:");
        DFS(jug1Capacity, jug2Capacity, targetCapacity);
        System.out.println("Possible steps to achieve target capacity using Heuristic:");
        heuristicSearch(jug1Capacity, jug2Capacity, targetCapacity);
    }
}

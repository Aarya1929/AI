import java.io.*;
import java.util.*;

class Graph {
    private int V;
    private LinkedList<Integer>[] adj;

    public Graph(int v) {
        V = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; i++)
            adj[i] = new LinkedList<>();
    }

    public void addEdge(int u, int v) {
        adj[u].add(v);
        adj[v].add(u);
    }

    public void bfs(Queue<Integer> queue, Boolean[] visited, int[] parent) {
        int current = queue.poll();
        for (int i : adj[current]) {
            if (!visited[i]) {
                parent[i] = current;
                visited[i] = true;
                queue.add(i);
            }
        }
    }

    public int isIntersecting(Boolean[] s_visited, Boolean[] t_visited) {
        for (int i = 0; i < V; i++) {
            if (s_visited[i] && t_visited[i])
                return i;
        }
        return -1;
    }

    public void printPath(int[] s_parent, int[] t_parent, int s, int t, int intersectNode) {
        LinkedList<Integer> path = new LinkedList<>();
        path.add(intersectNode);
        int i = intersectNode;
        while (i != s) {
            path.add(s_parent[i]);
            i = s_parent[i];
        }
        Collections.reverse(path);
        i = intersectNode;
        while (i != t) {
            path.add(t_parent[i]);
            i = t_parent[i];
        }

        System.out.println("*****Path*****");
        for (int it : path)
            System.out.print(it + " ");
        System.out.println();
    }

    public int biDirSearch(int s, int t) {
        Boolean[] s_visited = new Boolean[V];
        Boolean[] t_visited = new Boolean[V];
        int[] s_parent = new int[V];
        int[] t_parent = new int[V];
        Queue<Integer> s_queue = new LinkedList<>();
        Queue<Integer> t_queue = new LinkedList<>();
        int intersectNode = -1;

        for (int i = 0; i < V; i++) {
            s_visited[i] = false;
            t_visited[i] = false;
        }

        s_queue.add(s);
        s_visited[s] = true;
        s_parent[s] = -1;

        t_queue.add(t);
        t_visited[t] = true;
        t_parent[t] = -1;

        while (!s_queue.isEmpty() && !t_queue.isEmpty()) {
            bfs(s_queue, s_visited, s_parent);
            bfs(t_queue, t_visited, t_parent);

            intersectNode = isIntersecting(s_visited, t_visited);

            if (intersectNode != -1) {
                System.out.printf("Path exist between %d and %d\n", s, t);
                System.out.printf("Intersection at: %d\n", intersectNode);
                printPath(s_parent, t_parent, s, t, intersectNode);
                System.exit(0);
            }
        }
        return -1;
    }
}

public class BiDirSearch {
    public static void main(String[] args) {
        int n = 15;
        int s = 0;
        int t = 14;
        Graph g = new Graph(n);
        g.addEdge(0, 4);
        g.addEdge(1, 4);
        g.addEdge(2, 5);
        g.addEdge(3, 5);
        g.addEdge(4, 6);
        g.addEdge(5, 6);
        g.addEdge(6, 7);
        g.addEdge(7, 8);
        g.addEdge(8, 9);
        g.addEdge(8, 10);
        g.addEdge(9, 11);
        g.addEdge(9, 12);
        g.addEdge(10, 13);
        g.addEdge(10, 14);
        if (g.biDirSearch(s, t) == -1)
            System.out.printf("Path don't exist between %d and %d", s, t);
    }
}

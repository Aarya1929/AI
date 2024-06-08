#include <stdio.h>
#include <stdlib.h>

#define MAX_NODES 10

struct Graph
{
    int adj[MAX_NODES][MAX_NODES];
};

void addEdge(struct Graph *gp, int v, int w)
{
    gp->adj[v][w] = 1;
}

int DLS(struct Graph *gp, int v, int goal, int limit)
{
    if (v == goal)
    {
        return 1;
    }

    for (int i = 0; i < MAX_NODES; ++i)
    {
        if (gp->adj[v][i] == 1 && limit - 1 >= 0)
        {
            printf("%d ", i);
            if (DLS(gp, i, goal, limit - 1) != -1)
                return 1;
        }
    }
    return -1;
}

int IDDFS(struct Graph *gp, int src, int goal, int limit)
{
    for (int i = 0; i <= limit; ++i)
    {
        printf("Iteration %d: %d ", i, src);
        if (DLS(gp, src, goal, i) == 1)
        {
            return 1;
        }
        printf("\n");
    }
    return -1;
}

int main()
{
    struct Graph gp;
    for (int i = 0; i < MAX_NODES; ++i)
        for (int j = 0; j < MAX_NODES; ++j)
            gp.adj[i][j] = 0;

    int edges, v, w;
    printf("Enter the number of edges: ");
    scanf("%d", &edges);

    printf("Enter the edges (format: vertex1 vertex2):\n");
    for (int i = 0; i < edges; ++i)
    {
        scanf("%d %d", &v, &w);
        addEdge(&gp, v, w);
    }

    int src, goal, limit;
    printf("Enter the source vertex: ");
    scanf("%d", &src);

    printf("Enter the goal vertex: ");
    scanf("%d", &goal);

    printf("Enter the depth limit: ");
    scanf("%d", &limit);

    if (IDDFS(&gp, src, goal, limit) == 1)
        printf("\nGoal node found within depth limit\n");
    else
        printf("Goal node not found!\n");

    return 0;
}

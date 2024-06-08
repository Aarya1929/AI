#include <bits/stdc++.h>
using namespace std;
vector<vector<pair<int, int>>> graph;
vector<int> heurist;
void link_nodes(int x, int y, int cost)
{
    graph[x].push_back(make_pair(cost, y));
    graph[y].push_back(make_pair(cost, x));
}
void best_first_search(int actual_Src, int target, int n)
{
    vector<bool> visited(n, false);
    priority_queue<pair<int, int>, vector<pair<int, int>>, greater<pair<int, int>>> pq;
    // sorting in pq gets done by first value of pair i.e. cost here
    pq.push(make_pair(0, actual_Src));
    int s = actual_Src;
    visited[s] = true;
    while (!pq.empty())
    {
        int x = pq.top().second;
        // Displaying the optimal path having minimum cost
        cout << x;
        pq.pop();
        if (x == target)
            break;
        else
            cout << "--->";
        for (int i = 0; i < graph[x].size(); i++)
        {
            if (!visited[graph[x][i].second])
            {

                visited[graph[x][i].second] = true;
                pq.push(make_pair(graph[x][i].first, graph[x][i].second));
            }
        }
    }
}
int main()
{
    int v;
    cout << "Enter number of nodes: ";
    cin >> v;
    graph.resize(v);
    int target;
    cout << "Enter target node: ";
    cin >> target;
    for (int i = 0; i < v; i++)
    {
        int y = 0, dist = 0, neighb;
        int cost;
        cout << "Does node " << i << " has any neighbour with higher index?.Enter number of neighbours if yes else enter 0:\n";
        cin >> y;
        if (y != 0)
        {
            for (int j = 0; j < y; j++)
            {
                cout << "Enter neighbour no. " << j + 1 << ": ";
                cin >> neighb;
                cout << "Enter cost of node " << neighb << " from target node " << target << ": ";
                cin >> cost;
                link_nodes(i, neighb, cost);
            }
        }
    }
    int source = 0;
    cout << "\nPath is : ";
    best_first_search(source, target, v);

    return 0;
}

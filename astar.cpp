#include <bits/stdc++.h>
using namespace std;
typedef pair<int, int> pi;
vector<vector<pi>> graph;

void addedge(int x, int y, int heuristic_val)
{
    graph[x].push_back(make_pair(heuristic_val, y));
    graph[y].push_back(make_pair(heuristic_val, x));
}
void a_star_search(int actual_Src, int target, int n)
{
    vector<bool> visited(n, false);
    priority_queue<pi, vector<pi>, greater<pi>> pq;
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

            if (visited[graph[x][i].second] == false)
            {
                visited[graph[x][i].second] = true;
                pq.push(make_pair(graph[x][i].first, graph[x][i].second));
            }
        }
    }
}

int main()
{
    int source = 0;
    int i, v;
    cout << "Enter number of nodes: ";
    cin >> v;
    graph.resize(v);
    int target;
    cout << "Enter target node: ";
    cin >> target;
    for (i = 0; i < v; i++)
    {
        int y = 0, dist = 0, neighb;
        int cost, eucd, heur;
        cout << "Does node " << i << " has any neighbour with higher index?.Enter number of neighbours if yes else enter 0:\n";
        cin >> y;
        if (y != 0)
        {
            for (int j = 0; j < y; j++)
            {
                cout << "Enter neighbour no. " << j + 1 << ": ";
                cin >> neighb;
                cout << "Enter cost of node " << neighb << " from source node " << source << ": ";
                cin >> cost;
                cout << "Enter euclidean distance of node " << neighb << " from target node " << target << ": ";
                cin >> eucd;
                heur = cost + eucd;

                addedge(i, neighb, heur);
            }
        }
    }
    cout << "Optimal Path: ";
    a_star_search(source, target, v);
    return 0;
}

import java.util.*;

class State {
    int missionariesLeft, cannibalsLeft, missionariesRight, cannibalsRight, boatLeft;

    public State(int missionariesLeft, int cannibalsLeft, int missionariesRight, int cannibalsRight, int boatLeft) {
        this.missionariesLeft = missionariesLeft;
        this.cannibalsLeft = cannibalsLeft;
        this.missionariesRight = missionariesRight;
        this.cannibalsRight = cannibalsRight;
        this.boatLeft = boatLeft;
    }

    public boolean isValid() {
        return (missionariesLeft >= 0 && missionariesRight >= 0 && cannibalsLeft >= 0 && cannibalsRight >= 0
                && (missionariesLeft == 0 || missionariesLeft >= cannibalsLeft)
                && (missionariesRight == 0 || missionariesRight >= cannibalsRight));
    }

    public boolean isGoal() {
        return (missionariesLeft == 0 && cannibalsLeft == 0);
    }

    public int heuristic() {
        return missionariesLeft + cannibalsLeft; // Heuristic: Number of missionaries and cannibals on the left side
    }

    public boolean equals(State s) {
        return (this.missionariesLeft == s.missionariesLeft && this.cannibalsLeft == s.cannibalsLeft
                && this.missionariesRight == s.missionariesRight && this.cannibalsRight == s.cannibalsRight
                && this.boatLeft == s.boatLeft);
    }
}

class AStarNode implements Comparable<AStarNode> {
    State state;
    int cost;

    public AStarNode(State state, int cost) {
        this.state = state;
        this.cost = cost;
    }

    @Override
    public int compareTo(AStarNode other) {
        return Integer.compare(this.cost, other.cost);
    }
}

public class MissionariesCannibalsHeuristicSearch {

    static HashMap<State, State> parent = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("Solution using Heuristic Search:");
        heuristicSearch(3, 3);
    }

    static void heuristicSearch(int initialMissionaries, int initialCannibals) {
        PriorityQueue<AStarNode> pq = new PriorityQueue<>();
        HashSet<State> visited = new HashSet<>();

        State initialState = new State(initialMissionaries, initialCannibals, 0, 0, 1);
        pq.add(new AStarNode(initialState, initialState.heuristic()));

        while (!pq.isEmpty()) {
            State currentState = pq.poll().state;

            if (currentState.isGoal()) {
                printPath(currentState);
                return;
            }

            List<State> nextStates = generateNextStates(currentState);

            for (State nextState : nextStates) {
                if (!visited.contains(nextState) && nextState.isValid()) {
                    int newCost = currentState.heuristic() + 1; // Assuming cost of each move is 1
                    pq.add(new AStarNode(nextState, newCost));
                    visited.add(nextState);
                    parent.put(nextState, currentState);
                }
            }
        }

        System.out.println("No solution found using Heuristic Search");
    }

    static List<State> generateNextStates(State currentState) {
        List<State> nextStates = new ArrayList<>();

        int[] moveMissionaries = { 2, 0, 1, 1, 0 };
        int[] moveCannibals = { 0, 2, 0, 1, 1 };

        for (int i = 0; i < 5; i++) {
            int m = moveMissionaries[i];
            int c = moveCannibals[i];

            nextStates.add(new State(
                    currentState.missionariesLeft - m,
                    currentState.cannibalsLeft - c,
                    currentState.missionariesRight + m,
                    currentState.cannibalsRight + c,
                    1 - currentState.boatLeft));
        }

        return nextStates;
    }

    static void printPath(State goalState) {
        List<State> path = new ArrayList<>();
        State currentState = goalState;

        while (currentState != null) {
            path.add(currentState);
            currentState = parent.get(currentState);
        }

        Collections.reverse(path);

        for (State state : path) {
            System.out.println("Left Side: " + state.missionariesLeft + "M " + state.cannibalsLeft + "C | Boat (" +
                    (state.boatLeft == 1 ? "L" : "R") + ") | Right Side: " + state.missionariesRight + "M " +
                    state.cannibalsRight + "C");
        }
    }
}

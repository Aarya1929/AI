/*Initial State:

Left Side: 3 Missionaries (3M), 3 Cannibals (3C) | Boat (L) | Right Side: 0M 0C
Move 2 Missionaries and 2 Cannibals from Left to Right:

Left Side: 3M 1C | | Right Side: 0M 2C
Return with the Boat (2 Missionaries and 2 Cannibals):

Left Side: 3M 2C | Boat (L) | Right Side: 0M 1C
Move 2 Cannibals from Left to Right:

Left Side: 3M 0C | | Right Side: 0M 3C
Return with the Boat (2 Cannibals):

Left Side: 3M 1C | Boat (L) | Right Side: 0M 2C
Move 1 Missionary and 1 Cannibal from Left to Right:

Left Side: 1M 1C | | Right Side: 2M 2C
Return with the Boat (1 Missionary and 1 Cannibal):

Left Side: 2M 2C | Boat (L) | Right Side: 1M 1C
Move 2 Missionaries from Left to Right:

Left Side: 0M 2C | | Right Side: 3M 1C
Return with the Boat (2 Missionaries):

Left Side: 0M 3C | Boat (L) | Right Side: 3M 0C
Move 1 Cannibal from Left to Right:

Left Side: 0M 1C | | Right Side: 3M 2C
Return with the Boat (1 Cannibal):

Left Side: 1M 1C | Boat (L) | Right Side: 2M 2C
Move 1 Missionary and 1 Cannibal from Left to Right:

Left Side: 0M 0C | | Right Side: 3M 3C */

import java.util.*;

class State {
    int missionariesLeft, cannibalsLeft, missionariesRight, cannibalsRight;
    boolean boatLeft;

    public State(int missionariesLeft, int cannibalsLeft, int missionariesRight, int cannibalsRight, boolean boatLeft) {
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

    public boolean equals(State s) {
        return (this.missionariesLeft == s.missionariesLeft && this.cannibalsLeft == s.cannibalsLeft
                && this.missionariesRight == s.missionariesRight && this.cannibalsRight == s.cannibalsRight
                && this.boatLeft == s.boatLeft);
    }
}

public class MissionariesCannibalsBFS {

    static HashMap<State, State> parent = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("Solution using Breadth-First Search:");
        BFS(3, 3);
    }

    static void BFS(int initialMissionaries, int initialCannibals) {
        Queue<State> queue = new LinkedList<>();
        HashSet<State> visited = new HashSet<>();

        State initialState = new State(initialMissionaries, initialCannibals, 0, 0, true);
        queue.add(initialState);
        visited.add(initialState);

        while (!queue.isEmpty()) {
            State currentState = queue.poll();

            if (currentState.isGoal()) {
                printPath(currentState);
                return;
            }

            List<State> nextStates = generateNextStates(currentState);

            for (State nextState : nextStates) {
                if (!visited.contains(nextState) && nextState.isValid()) {
                    queue.add(nextState);
                    visited.add(nextState);
                    parent.put(nextState, currentState);
                }
            }
        }

        System.out.println("No solution found using BFS");
    }

    static List<State> generateNextStates(State currentState) {
        List<State> nextStates = new ArrayList<>();

        int[] moveMissionaries = { 2, 0, 1, 1, 0 };
        int[] moveCannibals = { 0, 2, 0, 1, 1 };

        for (int i = 0; i < 5; i++) {
            int m = moveMissionaries[i];
            int c = moveCannibals[i];

            if (currentState.boatLeft) {
                nextStates.add(new State(
                        currentState.missionariesLeft - m,
                        currentState.cannibalsLeft - c,
                        currentState.missionariesRight + m,
                        currentState.cannibalsRight + c,
                        false));
            } else {
                nextStates.add(new State(
                        currentState.missionariesLeft + m,
                        currentState.cannibalsLeft + c,
                        currentState.missionariesRight - m,
                        currentState.cannibalsRight - c,
                        true));
            }
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
            System.out.println("Left Side: " + state.missionariesLeft + "M " + state.cannibalsLeft + "C | "
                    + (state.boatLeft ? "Boat (L)" : "") + " | Right Side: " + state.missionariesRight + "M "
                    + state.cannibalsRight + "C");
        }
    }
}

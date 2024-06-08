import java.util.*;

class PuzzleState {
    int[][] board;

    public PuzzleState(int[][] board) {
        this.board = board;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PuzzleState that = (PuzzleState) obj;
        return Arrays.deepEquals(board, that.board);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }
}

public class EightPuzzleDFS {

    static final int SIZE = 3;

    public static void main(String[] args) {
        int[][] initialBoard = {
            {1, 2, 3},
            {4, 5, 6},
            {0, 7, 8}
        };

        int[][] goalBoard = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
        };

        PuzzleState initialState = new PuzzleState(initialBoard);
        PuzzleState goalState = new PuzzleState(goalBoard);

        System.out.println("Solution using Depth-First Search:");
        dfs(initialState, goalState);
    }

    static void dfs(PuzzleState start, PuzzleState goal) {
        Stack<PuzzleState> stack = new Stack<>();
        Map<PuzzleState, PuzzleState> parent = new HashMap<>();
        Set<PuzzleState> visited = new HashSet<>();

        stack.push(start);
        visited.add(start);

        while (!stack.isEmpty()) {
            PuzzleState currentState = stack.pop();
            if (currentState.equals(goal)) {
                printPath(currentState, parent);
                return;
            }

            List<PuzzleState> nextStates = generateNextStates(currentState);

            for (PuzzleState nextState : nextStates) {
                if (!visited.contains(nextState)) {
                    stack.push(nextState);
                    visited.add(nextState);
                    parent.put(nextState, currentState);
                }
            }
        }

        System.out.println("No solution found using DFS");
    }

    static List<PuzzleState> generateNextStates(PuzzleState state) {
        List<PuzzleState> nextStates = new ArrayList<>();
        int zeroRow = -1;
        int zeroCol = -1;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (state.board[i][j] == 0) {
                    zeroRow = i;
                    zeroCol = j;
                    break;
                }
            }
        }

        // Move blank space up
        if (zeroRow > 0) {
            int[][] newState = copyBoard(state.board);
            swap(newState, zeroRow, zeroCol, zeroRow - 1, zeroCol);
            nextStates.add(new PuzzleState(newState));
        }

        // Move blank space down
        if (zeroRow < SIZE - 1) {
            int[][] newState = copyBoard(state.board);
            swap(newState, zeroRow, zeroCol, zeroRow + 1, zeroCol);
            nextStates.add(new PuzzleState(newState));
        }

        // Move blank space left
        if (zeroCol > 0) {
            int[][] newState = copyBoard(state.board);
            swap(newState, zeroRow, zeroCol, zeroRow, zeroCol - 1);
            nextStates.add(new PuzzleState(newState));
        }

        // Move blank space right
        if (zeroCol < SIZE - 1) {
            int[][] newState = copyBoard(state.board);
            swap(newState, zeroRow, zeroCol, zeroRow, zeroCol + 1);
            nextStates.add(new PuzzleState(newState));
        }

        return nextStates;
    }

    static int[][] copyBoard(int[][] board) {
        int[][] copy = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, SIZE);
        }
        return copy;
    }

    static void swap(int[][] board, int row1, int col1, int row2, int col2) {
        int temp = board[row1][col1];
        board[row1][col1] = board[row2][col2];
        board[row2][col2] = temp;
    }

    static void printPath(PuzzleState goalState, Map<PuzzleState, PuzzleState> parent) {
        List<PuzzleState> path = new ArrayList<>();
        PuzzleState currentState = goalState;

        while (currentState != null) {
            path.add(currentState);
            currentState = parent.get(currentState);
        }

        Collections.reverse(path);

        for (PuzzleState state : path) {
            printBoard(state.board);
        }
    }

    static void printBoard(int[][] board) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}

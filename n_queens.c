#include <stdio.h>
#include <stdbool.h>

#define MAX_N 10

// Function to check if placing a queen at a specific position violates constraints
bool isSafe(int board[MAX_N][MAX_N], int row, int col, int N)
{
    // Check in the same row
    for (int i = 0; i < col; i++)
        if (board[row][i])
            return false;

    // Check upper diagonal on the left side
    for (int i = row, j = col; i >= 0 && j >= 0; i--, j--)
        if (board[i][j])
            return false;

    // Check lower diagonal on the left side
    for (int i = row, j = col; j >= 0 && i < N; i++, j--)
        if (board[i][j])
            return false;

    return true;
}

// Recursive function to solve N Queens using constraint satisfaction
bool solveNQ(int board[MAX_N][MAX_N], int col, int N)
{
    if (col >= N)
        return true;

    for (int i = 0; i < N; i++)
    {
        if (isSafe(board, i, col, N))
        {
            board[i][col] = 1;

            if (solveNQ(board, col + 1, N))
                return true;

            board[i][col] = 0; // Backtrack if placing a queen at (i, col) doesn't lead to a solution
        }
    }

    return false;
}

// Function to print the solution
void printSolution(int board[MAX_N][MAX_N], int N)
{
    for (int i = 0; i < N; i++)
    {
        for (int j = 0; j < N; j++)
            printf("%2d ", board[i][j]);
        printf("\n");
    }
}

int main()
{
    int N;

    // Take user input for the size of the chessboard
    printf("Enter the size of the chessboard (N): ");
    scanf("%d", &N);

    if (N <= 0 || N > MAX_N)
    {
        printf("Invalid size. Please enter a value between 1 and %d\n", MAX_N);
        return 1;
    }

    int board[MAX_N][MAX_N] = {0};

    if (solveNQ(board, 0, N))
    {
        printf("Solution exists:\n");
        printSolution(board, N);
    }
    else
    {
        printf("Solution does not exist");
    }

    return 0;
}
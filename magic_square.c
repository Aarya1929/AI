#include <stdio.h>
#include <stdbool.h>

int magicSquare[3][3] = {
    {2, 7, 6},
    {9, 5, 1},
    {4, 3, 8}
};

char board[3][3] = {
    {' ', ' ', ' '},
    {' ', ' ', ' '},
    {' ', ' ', ' '}
};

char currentPlayer = 'X';

void displayBoard() {
    printf("-------------\n");
    printf("| %c | %c | %c |\n", board[0][0], board[0][1], board[0][2]);
    printf("-------------\n");
    printf("| %c | %c | %c |\n", board[1][0], board[1][1], board[1][2]);
    printf("-------------\n");
    printf("| %c | %c | %c |\n", board[2][0], board[2][1], board[2][2]);
    printf("-------------\n");
}

bool isMoveValid(int row, int col) {
    return (board[row][col] == ' ');
}

bool checkWinner(char player) {
    // Check rows
    for (int i = 0; i < 3; i++) {
        if (board[i][0] == player && board[i][1] == player && board[i][2] == player)
            return true;
    }

    // Check columns
    for (int j = 0; j < 3; j++) {
        if (board[0][j] == player && board[1][j] == player && board[2][j] == player)
            return true;
    }

    // Check diagonals
    if (board[0][0] == player && board[1][1] == player && board[2][2] == player)
        return true;
    if (board[0][2] == player && board[1][1] == player && board[2][0] == player)
        return true;

    return false;
}

bool isBoardFull() {
    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
            if (board[i][j] == ' ')
                return false;
        }
    }
    return true;
}

void makeMove(int row, int col) {
    board[row][col] = currentPlayer;
    currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
}

int main() {
    int row, col;

    printf("Tic Tac Toe - Magic Square\n");
    printf("Player 1: X\n");
    printf("Player 2: O\n");
    printf("-------------------------\n");

    while (true) {
        displayBoard();

        // Input move from the current player
        printf("Player %c, enter your move (row column): ", currentPlayer);
        scanf("%d %d", &row, &col);

        // Adjust row and column to array indices
        row--;
        col--;

        // Check if the move is valid
        if (row >= 0 && row < 3 && col >= 0 && col < 3 && isMoveValid(row, col)) {
            // Make the move
            makeMove(row, col);

            // Check for a winner
            if (checkWinner('X')) {
                printf("Player X wins!\n");
                break;
            } else if (checkWinner('O')) {
                printf("Player O wins!\n");
                break;
            }

            // Check if the board is full
            if (isBoardFull()) {
                printf("It's a tie!\n");
                break;
            }
        } else {
            printf("Invalid move. Try again.\n");
        }
    }

    displayBoard();

    return 0;
}

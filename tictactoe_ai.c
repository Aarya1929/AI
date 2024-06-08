#include <stdio.h>
#include <stdbool.h>

#define BOARD_SIZE 9

char board[BOARD_SIZE] = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};

typedef struct
{
    char position[BOARD_SIZE];
    int rating;
} BoardPosition;

// checks for empty spaces in board
bool isBoardFull()
{
    for (int i = 0; i < BOARD_SIZE; i++)
    {
        if (board[i] == ' ')
            return false;
    }
    return true;
}

bool checkWinner(char player)
{
    // Check rows
    for (int i = 0; i < 3; i++)
    {
        if (board[i * 3] == player && board[i * 3 + 1] == player && board[i * 3 + 2] == player)
            return true;
    }

    // Check columns
    for (int j = 0; j < 3; j++)
    {
        if (board[j] == player && board[j + 3] == player && board[j + 6] == player)
            return true;
    }

    // Check diagonals
    if (board[0] == player && board[4] == player && board[8] == player)
        return true;
    if (board[2] == player && board[4] == player && board[6] == player)
        return true;

    return false;
}

// evaluates current board position
int evaluateBoard(char player)
{
    if (checkWinner(player))
        return 1;
    else if (checkWinner((player == 'X') ? 'O' : 'X'))
        return -1;
    else
        return 0;
}

// Minimax algorithm
int minimax(char player)
{
    if (checkWinner('X'))
        return 1;
    else if (checkWinner('O'))
        return -1;
    else if (isBoardFull())
        return 0;

    int bestRating;
    if (player == 'X')
        bestRating = -2;
    else
        bestRating = 2;

    for (int i = 0; i < BOARD_SIZE; i++)
    {
        if (board[i] == ' ')
        {
            board[i] = player;
            int rating = minimax((player == 'X') ? 'O' : 'X');
            board[i] = ' ';

            if ((player == 'X' && rating > bestRating) || (player == 'O' && rating < bestRating))
                bestRating = rating;
        }
    }

    return bestRating;
}

// AI move
void makeMove(char player)
{
    int bestMove = -1;
    int bestRating = (player == 'X') ? -2 : 2;

    for (int i = 0; i < BOARD_SIZE; i++)
    {
        if (board[i] == ' ')
        {
            board[i] = player;
            int rating = minimax((player == 'X') ? 'O' : 'X');
            board[i] = ' ';

            if ((player == 'X' && rating > bestRating) || (player == 'O' && rating < bestRating))
            {
                bestMove = i;
                bestRating = rating;
            }
        }
    }

    board[bestMove] = player;
}

// board
void displayBoard()
{
    printf("-------------\n");
    for (int i = 0; i < BOARD_SIZE; i++)
    {
        printf("| %c ", board[i]);
        if ((i + 1) % 3 == 0)
            printf("|\n-------------\n");
    }
}

int main()
{
    printf("Tic Tac Toe - AI using Minimax Algorithm\n");
    printf("Player 1: X\n");
    printf("Player 2: O (AI)\n");
    printf("-------------------------\n");

    char currentPlayer = 'X';

    while (true)
    {
        displayBoard();

        if (currentPlayer == 'X')
        {
            int move;
            printf("Player X, enter your move (1-9): ");
            scanf("%d", &move);
            move--;

            if (board[move] == ' ')
                board[move] = currentPlayer;
            else
            {
                printf("Invalid move. Try again.\n");
                continue;
            }
        }
        else
        {
            makeMove(currentPlayer);
        }

        if (checkWinner(currentPlayer))
        {
            displayBoard();
            printf("Player %c wins!\n", currentPlayer);
            break;
        }
        else if (isBoardFull())
        {
            displayBoard();
            printf("It's a draw!\n");
            break;
        }

        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    return 0;
}

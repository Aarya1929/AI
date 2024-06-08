#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <time.h>

char board[9] = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}; // array fr board

// basic numbering of squares
void showInstructions()
{
    printf("Choose a cell numbered from 1 to 9 as below and play\n\n");
    printf("   |   |   \n");
    printf(" %d | %d | %d \n", 1, 2, 3);
    printf("   |   |   \n");
    printf("---------------------------\n");
    printf("   |   |   \n");
    printf(" %d | %d | %d \n", 4, 5, 6);
    printf("   |   |   \n");
    printf("---------------------------\n");
    printf("   |   |   \n");
    printf(" %d | %d | %d \n", 7, 8, 9);
    printf("   |   |   \n\n");
    printf("\n");
}

// print current board everytime
void show_board()
{
    printf("   |   |   \n");
    printf(" %c | %c | %c \n", board[0], board[1], board[2]);
    printf("   |   |   \n");
    printf("---------------------------\n");
    printf("   |   |   \n");
    printf(" %c | %c | %c \n", board[3], board[4], board[5]);
    printf("   |   |   \n");
    printf("---------------------------\n");
    printf("   |   |   \n");
    printf(" %c | %c | %c \n", board[6], board[7], board[8]);
    printf("   |   |   \n");
    printf("\n");
}

// get player choice
void get_1_choice()
{
    while (1)
    {
        printf("Select your desired position (1-9): ");
        int choice;
        scanf("%d", &choice);
        choice = choice - 1;
        if (choice < 0 || choice > 8)
        {
            printf("Invalid choice. Select from (1-9)\n");
        }
        else if (board[choice] != ' ')
        {
            printf("Please select an empty position\n");
        }
        else
        {
            board[choice] = 'X';
            break;
        }
    }
}

// random;y generate no. for computer's turn
void get_com_choice()
{
    srand(time(0)); // generates different sequence everytime program runs
    int choice;
    do
    {
        choice = rand() % 9;
    } while (board[choice] != ' ');

    board[choice] = 'O';
}

// count no. of occurrences of each symbol
int count_board(char symbol)
{
    int total = 0;
    for (int i = 0; i < 9; ++i)
    {
        if (board[i] == symbol)
        {
            total++;
        }
    }
    return total;
}

// winner conditions
char check_winner()
{
    if (board[0] == board[1] && board[1] == board[2] && board[0] != ' ')
    {
        return board[0];
    }
    if (board[3] == board[4] && board[4] == board[5] && board[3] != ' ')
    {
        return board[3];
    }
    if (board[6] == board[7] && board[7] == board[8] && board[6] != ' ')
    {
        return board[6];
    }

    if (board[0] == board[3] && board[3] == board[6] && board[0] != ' ')
    {
        return board[0];
    }
    if (board[1] == board[4] && board[4] == board[7] && board[1] != ' ')
    {
        return board[1];
    }
    if (board[2] == board[5] && board[5] == board[8] && board[2] != ' ')
    {
        return board[2];
    }

    if (board[0] == board[4] && board[4] == board[8] && board[0] != ' ')
    {
        return board[0];
    }
    if (board[2] == board[4] && board[4] == board[6] && board[2] != ' ')
    {
        return board[2];
    }

    if (count_board('X') + count_board('O') < 9)
    {
        return 'C';
    }
    else
    {
        return 'D';
    }
}

// actual playing
void computer_vs_player()
{
    char name1[100];
    printf("Enter player name: ");
    scanf("%s", name1);
    printf("\n");
    while (1)
    {
        show_board();
        if (count_board('X') == count_board('O'))
        {
            printf("%s's turn:\n", name1);
            get_1_choice();
        }
        else
        {
            printf("Computer's turn:\n");
            get_com_choice();
        }
        char winner = check_winner();
        if (winner == 'X')
        {
            show_board();
            printf("%s won the game.\n", name1);
            break;
        }
        else if (winner == 'O')
        {
            show_board();
            printf("Computer won the game.\n");
            break;
        }
        else if (winner == 'D')
        {
            show_board();
            printf("Game Draw\n");
            break;
        }
    }
}

int main()
{
    showInstructions();
    show_board();
    computer_vs_player();
    return 0;
}

#include <stdio.h>
#include <stdbool.h>

#define SIZE 4

typedef struct
{
    int x, y; // Agent's position
} Agent;

typedef struct
{
    bool hasPit;
    bool hasWumpus;
    bool hasGold;
} Room;

typedef struct
{
    Agent agent;
    Room cave[SIZE][SIZE];
    bool isAlive;
    bool hasGold;
} WumpusWorld;

// Initialize the Wumpus World
void initializeWorld(WumpusWorld *world)
{
    world->agent.x = 0;
    world->agent.y = 0;
    world->isAlive = true;
    world->hasGold = false;

    // Initialize rooms
    for (int i = 0; i < SIZE; ++i)
    {
        for (int j = 0; j < SIZE; ++j)
        {
            world->cave[i][j].hasPit = false;
            world->cave[i][j].hasWumpus = false;
            world->cave[i][j].hasGold = false;
        }
    }

    // Place hazards and gold (for simplicity, this can be randomized in a real implementation)
    world->cave[1][1].hasPit = true;
    world->cave[2][2].hasWumpus = true;
    world->cave[3][3].hasGold = true;
}

// Display the current state of the world
void displayWorld(const WumpusWorld *world)
{
    printf("Current state of the world:\n");

    for (int i = 0; i < SIZE; ++i)
    {
        for (int j = 0; j < SIZE; ++j)
        {
            if (world->agent.x == i && world->agent.y == j)
            {
                printf("A ");
            }
            else if (world->cave[i][j].hasGold)
            {
                printf("G ");
            }
            else if (world->cave[i][j].hasWumpus)
            {
                printf("W ");
            }
            else if (world->cave[i][j].hasPit)
            {
                printf("P ");
            }
            else
            {
                printf(". ");
            }
        }
        printf("\n");
    }

    if (world->hasGold)
    {
        printf("Agent has the gold!\n");
    }

    if (!world->isAlive)
    {
        printf("Agent is dead!\n");
    }
}

// Move the agent
void moveAgent(WumpusWorld *world, int newX, int newY)
{
    if (newX >= 0 && newX < SIZE && newY >= 0 && newY < SIZE)
    {
        world->agent.x = newX;
        world->agent.y = newY;
    }
}

// Handle the agent grabbing gold
void grabGold(WumpusWorld *world)
{
    if (world->cave[world->agent.x][world->agent.y].hasGold)
    {
        world->cave[world->agent.x][world->agent.y].hasGold = false;
        world->hasGold = true;
        printf("Agent grabbed the gold!\n");
    }
}

// Handle the agent shooting the wumpus
void shootWumpus(WumpusWorld *world, int targetX, int targetY)
{
    if (world->cave[targetX][targetY].hasWumpus)
    {
        world->cave[targetX][targetY].hasWumpus = false;
        printf("Agent killed the wumpus!\n");
    }
}

// Simulate the agent's actions
void simulateAgentActions(WumpusWorld *world)
{
    // Move to the right
    moveAgent(world, world->agent.x, world->agent.y + 1);
    displayWorld(world);

    // Grab the gold if present
    grabGold(world);
    displayWorld(world);

    // Move down
    moveAgent(world, world->agent.x + 1, world->agent.y);
    displayWorld(world);

    // Shoot the wumpus
    shootWumpus(world, 2, 2);
    displayWorld(world);
}

int main()
{
    WumpusWorld world;

    initializeWorld(&world);
    displayWorld(&world);

    simulateAgentActions(&world);

    return 0;
}

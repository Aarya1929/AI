import java.util.Random;

class VacuumWorld {
    static class Environment {
        boolean[] isDirty = { true, true }; // Room 0 and Room 1
        int vacuumLocation; // 0 for Room 0, 1 for Room 1

        Environment() {
            Random random = new Random();
            vacuumLocation = random.nextInt(2); // Randomly initialize vacuum location
        }

        void clean() {
            System.out.println("Cleaning Room " + vacuumLocation);
            isDirty[vacuumLocation] = false;
        }

        void move() {
            System.out.println("Moving to Room " + (1 - vacuumLocation));
            vacuumLocation = 1 - vacuumLocation; // Move to the other room
        }

        boolean isDirty() {
            return isDirty[vacuumLocation];
        }

        void printState() {
            System.out.println("Room 0: " + (isDirty[0] ? "Dirty" : "Clean") +
                    " | Room 1: " + (isDirty[1] ? "Dirty" : "Clean") +
                    " | Vacuum Location: Room " + vacuumLocation);
        }
    }

    static class VacuumAgent {
        void clean(Environment environment) {
            if (environment.isDirty()) {
                environment.clean();
            } else {
                System.out.println("No dirt to clean in Room " + environment.vacuumLocation);
            }
        }

        void move(Environment environment) {
            environment.move();
        }
    }

    public static void main(String[] args) {
        Environment environment = new Environment();
        VacuumAgent vacuumAgent = new VacuumAgent();

        for (int i = 0; i < 5; i++) {
            System.out.println("Step " + (i + 1));
            environment.printState();
            vacuumAgent.clean(environment);
            vacuumAgent.move(environment);
        }
    }
}

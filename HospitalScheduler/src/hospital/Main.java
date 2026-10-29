package hospital;

import java.util.Scanner;

public class Main {

    private static TaskDataSet data;
    private static ResourcePool resources;
    private static Scheduler scheduler;

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        int choice;

        do {

            displayMenu();

            System.out.print("Enter your choice: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
                System.out.print("Enter your choice: ");
            }

            choice = scanner.nextInt();

            switch (choice) {

            case 1:
                generateDataset();
                break;

            case 2:
                displayDataset();
                break;

            case 3:
                processNextEvent();
                break;

            case 4:
                updateTaskPriority();
                break;

            case 5:
                viewWaitingQueue();
                break;

            case 6:
                viewActiveTasks();
                break;

            case 7:
                viewTaskStatus();
                break;

            case 8:
                runRemainingSimulation();
                break;

            case 9:
                viewReport();
                break;

            case 10:
                runPerformanceTest();
                break;

            case 0:

                System.out.println(
                        "\nExiting system...");

                break;

            default:

                System.out.println(
                        "\nInvalid option. Please choose 0 to 10.");
        }
        } while (choice != 0);

        scanner.close();

        System.out.println("Program terminated.");
    }


    private static void displayMenu() {

        System.out.println();
        System.out.println(
                "===============================================");

        System.out.println(
                " HOSPITAL RESOURCE PLANNING & PRIORITY SCHEDULER");

        System.out.println(
                "===============================================");

        System.out.println(
                "1. Generate New Task Dataset");

        System.out.println(
                "2. Display Generated Dataset");

        System.out.println(
                "3. Process Next Simulation Event");

        System.out.println(
                "4. Update Waiting Task Urgency");

        System.out.println(
                "5. View Waiting Priority Queue");

        System.out.println(
                "6. View Active Tasks");

        System.out.println(
                "7. View Task Status");

        System.out.println(
                "8. Run Remaining Simulation");

        System.out.println(
                "9. View Scheduling Report");

        System.out.println(
                "10. Run Performance Analysis");

        System.out.println(
                "0. Exit");

        System.out.println(
                "===============================================");
    }

    private static void generateDataset() {

        System.out.println("\n--- Generate Task Dataset ---");

        System.out.print("Enter number of tasks: ");

        int numberOfTasks = scanner.nextInt();

        System.out.print("Enter random seed: ");

        long seed = scanner.nextLong();

        data = DatasetGenerator.generate(numberOfTasks, seed);

        resources = createResourcePool();

        scheduler = new Scheduler(data, resources, true);

        System.out.println(
            "\nDataset generated successfully with "
            + numberOfTasks
            + " tasks."
        );

        System.out.println("Random seed used: " + seed);
    }


    private static void displayDataset() {

        System.out.println("\n--- Generated Task Dataset ---");

        if (data == null) {

            System.out.println(
                "No dataset has been generated yet."
            );

            System.out.println(
                "Please select Option 1 first."
            );

            return;
        }

        data.printDataSet();
    }


    private static void processNextEvent() {

        System.out.println(
                "\n--- Process Next Simulation Event ---");

        if (scheduler == null) {

            System.out.println(
                    "Please generate a dataset first.");

            return;
        }

        System.out.println(
                "Current simulation time: "
                + scheduler.getCurrentTime());

        scheduler.processNextEvent();
    }
    
    private static void viewWaitingQueue() {

        if (scheduler == null) {

            System.out.println(
                    "\nPlease generate a dataset first.");

            return;
        }

        scheduler.printWaitingQueue();
    }
    
    private static void viewActiveTasks() {

        if (scheduler == null) {

            System.out.println(
                    "\nPlease generate a dataset first.");

            return;
        }

        scheduler.printActiveTasks();
    }
    
    private static void runRemainingSimulation() {

        System.out.println(
                "\n--- Run Remaining Simulation ---");

        if (scheduler == null) {

            System.out.println(
                    "Please generate a dataset first.");

            return;
        }

        if (scheduler.isFinished()) {

            System.out.println(
                    "Simulation has already finished.");

            return;
        }

        scheduler.run();

        System.out.println(
                "\nRemaining simulation completed.");
    }
    private static void updateTaskPriority() {

        System.out.println("\n--- Update Task Priority ---");

        if (scheduler == null) {

            System.out.println(
                "No scheduler has been initialized."
            );

            System.out.println(
                "Please generate a dataset first."
            );

            return;
        }

        System.out.print("Enter Task ID: ");

        int taskId = scanner.nextInt();

        System.out.print(
            "Enter new urgency level (1-5): "
        );

        int newUrgency = scanner.nextInt();

        if (newUrgency < 1 || newUrgency > 5) {

            System.out.println(
                "Urgency must be between 1 and 5."
            );

            return;
        }

        scheduler.updateTaskPriority(
            taskId,
            newUrgency
        );
    }


    private static void viewTaskStatus() {

        System.out.println("\n--- Task Status ---");

        if (scheduler == null) {

            System.out.println(
                "No scheduler has been initialized."
            );

            System.out.println(
                "Please generate a dataset first."
            );

            return;
        }

        System.out.print("Enter Task ID: ");

        int taskId = scanner.nextInt();

        scheduler.printTaskStatus(taskId);
    }


    private static void viewReport() {

        System.out.println("\n--- Scheduling Report ---");

        if (scheduler == null) {

            System.out.println(
                "No scheduler has been initialized."
            );

            System.out.println(
                "Please generate a dataset first."
            );

            return;
        }

        scheduler.printReport();
    }


    private static void runPerformanceTest() {

        System.out.println(
            "\n--- Performance Analysis ---"
        );

        PerformanceTest.run();
    }


    private static ResourcePool createResourcePool() {

        ResourcePool pool = new ResourcePool();

        int id = 1;

        pool.addResource(
            new Resource(id++, "Doctor")
        );

        pool.addResource(
            new Resource(id++, "Doctor")
        );

        pool.addResource(
            new Resource(id++, "Doctor")
        );


        pool.addResource(
            new Resource(id++, "Nurse")
        );

        pool.addResource(
            new Resource(id++, "Nurse")
        );

        pool.addResource(
            new Resource(id++, "Nurse")
        );

        pool.addResource(
            new Resource(id++, "Nurse")
        );


        pool.addResource(
            new Resource(id++, "DiagnosticMachine")
        );

        pool.addResource(
            new Resource(id++, "DiagnosticMachine")
        );


        pool.addResource(
            new Resource(id++, "Ambulance")
        );

        pool.addResource(
            new Resource(id++, "Ambulance")
        );


        pool.addResource(
            new Resource(id++, "Bed")
        );

        pool.addResource(
            new Resource(id++, "Bed")
        );

        pool.addResource(
            new Resource(id++, "Bed")
        );

        pool.addResource(
            new Resource(id++, "Bed")
        );

        return pool;
    }
}

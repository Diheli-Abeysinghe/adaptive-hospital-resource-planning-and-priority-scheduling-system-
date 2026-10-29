package hospital;

public class PerformanceTest {

    public static void run() {

        System.out.println();
        System.out.println(
                "==========================================");

        System.out.println(
                "       PERFORMANCE EVALUATION");

        System.out.println(
                "==========================================");

        System.out.println(
                "Tasks | Runtime(ns) | Runtime(ms) | " +
                "Avg Wait | Deadline Miss %");

        runTest(50);
        runTest(100);
        runTest(250);
        runTest(500);
        runTest(1000);
        runTest(2500);
        runTest(5000);
    }

    private static void runTest(
            int taskCount) {

        TaskDataSet data =
                DatasetGenerator.generate(
                taskCount,
                12345L);

        ResourcePool resources =
                createStandardResourcePool();

        Scheduler scheduler =
                new Scheduler(
                data,
                resources,
                false);

        long start =
                System.nanoTime();

        scheduler.run();

        long end =
                System.nanoTime();

        long runtime =
                end - start;

        double milliseconds =
                (double) runtime
                / 1000000.0;

        SchedulerMetrics metrics =
                scheduler.getMetrics();

        System.out.println(
                taskCount +
                " | " +
                runtime +
                " | " +
                milliseconds +
                " | " +
                metrics.getAverageWaitingTime() +
                " | " +
                metrics.getDeadlineMissRate());
    }

    private static ResourcePool
            createStandardResourcePool() {

        ResourcePool pool =
                new ResourcePool();

        int id = 1;

        
        pool.addResource(
                new Resource(
                id++,
                "Doctor"));

        pool.addResource(
                new Resource(
                id++,
                "Doctor"));

        pool.addResource(
                new Resource(
                id++,
                "Doctor"));

       
        pool.addResource(
                new Resource(
                id++,
                "Nurse"));

        pool.addResource(
                new Resource(
                id++,
                "Nurse"));

        pool.addResource(
                new Resource(
                id++,
                "Nurse"));

        pool.addResource(
                new Resource(
                id++,
                "Nurse"));

        //Diagnostic machines
        pool.addResource(
                new Resource(
                id++,
                "DiagnosticMachine"));

        pool.addResource(
                new Resource(
                id++,
                "DiagnosticMachine"));

        
        pool.addResource(
                new Resource(
                id++,
                "Ambulance"));

        pool.addResource(
                new Resource(
                id++,
                "Ambulance"));

        
        pool.addResource(
                new Resource(
                id++,
                "Bed"));

        pool.addResource(
                new Resource(
                id++,
                "Bed"));

        pool.addResource(
                new Resource(
                id++,
                "Bed"));

        pool.addResource(
                new Resource(
                id++,
                "Bed"));

        return pool;
    }
}
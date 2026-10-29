package hospital;

public class SchedulerMetrics {
	private int totalTasks;
	private int processedTasks;
	private long totalWaitingTime;
	private int deadlineMisses;
	private int starvationRisks;
	private long totalProcessingTime;
	
	public SchedulerMetrics(int totalTasks) {
		this.totalTasks = totalTasks;
		
		processedTasks = 0;
		totalWaitingTime = 0;
		deadlineMisses = 0;
		starvationRisks = 0;
		totalProcessingTime = 0;
	}
	
	public void recordCompletion(Task task) {
		processedTasks++;
		totalWaitingTime += task.getWaitingTime();
		
		totalProcessingTime += task.getDuration();
		
		if(task.isDeadlineMissed()) {
			deadlineMisses++;
		}
		
		if(task.hasStarvationRisk()) {
			starvationRisks++;
		}
	}
	
	public int getProcessedTasks() {
		return processedTasks;
	}
	
	public double getAverageWaitingTime() {
		if(processedTasks == 0) {
			return 0.0;
		}
		return (double)totalWaitingTime / processedTasks;
	}
	
	public double getDeadlineMissRate() {
		if(processedTasks == 0) {
			return 0.0;
		}
		return((double)deadlineMisses / processedTasks) *100.0;
	}
	
	public int getDeadlineMisses() {
		return deadlineMisses;
	}
	
	public int getStarvationRisks() {
		return starvationRisks;
	}
	
	public long getTotalProcessingTime() {
		return totalProcessingTime;
	}
	
	public void printReport(int simulationTime, ResourcePool resourcePool) {
		System.out.println();
        System.out.println(
                "==========================================");

        System.out.println(
                "           SCHEDULER REPORT");

        System.out.println(
                "==========================================");

        System.out.println(
                "Total Tasks: " +
                totalTasks);

        System.out.println(
                "Tasks Processed: " +
                processedTasks);

        System.out.println(
                "Average Waiting Time: " +
                getAverageWaitingTime());

        System.out.println(
                "Deadline Misses: " +
                deadlineMisses);

        System.out.println(
                "Deadline Miss Rate: " +
                getDeadlineMissRate() +
                "%");

        System.out.println(
                "Starvation Risks Detected: " +
                starvationRisks);

        System.out.println(
                "Total Processing Time: " +
                totalProcessingTime);

        System.out.println(
                "Simulation Duration: " +
                simulationTime);

        double throughput = 0.0;

        if (simulationTime > 0) {

            throughput =
                    (double) processedTasks
                    / simulationTime;
        }

        System.out.println(
                "Throughput: " +
                throughput +
                " tasks/minute");

        double utilization = 0.0;

        if (resourcePool.getResourceCount() > 0
                && simulationTime > 0) {

            utilization =
                    ((double)
                    resourcePool.getTotalBusyTime()
                    /
                    ((double)
                    resourcePool.getResourceCount()
                    * simulationTime))
                    * 100.0;
        }

        System.out.println(
                "Resource Utilization: " +
                utilization +
                "%");

        System.out.println(
                "==========================================");
    }
}

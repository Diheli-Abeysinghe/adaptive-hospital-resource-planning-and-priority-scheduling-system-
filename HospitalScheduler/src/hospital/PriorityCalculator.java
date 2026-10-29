package hospital;

public class PriorityCalculator {

    private static final int URGENCY_WEIGHT = 100;
    private static final int AGEING_WEIGHT = 2;
    private static final int DEADLINE_WEIGHT = 1;

    private static final int STARVATION_THRESHOLD = 120;

    public static int calculatePriority(
            Task task,
            int currentTime) {

        int waitingTime =
                currentTime -
                task.getArrivalTime();

        if (waitingTime < 0) {
            waitingTime = 0;
        }

        int deadlineRemaining =
                task.getDeadline() -
                currentTime;

        int deadlineScore;

        if (deadlineRemaining <= 0) {

            deadlineScore = 1000;

        } else {

            deadlineScore =
                    1000 /
                    (deadlineRemaining + 1);
        }

        int urgencyScore =
                task.getUrgency() *
                URGENCY_WEIGHT;

        int ageingScore =
                waitingTime *
                AGEING_WEIGHT;

        int finalScore =
                urgencyScore +
                ageingScore +
                (deadlineScore *
                DEADLINE_WEIGHT);

        return finalScore;
    }

    public static void updateTaskPriority(
            Task task,
            int currentTime,
            CustomPriorityQueue queue) {

        int waitingTime =
                currentTime -
                task.getArrivalTime();

        if (waitingTime < 0) {
            waitingTime = 0;
        }

        task.setWaitingTime(waitingTime);

        if (waitingTime >= STARVATION_THRESHOLD) {

            task.setStarvationRisk(true);
        }

        int newPriority =
                calculatePriority(
                task,
                currentTime);

        queue.updatePriority(
                task,
                newPriority);
    }

    public static boolean isStarvationRisk(
            Task task,
            int currentTime) {

        int waiting =
                currentTime -
                task.getArrivalTime();

        return waiting >=
                STARVATION_THRESHOLD;
    }
}
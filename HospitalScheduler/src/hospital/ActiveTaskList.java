package hospital;

public class ActiveTaskList {

    private ActiveTaskNode head;

    private int size;

    public ActiveTaskList() {

        head = null;
        size = 0;
    }

    public void add(Task task) {

        ActiveTaskNode newNode =
                new ActiveTaskNode(task);

        if (head == null) {

            head = newNode;

        } else {

            ActiveTaskNode current = head;

            while (current.getNext() != null) {
                current = current.getNext();
            }

            current.setNext(newNode);
        }

        size++;
    }

    public void removeCompletedTasks(
            int currentTime,
            ResourcePool resourcePool,
            SchedulerMetrics metrics) {

        ActiveTaskNode current = head;
        ActiveTaskNode previous = null;

        while (current != null) {

            Task task = current.getTask();

            if (task.getCompletionTime()
                    <= currentTime) {

                task.setStatus(
                        Task.COMPLETED);

                if (task.getCompletionTime()
                        > task.getDeadline()) {

                    task.setDeadlineMissed(true);
                }

                resourcePool.releaseResources(task);

                metrics.recordCompletion(task);

                if (previous == null) {

                    head = current.getNext();

                } else {

                    previous.setNext(
                            current.getNext());
                }

                size--;

                if (previous == null) {

                    current = head;

                } else {

                    current =
                            previous.getNext();
                }

            } else {

                previous = current;
                current = current.getNext();
            }
        }
    }

    public int getSize() {
        return size;
    }

    public int findNextCompletionTime() {

        if (head == null) {
            return -1;
        }

        ActiveTaskNode current = head;

        int earliest =
                current.getTask()
                .getCompletionTime();

        current = current.getNext();

        while (current != null) {

            int completion =
                    current.getTask()
                    .getCompletionTime();

            if (completion < earliest) {
                earliest = completion;
            }

            current = current.getNext();
        }

        return earliest;
    }

    public void printActiveTasks() {

        ActiveTaskNode current = head;

        while (current != null) {

            Task task = current.getTask();

            System.out.println(
                    "Running Task " +
                    task.getTaskId() +
                    " | Completion Time: " +
                    task.getCompletionTime()
            );

            current = current.getNext();
        }
    }
}

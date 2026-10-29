package hospital;

public class CustomPriorityQueue {

    private HeapNode root;
    private int size;

    public CustomPriorityQueue() {

        root = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void insert(Task task) {

        HeapNode newNode =
                new HeapNode(task);

        task.setHeapNode(newNode);

        size++;

        if (root == null) {
            root = newNode;
            return;
        }

        HeapNode parent =
                findParentForIndex(size);

        newNode.setParent(parent);

        if (isLeftChild(size)) {
            parent.setLeft(newNode);
        } else {
            parent.setRight(newNode);
        }

        bubbleUp(newNode);
    }

    public Task extractHighestPriority() {

        if (root == null) {
            return null;
        }

        Task highest =
                root.getTask();

        highest.setHeapNode(null);

        if (size == 1) {

            root = null;
            size = 0;

            return highest;
        }

        HeapNode last =
                getNodeAtIndex(size);

        Task replacement =
                last.getTask();

        root.setTask(replacement);

        replacement.setHeapNode(root);

        HeapNode parent =
                last.getParent();

        if (parent.getLeft() == last) {
            parent.setLeft(null);
        } else {
            parent.setRight(null);
        }

        size--;

        bubbleDown(root);

        return highest;
    }

    public void updatePriority(
            Task task,
            int newPriority) {

        if (task == null) {
            return;
        }

        HeapNode node =
                task.getHeapNode();

        if (node == null) {
            return;
        }

        int oldPriority =
                task.getPriority();

        task.setPriority(newPriority);

        if (newPriority > oldPriority) {

            bubbleUp(node);

        } else if (newPriority < oldPriority) {

            bubbleDown(node);
        }
    }

    public boolean contains(Task task) {

        if (task == null) {
            return false;
        }

        return task.getHeapNode() != null;
    }

    private void bubbleUp(HeapNode node) {

        HeapNode current = node;

        while (current.getParent() != null) {

            HeapNode parent =
                    current.getParent();

            if (higherPriority(
                    current.getTask(),
                    parent.getTask())) {

                swapTasks(current, parent);

                current = parent;

            } else {

                break;
            }
        }
    }

    private void bubbleDown(HeapNode node) {

        HeapNode current = node;

        while (current != null) {

            HeapNode highest = current;

            HeapNode left =
                    current.getLeft();

            HeapNode right =
                    current.getRight();

            if (left != null &&
                    higherPriority(
                    left.getTask(),
                    highest.getTask())) {

                highest = left;
            }

            if (right != null &&
                    higherPriority(
                    right.getTask(),
                    highest.getTask())) {

                highest = right;
            }

            if (highest != current) {

                swapTasks(current, highest);

                current = highest;

            } else {

                break;
            }
        }
    }

    private boolean higherPriority(
            Task first,
            Task second) {

        if (first.getPriority()
                > second.getPriority()) {

            return true;
        }

        if (first.getPriority()
                < second.getPriority()) {

            return false;
        }

        

        if (first.getDeadline()
                < second.getDeadline()) {

            return true;
        }

        if (first.getDeadline()
                > second.getDeadline()) {

            return false;
        }

        

        if (first.getArrivalTime()
                < second.getArrivalTime()) {

            return true;
        }

        return false;
    }

    private void swapTasks(
            HeapNode first,
            HeapNode second) {

        Task temp =
                first.getTask();

        first.setTask(second.getTask());
        second.setTask(temp);

        first.getTask()
                .setHeapNode(first);

        second.getTask()
                .setHeapNode(second);
    }

    private HeapNode findParentForIndex(int index) {

        

        int mask = highestPowerOfTwo(index);

        mask = mask / 2;

        HeapNode current = root;

        while (mask > 1) {

            if ((index & mask) == 0) {

                current =
                        current.getLeft();

            } else {

                current =
                        current.getRight();
            }

            mask = mask / 2;
        }

        return current;
    }

    private HeapNode getNodeAtIndex(int index) {

        if (index <= 0 || root == null) {
            return null;
        }

        int mask =
                highestPowerOfTwo(index);

        mask = mask / 2;

        HeapNode current = root;

        while (mask > 0) {

            if ((index & mask) == 0) {

                current =
                        current.getLeft();

            } else {

                current =
                        current.getRight();
            }

            mask = mask / 2;
        }

        return current;
    }

    private boolean isLeftChild(int index) {

        return (index & 1) == 0;
    }

    private int highestPowerOfTwo(int number) {

        int power = 1;

        while (power <= number / 2) {
            power = power * 2;
        }

        return power;
    }

    public void printHeap() {

        if (root == null) {

            System.out.println(
                    "Priority Queue is empty.");

            return;
        }

        printLevel(root, 0);
    }

    private void printLevel(
            HeapNode node,
            int level) {

        if (node == null) {
            return;
        }

        System.out.println(
                "Level " + level +
                " | Task " +
                node.getTask().getTaskId() +
                " | Priority " +
                node.getTask().getPriority()
        );

        printLevel(
                node.getLeft(),
                level + 1);

        printLevel(
                node.getRight(),
                level + 1);
    }
}


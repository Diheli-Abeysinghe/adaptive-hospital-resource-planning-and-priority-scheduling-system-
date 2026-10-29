package hospital;

public class TaskRegistry {
	private TaskNode head;
	private TaskNode tail;
	
	private int size;
	
	public TaskRegistry() {
		head = null;
		tail = null;
		size = 0;
	}
	
	public void add (Task task) {
		TaskNode newNode = new TaskNode(task);
		
		if(head == null) {
			head = newNode;
			tail = newNode;
		}
		else {
			tail.setNext(newNode);
			tail = newNode;
		}
		size++;
	}
	
	public Task findTask(int taskId) {
		TaskNode current = head;
		while(current != null) {
			if(current.getTask().getTaskId() == taskId) {
				return current.getTask();
			}
			current = current.getNext();
		}
		return null;
	}
	
	public TaskNode getHead() {
		return head;
	}
	
	public int getSize() {
		return size;
	}
	public int countCompleted() {
		int count = 0;
		TaskNode current = head;
		
		while(current != null) {
			if(current.getTask().getStatus() == Task.COMPLETED) {
				count++;
			}
			current = current.getNext();
		}
		return count;
	}
	
	public void printAllTasks() {
		TaskNode current = head;
		while(current != null) {
			current.getTask().printDetails();
			current = current.getNext();
		}
	}
}

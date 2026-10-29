package hospital;

public class TaskDataSet {
	TaskNode head;
	TaskNode tail;
	int size;
	
	public TaskDataSet() {
		head = null;
		tail = null;
		size = 0;
	}
	
	public void add(Task task) {
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
	
	public TaskNode getHead() {
		return head;
	}
	
	public int getSize() {
		return size;
	}
	
	public void printDataSet() {
		TaskNode current = head;
		
		while(current!=null) {
			current.getTask().printDetails();
			current = current.getNext();
		}
	}
}

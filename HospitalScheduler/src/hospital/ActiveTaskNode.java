package hospital;

public class ActiveTaskNode {
	
	private Task task;
	private ActiveTaskNode next;
	
	public ActiveTaskNode(Task task) {
		this.task = task;
		this.next = null;
	}
	
	public Task getTask() {
		return task;
	}
	
	public ActiveTaskNode getNext() {
		return next;
	}
	
	public void setNext(ActiveTaskNode next) {
		this.next = next;
	}
}

package hospital;

public class HeapNode {
	private Task task;
	private HeapNode parent;
	private HeapNode left;
	private HeapNode right;
	
	public HeapNode(Task task) {
		this.task = task;
		this.parent = null;
		this.left = null;
		this.right = null;
	}
	
	public Task getTask() {
		return task;
	}
	
	public void setTask(Task task) {
		this.task = task;
	}
	
	public HeapNode getParent() {
		return parent;
	}
	
	public void setParent(HeapNode parent) {
		this.parent = parent;
	}
	
	public HeapNode getLeft() {
		return left;
	}
	
	public void setLeft(HeapNode left) {
		this.left = left;
	}
	
	public HeapNode getRight() {
		return right;
	}
	
	public void setRight(HeapNode right) {
		this.right = right;
	}
}

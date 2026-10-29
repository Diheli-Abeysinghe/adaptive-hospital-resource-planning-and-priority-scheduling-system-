package hospital;

public class AllocationNode {
	private Resource resource;
	private AllocationNode next;
	
	public AllocationNode(Resource resource) {
		this.resource = resource;
		this.next = null;
	}
	public Resource getResource() {
		return resource;
	}
	
	public AllocationNode getNext() {
		return next;
	}
	
	public void setNext(AllocationNode next) {
		this.next = next;
	}
}

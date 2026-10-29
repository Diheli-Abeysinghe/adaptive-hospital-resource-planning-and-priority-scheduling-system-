package hospital;

public class ResourceNode {
	private Resource resource;
	private ResourceNode next;
	
	public ResourceNode(Resource resource) {
		this.resource = resource;
		this.next = null;
	}
	
	public Resource getResource() {
		return resource;
	}
	
	public ResourceNode getNext() {
		return next;
	}
	
	public void setNext(ResourceNode next) {
		this.next = next;
	}
}

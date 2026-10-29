package hospital;

public class RequirementNode {
	private String resourceType;
	private RequirementNode next;
	
	public RequirementNode(String resourceType) {
		this.resourceType = resourceType;
		this.next = null;
	}
	
	public String getResourceType() {
		return resourceType;
	}
	
	public RequirementNode getNext() {
		return next;
	}
	
	public void setNext(RequirementNode next) {
		this.next = next;
	}

}

package hospital;

public class ResourcePool {
	private ResourceNode head;
	private ResourceNode tail;
	
	private int resourceCount;
	
	public ResourcePool() {
		head = null;
		tail = null;
		resourceCount = 0;
	}
	
	public void addResource(Resource resource) {
		ResourceNode newNode = new ResourceNode(resource);
		
		if(head == null) {
			head = newNode;
			tail = newNode;
		}
		else {
			tail.setNext(newNode);
			tail = newNode;
		}
		
		resourceCount++;
	}
	
	public boolean isResourceAvailable(String resourceType) {
		ResourceNode current = head;
		
		while(current != null) {
			Resource resource = current.getResource();
			
			if(resource.isAvailable() && sameText(resource.getResourceType(), resourceType)) {
				return true;
			}
			
			current = current.getNext();
		}
		
		return false;
	}
	
	public boolean canAllocate(Task task) {
		RequirementNode requirement = task.getRequirements();
		
		while(requirement != null) {
			if(!isResourceAvailable(requirement.getResourceType())) {
				return false;
			}
			
			requirement = requirement.getNext();
		}
		
		return true;
	}
	
	public boolean allocateResources(Task task) {
		
		if(!canAllocate(task)) {
			return false;
		}
		
		RequirementNode requirement = task.getRequirements();
		
		while(requirement != null) {
			Resource resource = findAvailableResource(requirement.getResourceType());
			
			if(resource != null) {
				resource.allocate(task);
				task.addAllocation(resource);
			}
			
			requirement = requirement.getNext();
		}
		
		return true;
	}
	
	private Resource findAvailableResource(String resourceType) {
		ResourceNode current = head;
		
		while(current != null) {
			Resource resource =
                    current.getResource();

            if (resource.isAvailable()
                    && sameText(
                    resource.getResourceType(),
                    resourceType)) {

                return resource;
            }

            current = current.getNext();
        }

        return null;
    }

    public void releaseResources(Task task) {

        AllocationNode current =
                task.getAllocations();

        while (current != null) {

            Resource resource =
                    current.getResource();

            resource.release(task);

            current = current.getNext();
        }

        task.clearAllocations();
    }

    public int getResourceCount() {
        return resourceCount;
    }

    public int getAvailableCount() {

        int count = 0;

        ResourceNode current = head;

        while (current != null) {

            if (current.getResource().isAvailable()) {
                count++;
            }

            current = current.getNext();
        }

        return count;
    }

    public long getTotalBusyTime() {

        long total = 0;

        ResourceNode current = head;

        while (current != null) {

            total +=
                    current.getResource()
                    .getTotalBusyTime();

            current = current.getNext();
        }

        return total;
    }

    private boolean sameText(String a, String b) {

        if (a == null || b == null) {
            return a == b;
        }

        if (a.length() != b.length()) {
            return false;
        }

        int i = 0;

        while (i < a.length()) {

            if (a.charAt(i) != b.charAt(i)) {
                return false;
            }

            i++;
        }

        return true;
    }
}
package hospital;

public class Resource {
	private int resourceId;
	private String resourceType;
	private boolean available;
	
	private Task currentTask;
	private long totalBusyTime;
	
	public Resource(int resourceId, String resourceType) {
		this.resourceId = resourceId;
		this.resourceType = resourceType;
		this.available = true;
		this.currentTask = null;
		this.totalBusyTime = 0;
	}
	
	public int getResourceId() {
		return resourceId;
	}
	
	public String getResourceType() {
		return resourceType;
	}
	
	public boolean isAvailable() {
		return available;
	}
	
	public Task getCurrentTask() {
		return currentTask;
	}
	
	public long getTotalBusyTime() {
		return totalBusyTime;
	}
	
	public void allocate(Task task) {
		available = false;
		currentTask = task;
	}
	
	public void release(Task task) {
		if(currentTask == task) {
			if(task.getDuration() > 0) {
				totalBusyTime += task.getDuration();
			}
			
			available = true;
			currentTask = null;
		}
	}
}

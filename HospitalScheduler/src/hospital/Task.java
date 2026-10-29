package hospital;

public class Task {
	//define states a Task obj can have
	
	public static final String WAITING = "WAITING";
	public static final String RUNNING = "RUNNING";
	public static final String COMPLETED = "COMPLETED";
	
	//basic Task obj attributes
	private int taskId;
	private String taskType;
	private int urgency;
	private int arrivalTime;
	private int duration;
	private int deadline;
	
	//runtime attributes of Task obj
	private int priority;
	private int startTime;
	private int completionTime; //the time the scheduler took to mark task as completed
	private int waitingTime; //the time the task was waiting to be completed during runtime
	private String status;
	private boolean deadlineMissed;
	private boolean starvationRisk;
	
	private RequirementNode requirements; //tasks have requirements which are of type RequirementNode
	private AllocationNode allocations; //resources allocated to complete a task
	
	private HeapNode heapNode;
	
	//constructor
	public Task(int taskId, String taskType, int urgency, int arrivalTime, int duration, int deadline) {
		this.taskId = taskId;
		this.taskType = taskType;
		this.urgency = urgency;
		this.arrivalTime = arrivalTime;
		this.duration = duration;
		this.deadline = deadline;
		
		//settings for a new task
		this.priority = 0;
		this.startTime = -1;
		this.completionTime = -1;
		this.waitingTime = 0;
		this.status = WAITING;
		this.deadlineMissed = false;
		this.starvationRisk = false;
		this.requirements = null;
		this.allocations = null;
		this.heapNode = null;
	}
	
	public int getTaskId() {
		return taskId;
	}
	
	public String getTaskType() {
		return taskType;
	}
	
	public int getUrgency() {
		return urgency;
	}
	
	public void setUrgency(int urgency) {
		this.urgency = urgency;
	}
	
	public int getArrivalTime() {
		return arrivalTime;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public int getPriority() {
		return priority;
	}
	
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	public int getStartTime() {
		return startTime;
	}
	
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
	
	public int getCompletionTime() {
		return completionTime;
	}
	
	public void setCompletionTime(int completionTime) {
		this.completionTime = completionTime;
	}
	
	public int getWaitingTime() {
		return waitingTime;
	}
	
	public void setWaitingTime(int waitingTime) {
		this.waitingTime = waitingTime;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public int getDeadline() {
		return deadline;
	}
	
	public boolean isDeadlineMissed() {
		return deadlineMissed;
	}
	
	public void setDeadlineMissed(boolean deadlineMissed) {
		this.deadlineMissed = deadlineMissed;
	}
	
	public boolean hasStarvationRisk() {
		return starvationRisk;
	}
	
	public void setStarvationRisk(boolean starvationRisk) {
		this.starvationRisk = starvationRisk;
	}
	
	public RequirementNode getRequirements() {
		return requirements;
	}
	
	public void addRequirement(String resourceType) {
		RequirementNode newNode = new RequirementNode(resourceType);
		
		if(requirements == null) {
			requirements = newNode;
		}
		else {
			RequirementNode current = requirements;
			while(current.getNext() != null) {
				current = current.getNext();
			}
			current.setNext(newNode);
		}
	}
	
	public AllocationNode getAllocations() {
		return allocations;
	}
	
	public void addAllocation(Resource resource) {
		AllocationNode newNode = new AllocationNode(resource);
		
		if(allocations == null) {
			allocations = newNode;
		}
		else {
			AllocationNode current = allocations;
			while(current.getNext() != null) {
				current = current.getNext();
			}
			current.setNext(newNode);
		}
	}
	
	public void clearAllocations() {
		allocations = null;
	}
	
	public HeapNode getHeapNode() {
		return heapNode;
	}
	
	public void setHeapNode(HeapNode heapNode) {
		this.heapNode = heapNode;
	}
	
	public void printDetails() {
		System.out.println(
				"Task: "+taskId+ " | Type:"+taskType+" | Urgency: "+urgency+" | Arrival: "+arrivalTime+" | Duration: "+duration+" | Deadline: "+deadline+" |Priority: "+priority+" | Status: "+status+" | Wait: "+waitingTime+" | Deadline Miss: "+deadlineMissed+" | Starvation Risk: "+starvationRisk);
	}
}

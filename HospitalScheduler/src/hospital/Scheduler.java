package hospital;

public class Scheduler {
	private TaskDataSet dataSet;
	private TaskRegistry registry;
	private CustomPriorityQueue priorityQueue;
	private ResourcePool resourcePool;
	private ActiveTaskList activeTasks;
	private SchedulerMetrics metrics;
	private TaskNode nextArrival;
	private int currentTime;
	private boolean verbose;
	
	public Scheduler(TaskDataSet dataSet, ResourcePool resourcePool, boolean verbose) {
		this.dataSet = dataSet;
		this.resourcePool = resourcePool;
		this.verbose = verbose;
		
		registry = new TaskRegistry();
		
		priorityQueue = new CustomPriorityQueue();
		
		activeTasks = new ActiveTaskList();
		
		metrics = new SchedulerMetrics(dataSet.getSize());
		
		nextArrival = dataSet.getHead();
		currentTime = 0;
	}
	
	public void run() {
		if(dataSet.getSize() == 0) {
			return;
		}
		while(metrics.getProcessedTasks() < dataSet.getSize()) {
			ingestArrivals();
			completeTasks();
			refreshWaitingPriorities();
			dispatchTasks();
			
			if(metrics.getProcessedTasks() >= dataSet.getSize()) {
				break;
			}
			int nextEvent = findNextEventTime();
			
			if(nextEvent == -1) {
				break;
			}
			if(nextEvent <= currentTime) {
				currentTime++;
			}
			else {
				currentTime = nextEvent;
			}
		}
	}
	
	public void processNextEvent() {
		if(isFinished()) {
			System.out.println("Simualtion has already finished.");
			return;
		}
		
		//check everything happening during current simulation
		ingestArrivals();
		completeTasks();
		refreshWaitingPriorities();
		dispatchTasks();
		
		//check if all tasks are now completed
		if(isFinished()) {
			System.out.println("All tasks have been processed.");
			return;
		}
		
		//check for next arrival / completion
		int nextEvent = findNextEventTime();
		if(nextEvent == -1) {
			System.out.println("No further events can be processed.");
			return;
		}
		
		//move simulation clock to next event
		if(nextEvent <= currentTime) {
			currentTime++;
		}
		else {
			currentTime = nextEvent;
		}
		System.out.println("Simulation moved to time "+currentTime);
	}
	private void ingestArrivals() {
		while(nextArrival != null) {
			Task task = nextArrival.getTask();
			
			if(task.getArrivalTime() <= currentTime) {
				registry.add(task);
				task.setPriority(PriorityCalculator.calculatePriority(task, currentTime));
				priorityQueue.insert(task);
				
				if(verbose) {
					System.out.println("[TIME " +
                            currentTime +
                            "] Task " +
                            task.getTaskId() +
                            " arrived. Priority = " +
                            task.getPriority());
				}
				nextArrival = nextArrival.getNext();
			}
			else {
				break;
			}
		}
	}
		
		private void completeTasks() {
			activeTasks.removeCompletedTasks(currentTime,resourcePool, metrics);
			if(verbose) {
				System.out.println("[TIME "+ currentTime + "] Completion check done.");
			}
		}
		private void refreshWaitingPriorities() {
			TaskNode current = registry.getHead();
			
			while(current!= null) {
				Task task = current.getTask();
				if(task.getStatus() == Task.WAITING) {
					PriorityCalculator.updateTaskPriority(task,currentTime,priorityQueue);
				}
				current = current.getNext();
			}
		}
		private void dispatchTasks() {
			boolean dispatchedSomething = true;
			while(dispatchedSomething && !priorityQueue.isEmpty()) {
				dispatchedSomething = false;
				Task selected = findDispatchableTask();
				
				if(selected != null) {
					dispatch(selected);
					dispatchedSomething = true;
				}
			}
		}
		
		private Task findDispatchableTask() {
			int numberToCheck = priorityQueue.size();
			
			TaskNode skippedHead = null;
			TaskNode skippedTail = null;
			
			Task selected = null;
			
			int i = 0;
			while( i < numberToCheck && !priorityQueue.isEmpty()) {
				Task candidate = priorityQueue.extractHighestPriority();
				
				if(resourcePool.canAllocate(candidate)) {
					selected = candidate;
					
					//re-add skipped tasks
					reinsertSkipped(skippedHead);
					return selected;
				}
				else {
					TaskNode skipped = new TaskNode(candidate);
					if(skippedHead == null) {
						skippedHead = skipped;
						skippedTail = skipped;
					}
					else {
						skippedTail.setNext(skipped);
						skippedTail = skipped;
					}
				}
				
				i++;
			}
			
			reinsertSkipped(skippedHead);
			return null;
		}
		
		private void reinsertSkipped(TaskNode skippedHead) {
			TaskNode current = skippedHead;
			while(current != null) {
				priorityQueue.insert(current.getTask());
				current = current.getNext();
			}
		}
		
		private void dispatch(Task task) {
			boolean allocated = resourcePool.allocateResources(task);
			
			if(!allocated) {
				priorityQueue.insert(task);
				return;
			}
			task.setStatus(Task.RUNNING);
			task.setStartTime(currentTime);
			task.setWaitingTime(currentTime - task.getArrivalTime());
			task.setCompletionTime(currentTime + task.getDuration());
			
			activeTasks.add(task);
			
			if(verbose) {
				System.out.println(
	                    "[TIME " +
	                    currentTime +
	                    "] DISPATCHED Task " +
	                    task.getTaskId() +
	                    " | Priority = " +
	                    task.getPriority() +
	                    " | Duration = " +
	                    task.getDuration() +
	                    " | Completes = " +
	                    task.getCompletionTime());
			}
		}
		
		private int findNextEventTime() {

	        int nextArrivalTime = -1;

	        if (nextArrival != null) {

	            nextArrivalTime =
	                    nextArrival
	                    .getTask()
	                    .getArrivalTime();
	        }

	        int nextCompletionTime =
	                activeTasks
	                .findNextCompletionTime();

	        if (nextArrivalTime == -1) {
	            return nextCompletionTime;
	        }

	        if (nextCompletionTime == -1) {
	            return nextArrivalTime;
	        }

	        if (nextArrivalTime
	                < nextCompletionTime) {

	            return nextArrivalTime;
	        }

	        return nextCompletionTime;
	    }
		public void updateTaskPriority(
	            int taskId,
	            int newUrgency) {

	        Task task =
	                registry.findTask(taskId);

	        if (task == null) {

	            System.out.println(
	                    "Task " +
	                    taskId +
	                    " has not arrived yet.");

	            return;
	        }

	        if (task.getStatus()
	                != Task.WAITING) {

	            System.out.println(
	                    "Task " +
	                    taskId +
	                    " is not currently waiting.");

	            return;
	        }

	        int oldUrgency =
	                task.getUrgency();

	        int oldPriority =
	                task.getPriority();

	        task.setUrgency(newUrgency);

	        int newPriority =
	                PriorityCalculator
	                .calculatePriority(
	                task,
	                currentTime);

	        priorityQueue.updatePriority(
	                task,
	                newPriority);

	        System.out.println();
	        System.out.println(
	                "Task " + taskId +
	                " priority updated.");

	        System.out.println(
	                "Urgency: " +
	                oldUrgency +
	                " -> " +
	                newUrgency);

	        System.out.println(
	                "Priority: " +
	                oldPriority +
	                " -> " +
	                newPriority);
	    }
		public void printTaskStatus(
	            int taskId) {

	        Task task =
	                registry.findTask(taskId);

	        if (task == null) {

	            System.out.println(
	                    "Task not found.");

	            return;
	        }

	        task.printDetails();
	    }
	    
	    public void printWaitingQueue() {

	        System.out.println();

	        System.out.println(
	                "--- WAITING PRIORITY QUEUE ---");

	        if (priorityQueue.isEmpty()) {

	            System.out.println(
	                    "No tasks are currently waiting.");

	            return;
	        }

	        priorityQueue.printHeap();
	    }
	    
	    public void printActiveTasks() {

	        System.out.println();

	        System.out.println(
	                "--- ACTIVE TASKS ---");

	        if (activeTasks.getSize() == 0) {

	            System.out.println(
	                    "No tasks are currently running.");

	            return;
	        }

	        activeTasks.printActiveTasks();
	    }

	    public void printReport() {

	        metrics.printReport(
	                currentTime,
	                resourcePool);
	    }

	    public TaskRegistry getRegistry() {
	        return registry;
	    }

	    public CustomPriorityQueue
	            getPriorityQueue() {

	        return priorityQueue;
	    }

	    public ResourcePool getResourcePool() {
	        return resourcePool;
	    }

	    public int getCurrentTime() {
	        return currentTime;
	    }

	    public SchedulerMetrics
	            getMetrics() {

	        return metrics;
	    }
	    
	    public boolean isFinished() {

	        return metrics.getProcessedTasks()
	                >= dataSet.getSize();
	    }
	}
## README
## Adaptive Hospital Resource Planning & Priority Scheduling System
## Java | Event-Driven Scheduling Simulation

## 1. Project Overview

This Java project implements a menu-driven simulation of an adaptive hospital resource planning and priority scheduling system. Synthetic hospital tasks arrive over simulated time, are assigned dynamic priorities, wait in a custom max-heap priority queue, and are dispatched when the resources they require are available. The system tracks task waiting, execution and completion while recording deadline misses, starvation risks, throughput and resource utilization.
The project uses custom node-based structures for the task data set, task registry, active task list, resource pool and priority queue. The scheduler can be operated interactively one event at a time or allowed to complete the remaining simulation automatically.

## 2. Project Package and Entry Point

•	Java package: hospital
•	Main entry point: hospital.Main
•	Source directory: src/hospital
•	The submitted project contains 20 Java source classes.

## 3. Running the Application

3.1 Eclipse
1.	Open or import the HospitalScheduler project in Eclipse.
2.	Confirm that src is configured as a source folder.
3.	Open src/hospital/Main.java.
4.	Run Main.java as a Java Application.
5.	Use the numbered console menu to generate a dataset and operate the simulation.
3.2 Command Line
From the project root, the intended compile and run commands are:
javac -d bin src/hospital/*.java
java -cp bin hospital.Main

## 4. Main Menu

Option	Function
1	Generate New Task Dataset
2	Display Generated Dataset
3	Process Next Simulation Event
4	Update Waiting Task Urgency
5	View Waiting Priority Queue
6	View Active Tasks
7	View Task Status
8	Run Remaining Simulation
9	View Scheduling Report
10	Run Performance Analysis
0	Exit

## 5. Synthetic Data set

All task data used by the application is synthetic. The project does not use real patient records, clinical information or operational hospital data sets. DatasetGenerator.java creates artificial tasks specifically for testing the scheduling algorithm and its custom data structures.
To generate a data set, run the application and select Option 1. The program asks for the number of tasks and a random seed, then calls DatasetGenerator.generate(numberOfTasks, seed). A new standard resource pool and Scheduler are also created for that data set.

## 6. Generator Seed and Reproducibility

DatasetGenerator uses the custom SimpleRandom class. Supplying the same task count and the same seed produces the same pseudo-random sequence and therefore the same generated workload. This makes the data set reproducible for demonstrations, testing and performance comparison.
Example standard demonstration input:
Number of tasks: 50
Random seed: 12345
The console seed should be entered as 12345. The form 12345L is used only inside Java source code to mark a long literal.
PerformanceTest.java uses the fixed seed 12345L for every workload size so that its generated test workloads are deterministic.

## 7. Data set Generation Parameters

Generated Attribute	Rule / Range	Purpose
Task ID	Sequentially from 1	Uniquely identifies each generated task.
Arrival gap	0 to 10	Added to the previous arrival time to produce non-decreasing arrivals.
Urgency	1 to 5	Represents the urgency level used in priority calculation.
Duration	10 to 90	Simulated execution time once the task is dispatched.
Deadline buffer	30 to 180	Added to arrival time to create the deadline.
Deadline	arrival time + deadline buffer	Target completion time for deadline monitoring.
Task type	Random choice 1 to 5	Determines the task category and primary resource.
Extra requirement	Every 10th task	Adds an additional Nurse requirement.

## 8. Task Types and Resource Requirements

Task Type	Primary Resource
Emergency	Doctor
Diagnostic	DiagnosticMachine
PatientCare	Nurse
Transport	Ambulance
BedRequest	Bed
After the primary requirement is added, every task whose ID is divisible by 10 receives an additional Nurse requirement. This introduces multi-resource demand into the generated workload. If the primary resource is already Nurse, that generated task contains two Nurse requirements.

## 9. Task Attributes

Attribute	Meaning
taskId	Unique task identifier.
taskType	Synthetic hospital task category.
urgency	Urgency level used by the adaptive priority calculation.
arrivalTime	Simulation time at which the task arrives.
duration	Simulated execution duration.
deadline	Target completion time.
priority	Current dynamically calculated scheduling score.
startTime	Time at which the scheduler dispatches the task.
completionTime	Scheduled completion time, calculated from start time plus duration.
waitingTime	Amount of simulated time spent waiting.
status	WAITING, RUNNING or COMPLETED.
deadlineMissed	Records whether completion occurs after the deadline.
starvationRisk	Records whether the waiting time reaches the starvation threshold.
requirements	Linked resource requirements associated with the task.
allocations	Actual resources allocated to the running task.

## 10. Adaptive Priority Calculation

PriorityCalculator.java calculates a task's scheduling score using urgency, ageing and deadline pressure. The submitted constants are URGENCY_WEIGHT = 100, AGEING_WEIGHT = 2, DEADLINE_WEIGHT = 1 and STARVATION_THRESHOLD = 120.
Waiting time is calculated as current simulation time minus arrival time. Urgency contributes urgency x 100, while ageing contributes waiting time x 2. Deadline pressure increases as the deadline approaches. If the deadline has been reached or passed, the deadline score is 1000; otherwise it is calculated as 1000 / (deadlineRemaining + 1).
While a task remains WAITING, its priority can be recalculated and its heap position updated. The menu also allows the urgency of an arrived waiting task to be changed from 1 to 5.

## 11. Simulated Resource Pool

Resource Type	Quantity
Doctor	3
Nurse	4
DiagnosticMachine	2
Ambulance	2
Bed	4
The same resource quantities are used by the interactive application's createResourcePool() method and the standard resource pool created for performance testing.
## 12. Hospital Simulation Assumptions

•	The project is a simplified educational simulation and not a clinical decision-support system.
•	Time is represented using integer simulation units. The report labels throughput as tasks/minute, but the generator itself uses abstract integer time values.
•	Tasks arrive according to cumulative pseudo-random arrival gaps.
•	A task must obtain its required resources before it can be dispatched.
•	Allocated resources remain unavailable until the task completes and the scheduler releases them.
•	A task progresses through WAITING, RUNNING and COMPLETED states.
•	A running task receives a scheduled completion time equal to its start time plus its duration.
•	The scheduler can skip a higher-priority task whose resources are unavailable and examine another feasible waiting task, reducing head-of-line blocking.
•	Waiting priorities are refreshed as simulation time advances, allowing ageing and deadline pressure to influence scheduling.
•	Waiting for at least 120 simulated time units is recorded as a starvation risk.

## 13. Performance Analysis

Option 10 executes PerformanceTest.java. The test creates fresh datasets containing 50, 100, 250, 500, 1,000, 2,500 and 5,000 tasks. Every dataset is generated using the fixed seed 12345L and a fresh standard resource pool.
Each scheduler run is timed using System.nanoTime(). The console output reports task count, runtime in nanoseconds, runtime in milliseconds, average waiting time and deadline miss percentage. The scheduling report additionally records processed tasks, starvation risks, total processing time, simulation duration, throughput and resource utilisation.

## 14. Reproducing a Standard Demonstration

6.	Run hospital.Main.
7.	Select Option 1 and generate 50 tasks using seed 12345.
8.	Use Option 2 to inspect the generated synthetic dataset.
9.	Use Option 3 repeatedly to process simulation events interactively.
10.	Use Options 5 and 6 to inspect waiting and active tasks.
11.	When an arrived task is still WAITING, use Option 4 to change its urgency and observe the priority update.
12.	Use Option 8 to complete the remaining simulation.
13.	Use Option 9 to display the final scheduling report.
14.	Use Option 10 separately to run the built-in performance evaluation.
15. Academic Scope
This project is an educational proof-of-concept intended to demonstrate data structures and algorithms for adaptive priority scheduling and resource allocation. The generated workload, task categories, timing values and resource quantities are simplified assumptions created for algorithm testing. They should not be interpreted as real hospital staffing levels, patient data, clinical priorities or medical guidance.

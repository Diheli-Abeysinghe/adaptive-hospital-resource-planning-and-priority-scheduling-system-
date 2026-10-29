package hospital;

public class DatasetGenerator {
	public static TaskDataSet generate(
			int numberOfTasks, long seed) 
	{
		TaskDataSet data = new TaskDataSet();
		
		SimpleRandom random = new SimpleRandom(seed);
		int currentArrivalTime = 0;
		int i = 1;
		while(i <= numberOfTasks) {
			int arrivalGap = random.nextInt(0,10);
			currentArrivalTime += arrivalGap;
			
			int urgency = random.nextInt(1,5);
			
			int duration = random.nextInt(10,90);
			
			int deadlineBuffer = random.nextInt(30,180);
			int deadline = currentArrivalTime + deadlineBuffer;
			
			String taskType;
			String resourceType;
			
			int typeChoice = random.nextInt(1,5);
			
			if(typeChoice == 1) {
				taskType = "Emergency";
				resourceType = "Doctor";
			}
			else if(typeChoice == 2) {
				taskType = "Diagnostic";
				resourceType = "DiagnosticMachine";
			}
			else if(typeChoice == 3) {
				taskType = "PatientCare";
				resourceType = "Nurse";
			}
			else if (typeChoice == 4) {
				taskType = "Transport";
				resourceType = "Ambulance";
			}
			else {
				taskType = "BedRequest";
				resourceType = "Bed";
			}
			
			Task task = new Task(
					i,
					taskType,
					urgency,
					currentArrivalTime,
					duration,
					deadline);
			
			task.addRequirement(resourceType);
			
			if(i % 10 == 0) {
				task.addRequirement("Nurse");
			}
			data.add(task);
			i++;
		}
		return data;
	}
	
	private static boolean sameText(
            String a,
            String b) {

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
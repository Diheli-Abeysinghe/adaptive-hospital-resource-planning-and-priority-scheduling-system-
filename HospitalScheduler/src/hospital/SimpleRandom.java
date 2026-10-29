package hospital;

public class SimpleRandom {
	private long seed;
	
	public SimpleRandom(long seed) {
		this.seed = seed;
	}
	
	public int nextInt(int minimum, int maximum) {
		seed = (seed * 1103515245L + 12345L)& 2147483647L;
		
		int range = maximum - minimum + 1;
		int value = (int)(seed % range);
		return minimum + value;
	}

}

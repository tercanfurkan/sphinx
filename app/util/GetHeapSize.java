package util;

public class GetHeapSize {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long heapSize = Runtime.getRuntime().totalMemory();
		System.out.println("Heap Size= " + heapSize);

	}

}

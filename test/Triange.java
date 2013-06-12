import java.util.Scanner;




public class Triange {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		printTriangle(sc.nextInt());
	}
	
	public static int pixelsOfARow(int columns) {
		
		if (columns == 1)
			return 1;
		int pixelsOfARow = (int) Math.pow(2, columns-1);
		return pixelsOfARow(columns-1) + pixelsOfARow;
	}
	
	public static String binaryFormatPixelsOfaRow(int pixelsOfARow) {
		return Integer.toBinaryString(pixelsOfARow);
	}

	public static void printTriangle(int height) {
		int pixelsOfARow = pixelsOfARow(height);
		System.out.println(pixelsOfARow);
		for (int i = 1; i <= 4; i++) {
			
			// except first row  apply >>2 and <<1
			if (i > 1) {
				pixelsOfARow = pixelsOfARow >> 1;
				pixelsOfARow = pixelsOfARow << 2;
				pixelsOfARow = pixelsOfARow >> 1;
			}
			
			//convert to string 
			String binaryFormatPixelsOfaRow = binaryFormatPixelsOfaRow(pixelsOfARow);
			System.out.println(binaryFormatPixelsOfaRow);
			//print "" for each 0 and "*" for each 1
			for (int j = 0; j < binaryFormatPixelsOfaRow.length(); j++) {
				if (binaryFormatPixelsOfaRow.substring(j, j+1).equals("0")) System.out.print(" ");
				else System.out.print("*");
			}
			
			System.out.println();
		}
	}
}

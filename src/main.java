import java.util.ArrayList;
import java.util.List;

public class main {

	public static void main(String[] args) {
		int[] numbers = { 34, 21, 42, 15, 69, 48, 25, 39 };
 		System.out.println(mystery(3));
		
	
}
	private int[] numbers;

	public static void mystery(int x) {
	   for (int k = 1; k < numbers.length; k = k + x) {
	      numbers[k] = numbers[k - 1] + x;
	   }
	   return x;
	}
}
/* 
 * John Curran
 * 
 * This class is a collection of array sorting algorithms, along with a 
 * helpful function for printing out a full array on a single line. 
 * The functions in this class do not mutate the array on their own, so 
 * in order to change the array the user must write array = function(array);
 * 
 * These are all common sorting algorithms, and I do not take credit for 
 * creating any of them. I simply worked indepently to translate common
 * pseudocode used to represent them into Java code
 * 
 * More sorting algorithms will be added to this class
 */

package Algorithms;

import java.util.Arrays;

public class Sorting {
	public static double[] BubbleSort(double[] A) { // O(n^2) efficiency
		A = Arrays.copyOf(A, A.length);
		
		for (int i = A.length - 1; i > 0; i--) {
			for (int j = 0; j < i; j++) {
				if (A[j] > A[j + 1]) {
					double t = A[j];
					A[j] = A[j + 1];
					A[j + 1] = t;
				}
			}
		}
		
		return A;
	}
	
	public static double[] InsertionSort(double[] A) { // O(n^2) efficiency
		A = Arrays.copyOf(A, A.length);
		
		for (int i = 1; i < A.length; i++) {
			double t = A[i];
			int j = i - 1;
			
			while (j >= 0 && A[j] > t) {
				A[j + 1] = A[j];
				j--;
			}
			
			A[j + 1] = t;
		}
		
		return A;
	}
	
	public static double[] SelectionSort(double[] A) { // O(n^2) efficiency
		A = Arrays.copyOf(A, A.length);
		
		for (int i = A.length - 1; i > 0; i--) {
			int k = 0;
			for (int j = 1; j <= i; j++) 
				if (A[j] > A[k])
					k = j;
			
			double t = A[k];
			A[k] = A[i];
			A[i] = t;
		}
		
		return A;
	}
	
	public static double[] MergeSort(double[] A) { // O(nlogn) efficiency
		double[] firstHalf = Arrays.copyOfRange(A, 0, A.length / 2);
		double[] secondHalf = Arrays.copyOfRange(A, A.length / 2, A.length);
		return mergeSortHelp(firstHalf, secondHalf);
	}
	
	public static double[] QuickSort(double[] A) { // O(nlogn) efficiency
		return quickSortHelp(A, 0, A.length);
	}
	
	public static double[] HeapSort(double[] A) { // O(nlogn) efficiency
		A = Arrays.copyOf(A, A.length);
		
		//create heap
		for (int i = A.length / 2; i > 0; i--) 
			siftDown(A, i, A.length);
		
		//sort from the heap
		int end = A.length - 1;
		for (int i = 0; i < A.length; i++) {
			double temp = A[0];
			A[0] = A[end];
			A[end] = temp;
			
			siftDown(A, 1, end);
			
			end--;
		}
		
		return A;		
	}
	
	
	

	//merges two arrays together, starting from the front of each array 
	//and adding the smaller number of each until both arrays have been 
	//added in full
	private static double[] merge(double[] A, double[] B) {
		double[] C = new double[A.length + B.length];
		int CCount = 0;
		
		while (A.length > 0 && B.length > 0) {
			if (A[0] < B[0]) {
				C[CCount] = A[0];
				A = Arrays.copyOfRange(A, 1, A.length);
			} else {
				C[CCount] = B[0];
				B = Arrays.copyOfRange(B, 1, B.length);
			}
			
			CCount++;
		}
		
		if (A.length > 0) 
			while (A.length > 0) {
				C[CCount] = A[0];
				A = Arrays.copyOfRange(A, 1, A.length);
				CCount++;
			}
		else 
			while (B.length > 0) {
				C[CCount] = B[0];
				B = Arrays.copyOfRange(B, 1, B.length);
				CCount++;
			}
		
		return C;
	}
	
	//performs merge sort on two arrays as if they were a single array
	//split in two
	private static double[] mergeSortHelp(double[] A, double[] B) {
		if (A.length <= 1 && B.length <= 1)
			return merge(A, B);
		else if (A.length <= 1)
			return merge(A, mergeSortHelp(Arrays.copyOfRange(B, 0, B.length / 2), 
											Arrays.copyOfRange(B, B.length / 2, B.length)));			
		else if (B.length <= 1)
			return merge(mergeSortHelp(Arrays.copyOfRange(A, 0, A.length / 2), 
										Arrays.copyOfRange(A, A.length / 2, A.length)), B);
		else
			return merge(mergeSortHelp(Arrays.copyOfRange(A, 0, A.length / 2), Arrays.copyOfRange(A, A.length / 2, A.length)),
					mergeSortHelp(Arrays.copyOfRange(B, 0, B.length / 2), Arrays.copyOfRange(B, B.length / 2, B.length)));
	}
	
	//performs quicksort on an array within the set index boundaries
	private static double[] quickSortHelp(double[] A, int l, int r) {
		double[] B = Arrays.copyOf(A, A.length);
		if (l < r) {
			int f = l, b = r - 1;
			double pivot = A[r - 1];
			for (int i = l; i < r - 1; i++)
				if (A[i] < pivot) {
					B[f] = A[i];
					f++;
				} else {
					B[b] = A[i];
					b--;
				}
					
			B[f] = pivot;
			
			return quickSortHelp(quickSortHelp(B, l, f), f + 1, r);
		} else {
			return B;
		}
	}
	
	//sorts a number in a specific index in the array into its proper 
	//position in the heap, ignoring any numbers after the end index
	private static double[] siftDown(double[] A, int index, int end) {
		int p = index - 1, left = (2 * index) - 1, right = left + 1;
		if (left >= end) { // neither right nor left child
			return A; 
			
		} else if (right >= end) { // no right child
			if (A[p] < A[left]) {
				double temp = A[p];
				A[p] = A[left];
				A[left] = temp;
				
				return siftDown(A, left + 1, end);
			} else {
				return A;
			}
			
		} else { // both a right and left child
			int largest;
			if (A[right] > A[left])
				largest = right;
			else
				largest = left;
			
			if (A[p] < A[largest]) {
				double temp = A[largest];
				A[largest] = A[p];
				A[p] = temp;
				
				return siftDown(A, largest + 1, end);
			} else {
				return A;
			}
		}
	}
	
	
	//prints the array on a single line, separated by spaces, and ending in a new line
	public static void printArray(double[] A) {
		for (double i : A) 
			if (i % 1 == 0)
				System.out.print((new Double(i)).intValue() + " ");
			else
				System.out.print(i + " ");
		
		System.out.println();
	}	
}

import java.util.Collections;
import java.util.LinkedList;

/**
 * List of Sorting Algorithm Available:
 *
 * heapSort(double[] arr)
 * countingSort(int[] arr)
 * radixSort(int[] arr)
 * bucketSort(double[] arr)
 * shellSort(double[] arr)
 * timSort(double[] arr)
 * pigeonHoleSort(int[] arr)
 */

public class SortingAlgo {

    // Given an array of type double.
    // Assume the array as a tree.
    // arr[0] indicates root node. To obtain its (or other parent nodes) children,
    // Left child: i * 2 + 1; Right child: i * 2 + 1
    // To know the range of parent nodes from 0 to m, m = n / 2 - 1;
    // Steps involved:
    // i. Form max heap. (Parent node is greater than children)
    // ii. Swap arr[0] with the last element.
    // iii. Repeat (i) (ii).
    // Time Complexity: O(n * log n)
    public void heapSort(double[] arr){
        if(arr.length > 1){
            // Form max heap.
            for(int i = arr.length / 2 - 1; i >= 0; i--){
                maxHeap(arr, arr.length, i);
            }

            for(int i = arr.length - 1; i > 0; i--){
                // Swap the last element with the first element. (to eliminate the first element)
                double temp = arr[i];
                arr[i] = arr[0];
                arr[0] = temp;


                // Form max heap again.
                maxHeap(arr, i, 0);
            }
        }
    }

    // Use an array to count number of time of each value (from 0 to 255) in the array appears.
    // int array whose values ranging from 0 to 255.
    // Some arithmetic is done to calculate the position of each element in the output sequence.
    // It is not a comparison based sorting.
    // Time complexity: O(n+k) where n is the number of elements in input array and k is the range of input.
    public void countingSort(int[] arr){
        final int RANGE = 256;
        final int NUM = arr.length;

        int[] count = new int[RANGE];
        int[] outputArr = new int[NUM];

        // Counting the number of times each number appears.
        for(int i = 0; i < NUM; i++){
            count[arr[i]]++;
        }

        // Accumulate times of appearing for each number in the range of 0 to 255.
        for(int i = 1; i < RANGE; i++){
            count[i] += count[i - 1];
        }

        // Sorting the element by copying the value to appropriate position in outputArr.
        for(int i = 0; i < NUM; i++){
            outputArr[--count[arr[i]]] = arr[i];
        }

        // Copying the sorted array to the original array.
        for(int i = 0; i < NUM; i++){
            arr[i] = outputArr[i];
        }
    }

    // Use counting sort as subroutine.
    // Sort elements based on place values, starting from units, tenth...
    // Determine maximum and minimum values to calculate the range of the values in array.
    // In this case, time complexity: O((n * log10(k)) where k is maximum possible value.
    public void radixSort(int[] arr){
        int max;

        try {
            max = maxNum(arr);
        } catch (ArrayIndexOutOfBoundsException ex){
            return;
        }

        for(int i = 1; max / i > 0;i *= 10){
            countingSort(arr, i);
        }
    }

    // Sort a large set of floating point numbers which are in range from 0.0 to 1.0
    // and are uniformly distributed across the range.
    // Create n-element bucket array (each element is a linked list).
    // Copying the element of arr into the appropriate bucket (linked list)
    // where the position is determined by (int) (valueOfElement * n).
    // Perform sorting on each linked list.
    // Then, merge each sorted linked list together.
    // Time complexity: O(n) assume that numbers are uniformly distributed.
    public void bucketSort(double[] arr){
        final int NUM = arr.length;
        LinkedList<Double>[] bucket = new LinkedList[NUM];

        // Initialization of each linked list.
        for(int i = 0; i < NUM; i++)
            bucket[i] = new LinkedList<Double>();

        // Copy elements into appropriate linked list.
        for(double elem : arr)
            bucket[(int) (elem * NUM)].add(elem);

        // Sorting each linked list.
        for(int i = 0; i < NUM; i++)
            Collections.sort(bucket[i]);

        int j = 0;
        int i = 0;
        while (i < NUM){
            // Merging linked lists.
            while(!bucket[j].isEmpty()){
                arr[i] = bucket[j].remove();
                i++;
            }
            j++;
        }
    }

    // Improvement over insertion sort where
    // two elements distanced by a number of gap are
    // compared and reordered if necessary.
    // Time complexity: O(n^2)
    public void shellSort(double[] arr){
        int num = arr.length;
        for(int gap = num / 2; gap > 0; gap /= 2){
            for(int j = gap; j < num; j++){
                double temp = arr[j];

                // If the left side element is larger than the right element
                // (separated by m gaps), swap their values.
                int k;
                for(k = j; k >= gap && arr[k - gap] > temp; k -= gap){
                    arr[k] = arr[k - gap];
                }
                arr[k] = temp;
            }
        }
    }

    // Used in Java’s Arrays.sort()
    // Divide elements in arr into blocks of 32 called RUN.
    // Each group is sorted by using insertion sort.
    // After that, merge sort is performed.
    // Time Complexity: O(n Log n).
    public void timSort(double[] arr){
        int num = arr.length;
        // Assume each run has 32 elements.
        for(int i = 0; i < num; i += 32){
            insertionSort(arr, i, Math.min(i + 31, num - 1));
        }

        for(int size = 32; size < num; size *= 2)
            for (int left = 0; left < num; left += 2 * size){
                merge(arr, left, Math.min(left + size - 1, num - 1), Math.min(left + 2 * size - 1, num - 1));
            }
    }

    // Improvement over bubble sort.
    // Rather than comparing two adjacent elements, it works like shellSort which
    // compares two elements with gaps in between.
    // Number of gaps between two elements are divided by 1.3 for each iteration until the gap is 1.
    // Time complexity: O(n^2), but better than bubble sort on average.
    public void combSort(double[] arr){
        if(arr.length > 1){
            int num = arr.length;
            int gap = num;
            boolean swapped;

            do {
                gap = nextGap(gap);

                swapped = false;
                for(int i = 0; i < num - gap; i++){
                    if(arr[i] > arr[i + gap]){
                        double temp = arr[i];
                        arr[i] = arr[i + gap];
                        arr[i + gap] = temp;

                        swapped = true;
                    }
                }

            } while(gap > 1 || swapped);
        }
    }

    // Works similarly as countingSort but unlike countingSort which restricts the integer to be within a specific range,
    // pigeoonHoleSort determines the range of values of arr by finding maximum value and minimum value.
    // It works well if the number of elements and the number of possible key values are approximately the same.
    // Steps:
    // 1. Make RANGE number of pigeon holes.
    // 2. Copy each element into the corresponding hole. An element arr[i] is put in hole at index (arr[i] – min).
    // 3. Put all the element from non-empty pigeon holes into original array in order.
    // Time Complexity: O(n + Range)
    public void pigeonHoleSort(int[] arr){
        int min, max;
        try {
            min = minNum(arr);
            max = maxNum(arr);
        } catch (ArrayIndexOutOfBoundsException ex){
            return;
        }

        int RANGE = max - min + 1;
        LinkedList<Integer>[] pigeoHoles = new LinkedList[RANGE];

        // Initialization od each linked list (pigeon hole).
        for(int i = 0; i < RANGE; i++){
            pigeoHoles[i] = new LinkedList<Integer>();
        }

        // Copy element of arr into its corresponding pigeon hole.
        for(int i = 0; i < arr.length; i++){
            pigeoHoles[arr[i] - min].add(arr[i]);
        }

        // Put elements from non-empty pigeon hole to original array.
        for(int i = 0, j = 0; i < RANGE; i++){
            while(!pigeoHoles[i].isEmpty()){
                arr[j] = pigeoHoles[i].remove();
                j++;
            }
        }
    }

    // Find minimum number.
    private int minNum(int[] arr)
        throws IndexOutOfBoundsException {
        if(arr.length > 0){
            int minimum = arr[0];
            for(int i = 1; i < arr.length; i++){
                if(arr[i] < minimum)
                    minimum = arr[i];
            }
            return minimum;
        }
        else
            throw new IndexOutOfBoundsException();
    }

    // Meke max heap. (Parent node is greater than its children.)
    private void maxHeap(double[] arr, int num, int root){
        int leftChild = root * 2 + 1;
        int rightChild = root * 2 + 2;
        int largest = root;

        if(leftChild < num && arr[largest] < arr[leftChild])
            largest = leftChild;

        if(rightChild < num && arr[largest] < arr[rightChild])
            largest = rightChild;

        if(largest != root){
            double temp = arr[root];
            arr[root] = arr[largest];
            arr[largest] = temp;

            // Since the swapping modify the value of the root's child and the child might be the parent node.
            maxHeap(arr, num, largest);
        }
    }

    // Calculate next gap by shrinking the current gap by a factor of 1.3.
    private int nextGap(int gap){
        gap = (int) (gap / 1.3);
        if(gap < 1)
            return 1;
        return gap;
    }

    // Merge two sorted sub-arrays together. (Ascending order)
    private void merge(double[] arr, int first, int middle, int last){
        if(first <= middle && middle + 1 <= last){
            int list1First = first;
            int list1Last = middle;
            int list2First = middle + 1;
            int list2Last = last;
            double[] tempArr = new double[last - first + 1];

            int i = 0;
            while(list1First <= list1Last && list2First <= list2Last){
                if(arr[list1First] < arr[list2First]) {
                    tempArr[i] = arr[list1First];
                    list1First++;
                }
                else if(arr[list2First] < arr[list1First]){
                    tempArr[i] = arr[list2First];
                    list2First++;
                }
                else{
                    tempArr[i] = arr[list1First];
                    tempArr[i] = arr[list2First];
                    list1First++;
                    list2First++;
                }
                i++;
            }

            // In case, there are still unsorted elements in list1.
            while(list1First <= list1Last){
                tempArr[i] = arr[list1First];
                list1First++;
                i++;
            }

            // In case, there are still unsorted elements in list2.
            while (list2First <= list2Last){
                tempArr[i] = arr[list2First];
                list2First++;
                i++;
            }

            // Copying the sorted array to original array.
            for(int j = first, k = 0; j <= last; j++, k++){
                arr[j] = tempArr[k];
            }
        }
    }

    // Perform insertion sort within a specified range of array.
    private void insertionSort(double[] arr, int first, int last){
        if(first < last){
            // Start from second element
            for(int i = first + 1; i <= last; i++){
                // Compare the element to the previous elements.
                for(int j = first; j < i; j++){
                    if(arr[j] > arr[i]){
                        double temp = arr[i];
                        // Shifting the elements (which is large than the element)
                        // one step backwards.
                        for(int k = i; k > j; k--){
                            arr[k] = arr[k - 1];
                        }

                        arr[j] = temp;
                    }
                }
            }
        }
    }

    // Determine maximum number in an array.
    private int maxNum(int[] arr)
            throws ArrayIndexOutOfBoundsException{
        if(arr.length > 0) {
            int maximum = arr[0];
            for (int i = 1; i < arr.length; i++){
                if(arr[i] > maximum)
                    maximum = arr[i];
            }
            return maximum;
        }
        else
            throw new ArrayIndexOutOfBoundsException();
    }

    // works like countingSort but the operation is
    // perfomed based on a specified placeValue of elements.
    // Count contains 10 elements, each represent one digit of base-10 number system.
    private void countingSort(int[] arr, int placeValue){
        final int RANGE = 10;
        final int NUM = arr.length;

        int[] count = new int[RANGE];
        int[] outputArr = new int[NUM];

        for(int i = 0; i < NUM; i++){
            count[(arr[i] / placeValue) % RANGE]++;
        }

        for(int i = 1; i < RANGE; i++){
            count[i] += count[i - 1];
        }

        for(int i = NUM - 1; i >= 0; i--){
            outputArr[--count[(arr[i] / placeValue) % RANGE]] = arr[i];
        }

        for(int i = 0; i < NUM; i++){
            arr[i] = outputArr[i];
        }
    }
}

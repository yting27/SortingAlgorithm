import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args){
        Random rnd = new Random();

        double[] arr = new double[20];
        for(int i = 0; i < 20; i++){
            arr[i] = (int) (rnd.nextDouble() * 100) / 100.0;
        }

        int[] arrInt = new int[10];
        for(int i = 0; i < 10; i++){
            arrInt[i] = rnd.nextInt(256);
        }

        System.out.println("Double array: " + Arrays.toString(arr));
        System.out.println("Int array: " + Arrays.toString(arrInt));

        SortingAlgo sort = new SortingAlgo();

//       sort.heapSort(arr);
//       sort.bucketSort(arr);
//       sort.shellSort(arr);
       sort.timSort(arr);

//       sort.countingSort(arrInt);
//       sort.radixSort(arrInt);
       sort.pigeonHoleSort(arrInt);

        System.out.println();
        System.out.println("After sorting: " + Arrays.toString(arr));
        System.out.println("After sorting: " + Arrays.toString(arrInt));
    }
}

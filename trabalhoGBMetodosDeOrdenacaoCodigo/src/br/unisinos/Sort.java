package br.unisinos;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Consumer;

public class Sort {
    int size;
    int[] orderedAscending;
    int[] orderedDescending;
    int[] randomNoDuplicates;
    int[] randomWithDuplicates;
    int[] originalOrderedAscending;
    int[] originalOrderedDescending;
    int[] originalRandomNoDuplicates;
    int[] originalRandomWithDuplicates;

    public Sort(int size) {
        this.size = size;
        this.originalOrderedAscending = createOrderedAscendingArray(size);
        this.originalOrderedDescending = createOrderedDescendingArray(size);
        this.originalRandomNoDuplicates = createRandomNoDuplicatesArray(size);
        this.originalRandomWithDuplicates = createRandomWithDuplicatesArray(size);
        resetArrays();
    }

    // Inicializa as arrays com cópias dos originais
    public void resetArrays() {
        this.orderedAscending = Arrays.copyOf(this.originalOrderedAscending, this.size);
        this.orderedDescending = Arrays.copyOf(this.originalOrderedDescending, this.size);
        this.randomNoDuplicates = Arrays.copyOf(this.originalRandomNoDuplicates, this.size);
        this.randomWithDuplicates = Arrays.copyOf(this.originalRandomWithDuplicates, this.size);
    }

    // Cria as arrays em ordem crescente
    private int[] createOrderedAscendingArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }
        return array;
    }

    // Cria as arrays em ordem decrescente
    private int[] createOrderedDescendingArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = size - i - 1;
        }
        return array;
    }

    // Cria as arrays aleatórias sem duplicatas
    private int[] createRandomNoDuplicatesArray(int size) {
        int[] array = createOrderedAscendingArray(size);
        shuffleArray(array);
        return array;
    }

    // Cria as arrays aleatórias com duplicatas
    private int[] createRandomWithDuplicatesArray(int size) {
        int[] array = new int[size];
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = rand.nextInt(size / 2); 
        }
        return array;
    }

    private void shuffleArray(int[] array) {
        Random rand = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            int a = array[index];
            array[index] = array[i];
            array[i] = a;
        }
    }

    // Método para testar a ordenação e retornar o tempo de execução
    public static long testSortAndGetTime(int[] array, Consumer<int[]> sortFunction, int size, String description, String methodName) {
        int[] arrayCopy = Arrays.copyOf(array, size); 
        long startTime = System.nanoTime();
        sortFunction.accept(arrayCopy);
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;

        System.out.println("Sort: " + methodName + ", tamanho da array: " + size + ", tipo de array: " + description + ", tempo: " + elapsedTime + " nanossegundos");
        return elapsedTime;
    }

    // Método Bubble Sort 
    public static void bubbleSort(int[] array) {
        int n = array.length;
        boolean swapped;
        do {
            swapped = false;
            for (int i = 1; i < n; i++) {
                if (array[i - 1] > array[i]) {
                    int temp = array[i - 1];
                    array[i - 1] = array[i];
                    array[i] = temp;
                    swapped = true;
                }
            }
            n--;
        } while (swapped);
    }

    // Método de troca para arrays de inteiros
    private static void exchange(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }


    // Método Insertion Sort
    public static void insertionSort(int[] a) {
    	for (int i = 1; i < a.length; i++) {
    		for (int j = i; j > 0 && a[j - 1] > a[j]; j--) {
    			exchange(a, j - 1, j);
    		}
    	}
    }

    // Método Selection Sort
    public static void selectionSort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                int temp = array[i];
                array[i] = array[minIndex];
                array[minIndex] = temp;
            }
        }
    }

    // Método Shell Sort
    public static void shellSort(int[] array) {
        int h = 1;
        while (3 * h + 1 < array.length) h = 3 * h + 1;
        while (h > 0) {
            for (int i = h; i < array.length; i++) {
                int temp = array[i];
                int j = i;
                while (j >= h && array[j - h] > temp) {
                    array[j] = array[j - h];
                    j -= h;
                }
                array[j] = temp;
            }
            h /= 3;
        }
    }

    // Método Heap Sort 
    public static void heapSort(int[] array) {
        int n = array.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(array, n, i);
        }
        for (int i = n - 1; i > 0; i--) {
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;
            heapify(array, i, 0);
        }
    }

    private static void heapify(int[] array, int n, int i) {
        int largest = i; 
        int leftChild = 2 * i + 1; 
        int rightChild = 2 * i + 2;
      
        if (leftChild < n && array[leftChild] > array[largest]) {
            largest = leftChild;
        }
       
        if (rightChild < n && array[rightChild] > array[largest]) {
            largest = rightChild;
        }
       
        if (largest != i) {
            int swap = array[i];
            array[i] = array[largest];
            array[largest] = swap;

            heapify(array, n, largest);
        }
    }  

    // Método Merge Sort
    public static void mergeSort(int[] a) {
        mergeSort(a, 0, a.length - 1);
    }

    private static void mergeSort(int[] a, int left, int right) {
        if (left < right) {
            int middle = (left + right) / 2;
            mergeSort(a, left, middle);
            mergeSort(a, middle + 1, right);
            merge(a, left, middle, right);
        }
    }

    private static void merge(int[] a, int left, int middle, int right) {
        int n1 = middle - left + 1;
        int n2 = right - middle;

        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];

        for (int i = 0; i < n1; ++i) {
            leftArray[i] = a[left + i];
        }
        for (int j = 0; j < n2; ++j) {
            rightArray[j] = a[middle + 1 + j];
        }

        int i = 0, j = 0;
        int k = left;
        while (i < n1 && j < n2) {
            if (leftArray[i] <= rightArray[j]) {
                a[k] = leftArray[i];
                i++;
            } else {
                a[k] = rightArray[j];
                j++;
            }
            k++;
        }
        while (i < n1) {
            a[k] = leftArray[i];
            i++;
            k++;
        }
        while (j < n2) {
            a[k] = rightArray[j];
            j++;
            k++;
        }
    }

    // Método Quicksort
   
    public static void quickSort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    private static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        // Mediana de três para escolha do pivô
        int mid = low + (high - low) / 2;
        int pivot = medianOfThree(arr, low, mid, high);

        // Mova o pivô escolhido para a posição low
        swap(arr, low, pivot);
        pivot = arr[low];

        int left = low + 1;
        int right = high;

        while (left <= right) {
            while (left <= right && arr[left] <= pivot) {
                left++;
            }

            while (left <= right && arr[right] > pivot) {
                right--;
            }

            if (left < right) {
                swap(arr, left, right);
            }
        }

        swap(arr, low, right);
        return right;
    }

    private static int medianOfThree(int[] arr, int low, int mid, int high) {
        if (arr[low] > arr[mid]) {
            swap(arr, low, mid);
        }
        if (arr[low] > arr[high]) {
            swap(arr, low, high);
        }
        if (arr[mid] > arr[high]) {
            swap(arr, mid, high);
        }
        return mid;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }    
}
package br.unisinos;

import java.util.Arrays;

public class TesteHeap {
    public static void main(String[] args) {
        // Tamanhos das arrays
        int[] sizes = {128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536};

        // Tipos de arrays
        String[] arrayTypes = {"ordenado crescente sem duplicatas", "ordenado decrescente sem duplicatas", "aleatório sem duplicatas", "aleatório com duplicatas"};

        // Testes para cada tamanho de array
        for (int size : sizes) {
            Sort sort = new Sort(size);
            long[][] times = new long[4][10]; // Array para armazenar os tempos de cada tipo de array

            // Testes para cada tipo de array
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 10; j++) {
                    sort.resetArrays(); // Reseta as arrays para seu estado original antes de cada medição
                    switch (i) {
                        case 0:
                            times[i][j] = Sort.testSortAndGetTime(
                                    Arrays.copyOf(sort.orderedAscending, sort.orderedAscending.length),
                                    Sort::heapSort,
                                    size,
                                    "1",
                                    "Heap Sort"
                            );
                            break;
                        case 1:
                            times[i][j] = Sort.testSortAndGetTime(
                                    Arrays.copyOf(sort.orderedDescending, sort.orderedDescending.length),
                                    Sort::heapSort,
                                    size,
                                    "2",
                                    "Heap Sort"
                            );
                            break;
                        case 2:
                            times[i][j] = Sort.testSortAndGetTime(
                                    Arrays.copyOf(sort.randomNoDuplicates, sort.randomNoDuplicates.length),
                                    Sort::heapSort,
                                    size,
                                    "3",
                                    "Heap Sort"
                            );
                            break;
                        case 3:
                            times[i][j] = Sort.testSortAndGetTime(
                                    Arrays.copyOf(sort.randomWithDuplicates, sort.randomWithDuplicates.length),
                                    Sort::heapSort,
                                    size,
                                    "4",
                                    "Heap Sort"
                            );
                            break;
                    }
                }
            }
            System.out.println("Média dos tempos de execução (nanossegundos) para tamanho " + size + ":");

            for (int i = 0; i < 4; i++) {
                double mean = calculateMean(times[i]);
                double variance = calculateVariance(times[i], mean);
                double standardDeviation = Math.sqrt(variance);//Calculando o desvio padrão
                double lowerBound = mean - standardDeviation;
                double upperBound = mean + standardDeviation;
                double sumWithinRange = 0;
                int countWithinRange = 0;
                for (long time : times[i]) {
                    if (time >= lowerBound && time <= upperBound) {
                        sumWithinRange += time;
                        countWithinRange++;
                    }
                }
                double meanWithinRange = countWithinRange > 0 ? sumWithinRange / countWithinRange : 0; 
                System.out.printf("%s: Média dentro do desvio padrão: %.2f%n", arrayTypes[i], meanWithinRange);
            }
        }
    }
    
    // Método para calcular a média
    private static double calculateMean(long[] times) {
        long sum = 0;
        for (long time : times) {
            sum += time;
        }
        return (double) sum / times.length;
    }
    
    //Método para calcular a variância
    private static double calculateVariance(long[] times, double mean) {
        double sumSquaredDiff = 0;
        for (long time : times) {
            sumSquaredDiff += Math.pow((time - mean), 2);
        }
        return sumSquaredDiff / (times.length - 1);
    }
}


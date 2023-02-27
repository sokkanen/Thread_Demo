package org.example;

import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws Exception{
        boolean runInThread = false;
        int maxN = 1000;
        Random rnd = new Random();
        int[] randomNumbers = rnd.ints(1000, 1, 1001).toArray();
        long[] timeSamples = new long[maxN];
        for (int i = 0; i < 1000; i++) {
            long startTime = System.currentTimeMillis();
            int sumCount = rnd.nextInt(maxN + 1) + 1;
            if (runInThread) {
                int finalI = i;
                Thread t = new Thread() {
                    public void run() {
                        arraySum(randomNumbers, sumCount);
                    }
                };
                t.start();
                long endTime = System.currentTimeMillis();
                timeSamples[finalI] = endTime - startTime;
            } else {
                arraySum(randomNumbers, sumCount);
                long endTime = System.currentTimeMillis();
                timeSamples[i] = endTime - startTime;
            }

        }
        printStatistics(timeSamples, maxN);
    }

    public static void printStatistics(long[] timeSamples, int maxN) {
        long min = Arrays.stream(timeSamples).min().getAsLong();
        long max = Arrays.stream(timeSamples).max().getAsLong();
        double avg = Arrays.stream(timeSamples).average().getAsDouble();
        long mean = Arrays.stream(timeSamples).sorted().toArray()[maxN / 2];
        System.out.println(mean);
        double stdDev = Math.sqrt(
                Arrays.stream(timeSamples)
                        .mapToDouble(num -> Math.abs(mean - (double)num))
                        .map(val -> Math.sqrt(val))
                        .sum() / timeSamples.length
        );
        System.out.printf("Minimum value: %d, Maximum value: %d, Average: %f, Standard deviation: %f", min, max, avg, stdDev);
    }

    public static void arraySum(int[] numbers, int sumCount) {
        Arrays.stream(numbers).limit(sumCount).sum();
    }
}
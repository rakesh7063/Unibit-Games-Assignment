package Approach.Two_Pointer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArraySumPairByTwoPointer {

    // Method to find pairs whose sum equals the target value
    public static int[][] findPairs(int[] arr, int target) {
        // Sort the array in ascending order
        Arrays.sort(arr);
        List<int[]> result = new ArrayList<>();
        int left = 0;
        int right = arr.length - 1;

        while (left < right) {
            int sum = arr[left] + arr[right];
            if (sum == target) {
                // Pair found, add it to the result list
                result.add(new int[]{arr[left], arr[right]});
                left++;
                right--;
                // Skip duplicate elements to avoid duplicate pairs
                while (left < right && arr[left] == arr[left - 1]) {
                    left++;
                }
                while (left < right && arr[right] == arr[right + 1]) {
                    right--;
                }
            } else if (sum < target) {
                // Sum is smaller than target, move left pointer to increase the sum
                left++;
            } else {
                // Sum is larger than target, move right pointer to decrease the sum
                right--;
            }
        }

        return result.toArray(new int[0][]);
    }

    // Method to merge pairs into a single array and sort it in ascending order
    public static int[] mergeAndSort(int[][] pairs) {
        List<Integer> mergedList = new ArrayList<>();
        for (int[] pair : pairs) {
            for (int num : pair) {
                mergedList.add(num);
            }
        }
        mergedList.sort(null);
        int[] mergedArray = new int[mergedList.size()];
        for (int i = 0; i < mergedList.size(); i++) {
            mergedArray[i] = mergedList.get(i);
        }
        return mergedArray;
    }

    // Method to find combinations that equal the double of the target value
    public static int[][] findCombinations(int[] arr, int target) {
        target *= 2; // Double the target value
        List<List<Integer>> combinations = new ArrayList<>();
        findAllCombinations(combinations, new ArrayList<>(), arr, target, 0);
        return convertListToArray(combinations);
    }

    // Recursive helper method to find all combinations
    public static void findAllCombinations(List<List<Integer>> combinations, List<Integer> currentCombination, int[] arr, int target, int start) {
        if (target < 0) {
            // Base case: target value exceeded, backtrack
            return;
        } else if (target == 0) {
            // Base case: target value reached, add current combination to the list
            combinations.add(new ArrayList<>(currentCombination));
        } else {
            // Explore all possible combinations starting from 'start' index
            for (int i = start; i < arr.length; i++) {
                if (i > start && arr[i] == arr[i - 1]) {
                    // Skip duplicate elements to avoid duplicate combinations
                    continue;
                }
                currentCombination.add(arr[i]);
                findAllCombinations(combinations, currentCombination, arr, target - arr[i], i + 1);
                currentCombination.remove(currentCombination.size() - 1);
            }
        }
    }

    // Method to convert a list of lists to a 2D array
    public static int[][] convertListToArray(List<List<Integer>> list) {
        int[][] result = new int[list.size()][];
        for (int i = 0; i < list.size(); i++) {
            List<Integer> innerList = list.get(i);
            result[i] = new int[innerList.size()];
            for (int j = 0; j < innerList.size(); j++) {
                result[i][j] = innerList.get(j);
            }
        }
        return result;
    }

    // Main method to execute the code
    public static void main(String[] args) {
        int[] arr = {1, 3, 2, 2, -4, -6, -2, 8};
        int target = 4;

        // Find pairs whose sum equals the target value
        int[][] pairs = findPairs(arr, target);

        System.out.println("First Combination for \"" + target + "\":");
        for (int[] pair : pairs) {
            System.out.println(Arrays.toString(pair));
        }

        // Merge pairs into a single array and sort it
        int[] mergedArray = mergeAndSort(pairs);
        System.out.println("Merge Into a Single Array:");
        System.out.println(Arrays.toString(mergedArray));

        // Find combinations that equal the double of the target value
        int[][] combinations = findCombinations(arr, target);

        System.out.println("Second Combination for \"" + (target * 2) + "\":");
        for (int[] combination : combinations) {
            System.out.println(Arrays.toString(combination));
        }
    }
}

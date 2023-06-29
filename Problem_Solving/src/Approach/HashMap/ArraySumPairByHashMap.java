package Approach.HashMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArraySumPairByHashMap {
    // Finds all combinations of two numbers in the given array that sum up to the target
    public static int[][] findTwoSumCombination(int[] nums, int target) {
        Map<Integer, List<Integer>> map = new HashMap<>();  // Map to store number indices for efficient lookup
        List<int[]> combinations = new ArrayList<>();  // List to store valid combinations

        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];  // Calculate the complement for the current number
            if (map.containsKey(complement)) {
                List<Integer> indices = map.get(complement);
                for (int index : indices) {
                    combinations.add(new int[]{nums[index], nums[i]});  // Add a combination to the list
                }
            }

            if (!map.containsKey(nums[i])) {
                map.put(nums[i], new ArrayList<>());  // Initialize the list of indices for a new number
            }
            map.get(nums[i]).add(i);  // Add the current number's index to the list
        }

        int[][] result = new int[combinations.size()][2];  // Convert the list to a 2D array
        for (int i = 0; i < combinations.size(); i++) {
            result[i] = combinations.get(i);
        }

        return result;  // Return the resulting combinations
    }

    // Finds all combinations of numbers in the given array that sum up to double the target
    public static int[][] findDoubleTargetCombination(int[] nums, int target) {
        int doubleTarget = target * 2;  // Calculate the double target value
        Arrays.sort(nums);  // Sort the array in ascending order for efficient combination search
        List<int[]> combinations = new ArrayList<>();  // List to store valid combinations
        findCombinations(nums, doubleTarget, new ArrayList<>(), combinations, 0);  // Recursively find combinations

        int[][] result = new int[combinations.size()][];  // Convert the list to a 2D array
        for (int i = 0; i < combinations.size(); i++) {
            result[i] = combinations.get(i);
        }

        return result;  // Return the resulting combinations
    }

    // Recursively finds combinations of numbers that sum up to the target value
    public static void findCombinations(int[] nums, int target, List<Integer> current, List<int[]> combinations, int start) {
        if (target == 0) {
            combinations.add(current.stream().mapToInt(Integer::intValue).toArray());  // Add a valid combination
            return;
        }

        for (int i = start; i < nums.length; i++) {
            if (nums[i] > target) {
                break;  // Skip numbers larger than the remaining target value
            }
            current.add(nums[i]);  // Add the current number to the combination
            findCombinations(nums, target - nums[i], current, combinations, i + 1);  // Recursive call with updated target
            current.remove(current.size() - 1);  // Remove the current number from the combination for backtracking
        }
    }

    public static void main(String[] args) {
        int[] nums = {1, 3, 2, 2, -4, -6, -2, 8};
        int target = 4;

        // Find combinations of two numbers that sum up to the target
        int[][] twoSumCombination = findTwoSumCombination(nums, target);
        System.out.println("First Combination For \"" + target + "\":");
        for (int[] combination : twoSumCombination) {
            System.out.println(Arrays.toString(combination));
        }

        int[] mergedArray = Arrays.stream(nums).sorted().toArray();
        System.out.println("Merge Into a single Array: " + Arrays.toString(mergedArray));

        // Find combinations of numbers that sum up to double the target
        int[][] doubleTargetCombination = findDoubleTargetCombination(mergedArray, target);
        int doubleTarget = target * 2;
        System.out.println("Second Combination For \"" + doubleTarget + "\":");
        for (int[] combination : doubleTargetCombination) {
            System.out.println(Arrays.toString(combination));
        }
    }
}

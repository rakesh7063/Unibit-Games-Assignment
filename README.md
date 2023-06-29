# Unibit-Games-Assignment

## <u> Problem Statement </u>
Given an array of integers and a target value, you must determine which two integers' sum
equals the target and return a 2D array. Then merge the array into a single array with sorting (
ascending ) order, in the next step double the target value and find again the combination of
digits (can be multiple digits ) that are equal to the double targeted value and returned into a 2D
array.

     Sample Input : [1, 3, 2, 2, -4, -6, -2, 8];

     Target Value = 4,

     Output: First Combination For “4” : [ [1,3],[2,2],[-4,8],[-6,2] ];

     Merge Into a single Array : [-6,-4,1,2,2,2,3,8];

     Second Combination For “8” : [ [ 1,3,2,2], [8,-4,2,2],....,[n,n,n,n] ]

# Explanation of Code
## 1. `ArraySumPairByHashMap`
<details>
<summary><b><u>Code</u></b></summary>

```Java
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


```
          
</details>

This Java program demonstrates how to find combinations of numbers in an array that sum up to a target value. It provides two different approaches:

 ### 1. findTwoSumCombination:
 This method uses a HashMap to efficiently find all combinations of two numbers in the given array that sum up to the target. It iterates through the array, calculates the complement for each number, and checks if the complement exists in the HashMap. If a complement is found, it creates a combination with the current number and adds it to the list of combinations. Finally, it returns a 2D array containing all the valid combinations.

### 2. findDoubleTargetCombination: 
This method finds combinations of numbers in the array that sum up to double the target value. It first sorts the array in ascending order for efficient combination search. Then, it recursively finds combinations using a backtracking approach. Starting from each index, it adds the current number to the combination and recursively calls itself with the updated target. If the target becomes zero, it adds the current combination to the list of valid combinations. This process continues until all combinations are explored. Finally, it returns a 2D array containing all the valid combinations.

## Usage
To use the program, follow these steps:

1. Define the input array (`nums`) and the target value (`targe`).

2. Call the `findTwoSumCombination` method, passing the nums array and the target value, to find combinations of two numbers that sum up to the target.

3. Print the first combination set obtained.

4. Create a merged array by sorting the `num`s array in ascending order using the `Arrays.sort` method.

5. Call the `findDoubleTargetCombination` method, passing the merged array and the `target` value multiplied by 2, to find combinations of numbers that sum up to double the target.

6. Print the second combination set obtained.

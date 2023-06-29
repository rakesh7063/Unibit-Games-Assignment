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
### 1. `ArraySumPairByHashMap`
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

- <b>`findTwoSumCombination:` </b> This method uses a HashMap to efficiently find all combinations of two numbers in the given array that sum up to the target. It iterates through the array, calculates the complement for each number, and checks if the complement exists in the HashMap. If a complement is found, it creates a combination with the current number and adds it to the list of combinations. Finally, it returns a 2D array containing all the valid combinations.

- <b>`findDoubleTargetCombination:` </b> This method finds combinations of numbers in the array that sum up to double the target value. It first sorts the array in ascending order for efficient combination search. Then, it recursively finds combinations using a backtracking approach. Starting from each index, it adds the current number to the combination and recursively calls itself with the updated target. If the target becomes zero, it adds the current combination to the list of valid combinations. This process continues until all combinations are explored. Finally, it returns a 2D array containing all the valid combinations.

## Usage
To use the program, follow these steps:

1. Define the input array (<b>`nums`</b>) and the target value (<b>`targe`</b>).

2. Call the <b> `findTwoSumCombination` </b> method, passing the nums array and the target value, to find combinations of two numbers that sum up to the target.

3. Print the first combination set obtained.

4. Create a merged array by sorting the <b>`nums`</b> array in ascending order using the <b>`Arrays.sort`</b> method.

5. Call the <b>`findDoubleTargetCombination`</b> method, passing the merged array and the <b>`target` </b> value multiplied by 2, to find combinations of numbers that sum up to double the target.

6. Print the second combination set obtained.


### 2. `ArraySumPairByTwoPointer`
<details>
<summary><b><u>Code</u></b></summary>

```Java
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

```

</details>

This Java program finds pairs and combinations in an array of integers based on a target value. It consists of the following key methods:
- <b> `findPairs(arr, target):`</b>  Finds pairs in the array whose sum equals the target value. It sorts the array, uses two pointers (left and right) to traverse the array, and adjusts the pointers based on the sum comparison with the target.

- <b> `mergeAndSort(pairs):` </b>  Merges pairs into a single array and sorts it in ascending order.

- <b> `findCombinations(arr, target):` </b> Finds combinations in the array that equal double the target value. It doubles the target value and uses a recursive helper method to explore all possible combinations.

  ## Usage

1. Define an array of integers (<b>`arr`</b>) and specify a target value.
2. Invoke the <b> `findPairs`</b> method to find pairs whose sum equals the target value.
3. Print the pairs found.
4. Invoke the <b> `mergeAndSort`</b> method to merge the pairs into a single sorted array.
5. Print the merged array.
6. Invoke the <b> `findCombinations`</b> method to find combinations that equal double the target value.
7. Print the combinations found.

### Time and Space Complexity Analysis
- <b> ArraySumPairByHashMap</b>
  <b>1. `findTwoSumCombination` method: </b>
  - <b> Time complexity: </b> O(n), where n is the length of the input array `nums`.
  - <b> Space Complexity:</b>  O(n), where n is the length of the input array `nums`. It uses a HashMap and a List to store the combinations, which can grow up to the size of the input array.

<b> 2. 

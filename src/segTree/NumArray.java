package segTree;

import java.util.*;

public class NumArray {

    public boolean checkAlmostEquivalent(String word1, String word2) {
        Map<Character,Integer> frequencyMap = new HashMap<>();
        for(char c : word1.toCharArray()){
            frequencyMap.put(c,frequencyMap.getOrDefault(c,0)+1);
        }
        for(char curr : word2.toCharArray()){
           int freq = frequencyMap.getOrDefault(curr,0);
           if(freq == 0){
               frequencyMap.put(curr,-1);
           }else{
               frequencyMap.put(curr,freq-1);
           }
        }
        for(Map.Entry<Character,Integer> entry : frequencyMap.entrySet()){
            if(Math.abs(entry.getValue()) > 3){
                return false;
            }
        }
        return true;
    }
    int[] tree;
    int n;
    public NumArray(int[] nums) {
        if (nums.length > 0) {
            n = nums.length;
            tree = new int[n * 2];
            buildTree(nums);
        }
    }


    public int searchInsert(int[] nums, int target) {
        int start = 0 , end = nums.length-1;
        if(nums[end] < target){
            return end+1;
        }
        while(start < end){
            int mid = start + (end - start)/2;
            if(nums[mid] == target){
                return mid;
            }
            if(nums[mid] < target){
                start = mid+1;
            }else {
                end = mid;
            }
        }
        return start;
    }
    private void buildTree(int[] nums) {
        for (int i = n, j = 0;  i < 2 * n; i++,  j++)
            tree[i] = nums[j];
        for (int i = n - 1; i > 0; --i)
            tree[i] = tree[i * 2] + tree[i * 2 + 1];
    }
    public void update(int index, int val) {
        int pos = index+n;
        tree[pos] = val;
        int parentIdx = pos/2;
        while(parentIdx > 0){
            tree[parentIdx] = tree[parentIdx*2] + tree[parentIdx*2 +1];
            parentIdx = parentIdx/2;
        }
    }

    public int sumRange(int left, int right) {
        int l = n+left;
        int r = n+right;
        int sum = 0;
        while(l <= r) {
            if (l % 2 == 1) {
                sum += tree[l];
                l++;
            }
            if (r % 2 == 0) {
                sum += tree[r];
                r--;
            }
            l = l/2;
            r = r/2;
        }
        return sum;
    }
}

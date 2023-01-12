package BinarySearch;

import java.util.*;

public class DoubleArray {

    public static void main(String[] args){
        DoubleArray da = new DoubleArray();
        int[] arr = {3,1,1};
        System.out.println(da.find132pattern(arr));
    }

    public int shipWithinDays(int[] weights, int days) {
        int low = 0 , high = 0, ans = Integer.MAX_VALUE;
        for(int i  =0; i < weights.length;i++){
            high += weights[i];
            low = Math.max(low,weights[i]);
        }
        while(low < high){
            int maxWeightAllowed = low+(high-low)/2;
            int d = calculateDays(maxWeightAllowed,weights);
            if(d > days){
               low = maxWeightAllowed+1;
            }else  if(d <= days){
                high = maxWeightAllowed-1;
                ans = maxWeightAllowed;
            }/*else{
                ans = maxWeightAllowed;
                high = maxWeightAllowed-1;
            }*/
        }
        return ans;
    }

    private int calculateDays(int maxWeightAllowed, int[] weights) {
        int count = 1, sum =0;
        for(int i =0 ; i < weights.length;i++){
            if(sum + weights[i] > maxWeightAllowed){
                count++;
                sum = 0;
            }
            sum += weights[i];
        }
        return count;
    }

    public int[] searchRange(int[] nums, int target) {
        int[] res = new int[2];
        int firstIndx = findLastOccurance(nums,target,true);
        if(firstIndx == -1){
            Arrays.fill(res,-1);
            return res;
        }
        int lastIdx = findLastOccurance(nums,target,false);
        res[0] = firstIndx;
        res[1] = lastIdx;
        return res;
    }

    private int findLastOccurance(int[] nums, int target,boolean isFirst) {
        int low = 0, high = nums.length, ans =-1;
        while(low <= high){
            int mid = low +(high-low)/2;
            if(nums[mid] > target){
                high = mid-1;
            }else if(nums[mid] < target){
                low = mid+1;
            }else{
                ans = mid;
                if(isFirst) {
                    low = mid + 1;
                }else{
                    high = mid-1;
                }
            }
        }
        return ans;
    }



    public int findKthPositive(int[] arr, int k) {
        if(arr[0] > k){
            return k;
        }
        int low = 0 , high = arr.length-1;
        while(low < high){
            int mid = low+(high - low)/2;
            int actMidValue = arr[mid];
            int expectedMidVal = low+1+(high - low)/2;
            if(actMidValue - expectedMidVal  < k){
                low = mid+1;
            }else {
                high = mid;
            }

        }
        return low+k;
    }
    public int minSubArrayLen(int target, int[] nums) {
        int currSum = 0, minLen = Integer.MAX_VALUE, len = 0,start =0;
        for(int i  =0 ; i < nums.length;i++){
            currSum = currSum + nums[i] ;
            while(currSum >= target){
                minLen = Math.min(minLen,i-start+1);
                currSum = currSum - nums[start];
                start = start+1;
            }
        }
        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }

    public int findPeakElement(int[] nums) {
        int low =0 , high = nums.length;
        while(low < high){
            int mid = low +(high-low)/2;
            if(mid-1 >=low && nums[mid] > nums[mid-1] && mid+1 <= high && nums[mid] > nums[mid+1]){
                return mid;
            }
            if(mid-1 >=low && nums[mid] < nums[mid-1]){
                high = mid-1;
            }else {
                low = mid;
            }
        }
        return low;
    }

    public boolean find132pattern(int[] nums) {
        if(nums.length < 3)
            return false;
        int n = nums.length;
        int[] min = new int[n];
        min[0] = nums[0];
        for(int i = 1 ; i < n ;i++){
            min[i] = Math.min(min[i-1],nums[i]); //nums[i]
        }
        for (int j = nums.length - 1, k = nums.length; j >= 0; j--) {
            if(nums[j] > min[j]){
                while(nums[k] <= min[j] && k< nums.length){
                    k++;
                }
                if(k<nums.length && nums[k] > min[k] ){
                    return true;
                }
                nums[--k] = nums[j];
            }
        }
        return false;
    }

    public int splitArray(int[] nums, int m) {
        int low = 0, high =0,minSum = Integer.MAX_VALUE;
        for(int i  = 0 ; i < nums.length ;i++){
            low = Math.max(low,nums[i]);
            high += nums[i];
        }
        while(low <= high){
            int mid = low +(high-low)/2;
            int possibleSplits = getPossibleSplits(mid,nums);
            if(possibleSplits > m){
                low = mid+1;
            }else {
                high = mid-1;
                minSum = mid;
            }
        }
        return minSum;
    }

    private int getPossibleSplits(int maxSum, int[] nums) {
        int splitCount = 0, sumSoFar = 0 , sum = 0;
        for(int i  = 0 ; i < nums.length ;i++){
            if(sumSoFar + nums[i] > maxSum){
                splitCount++;
                sum = Math.max(sum, sumSoFar);
                sumSoFar = 0;
            }
            sumSoFar += nums[i];
        }
        return splitCount+1;
    }

    public int[] findOriginalArray(int[] changed) {
        Map<Integer,Integer> valFreq = new HashMap<>();
        int[] res = new int[changed.length/2];
        if(changed.length % 2 != 0){
            return res;
        }
        for(int i : changed){
            if(i==0){
                return res;
            }
            valFreq.put(i,valFreq.getOrDefault(i,0)+1);
        }

        int idx =0;
        for(int i : changed){
            int doubleVal = 2*i;
            if(valFreq.containsKey(doubleVal)){
                res[idx++] = i;
                if(valFreq.get(doubleVal)-1 == 0){
                    valFreq.remove(doubleVal);
                }else{
                    valFreq.put(doubleVal,valFreq.get(doubleVal)-1);
                }
                if(valFreq.get(i)-1 == 0){
                    valFreq.remove(i);
                }else{
                    valFreq.put(i,valFreq.get(i)-1);
                }
            }
        }

        return valFreq.size() == 0 ? res : new int[0];
    }
}

package Design;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Twitter {

    public static void main(String[] args){
        MedianFinder medianFinder = new MedianFinder();
        medianFinder.addNum(1);
        medianFinder.addNum(2);
        medianFinder.findMedian();

    }

    public int orangesRotting(int[][] grid) {
        int minMinutes = 0;
        for(int i  =0 ; i < grid.length;i++){
            for(int j  =0 ; j < grid[0].length;j++){
                if(grid[i][j] == 1){
                    changeAdjOrange(grid,i,j);
                    minMinutes++;
                }
            }
        }
        return minMinutes;
    }

    private void changeAdjOrange(int[][] grid, int r, int c) {
        int[] x = {0,0,-1,1};
        int[] y = {-1,1,0,0};
        for(int i  = 0 ; i < 4 ;i++){

        }


    }

    static class MedianFinder {
        PriorityQueue<Integer> maxHeap ;
        PriorityQueue<Integer> minHeap;
        public MedianFinder() {
            maxHeap = new PriorityQueue<>((a,b) -> (b-a));
            minHeap = new PriorityQueue<>((a,b) -> (a-b));
        }

        public void addNum(int num) {
            if(maxHeap.size() == 0 || maxHeap.peek() > num){
                maxHeap.add(num);
                if(Math.abs(maxHeap.size() - minHeap.size()) > 1){
                    minHeap.add(maxHeap.poll());
                }
            }else {
                minHeap.add(num);
                if(Math.abs(maxHeap.size() - minHeap.size()) > 1){
                    maxHeap.add(minHeap.poll());
                }
            }
        }

        public double findMedian() {
            double med = 0;
           if(minHeap.size()>maxHeap.size()){
                med = minHeap.peek();
           }else if(minHeap.size()<maxHeap.size()){
                med = maxHeap.peek();
            }else{
                med = (maxHeap.peek()+minHeap.peek())/2D;
            }
            return med;
        }
    }

    public char slowestKey(int[] releaseTimes, String keysPressed) {
        char slowKey = keysPressed.charAt(0);
        int highestDuration = releaseTimes[0];
        for(int i = 1 ; i < releaseTimes.length;i++){
            int diff = releaseTimes[i] - releaseTimes[i-1];
            char c = keysPressed.charAt(i);
           if(diff > highestDuration){
               slowKey = c;
               highestDuration = diff;
           }else if(diff == highestDuration && slowKey < c){
               slowKey = c;
           }
        }
        return slowKey;
    }
}

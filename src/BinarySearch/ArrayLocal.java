package BinarySearch;

import java.util.*;

public class ArrayLocal {

    public static void main(String[] args)
    {
        int arr[] = {2,4,7,9,10,14};
        int n = arr.length;
        ArrayLocal ar = new ArrayLocal();
        System.out.println(ar.missingElement(arr,4 ));
    }



    public int missingElement(int[] nums, int k) {
        int n = nums.length;

        if(k > missing(n-1,nums)){
          return k- missing(n-1,nums) + nums[n-1];
        }
        int left = 0 , right = n;
        while(left < right){
            int mid = left + (right - left)/2;
            if(missing(mid,nums) < k ){
                left = mid +1;
            }else {
                right = mid;
            }
        }
        return k-missing(left-1,nums) + nums[left-1];
    }
    int missing(int idx, int[] nums) {
        return nums[idx] - nums[0] - idx;
    }

    public int findTheWinner(int n, int k) {
       Node root = new  Node(1);
       Node prev = root;
       Node next = null;
       for(int i = 2 ; i <= n ; i++){
            next = new Node(i);
           prev.next = next;
           next.prev = prev;
           prev = next;
       }
       next.next = root;
       root.prev = prev;

       Node start = prev;
       for(int i = 0 ; i < n-1 ; i++){
           for(int j = 0 ; j < k ;j++){
               start = start.next;
           }
           kill(start);
       }
       return start.next.val;
    }

    private void kill(Node node) {
        Node last = node.prev;
        Node nxt = node.next;
        nxt.prev = last;
        last.next = nxt;
    }


    public int triangleNumber(int[] nums) {
        Arrays.sort(nums);
        int count = 0;
        for(int i =0; i < nums.length-2 ;i++){
            int k = i+2;
            for( int j = i+1 ; j < nums.length-1 ; j++) {
                while (nums[i] + nums[j] > nums[k]) {
                    k++;
                }
                count += j-k-1;
            }
        }
        return count;
    }

    static int maxPartitions(int arr[], int n)
    {
        int ans = 0, max_so_far = 0;
        for (int i = 0; i < n; ++i) {

            // Find maximum in prefix arr[0..i]
            max_so_far = Math.max(max_so_far, arr[i]);

            // If maximum so far is equal to index, we can
            // make a new partition ending at index i.
            if (max_so_far == i)
                ans++;
        }
        return ans;
    }

    class JewelStone {
        int weight, value;
        JewelStone(int weight, int value) {
            this.weight = weight;
            this.value = value;
        }
    }


        int getMaxValue (JewelStone[] stones, int capacity) {
            Arrays.sort(stones, new Comparator<JewelStone>() {
                @Override
                public int compare(JewelStone jewelStone, JewelStone t1) {
                    return t1.value-jewelStone.value;
                }
            });

            int totalValue = 0 , i =0;
            while(capacity > 0 && i < stones.length){

               if(capacity >= stones[i].weight){
                   totalValue += stones[i].weight;
                   capacity -= stones[i].weight;
               }
            }
            return totalValue;
        }


    int pathExist[][] ;
    public boolean hasPath(int[][] maze, int[] start, int[] destination) {
        pathExist = new int[maze.length][maze[0].length];
        int m = maze.length;
        int n = maze[0].length;
        for(int[] arr:pathExist){
            Arrays.fill(arr,0);
        }
        return hasPathRec(maze,start,destination,m,n);
    }

    private boolean hasPathRec(int[][] maze, int[] start, int[] destination,int m , int n) {
        if(start[0] == m && start[1] == n){
            return false;
        }
        if(start[0] == destination[0] && start[1] == destination[1]){
            pathExist[start[0]][start[1]] = 2;
            return true;
        }
        if(pathExist[start[0]][start[1]] != 0){
            return pathExist[start[0]][start[1]] == 2 ? true : false;
        }
        pathExist[start[0]][start[1]] = 1;
        int[] adjX = {-1,1,0,0};
        int[] adjY = {0,0,-1,1};
        for(int i =0 ; i < 4; i++){
            int x = start[0]+ adjX[i];
            int y = start[1]+adjY[i];
            if (x >= m || x < 0 || y >= n || y < 0 || pathExist[x][y] == 1 || maze[x][y] == 1) {
                continue;
            }
            int[] newStart = {x,y};
            if(hasPathRec(maze,newStart,destination,m,n)){
                pathExist[x][y] = 2;
                return true;
            }
        }
        pathExist[start[0]][start[1]] = -1;
        return false;
    }
}
class BoundedBlockingQueue {

    Queue<Integer> qu ;
    int  capacity ;
    public BoundedBlockingQueue(int capacity) {
        qu = new LinkedList<>();
        this.capacity =capacity;
        //this.size =0;
    }

    public void enqueue(int element) throws InterruptedException {
        synchronized (this){
            while(qu.size() >= capacity){
                wait();
            };
            qu.add(element);
            notifyAll();
        }
    }

    public int dequeue() throws InterruptedException {
        synchronized (this){
            while(qu.size() == 0){
               wait();
            };
            int a = qu.poll();
            notifyAll();
            return a;
        }
    }

    public int size() {
        synchronized (this) {
            return qu.size();
        }
    }
}



class MinStack {

    Stack<Integer> mainSTack;
    Stack<int[]> minStack;
    public MinStack() {
        mainSTack = new Stack<>();
        minStack = new Stack<>();
    }

    public void push(int val) {
        mainSTack.push(val);
        if(minStack.isEmpty() || minStack.peek()[0] > val){
            minStack.push(new int[]{val, 1});
        }else if(minStack.peek()[0] == val){
            minStack.peek()[1]++;
        }
    }

    public void pop() {
        int a = mainSTack.pop();
        int[] lastMin = minStack.peek();
        if(lastMin[0] == a){
            if(lastMin[1] == 1){
                minStack.pop();
            }else{
                minStack.peek()[1]--;
            }
        }
    }

    public int top() {
       return mainSTack.peek();
    }

    public int getMin() {
        return minStack.peek()[0];
    }
}

class Node{
    int val ;
    Node next;
    Node prev;
    Node(int val){
        this.val  = val;
    }
}

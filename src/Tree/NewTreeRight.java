package Tree;
// CA3JMADJBA

import sun.reflect.generics.tree.Tree;

import java.util.*;
import java.util.function.IntPredicate;

public class NewTreeRight {

    public static void main(String[] args){
        NewTreeRight n = new NewTreeRight();
       n.reverseWords("  hello world  ");
    }

    Node head,tail;
    public Node treeToDoublyList(Node root) {
        if(root == null){
            return null;
        }
        treeToDoublyListR(root);
        head.left = tail;
        tail.next = head;
        return head;
    }

    public void treeToDoublyListR(Node root) {
        if(root == null){
            return ;
        }
        treeToDoublyListR(root.left);

        if(tail != null) {
            tail.right = root;
            root.left = tail;
        }else{
            head = root;
        }
        tail = root;
        treeToDoublyListR(root.right);
    }

    boolean isPresent = false;
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        if(root == null){
            return null;
        }
        TreeNode r = callRec(root,p,null);
        return isPresent == true ? r : null;
    }

    private TreeNode callRec(TreeNode root, TreeNode p, TreeNode prev) {
        if(root == null){
            return null;
        }
        if(prev == p){
            isPresent = true;
            return root;
        }
        TreeNode left = callRec(root.left, p,prev);
        if(p ==  left){
            isPresent = true;
            return root;
        }
        TreeNode right = callRec(root.right,p,root);
        return right != null ? right : root;

    }

    public String reverseWords(String s) {
        s = s.replaceAll("([ ]{2,})"," ");
        String[] words = s.split(" ");
        StringBuilder res = new StringBuilder();
        for(int i = words.length-1 ; i >=0 ;i--){
            res.append(words[i]);
            if(i > 0) res.append(" ");
        }
        return res.toString();
    }

    public int findCelebrity(int n) {
        boolean flag = false;
        for(int i = 0 ; i < n ;i++){
            flag = false;
            for(int j = 0 ; j < n ; j++){
                if(i != j && knows(i,j)){
                    flag = true;
                    break;
                }
            }
            if(!flag){
                return i;
            }
        }
        return -1;
    }

    boolean knows(int a, int b){return false;}

    public int minCost(String colors, int[] neededTime) {
        int totalCost = 0;
        char lastChar = colors.charAt(0);
        int lastTime = neededTime[0];
        for(int i = 1 ; i < colors.length();i++){
            char curr = colors.charAt(i);
            if(curr == lastChar) {
                while (i < colors.length() && colors.charAt(i) == lastChar) {
                    curr = colors.charAt(i);
                    if (lastTime > neededTime[i]) {
                        totalCost += neededTime[i];
                    } else {
                        totalCost += lastTime;
                        lastTime = neededTime[i];
                    }
                    lastChar = curr;
                    i++;
                }
                i--;
            }else{
                lastChar = curr;
                lastTime = neededTime[i];
            }

        }
        return totalCost;
    }

    public List<Integer> boundaryOfBinaryTree(TreeNode root) {
        List<Integer> leaves = new ArrayList<>();
        List<Integer> leftBoundry = new ArrayList<>();
        List<Integer> rightBoundry = new ArrayList<>();
        preorder(root,leaves,leftBoundry,rightBoundry,0);
        leftBoundry.addAll(leaves);
        leftBoundry.addAll(rightBoundry);
        return leftBoundry;
    }

    private void preorder(TreeNode root, List<Integer> leaves, List<Integer> leftBoundry, List<Integer> rightBoundry, int flag) {
        if(root == null){
            return;
        }

        if(isLeftBoundry(root,flag)){
            leftBoundry.add(root.val);
        }
        else if(isLeaf(root)){
            leaves.add(root.val);
        }
        else if(isRightBoundry(root,flag)){
            rightBoundry.add(0,root.val);
        }
        preorder(root.left,leaves,leftBoundry,rightBoundry,checkLeftChild(root,flag));
        preorder(root.right,leaves,leftBoundry,rightBoundry,rightChildFlag(root,flag));
    }
    public int rightChildFlag(TreeNode cur, int flag) {
        if (isRightBoundry(cur,flag) || flag ==0)
            return 2;
        else if (isLeftBoundry(cur,flag) && cur.left == null)
            return 1;
        else return 3;
    }

    private int checkLeftChild(TreeNode root, int flag) {
        if(flag == 0 || isLeftBoundry(root, flag)){
            return 1;
        }
        if(isRightBoundry(root, flag) && root.right == null){
            return 2;
        }
        return 3;
    }

    private boolean isRightBoundry(TreeNode root, int flag) {
        if(flag == 2){
            return true;
        }
        return false;
    }

    private boolean isLeftBoundry(TreeNode root, int flag) {
        if(flag == 0 || flag == 1){
            return true;
        }
        return false;
    }

    private boolean isLeaf(TreeNode root) {
        if(root.left == null && root.right == null){
            return true;
        }
        return false;
    }

    int minCost = Integer.MAX_VALUE;

    public int minCostIIDP(int[][] costs) {
        int totalColour = costs[0].length; int totalHouse = costs.length;
        int[][] dp = new int[totalHouse][totalColour];
        int min = totalColour ,nextMin = totalColour;
        for(int i = 0 ; i < totalColour;i++){
            dp[0][i] = costs[0][i];
            if(min == totalColour || dp[0][i] < dp[0][min]){
                nextMin = min;
                min = i;
            }else if(nextMin == totalColour || dp[0][i] <dp[0][nextMin]){
                nextMin = i;
            }
        }
        for(int houseNumber = 1 ; houseNumber < totalHouse ;houseNumber++) {
            int currMin = totalColour, currNextMin = totalColour;
            for (int i = 0; i < totalColour; i++) {
                dp[houseNumber][i] = costs[houseNumber][i];
                if (i == min) {
                    dp[houseNumber][i] += dp[houseNumber - 1][nextMin];
                } else {
                    dp[houseNumber][i] += dp[houseNumber - 1][min];
                }
                if (dp[houseNumber][i] < dp[houseNumber][currMin]) {
                    currNextMin = currMin;
                    currMin = i;
                } else if (dp[houseNumber][i] < dp[houseNumber][currNextMin]) {
                    currNextMin = i;
                }
            }
            min = currMin;
            nextMin = currNextMin;
        }
        return dp[totalHouse-1][min];
    }

    public int minCostII(int[][] costs) {
        int[][] mem = new int[costs.length][costs[0].length];
        for(int i  = 0 ; i < costs[0].length; i++) {
            callMinCost(costs, i, 1, costs[0][i]);
        }
        return minCost == Integer.MAX_VALUE ? 0 :minCost;
    }

    private void callMinCost(int[][] costs, int colourSelectedInLastHouse, int houseNumber, int totalCOstSOFar) {
        if(houseNumber == costs.length){
            minCost = Math.min(minCost,totalCOstSOFar);
            return;
        }
        for(int i  =0 ; i < costs[0].length; i++){
            if(colourSelectedInLastHouse != i){
                totalCOstSOFar += costs[houseNumber][i];
                callMinCost(costs,i,houseNumber+1,totalCOstSOFar);
                totalCOstSOFar -= costs[houseNumber][i];
            }
        }
    }


    private void callChildDistK(TreeNode root, int k, List<Integer> res) {
        if(root == null) return;
        if(k == 0 &&  root != null){
            res.add(root.val);
            return;
        }
        callChildDistK(root.left,k-1,res);
        callChildDistK(root.right,k-1,res);
    }
    int res = 0;
    public int findDistance(TreeNode root, int p, int q) {
        if (p == q) return 0;
        res = -1;
        Comparator<TreeNode> a = ( t1 ,  t2) -> Integer.valueOf(t1.val).compareTo(t2.val);
        findDistance1(root,p,q);
        return res;
    }
    public int findDistance1(TreeNode root, int p, int q) {
        if(root == null){
            return -1;
        }
        int left = -1, right = -1;
        left = findDistance1(root.left,p,q);
        right = findDistance1(root.right,p,q);

        if(res != 0){
            return -1;
        }
        if(root.val == p || root.val == q){
            if(left == -1 && right ==-1){
              return 0;
            }
            res = (left != -1 ? left : right) +1;
            return -1;
        }
        if(left != -1 && right != -1 ){
            res = left+right+2;
            return -1;
        }
        if(left >= 0){
            return left+1;
        }
        if(right >= 0){
            return right+1;
        }
        return -1;
    }

   /* int max = 0;
    public int largestBSTSubtree(TreeNode root) {

    }
    public MinMaxNode largestRecBSTSubtree(TreeNode root) {
        if(root ==  null){
            return null;
        }
        if(root.left == null && root.right == null){
            max = Math.max(max, 1);
            return new MinMaxNode(root.val);
        }
        MinMaxNode left = largestRecBSTSubtree(root.left);
        MinMaxNode right = largestRecBSTSubtree(root.right);
        MinMaxNode newNode = null;

        if(root.val > left.max && root.val < right.min){
            newNode = new MinMaxNode(root.val);
            newNode.min = left.min;
            newNode.max = right.max;
            newNode.count += left.count + right.count ;
            max = Math.max(max, newNode.count);
        }
        return

    }*/

    class MinMaxNode{
        int min;
        int max;
        int val;
        int count;
        MinMaxNode(int val){
            count =1;
            this.val = val;
            this.min = val;
            this.max = val;
        }
    }
    Node leftMost = null, prev = null ; // leftMost = left most child of next level
    public Node connect(Node root) {    // prev = prev node in the next level
        if (root == null) {
            return null;
        }
        Node curr = null; // curr node at the current level
        leftMost = root;
        while(leftMost != null){
            curr = leftMost;
            prev = null;
            leftMost = null;
            while(curr != null) {
                if(curr.left != null) {
                    processChild(curr.left);
                }
                if(curr.right != null){
                    processChild(curr.right);
                }
                curr = curr.next;
            }
        }
        return root;
    }

    private void processChild(Node child) {
        if(prev == null){
            leftMost = child;
        }else{
            prev.next = child;
        }
        prev = child;
    }


    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    };


  public class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode() {}
      TreeNode(int val) { this.val = val; }
      TreeNode(int val, TreeNode left, TreeNode right) {
          this.val = val;
          this.left = left;
          this.right = right;
      }
  }

}



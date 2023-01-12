package ooa.fb;

import com.sun.xml.internal.ws.util.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Test {

    public static void main(String[] args){
        Test t = new Test();
        //String a = "ab";
        int[] a = {1,2,2,3,1};
        t.findShortestSubArray(a);
    }
    public int findShortestSubArray(int[] nums) {
        Map<Integer,StartEndIndxNFreq> map = new HashMap<>();
        int maxFreq = 0;
        Set<Integer> maxValues = new HashSet<>();
        int minSize = Integer.MAX_VALUE ;
        for(int i = 0 ; i < nums.length;i++){
            StartEndIndxNFreq startEndIndxNFreq =  map.getOrDefault(nums[i],new StartEndIndxNFreq());
            startEndIndxNFreq.freq++;
            startEndIndxNFreq.start = (startEndIndxNFreq.start == -1) ? i : startEndIndxNFreq.start;
            startEndIndxNFreq.end = i;
            if(maxFreq < startEndIndxNFreq.freq){
                maxValues = new HashSet<>();
                maxFreq = startEndIndxNFreq.freq;
                maxValues.add(nums[i]);
            }else if(maxFreq == startEndIndxNFreq.freq){
                maxValues.add(nums[i]);
            }
            map.put(nums[i],startEndIndxNFreq);
        }
        for(Integer value : maxValues){
            StartEndIndxNFreq a = map.get(value);
            minSize = Math.min(minSize, a.end-a.start+1);
        }
        return minSize;
    }

    class StartEndIndxNFreq{
        int start = -1;
        int end;
        int freq;
    }


    public boolean isAlienSorted(String[] words, String order) {
        for(int i = 0 ; i < words.length-1 ;i++){
            if(compare(words[i],words[i+1],order) == 1){
                return false;
            }
        }
        return true;
    }
    int compare(String curr , String o,String order){
            for(int i  =0 ; i < Math.min(o.length(),curr.length());i++) {
                int idx1 = order.indexOf(curr.charAt(i));
                int idx2 = order.indexOf(o.charAt(i));
                if (idx1 < idx2){
                    return -1;
                }else if (idx1 > idx2){
                    return 1;
                }
            }
            if(o.length() == curr.length()){
                return 0;
            }
            return o.length() > curr.length() ? -1 : 1;
        }


    public boolean winnerOfGame(String colors) {
        int aliceCount = 0 , bobCount =0;
        for(int i=1; i < colors.length()-1;i++){
            if(colors.substring(i-1,i+2).equals("AAA")){
                aliceCount++;
            }
            if(colors.substring(i-1,i+2).equals("BBB")){
                bobCount++;
            }
        }
        return aliceCount > bobCount ? true : false;
    }
    int len;
    String res;
    public String longestDiverseString(int a, int b, int c) {
        StringBuilder str = new StringBuilder();
        String[][][]  dp = new String[a+1][b+1][c+1];
        callRec(a,b,c,str,dp);
        return res;
    }

    private void callRec(int a, int b, int c, StringBuilder str,String[][][]  dp) {

       if(str.length() > len){
           res = str.toString();
           len = res.length();
       }
       if(a <=0 && b<=0 && c<=0) {
           return;
       }

       if(dp[a][b][c] != null && !dp[a][b][c].isEmpty()){
           str.append(dp[a][b][c]);
           return;
       }

       if(a > 0 && (str.length() <= 1 || !str.substring(str.length()-2).equals("aa"))) {
           StringBuilder tmp = new StringBuilder(str);
           str.append('a');
           dp[a][b][c] = str.toString();
           callRec(a-1,b,c,str,dp);
           str = tmp;
       }
        if(b > 0 && (str.length() <= 1 || !str.substring(str.length()-2).equals("bb"))) {
            StringBuilder tmp = new StringBuilder(str);
            str.append('b');
            dp[a][b][c] = str.toString();
            callRec(a,b-1,c,str,dp);
            str = tmp;
        }
        if(c > 0 && (str.length() <= 1 || !str.substring(str.length()-2).equals("cc"))) {
            StringBuilder tmp = new StringBuilder(str);
            str.append('c');
            dp[a][b][c] = str.toString();
            callRec(a,b,c-1,str,dp);
            str = tmp;
        }
    }

    public int arraySign(int[] nums) {
        double prod = 1 ;
        for(int i =0 ; i < nums.length;i++){
            if(nums[i] == 0){
                return 0;
            }
            prod *= nums[i];
        }
        return (prod < 0)? -1:1;
    }
    int maxCount;
    public int goodNodes(TreeNode root) {
        callaRec(root,Integer.MIN_VALUE);
        return maxCount;
    }

    private void callaRec(TreeNode root, int maxSoFar) {
        if(root == null){
            return;
        }
        if(maxSoFar < root.val) {
            maxSoFar = root.val;
            maxCount++;
        }
        callaRec(root.left,maxSoFar);
        callaRec(root.right,maxSoFar);
    }

    public int maxArea(int h, int w, int[] horizontalCuts, int[] verticalCuts) {
        double mod = Math.pow(10,9)+7;
        Arrays.sort(horizontalCuts);
        Arrays.sort(verticalCuts);
        int maxHor = horizontalCuts[0], maxVert =verticalCuts[0];
        for(int i = 1 ; i < horizontalCuts.length;i++){
            maxHor = Math.max(maxHor,horizontalCuts[i]-horizontalCuts[i-1]);
        }
        maxHor  = Math.max(maxHor, h-horizontalCuts[horizontalCuts.length-1]);
        for(int i = 1 ; i < verticalCuts.length;i++){
            maxVert = Math.max(maxVert,verticalCuts[i]-verticalCuts[i-1]);
        }
        maxVert  = Math.max(maxVert, w-verticalCuts[verticalCuts.length-1]);
        double res =  (maxHor*maxVert)/mod;
        return (int)res;
    }
   int closestVal;
    public int closestValue(TreeNode root, double target) {
        closestVal = root.val;
        callClosetest(root,target);
        return (int) closestVal;
    }

    private void callClosetest(TreeNode root, double target) {
        if(root == null){
            return ;
        }
        double diff = Math.abs(root.val-target);
        if(diff < Math.abs(closestVal-target)){
             closestVal = root.val;
        }

        if(root.val > target ){
            callClosetest(root.left,target);
        }else {
            callClosetest(root.right,target);
        }
    }
}

 class TreeNode {
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

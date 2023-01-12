package dp;

import java.util.*;
import java.util.stream.Collectors;

public class Knight {

    public static void main(String[] args){
        Knight knight = new Knight();
        knight.knightDialer(3131);
    }

    Map<String,List<Integer>> resultPerString = new HashMap<>();
    public List<Integer> diffWaysToCompute(String expression) {
        List<Integer> result = new ArrayList<>();
        for(int i = 0 ; i< expression.length();i++){
            char currChar = expression.charAt(i);
            if(currChar == '+' || currChar == '-' || currChar == '*'){
                String firstPart = expression.substring(0,i);
                String secondPart = expression.substring(i+1);
                List<Integer> firstRes = resultPerString.getOrDefault(firstPart,diffWaysToCompute(firstPart));
                List<Integer> secondERes = resultPerString.getOrDefault(secondPart,diffWaysToCompute(secondPart));
                for(Integer first:firstRes){
                    for(Integer sec: secondERes){
                        switch (currChar){
                            case '+':
                                result.add(first+sec); break;
                            case '-':
                                result.add(first-sec); break;
                            case '*':
                                result.add(first*sec); break;
                        }
                    }
                }
            }
        }
        if(result.size() == 0){
            result.add(Integer.parseInt(expression));
        }
        resultPerString.put(expression,result);
        return result;
    }


    public boolean wordBreak(String s, List<String> wordDict) {
        Set<String> words = wordDict.stream().collect(Collectors.toSet());
        int[] mem = new int[s.length()];
        Arrays.fill(mem,-1);
        return wordBreakRec(s,0, words,mem);
    }

    private boolean wordBreakRec(String s, int start, Set<String> words,int[] mem) {
        if(start == s.length()){
            return true;
        }
        if(mem[start] != -1){
            return mem[start] == 1 ? true: false;
        }
        boolean res = false;
        for(int end = start+1; end <= s.length();end++){
            String subWord = s.substring(start,end);
            if(words.contains(subWord)){
                res = wordBreakRec(s,end, words,mem);
                if(res){
                    mem[start] = 1;
                    return true;
                }
            }
        }
        mem[start] = 0;
        return res;
    }

    int max = (int) Math.pow(10,9)+7;

    public int knightDialer(int n) {
        if(n == 1){
            return 10;
        }
        long[][][] mem = new long[n][4][3];

        for(int i = 0; i < 4; i++ ){
            Arrays.fill(mem[0][i],1);
        }
        long count = 0;
        for(int step = 1 ; step < n ;step++){
            for(int x = 0 ; x < 4 ;x++){
                for(int y =0 ; y < 3 ;y++){
                    if(x ==3 && y != 1){
                        continue;
                    }
                    mem[step][x][y] = callKnight(step,x,y,mem);
                    count = (count+mem[step][x][y]) % max;
                }
            }
        }
        return (int)count;
    }

    private long callKnight(int n, int x, int y, long[][][] mem) {
        long count = 0;
        int[] xDirec = {-2,-2,-1,-1,2,2,1,1};
        int[] yDirec = {1,-1,2,-2,-1,1,-2,2};
        for(int i = 0 ; i < 8 ;i++){
            int newX = x + xDirec[i];
            int newY = y + yDirec[i];
            if(newX >= 4 || newY >= 3 || newX < 0 || newY < 0 || (newX ==3 && newY != 1)){
                continue;
            }
            count += mem[n-1][newX][newY] % max;
        }
        mem[n][x][y] = count;
        return count;
    }

}

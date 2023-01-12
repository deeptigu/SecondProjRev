package String;

import java.util.*;
import java.util.stream.Collectors;

public class Tic_Tac_Toe {

    public boolean stoneGame(int[] piles) {
       int[][] dp = new int[piles.length][piles.length];
       //Arrays.fill(dp[0],0);
       for(int i = 0 ; i < dp.length;i++){
           Arrays.fill(dp[i],0,dp.length,-1);
          // dp[i][0] =0;
       }
       int val = callHelper(piles,0, piles.length-1,dp);
        if(val > 0){
            return true;
        }
        return false;
    }

    private int callHelper(int[] piles, int start, int end, int[][] dp) {
        if(start == end){
            dp[start][end] = piles[start];
            return piles[start];
        }
        if(start > end){
            return 0;
        }
        if(dp[start][end] != -1){
            return dp[start][end];
        }
        int max = piles[start] - callHelper(piles,start+1, end,dp);
        max = Math.max(max, piles[end]-callHelper(piles,start, end-1,dp));
        dp[start][end] = max;
        return max;
    }

    public boolean stoneGameNo(int[] piles) {
        int start = 0 , end = piles.length-1;
        int aliceSum  =0 , bobSum =0;
        boolean isOdd = true;
        while(start<=end){
            if(isOdd){
                if(piles[start] >= piles[end]){
                    aliceSum += piles[start++];
                }else{
                    aliceSum += piles[end--];
                }
            }else {
                if(piles[start] > piles[end]){
                    bobSum += piles[end--];
                }else{
                    bobSum -= piles[start++];
                }
            }
            isOdd = !isOdd;
        }
        return aliceSum > bobSum ? true : false;
    }

    public List<String> crawl(String startUrl, HtmlParser htmlParser) {
        String hostname= startUrl.split("/")[2];
        Queue<String> queue = new LinkedList<>();
        Set<String> res = new HashSet<>();
        queue.offer(startUrl);

        while (!queue.isEmpty()){
            String url = queue.poll();
            List<String> urls = htmlParser.getUrls(url);
            urls.forEach(link -> {
               if(link.contains(hostname)) {
                   res.add(link);
                   queue.add(link);
               }
            });
        }
    return res.stream().collect(Collectors.toList());
    }

    public boolean validTicTacToe(String[] board) {
        int S = board.length;
        int[] rows = new int[S];
        int[] cols = new int[S];
        int dia = 0 , antiD = 0 , turns =0;
        boolean xWins = false, oWins = false;
        for(int i =0 ; i < S; i++){
            for(int j = 0 ; j < S ;j++){
                if(board[i].charAt(j) == 'X'){
                    rows[i]++;
                    cols[j]++;
                    turns++;
                    if(i == j){
                        dia += 1;
                    }
                    if(i+j == S-1){
                        antiD += 1;
                    }
                    if(rows[i] == 3 || cols[j] == 3 || dia == 3 || antiD == 3){
                        xWins = true;
                    }

                } else if(board[i].charAt(j) == 'O'){
                    rows[i]--;
                    cols[j]--;
                    turns--;
                    if(i == j){
                        dia -= 1;
                    }
                    if(i+j == S-1){
                        antiD -= 1;
                    }
                    if(rows[i] == -3 || cols[j] == -3 || dia == -3 || antiD == -3){
                        oWins = true;
                    }
                }
            }
        }

        if (xWins && turns == 0 || oWins && turns == 1) {
            return false;
        }
        return (turns == 0 || turns == 1) && (!xWins || !oWins);
    }

    interface HtmlParser {
     public List<String> getUrls(String url) ;
  }
}

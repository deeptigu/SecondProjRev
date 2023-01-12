package array;

import javax.swing.text.html.parser.Entity;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toMap;

public class UBer {

    String[] days = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    public String dayOfTheWeek(int d, int m, int y) {
        if (m < 3) {
            m += 12;
            y -= 1;
        }
        int c = y / 100;
        y = y % 100;
        int w = (c / 4 - 2 * c + y + y / 4 + 13 * (m + 1) / 5 + d - 1) % 7;
        return days[(w + 7) % 7];
    }

    public int maxProfit(int[] prices) {

        int min = prices[0], totalProfit = 0,profit =0;
        for(int i = 1; i < prices.length;i++){
            if(prices[i-1] < prices[i]){
                profit = Math.max(prices[i]-min ,profit);
            }else {
                    min = prices[i];
                    totalProfit += profit;
                    profit = 0;
            }
        }
        return totalProfit+profit;

    }

    public boolean canConstruct(String s, int k) {
        int len = s.length();
        if(k > len){
            return false;
        }
        if( k == len){
            return true;
        }
        Map<Character,Integer> charVasCount = new HashMap<>();
        for(char c :s.toCharArray()){
            int count = charVasCount.getOrDefault(c,0);
            count++;
            charVasCount.put(c,count);
        }
       // int oddConsumables = k;
        Map<Integer,Integer> freqVsTotalNoOfCharsHavingThatFReq = new HashMap<>();
        for(Map.Entry<Character,Integer> entry:charVasCount.entrySet()){
            int freq = entry.getValue();
            int count = freqVsTotalNoOfCharsHavingThatFReq.getOrDefault(freq,0);
            count++;
            freqVsTotalNoOfCharsHavingThatFReq.put(freq,count);
        }
        for(Map.Entry<Integer,Integer> entry:freqVsTotalNoOfCharsHavingThatFReq.entrySet()){
            int freq = entry.getKey();
            int count = entry.getValue();
            if(freq % 2 == 1 && k - count < 0){
                return false;
            }
            k -= count;
        }
        return true;
    }

    public boolean canFormArray(int[] arr, int[][] pieces) {
        int len = pieces.length;
      //  List<Integer> resIndx = new ArrayList<>();
        Map<Integer,Integer> valVsIdx = new HashMap<>();
        for(int i = 0 ; i < arr.length; i++){
            valVsIdx.put(arr[i],i);
        }

        for(int i = 0 ; i < len; i++){
           if(valVsIdx.containsKey(pieces[i][0])){
               int idx = valVsIdx.get(pieces[i][0]);
               for(int j =1 ; j < pieces[i].length; j++){
                   if(idx+1 < arr.length && pieces[i][j] == arr[idx+1] ){
                       idx++;
                   }else {
                       return false;
                   }
               }
            }else {
               return false;
           }
        }
        return true;
    }
}

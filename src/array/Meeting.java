package array;

import javafx.util.Pair;

import java.util.*;

public class Meeting {


    public static void main(String[] args){
        Meeting n = new Meeting();
        char[] chars = {'a','a','b','b','c','c','c'};
        n.compress(chars);
    }

    public int minInsertions(String s) {
        int count = 0 , ClosingBraketsNeeded = 0;
        for(int i = 0 ; i < s.length();i++){
            char curr = s.charAt(i);
            if(curr == '('){
                ClosingBraketsNeeded += 2;
            } else if (curr == ')') {
                ClosingBraketsNeeded -= 1;
                if(ClosingBraketsNeeded < 0){ // closing are more than opening B
                    count += 1; // adding one for opeining B
                    ClosingBraketsNeeded += 2 ; // Need 2 Closing B for the above opening B to balance
                }
            }
        }
        return count+ClosingBraketsNeeded;
    }

    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        if(k == 0){
            return 0;
        }
        int start = 0 , end = 0, max = -1;
        Map<Character,Integer> uniqueChars = new HashMap<>();
        for(end = start ; end < s.length(); end++){
            char curr = s.charAt(end);
            int freq = uniqueChars.getOrDefault(s.charAt(end),0)+1;
            uniqueChars.put(curr,freq);
            while(uniqueChars.size() > k){
                char startChar = s.charAt(start);
                int fe = uniqueChars.get(startChar)-1;
                if(fe == 0){
                    uniqueChars.remove(startChar);
                }else{
                    uniqueChars.put(startChar,fe);
                }
                start++;
            }
            max = Math.max(max,end-start+1);
        }
        return max;
    }
    public int compress(char[] chars) {
        int start = 0 , j = 0;
        for(int i = 0 ; i < chars.length;i++){
            start = i ;
            chars[j++] = chars[i];
            while( i+1 < chars.length && chars[i] == chars[i+1]){
                i++;
            }
            if(start != i){
                chars[j++] = (char)(i-start+1 + '0') ;
            }
        }
        chars[j] = '\0';
        return chars.length;
    }

    public int minMeetingRooms(int[][] intervals) {
        PriorityQueue<Pair<Integer,Integer>> pq = new PriorityQueue<>(new Comparator<Pair<Integer, Integer>>() {
            @Override
            public int compare(Pair<Integer, Integer> o1, Pair<Integer, Integer> o2) {
                return o1.getValue()-o2.getValue();
            }
        });
        for(int[] arr:intervals){
            pq.offer(new Pair<>(arr[0],arr[1]));
        }
        int endTime = pq.poll().getValue();
        int maxSize = 0;
        Queue<Integer> endTimes = new LinkedList<>();
        endTimes.add(endTime);
        while(!pq.isEmpty()){
            Pair<Integer,Integer> sec = pq.poll();
            endTime = endTimes.peek();
            if(endTime <= sec.getKey()){
                endTimes.poll();
            }
            endTimes.add(sec.getValue());
            maxSize = Math.max(maxSize,endTimes.size());
        }
        return maxSize;
    }
}

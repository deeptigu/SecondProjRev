package Graph;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Dfs {

    public int longestStrChain(String[] words) {
        Arrays.sort(words, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.length() - o2.length();
            }
        });
        int longestChainLength = 0;
        Map<String,Integer> wordsToSeqLength = new HashMap<>();
        for(int i = 0 ; i < words.length ; i++){
            StringBuilder curr = new StringBuilder(words[i]);
            int maxSeqLen = 1;
            for(int j = 0 ; j < curr.length();j++){
                String predecessor = curr.deleteCharAt(j).toString();
                if(wordsToSeqLength.containsKey(predecessor)){
                    int len = wordsToSeqLength.get(predecessor);
                    maxSeqLen = Math.max(maxSeqLen,len+1);
                }
                curr.insert(j,words[i].charAt(j));
            }
            wordsToSeqLength.put(curr.toString(),maxSeqLen);
            longestChainLength = Math.max(longestChainLength,maxSeqLen);
        }
        return longestChainLength;
    }
}

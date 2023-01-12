package String;

import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class Decode {

    public static void main(String[] args){
        Decode d = new Decode();
        List<Integer> pid = Arrays.asList(new Integer[]{1, 2, 3, 4, 5});
        List<Integer> ppid = Arrays.asList(new Integer[]{0,1,1,1,1});
        int[] arr = {5,0,3,8,6};
        d.restoreIpAddresses("25525511135");

    }

    public List<String> restoreIpAddresses(String s) {
        List<String> res = new ArrayList<>();
        callRec(s,0,new StringBuilder(),res);
        return res;
    }

    private void callRec(String s, int idx, StringBuilder currString, List<String> res) {

        if(idx == s.length()){
            String[] ips = currString.toString().split("\\.");
            if(ips.length == 4) {
                res.add(currString.toString());
            }
            return;
        }
        StringBuilder subIp = new StringBuilder();
        for(int end = idx ; end < Math.min(idx+3,s.length()-1) ; end++){
                subIp.append(s.charAt(end));
                int length = currString.toString().split("\\.").length;
                if((subIp.length() > 1 && subIp.charAt(0) == '0') || Integer.parseInt(subIp.toString()) > 255 ||
                        length > 4){
                    return;
                }
                currString.append(subIp.charAt(subIp.length()-1));

               if(length+1 < 4)
                   currString.append(".");
                callRec(s, end + 1, currString, res);
                currString.deleteCharAt(currString.length() - 1);
        }
    }

    public List<String> topKFrequent(String[] words, int k) {
        Map<String,Integer> wordVsCount = new HashMap<>();
        for(String word: words){
            wordVsCount.put(word, wordVsCount.getOrDefault(word,0)+1);
        }
        PriorityQueue<String> pq = new PriorityQueue<>(
                (String a , String b)-> wordVsCount.get(a).equals(wordVsCount.get(b)) ?
                        a.compareTo(b) : wordVsCount.get(b) - wordVsCount.get(a));

        for(Map.Entry<String,Integer> entry : wordVsCount.entrySet()){
            pq.offer(entry.getKey());
        }
        List<String> res = new ArrayList<>();
        for(int i = 0 ; i < k && !pq.isEmpty() ; i++){
            res.add(pq.poll());
        }
        return res;
    }

    public List<Integer> killProcess(List<Integer> pid, List<Integer> ppid, int kill) {
        List<Integer> res = new ArrayList<>();
        Map<Integer, List<Integer>> parChildren = new HashMap<>();
        for(int i = 0 ; i < pid.size();i++){
            if(!parChildren.containsKey(ppid.get(i))){
                parChildren.put(ppid.get(i),new ArrayList<>());
            }
            parChildren.get(ppid.get(i)).add(pid.get(i));
        }
        res.add(kill);
        callDFS(kill,parChildren,res);
        return res;
    }

    private void callDFS(Integer start, Map<Integer, List<Integer>>  parChildren, List<Integer> res) {
        if(!parChildren.containsKey(start)){
            return;
        }
        List<Integer> val = parChildren.get(start);
        for(int a : val){
            res.add(a);
            callDFS(a,parChildren,res);
        }
    }

    public String minRemoveToMakeValid(String s) {
        String r = remove('(',')',s);
        return remove(')','(',r);
    }

    private String remove(char opening,char closing, String s){
        int left = 0;
        StringBuilder str = new StringBuilder();
        for(int i = 0 ; i < s.length();i++){
            if(s.charAt(i) == closing){
               if(left <= 0){
                   continue;
               }else {
                   left--;
               }

            }else if(s.charAt(i) == opening){
                left++;
            }
            str.append(s.charAt(i));
        }
        return str.toString();
    }

    public String removeDuplicates(String s, int k) {
        StringBuilder str = new StringBuilder(s);
        StringBuilder str2 = new StringBuilder();
        boolean flag = true;
        while(flag) {
            flag = !flag;
            for (int i = 0; i < str.length(); i++) {
                int lastRepeatedIndx = checkRepeatedChar(str, i);
                if (lastRepeatedIndx - i + 1 >= k) {
                    flag = true;
                    i = i + k - 1;
                } else{
                    str2.append(str.substring(i,lastRepeatedIndx+1));
                    i += lastRepeatedIndx - i;
                }
            }
            str = str2;
            str2 = new StringBuilder();
        }
        return str.toString();
    }

    private int checkRepeatedChar(StringBuilder str, int i) {
        char c = str.charAt(i);
        int j = i;
        while(j < str.length() && str.charAt(j) == c){
            j++;
        }
        return j-1;
    }

    public int expressiveWords(String s, String[] words) {
        int count = 0;
        for(String word:words){
            if(sketchy(word,s)){
                count++;
            }
        }
        return count;
    }

    private boolean sketchy(String word, String s) {
        int i = 0, j = 0;
        while(j < word.length() && i < s.length()){
            if(word.charAt(j) != s.charAt(i)){
                return false;
            }
            int l1 = getLength(s,i);
            int l2 = getLength(word,j);
            if((l1 < 3 && l1 != l2) ||(l1 >= 3 && l2 > l1 )){
                return false;
            }

            i += l1;
            j += l2;
        }
        return j == word.length() && i == s.length();
    }

    private int getLength(String s, int i) {
        char ch = s.charAt(i);
        int count = 0;
        for(int j = i ; j <s.length() && s.charAt(j) == ch;j++){
            count++;
        }
        return count;
    }

    public String decodeString(String s) {
        StringBuilder tmp = null;
        StringBuilder res = null;
        Stack<String> stack = new Stack<>();
        int repeatCount = 0;
        for(int i = 0 ; i < s.length();i++){
            Character ch = s.charAt(i);
            if(Character.isDigit(ch)){
                int num = 0;
                while(i <s.length() && Character.isDigit(s.charAt(i) )){
                   num = num * 10+(s.charAt(i)-'0');
                   i++;
                }
                stack.push(String.valueOf(num));
                i--;
            }else if(ch == ']'){
                tmp = new StringBuilder();
                res = new StringBuilder();
                while(!stack.isEmpty() && !stack.peek().equals( "[")){
                    tmp.insert(0,stack.pop());
                }
                stack.pop();
                repeatCount = Integer.parseInt(stack.pop());
                String t = tmp.toString();
                while(repeatCount > 0){
                 res.append(t);
                 repeatCount--;
                }
                stack.push(res.toString());
            }else{
                stack.push(ch.toString());
            }
        }
         res = new StringBuilder() ;
         while(!stack.isEmpty()){
             String str = stack.pop();
            res.insert(0,str);
         }
         return res.toString();
    }
}

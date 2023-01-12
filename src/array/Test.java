package array;

import com.sun.xml.internal.ws.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.*;

public class Test {

    /*
You are working on an authentication system and there is a set of rules the users have to follow when picking a new password:

1. It has to be at least 16 characters long.
2. The password cannot contain the word "password". This rule is not case-sensitive.
3. The same character cannot be used more than 4 times. This rule is case-sensitive, "a" and "A" are different characters.
4. The password has to contain at least one uppercase and one lowercase letter.
5. The password has to contain at least one of the following special characters "*","#","@".

Write a function that takes in a password and returns a collection of any rule numbers that are not met.

password1 = "Strongpwd9999#abc"             ==> []
password2 = "Less10#"                       ==> [1]
password3 = "Password@"                     ==> [1,2]
password4 = "#PassWord011111112222223x"     ==> [2,3]
password5 = "password#1111111"              ==> [2,3,4]
password6 = "aaaapassword$$"                ==> [1,2,3,4,5]
password7 = "LESS10#"                       ==> [1,4]


All test cases:

validate(password1) ==> []
validate(password2) ==> [1]
validate(password3) ==> [1,2]
validate(password4) ==> [2,3]
validate(password5) ==> [2,3,4]
validate(password6) ==> [1,2,3,4,5]
validate(password7) ==> [1,4]


Complexity variables:

N = length of the password
1. It has to be at least 16 characters long.
2. The password cannot contain the word "password". This rule is not case-sensitive.
3. The same character cannot be used more than 4 times. This rule is case-sensitive, "a" and "A" are different characters.
4. The password has to contain at least one uppercase and one lowercase letter.
5. The password has to contain at least one of the following special characters "*","#","@".

*/

        public static void main(String[] argv) {
            String password1 = "Strongpd9999#abc";
            String password2 = "Less10#";
            String password3 = "Password@";
            String password4 = "#PassWord011111112222223x";
            String password5 = "password#11111";
            String password6 = "aaaapassword$";
            String password7 = "LESS10#";
            Test test = new Test();
            //test.validate(password5).forEach(a -> System.out.println(a));
            //test.maxValue(7,5,29);
            test.fractionToDecimal(1,6);
        }

    public String fractionToDecimal(int numerator, int denominator) {
        if (numerator == 0) {
            return "0";
        }
        StringBuilder fraction = new StringBuilder();
        // If either one is negative (not both)
        if (numerator < 0 ^ denominator < 0) {
            fraction.append("-");
        }
        // Convert to Long or else abs(-2147483648) overflows
        long dividend = Math.abs(Long.valueOf(numerator));
        long divisor = Math.abs(Long.valueOf(denominator));
        fraction.append(String.valueOf(dividend / divisor));
        long remainder = dividend % divisor;
        if (remainder == 0) {
            return fraction.toString();
        }
        fraction.append(".");
        Map<Long, Integer> map = new HashMap<>();
        while (remainder != 0) {
            if (map.containsKey(remainder)) {
                fraction.insert(map.get(remainder), "(");
                fraction.append(")");
                break;
            }
            map.put(remainder, fraction.length());
            remainder *= 10;
            fraction.append(String.valueOf(remainder / divisor));
            remainder %= divisor;
        }
        return fraction.toString();

    }

        public int[][] intervalIntersectionGiven(int[][] A, int[][] B) {
            List<int[]> ans = new ArrayList();
            int i = 0, j = 0;

            while (i < A.length && j < B.length) {
                // Let's check if A[i] intersects B[j].
                // lo - the startpoint of the intersection
                // hi - the endpoint of the intersection
                int lo = Math.max(A[i][0], B[j][0]);
                int hi = Math.min(A[i][1], B[j][1]);
                if (lo <= hi)
                    ans.add(new int[]{lo, hi});

                // Remove the interval with the smallest endpoint
                if (A[i][1] < B[j][1])
                    i++;
                else
                    j++;
            }

            return ans.toArray(new int[ans.size()][]);
        }


    public int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
         List<int[]> res = new ArrayList<>();
         int start =0 , end =0;
         //int len = firstList[firstList.length-1][1] > secondList[secondList.length-1][1] ? secondList.length : firstList.length;
         for(int  i1 = 0 , i2 = 0 ; i1 < firstList.length && i2 < secondList.length ;){
             if(firstList[i1][1] < secondList[i2][0]){
                 i1++;
                 continue;
             }else if(secondList[i2][1] < firstList[i1][0]){
                 i2++;
                 continue;
             }else if(firstList[i1][1] >= secondList[i2][0])
             {
                  start = Math.max(firstList[i1][0], secondList[i2][0]);
                  if(firstList[i1][1] <= secondList[i2][1]) {
                      end = firstList[i1][1];
                      i1++;
                  }else{
                      end = secondList[i2][1];
                      i2++;
                  }
             }else if(firstList[i1][0] <= secondList[i2][1])
             {
                 start = Math.max(firstList[i1][0], secondList[i2][0]);
                 if(firstList[i1][1] <= secondList[i2][1]) {
                     end = firstList[i1][1];
                     i1++;
                 }else{
                     end = secondList[i2][1];
                     i2++;
                 }
             }
             int[] interval = {start,end};
             res.add(interval);
         }
        int[][] arr = new int[res.size()][2];
         return res.toArray(arr);

    }

    public String removeDuplicateLetters(String s) {
        Map<Character,Integer> charVsIndex = new HashMap<>();
        int start = 0;
        String smallestSTring =  null;
        for(int i = 0 ; i < s.length();i++){
            char curr = s.charAt(i);
            boolean flag = false;
            if(charVsIndex.containsKey(curr) && start < charVsIndex.get(curr)){
                int oldIndx = charVsIndex.get(curr);
                start = oldIndx+1;
                flag = true;
            }
            String tmp = s.substring(start,i+1);
            charVsIndex.put(curr,i);
            smallestSTring = smallestSTring == null || !flag || tmp.compareTo(smallestSTring) < 0  ? tmp : smallestSTring;
        }
        return smallestSTring;
    }
    /*Given a string num which represents an integer, return true if num is a strobogrammatic number.

    A strobogrammatic number is a number that looks the same when rotated 180 degrees (looked at upside down).
    6-> 9 , 0 ,1 ,9->6
    Example 1:

    Input: num = "69"
    Output: true */
    public boolean isStrobogrammatic(String num) {
        Set<Integer> dict = new HashSet<Integer>();
        dict.add(0);
        dict.add(6);
        dict.add(9);
        dict.add(8);
        dict.add(1);
        StringBuilder newNum = new StringBuilder();
        for(int i = num.length()-1; i >=0  ;i--){
            int n = Integer.parseInt(num.substring(i,i+1));
            if(!dict.contains(n)){
                return false;
            }else if(n == 6){
                newNum.append(Integer.toString(9));
            }else if(n == 9){
                newNum.append(Integer.toString(6));
            }else {
                newNum.append(Integer.toString(n));
            }
        }
        return newNum.toString().equals(num);
    }

    public void wiggleSort(int[] nums) {
        boolean greater = false;
        for(int i = 1 ; i < nums.length ; i++){
            if(greater){
                if(nums[i] < nums[i-1]){
                    swap(nums,i,i-1);
                }
            }else {
                if(nums[i-1] > nums[i]){
                    swap(nums,i,i-1);
                }
            }
            greater = !greater;
        }
    }

    private void swap(int[] nums, int i, int i1) {
            int tmp = nums[i1];
            nums[i1] = nums[i];
            nums[i] = tmp;
    }


    public int maxValue(int n, int index, int maxSum) {
        int incr = 1, maxValue =1;
        maxSum  -= n;
        while(maxSum  >= incr){
            maxSum -= incr;
            maxValue++;
            incr = Math.min(n,index == 0 || index == n-1 || index+(incr-1)/2 >= n-1 || index-(incr-1)/2 <= 0 ? incr+1 : incr+2);
        }
        return maxValue;
    }

        int countUpperCase = 0 , lowerCaseCount =0, specialCharCount =0, replicationCount = 0;

        private List<Integer> validate(String password){
            List<Integer> result = new ArrayList<Integer>();

            if(password.matches(".*[p|P][a|A].*")){
                result.add(2);
            }
            int i =0;
            for(i = 0 ; i < password.length(); i++){
                if(i+1 < password.length() && password.charAt(i+1) == password.charAt(i) & !result.contains(3)) {
                    char curChar = password.charAt(i);
                    while(i+1 < password.length() && curChar == password.charAt(i+1)) {
                        replicationCount++;
                        i++;
                    }
                    if (replicationCount >= 4) {
                        result.add(3);
                    }
                    replicationCount = 0;
                    check(curChar);
                }else {
                    check(password.charAt(i));
                }
            }
            if(i < 16){
                result.add(1);
            }

            if(countUpperCase < 1 || lowerCaseCount < 1){
                result.add(4);
            }
            if(specialCharCount < 1){
                result.add(5);
            }

            return result;
        }



        void check(Character ch){
            if(ch >= 'a' && ch <= 'z'){
                lowerCaseCount++;
            }
            if(ch >= 'A' && ch <='Z'){
                countUpperCase++;
            }
            if(ch == '#'|| ch== '*' || ch =='@'){
                specialCharCount++;
            }
        }





}


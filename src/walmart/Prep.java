package walmart;

import java.util.*;

public class Prep {

    List<String> finalRes ;
    List<String> findItinerary(List<List<String>> tickets) {
        Map<String, DestinationObject> itinery = new HashMap<>();
        for(List<String> ticket : tickets){
            DestinationObject destinationObject = itinery.getOrDefault(ticket.get(0),new DestinationObject());
            destinationObject.destinations.add(ticket.get(1));
            itinery.put(ticket.get(0),destinationObject);
        }
        List<String> res = new ArrayList<>();
        res.add("JFK");
        callaRec("JFK",itinery,res);
        return finalRes;
    }

    private void callaRec(String startNode, Map<String, DestinationObject> itinery, List<String> res) {
        if(!itinery.containsKey(startNode)){
            if(finalRes == null || finalRes.size() < res.size()){
                finalRes = new ArrayList<>(res);
            }
            return;
        }

        PriorityQueue<String> priorityQueue = itinery.get(startNode).destinations;
        while(!priorityQueue.isEmpty()) {
            String nextNode = priorityQueue.poll();
            if (priorityQueue.size() == 0) {
                itinery.remove(startNode);
            }
            res.add(nextNode);
            callaRec(nextNode, itinery, res);
            res.remove(nextNode);
        }

    }

    class DestinationObject{
        PriorityQueue<String> destinations = new PriorityQueue<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
    }

    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
       int  i = 0 , j =0;
       List<List<Integer>> res = new ArrayList<>();
       if(k > Math.min(nums1.length, nums2.length)) return res;
       res.add(Arrays.asList(nums1[i],nums2[j])); k--;
       while(k > 0  && i < nums1.length && j < nums2.length){
            if( i+1 < nums1.length && ((j+1 < nums2.length && nums1[i+1]+nums2[j] < nums1[i]+nums2[j+1]) || (j+1 >= nums2.length))) {
                res.add(Arrays.asList( nums1[++i],nums2[j]));
            }else  if( j+1 < nums2.length && ((i+1 < nums1.length && nums1[i+1]+nums2[j] >= nums1[i]+nums2[j+1])||(i+1 >= nums1.length) )){
                res.add(Arrays.asList( nums1[i],nums2[++j]));
            }
            k--;
       }
       return res;
    }

    public List<String> subdomainVisits(String[] cpdomains) {
        Map<String,Integer> map = new HashMap<>();
        for(String domain : cpdomains){
            String[] freqVsDomain = domain.split("\\s+");
            int count = Integer.valueOf(freqVsDomain[0]);
            String[] subDomains = freqVsDomain[1].split("\\.");
            String curr = "";
            for(int i = subDomains.length-1 ; i >= 0 ;i--){
                curr = subDomains[i].concat(curr.length() > 0 ? ".":"").concat(curr);
                map.put(curr,map.getOrDefault(curr,0)+count);
            }
        }
        List<String> res = new ArrayList<>();
        for(Map.Entry<String,Integer> entry : map.entrySet()){
            String s = entry.getValue() + " " +entry.getKey();
            res.add(s);
        }
        return res;
    }

    public int minSessions(int[] tasks, int sessionTime) {
        Arrays.sort(tasks);
        List<Integer> groupsSum = new ArrayList<>();
        groupsSum.add(sessionTime-tasks[tasks.length-1]);
        for(int i = tasks.length-2; i >= 0 ; i--){
            int val = tasks[i];
            boolean ischanged = false;
            for(int j =0 ; j < groupsSum.size();j++){
                if(groupsSum.get(j)-val >= 0){
                    groupsSum.set(j,groupsSum.get(j)-val);
                    ischanged = true;
                    break;
                }
            }
            if(!ischanged){
                groupsSum.add(sessionTime-val);
            }
        }
        return groupsSum.size();
    }
}

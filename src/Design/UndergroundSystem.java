package Design;

import javafx.util.Pair;

import java.util.*;


class RandomizedSet {
    HashMap<Integer,Integer> valVsIndex;
    List<Integer> list;
    public RandomizedSet() {
        valVsIndex = new HashMap<>();
        list = new ArrayList<>();
    }

    public boolean insert(int val) {
        if(valVsIndex.containsKey(val)){
            return false;
        }
        list.add(val);
        valVsIndex.put(val,list.size()-1);
        return true;
    }

    public boolean remove(int val) {
        if(!valVsIndex.containsKey(val)){
            return false;
        }
        int index = valVsIndex.get(val);
        valVsIndex.remove(val);
        if(list.size() > 1 && index < list.size()-1){
            int lastElement = list.get(list.size()-1);
            valVsIndex.put(lastElement,index);
            list.set(index,lastElement);
        }
        list.remove(list.size()-1);
        return true;
    }

    public int getRandom() {
        Random random = new Random();
        int randIndx = random.nextInt(list.size());
        return list.get(randIndx);
    }
}
public class UndergroundSystem {

    Map<Integer, Pair<String,Integer>> checkInData;
    Map<String,Pair<Double,Double>> journeyData ;

    public UndergroundSystem() {
        checkInData = new HashMap<>(); // id,Pair<STation,time>
        journeyData = new HashMap<>(); //StationA--STationB,Pair<TotalTime,TotalNumberOfSTations>>
    }

    public void checkIn(int id, String stationName, int t) {
        checkInData.put(id,new Pair<>(stationName,t));
    }

    public void checkOut(int id, String stationName, int t) {
        String startStationName = checkInData.get(id).getKey();
        Integer startTime = checkInData.get(id).getValue();
        String station = getStation(startStationName,stationName);
        Pair<Double,Double> timeAndTotalSTations;
        if(!journeyData.containsKey(station)){
            timeAndTotalSTations = new Pair<>(0.0,0.0);
           journeyData.put(station,timeAndTotalSTations);
        }
        timeAndTotalSTations = journeyData.get(station);
        Double totalTime = timeAndTotalSTations.getKey();
        Double totalNoOfStations = timeAndTotalSTations.getValue();
        totalTime += t-startTime;
        totalNoOfStations++;
        journeyData.put(station,new Pair<>(totalTime,totalNoOfStations));
        checkInData.remove(id);
    }

    private String getStation(String startStationName, String endStationName) {
        return startStationName+"->"+ endStationName;
    }

    public double getAverageTime(String startStation, String endStation) {
        Pair<Double,Double> timeAndTotalSTations = journeyData.get(getStation(startStation,endStation));
        if(timeAndTotalSTations == null){
            return 0;
        }
        return timeAndTotalSTations.getKey()/timeAndTotalSTations.getValue();
    }
}


class BrowserHistory {
    List<String> history ;
    int currIndex = -1;
    public BrowserHistory(String homepage) {
        history = new LinkedList<>();
        history.add(++currIndex,homepage);
    }

    public void visit(String url) {

        if(history.size() -1 >= currIndex+1) {
            history.removeAll(history.subList(currIndex + 1, history.size()));
        }
        history.add(++currIndex, url);
    }

    public String back(int steps) {
        if(currIndex-steps >=0){
            currIndex = currIndex-steps;
        }else{
            currIndex = 0;
        }
        return history.get(currIndex);
    }

    public String forward(int steps) {
        if(currIndex+steps <= history.size()-1){
            currIndex += steps;
        }else {
            currIndex = history.size()-1;
        }
        return history.get(currIndex);
    }
}


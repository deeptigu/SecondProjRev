package Design;


import java.util.*;
import java.util.stream.Collectors;

public class LeaderBoard {

    public List<String> invalidTransactions(String[] transactions) {
        Map<String,TxnDetail> mapping = new HashMap<>();
        Set<Integer> res = new HashSet<>();
        for(int i =0 ;i<transactions.length;i++){
            TxnDetail data = getObject(transactions[i]);
            data.idx = i;
            if(data.amount > 1000){
                res.add(i);
                continue;
            }
            if(mapping.containsKey(data.name)){
                TxnDetail lastTxn = mapping.get(data.name);
                if(!lastTxn.cityName.equals(data.cityName) || Math.abs(data.time-lastTxn.time) <= 60){
                    res.add(lastTxn.idx);
                    res.add(i);
                }
                if(data.time > lastTxn.time){
                    mapping.get(lastTxn.name).time = data.time;
                    mapping.get(lastTxn.name).cityName = data.cityName;
                }
            }else {
                mapping.put(data.name, data);
            }
        }
        return res.stream().map(i->transactions[i]).collect(Collectors.toList());
    }

    private TxnDetail getObject(String transaction) {
        //{name},{time},{amount},{city}
        String[] val = transaction.replace("{","").replace("}","").split(",");
        TxnDetail txnDetail = new TxnDetail();
        txnDetail.name = val[0];
        txnDetail.time = Double.parseDouble(val[1]);
        txnDetail.amount = Double.parseDouble(val[2]);
        txnDetail.cityName = val[3];
        return txnDetail;
    }

    Map<Integer,Integer> IdVsScore;
    TreeMap<Integer,Integer> scroreVsCount;

    public LeaderBoard() {
        IdVsScore = new HashMap<>();
        scroreVsCount = new TreeMap(Collections.reverseOrder());
    }

    public void addScore(int playerId, int score) {
        int oldScore = IdVsScore.getOrDefault(playerId,0);
        int countPlayer = oldScore ==0 ? 0 : scroreVsCount.get(oldScore);
        if(countPlayer > 1){
            scroreVsCount.put(oldScore,countPlayer-1);
        }else if(countPlayer == 1){
            scroreVsCount.remove(oldScore);
        }
        score += oldScore;
        IdVsScore.put(playerId,score);
        scroreVsCount.put(score,scroreVsCount.getOrDefault(score,0)+1);
    }

    public int top(int K) {
        int total =0 ;
        for(Map.Entry<Integer,Integer> entry : scroreVsCount.entrySet()){
            int times = entry.getValue();
            while(times >= 1 && K >0){
                total += entry.getKey();
                times--;
                K--;
            }
            if( K == 0){
                break;
            }
        }
        return total;
    }

    public void reset(int playerId) {
        int score = IdVsScore.get(playerId);
        IdVsScore.remove(playerId);
        int count = scroreVsCount.get(score);
        if(count == 1){
            scroreVsCount.remove(score);
        }else{
            scroreVsCount.put(score,scroreVsCount.get(score)-1);
        }
    }
}

class TxnDetail{
    String name;
    double time;
    double amount;
    String cityName;

    int idx;
}

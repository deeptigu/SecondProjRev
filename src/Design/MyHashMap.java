package Design;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
public class MyHashMap {
    List<Bucket> bucketList;
    private int keySpace;
    public MyHashMap() {
        bucketList = new ArrayList<>();
        this.keySpace = 2069;
        for(int i = 0 ; i < keySpace;i++){
            bucketList.add(new Bucket());
        }
    }

    public void put(int key, int value) {
        int k = key%keySpace;
        bucketList.get(k).addPair(key,value);
    }

    public int get(int key) {
        int k = key%keySpace;
        return bucketList.get(k).getVal(key);
    }

    public void remove(int key) {
        int k = key%keySpace;
        bucketList.get(k).removePair(key);
    }
}

class Bucket{
    List<PairLocal<Integer,Integer>> pairList;
    Bucket(){
        pairList = new LinkedList<>();
    }
    int getVal(int key){
        boolean isPresent = false;
        for(PairLocal<Integer,Integer> pair:pairList){
            if(pair.getKey().equals(key)){
                return pair.getVal();
            }
        }

        return -1;
    }

    void addPair(int key, int value){
        boolean isPresent = false;
        for(PairLocal<Integer,Integer> pair:pairList){
            if(pair.getKey().equals(key)){
                pair.setVal(value);
                isPresent = true;
            }
        }
        if(!isPresent){
            this.pairList.add(new PairLocal<>(key,value));
        }
    }

    void removePair(int key){
        for(PairLocal<Integer,Integer> pair:pairList){
            if(pair.getKey().equals(key)){
                pairList.remove(pair);
                break;
            }
        }
    }
}

class PairLocal<K,V>{
    private K key;
    private V val;

    public PairLocal(K key, V val) {
        this.key = key;
        this.val = val;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getVal() {
        return val;
    }

    public void setVal(V val) {
        this.val = val;
    }
}



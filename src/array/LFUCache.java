package array;

import java.util.*;

public class LFUCache {

    public static void main(String[] args){
        LFUCache t = new LFUCache(3);
        t.put(2,2);
        t.put(1,1);
        System.out.println(t.get(2));
        System.out.println(t.get(1));
        System.out.println(t.get(2));
        t.put(3,3);
        t.put(4,4);
        System.out.println(t.get(3));
        System.out.println(t.get(2));
        System.out.println(t.get(1));
        System.out.println(t.get(4));
    }
    int capacity;
    Map<Integer, Node> valueVsFreq;
    Map<Integer, CustomLinkedList> freqVsValues;

    int minFreq = 0;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        valueVsFreq = new HashMap<>();
        freqVsValues = new HashMap<>();
    }

    public int get(int key) {
        if (!valueVsFreq.containsKey(key)) {
            return -1;
        }
        Node node = valueVsFreq.get(key);
        update(node);
        return node.value;
    }

    public void put(int key, int value) {
        if(capacity == 0 && valueVsFreq.size() ==0 ){
            return;
        }
        Node node = valueVsFreq.get(key);
        if(node == null){
            node = new Node(value,1);
            node.key = key;
            CustomLinkedList list = freqVsValues.getOrDefault(minFreq,new CustomLinkedList());
            if(capacity == 0){
                Node lastNode = list.removeLast();
                valueVsFreq.remove(lastNode.key);
                capacity++;
            }
            CustomLinkedList oneFreqList = freqVsValues.getOrDefault(1,new CustomLinkedList());
            oneFreqList.addAtFirst(node);
            freqVsValues.put(node.freq, oneFreqList);
            capacity--;
            minFreq = 1;
        }else{
            update(node);
            node.value = value;
        }
        valueVsFreq.put(key,node);
    }

    void update(Node node){
        CustomLinkedList numValues = freqVsValues.get(node.freq);
        numValues.removeMiddle(node);
        if(numValues.size == 0){
            freqVsValues.remove(node.freq);
            if(minFreq == node.freq){
                minFreq++;
            }
        }
        node.freq++;
        if(!freqVsValues.containsKey(node.freq)){
            freqVsValues.put(node.freq, new CustomLinkedList());
        }
        freqVsValues.get(node.freq).addAtFirst(node);
    }
}
    class Node {
        int freq;
        int value;

        int key;
        Node(int val,int freq){
            this.value = val;
            this.freq = freq;
        }

        Node next;
        Node prev;
    }
    class CustomLinkedList{
        Node head;
        Node tail;
        int size = 0;

        public CustomLinkedList() {
            head = new Node(0,0);
            tail = new Node(0,0);
            head.next = tail;
            tail.next = head;
        }

        void addAtFirst(Node node){
            Node first = head.next;
            node.next = first;
            first.prev = node;
            head.next = node;
            node.prev = head;
            size++;
        }
        void removeMiddle(Node node){
            Node prev = node.prev ;
            Node next = node.next;
            prev.next = next;
            next.prev = prev;
            size--;
        }

        Node removeLast(){
            Node lastNode = tail.prev;
            if(size > 0){
                removeMiddle(lastNode);
            }
            return lastNode;
        }

    }






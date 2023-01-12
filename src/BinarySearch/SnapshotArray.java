package BinarySearch;

import java.util.HashMap;
import java.util.TreeMap;

public class SnapshotArray {

        HashMap<Integer, Integer>[] A;
        int snap_id = 0;
        public SnapshotArray(int length) {
            A = new HashMap[length];
            for (int i = 0; i < length; i++) {
                A[i] = new HashMap<>();
                A[i].put(0, 0);
            }
        }

        public void set(int index, int val) {
            A[index].put(snap_id, val);
        }

        public int snap() {
            return snap_id++;
        }

        public int get(int index, int snap_id) {
            return A[index].get(snap_id);
        }
}


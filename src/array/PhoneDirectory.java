package array;

import java.util.HashSet;
import java.util.Set;
/*
Design a phone directory that initially has maxNumbers empty slots that can store numbers.
The directory should store numbers, check if a certain slot is empty or not, and empty a given slot.

Implement the PhoneDirectory class:

PhoneDirectory(int maxNumbers) Initializes the phone directory with the number of available slots maxNumbers.
int get() Provides a number that is not assigned to anyone. Returns -1 if no number is available.
bool check(int number) Returns true if the slot number is available and false otherwise.
void release(int number) Recycles or releases the slot number.


Example 1:

Input
["PhoneDirectory", "get", "get", "check", "get", "check", "release", "check"]
[[3], [], [], [2], [], [2], [2], [2]]
Output
[null, 0, 1, true, 2, false, null, true]

 */
public class PhoneDirectory {

    int[] next;
    int pos;
    public PhoneDirectory(int maxNumbers) {
        next = new int[maxNumbers];
        for (int i=0; i<maxNumbers; ++i){
            next[i] = (i+1)%maxNumbers;
        }
        pos=0;
    }

    /** Provide a number which is not assigned to anyone.
     @return - Return an available number. Return -1 if none is available. */
    public int get() {
        if (next[pos]==-1) return -1;
        int ret = pos;
        pos = next[pos];
        next[ret]=-1;
        return ret;
    }

    /** Check if a number is available or not. */
    public boolean check(int number) {
        return next[number]!=-1;
    }

    /** Recycle or release a number. */
    public void release(int number) {
        if (next[number]!=-1) return;
        next[number] = pos;
        pos = number;
    }
}

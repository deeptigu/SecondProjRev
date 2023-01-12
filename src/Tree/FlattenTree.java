package Tree;

import java.util.LinkedList;
import java.util.Queue;

public class FlattenTree {

    public Node flatten(Node head) {
        if(head == null){
            return null;
        }
        Node curr = head;
        Node lastNode = flatten(curr.child);
        Node next = curr.next;
        if(lastNode != null){
             next = curr.next;
             curr.next = curr.child;
             curr.child.prev = curr;
             while(lastNode.next != null){
                 lastNode = lastNode.next;
             }
             next.prev = lastNode;
             lastNode.next = next;
        }
        curr.child = null;
        flatten(next);
        return head;
    }
}
class Node {
    public int val;
    public Node prev;
    public Node next;
    public Node child;
};
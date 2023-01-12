package Tree;

import BinarySearch.Tree;

public class BSTIterator {

           TreeNode curr ;
           TreeNode head;

            public BSTIterator(TreeNode root) {
                inorderSucc(root);
                curr = head;
            }
            public int next() {
                int res = curr.val;
                curr = curr.right;
                return res;
            }

            public boolean hasNext() {
                return curr != null ;
            }

            TreeNode end = null;
            void inorderSucc(TreeNode root){
                if (root == null) {
                    return;
                }
                inorderSucc(root.left);
                if(head == null){
                    head = root;
                    end = root;
                }else{
                    end.right = root;
                    end = end.right;
                }
                inorderSucc(root.right);
            }
}


class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

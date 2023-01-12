package BinarySearch;

public class Tree {

    public static void main(String[] args) {

        TreeNode root1 = new TreeNode(1);
        root1.left = new TreeNode(2);
        root1.right = new TreeNode(3);
        root1.left.left = new TreeNode(4);
        root1.left.right = new TreeNode(5);
        root1.right.left = new TreeNode(6);
        //root1.right.right = new TreeNode(6);
        //  root1.left.right.left = new TreeNode(3);
//        root1.left.right.right = new TreeNode(5);
        Tree tree = new Tree();
        tree.countNodes(root1);
    }


        public int countNodes2(TreeNode root) {
        if(root == null){
            return 0;
        }
        int count = 1;
        count += countNodes2(root.left);
        count += countNodes2(root.right);
        return count;
    }


        // Return tree depth in O(d) time.
        public int computeDepth(TreeNode node) {
            int d = 0;
            while (node.left != null) {
                node = node.left;
                ++d;
            }
            return d;
        }

        // Last level nodes are enumerated from 0 to 2**d - 1 (left -> right).
        // Return True if last level node idx exists.
        // Binary search with O(d) complexity.
        public boolean exists(int idx, int d, TreeNode node) {
            int left = 0, right = (int)Math.pow(2, d) - 1;
            int pivot;
            for(int i = 0; i < d; ++i) {
                pivot = left + (right - left) / 2;
                if (idx <= pivot) {
                    node = node.left;
                    right = pivot;
                }
                else {
                    node = node.right;
                    left = pivot + 1;
                }
            }
            return node != null;
        }

        public int countNodes(TreeNode root) {
            // if the tree is empty
            if (root == null) return 0;

            int d = computeDepth(root);
            // if the tree contains 1 node
            if (d == 0) return 1;

            // Last level nodes are enumerated from 0 to 2**d - 1 (left -> right).
            // Perform binary search to check how many nodes exist.
            int left = 1, right = (int)Math.pow(2, d) - 1;
            int pivot;
            while (left <= right) {
                pivot = left + (right - left) / 2;
                if (exists(pivot, d, root)) left = pivot + 1;
                else right = pivot - 1;
            }

            // The tree contains 2**d - 1 nodes on the first (d - 1) levels
            // and left nodes on the last level.
            return (int)Math.pow(2, d) - 1 + left;
        }

}



 class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}


package Tree;

public class ParentNode {


    public Node inorderSuccessor(Node node) {
        if(node.right != null){
            Node start = node.right;
            while(start != null && start.left != null){
                start = start.left;
            }
            return start;
        }
        Node parent = node.parent;
        while(parent != null && parent.val < node.val){
            parent = parent.parent;
        }
        return parent;
    }


    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node parent;
    };
}





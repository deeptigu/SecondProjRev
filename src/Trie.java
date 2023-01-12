import java.util.ArrayList;
import java.util.List;
class Solution {
    Trie trie = new Trie();
    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        for(String product : products){
            trie.insert(product);
        }
        List<List<String>> lists = new ArrayList<>();
        for(int i = 1; i <= searchWord.length() ;i++){
            lists.add(trie.get3StringsWithGivenPrefix(searchWord.substring(0,i)));
        }
        return lists;
    }

}
public class Trie {
    Node root;
    Trie(){
        this.root = new Node();
    }

    void insert(String str){
        Node curr = root;
        for(char c : str.toCharArray()){
            if(curr != null && curr.children[c-'a'] == null){
                curr.children[c-'a'] = new Node();
            }
            curr = curr.children[c-'a'];
        }
        curr.isWord = true;
    }

    Node getLower(String prefix){
        Node curr= root,prev = root;
        for(char c : prefix.toCharArray()) {
            if (curr == null || curr.children[c - 'a'] == null) {
                return null;
            }
            prev = curr;
            curr = curr.children[c-'a'];
        }
        return prev;
    }

    List<String> get3StringsWithGivenPrefix(String prefix){
        List<String> res = new ArrayList<>();
        Node root = getLower(prefix);
        if(root == null){
            return res;
        }
        StringBuilder stringBuilder = new StringBuilder(prefix);
        callDfs(root,res,stringBuilder);
        return res;
    }

    private void callDfs(Node root, List<String> res, StringBuilder stringBuilder) {
        if(res.size() == 3){
            return;
        }
        //stringBuilder = stringBuilder.append()
        if(root.isWord){
            res.add(stringBuilder.toString());
        }

        for(char i = 'a'; i<='z';i++){
            if(root.children[i-'a'] != null) {
                stringBuilder = stringBuilder.append(i);
                callDfs(root.children[i-'a'],res,stringBuilder);
                stringBuilder = stringBuilder.deleteCharAt(stringBuilder.length()-1);
            }
        }

    }
}

class Node{
    char ch;
    Node[] children = new Node[26];
    boolean isWord;
}

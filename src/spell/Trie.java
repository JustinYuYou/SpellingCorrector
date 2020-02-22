package spell;


public class Trie implements ITrie{

    private Node root = new Node();
    private int word_count = 0;
    //we got a root node and that is why node_count is 1
    private int node_count = 1;
    private int hash_code = 0;

    private class Node implements INode {
        Node children[] = new Node[26];
        private int frequency_of_word = 0;

        @Override
        public int getValue() {
            return frequency_of_word;
        }
    }

    @Override
    public void add(String word) {
        hash_code += word.hashCode();
        //We need a pointer to crawl down
        Node pointer_node = root;

        for(int i = 0; i < word.length(); i++){
            int index = word.charAt(i) - 'a';
            //if the node doesn't exist on that spot, we create one; otherwise, we move down the pointer node
            if(pointer_node.children[index] == null){
                pointer_node.children[index] = new Node();
                node_count++;
                pointer_node = pointer_node.children[index];
            } else {
                pointer_node = pointer_node.children[index];
            }
        }
        //If it is not a repeated word
        if(pointer_node.getValue() == 0){
            word_count++;
        }
        //We add one for the last node in that word
        pointer_node.frequency_of_word++;

    }

    @Override
    public INode find(String word) {

        Node pointer_node = root;

        for(int i = 0; i < word.length(); i++) {
            int index = word.charAt(i) - 'a';
            //If the
            if (pointer_node.children[index] == null) {
                return null;
            }
            pointer_node = pointer_node.children[index];
        }
        //If the word is not stored in the dictionary
        if(pointer_node.getValue() == 0){
            return null;
        }

        return pointer_node;
    }

    @Override
    public int getWordCount() {
        return word_count;
    }

    @Override
    public int getNodeCount() {
        return node_count;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        StringBuilder current_word = new StringBuilder();
        build_string(root, output, current_word);

        return output.toString();
    }
//    private void build(Node node, StringBuilder output, StringBuilder curword){
//        if(node == null){
//            return;
//        }
//        if(node.getValue() > 0){
//            output.append(curword + );
//
//        }
//        for(int i = 0; i < 26; i++){
//            Node child = node.children[i];
//            if(child != null){
//                char letter;
//                curword.append(letter);
//                build(child, output, curword);
//                curword.deleteCharAt(curword.length()-1);
//            }
//        }
//    }
void build(Node node, StringBuilder output, StringBuilder curowrd){
    if(node == null){
        return;
    }
    if(node.getValue() > 0){
        output.append(curowrd.toString() + '\n');
    }
    for(int i =0; i < 26; i++){
        Node child = node.children[i];
        if(child != null){
            curowrd.append("a"+i);
            build(child, output, curowrd);
            curowrd.deleteCharAt(curowrd.length()-1);
        }
    }
}
    //Build the string by recursion
//    private void build_string(Node node, StringBuilder output, StringBuilder current_word){
//
//        //End condition
//        if (node == null) {
//            return;
//        }
//
//        //We have a word now
//        if(node.getValue() > 0) {
//            output.append(current_word.toString() + "\n");
//        }
//        Node [] children = node.children;
//
//        for (int i = 0; i < 26; i++) {
//            Node child = children[i];
//            if (child != null) {
//                char letter = (char) ('a' + i);
//                current_word.append(letter);
//                build_string(child, output, current_word);
//                //Delete the last word
//                current_word.deleteCharAt(current_word.length() - 1);
//            }
//        }
//    }

    @Override
    public int hashCode() {
        return hash_code;
    }

//    private int build_hashCode(Node node) {
//
//        if (node == null) {
//            return 0;
//        }
//
//        Node [] children = node.children;
//
//        for (int i = 0; i < 26; i++) {
//            Node child = children[i];
//            if(child != null) {
//                return build_hashCode(child) * 31 + i;
//            }
//        }
//        return 0;
//    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (this.getClass() != o.getClass()) {
            return false;
        }

        Trie second_trie = (Trie) o;

        if(this.word_count != second_trie.word_count) {
            return false;
        }
        if(this.node_count != second_trie.node_count) {
            return false;
        }

        return build_equals(root, second_trie.root);

    }

    private boolean build_equals(Node first_trie_node, Node second_trie_node) {

        for(int i = 0; i < 26; i++) {
            if(first_trie_node.children[i] == null && second_trie_node.children[i] != null) {
                return false;
            } else if (first_trie_node.children[i] != null && second_trie_node.children[i] == null) {
                return false;
            }

            if(first_trie_node.children[i] != null && second_trie_node.children[i] != null) {
                if(first_trie_node.getValue() != second_trie_node.getValue()) {
                    return false;
                }
                if(build_equals(first_trie_node.children[i], second_trie_node.children[i]) == false) {
                    return false;
                }
            }
        }
        return true;
    }

    void build_string(Node node, StringBuilder output, StringBuilder curword){
        if (node == null) {
            return;
        }
        if(node.getValue() > 0){
            output.append(curword.toString() + "\n");
        }
        for(int i = 0; i < 26; i++){
            Node child = node.children[i];
            if(child != null){
                char letter = (char)('a'+i);
                curword.append(letter);
                build_string(child, output, curword);
                curword.deleteCharAt(curword.length()-1);
            }
        }
    }


}

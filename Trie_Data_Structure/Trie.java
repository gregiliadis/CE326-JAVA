package ce326.hw1;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author uesr1
 */
//Class Trie implemnts a Trie with nodes class TrieNode objects.
public class Trie {
    TrieNode root; //root of Trie.
    public static final String alphabet = "abcdefghijklmnopqrstuvwxyz"; //help String.
    int numOfwords; //number of words in the Trie. 
    
    //Method that adds a word in the Trie if it doesn't already exists.
    public boolean add(String word){
        int pos; 
        boolean exists = true;
        TrieNode curr = root;
        
        for(int i = 0; i < word.length(); i++){
            pos = alphabet.indexOf(word.charAt(i));
            //Check if node has child node that reffers to the specific letter.
            if( curr.childern[pos] != null ){
                //Move to the next node.
                curr = curr.childern[pos];
            }
            else{
                //Word doesn't exists.
                exists = false;
                //Add letter.
                curr.childern[pos] = new TrieNode();
                curr = curr.childern[pos];
            }
        }
        //At this point curr is the node of the last letter.
        //So curr is terminal node even if words already exists.
        curr.isTerminal = true;
        
        if( exists ) {
            return(false);
        }
        else{
            return(true);
        }
    }
    
    //Class constructor: fills the Trie with words using the method add.   
    public Trie( String []words){
        numOfwords = 0;
        root = new TrieNode();
        for(String str: words){
           if( str != null){
               if( add(str) ){
                   numOfwords++;
               }
           }
        }
    }
    //This method returns True if Trie contains the argument word .
    //Otherwise returns False.
    boolean contains(String word){
        int pos;
        TrieNode curr = root;
        
        for(int i = 0; i < word.length(); i++){
            pos = alphabet.indexOf(word.charAt(i));
            if( curr.childern[pos] != null ){
                curr = curr.childern[pos];
            }
            else{
                return(false);
            }
        }
        return(true);
    }
    
    //Method that returns the number of words inserted in the Trie. 
    int size(){
        return(numOfwords);
    }
    
    //This method returns all the words in the trie that differ from the 
    //the argument word by one letter.
    String[] differByOne(String word){
        String []retStr;
        StringBuffer array = new StringBuffer();
        StringBuffer temp = new StringBuffer();
        int arraySize;
        
        findWordsDiff(root, -1, word, array, temp, 1);
        //Temp string contains all words with the correct differance. 
        arraySize = temp.length()/word.length(); //all words have the same length.
        //Create array.
        retStr = new String[arraySize];
        for(int i = 0 ; i < arraySize; i++){
            //Isolate word from temp str.
            retStr[i] = temp.substring( i * word.length(),
                    i * word.length() + word.length() );
        }
        
        return(retStr);
    }
   
    //This method returns all the words in the trie that differ from the 
    //the argument word by a given number of letters.
    //The number of different letters is the second argument.
    String[] differBy(String word, int max){
        String []retStr;
        StringBuffer array = new StringBuffer();
        StringBuffer temp = new StringBuffer();
        int arraySize;
        
        findWordsDiff(root, -1, word, array, temp, max);
        //Temp string contains all words with the correct differance.
        arraySize = temp.length()/word.length(); //all words have the same length.
        retStr = new String[arraySize];
        //Create array.
        for(int i = 0 ; i < arraySize; i++){
            //Isolate word from temp str.
            retStr[i] = temp.substring( i * word.length(),  
                    i * word.length() + word.length() );
        }
        
        return(retStr);
    }
    
    //This function appends on the StringBuffer temp words that differ 
    //by diff(last argument) from the argument String word. 
    void findWordsDiff(TrieNode curr,int pos, String word,
            StringBuffer array, StringBuffer temp, int diff){
        
        //With Pre-order traversal of the Trie this recursive method appends 
        //in temp str letter: 'a' + child_node_pos for each node.
        if(pos != -1){ //root not refferd in letter.
            array.append((char)('a'+pos));
        }
        //If current node is terminal and the temp str has same length 
        //with the given word check for differance.
        if( (curr.isTerminal == true) && (word.length() == array.length())){
            //If the diff is correct append the temp str in array str.
            if( checkDiff( word, array.toString(), diff) ){
                temp.append(array);
            }
        }
        //Recursive call for child nodes.
        if( array.length() < word.length() ){
            for(int i = 0; i < 26; i++){
                if(curr.childern[i] != null){
                    findWordsDiff(curr.childern[i], i, word, array, temp, diff);
                }
            }
        }
        //At the back-track of recursion delete the last character in order 
        //string temp always to contain one word.
        if( pos != -1){
            array.deleteCharAt(array.length()-1);
        }
    }
    
    //Method that checks the number of different letters between str1 and str2
    //Returns true if differance is less than or equal to argument diff. 
    boolean checkDiff(String str1, String str2, int diff){
        int counter = 0;
        
        for(int i = 0;i < str1.length(); i++){
            if( str1.charAt(i) != str2.charAt(i) ){
                counter++;
            }
        }
        if( (counter >= 0) && (counter <= diff) ){
            return(true);
        }
        else return(false);
    }
    
    //Method that returns all words with same prefix in the Trie.
    String []wordsOfPrefix(String prefix){
        TrieNode curr = root;
        String []retStr;
        StringBuffer array = new StringBuffer();
        StringBuffer temp = new StringBuffer();
        int arraySize = 0, start = 0, i;
        
        //Find node that matches the last letter of the prefix.
        for(i = 0; i < prefix.length(); i++){
            if(curr.childern[alphabet.indexOf(prefix.charAt(i))] != null){
                curr = curr.childern[alphabet.indexOf(prefix.charAt(i))];
            }
        }
        //Find all words of the subTrie with root node curr.
        findAllWords(curr, -1, array, temp);
        for(i = 0; i < temp.length(); i++){
            if( temp.charAt(i) == ' '){
                arraySize++;
            }
        }
        //Create array of words.
        retStr = new String[arraySize];
        for(i = 0, arraySize = 0; i < temp.length(); i++){
            if( temp.charAt(i) == ' '){
                retStr[arraySize] = new String();
                //Add prefix.
                retStr[arraySize] += prefix;
                //add the rest of the word.
                retStr[arraySize] += temp.substring(start, i);
                start = i + 1;
                arraySize++;
            }
        }
        
        return(retStr);
    }
    
    //This function appends on the StringBuffer temp all words of
    //Trie with root the node curr(argument 1).
    void findAllWords(TrieNode curr,int pos, StringBuffer array, 
            StringBuffer temp){
        
        //With Pre-order traversal of the Trie this recursive method appends 
        //in temp str letter: 'a' + child_node_pos for each node.
        if(pos != -1){ //root not refferd in letter.
            array.append((char)('a'+pos));
        }
        
        //If current node is terminal append a space character in order to 
        //the end of the word.
        if( curr.isTerminal == true){
            temp.append(array);
            temp.append(" ");
        }
      
        //Recursive call for child nodes.
        for(int i = 0; i < 26; i++){
            if(curr.childern[i] != null){
                findAllWords(curr.childern[i], i, array, temp);
            }
        }
        
        //At the back-track of recursion delete the last character in order 
        //string temp always to contain one word.
        if( pos != -1){
            array.deleteCharAt(array.length()-1);
        }
    }
    
    //This method returns a String with pre-order traversal of the Trie.
    @Override
    public String toString(){
        StringBuffer array = new StringBuffer();
        preOrderFind(root, -1, array);
        
        return(array.toString());
    }   
    
    //With Pre-order traversal of the Trie this recursive method appends 
    //in temp str letter: 'a' + child_node_pos for each node.
    void preOrderFind(TrieNode curr,int pos, StringBuffer array){
         if(pos != -1){ //root not reffered in letter. 
            array.append(" ");
            array.append((char)('a'+pos));
        }
        
        //If node is terminal append '!'.
        if( curr.isTerminal == true){
            array.append("!");
        }
      
        //Recursive call for childern.
        for(int i = 0; i < 26; i++){
            if(curr.childern[i] != null){
                preOrderFind(curr.childern[i], i, array);
            }
        }
    }
    
    //With Pre-order traversal of the Trie this recursive method appends 
    //in String Buffer array a code for each node.
    void preOrderToDotString(TrieNode curr,int pos, StringBuffer array){
        if(pos != -1){
            //if node is terminal.
            if( curr.isTerminal == true){
                array.append(curr.hashCode() + " [label=\"" + (char)('a'+pos) +
                        "\" ,shape=circle, color=red]\n");
            }
            else{
                array.append(curr.hashCode() + " [label=\"" + (char)('a'+pos) + 
                        "\" ,shape=circle, color=black]\n");
            }
        }
        else{
            //If node is ROOT.
            array.append(curr.hashCode() + " [label=\"ROOT\" ,shape=circle,"
                    + " color=black]\n");
         }
        //Recursive call for childern.
        for(int i = 0; i < 26; i++){
            if(curr.childern[i] != null){
                //Connection between nodes.
                array.append(curr.hashCode() + " -- " + 
                        curr.childern[i].hashCode() + "\n");
                preOrderToDotString(curr.childern[i], i, array);
            }
        }
    }
    
    //Method that returns a string suiatble for graphilization of the Trie.
    String toDotString(){
        String str = new String();
        StringBuffer strBuff = new StringBuffer();
        
        str += "graph Trie {\n";
        preOrderToDotString(root, -1, strBuff);
        str += strBuff.toString();
        str += "}";
        
        return(str);
    }
    
}

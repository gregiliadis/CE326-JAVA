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
//Class TrieNode
public class TrieNode {
    TrieNode []childern; //Childern nodes.
    boolean isTerminal;  //True if node is terminal.
    
    //Constructor.
    public TrieNode(){
        childern = new TrieNode[26];
        isTerminal = false;
    }
 
}

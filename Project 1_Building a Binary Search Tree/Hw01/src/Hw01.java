import java.io.*;
import java.util.*;
import java.util.Scanner;
import java.lang.StringBuilder;

/*=============================================================================
| Assignment: HW 01 - Building and managing a BST
|
| Author: Christian D. Rosado Arroyo
| Language: Java
|
| To Compile: javac Hw01.java
|
| To Execute: java Hw01 filename
| where filename is in the current directory and contains
| commands to insert, delete, print.
|
| Class: COP3503 - CS II Spring 2021
| Instructor: McAlpin
| Due Date: Sunday, February 28
|
+=============================================================================*/


class Node
{
//Class Variables: Attributes
    int data;
    Node left, right;
    
//Constructor
    Node(int data){
        this.data = data;
    }
}

//Binary Search Tree Class: Hw01
public class Hw01{ 
	
	//Class Variables: Attributes
    private Node root; //Same as the parent 
    

	//******MAIN******
    public static void main (String[] args) throws IOException //File not found exception 
    {
    	
        //Local Variable:
        String line;
        
        Hw01 tree = new Hw01();       
         
       //File file = new File("src/in10Km50M.txt");//FOR TESTING ON CONSOLE ONLY: PART 1: File: requires path to File
       //Scanner in = new Scanner(file);     //TESTING PART 2
        
       Scanner in = new Scanner(new File(args[0]));
        
       //Outputs File Name to Command Line
        for (int i = 0; i < args.length; i++){
        	System.out.print(args[i] + " contains:\n");
        	}
        
        //Copy File content into an ArrayList
        ArrayList<String> list = new ArrayList<>(); 
                      
        //Populates array list with text file content        
        while(in.hasNextLine()) { 
        list.add(in.nextLine());
        }
        
        //Prints command and number 
        for(int i = 0; i<list.size(); i++){	
        line = list.get(i); 
        System.out.println(line);	
        }
        
        //EXECUTE COMMANDS 
        for(int i = 0; i<list.size(); i++){ //Whiles there's content to be scanned 
         
        	
            line = list.get(i);                                         
                      
       
            //CHECK: for PrintList or Quit Command 
            if(line.equals("p")){
                tree.inorderHelper(); //If tree has content
            }
            
            else if(line.equals("q")){
            System.out.println("left children:\t       " + tree.countChildren(tree.root.left));
            System.out.println("left depth:\t       " + tree.getDepth(tree.root.left));
            System.out.println("right children:\t       " + tree.countChildren(tree.root.right));
            System.out.println("right depth:\t       " + tree.getDepth(tree.root.right));
                break;
            }
            
            //CHECK: For Commands Missing Integer Error and Queue's Up Error
            else if(line.equals("i")){              
            
            System.out.print("i command:missing numeric parameter\n"); 
            }
            
            else if(line.equals("d")){           
            
            System.out.print("d command:missing numeric parameter\n"); 
            }
            
            else if(line.equals("s")){          
          
            System.out.print("s command:missing numeric parameter\n"); 
            }
            
            //ElSE: Checks for Command and Key value and executes Methods
            else{
            	
            	//Splits Line with space delimiter into String Array
            	String[] letter = line.split(" ");
        
            	String command = letter[0];
            	int key = Integer.parseInt(letter[1]);
            	                           
                if(command.equals("i"))
                {
                    tree.insert(key);               
                }
                                              
                else if(command.equals("d"))
                {
                    tree.delete(key); 
                }
                
                else if(command.equals("s"))
                {               	
                	tree.search(tree.root, key);                                         
                }               
            }                
        }

       complexityIndicator();
            
       in.close();//Closes Scanner at End of Program
    }
    
    /* METHODS: */
    
    //******COMPLEXITY INDICATOR******
    public static void complexityIndicator() {
        System.err.println("ch117679;5.0;35.0");
    }
    
    
    //******INSERT HELPER******
    public void insert(int data){
        root = insert(root, data);
    }
    
    
    //******REAL INSERT******
    public Node insert(Node root, int data){
    		
        if(root == null) {   
        	return new Node(data);
        }
               
        if(data < root.data){
            root.left = insert(root.left, data);
        }
        
        else if(data >= root.data){ //changed to >= from > 
            root.right = insert(root.right, data);
        }
                 
        return root;
    }
    
    
    //******DELETE HELPER******
    public void delete(int data)
    {
        root = delete(root, data);
    }
    
    //******REAL DELETE******   
    public Node delete(Node root, int value){
        if(root == null)
        {
           // Keep Track of found or not found with a FlagDelete function
        	System.out.println("d "+ value + ": integer "+ value + " NOT found - NOT deleted");
            return null;
        }

        if(value < root.data){
            root.left = delete(root.left, value);
        }
        
        else if(value > root.data){
            root.right = delete(root.right, value);
        }
        
        else{
            if(root.left == null && root.right == null){
                return null;
            }
            
            else if(root.left == null){
                return root.right;
            }
            
            else if(root.right == null){
                return root.left;
            }
            
            else{
                root.data = findTreeMax(root.left);
                root.left = delete(root.left, root.data);
            }
        }

        return root;
    }
    

    //******SEARCH******
    public static Node search(Node root, int value)
    {
        // Base Case 1: root is null 
        if (root==null) {
        	System.out.println("s "+ value + ": integer "+ value +" NOT found"); // add not deleted 
            return root;
        }
        
        //Base Case 2: Data is Found at Current Root
        else if(root.data == value) {
        	System.out.println(value + ": found"); 
        	return root;
        }
     
        // Key value is greater than root's key: Search on Right Subtree
        else {
        	
        	if(root.data < value) {
           return search(root.right, value);
        }
        
        	else
        // Key value is smaller than root's key: Search on Left Subtree
        return search(root.left, value);
        
        }
    }
    
   
    //******* IN ORDER HELPER ******
    public void inorderHelper(){
  	
    	System.out.print(" ");
    	PrintInOrder(root);
    	System.out.println();       
    }
    
    //*******IN ORDER******
    public void PrintInOrder(Node root){
    	
        if(root == null) {
            return;
        }

        PrintInOrder(root.left);
        System.out.print(root.data + " ");
        PrintInOrder(root.right);

    }
    
    //******COUNT CHILDREN****** 
    public int countChildren(Node node) { 
    	//if 0 children:
        if (node == null){ 
            return 0;
        }
        //if only 1 value
        if (node.left == null && node.right == null){       
            return 1; 
        }
        
      //recursively add up each return +1 and return total children
        else
            return countChildren(node.left) + countChildren(node.right) + 1; 
    } 
    
  
  
    //******FIND MAX******
    public int findTreeMax(Node root)
    {

        while(root.right != null)
            root = root.right;

        return root.data;

    }
    
    //******GET DEPTH******
    public static int getDepth(Node root){
        if(root == null) {
        	return 0;
        }
        
        //calculate max depth 
        int leftTreeDepth;
        leftTreeDepth = getDepth(root.left);
        
        int rightTreeDepth;
        rightTreeDepth = getDepth(root.right);

        //return the max depth:
        if(leftTreeDepth > rightTreeDepth){
            return 1 + leftTreeDepth;
        }
        
        else
            return 1 + rightTreeDepth;
    }
    
          
}

/*=============================================================================
| I Christian D. Rosado Arroyo (ch117679) affirm that this program is
| entirely my own work and that I have neither developed my code together with
| any another person, nor copied any code from any other person, nor permitted
| my code to be copied or otherwise used by any other person, nor have I
| copied, modified, or otherwise used programs created by others. I acknowledge
| that any violation of the above terms will be treated as academic dishonesty.
+=============================================================================*/

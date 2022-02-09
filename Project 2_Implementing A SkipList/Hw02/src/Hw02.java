import java.lang.*;
import java.util.*;
import java.io.*;


/*=============================================================================
| Assignment: HW 02 - Implementing a Skip list
|
| Author: Christian D. Rosado Arroyo
| Language: Java
|
| To Compile: javac Hw02.java
|
| To Execute: java Hw02 filename
| where filename is in the current directory and contains
| commands to insert, delete, print.
|
| Class: COP3503 - CS II Spring 2021
| Instructor: McAlpin
| Due Date: 4/4/2021
|
+=============================================================================*/

//Class HW02
public class Hw02{
	
 //*******MAIN*******
 public static void main(String[] args) throws IOException{

	 complexityIndicator();
	 
	    //Variables:
        String [] commands = new String [700000]; //Array That Holds File Contents
		String printSeed;
		
		  int maxLevel = 1;
		  int temp; 		
	      int seed = 42;
	   
	    
	   //Read File Arguments
	   String fileName = args[0];
	   Scanner scan = new Scanner(new File(fileName));
	    
	    
	   //TESTING OUTPUT ONLY
	  // File fileName = new File("src/in-10.txt");
	  // Scanner scan = new Scanner(fileName);  
	                   
	    
	   //Populating commands array with file contents
        int x = 0;  
        while (scan.hasNext()){       	
          commands[x++] = scan.next(); 
        }
     

	   System.out.println("For the input file named " + fileName);
	    
	   
		     if (args.length >1 && args[1].equalsIgnoreCase("R")){ 
				 
				 seed = (int) (System.currentTimeMillis());
				 printSeed = "With the RNG seeded with " + seed + ",";
			 }
			 
			 else {
				 printSeed ="With the RNG unseeded,"; //continues when print method is called after the insertions 
			 }
	
				 System.out.println(printSeed);	
    
     
     List list = new List(seed);
     SkipList[] skipList = list.makeSkipList();   

    
    for (int i = 0; i < commands.length; i++){  //try a switch statement   	
    	
    	switch(commands[i]) {
    	
    	case "i": 
    		i++;
            temp = list.insert(Integer.parseInt(commands[i]), skipList);
            if (temp > maxLevel)
                maxLevel = temp;
            
            break;
            
    	case "d":
    		i++;
            list.delete(Integer.parseInt(commands[i]), skipList, maxLevel);
            break;
            
    	case "s":
    		i++;
            list.search(Integer.parseInt(commands[i]), skipList, maxLevel);
            break;
            
    	case "p":
    		list.print(skipList, maxLevel, commands.length);
    		break;
    		
    	case "q":
    		list.quit();   				
    	}
    	       	                 	
        }
         
   }
 
 
   //COMPLEXITY INDICATOR METHOD
   public static void complexityIndicator(){
       System.err.println("ch117679;5;50");
     }
}


//*******SUPPORTING CLASS & METHODS*******:
//CLASS LIST
class List{
   public static Random rand = new Random();

   public List(Integer subSeed){
       rand.setSeed(subSeed);
   }

   public SkipList[] makeSkipList(){
       SkipList[] level = new SkipList[15];
             
       int i = 0;
       while(i < 15) {
    	   
    	   level[i] = new SkipList();
           level[i].createNode(Integer.MIN_VALUE);
           level[i].createNode(Integer.MAX_VALUE);   
    	   
    	   i++;
       }                  
       return level;
   }

   
   //INSERT
   public int insert(int data, SkipList[] level){
	   
       int i = 0;
       Node currentNode = level[i].head;
       while(currentNode.next != null){
    	   
    	   if (currentNode.data == data)
               return ++i;
    	   
    	   currentNode = currentNode.next;    	      	   
       }
           
       level[i].createNode(data);

       while (coinFlip()){
           i++;
           level[i].createNode(data);
       }
       return ++i;
   }
   
   
   //DELETE
	 public void delete(int data, SkipList[] level, int maxLevel){
       Boolean deleteValue = false;
      
       int i = maxLevel - 1;
       while(i >= 0) {
    	   
    	   if (level[i].delete(data)){
               deleteValue = true;
           }  
    	   
    	   i--;
    	   
       }
                               
       if (deleteValue) {
               System.out.println(data + " deleted");
       }
             
       else
           System.out.println(data + " integer not found - delete not successful");

       return;
   }
   
   
   
   
   
   //SEARCH
   public void search(int data, SkipList[] level, int maxLevel){
       boolean findValue = false;
       
       int i = maxLevel - 1;
       while (i >= 0 && !findValue){
    	   
    	   
    	   if (level[i].search(data))
           {
               findValue = true;
           }
    	   
    	   i--;    	      	     	   
       }
       
            
       if (findValue) {
               System.out.println(data + " found");
       }
       
       else
           System.out.println(data + " NOT FOUND");

       return;
   }
   

   //PRINT 
   public void print(SkipList[] level, int maxLevel, int maxLength){
       System.out.println("the current Skip List is shown below:");

       int myCount;
       String[] printList = new String[maxLength];


       for (int i = 0; i < maxLevel; i++){
           myCount = 0;
           String checkString;

           if (i == 0){
        	          	                                         
               Node currentNode = level[i].head;            
               while(currentNode != null) {
            	   
            	   printList[myCount++] =" " + currentNode.data + "; ";
                   maxLength = myCount;
                   
                   currentNode = currentNode.next;                            	                        
               }                                                      
           }
           
           
           else{
        	   
               for (Node currentNode = level[i].head; currentNode != null; currentNode = currentNode.next){
                   myCount = 0;

                   checkString = " " + currentNode.data + "; ";

                   if (currentNode.data != Integer.MAX_VALUE && currentNode.data != Integer.MIN_VALUE){
                	   
                       for (int k = 1; k < i; k++){
                    	   
                           checkString = checkString + " " + currentNode.data + "; ";
                           
                       }

                       while (!checkString.equals(printList[myCount])){
                           myCount++;
                       }

                       printList[myCount] = printList[myCount] + " " + currentNode.data + "; ";
                   }
               }
           }

       }

       for (int j = 0; j < maxLength; j++)
           if (printList[j].equals(" " + Integer.MAX_VALUE + "; "))
               System.out.println("+++infinity");
       
           else if (printList[j].equals(" " + Integer.MIN_VALUE + "; "))
               System.out.println("---infinity");
       
           else
               System.out.println(printList[j]);

       System.out.println("---End of Skip List---");
   }
   
   //QUIT
   public void quit(){
       System.exit(0);
   }
   
   //COIN FLIP
   public Boolean coinFlip(){
	   
   if(((rand.nextInt()% 2)+2)%2 == 1) 
	   return true;
   
   else
	   return false;  
   } 
}

//CLASS NODE
class Node{
	
   public int data;
   public Node next;
	
   public Node(int data){
       this.data = data;
   }
}

//CLASS SKIPLIST that holds reference to list 
class SkipList{
   public Node head = null;
   public Node tail = null;

	 public void createNode(int data){
		 
 			Node temp = new Node(data);

 			if (head == null)
 					head = tail = temp;
 			else if (data < head.data){
 					temp.next = this.head;
 					this.head = temp;
 			}
 			
 			else if (data > tail.data){
 					this.tail.next = temp;
 					this.tail = temp;
 			}
 			
 			else{
 					Node currentNode = head;

 					while (currentNode.next.data <= data) {
 						currentNode = currentNode.next;
 					}
 					temp.next = currentNode.next;
 					currentNode.next = temp;
 			}
 	}

   public Boolean delete(int data){
       if (head == null){
           return false;
       }

       else if (head == tail && head.data == data){
           head = null;
		   tail = null;
		   
           return true;
       }

       else if (head.data == data){
           head = head.next;
           return true;
       }
       
       else{
           for (Node currentNode = head; currentNode.next != null; currentNode = currentNode.next)
               if (currentNode.next.data == data){
                   currentNode.next = currentNode.next.next;
                   return true;
               }
       }

       return false;
   }

   public boolean search(int data){
	   
       if (head == null){
           return false;
       }

       else if (head == tail && head.data == data){
           return true;
       }

       else if (head.data == data){
           return true;
       }
       
       else{
    	   
           for (Node curr = head; curr.next != null; curr = curr.next)
        	   
               if (curr.next.data == data){
                   return true;
               }
       }

       return false;
   }
}

/*=============================================================================
| I Christian D. Rosado Arryo (4750019) affirm that this program is
| entirely my own work and that I have neither developed my code together with
| any another person, nor copied any code from any other person, nor permitted
| my code to be copied or otherwise used by any other person, nor have I
| copied, modified, or otherwise used programs created by others. I acknowledge
| that any violation of the above terms will be treated as academic dishonesty.
+=============================================================================*/










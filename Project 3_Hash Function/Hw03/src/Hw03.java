import java.lang.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;

/*=============================================================================
| Assignment: HW 03 - Implementing a Hash function
|
| Author: Christian D. Rosado Arroyo
| Language: Java
|
| To Compile: javac Hw03.java
|
| To Execute: java Hw03 filename
| where filename is in the current directory and contains
| the UCFxram function.
|
| Class: COP3503 - CS II Spring 2021
| Instructor: McAlpin
| Due Date: 5/2/2021
|
+=============================================================================*/


public class Hw03{
    
	
	//*****MAIN*****
	public static void main(String[] args)throws FileNotFoundException{
		
		complexityIndicator();
			
		//TESTING OUTPUT
		//File file = new File("src/5words.txt");
	       
		File file = new File(args[0]);
		Scanner in = new Scanner(file);

		 while ( in.hasNextLine() ) {
			 String readLine = in.nextLine();
             UCFxram(readLine,readLine.length());
		 }
		 
		 System.out.println("Input file processed");
		 
		 in.close();
	}
    
	//*****UCFxram function***** 
	public static void UCFxram(String input, int len){
        
		//Local Vals
		int hashVal = 0xfa01bc96;
		int randVal1 = 0xbcde98ef;
		int randVal2 = 0x7890face;
        int roundedEnd = len & 0xfffffffc;
        
        int tempData;
        
        //Array Data
        char[] data = new char[input.length()];
        
        //Populating Array Data
        for(int i = 0; i< input.length(); i++){
            data[i] = input.charAt(i);
        }
        
		for (int i = 0; i< roundedEnd; i = i + 4) {

            tempData = (data[i] & 0xff) | ((data[i+1] & 0xff)<<8) | ((data[i+2] & 0xff) <<16) | ((data[i+3] & 0xff)<<24);
			tempData = randVal1 * tempData;
			
			//Rotating Left 12 bits
            tempData = Integer.rotateLeft(tempData,12);
            tempData= tempData*randVal2;
            
            //Rotating Left 13 Bits 
			hashVal  = hashVal ^ tempData;
			hashVal = Integer.rotateLeft(hashVal,13);
						
            hashVal = hashVal * 5 + 0x46b6456e;
        }
		
		//Reset
		tempData = 0;
		
		if ((len & 0x03) == 3) {
			tempData = (data[roundedEnd +2] & 0xff)<<16;
			len = len-1;
		}
		
		if ((len & 0x03) == 2) {
			tempData |= ((data[roundedEnd +1] & 0xff)<<8);
			len = len-1;
		}
		
		if ((len & 0x03) == 1) {
			tempData |= (data[roundedEnd] & 0xff);
			tempData = tempData * randVal1;
	
			//Rotating Left 14 Bits
			tempData = Integer.rotateLeft(tempData,14); 
			tempData = tempData * randVal2;
			hashVal = hashVal ^ tempData;
        }


        hashVal = hashVal ^ len;
        hashVal = hashVal & 0xb6acbe58;
        hashVal = hashVal ^ hashVal >>> 13;
			
        hashVal = hashVal * 0x53ea2b2c;
        hashVal = hashVal ^ hashVal >>> 16;

		System.out.format("%10x:%s\n",hashVal, input );
	}

	
	//*****Complexity Indicator Method*****
	public static void complexityIndicator(){
	    System.err.println("ch117679;1;3");
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
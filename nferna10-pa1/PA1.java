/**
 * @author Neeraj Fernandes (G01035894)
 * INFS 519
 * Fall 2016
 */

import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.BufferedWriter;
//import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
 
/**
 * Program to inspect the CS honor code.
 * This is a User interactive program where a brief description about Honor Code is displayed initially. 
 * User is prompted if he/she wishes to continue reading further. If user selects yes then a menu is displayed where user can choose to read specific part of Honor Code.
 * User is also provided with an option to write the Honor Code to the file.
 * Program continues until user selects to quit.
 * 
 */
public class PA1
{
	/**
    * Entrance to the program passing in arguments
    */
	static boolean mDetails=true;  // controls the quit condition. If mDetails is found to be false programs terminates
	static String hCode;
	static String cite;
	String content1;
	String content2;
	String content3;
	String content4;
	static String newLine=System.getProperty("line.separator");
	
	public static void main( String[] args ) throws IOException
    {
    	//Variable declaration and initialization
    	int inp1=0;
    	
    	hCode= "All CS students must adhere to the GMU Honor Code."+newLine
    			+ "In addition to this honor code, the computer science department has further honor code policies regarding programming projects, which is detailed below."+newLine
    			+ "Your instructor may state further policies for his or her class as well. "+newLine
    			+ "\n"+"\n"+"Unless otherwise stated, at the time that an assignment or project is given, all work handed in for credit is to be the result of individual effort. "+newLine
    			+ "(In some classes group work is encouraged; if so, that will be made explicit when the assignment is given.)";
    	
    	cite="The content in this project is Honor code which is taken from George Mason University, Computer Science department"+newLine
    			+ "Reference - visit website: https://cs.gmu.edu/wiki/pmwiki.php/HonorCode/CSHonorCodePolicies";
    	
    	
    	// Displays summary of honor code
    	System.out.println("\n"+"------------------------------------------------------------------------------------------------------------------------------------------------");
    	System.out.println("\n"+"CS Honor code policies:");
    	System.out.println("\n"+cite);
    	System.out.println("\n"+hCode);    
    	
    	/*
    	 * The While loop displays the honor code initially and prompts user if he/she wishes to continue further.
    	 * It keeps prompting to user until the he/she enters the valid choice
    	 * If user wishes to continue, function hcDetails is called where user can view specific part of honor code
    	 */
    	
    	while(mDetails) 
    	{	
    		System.out.println("\n"+"------------------------------------------------------------------------------------------------------------------------------------------------");
    		System.out.println("Do want to read specific contents of Honor Code ?"); 
    		System.out.println("If YES press 1 else press any Single Digit Number to Quit");
    		Scanner sc1=new Scanner(System.in);	          //creating object sc1 of Scanner class
    		
    	    /* try-catch block
    		 * Used to handle exceptions as user is only required to enter 1 to process or any single digit number to quit
    		 * Exception is handled for 
    		 * 1) If user enters alphabets
    		 * 2) If user enters special characters
    		 * 3) If user enters more than single digit number
    		 */
    		try
    		{
    			inp1=sc1.nextInt();                       //if user enters alphabet or special characters catch block is executed
    		    int length=Integer.toString(inp1).length();
    			if(length>1)                              //checks if user has entered a single digit number
    			{
    				System.out.println("Invalid choice please enter a Single Digit Number");
    				continue;
    			} 
    		}
    		catch(InputMismatchException e)
    		{
	    		System.out.println("Invalid choice (No Alphabets or special characters are allowed)");
	    		continue;	
    		}
        	
        	if(inp1==1)         // if user enters 1 hcDetails function is called where more options are available to view and output the honor code in a file.
        	{
        	   hcDetails();
        	}
        	else
        	{
        		mDetails=false;                           //Quit condition
        	}
        	sc1.close();                                  // closing scanner sc1
      	}
    	
		System.out.println("Thank You for reading the content !!! GoodBye !!!"+"\n");                    
		
    }
    	
    	
    
    /*
     * function hcDetails() contains the menu driven code.
     * 1) While loop condition (mDetails) - checks for quit condition, if mDetails is false, control is returned back to main.
     * 2) Switch case (Menu)- Used to display content to user. It also has option to write Honor Code to the file.
     */
    
     public static void hcDetails()                
     {
    	//declaration and initialization of variables and strings
    	int inp2=0,inp3=0;
    	String sOption,content1,content2,content3,content4;
    	
    	sOption="------------------------------------------------------------------------------------------------------------------------------------------------"+newLine
    			+ "Select an option from the following menu:  "+newLine
    			+ "1) View contents: You (or your group, if a group assignment) may...  "+newLine
    			+ "2) View contents: You (or your group, if a group assignment) may not seek assistance from anyone else, other than your instructor or teaching assistant...  "+newLine
    			+ "3) View contents: Unless permission to do so is granted by the instructor, you (or your group, if a group assignment) may not...  "+newLine
    			+ "4) View contents: You must...  "+newLine
    			+ "5) Print the honor code to outputfile.txt  "+newLine
    			+ "6) Quit";
    	
    	content1=newLine+"I) You (or your group, if a group assignment) may:"+newLine
    			+ "* seek assistance in learning to use the computing facilities ;"+newLine
    			+ "* seek assistance in learning to use special features of a programming language's implementation ;"+newLine
    			+ "* seek assistance in determining the syntactic correctness of a particular programming language statement or construct ;"+newLine
    			+ "* seek an explanation of a particular syntactic error ;"+newLine
    			+ "* seek explanations of compilation or run-time error messages ";
    	
    	content2=newLine+"II) You (or your group, if a group assignment) may not seek assistance from anyone else, other than your instructor or teaching assistant"+newLine
    			+ "* in designing the data structures used in your solution to a problem ;"+newLine
    			+ "* in designing the algorithm to solve a problem ;"+newLine
    			+ "* in modifying the design of an algorithm determined to be faulty ;"+newLine
    			+ "* in implementing your algorithm in a programming language ;"+newLine
    			+ "* in correcting a faulty implementation of your algorithm"+newLine
    			+ "* in determining the semantic correctness of your algorithm. ";
    	
    	content3=newLine+"III) Unless permission to do so is granted by the instructor, you (or your group, if a group assignment) may not"+newLine
    			+ "* give a copy of your work in any form to another student ;"+newLine
    			+ "* receive a copy of someone else's work in any form ;"+newLine
    			+ "* attempt to gain access to any files other than your own or those authorized by the instructor or computer center ;"+newLine
    			+ "* inspect or retain in your possession another student's work, whether it was given to you by another student, it was found after other student has discarded his/her work, or it accidently came into your possession;"+newLine
    			+ "* in any way collaborate with someone else in the design or implementation or logical revision of an algorithm ;"+newLine
    			+ "* present as your own, any algorithmic procedure which is not of your own or of the instructor's design, or which is not part of the course's required reading (if you modify any procedure which is presented in the course's texts but which is not specifically mentioned in class or covered in reading assignments, then a citation with page number must be given) ;"+newLine
    			+ "* incorporate code written by others (such as can be found on the Internet) ;";
    	
    	content4=newLine+"IV) You must:"+newLine
    			+ "* report any violations of II and III that you become aware of ;"+newLine
    			+ "* if part of a group assignment, be an equal 'partner' in your group's activities and productions, and represent accurately the level of your participation in your group's activities and productions .";
    	
    	
    	
    	while(mDetails!=false)               
    	{          
    		boolean iMenu=true;            //iMenu is used inside the inner while loop. It is used as condition which if true displays the menu again.
    		
    		System.out.println(sOption);
    		System.out.println("What do you wish to do ?");
			Scanner sc2=new Scanner(System.in);          //creating object sc2 of Scanner class
			
			
    	    /* try-catch block
    		 * Used to handle exceptions as user is only required to enter 1 to process or any single digit number to quit
    		 * Exception is handled for 
    		 * 1) If user enters alphabets
    		 * 2) If user enters special characters
    		 * 3) If user enters more than single digit number
    		 */
			try
    		{ 
    			inp2=sc2.nextInt();                      //if user enters alphabet or special characters catch block is executed
    		    int length=Integer.toString(inp2).length();
    			if(length>1)                             //checks if user has entered a single digit number
    			{
    				System.out.println("Invalid choice please enter a Single Digit Number");
    				continue;
    			} 
    			
            	switch(inp2)
            	{
            	case 1:                                  //displays content 1 if inp2 is 1
            		System.out.println(content1+"\n");
            		break;
            		
            	case 2:                                  //displays content 2 if inp2 is 2
            		System.out.println(content2+"\n");
            		break;
            		
            	case 3:                                  //displays content 3 if inp2 is 3
            		System.out.println(content3+"\n");
            		break;
            		
            	case 4:                                  //displays content 4 if inp2 is 4
            		System.out.println(content4+"\n");
            		break;
            		
            	case 5:                                  //generates output.txt file.
            		try 
            		{
            			BufferedWriter bw= new BufferedWriter(new FileWriter("outputfile.txt"));
              			bw.write(hCode+newLine);
            			bw.newLine();
            			bw.write(content1+newLine);
            			bw.newLine();
            			bw.write(content2+newLine);
            			bw.newLine();
            			bw.write(content3+newLine);
            			bw.newLine();
            			bw.write(content4);
            			System.out.println("outputfile.txt file has been generated");
            			bw.close();
            		}
            		catch(IOException e)
            		{
            		   System.out.println(e.getMessage());
            		}
            		
            		break;
            		
            	case 6:                                 
            		mDetails=false;               //mDetails is false which makes sure while condition in main and hcDetails function is false.
            		sc2.close();                  // closing scanner sc2
            		break;   	       	
            		
            	default:
            		System.out.println("Invalid choice please re-enter your choice");
            		continue;
            	}
            }
    		catch(InputMismatchException e)
    		{
	    		System.out.println("Invalid choice (No Alphabets or special characters are allowed)");
	    		continue;	
    		}
    	
    		
       /*
    	*    While loop inside While loop - prompts user to either display the menu again or quit.
        *    Once the specific part of honor code has been viewed by user he/she has the option either to quit or take the control back to main menu.
        */
    		
    		while(mDetails!=false && (iMenu==true))
    		{
    			System.out.println("\n"+"------------------------------------------------------------------------------------------------------------------------------------------------");
    			System.out.println("To go back to Main Menu press 1 or any Single Digit Number to Quit");
    			Scanner sc3=new Scanner(System.in);      //creating object sc3 of Scanner class
    			
    			
        	    /* try-catch block
        		 * Used to handle exceptions as user is only required to enter 1 to process or any single digit number to quit
        		 * Exception is handled for 
        		 * 1) If user enters alphabets
        		 * 2) If user enters special characters
        		 * 3) If user enters more than single digit number
        		 */
    		    try
    		    {
    	    		inp3=sc3.nextInt();                  //if user enters alphabet or special characters catch block is executed
    	    		int length=Integer.toString(inp3).length();
    	    	    if(length>1)                         //checks if user has entered a single digit number
    	    		{
    	    			System.out.println("Invalid choice please enter a Single Digit Number");
    	    			continue;
    	    		} 
          			iMenu=false;
                }
        		catch(InputMismatchException e)
        		{
        			iMenu=true;
            		System.out.println("Invalid choice (No Alphabets or special characters are allowed)");
            		continue;
        		}
            	if((inp3==1) && (iMenu==false))         //this condition makes sure inner while loop condition is false. So control is back to outer loop                
            	{
            		System.out.println("welcome back ...");
             	}
             	else                                   //handles quit condition
            	{                                        
            		mDetails=false;                    //mDetails is false which makes sure while condition in main and hcDetails function is false. 
            		sc3.close();                       // closing scanner sc3
            	}	
    		}
         }
    	
     }    
   }    


        	
        	
        	      	
    	
    	



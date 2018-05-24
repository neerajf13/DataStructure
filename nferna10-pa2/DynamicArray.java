
/**
 * @author Neeraj Fernandes
 * INFS 519
 * Fall 2016
 */


/** DynamicArray program - performs operations on dynamic array which are flexible in any order 
 * This program takes one input file operations.txt as command line argument and performs various operations based on the operation included in the file
 * It contains various methods which are : add, remove, insert,set, get, size, capacity
 *  arrayGrow() and arrayShrink() methods - takes care of the length of the array as per requirement
 *  size is used as an int variable which keeps track of items in array, so the length of array could be increase or decrease accordingly
 *  Exception handling is used to ensure the program doesn't exit abnormally
 */

public class DynamicArray <Type> implements List<Type>
{
	/**Entrance to the program creating and assigning values to variables
	*/
	
    public static final int MIN_CAPACITY = 4;              //constant used to make sure minimum size of array is not less than 4
    public static int size=0,i=0;                 
    Type[] aCurrent,aNew;                                  // declaring arrays aCurrent and aNew
    Type item;                                             

    public DynamicArray()                                  // constructor - used to initialize arrays when object is created
    {
        this.aCurrent=(Type[])new Object[MIN_CAPACITY]; 
        this.aNew=(Type[])new Object[MIN_CAPACITY];
    }

    /**get() method
     * returns the element based on the index
     * 1) IndexOutOfBoundsException - If index is out of range
     * 2) NullPointerException - If it tries to return the null value from array location where value is not set yet
     */
    
    public Type get(int i)
    {
    	Type temp=null;
    	try                                                // try-catch used to catch exceptions
    	{ 
    		if(i>=aCurrent.length)                         //if i is not in range of array length, it will throw IndexOutOfBoundsException
    		{
    			throw new IndexOutOfBoundsException();
    		}
    		else if(i>=size && i<aCurrent.length)           //if i is between size-1 and length-1 of array: Access denied as those array locations don't hold value
    		{
    			System.out.println("Access denied as trying to get item that was never assigned value");
    		}
    		else
    		{
    			temp=aCurrent[i];
        		if (temp==null)
        		{
        			throw new NullPointerException();
        		}
    		}    		
    	}
    	catch(IndexOutOfBoundsException e)                 //throws IndexOutOfBoundsException if index is greater than length of array, that is not in range
    	{
   	    	System.out.println("IndexOutOfBoundsException: Getting element "+item+" from array index:"+i+" which does not exists");
    	}
    	catch(NullPointerException e)                      //throws NullPointerException if item is null
    	{
    		System.out.println("NullPointerException: trying to retrieve null item");
    		return null;
    	}
        return temp;	
    }
    
    /**set() method
     * This method sets the value  of element to new value based on the index
     * Also it throws following error
     * 1) IndexOutOfBoundsException - If index is out of range
     * 2) NullPointerException - If it tries to modify the value in array location where value is not set yet, that is it is null
     */
    
    public void set(int i, Type item)
    {
    	try
    	{
    		if(item!=null)
    		{
            	this.item=item;
            	if(i<aCurrent.length)                          // avoids IndexOutOfBoundsException exception
            	{
        			if(i<size)                                 //it only assigns value to the ith index if i is less than size                 
        			{
        				aCurrent[i]=this.item;
        			}
        			else                                       //throws NullPointerException if it tries to set value to array location that don't have values yet
        			{
        				System.out.println("Access denied as trying to set element that don't have a value assigned at "+i+" array index");
        			}
            	}
            	else if(i>=aCurrent.length)                    //if i >=aCurrent.length it means i is not in range and should throw IndexOutOfBoundsException
            	{ 
            		throw new IndexOutOfBoundsException();
            	} 
    		}
    		else
    		{
    			throw new NullPointerException();
    		}
    	}
    	catch(IndexOutOfBoundsException e)
    	{
   	    	System.out.println("IndexOutOfBoundsException: setting element "+item+" to array index:"+i+" which does not exists");
   	    	return;
    	}
        catch(NullPointerException e)
    	{
      		System.out.println("NullPointerException: trying to set item to null");
    		return;
    	}
    }

    /** add() method
     * This method first calls the arrayGrow() method and doubles the length of array if necessary
     * Appends the item 
     */
    public void add(Type item)
    {
    	try
	    {
    		if(item==null)
    		{
    			throw new NullPointerException();
    		}
    		else
    		{
    		   	arrayGrow();                                       //calls arrayGrow() method to increase the capacity of array if required
    	    	aCurrent[size]=item;
    		}
	    }
        catch(NullPointerException e)
     	{
       		System.out.println("NullPointerException: item has null value");
     		return;
     	}
       	size++;
     }

    
    /** remove() method
     * This method first calls the arrayShrink() method and will decrease the length of array to half if necessary
     */
    public Type remove(int i)
    { 
      Type ans=null; 	
     
      if(i<size)                                         //it will remove item present at specified index location if its ranging between 1st item till the last item       
      {
    	if(size==0)                                        // if size is zero, array is empty and it won't remove any item from array
      	{
      		System.out.println("Array is empty");
      	}  
        if(size==1)                                        //if there is only 1 element in an array
      	{
      		ans=aCurrent[i];
      		aCurrent[i]=null;
      		size--;
      	}
      	else
      	{
          		for(int j=0;j<size;j++)                          
          		{
          			if(j==i)                                   
          			{
          				ans=aCurrent[i];
          				for(j=i+1;j<size;j++,i++)            //for loop is used to shift elements from right to left till the ith position where item is removed
          				{
          					aCurrent[i]=aCurrent[j];
          				} 
          				aCurrent[size-1]=null;	             //situation where after shifting elements from right to left, the previous position of rightmost element is set to null
          				break;
          			}
          		}
          	size--;
      		}	
      }
      else if(i>size-1 && i<aCurrent.length)                //denies access if it tries to remove item ranging from size to capacity of array, as they are not yet assigned a value
      {
    	  System.out.println("Cannot remove items that are not yet assigned");
    	  return null;
      }
      else  if(i>=aCurrent.length)                         //throws IndexOutOfBoundsException if it tries to remove item from array location which is out of range
      {
    	  System.out.println("IndexOutOfBoundsException: removing element "+item+" to array index:"+i+" which does not exists");
    	  return null;
      }
      arrayShrink();                                        // this methods shrinks array if required
	  return ans;
    }
    

    /** insert() method
     * This method inserts the item in array based on the array location
     * try-catch block is used to handle NullPointerException and IndexOutOfBoundsException
     */
    public void insert(int i, Type item)
    {
        try
    	{	
        	if(item!=null)                    
            {
        		arrayGrow();                              //calls arrayGrow() method to increase the capacity of array if required
                if(i>size && i<aCurrent.length)           //denies access if it tries to insert item to array location ranging from size to capacity, as previous location values are not assigned yet        
            	{
            		System.out.println("Access denied as trying to insert at "+i+" array index when previous array index values are not set yet");
            	}
                else if(i>=aCurrent.length)                //throws IndexOutOfBoundsExceptionif i is not in range of length of array
                {
                	throw new IndexOutOfBoundsException();
                }
                else
                {
                	if(size==0)                            //if element to be inserted is the 1st item in array, no shifting required
                	{
                    	aCurrent[i]=item;
                    	size++;	
                	}
                	else
                	{
                		for(int k=size-1,l=size;l>i;l--,k--)   //for loop is used to shift items from left to right from the ith position
                    	{
                    		aCurrent[l]=aCurrent[k];
                    	}
                       	aCurrent[i]=item;
                    	size++;
                	}         	
                }
            }
        	else if(item==null)                              //if item is null throws NullPointerException  
        	{
        		throw new NullPointerException();
        	}
    	}
    	catch(IndexOutOfBoundsException e)
    	{
   	    	System.out.println("IndexOutOfBoundsException: inserting element "+item+" to array index:"+i+" which does not exists");
   	    	return;
    	}
       catch(NullPointerException e)
    	{
      		System.out.println("NullPointerException: trying to insert null item");
    		return;
    	}
    }

    /**size() method
     * This method returns the current size of array. That is the number of elements present in the array 
     */
    public int size()                              
    {
        return size;
    }

    /** capacity() method
     *  This method returns the capacity of array. That is the maximum number of elements it can store in an array
     */
    public int capacity()
    {
    	return aCurrent.length;
    }


    // utility methods
    
    /** arrayGrow
     * This method is called while inserting or adding elements to an array
     * It checks the number of elements (size) of array and compares it with the length of array.
     * If the size of array is equal to the length of array, this method doubles the length of array before the next element could be added to it.
     */
    private void arrayGrow()
    {
    	if(size==aCurrent.length)
    	{
    		aNew=(Type[])new Object[aCurrent.length*2];          //doubles the length of array
        		
        	for(int j=0;j<aCurrent.length;j++)
        	{
        		aNew[j]=aCurrent[j];
        	}
        	aCurrent=aNew;
        }    	
     }
    
    /** arrayShrink()
     * This method is called after the element is removed from the array.
     * It compares the size of array with the length of array.
     * If the size of array is less than or equal to half the length of array, this method decreases the length of array by half. 
     * Special condition - Method makes sure length of array never falls below the minimum capacity which is 4 
     */
    private void arrayShrink()
    {
    	 if(size<=aCurrent.length/2 && aCurrent.length!=MIN_CAPACITY)   //MIN_CAPACITY condition makes sure length of array never falls below 4
         {
             aNew=(Type[])new Object[aCurrent.length/2];        //shrink the length of array by half
     		
             for(int j=0;j<aCurrent.length/2;j++)
             {
             	aNew[j]=aCurrent[j];
             }	
             aCurrent=aNew;    
         }
    }
    	

    //--------------------- DO NOT MODIFY BELOW THIS -----------------------//
    @Override
    public String toString()
    {
        StringBuilder buf = new StringBuilder();
        //buf.append("cap="+this.items.length+"[");
        buf.append("[");
        for( int i = 0; i < this.size(); i++ )
        {
            Type item = this.get(i);
            if( i != 0 ) buf.append( ", " );
            buf.append( item.toString() );
        }
        buf.append("]");
        return buf.toString();
    }


    public static void main(String[] args)
    {
        if( args.length != 1 )
        {
            String u = "Usage: java DynamicArray <filename> \n"+
                       "  e.g: java DynamicArray operations.txt";
            Stdio.println(u); 
            return;
        }

        DynamicArray<String> list = new DynamicArray<String>();
        
        Stdio.open( args[0] );
        while( Stdio.hasNext() )
        {
            String method = Stdio.readString();
            if( method.equals("add") )
            {
                String param1 = Stdio.readString();
                Stdio.println( "adding "+ param1 );
                list.add( param1 );
            }
            else if( method.equals("get") )
            {
                int index = Stdio.readInt();
                Stdio.println( "get(" + index+")="+list.get(index) );
            }
            else if( method.equals("size") )
            {
                Stdio.println( "size="+list.size() );
            }
            else if( method.equals("capacity") )
            {
                Stdio.println( "capacity="+list.capacity() );
            }
            else if( method.equals("remove") )
            {
                int index = Stdio.readInt();
                Stdio.println( "remove "+  list.remove(index) );
            }
            else if( method.equals("set") )
            {
                int index     = Stdio.readInt();
                String item   = Stdio.readString();
                list.set(index, item);
                Stdio.println( "set "+ index + " to " + list.get(index) );
            }
            else
            {
                Stdio.println( "Unknown method: "+ method );
            }
        }
        Stdio.println( "" );
        Stdio.println( "Final list=" +list.toString() );
        Stdio.close();
    }

}

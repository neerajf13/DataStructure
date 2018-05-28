
import java.util.Iterator;

/**
 * @author Neeraj Fernandes
 * INFS 519
 * Fall 2016
 */

/**
 * Heap Sort Algorithm - Min Heap
 * Functions used - insert(), delMin(), min(), swim(), sink(), swap(), greater(), isEmpty(), size(), checkGrow()
 * Algorithm inserts item(key) to the next spot at lowest level
 * Always deletes item at key 1 to find the minimum item in heap
 */
public class MinHeap implements MinPQ
{
    public static final int DEFAULT_CAPACITY = 8;                   //declare and initialize constant DEFAULT_CAPACITY
    Comparable[] keys,tempitem;
    int key=1,count=0;                                            
    
    public MinHeap( )                                               //default constructor
    {
    	keys=new Comparable[DEFAULT_CAPACITY];
    }

    public MinHeap( int initialCapacity )                           //parameterized constructor
    {
    	keys=new Comparable[initialCapacity];
    }
    
    /**
     * Inserts the key into the heap at the last.
     * @param key
     */
    public void insert( Comparable item )
    {
    	checkGrow();                                                //call checkGrow function which checks if the length of array should be increased
    	keys[key]=item;
    	swim(key);                                                  //call swim function to position the new key added in heap
    	count++;
    	key++;
    }

    
    /**
     * swim - called when the heap order is violated
     * swims new key added until its parent key is less
     * @param s
     */
    private void swim( int s )
    {	
    	 while( s > 1 && greater(s/2, s) )                           //when key value is smaller than its parent's value
    	 {            
    		  swap(s/2,s);                                           // swap parent and child
    		  s = s/2;                                               // move up, takes floor 
    	 }
    }

    
    /**
     * Finds the key from the heap with the minimum key value.
     * @return key
     * @throws java.util.NoSuchElementException if empty
     */
    public Comparable min()
    {
    	if(isEmpty())                                                //checks if array is empty
    	{
    		//throw new java.util.NoSuchElementException("No item present");
    		System.out.println("No item present");
    		return null;
    	}
    	else
    	{
    		return keys[1];
    	}
    }

    
    /**
     * Finds and removes the key from the heap with the minimum value.
     * @return key
     * @throws java.util.NoSuchElementException if empty
     */
    public Comparable delMin()
    {
    	if(isEmpty())                                                //checks if array is empty
    	{
    		//throw new java.util.NoSuchElementException("No elements found");
    		System.out.println("No item to delete");
    		return null;
    	}
    	else                                                         //delete the min key at position 1
    	{	
    		Comparable temp=keys[1];                                 //store key in temp which is to be deleted
      		keys[1]=keys[key-1];                                     //copies last key to position 1 in queue
      		keys[key-1]=null;                                        //null out the last value, prevents loitering
    		count--;
        	key--;
    		sink(1);                                                 //call sink function to position the key at location 
    		return temp;                                              
    	} 
    }

    
    /**
     * sink - called when heap order is violated
     * sinks key and moves down the heap until both children are greater
     * @param s
     */
    private void sink( int s )
    {
        while( 2*s <= this.count )                                   // equals is important to get right child
        {  
        	int j = 2*s;                                         // left child index
        	
        	if( j < this.count && greater(j, j+1) )              //j points to right child if left child is greater than right
        		j++;
        	
        	if( greater(s, j) == false )
        		break;  
        	
            swap(s,j);                                               // swap and continue to sink 
            s = j;                                                   //keep moving down
        }
    }  

    
    /**
     * greater - compares key at @param x and @param y location in heap and finds greater key
     * @return true or false
     */
    private boolean greater( int x, int y )
    {
    	return keys[x].compareTo(keys[y]) > 0;
    }

    
    /**
     * swap - swaps the two keys present at location @param x and @param y in the heap
     */
    private void swap( int x, int y )
    {
    	Comparable temp=keys[x];
    	keys[x]=keys[y];
    	keys[y]=temp;
    }
    

    /**
     * Returns number of keys in the heap.
     * @return size
     */
    public int size()
    {
        return count; 	                                           //return number of items(key) in array
    }

    
    /** isEmpty()
     *  if count is 0 it means heap is empty and will return true else vice-versa
     *  @return true or false
     */
    public boolean isEmpty()
    {
       if(count!=0)                                                //heap is not empty
       {
    	   return false;
       }
       else                                                        //heap is empty
       {
    	   return true;
       }
    }

    
    /** checkGrow()
     * This method is called while inserting key in the heap
     * It checks the number of key and compares it with the length of array.
     * If number of keys in heap is equal to the length of array, this method doubles the length of array before the next key could be added to heap.
     */
    private void checkGrow()
    {
    	if(key==keys.length)                                       //saturation condition, no more space in array
    	{
    		tempitem=new Comparable[keys.length*2];                //doubles the length of array
        		
        	for(int j=0;j<keys.length;j++)                         //copies the items from keys to tempitem array
        	{
        		tempitem[j]=keys[j];
        	}
        	keys=tempitem;
        }
    }
    
    
    /**
     * Returns Iterator that returns items in level order.  Does not support remove.
     * @return iterator
     */
    public Iterator<Comparable> iterator()
    {
        return new Iterator<Comparable>()
        {
            private int current=1;
            public boolean hasNext()                                //check if any item(key) is present in the heap
            {
                return current <=count;
            }
            public Comparable next()                                //traverse in array(heap) and return item
            {
            	if(!hasNext())
                {
                    throw new java.util.NoSuchElementException("No more elements");
                }
                return keys[current++];                              
            }
            public void remove()
            {
                throw new UnsupportedOperationException("Not supported.");
            }

        };
    }

    //--------------------- DO NOT MODIFY BELOW THIS -----------------------//

    @Override
    public String toString()
    {
        StringBuilder buf = new StringBuilder();
        boolean first = true;
        for (Comparable item : this)
        {
            if( first ) first = false;
            else buf.append(", ");
            
            buf.append( item.toString() );
        }
        return buf.toString();
    }
    
    /**
     * Unit tests the BinaryHeap data type.
     * @param args 
     */
    public static void main(String[] args)
    {
        Stdio.open( args[0] );
        MinPQ pq = new MinHeap();
        while( Stdio.hasNext() )
        {
            String method = Stdio.readString();
            if( method.equalsIgnoreCase("insert") )
            {
                int value = Stdio.readInt();
                pq.insert( value );
                Stdio.println( "insert="+pq.toString() );
            }
            else if( method.equalsIgnoreCase("delMin") )
            {
                Stdio.println( "delMin="+pq.delMin() );
            }
            else if( method.equalsIgnoreCase("size") )
            {
                Stdio.println( "size="+pq.size() );
            }
            else if( method.equalsIgnoreCase("min") )
            {
                Stdio.println( "min="+pq.min() );
            }
            else if( method.equalsIgnoreCase("empty") )
            {
                while( pq.isEmpty() == false )
                {
                    Stdio.println( "delMin="+pq.delMin() );
                }
            }
        }
        Stdio.println( "Final priority queue=" +pq.toString() );
        Stdio.close();
    }
}

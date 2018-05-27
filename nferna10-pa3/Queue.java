
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Neeraj Fernandes
 * INFS 519
 * Fall 2016
 */

/**
This program stores the data in a Queue(FIFO) using Linked List.
Elements are added from end of queue(tail) and removed from front of queue(head).
It also uses Iterator to travel from one Node to another.
It supports various methods :enqueue(), dequeue(), isEmpty(),size() and peek()
 */
public class Queue <Type> implements QueueAPI<Type>
{
   /** Node class 
	 *  It defines the basic structure of a node having two fields
	 *  1) Name - stores the name 
	 *  2) Address - to point to the next node in the queue or address is null if its the only node in the queue
	 */
	private class Node   
	{
		Type name;
		Node address;
		public Node()                       //constructor of Node class
		{
			name=null;
			address=null;
		}
	}
	Node head,tail;
	int size;
    
    public Queue()                          //constructor of Queue class
    {
    	head=null;
    	tail=null;
    	size=0;
    }
   
	Iterator <Type> it=this.iterator();     
    /**
     * Returns number of items in the queue.
     * @return size
     */
    public int size()
    {
        return size;
    }
    
    /** isEmpty()
     *  if tail is null it means queue is empty and will return true else vice-versa
     *  @return true or false
     */
    public boolean isEmpty()
    {
    	if(tail==null)
    	{
    		return true;
    	}
    	else 
    	{
            return false;
    	}
    }
    
    /**enqueue()
     * Adds an item to the end of the queue.
     * @param item 
     */
    public void enqueue(Type item)
    {
        Node n=new Node();     
        if(isEmpty())                        //queue is empty, item to be inserted is the 1st item
        {
        	n.name=item;
        	n.address=null;
        	tail=n;
        	head=n;
        }
        else                                 //condition when one or more elements are present in the queue
        {
        	n.name=item;
        	n.address=null;
        	tail.address=n;
        	tail=n;
        }
        size++;
    }
    
    /** peek()
     *  Returns item at front of queue without removing it
     *  @throws If queue is empty it will throw NoSuchElementException
     *  @return item from front
     */
    public Type peek()
    {
    	try
    	{
        	if(isEmpty())                   //checks if element is present in the queue if not then it throws exception
        	{
        		throw new NoSuchElementException();
        	}
    	}
    	catch(NoSuchElementException e)
    	{
    		System.out.println("Queue is empty: No Elements found to be displayed");
    		return null;
    	}
       return head.name;
    }
    
    /**dequeue()
     * Removes item from front of queue.
     * @return item removed from front
     * @throws java.util.NoSuchElementException if empty
     */
    public Type dequeue( )
    {
    	Type store=null;
    	try
    	{
            if(isEmpty())                  //checks if element is present in the queue by calling isEmpty() function, if element is not present then it throws exception
            {
        		throw new NoSuchElementException();
            }
    	}
    	catch(NoSuchElementException e)
    	{
    		System.out.println("Queue is empty: No element found to be deleted");
    		return null;
    	}
        if(head==tail)                     //case when only one element is present in the queue
        {
        	store=head.name;
        	head=null;
        	tail=null;
        	size--;
        }
        else                               // case when more than one element are present in the queue
        {
        	Node temp=head;
        //	store=head.name;
        //	head=head.address;
        	store=it.next();
        	temp.address=null;
        	size--;
        }
    	return store;
    }
    
    public Iterator<Type> iterator()
    {
        return new QLinkedListIterator();  
    } 
    
    
    /**
     * QLinkedListIterator is an inner class which has 3 methods:
     * hasNext(), next(), remove()
     * here remove() is not supported
     */
    private class QLinkedListIterator implements Iterator<Type>
    {
       /**
        * hasNext() - it checks if there any element is present in the queue
        */
       @Override
       public boolean hasNext()          
       {
    	   if(head!=null)
    	   {
    		   return true;
    	   }
    	   else
    	   {
    	      return false;
    	   }
   	   }

       /**
        * next() - traverses to next element and returns the element.
        */
       @Override
   	   public Type next() 
   	   {
    	   try
    	   {
        	   if(!hasNext())             //checks if element is present in the queue if not then it throws exception
        	   {
        		   throw new NoSuchElementException();
        	   }
    	   }
    	   catch(NoSuchElementException e)
    	   {
    		   System.out.println("Queue is empty: No elements are present in queue");
    		   return null;
    	   }
    	   Type item = head.name;
    	   head=head.address;
    	   return item;
    	// return null;
   	   }
       
       public void remove()               
       {
    	   throw new UnsupportedOperationException("Remove operation is not supported");  
       }
   	}
    
    
    //--------------------- DO NOT MODIFY BELOW THIS -----------------------//
    
    @Override
    public String toString()
    {
        // Uses the iterator to build String
        StringBuilder buf = new StringBuilder();
        boolean first = true;
        buf.append("[");
        for (Type item : this)
        {
            if( first ) first = false;
            else buf.append( ", " );
            buf.append( item.toString() );
        }
        buf.append("]");
        return buf.toString();
    }
    
    
    /**
     * Unit tests the Queue data type.
     * @param args 
     */
    public static void main( String[] args )
    {
        if( args.length != 1 )
        {
            String u = "Usage: java Queue <filename> \n"+
                       "  e.g: java Queue operations.txt";
            Stdio.println(u); 
            return;
        }

        Queue queue = new Queue();
        
        Stdio.open( args[0] );
        while( Stdio.hasNext() )
        {
            String operation = Stdio.readString();
            if( operation.equals("enqueue") )
            {
                String item = Stdio.readString();
                Stdio.println( "enqueue "+ item );
                queue.enqueue(item);
            }
            else if( operation.equals("dequeue") )
            {
                Stdio.println( "dequeue "+ queue.dequeue() );
            }
            else if( operation.equals("peek") )
            {
                Stdio.println( "peek "+ queue.peek() );
            }
            else if( operation.equals("size") )
            {
                Stdio.println( "size "+ queue.size() );
            }
        }
        
        Stdio.println( "Queue=" +queue.toString() );
        
        Stdio.close();
    }

	
}

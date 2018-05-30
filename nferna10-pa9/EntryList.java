
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * SymbolTable implementation that holds key/value pairs in a singly linked list.  
 * @param <Key>
 * @param <Value>
 */
 
 /**
 * @author Neeraj Fernandes
 * INFS 519
 * Fall 2016
 */
 
public class EntryList <Key, Value> implements BasicSymbolTable <Key, Value>
{
   //declaration and initialization
    private Entry head;
    private int size;
   
    private class Entry
    {
        Key key;
        Value value;
        Entry next;
     
        public Entry(Key key , Value value , Entry next)         //constructor
        {
            this.key=key;
            this.value=value;
            this.next=next;
        }
    }

    //initializes the EntryList
    public EntryList()
    {   
        this.head=null;
        this.size=0;
    }
   
    /**
     * Gets the number of elements currently in this symbol table
     * @return size
     */
    public int size()
    {
        return size;
    }

    /**
     * Determines if there are not elements in this symbol table.
     * @return true if no elements, false otherwise
     */
    public boolean isEmpty()
    {
        if(size==0)
        {
        	return true;
        }
        else
        {
        	return false;
        }
    }

    /**
     * Inserts the value into this symbol table using specified key.
     * Overwrites any previous value with specified value.
     * @param key
     * @param value
     * @throws NullPointerException if the key or value is null
     */
    public void put( Key key, Value value )
    {
       if(key==null || value==null)                    //condition for null pointer exception
       {
           throw new NullPointerException("key or value is null");
       }
       
       Entry temp=head;
       while(temp!=null)                               //iterates until no duplicates found
       {
           if (key.equals(temp.key))                   //condition for duplicate entry
           {
               temp.value = value;
               return;
           }  
           temp=temp.next;                             //point to next entry
       } 
       head= new Entry(key,value,head);                //adding the new entry
       size++;    
    }

    /**
     * Finds Value for the given Key.
     * @param key
     * @return value that key maps to or null if not found
     * @throws NullPointerException if the key is null
     */
    public Value get( Key key )
    {
    	Entry temp=head;                               
    	while(temp!=null)                                
    	{
            if (key.equals(temp.key))                  //condition to find the match for given key
            {
                return temp.value;
            }
            temp=temp.next;                            //point to next entry
    	}
        return null;
    }

    /**
     * Removes the Value for the given Key from this symbol table.
     * @param key
     * @return value that was removed or null if not found
     * @throws NullPointerException if the key is null
     */
    public Value delete( Key key )
    {
    	if(key==null)
    	{
    		throw new NullPointerException("Key cannot be null");
    	}
    	
        Entry current=head;
        Entry previous=head;
       
        while(!(key.equals(current.key)))       //traverse till key is found
        {
            if(current.next==null)              //match not found
            {
                return null;
            }
            else                                //keep traversing
            {
                previous=current;
                current=current.next;
            }
        }
        if(current==head)                       //key to be deleted is the head
        {
            Entry temp=head;
            head=head.next;                     //head points to next element
            size--;
            //System.out.println("delete"+temp.value);
            Value val=temp.value;
            temp.next=null;                     //removes the link of the element to be deleted its pointing to
            return val;
        }
        else
        {
            Entry temp=current;
            previous.next=current.next;         //key is deleted, reconnecting the list
            size--;
            return temp.value;                 
        }
    }

    /**
     * Iterable that enumerates each key in this symbol table.
     * @return iter
     */
    public Iterable<Key> keys()
    {
        Iterable<Key> iterable = new Iterable()
        {
            public Iterator<Key> iterator()
            {
                return new Iterator()
                {
                    Entry traverse=head;
                   
                    public boolean hasNext()        //checks for next key
                    {
                        if(traverse!=null)          
                        {
                            return true;
                        }
                        else
                        {
                            return false;                        	
                        }
                    }

                    public Key next()                 //go to next key
                    { 
                        if(!hasNext())
                        {
                            throw new NoSuchElementException("No Key found");
                        }
                        else
                        {
                            Entry temp=traverse;
                            traverse=traverse.next;  //points to next key
                            return temp.key;
                        }
                    }
                    public void remove()
                    {
                        throw new UnsupportedOperationException("Operation not supported");
                    }
                };
            }
        };
        return iterable;
    }

    //------------------ DO NOT MODIFY BELOW THIS LINE -------------------//
    
    @Override
    public String toString()
    {
        // Uses the iterator to build String
        StringBuilder buf = new StringBuilder();
        boolean first = true;
        buf.append("[");
        for (Key key : this.keys())
        {
            Value item = this.get(key);
            if( first ) first = false;
            else buf.append( ", " );
            
            buf.append( key );
            
            buf.append( "->" );
            buf.append( item.toString() );
        }
        buf.append("]");
        return buf.toString();
    }
    
}

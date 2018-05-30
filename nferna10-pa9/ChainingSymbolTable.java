
/**
 * BasicSymbolTable implementation using hash table with separate chaining.
 * @param <Key>
 * @param <Value>
 */
 
/**
 * @author Neeraj Fernandes
 * INFS 519
 * Fall 2016
 */
 
public class ChainingSymbolTable <Key, Value> implements BasicSymbolTable<Key, Value>
{
	//variable declaration and initializations
    public static final int REHASH_MIN_THRESHOLD = 2;
    public static final int REHASH_MAX_THRESHOLD = 8;
    public static final int INITIAL_M = 4;
   
    private EntryList<Key,Value>[] items;
    private int size;
    private int m;
    
    public ChainingSymbolTable()                     //default constructor
    {
        this(INITIAL_M);
    }
   
    public ChainingSymbolTable(int initialM)         //Parameterized constructor
    {
       
        this.m=initialM;
        this.items = (EntryList<Key,Value>[]) new EntryList[m];    //creating table to support linked list
        
        for(int  i =0 ;i < m; i++)
        {
            items[i]= new EntryList<Key,Value>();     //creating linked list for each item
        }
    }
   
    /**
     * Gets the number of elements currently in the queue
     * @return size
     */
    public int size()
    {
        return this.size;
    }

    /**
     * Determines if there are not elements in the queue.
     * @return true is no elements, false otherwise
     */
    public boolean isEmpty()
    {
        return this.size == 0;
    }

    /**
     * Inserts the value into the table using specified key.
     * Overwrites any previous value with specified value.
     * @param key
     * @param value
     * @throws NullPointerException if the key or value is null
     */
    public void put( Key key, Value value )
    {
        if (key == null || key==null)
        {
        	throw new NullPointerException("Key and Value cannot be null");
        }

        int cal = hashFunction(key);                      //calculates hash index by calling hashFunction
        if (!(get(key)!=null))
        {
        	size++;
        }
        items[cal].put(key, value);                       //putting the key value
       
        if ( size()/m >= REHASH_MAX_THRESHOLD )           //condition if size of tables needs to be doubled
        {
            rehash(m*2);                                  //double the size
        }
    }
   
   
    /**
     * Finds Value for the given Key.
     * @param key
     * @return value that key maps to or null if not found
     * @throws NullPointerException if the key is null
     */
    public Value get( Key key )
    {
        if (key == null)                                     //condition for null pointer exception
        {
        	throw new NullPointerException("key cannot be null");        	
        }	
        
        int cal = hashFunction(key);                         //calculates the hash index
        return items[cal].get(key);                          
    }

    /**
     * Removes the Value for the given Key from the table.
     * @param key
     * @return value that was removed or null if not found
     * @throws NullPointerException if the key is null
     */
    public Value delete( Key key )
    {
    	if (key == null)                         //condition for null pointer exception
    	{
        	throw new NullPointerException("key is null");
    	}
    	
    	int cal = hashFunction(key);             //calculate the hash value by calling the hash function
    	
    	if (get(key)!=null) 
    	{
    		size--;                              //decrement the size
    	}
    	Value val=items[cal].delete(key);        //delete the key
  
    	if (m > INITIAL_M && size <= 2*m)        //condition to check if size of table needs to be reduced
    	{
    		rehash(m/2);                         //reduces the size of table by half
    	}
        return val;
    }

    /**
     * Iterable that enumerates each key in the table.
     * @return iterable over the keys
     */
    public Iterable<Key> keys()
    {
        Queue<Key> queue = new Queue<Key>();       // Create Queue<Key>
        for (int i = 0; i < m; i++)                // For each EntryList<Key, Value>
        {
            for (Key key : items[i].keys())        // For each key in the entry list (using EntryList.keys())
            {
            	queue.enqueue(key);				   //add the key to the queue
            }
        }
        return queue;
    }
   
    
    /**
     * Creates a new table (and thus new hash function), inserts previous
     * items using new hash function into the new table, sets new table.
     * @param newM
     */
    private void rehash( int newM )
    {
        ChainingSymbolTable<Key, Value> temp = new ChainingSymbolTable<Key, Value>(newM);  //creating table with capacity newM
        for (int k = 0; k < m; k++)
        {
            for (Key key : items[k].keys())
            {
                temp.put(key,items[k].get(key));         //using get() and put() to copy items
            }
        }
        
        //creating and copying the keys in new table
        this.m  = temp.m;
        this.size  = temp.size;
        this.items = temp.items;
    }
   
    /**
     * Determines an index in [0,M-1] using specified key to generate hash code.
     * @param key with properly implemented hash code
     * @param M
     * @return index in [0,M-1]
     */
    private int hashFunction( Key key )
    {
        return (key.hashCode() & 0x7fffffff) % m;
    }

    //------------------ DO NOT MODIFY BELOW THIS LINE -------------------//
    
    /**
     * The expected length of the linked lists, i.e. the load factor.
     * @return N / M
     */
    public double loadFactor()
    {
        return this.getN() / (double)this.getM();
    }
    
    // Utility method
    public int getN()
    {
        return this.size;
    }
    
    // Utility method
    public int getM()
    {
        return this.items.length;
    }
    
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
            
            buf.append( "\n" );
            
            buf.append( key );
            
            buf.append( "->" );
            buf.append( item.toString() );
        }
        buf.append("]");
        return buf.toString();
    }
    
    public String toTableString( boolean verbose )
    {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < this.items.length; i++)
        {
            String prefix = "\n["+i+"] size="+this.items[i].size()+":";
            buf.append( prefix );
            if(verbose)
            {
                boolean first = true;
                for( Key key : this.items[i].keys() )
                {
                    if( first ) first = false;
                    else buf.append( ", " );

                    buf.append( "(" );
                    buf.append( key );
                    buf.append( ")" );
                }
            }
        }
        return buf.toString();
    }
    
    
    
    /**
     * Unit tests the ST data type.
     * @param args 
     */
    public static void main(String[] args)
    {
        ChainingSymbolTable<ProductID,Product> st =
                new ChainingSymbolTable<ProductID,Product>();
        
        if( Stdio.isInteger(args[0]) )
        {
            int n = Integer.parseInt( args[0] );
            java.util.Random rand = new java.util.Random();
            Clock time = new Clock();
            for( int i = 0; i < n; i++ )
            {
                String university = "GMU"+rand.nextInt();
                int identifer     = rand.nextInt();
                String name       = "["+university+","+identifer+"]";
                int age           = 0;
                double grade      = 0.0;

                ProductID key = new ProductID(university, identifer);
                Product value = new Product( key, name, age, grade );
                
                st.put( key, value );

                if( st.get(key) == null ) throw new IllegalStateException("Put failed: "+key);
                if( i % 100000 == 0 ) Stdio.println("Put "+i);
            }
            Stdio.println( "Put "+n+ " items took " +time );
            Stdio.println( "Final symbol table=" +st.toTableString(false) );
            Stdio.printf(  "Load factor=%.2f\n", st.loadFactor() );
        }
        else
        {
            Stdio.open( args[0] );
            while( Stdio.hasNext() )
            {
                String method = Stdio.readString();
                if( method.equalsIgnoreCase("put") )
                {
                    String epc        = Stdio.readString();
                    int serialNumber  = Stdio.readInt();
                    String description= Stdio.readString();
                    int quantity      = Stdio.readInt();
                    double cost       = Stdio.readDouble();

                    ProductID key = new ProductID(epc, serialNumber);
                    Product value = new Product( key, description, quantity, cost );

                    st.put( key, value );
                    //Stdio.println( "put="+key.toString() );
                }
                else if( method.equalsIgnoreCase("delete") )
                {
                    String epc        = Stdio.readString();
                    int serialNumber  = Stdio.readInt();
                    ProductID key = new ProductID(epc, serialNumber);

                    Product deletedValue = st.delete(key);
                    //Stdio.println( "deleted="+deletedValue );
                }
                else if( method.equalsIgnoreCase("get") )
                {
                    String epc        = Stdio.readString();
                    int serialNumber  = Stdio.readInt();
                    ProductID key = new ProductID(epc, serialNumber);

                    Product value = st.get(key);
                    Stdio.println( "get="+value );
                }
                else if( method.equalsIgnoreCase("size") )
                {
                    Stdio.println( "size="+st.size() );
                }
                else if( method.equalsIgnoreCase("isEmpty") )
                {
                    Stdio.println( "isEmpty?="+st.isEmpty() );
                }
                else if( method.equalsIgnoreCase("toString") )
                {
                    Stdio.println( "toString?="+st.toTableString(true) );
                }
            }
            Stdio.println( "Final mappings=" +st.toString() );
            Stdio.println( "Final symbol table=" +st.toTableString(true) );
            Stdio.printf(  "Load factor=%.2f\n", st.loadFactor() );
            Stdio.close();
        }
    }
}

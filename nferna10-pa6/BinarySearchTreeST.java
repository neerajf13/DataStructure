
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @Neeraj Fernandes
 * INFS 519
 * Fall 2016
 */

/**
 * SymbolTable stores key/value pairs where keys map to unique values.
 * Binary search tree is a symbol table that addresses the weaknesses
 * of unordered list and ordered arrays.
 * @param <Key>
 * @param <Value>
 */
public class BinarySearchTreeST<Key extends Comparable, Value>
    implements OrderedSymbolTable<Key, Value>
{
	
	/** Node class (inner class) - defines the structure of node
	 */
	public class Node         
	{
		Node left,right;
		Key key;
		Value value;
		
		public Node(Key key, Value value)                             //constructor of node, it will be called when node is created
		{
			left=null;
			right=null;
			this.key=key;
			this.value=value;
		}
	}
    Node root;                                                        //head(first element) of Binary Search Tree
	int size=0;
 
    public BinarySearchTreeST()                                       //constructor of BinarySearchTree
    {
    	root=null;
    }
    
    /**
     * Gets the number of elements currently in the symbol table.
     * @return size
     */
    public int size()                                               
    {
        return size;
    }
    
    
    /**
     * Determines if there are no elements in the  symbol table.
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
     * Inserts the value into the table using specified key.  Overwrites
     * any previous value with specified value.
     * @param key
     * @param value
     * @throws NullPointerException if the key or value is null
     */
    public void put( Key key, Value value )
    {
    	if(key==null||value==null)
    	{
			throw new NullPointerException("NullPointerException :null key or value");
    	}
    	else
    	{
        	Node temp=new Node(key,value);		                   //create a new node to be inserted
        	temp.key=key;
        	temp.value=value;
        	
            if(root==null)                                                  //node to be inserted is the first node 
            {
            	temp.left=null;
            	temp.right=null;
            	root=temp;                                                  //temp node is now the root
            	size++;
            }
            else                                                            //node to be inserted has to be placed in its appropriate position
            {
            	boolean result = true;
            	Node ncurrent=root,nprevious=null;
            	while(ncurrent!=null && result==true)                       //keep traversing while condition is true 
            	{
            		nprevious=ncurrent;                                     //nprevious is used to keep track of previous node
            		int ans=temp.key.compareTo(ncurrent.key);               //compare node's key to be inserted with the current node's key
            		if(ans==1)                                              //key of node to be inserted is greater than current node's key
            		{
            			ncurrent=ncurrent.right;
            			result=true;
            		}
            		else if(ans==-1)                                        //key of node to be inserted is smaller than current node's key
            		{
            			ncurrent=ncurrent.left;
            			result=true;
            		}
            		else if(ans==0)                                         //key of node to be inserted is equal to current node's key
            		{
            			ncurrent.value=temp.value;                          //overwrite the value of the key with new value
            			result=false;                                       //as no further traversing and linking is needed
            		}
            	}
            	if(result)                                                  //condition of linking the new node
            	{
                	temp.left=null;
                	temp.right=null;
            		int ans=temp.key.compareTo(nprevious.key);
            		if(ans==-1)
            		{
            			nprevious.left=temp;                                //previous node points to new node
            		}
            		else
            		{
            			nprevious.right=temp;                               //previous node points to new node
            		}
            		size++;                                                 //increment the size
            	}
            }
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
	Node check=root;
      	Value store=null;
    	if(key==null)
    	{
			throw new NullPointerException("NullPointerException : key is null");
    	}
    	else                                                             //condition to find the key and return the value of that key
    	{
    		while(check!=null)                                          
    		{
    			int ans=key.compareTo(check.key);
    			if(ans==-1)
    			{
    				check=check.left;                                     //traverse left
       			}
    			else if(ans==1)                             
    			{
    				check=check.right;                                    //traverse right
    			}
    			else if(ans==0)                                           //key to be found matches with the node's key
    			{
    				store=check.value;                            
    	    		break;
    			}
    		}
    	}
        return store;                                                      //return the value
    }

    /**
     * Iterable that enumerates (in sorted order) each key in the table.
     * @return iter
     */
    public Iterable<Key> keys()
    {
    	DynamicArray<Key> list = new DynamicArray<Key>();
    	keys( this.root, list );                                           //call the keys for in-order traversal
    	return list; 
    }
    
    
    /**
     * keys - used to find the keys in-order traversal
     * @param traverse
     * @param list
     */
    private void keys( Node traverse, DynamicArray<Key> list)
    {
    	if(traverse!=null)
    	{
            keys(traverse.left,list);                                       //traverse to the left
            list.add(traverse.key);                                         //add the key to the list
            keys(traverse.right,list);                                      //traverse to the right
    	}
    }
    
    
    /**
     * Finds and returns minimum key
     * @return smallest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public Key min() throws NoSuchElementException
    {
    	if(size==0)
    	{
    		throw new NoSuchElementException("Symbol table is empty : No Element Found");
    	}
    	else
    	{
    		Node checkMin=root;
    		while(checkMin.left!=null)                                       //condition to find the smallest key 
    		{
    			checkMin=checkMin.left;                                  //traverse left till last left node is reached
    		}
            return checkMin.key;
    	}
    }

    
    /**
     * Finds and returns maximum key
     * @return largest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public Key max() throws NoSuchElementException
    {
    	if(size==0)
    	{
    		throw new NoSuchElementException("Symbol table is empty : No Element Found");
    	}
    	else
    	{
    		Node checkMax=root;
    		while(checkMax.right!=null)                                     //condition to find the largest key
    		{
    			checkMax=checkMax.right;                                //traverse right till last right node is reached
    		}
            return checkMax.key;
    	}
    }
    
    /**
     * Removes the maximum key from the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMax( ) throws NoSuchElementException
    {
    	if(size==0)
    	{
    		throw new NoSuchElementException("Symbol table is empty : No Element Found");
    	}
    	else
    	{
    		Node checkMax=root,checkPrev=null;                     
    		while(checkMax.right!=null)                                   //traverse right till last right node is reached   
    		{
    			checkPrev=checkMax;                                    //keep track of previous node
    			checkMax=checkMax.right;
    		}
    		if(checkMax.left!=null)                                       //if Maximum node to be deleted has left child
    		{
    			checkPrev.right=checkMax.left;                          //previous node before the node to be deleted points to the left child of node to be deleted
    			checkMax.left=null;
    		}
    		else
    		{
        		checkPrev.right=null;                                   //remove the link pointing to the maximum key
    		}
    		size--;
    	}
    }
    
    /**
     * Removes the minimum key from the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMin( ) throws NoSuchElementException
    {
    	if(size==0)
    	{
    		throw new NoSuchElementException("Symbol table is empty : No Element Found");
    	}
    	else
    	{
    		Node checkMin=root,checkPrev=null;
    		while(checkMin.left!=null)                                     //traverse left till last left node is reached
    		{
    			checkPrev=checkMin;			        	   //keep track of previous node
    			checkMin=checkMin.left;
    		}
    		if(checkMin.right!=null)                                       //if Minimum node to be deleted has right child
    		{
    			checkPrev.left=checkMin.right;                             //previous node before the node to be deleted points to the right child of node to be deleted
    			checkMin.right=null;
    		}
    		else
    		{
        		checkPrev.left=null;                                       //remove the link pointing to the minimum key
    		}
    		size--;
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

    /**
     * Unit tests the ST data type.
     * @param args 
     */
    public static void main(String[] args)
    {
        Stdio.open( args[0] );
        BinarySearchTreeST<Integer,String> st = new BinarySearchTreeST<Integer,String>();
        while( Stdio.hasNext() )
        {
            String method = Stdio.readString();
            if( method.equalsIgnoreCase("insert") )
            {
                int key    = Stdio.readInt();
                String val = Stdio.readString();
                st.put( key, val );
                Stdio.println( "insert="+st.toString() );
            }
            else if( method.equalsIgnoreCase("deleteMin") )
            {
                st.deleteMin();
                Stdio.println( "deleteMin" );
            }
            else if( method.equalsIgnoreCase("deleteMax") )
            {
                st.deleteMax();
                Stdio.println( "deleteMax" );
            }
            else if( method.equalsIgnoreCase("size") )
            {
                Stdio.println( "size="+st.size() );
            }
            else if( method.equalsIgnoreCase("min") )
            {
                Stdio.println( "min="+st.min() );
            }
            else if( method.equalsIgnoreCase("max") )
            {
                Stdio.println( "max="+st.max() );
            }
            else if( method.equalsIgnoreCase("isEmpty") )
            {
                Stdio.println( "isEmpty?="+st.isEmpty() );
            }
        }
        Stdio.println( "Final symbol table=" +st.toString() );
        Stdio.close();
    }
}


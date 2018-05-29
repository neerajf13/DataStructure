
import java.util.NoSuchElementException;

/**
 * @author Neeraj Fernandes
 * INFS 519
 * Fall 2016
 */

/**
 * Balanced BST symbol table.
 * @param <Key>
 * @param <Value>
 */
public class AVLTreeST<Key extends Comparable, Value>
    implements OrderedSymbolTable<Key, Value>
{   

    /**
     * Inserts the value into the table using specified key.  Overwrites
     * any previous value with specified value.
     * @param key
     * @param value
     * @throws NullPointerException if the key or value is null
     */
    private Node put( Node node, Key key, Value value )
    {
    	if(key==null||value==null)
    	{
    		//System.out.println("NullPointerException: Key or Value of node cannot be null");
    		throw new NullPointerException("Key or Value of node cannot be null");
    	}
    	
    	else
    	{
    		if( node == null )                                     //node to be inserted is the first node
            {
                node = new Node(key, value);
                this.size++;                                       //increment the size
                //System.out.println(node.value);
                return node;
            }
            else if( key.compareTo( node.key ) < 0 )               //node to be inserted has key less than the current node key it is compared with
            {
                node.left=put( node.left, key, value );            //keep traversing left
                if(height(node.left)-height(node.right)==2)        //condition checking if there is need for rotation
                {
                	
                	/*
                	 *rotate with left child  
                	 *(Eg: when first 3 put sequence are 10,5,2)
                	 *After rotation: 5 is root with 2 and 10 being left and right child respectively
                	 */
                	if(key.compareTo(node.left.key)<0)
                	{
                		//System.out.println("RotLeftChild");
                		node=rotateWithLeftChild(node);
                	}
                	
                	
                	/*
                	 *Double rotate with left child  
                	 *(Eg: when first 3 put sequence are 10,5,7)
                	 * There will be two rotations:
                	 * a) First rotation - rotate with right child. 10 will be root ==> 7 left child of 10 ===> 5 left child of 7
                	 * b) Second rotation - rotate with left child. 7 will be root ==> 5 left child of 7, 10 right child of 7
                	 */
                	else
                	{
                		//System.out.println("DoubleRotLeftChild");
                		node=doubleRotateLeft(node);
                	}
                }                    
            }
            
            else if( key.compareTo( node.key ) > 0 )             //node to be inserted has key larger than the current node key it is compared with
            {
                node.right = put( node.right, key, value );      //keep traversing right
                if(height(node.right)-height(node.left)==2)      //condition checking if there is need for rotation
                {
                	
                	/*
                	 *rotate with left child  
                	 *(Eg: when first 3 put sequence are 10,15,20)
                	 *After rotation: 15 is root with 10 and 20 being left and right child respectively
                	 */
                	if(key.compareTo(node.right.key)>0)
                	{
                		//System.out.println("RotRightChild");
                		node=rotateWithRightChild(node);
                	}
                	
                	
                	/*
                	 *Double rotate with right child  
                	 *(Eg: when first 3 put sequence are 10,15,12)
                	 * There will be two rotations:
                	 * a) First rotation - rotate with left child. 10 will still be root ==> 12 right child of 10 ===> 15 right child of 7
                	 * b) Second rotation - rotate with left child. 12 will be root ==> 10 left child of 12, 15 right child of 12
                	 */
                	else
                	{
                		//System.out.println("DoubleRotRightChild");
                		node=doubleRotateRight(node);
                	}
                }                                
            }
            else
            {
                node.value = value;                                                    //assign value to the node
            }
            node.height = 1 + Math.max(height(node.left), height(node.right));         //assign height to the node
            //System.out.println(node.height);
            
            return node;
    	}
    }
    
    

    /**
     * Rotate with left child.
     * @param Node k2
     * @return temp
     */
    private Node rotateWithLeftChild( Node k2 )
    {
    	//System.out.println("case 2");
        Node temp;                                    
        temp=k2.left;                               //temp points to left child of Node k2
        k2.left=temp.right;                         //right child of node to which temp points becomes the left child of node k1
        temp.right=k2;                              //node k2 is the right child of node to which temp points
        k2.height = 1 + Math.max(height(k2.left), height(k2.right));                  //update height for node k2
        temp.height = 1 + Math.max(height(temp.left), height(temp.right));            //update height for node temp
        //System.out.println(temp.value);
        return temp;
    }
    
    
    /**
     * Rotate with right child.
     * @param Node k1
     * @return temp
     */
    private Node rotateWithRightChild( Node k1 )
    {
    	//System.out.println("case 1");
    	Node temp;
        temp=k1.right;                               //temp points to the right child of node k1
        k1.right=temp.left;                          //left child of node to which temp points becomes the right child of node k1
        temp.left=k1;                                //node k1 is now the left child of the node to which node temp points
        k1.height = 1 + Math.max(height(k1.left), height(k1.right));                   //update height for node K1
        temp.height = 1 + Math.max(height(temp.left), height(temp.right));             //update height for node temp
        //System.out.println(temp.value);
        return temp;
    }
    
    
    /**
     * Double Rotate Left 
     * @param Node k3
     * @return call function to rotate left child with @param k3
     */
    private Node doubleRotateLeft( Node k3 )
    {
    	//System.out.println("case 3");
    	k3.left = rotateWithRightChild(k3.left);         //rotate left child having right child
        return rotateWithLeftChild(k3);                  //left rotate node k3 with its new child
    }
    
    
    /**
     * Double Rotate Right
     * @param Node k1
     * @return call function to rotate right child with @param k1
     */
    private Node doubleRotateRight( Node k1 )
    {
    	//System.out.println("case 4");
        k1.right = rotateWithLeftChild(k1.right);        //rotate right child having left child
        return rotateWithRightChild(k1);                 //right rotate node k1 with its new child
    }



    //--------------------- DO NOT MODIFY BELOW THIS -----------------------//

    private class Node
    {
        private Key key;
        private Value value;
        
        private Node left;
        private Node right;
        
        private int height;
        
        public Node( Key key, Value value )
        {
            this.key   = key;
            this.value = value;
        }
    }
    
    private Node root;
    private int size;
    
    /**
     * Creates a new search tree with default parameters.
     */
    public AVLTreeST()
    {
        this.size = 0;
    }


    private int max( int h1, int h2 )
    {
        return Math.max(h1, h2);
    }
    
    private int height( Node x )
    {
        return ( x == null ) ? -1 : x.height;
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
        return size == 0;
    }
    
    /**
     * Inserts the value into the table using specified key.  Overwrites
     * any previous value with specified value.
     * @param key
     * @param value
     * @throws NullPointerException if key or value is null
     */
    public void put( Key key, Value value )
    {
        this.root = this.put(this.root, key, value);
    }
    
    /**
     * Finds Value for the given Key.
     * @param key
     * @return value that key maps to or null if not found
     */
    public Value get( Key key )
    {
        Node node = this.find(key, this.root);
        return (node!=null)?node.value:null;
    }
    
    /**
     * Recursive function that finds the Node with specified Key or null.
     * @param key
     * @param node
     * @return node for key or null if not found
     */
    private Node find( Key key, Node node )
    {
        if(node == null )         return null;
        else
        {
            // Do we go left or right?
            int cmp = key.compareTo(node.key);
            if(      cmp < 0 ) return find( key, node.left  );
            else if( cmp > 0 ) return find( key, node.right );
            else     return node;
        }
    }
    
    
    
    public Iterable<Key> keys()
    {
        List<Key> list = new DynamicArray<Key>();
        this.keys(root, list);
        return list;
    }
    
    private void keys( Node x, List list )
    {
        if( x == null ) return;
        this.keys(x.left, list);
        list.add(x.key);
        this.keys(x.right, list);
    }
    
    private void checkEmpty()
    {
        if( this.size == 0 )
        {
            throw new NoSuchElementException("SymbolTable is empty");
        }
    }
    
    public Key min() throws NoSuchElementException
    {
        checkEmpty();
        return this.min(this.root).key;
    }
    
    private Node min( Node t )
    {
        if( t != null )
        {
            while( t.left != null ) t = t.left;
        }
        return t;
    }

    public Key max() throws NoSuchElementException
    {
        checkEmpty();
        return this.max(this.root).key;
    }
    
    private Node max( Node t )
    {
        if( t != null )
        {
            while( t.right != null ) t = t.right;
        }
        return t;
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
            
            buf.append( key );
            
            buf.append( "->" );
            buf.append( item.toString() );
        }
        buf.append("]");
        return buf.toString();
    }
    
    public boolean balanced( )
    {
        if( root == null ) throw new IllegalStateException("Expect root to be non-null");
        return this.balanced(root);
    }
    
    private boolean balanced( Node x )
    {
        if( x.left == null && x.right == null )
        {
            // leaf, height should be zero
            if( x.height != 0 ) return false;
        }
        
        // Check to see if our left and right subtrees are balanced
        boolean lb = (x.left != null) ? balanced(x.left)  : true;
        boolean rb = (x.right!= null) ? balanced(x.right) : true;
        
        if( lb == false || rb == false ) return false;
        
        // If subtrees are balanced, is our height correct
        // Non-leaf, should be one more than left/right height
        int lh = (x.left  != null) ? x.left.height  : -1;
        int rh = (x.right != null) ? x.right.height : -1;
            
        int expectedHeight = max(lh, rh) + 1;
            
        if( x.height != expectedHeight ) return false;
        
        // Everything up to here makes sure the height is correct
        // This final check makes sure balanced condition is met
        int diff = lh - rh;
        if( Math.abs(diff) >= 2 ) return false;
        
        return true;
    }
    
    public static boolean isInteger( String s )
    {
        try
        {
            Integer.parseInt(s);
        }
        catch(NumberFormatException e)
        {
            return false;
        }
        return true;
    }
    
    /**
     * Unit tests the ST data type.
     * @param args 
     */
    public static void main(String[] args)
    {
        AVLTreeST<Integer,String> st = new AVLTreeST<Integer,String>();
        
        if( isInteger(args[0]) )
        {
            int n = Integer.parseInt( args[0] );
            java.util.Random rand = new java.util.Random();
            Clock time = new Clock();
            for( int i = 0; i < n; i++ )
            {
                // Ways to generate test data
                // 1. Generate sorted            (worse case  )
                // 2. Generate uniformily random (average case)
                int key      = i;                // Case 1
                //int key      = rand.nextInt(); // Case 2                

                String value = "value"+key;
                st.put( key, value );

                if( st.get(key) == null ) throw new IllegalStateException("Put failed: "+key);
            }
            Stdio.println( "Put "+n+ " items took " +time );
            Stdio.println( "Balanced? " +st.balanced() );
        }
        else
        {
            Stdio.open( args[0] );
            while( Stdio.hasNext() )
            {
                String method = Stdio.readString();
                if( method.equalsIgnoreCase("put") )
                {
                    int key    = Stdio.readInt();
                    String val = Stdio.readString();
                    st.put( key, val );
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
            Stdio.println( "Balanced? " +st.balanced() );
            Stdio.close();
        }
    }
}


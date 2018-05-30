/**
 * @author Neeraj Fernandes
 * INFS 519
 * Fall 2016
 */

/**
 * Implementation of BFS using queue
 * Methods used - search(), hasPathFromSource() and iterator 
 */
public class BFS
{
	//variable declaration and initialization
    private static final int INFINITY = Integer.MAX_VALUE;
	private Graph graph;
	private int source;
    private boolean[] marked;  
    private int paths[];
    
    /**
     * Creates a new BFS and performs search on the specified Graph.
     * @param graph
     * @param source
     */
    public BFS( Graph graph, int source )              //constructor
    { 
    	//initializing graph and source
		this.graph = graph;                            
		this.source = source;
		marked = new boolean[graph.V()];
        paths = new int[graph.V()];
        search();                                      //calling search method
    }
    
    /**
     * Iterative approach to BFS.  Uses Queue that may grow to E.  Keeps
     * track of the marked and paths for later queries.
     */
    private void search()
    {
        Queue<Integer> q = new Queue<Integer>();             //creating queue
        marked[this.source] = true;                          
        q.enqueue(this.source);

        while (!q.isEmpty())                                 //condition if queue is not empty
		{
            int v = q.dequeue();                             //dequeue v      
            for (int w : this.graph.neighbors(v))            
			{
                if (!marked[w])                              //condition if neighbor is not yet marked
				{
                    paths[w] = v;                            //mark neighbor
                    marked[w] = true;                        //updating path for neighbor to v
                    q.enqueue(w);                            //enqueue neighbor
                }
            }
        }
    }
    
    /**
     * Returns whether or not a path exists from the source to v.
     * @param v
     * @return true if a path exists from the source to v, false otherwise
     */
    public boolean hasPathFromSource( int v )
    {
        if(v < 0 || v >= this.graph.V())
        {
        	throw new IndexOutOfBoundsException();
        }
        return marked[v];
    }
    
    /**
     * Returns path from the source to the given vertex v, in that order.
     * @param v
     * @return path from the source to v, starts with source, ends with v
     *         returns a null if no path exists
     */
    public Iterable<Integer> pathFromSource( int v )
    {
        if (!hasPathFromSource(v))                        //condition if path doesn't exist
        {
        	return null;
        }
        Stack<Integer> st = new Stack<Integer>();         //stack creation
        while(v != this.source)                           //condition if v is not source
		{ 
            st.push(v);                                   //push v on stack
			v = paths[v];
		}
		st.push(this.source);                             //push source to stack
        return st; 
    }
    
    //------- DO NOT MODIFY BELOW THIS LINE -------//

    /**
     * Unit test main for the BFS class.
     * @param args 
     * @throws java.io.FileNotFoundException 
     */
    public static void main( String[] args ) throws java.io.FileNotFoundException
    {
        if( args.length != 2 )
        {
            String u = "Usage: BFS <filename> <source>";
            Stdio.println(u);
            return;
        }
        
        String fileName = args[0];
        int source      = Integer.parseInt(args[1]);
        Graph graph = GraphFactory.make( fileName );
        
        BFS bfs = new BFS( graph, source );
        Stdio.println( "Paths to source: "+source );
        for (int vertexId = 0; vertexId < graph.V(); vertexId++)
        {
            Stdio.print( "  path for "+vertexId+" : " );
            if( bfs.hasPathFromSource(vertexId) )
            {
                for( int pathVertex : bfs.pathFromSource(vertexId) )
                {
                    Stdio.print( "->" + pathVertex );
                }
            }
            Stdio.println( "" );
        }
    }
}

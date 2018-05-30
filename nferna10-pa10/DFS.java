/**
 * @author Neeraj Fernandes
 * INFS 519
 * Fall 2016
 */

/**
 * Implementation of DFS using stack
 * Methods used - search(), hasPathFromSource() and iterator 
 */
public class DFS
{
    //variable declaration and initialization
	private static final int INFINITY = Integer.MAX_VALUE;
	private Graph graph;
	private int source;
	private boolean[] marked; 
    private int paths[];
    
    /**
     * Creates a new DFS and performs search on the specified Graph.
     * @param graph
     * @param source
     */
    public DFS( Graph graph, int source )                       //constructor
    {
		this.graph = graph;
		this.source = source;
		marked = new boolean[graph.V()];
		paths = new int[graph.V()];
        search();                                              //calling the search method
    }
    
    /**
     * Iterative approach to DFS.  Uses Stack that may grow to E.  Keeps
     * track of the marked and paths for later queries.
     */
    private void search()
    {
		Stack<Integer> st = new Stack<Integer>();                //stack creation
        paths[this.source] = this.source;                         
        st.push(this.source);                                    //push source on stack

        while (!st.isEmpty())                                    //condition stack is not empty
		{
            int v = st.pop();                                     
            if(!marked[v])                                       //condition if v is not marked
			{
				marked[v] = true;
				for(int n : this.graph.neighbors(v))
				{
					if(!marked[n])                               //condition if neighbor is not marked
					{
						st.push(n);                              //push neighbor on stack
						paths[n] = v;                            //update path for neighbor to v
					}
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
     * returns a null if no path exists
     */
    public Iterable<Integer> pathFromSource( int v )
    {
        if(!hasPathFromSource(v))                              //condition if path does'nt exist
        {
			return null;        	
        }
		Stack<Integer> st = new Stack<Integer>();              //stack creation
		while(v != this.source)                                //if v is not source
		{
			st.push(v);                                        //push v on stack
			v = paths[v];
		}
		st.push(this.source);                                  //push source to stack

		return st;
    }
    


    //------- DO NOT MODIFY BELOW THIS LINE -------//
    
    /**
     * Unit test main for the DFS class.
     * @param args 
     * @throws java.io.FileNotFoundException 
     */
    public static void main( String[] args ) throws java.io.FileNotFoundException
    {
        if( args.length != 2 )
        {
            String u = "Usage: DFS <filename> <source>";
            Stdio.println(u);
            return;
        }
        
        String fileName = args[0];
        int source      = Integer.parseInt(args[1]);
        Graph graph = GraphFactory.make( fileName );
        
        DFS dfs = new DFS( graph, source );
        Stdio.println( "Paths to source: "+source );
        for (int vertexId = 0; vertexId < graph.V(); vertexId++)
        {
            Stdio.print( "  path for "+vertexId+" : " );
            if( dfs.hasPathFromSource(vertexId) )
            {
                for( int pathVertex : dfs.pathFromSource(vertexId) )
                {
                    Stdio.print( "->" + pathVertex );
                }
            }
            Stdio.println( "" );
        }
    }
}

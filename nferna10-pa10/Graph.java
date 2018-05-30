/**
 * @author Neeraj Fernandes
 * INFS 519
 * Fall 2016
 */

import java.util.Iterator;

/**
 * The Graph is undirected and is implemented as an adjacency list.
 * Method used - v() for number of vertices, E() for number of edges and iterator
 */
public class Graph
{
	//variable declaration and initialization
    private Bag<Integer>[] vertices;		  	 //Array of lists
    private int V;	                             //number of vertices
	private int E;			  					 //number of edges
	
    /**
     * Creates a new graph.  Vertices are fixed.  Edges can be added 
     * after creation but not removed.
     * @param numVertices
     */
    public Graph( int numVertices )
    {
		V = numVertices;
		E = 0;
		vertices = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++) 
		{
            vertices[v] = new Bag<Integer>();       //assigning bag to each vertex
        }
    }
    
    /**
     * Gets the number of vertices in the graph.
     * @return V
     */
    public int V()
    {
        return V; 
    }
    
    /**
     * Gets the number of edges in the graph.
     * @return E
     */
    public int E()
    {
        return E; 
    }
    
    /**
     * Gets iterator that enumerates the vertexId of the neighbors of given
     * vertexId.
     * @param vertexId
     * @return neighbor of vertexId
     * @throws IndexOutOfBoundsException if vertexId is invalid
     *         (less than 0, more than or equal to V)
     */
    public Iterable<Integer> neighbors( final int vertexId )
    {
        if(vertexId < 0 || vertexId > V)               //condition for Invalid vertexId
        {
			throw new IndexOutOfBoundsException();        	
        }
        return vertices[vertexId];
    }
    
    /**
     * Adds an edge between v and w.
     * @param v
     * @param w 
     * @throws IndexOutOfBoundsException if v or w are invalid
     *         (less than 0, more than or equal to V)
     */
    public void addEdge( int v, int w )
    {
		if(v < 0 || w < 0 || v >= V || w >= V)        //condition for invalid v and w
		{
			throw new IndexOutOfBoundsException();			
		}
		E += 1;
		
		//adding vertex
        vertices[v].add(w);                           
        vertices[w].add(v);
    }


    //------- DO NOT MODIFY BELOW THIS LINE -------//

    /**
     * Gets String facsimile of this graph.
     * @return 
     */
    @Override
    public String toString()
    {
        StringBuilder buf = new StringBuilder();
        String title = "V=" + this.V() + " E=" + this.E();
        buf.append( title );
        for (int vertexId = 0; vertexId < this.vertices.length; vertexId++)
        {
            Bag<Integer> neighbors = this.vertices[vertexId];
            String prefix = "\n["+vertexId+"] neighbors="+neighbors.size()+": ";
            buf.append( prefix );
            boolean first = true;
            for( int neighborId : neighbors )
            {
                if( first ) first = false;
                else buf.append( ", " );
                buf.append( neighborId );
            }
        }
        return buf.toString();
    }
    
    
    /**
     * Unit test main for the Graph class.  Reads a file and prints out.
     * @param args 
     * @throws java.io.FileNotFoundException 
     */
    public static void main( String[] args ) throws java.io.FileNotFoundException
    {
        String fileName = args[0];
        Graph graph = GraphFactory.make( fileName );
        Stdio.println( "Graph: "+graph );
    }
}

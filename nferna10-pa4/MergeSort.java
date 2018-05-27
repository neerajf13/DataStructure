
/**
 * @author Neeraj Fernandes
 * INFS 519
 * Fall 2016
 */

/**MergeSort Algorithm 
 * Takes 'n' number of inputs and sorts them in ascending order
 * Reads an array of unsorted comparable items and passes to sort method
 * Unit testing is used to check if array is sorted
 * Clock is used to find out the execution time required to sort comparable items
 */
public class MergeSort
{
	static int count=1;                 
	
    /**
     * Sorts the items ascending, from smallest to largest.
     * @param items (modified)
     * @throws NullPointerException if items is null
     */
    public static void sort(Comparable[] items)
    {
    	Comparable[] aux=new Comparable[items.length];
    	int lo=0;
    	int hi=items.length-1;
    	try
    	{
        	if(items.length==0)
        	{
        		throw new NullPointerException();
        	}
        	mergeSort(items,aux,lo,hi);
    	}
    	catch(NullPointerException e)
    	{
    		System.out.println("No Elements found");
    		return;
    	}
        // Postcondition
        assert SortUtil.isSorted(items,0,items.length-1);
    }

    /**mergesort()
     * @param items aux 
     * @param lo hi 
     * 1) finds the mid of the array and keeps dividing the array in two halfs recursively
     * 2) calls the merge() function. Passes @param items, aux, lo, mid, hi
     */
    public static void mergeSort(Comparable[] items, Comparable[] aux, int lo, int hi )
    {
        if(lo>=hi)
        {
        	return;
        }
    	int mid=lo+(hi-lo)/2;
    	mergeSort(items,aux,lo,mid);
    	mergeSort(items,aux,mid+1,hi);
    	merge(items,aux,lo,mid,hi);
    }

    /**merge() function
     * @param items,aux
     * @param lo, mid, hi
     * 1) Sort the first half of the array till mid
     * 2) Sort the second half of the array after mid
     * 3)Merge the first half of the array with the second half adjusting the smallest value to the left of array.
     */
    public static void merge(Comparable[] items, Comparable[] aux, int lo, int mid, int hi )
    {
        // Preconditions using java assert, can also use junit tests
        assert SortUtil.isSorted( items, lo, mid );
        assert SortUtil.isSorted( items, mid+1, hi );
        for (int i=lo; i<=hi; i++)
        {
            aux[i]=items[i]; 
        }
        int l=lo,m=mid+1;
        for(int i=lo; i<=hi; i++) 
        {
            if(l>mid)
            {
            	 items[i]=aux[m++];
            }
            else if(m>hi) 
            {
            	items[i]=aux[l++];
            }
            else if(less(aux[m], aux[l]))
            {
            	items[i]=aux[m++];
            }
            else                           
            {
            	items[i]=aux[l++];
            }
        	//display(items);
        }    	   
    	
    //	Alternate logic without the use of auxillary array
     /*
        int end=mid,start=mid+1;
        while(lo<=end && start<=hi)
        {	
        	if(less(items[lo],items[start]))
        	{
        		lo++;
        	}
            else
            {
            	int temp=(int) items[start];
            	for(int k=start-1;k>=lo;k--)
            	{
            		items[k+1]=items[k];
            	}
            	items[lo]=temp;
            	lo++;
            	end++;
            	start++;
            }
        	//display(items);
         }   */
        // Postcondition
        assert SortUtil.isSorted( items, lo, hi );
    }
    
    /** Display() function
     * It is used to display how the elements move in array at each step
     * @param items 
     */
    public static void display(Comparable[]items)
    {
    	System.out.print("Step "+count);
    	for(int k=0;k<items.length;k++)
    	{
    		System.out.print("\t"+items[k]);
    	}
    	count++;
    	System.out.println("\n");
    }
    

    //--------------------- DO NOT MODIFY BELOW THIS -----------------------//

    /** USE IS OPTIONAL, NOT NECESSARY
     * Utility method that compares a and b.  Conceptually return a &lt b.
     * @param a
     * @param b
     * @return true if a strictly less than b, false otherwise
     */
    public static boolean less( Comparable a, Comparable b )
    {
        return a.compareTo(b) < 0;
    }

    /**
     * Unit tests MergeSort.
     * @param args 
     */
    public static void main( String[] args )
    {
        if( args.length != 1 )
        {
            String u = "Usage: MergeSort < n | inputFilename >";
            Stdio.println(u);
            return;
        }
        
        String argument = args[0];
        Comparable[] items;
        if( Stdio.isInteger( argument ) )
        {
            int n = Integer.parseInt(argument);
            int bounds = 100; // keep generated number between [0,bounds)
            items = Stdio.generate( n , bounds);
        }
        else
        {
            Stdio.open( args[0] );
            // Create list, (could also use DynamicArray here)
            Comparable[] list = new Comparable[4];
            int size = 0;
            while(Stdio.hasNext())
            {
                //String item = Stdio.readString();
                Integer item = Stdio.readInt();
                // expand capacity if necessary
                if( size == list.length )
                {
                    Comparable[] temp = new Comparable[list.length*2];
                    System.arraycopy(list, 0, temp, 0, list.length);
                    list = temp;
                }
                list[size++] = item;
            }
            Stdio.close();
            // Trim off null positions after size
            items = new Comparable[size];
            System.arraycopy(list, 0, items, 0, size);
        }
        
        Clock time = new Clock();
        MergeSort.sort(items);
        Stdio.println( "Is sorted? "+SortUtil.isSorted(items, 0, items.length-1) );
        Stdio.println( "Time=" + time );
    }
}

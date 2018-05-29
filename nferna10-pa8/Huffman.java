import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/**
/**
 * @author Neeraj Fernandes
 * INFS 519
 * Fall 2016
 */
 
/**
 * Huffman - generates an optimal, prefix free , coding table for data compression .
 * The methods used to obtain the above are: determineFrequencies(), compress(), makeCodingTable(), makeTrie(), decompress()
 */

public class Huffman
{
    /**
     * determineFrequencies()
     * tabulates the frequency count based on the characters of text that is passed
     * @param char[] text
     * @return freq
     */
    public static int[] determineFrequencies( char[] text )
    {
    	int[] freq = new int[RADIX];
        for (int i = 0; i < text.length; i++)
        {
            freq[text[i]]++;
        }
        return freq;
    }

    /**
     * Reads a sequence of 8-bit bytes from input; compresses them
     * using Huffman codes with an 8-bit alphabet; and writes the results
     * to output.
     * @param char[] text
     * @return byte[]
     */
     public static byte[] compress( char[] text )
    {
    	 //char[] input=text; 	     	 
    	 int freq[]=new int[RADIX];
    	 freq=determineFrequencies(text);                //Determine the frequencies- call the determineFrequencies function()
    	 
   /*    for(int i=0; i<freq.length;i++)
    	 {
    		 System.out.println(freq[i]);
    	 } */
         Node trie=makeTrie(freq);                       //Build a trie by calling makeTrie function
         
    	 String[] table = new String[RADIX];
    	 makeCodingTable(table, trie, "" );              //builds code table by calling makeCodingTable function
     	 
    	 BitStreamOutput out = new BitStreamOutput();
    	 writeTrie(trie, out);                           //write trie for decoder and number of symbols
    	 out.writeBits(text.length,31);                  //write number of characters in the text
    	 
    	 
    	 //for loop is used for encoding input by using huffman coding
    	 for( int i = 0; i < text.length; i++ )   
   	     {              	    	
    		 String genCode = table[text[i]];
             for (int j = 0; j < genCode.length(); j++) 
             {
                 if (genCode.charAt(j)=='0') 
                 {
                     out.writeBit(false);
                 }
                 else if (genCode.charAt(j)=='1') 
                 {
                     out.writeBit(true);
                 }
             }
   	     }
   	     out.flush();                                     //avoids last byte being missed
   	     return out.toArray();  	  
    }

    /**
     * make a lookup table from symbols and their encodings
     */
    public static void makeCodingTable( String[] table, Node x, String code )
    {
    	if(x.isLeaf())                                                     //leaf node reached
    	{
    		table[x.symbol]=code;
    	}
    	else
    	{
            makeCodingTable( table, x.left,  code+'0' );                    //call recursively the function for left element and by passing new code by appending 0
            makeCodingTable( table, x.right, code+'1' );					//call recursively the function for right element by passing new code by appending 1
    	}
    }

    /**
     * Using huffman approach, make prefix-free code
     * @param int[] freq
     * @return delMin()
     */
    public static Node makeTrie( int[] freq )
    { 	
    	MinHeap<Node> pq = new MinHeap<Node>(freq.length);    
    	for( char i = 0; i < freq.length; i++)
    	{      
    		if (freq[i] > 0)
    		{
    			pq.insert(new Node(i,null,null,freq[i]));					  // Add node for each non-zero frequency to priority queue
    		}                
    	}  
        if (pq.size() == 1)                                                   //condition for Special handling if only one "tree" 
        {
            if (freq['\0'] == 0)                                              
            {
            	pq.insert(new Node('\0',null,null,0));
            }
            else
            {
            	pq.insert(new Node('\1',null,null,0));
           	}           
        }
        while( pq.size() > 1 )    											   // condition to Merge all the sub-trees into one rooted tree
        { 
        	// Remove two smallest 
            Node left  = pq.delMin();
            Node right = pq.delMin();
            Node parent = new Node('\0',left, right, left.freq + right.freq);  // Create new node with sum of their frequencies
            pq.insert(parent);												   // Adding new node back to priority queue
        } 
        return pq.delMin();
    }
    
    /**Construct huffman tree from stream of sequence of 0's and 1's
     * Reads a sequence of bits that represents a Huffman-compressed message from
     * input; expands them; and writes the results to output.
     */
    public static char[] decompress(BitStreamInput in)
    {
    	Node trie=readTrie(in);						    // Read in the trie
    	int n = in.readBits(31);                        //read the number of symbols in the original text
    	char[] decompressedText = new char[n];          
    	
    	//loop is used to decode the remaining bitstream using trie
    	for (int i = 0; i < n; i++)     
    	{
    		Node node = trie;
            while (!node.isLeaf())                      //continue till leaf node is not reached
            { 
                boolean bit = in.readBit();
                if (bit)
                {
                	node = node.right;                   //traverse right
                }
                else
                {
                	node = node.left;                    //traverse left
                }
            }
            decompressedText[i] = node.symbol;           //assign the symbol to decompressedText array
    	}  
    	return decompressedText;
    }


    //----- DO NOT MODIFY ANYTHING BELOW THIS LINE -----//

    public static final int RADIX = 256; // number of symbols for extended ascii

    private static class Node implements Comparable<Node>
    {
        private Node left;
        private Node right;
        private char symbol;
        private int freq;    

        public Node( char c, int freq )
        {
            this(c, null, null, freq);
        }

        public Node( char symbol, Node left, Node right, int freq )
        {
            this.symbol = symbol;
            this.left   = left;
            this.right  = right;
            this.freq   = freq;
        }

        public Node getLeft() { return this.left; }
        public Node getRight() { return this.right; }
        public char getSymbol() { return this.symbol; }
        public int getFreq() { return this.freq; }
        public boolean isLeaf() { return this.left == null && this.right == null; }

        public int compareTo( Node other )
        {
            return this.freq - other.freq;
        }
        
        @Override
        public String toString()
        {
            return "("+this.symbol+" " + freq+")";
        }
    }
    

    public static void writeTrie(Node x, BitStreamOutput out)
    {
        // Use preorder traversal to encode the trie
        if (x.isLeaf())
        {
            out.writeBit(true);
            out.writeBits(x.symbol, 8);
            return;
        }
        out.writeBit(false);
        writeTrie(x.left,  out);
        writeTrie(x.right, out);
    }

    public static Node readTrie( BitStreamInput in )
    {
        boolean bit = in.readBit();
        if( bit )
        {
            char symbol = (char)in.readBits(8);
            return new Node(symbol, 0);
        }
        Node internalNode = new Node('\0', 0);
        internalNode.left  = readTrie( in );
        internalNode.right = readTrie( in );

        return internalNode;
    }
    
    public static void printTable(String[] table)
    {
        for( int i = 0; i < table.length; i++ )
        {
            String code = table[i];
            if(code != null) Stdio.println(""+((char)i) + " = " +code );
        }
    }
    
    
    /**
     * Unit tests the Huffman compression/decompression algorithm.
     * @param args 
     * @throws java.io.IOException 
     */
    public static void main( String[] args ) throws IOException
    {
        if( args.length != 2 )
        {
            String u = "Usage: Huffman <+|-> <filename>";
            Stdio.println(u);
            return;
        }
        
        String option   = args[0];
        String filename = args[1];
        if( option.equals("-") )
        {
            BufferedReader fileIn = new BufferedReader( new FileReader(filename) );
            StringBuilder buf = new StringBuilder();
            int nextChar = fileIn.read();
            while( nextChar != -1 )
            {
                buf.append((char)nextChar);
                nextChar = fileIn.read();
            }
            fileIn.close();
            
            char[] text = buf.toString().toCharArray();
            
            byte[] compressedText = compress(text);
            
            FileOutputStream fos = new FileOutputStream( filename+".zip" );
            BufferedOutputStream file = new BufferedOutputStream(fos);
            file.write(compressedText);
            file.close();
        }
        else if( option.equals("+") )
        {
            // READ IN THE FILE
            FileInputStream fis = new FileInputStream( filename );
            BufferedInputStream file = new BufferedInputStream(fis);
            
            byte[] compressedText = new byte[16];
            int size = 0;
            int byteRead = file.read();
            while( byteRead != -1 )
            {
                if( size == compressedText.length )
                {
                    byte[] newCompressedText = new byte[compressedText.length*2];
                    System.arraycopy(compressedText, 0, newCompressedText, 0, compressedText.length);
                    compressedText = newCompressedText;
                }
                
                compressedText[size++] = (byte)byteRead;
                byteRead = file.read();
            }            
            file.close();
            
            BitStreamInput in = new BitStreamInput(compressedText);
            char[] decompressedText = decompress( in );
            
            String text = new String(decompressedText);
            Stdio.println("Decompressed: "+text);
        }
        else
        {
            Stdio.println("Invalid option: "+option);
        }
        
    }
}

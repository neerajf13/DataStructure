/**
 * ProductID uniquely identifies a product.  Should be IMMUTABLE!!!
 */
 
 /**
 * @author Neeraj Fernandes
 * INFS 519
 * Fall 2016
 */
 
public class ProductID
{
	//variable declaration
    private final String epc;
    private final int serialNumber;
    
    public ProductID(String epc, int serialNumber)          //constructor
    {
        this.epc = epc;
        this.serialNumber = serialNumber;
    }
    
    @Override
    public int hashCode()
    {
    	int hash = 17;                                      //prime constant
    	hash = 31 * hash + epc.hashCode();
    	hash = 31 * hash + ((Integer)serialNumber).hashCode();
    	return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        
    	// Proper equals derived from attributes, ideally immutable
    	
    	
    	// 1. performance trick, typical to check super.equals also
    	if(obj == this) 
    	{
    		return true;
    	}
    	
    	// 2. type check, handles null
    	if(!(obj instanceof ProductID))
    	{
    		return false;	
    	}
    	
    	// 3. safe cast so can check each attribute
    	ProductID that = (ProductID)obj;
    	
    	// 4. check each attribute for equality
    	return (
    			this.epc.equals(that.epc)&&
    			this.serialNumber == that.serialNumber
    		   );
    }
    
    @Override
    public String toString()
    {
        return epc + ","+ serialNumber;
    }
}

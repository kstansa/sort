
/**
 * Class Item - describes an item with a size and an id number
 * 
 * @author Liam Geyer
 * @version v1.0.0
 */
public class Item
{
    protected double size;
    protected int id;
    
    /**
     * Constructs Item with given size and id
     * 
     * @param size size of the item
     * @param id item identifier
     */
    public Item(double size, int id)
    {
        this.size = size;
        this.id = id;
    }
    
    /**
     * Construcs Item with given id and random size between 0 and 1000
     * 
     * @param id item identifier
     */
    public Item(int id)
    {
        this(Math.random() * 1000, id);
    }
    
    /**
     * Construcs Item with given id and random size within specified range
     * 
     * @param id item identifier
     * @param lowerBound lower bound of size range
     * @param upperBound upper bound of size range
     * @throws IllegalArgumentException if upperBound is not greater than lowerBound
     */
    public Item(int lowerBound, int upperBound, int id)
    {
        this(Math.random() * (upperBound - lowerBound) - lowerBound, id);
        //throw exception if bounds are invalid
        if(upperBound < lowerBound)
        {
            throw new IllegalArgumentException("upperBound must be greater than lowerBound");
        }
    }
    
    public double getSize()
    {
        return size;
    }
    
    public double getRoundedSize()
    {
        return (int)(size * 100) / 100.0;
    }
    
    public int getId()
    {
        return id;
    }
    
    public String toString()
    {
        return "id: " + id + "\tsize: " + this.getRoundedSize();
    }
}

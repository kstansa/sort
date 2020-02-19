
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
     * Construcs Item with given id and random size
     * 
     * @param id item identifier
     */
    public Item(int id)
    {
        this(Math.random() * 1000, id);
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

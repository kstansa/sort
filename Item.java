
/**
 * Class Item - represents an immutable object with a value and an identifier
 * 
 * @author Liam Geyer
 * @version v1.0.0
 */
public class Item
{
    private double value;
    private int id;
    
    public Item(double value, int id)
    {
        this.value = value;
        this.id = id;
    }
    
    public Item(int maxValue, int id)
    {
        this.value = Math.random() * maxValue;
        this.id = id;
    }
    
    public double getValue()
    {
        return value;
    }
    
    public double getValueRounded()
    {
        return (int)(value * 100) / 100.0;
    }
    
    public int getId()
    {
        return id;
    }
    
    public String toString()
    {
        return "value: " + this.getValueRounded() + "\tid: " + this.getId();
    }
}

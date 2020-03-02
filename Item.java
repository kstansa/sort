
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

    /**
     * constructs an item with a set value and id
     * 
     * @param value value of new item to
     * @param id id of new item
     */
    public Item(double value, int id)
    {
        if(value < 0){throw new IllegalArgumentException("Value must be greater than or equal to 0");}
        this.value = value;
        this.id = id;
    }

    /**
     * constructs an item with a set is and a random value from 0 to maxValue
     * 
     * @param maxValue max possible value for the item
     * @param id id of new item
     */
    public Item(int maxValue, int id)
    {
        if(maxValue < 0){throw new IllegalArgumentException("maxValue must be greater than or equal to 0");}
        this.value = Math.random() * maxValue;
        this.id = id;
    }

    /**
     * returns the value of the item
     * 
     * @return value of item
     */
    public double getValue()
    {
        return value;
    }

    /**
     * returns the value of the item rounded to the nearest 100th
     * 
     * @return value of the item rounded to the nearest 100th
     */
    public double getValueRounded()
    {
        return (int)(value * 100) / 100.0;
    }

    /**
     * returns the item id
     * 
     * @return the item id
     */
    public int getId()
    {
        return id;
    }

    /**
     * returns a String representation of the item in the form of "value: (value)\tid: (id)"
     * 
     * @return String representation of the item in the form of "value: (value)\tid: (id)"
     */
    public String toString()
    {
        return "value: " + this.getValueRounded() + "\tid: " + this.getId();
    }
}

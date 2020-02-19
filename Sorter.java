import java.util.ArrayList;
/**
 * Abstract class Sorter - abstract class of an arr of Items to be sorted
 * 
 * @author Liam Geyer
 * @version v1.0.0
 */
public abstract class Sorter
{
    protected Item[] items;
    
    /**
     * Construct Sorter with n random items
     * 
     * @param n number of items
     * @throws IllegalArgumentException if n < 0
     */
    public Sorter(int n)
    {
        //throw exception if n < 0
        if(n < 0)
        {
            throw new IllegalArgumentException("n must be zero or greater");
        }
        this.items = new Item[n];
        //create items
        for(int i = 0; i < n; i++)
        {
            items[i] = new Item(i);
        }
    }
    
    /**
     * shuffles the locations of the items in the array
     */
    public void shuffle()
    {
        //create temp arr
        Item[] items = new Item[this.items.length];
        //create arrlist containing numbers
        ArrayList<Integer> newIndexes = new ArrayList<Integer>();
        for(int i = 0; i < items.length; i++)
        {
            newIndexes.add(i);
        }
        for(int index = 0; index < items.length; index++)
        {
            int newIndex = (int)(Math.random() * newIndexes.size());
            items[newIndexes.remove(newIndex)] = this.items[index];
        }
        this.items = items;
    }
    
    public abstract void sort();
    
    public String toString()
    {
        String output = "";
        for(Item item : items)
        {
            output += item.toString() + "\n";
        }
        //remove extra \n
        return output.substring(0, output.length() - 1);
    }
    
    public void print()
    {
        System.out.println(this.toString());
    }
    
    protected ArrayList<Item> getItemsArrayList()
    {
        ArrayList<Item> output = new ArrayList<Item>();
        for(Item item : items)
        {
            output.add(item);
        }
        return output;
    }
}

import java.util.ArrayList;
import java.awt.*;
import java.awt.image.*;
/**
 * Abstract class Sorter - abstract class of an arr of Items to be sorted
 * 
 * @author Liam Geyer
 * @version v1.0.0
 */
public abstract class Sorter
{
    public static final String[] SORT_TYPES = {"Selection", "Bubble", "Insertion"};
    
    protected Item[] items;
    protected int maxSize;
    
    /**
     * Construct Sorter with n random items
     * 
     * @param n number of items
     * @throws IllegalArgumentException if n < 0
     */
    public Sorter(int n)
    {
        this(n, 250);
    }
    
    public Sorter(int n, int size)
    {
        this.maxSize = size;
        //throw exception if n < 0
        if(n < 0)
        {
            throw new IllegalArgumentException("n must be zero or greater");
        }
        this.items = new Item[n];
        //create items
        for(int i = 0; i < n; i++)
        {
            items[i] = new Item(0, size, i);
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
    
    public abstract void sort(GUI g);
    
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
    
    protected void setItems(ArrayList<Item> items)
    {
        for(int i = 0; i < items.size(); i++)
        {
            this.items[i] = items.get(i);
        }
    }
    
    public BufferedImage display()
    {
        //create Image
        BufferedImage image = new BufferedImage(items.length, maxSize, BufferedImage.TYPE_INT_RGB);
        //for each x-coord
        for(int x = 0; x < image.getWidth(); x++)
        {
            //get column height
            int height = (int)(items[x].size);
            //for each pixel in height
            for(int y = image.getWidth() - 1; y >= height; y--)
            {
                image.setRGB(x, y, 255);
            }
        }
        return image;
    }
    
    public static BufferedImage display(Item[] items, int maxSize)
    {
        //create Image
        BufferedImage image = new BufferedImage(items.length, maxSize, BufferedImage.TYPE_INT_RGB);
        //for each x-coord
        for(int x = 0; x < image.getWidth(); x++)
        {
            //get column height
            int height = (int)(items[x].size);
            //for each pixel in height
            for(int y = image.getWidth() - 1; y >= height; y--)
            {
                image.setRGB(x, y, 255);
            }
        }
        return image;
    }
    
    public static BufferedImage display(ArrayList<Item> itemsList, int maxSize)
    {
        Item[] items = new Item[itemsList.size()];
        for(int i = 0; i < itemsList.size(); i++)
        {
            items[i] = itemsList.get(i);
        }
        //create Image
        BufferedImage image = new BufferedImage(items.length, maxSize, BufferedImage.TYPE_INT_RGB);
        //for each x-coord
        for(int x = 0; x < image.getWidth(); x++)
        {
            //get column height
            int height = (int)(items[x].size);
            //for each pixel in height
            for(int y = image.getWidth() - 1; y >= height; y--)
            {
                image.setRGB(x, y, 255);
            }
        }
        return image;
    }
    
    public int getMaxSize()
    {
        return this.maxSize;
    }
    
    public int getQuantity()
    {
        return this.items.length;
    }
}


/**
 * Abstract class Sorter - describes an array of items to be sorted
 * 
 * @author Liam Geyer
 * @version v1.0.0
 */
public abstract class Sorter
{
    public final int MAX_QUANTITY = Integer.MAX_VALUE;
    public final int MAX_VALUE = Integer.MAX_VALUE;
    public static final String[] SORT_TYPES = {"Insertion", "Bubble", "Selection", "Cocktail"};
    protected Item[] items;
    private int maxValue;
    private static double delay = 0.001; //delay between actions in seconds

    /**
     * Constructor for sorter
     * 
     * @param quantity the number of items to create
     * @param maxValue the upperBound for the values of the items
     * @throws IllegalArgumentException if quantity or maxValue are invalid values
     */
    public Sorter(int quantity, int maxValue)
    {
        //throw IllegalArgumentExeptions
        if(quantity <= 0 || quantity > MAX_QUANTITY){throw new IllegalArgumentException("Quantity must be greater than 0 and less than " + MAX_QUANTITY);}
        if(maxValue <= 0 || maxValue > MAX_VALUE){throw new IllegalArgumentException("Quantity must be greater than 0 and less than " + MAX_VALUE);}
        //initialize instance variables
        this.maxValue = maxValue;
        //initialize items
        this.items = new Item[quantity];
        //fill items
        for(int i = 0; i < quantity; i++)
        {
            items[i] = new Item(maxValue, i);
        }
    }

    //Getter methods
    /**
     * return items array
     * 
     * @return items array
     */
    public Item[] getItems()
    {
        return this.items;
    }

    /**
     * return number of items in items array
     * 
     * @return number of items in items array
     */
    public int getQuantity()
    {
        return this.items.length;
    }

    /**
     * return max value of items
     * 
     * @return max value of items in items
     */
    public int getMaxValue()
    {
        return this.maxValue;
    }

    /**
     * return global delay value
     * 
     * @return global delay value
     */
    public double getDelay()
    {
        return Sorter.delay;
    }

    public String toString()
    {
        String output = "";
        for(Item item : items)
        {
            output += item.toString() + "\n";
        }
        return output.substring(0, output.length() - 1);
    }

    public void print()
    {
        System.out.println(this.toString());
    }

    //Setter methods
    /**
     * Set global delay value
     * 
     * @param delay new delay
     * @throws IllegalArgumentException if delay is invalid
     */
    public static void setDelay(double delay)
    {
        if(delay < 0){throw new IllegalArgumentException("Quantity must be greater than or equal to 0");}
        Sorter.delay = delay;
    }

    //Mutator Methods
    /**
     * Sort the items array using the method described by the class
     */
    public abstract void sort();

    public abstract void sort(GUI gui);

    /**
     * Shuffles items array using the Fisher-Yates algorithm
     */
    public void shuffle()
    {
        //for all but the first item
        for(int i = items.length - 1; i > 0; i--)
        {
            //generate a random index
            int j = (int)(Math.random() * (i + 1));
            Item temp = items[i];
            items[i] = items[j];
            items[j] = temp;
        }
    }

    /**
     * Shuffles items array using the Fisher-Yates algorithm and updates the corrisponding GUI
     */
    public void shuffle(GUI gui)
    {
        //for all but the first item
        for(int i = items.length - 1; i > 0; i--)
        {
            gui.setSelector(i);
            //generate a random index
            int j = (int)(Math.random() * (i + 1));
            Item temp = items[i];
            items[i] = items[j];
            items[j] = temp;
            gui.updateBars();
            //check for abortFlag
            if(gui.getAbortFlag()){gui.abort(); return;}
        }
        //indicate to gui that process has ended
        gui.toggleProcess();
    }

    protected void move(int index, int newIndex)
    {
        //do nothing if index and newIndex are the same
        if(index == newIndex){return;}
        Item item = items[index];
        //if newIndex comes before index
        if(newIndex < index)
        {
            for(int i = index; i > newIndex; i--)
            {
                items[i] = items[i - 1];
            }
            items[newIndex] = item;
        }
        //else
        else
        {
            for(int i = index; i < newIndex; i++)
            {
                items[i] = items[i + 1];
            }
            items[newIndex] = item;
        }
    }
}
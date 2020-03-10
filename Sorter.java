import java.lang.reflect.*;
import java.util.concurrent.TimeUnit;

/**
 * Abstract class Sorter - describes an array of items to be sorted
 * 
 * @author Liam Geyer
 * @version v1.0.0
 */
public class Sorter
{
    public final int MAX_QUANTITY = Integer.MAX_VALUE;
    public final int MAX_VALUE = Integer.MAX_VALUE;
    public static final String[] SORT_TYPES = {"insertion", "bubble", "selection", "cocktail"};
    public String type;
    protected Item[] items;
    private GUI gui;
    private int maxValue;
    public long delay = 0;

    /**
     * Constructor for sorter
     * 
     * @param quantity the number of items to create
     * @param maxValue the upperBound for the values of the items
     * @param type default sort type
     * @param gui GUI that methods update. Set to a GhostGUI if set to null
     * @throws IllegalArgumentException if quantity or maxValue are invalid values
     */
    public Sorter(int quantity, int maxValue, String type)
    {
        //throw IllegalArgumentExeptions
        if(quantity <= 0 || quantity > MAX_QUANTITY){throw new IllegalArgumentException("Quantity must be greater than 0 and less than " + MAX_QUANTITY);}
        if(maxValue <= 0 || maxValue > MAX_VALUE){throw new IllegalArgumentException("Quantity must be greater than 0 and less than " + MAX_VALUE);}
        this.type = type;
        if(!isValidType()){throw new IllegalArgumentException("Invalid Type");}
        //initialize instance variables
        this.maxValue = maxValue;
        this.gui = new GhostGUI();
        //initialize items
        this.items = new Item[quantity];
        //fill items
        for(int i = 0; i < quantity; i++)
        {
            items[i] = new Item(maxValue, i);
        }
    }

    /*
     * Accessor methods
     */

    /**
     * returns items array
     * 
     * @return items array
     */
    public Item[] getItems()
    {
        return this.items;
    }

    /**
     * returns number of items in items array
     * 
     * @return number of items in items array
     */
    public int getQuantity()
    {
        return this.items.length;
    }

    /**
     * returns max value of items
     * 
     * @return max value of items in items
     */
    public int getMaxValue()
    {
        return this.maxValue;
    }

    /**
     * returns false if type is not valid
     * 
     * @return false if type is not valid
     */
    private boolean isValidType()
    {
        for(String value : SORT_TYPES)
        {
            if(value.equals(type)){return true;}
        }
        return false;
    }

    /**
     * returns a String representation of the item array in the form of a String representation of each item followed by a new line character with the exception of the last item
     * 
     * @return String representation of the item array
     */
    public String toString()
    {
        String output = "";
        for(Item item : items)
        {
            output += item.toString() + "\n";
        }
        return output.substring(0, output.length() - 1);
    }

    /**
     * pauses a process for the amount of time specified by delay
     */
    private void pause()
    {
        //         try
        //         {
        //             TimeUnit.MILLISECONDS.sleep(delay);
        //         }
        //         catch(InterruptedException e)
        //         {
        //             e.printStackTrace();
        //         }
    }

    /*
     * Mutator methods
     */

    /**
     * Sets the active gui
     * 
     * @param gui GUI to set the gui to
     */
    public void setGUI(GUI gui)
    {
        if(gui == null)
        {
            this.gui = new GhostGUI();
        }
        this.gui = gui;
    }
    
    /**
     * Shuffles items array using the Fisher-Yates algorithm
     */
    public void shuffle()
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

    /**
     * moves an item in the array from index to newIndex, objects as needed
     * 
     * @param index index of item to move
     * @param newIndex index to move item to
     * @throws ArrayIndexOutOfBoundsException if either index or newIndex are invalid indexes
     */
    private void move(int index, int newIndex)
    {
        //do nothing if index and newIndex are the same
        if(index == newIndex){return;}
        //throw exception if indexes are invalid
        if(index < 0 || index > items.length || newIndex < 0 || newIndex > items.length){throw new ArrayIndexOutOfBoundsException("index and newIndex must be valid indexes for the item array");}
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

    /**
     * sorts the sorter object using the algorithm defined by type and updates the param gui's graphic
     */
    public void sort()
    {
        Method method = null;
        try
        {
            Class<?> c = Class.forName("Sorter");
            method = c.getDeclaredMethod(type + "Sort");
        }
        catch(SecurityException e){e.printStackTrace();}
        catch(NoSuchMethodException e){e.printStackTrace();}
        catch(ClassNotFoundException e){e.printStackTrace();}
        try
        {
            method.invoke(this);
        }
        catch(IllegalArgumentException e){e.printStackTrace();}
        catch(IllegalAccessException e){e.printStackTrace();}
        catch(InvocationTargetException e){e.printStackTrace();}
    }

    /**
     * Find out of place item, then insert it into correct position
     */
    private void insertionSort()
    {
        //for each item
        for(int i = 1; i < items.length; i++)
        {
            int j = i - 1;
            //while the previous item is greater than the current item and j is an index, decrement j
            while(j != -1 && items[i].getValue() < items[j].getValue())
            {
                gui.setSelector(j);
                pause();
                j--;
            }
            //if j changed, move item at i to j + 1
            if(j != i - 1)
            {
                this.move(i, j + 1);
                gui.updateBars();
            }
            //check for abortFlag
            if(gui.getAbortFlag()){gui.abort(); return;}
        }
        //indicate to gui that process has ended
        gui.toggleProcess();
    }

    /**
     * Bubble Sort is the simplest sorting algorithm that works by repeatedly swapping the adjacent elements if they are in wrong order.
     */
    private void bubbleSort()
    {
        boolean sorted = false;
        while(!sorted)
        {
            sorted = true;
            //for each item pair
            for(int i = 0; i < this.items.length - 1; i++)
            {
                gui.setSelector(i);
                //if items are not in order, swap them and update sorted
                if(this.items[i].getValue() > this.items[i + 1].getValue())
                {
                    Item temp = this.items[i];
                    this.items[i] = this.items[i + 1];
                    this.items[i + 1] = temp;
                    sorted = false;
                    gui.updateBars();
                }
                //check for abortFlag
                if(gui.getAbortFlag()){gui.abort(); return;}
            }
        }
        //indicate to gui that process has ended
        gui.toggleProcess();
    }

    /**
     * The selection sort algorithm sorts an array by repeatedly finding the minimum element (considering ascending order) from unsorted part and putting it at the beginning.
     */
    private void selectionSort()
    {
        //for each item
        for(int i = 0; i < items.length; i++)
        {
            gui.setSelector(i);
            //initialize minimums
            double minValue = items[i].getValue();
            int minIndex = i;
            //for each unsorted item
            for(int j = i + 1; j < items.length; j++)
            {
                gui.setSelector(j);
                //update min value if item j is smaller
                if(items[j].getValue() < minValue)
                {
                    minValue = items[j].getValue();
                    minIndex = j;
                }
            }
            //move min item to i
            this.move(minIndex, i);
            gui.updateBars();
            //check for abortFlag
            if(gui.getAbortFlag()){gui.abort(); return;}
        }
        //indicate to gui that process has ended
        gui.toggleProcess();
    }

    /**
     * Each iteration of the algorithm is broken up into 2 stages:
     * 
     * The first stage loops through the array from left to right, just like the Bubble Sort. 
     * During the loop, adjacent items are compared and if value on the left is greater than the value on the right, then values are swapped. 
     * At the end of first iteration, largest number will reside at the end of the array.
     * 
     * The second stage loops through the array in opposite direction- starting from the item just before the most recently sorted item, and moving back to the start of the array. 
     * Here also, adjacent items are compared and are swapped if required.
     */
    private void cocktailSort()
    {
        boolean sorted = false;
        int start = 0;
        int end = this.items.length - 1;

        while(!sorted)
        {
            sorted = true;
            //forward pass
            for(int i = start; i < end; i++)
            {
                gui.setSelector(i);
                //if items are not in order, swap them and update sorted
                if(this.items[i].getValue() > this.items[i + 1].getValue())
                {
                    Item temp = this.items[i];
                    this.items[i] = this.items[i + 1];
                    this.items[i + 1] = temp;
                    sorted = false;
                    gui.updateBars();
                }
                //check for abortFlag
                if(gui.getAbortFlag()){gui.abort(); return;}
            }
            //end if sorted flag was not set
            if(sorted){return;}
            //decrease end
            end--;
            //reverse pass
            for(int i = end - 1; i >= start; i--)
            {
                gui.setSelector(i);
                //if items are not in order, swap them and update sorted
                if(this.items[i].getValue() > this.items[i + 1].getValue())
                {
                    Item temp = this.items[i];
                    this.items[i] = this.items[i + 1];
                    this.items[i + 1] = temp;
                    sorted = false;
                    gui.updateBars();
                }
                //check for abortFlag
                if(gui.getAbortFlag()){gui.abort(); return;}
            }
            //increase start
            start++;
        }
        //indicate to gui that process has ended
        gui.toggleProcess();
    }
}
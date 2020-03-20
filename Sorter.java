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
    public static final String[] SORT_TYPES = {"insertion", "bubble", "selection", "cocktail", "merge"};
    public String type;
    private ValuesArray values;
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
        gui = new GhostGUI();
        //initialize values
        values = new ValuesArray(quantity, maxValue);
    }

    /*
     * Accessor methods
     */

    /**
     * returns values array
     * 
     * @return valuess array
     */
    public double[] cloneValues()
    {
        return values.getArray();
    }

    /**
     * returns number of values in values array
     * 
     * @return number of values in values array
     */
    public int size()
    {
        return values.size();
    }

    /**
     * returns max value of the values
     * 
     * @return max value of values in values
     */
    public int getMaxValue()
    {
        return maxValue;
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
     * returns a String representation of the values array in the form of a String representation of each item followed by a new line character with the exception of the last item
     * 
     * @return String representation of the values array
     */
    public String toString()
    {
        return "Type:\t" + type + "\n" + values.toString();
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
     * Shuffles values array using the Fisher-Yates algorithm
     */
    public void shuffle()
    {
        //for all but the first item
        for(int i = size() - 1; i > 0; i--)
        {
            gui.setSelector(i);
            //generate a random index
            int j = (int)(Math.random() * (i + 1));
            values.swap(i, j);
            gui.updateBars();
            //check for abortFlag
            if(gui.getAbortFlag()){gui.abort(); return;}
        }
        //indicate to gui that process has ended
        gui.toggleProcess();
    }

    /**
     * merge two sub-arrays of values
     * 
     * @param lowerBound inclusive
     * @param midBound exclusive for first sub-array, inclusive for second sub-array
     * @param upperBoud exclusive
     */
    private void merge(int lowerBound, int midBound, int upperBound)
    {
        //check that bounds are valid TODO specify what about bounds is incorrect
        if(upperBound <= midBound || midBound <= lowerBound || lowerBound < 0 || upperBound > size()){throw new IllegalArgumentException("Invalid bounds");}
        //check for abort
        if(gui.getAbortFlag()){return;}

        //get indexes of sub-arrays
        int readIndex = midBound;
        int writeIndex = lowerBound;
        while(writeIndex < readIndex && readIndex < upperBound)
        {
            gui.setSelector(writeIndex);
            if(values.get(readIndex) < values.get(writeIndex))
            {
                values.move(readIndex, writeIndex);
                readIndex++;
                gui.updateBars();
            }
            writeIndex++;
            //check for abort
            if(gui.getAbortFlag()){return;}
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
        for(int i = 1; i < size(); i++)
        {
            int j = i - 1;
            //while the previous item is greater than the current item and j is an index, decrement j
            while(j != -1 && values.get(i) < values.get(j))
            {
                gui.setSelector(j);
                pause();
                j--;
            }
            //if j changed, move item at i to j + 1
            if(j != i - 1)
            {
                values.move(i, j + 1);
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
            for(int i = 0; i < size() - 1; i++)
            {
                gui.setSelector(i);
                //if items are not in order, swap them and update sorted
                if(values.get(i) > values.get(i + 1))
                {
                    values.swap(i, i + 1);
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
        for(int i = 0; i < size(); i++)
        {
            gui.setSelector(i);
            //initialize minimums
            double minValue = values.get(i);
            int minIndex = i;
            //for each unsorted item
            for(int j = i + 1; j < size(); j++)
            {
                gui.setSelector(j);
                //update min value if item j is smaller
                if(values.get(j) < minValue)
                {
                    minValue = values.get(j);
                    minIndex = j;
                }
            }
            //move min item to i
            values.move(minIndex, i);
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
        int end = size() - 1;

        while(!sorted)
        {
            sorted = true;
            //forward pass
            for(int i = start; i < end; i++)
            {
                gui.setSelector(i);
                //if items are not in order, swap them and update sorted
                if(values.get(i) > values.get(i + 1))
                {
                    values.swap(i, i + 1);
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
                if(values.get(i) > values.get(i + 1))
                {
                    values.swap(i, i + 1);
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

    private void mergeSort()
    {
        //perform helper for all of items
        mergeSortHelper(0, size());
        //check for abortFlag
        if(gui.getAbortFlag()){gui.abort(); return;}
        //indicate to gui that process has ended
        gui.toggleProcess();
    }

    private void mergeSortHelper(int lowerBound, int upperBound)
    {
        //if given section is longer than 1 and the process has not been aborted
        if(!gui.getAbortFlag() && lowerBound < upperBound - 1)
        {
            //find mid point
            int mid = (upperBound + lowerBound)/2;

            //sort both halves
            mergeSortHelper(lowerBound, mid);
            mergeSortHelper(mid, upperBound);

            //merge halves
            merge(lowerBound, mid, upperBound);
        }
    }
}
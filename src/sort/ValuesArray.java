package sort;
import java.util.*;

/**
 * Class ValuesArray - represents an array of randomly generated double values; after construction, values cannot be modified, added, or removed. Objects may only change their position in the array
 * 
 * @author Liam Geyer
 * @version v1.0.0
 */
public class ValuesArray
{
    private double[] values;
    private int quantity;
    
    /**
     * Sole constructor. Creates a double array of quantity values, all with a random value between 0 and maxValue
     * 
     * @param quantity size of the array of values
     * @param maxValue maximum value of an item in the array
     * @throws IllegalArgumentException if the quantity or size is less than or equal to 0
     */
    public ValuesArray(int quantity, int maxValue)
    {
        //check preconditions
        if(quantity < 1){throw new IllegalArgumentException("quantity must be greater than 0");}
        if(maxValue < 1){throw new IllegalArgumentException("maxValue must be greater than 0");}
        
        //set quantity
        this.quantity = quantity;
        
        //create array
        values = new double[quantity];
        
        //fill array
        Random rng = new Random();
        for(int i = 0; i < quantity; i++)
        {
            values[i] = rng.nextDouble() * maxValue;
        }
    }
    
    /*
     * Accessor Methods
     */
    
    /**
     * Returns the value at the given index
     * 
     * @param index index of value to return
     * @return value at the given index
     * @throws IllegalArgumentException if the index is not a valid index
     */
    public double get(int index)
    {
        //check preconditions
        if(index < 0 || index >= quantity){throw new IllegalArgumentException("index must be greater than or equal to 0 and less than quantity");}
        return values[index];
    }
    
    /**
     * Returns a copy of the values array
     * 
     * @return copy of values array
     */
    public double[] getArray()
    {
        return values.clone();
    }
    
    /**
     * Returns the number of values in the array
     * 
     * @returns quantity
     */
    public int size()
    {
        return quantity;
    }
    
    public String toString()
    {
        String output = "";
        for(double value : values)
        {
            output += "value:\t" + ((int)(value * 100) / 100.0) + "\n";
        }
        return output.substring(0, output.length() - 1);
    }
    
    /*
     * Mutators
     */
    
    /**
     * Swap the positions of the values at the given indexes
     * 
     * @param indexA index of first object
     * @param indexB index of second object
     */
    public void swap(int indexA, int indexB)
    {
        //do nothing if index and newIndex are the same
        if(indexA == indexB){return;}
        //check preconditions
        if(indexA < 0 || indexA >= quantity || indexB < 0 || indexB >= quantity){throw new IllegalArgumentException("indexes must be greater than or equal to 0 and less than quantity");}
        
        //swap values
        double temp = values[indexA];
        values[indexA] = values[indexB];
        values[indexB] = temp;
    }
    
    /**
     * Moves a value in the array from index to newIndex, shifting values as needed
     * 
     * @param index index of value to move
     * @param newIndex index to move value to
     * @throws ArrayIndexOutOfBoundsException if either index or newIndex are invalid indexes
     */
    public void move(int index, int newIndex)
    {
        //do nothing if index and newIndex are the same
        if(index == newIndex){return;}
        //check preconditions
        if(index < 0 || index > quantity || newIndex < 0 || newIndex > quantity){throw new ArrayIndexOutOfBoundsException("indexes must be greater than or equal to 0 and less than quantity");}
        double value = values[index];
        //if newIndex comes before index
        if(newIndex < index)
        {
            for(int i = index; i > newIndex; i--)
            {
                values[i] = values[i - 1];
            }
            values[newIndex] = value;
        }
        //else
        else
        {
            for(int i = index; i < newIndex; i++)
            {
                values[i] = values[i + 1];
            }
            values[newIndex] = value;
        }
    }
}

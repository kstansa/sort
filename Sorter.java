
/**
 * Abstract class Sorter - 
 * 
 * @author Liam Geyer
 * @version v1.0.0
 */
public abstract class Sorter
{
    private Item[] items;

    public Sorter(int quantity, int maxValue)
    {
        //initialize items
        this.items = new Item[quantity];
        //fill items
        for(int i = 0; i < quantity; i++)
        {
            items[i] = new Item(maxValue, i);
        }
    }

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
}

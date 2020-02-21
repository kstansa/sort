
/**
 * Class Bubble - Bubble Sort is the simplest sorting algorithm that works by repeatedly swapping the adjacent elements if they are in wrong order.
 * 
 * @author Liam Geyer
 * @version v1.0.0
 */
public class Bubble extends Sorter
{
    public Bubble(int n)
    {
        super(n);
        this.shuffle();
    }

    public Bubble(int n, int size)
    {
        super(n, size);
        this.shuffle();
    }

    public void sort()
    {
        boolean sorted = false;
        while(!sorted)
        {
            sorted = true;
            //for each item pair
            for(int i = 0; i < this.items.length - 1; i++)
            {
                //if items are not in order, swap them and update sorted
                if(this.items[i].size > this.items[i + 1].size)
                {
                    Item temp = this.items[i];
                    this.items[i] = this.items[i + 1];
                    this.items[i + 1] = temp;
                    sorted = false;
                }
            }
        }
    }

    public void sort(GUI g)
    {
        boolean sorted = false;
        while(!sorted)
        {
            sorted = true;
            //for each item pair
            for(int i = 0; i < this.items.length - 1; i++)
            {
                //if items are not in order, swap them and update sorted
                if(this.items[i].size > this.items[i + 1].size)
                {
                    Item temp = this.items[i];
                    this.items[i] = this.items[i + 1];
                    this.items[i + 1] = temp;
                    sorted = false;
                    //update graphics
                    g.updateFinal(items, this.maxSize);
                }
            }
        }
    }
}

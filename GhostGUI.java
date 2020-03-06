
/**
 * Class GhostGUI - A GUI where all public methods are overriden with methods that do nothing
 * 
 * @author Liam Geyer
 * @version v1.0.0
 */
public class GhostGUI extends GUI
{
    public GhostGUI()
    {
        super();
    }
    public void updateBars(){}
    public void setSelector(int index){}
    public boolean getAbortFlag(){return false;}
    public void toggleProcess(){}
    public void abort(){}
}

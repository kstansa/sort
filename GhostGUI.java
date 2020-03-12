
/**
 * Class GhostGUI - A GUI where all public methods are overriden with methods that do nothing and no instance variables are initialized
 * 
 * @author Liam Geyer
 * @version v1.0.0
 */
public class GhostGUI extends GUI
{
    /**
     * Sole constructor. Calls the GhostGUI constructor from GUI
     */
    public GhostGUI()
    {
        super(true);
    }
    
    /**
     * Does nothing
     */
    public void updateBars(){}
    
    /**
     * Does nothing
     */
    public void setSelector(int index){}
    
    /**
     * Returns false since to prevent methods with abort capabilities from aborting with a GhostGUI
     * 
     * @return false
     */
    public boolean getAbortFlag(){return false;}
    
    /**
     * Does nothing
     */
    public void toggleProcess(){}
    
    /**
     * Does nothing
     */
    public void abort(){}
}

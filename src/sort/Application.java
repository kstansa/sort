package sort;
/**
 * Class Application - Contains the main method that starts the application
 * 
 * @author Liam Geyer
 * @version v1.0.0
 */
public class Application
{
    private static boolean devArg = false;
    private static boolean verbArg = false;
    
    /**
     * Main method. Called to run application
     */
    public static void main(String[] args)
    {
        //check args
        for(int i = 0; i < args.length; i++)
        {
            if(args[i].matches("^-d((ev(eloper)?)|(ebug))?$")){devArg = true;}
            else if (args[i].matches("^-v(erb(ose)?)?$")){verbArg = true;}
            else{System.err.println(args[i] + " is not a valid argument");}
        }
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater
        (
            new Runnable()
            {
                public void run()
                {
                    GUI gui = new GUI(devArg, verbArg);
                }
            }
        );
    }
}

import javax.swing.JOptionPane;

/**
 * Class Application - Contains the main method that starts the application
 * 
 * @author Liam Geyer
 * @version v1.0.0
 */
public class Application
{
    public static void main(String[] args)
    {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater
        (
            new Runnable()
            {
                public void run()
                {
                    GUI gui = new GUI();
                }
            }
        );
    }
}

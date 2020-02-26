import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.PrintStream;

/**
 * Class GraphicsPanel -
 * 
 * @author Liam Geyer
 * @version v1.0.0
 */
public class GraphicsPanel extends JPanel implements ActionListener
{
    private int panels = 0;
    private JTabbedPane tabbedPane;
    private JButton nextButton;
    private JTextArea focused;

    public GraphicsPanel()
    {
        //TODO Proper Layout
        super(new GridLayout(1, 1));
        
        nextButton = new JButton("new tab");
        nextButton.setActionCommand("n");
        nextButton.addActionListener(this);

        tabbedPane = new JTabbedPane();

        addOutput();
        
        //Add the tabbed pane to this panel.
        add(tabbedPane);

        //The following line enables to use scrolling tabs.
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }

    public void addOutput()
    {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea();
        focused = textArea;
        
        panel.add(textArea, BorderLayout.CENTER);
        panel.add(nextButton, BorderLayout.NORTH);
        tabbedPane.addTab("Output " + panels++, panel);
    }
    
    public void setFocusedText(String str)
    {
        focused.setText(str);
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if(e.getActionCommand().equals("n"))
        {
            this.addOutput();
        }
        else
        {
            throw new IllegalArgumentException("shit");
        }
    }
}

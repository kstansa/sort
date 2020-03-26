/*
 * Created by JFormDesigner on Fri Mar 20 20:53:09 CDT 2020
 */

package GUI Generator;

import java.awt.*;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.border.*;

/**
 * @author Liam Geyer
 */
public class GUI extends JFrame
{
	public GUI()
	{
		initComponents();
	}

	private void initComponents()
	{
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Liam Geyer
		menuBar = new JMenuBar();
		file = new JMenu();
		edit = new JMenu();
		view = new JMenu();
		help = new JMenu();
		layeredPane1 = new JLayeredPane();
		graphicsPanel = new JPanel();
		panel1 = new JPanel();
		button8 = new JButton();

		//======== this ========
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== menuBar ========
		{

			//======== file ========
			{
				file.setText("File");
			}
			menuBar.add(file);

			//======== edit ========
			{
				edit.setText("Edit");
			}
			menuBar.add(edit);

			//======== view ========
			{
				view.setText("View");
			}
			menuBar.add(view);

			//======== help ========
			{
				help.setText("Help");
			}
			menuBar.add(help);
		}
		setJMenuBar(menuBar);

		//======== layeredPane1 ========
		{

			//======== graphicsPanel ========
			{
				graphicsPanel.setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(new javax
				.swing.border.EmptyBorder(0,0,0,0), "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn",javax.swing
				.border.TitledBorder.CENTER,javax.swing.border.TitledBorder.BOTTOM,new java.awt.
				Font("Dia\u006cog",java.awt.Font.BOLD,12),java.awt.Color.red
				),graphicsPanel. getBorder()));graphicsPanel. addPropertyChangeListener(new java.beans.PropertyChangeListener(){@Override
				public void propertyChange(java.beans.PropertyChangeEvent e){if("\u0062ord\u0065r".equals(e.getPropertyName(
				)))throw new RuntimeException();}});
				graphicsPanel.setLayout(new BorderLayout());
			}
			layeredPane1.add(graphicsPanel, JLayeredPane.DEFAULT_LAYER);
			graphicsPanel.setBounds(315, 135, 400, 396);

			//======== panel1 ========
			{
				panel1.setBorder(new EtchedBorder());

				//---- button8 ----
				button8.setText("text");

				GroupLayout panel1Layout = new GroupLayout(panel1);
				panel1.setLayout(panel1Layout);
				panel1Layout.setHorizontalGroup(
					panel1Layout.createParallelGroup()
						.addGroup(panel1Layout.createSequentialGroup()
							.addContainerGap()
							.addComponent(button8)
							.addContainerGap(187, Short.MAX_VALUE))
				);
				panel1Layout.setVerticalGroup(
					panel1Layout.createParallelGroup()
						.addGroup(panel1Layout.createSequentialGroup()
							.addContainerGap()
							.addComponent(button8)
							.addContainerGap(175, Short.MAX_VALUE))
				);
			}
			layeredPane1.add(panel1, JLayeredPane.DEFAULT_LAYER);
			panel1.setBounds(0, 0, 250, 210);
		}
		contentPane.add(layeredPane1, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Liam Geyer
	private JMenuBar menuBar;
	private JMenu file;
	private JMenu edit;
	private JMenu view;
	private JMenu help;
	private JLayeredPane layeredPane1;
	private JPanel graphicsPanel;
	private JPanel panel1;
	private JButton button8;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}

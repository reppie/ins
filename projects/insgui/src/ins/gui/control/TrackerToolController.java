package ins.gui.control;

import ins.gui.dialog.ConnectionDialog;
import ins.gui.model.Model;
import ins.gui.view.TrackerToolView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class TrackerToolController implements ActionListener{

	private Model model;
	private TrackerToolView view;
	
	public TrackerToolController(Model model, TrackerToolView view)
	{
		this.model = model;
		this.view = view;
	}

	public void actionPerformed(ActionEvent event) 
	{
		if(event.getSource() == view.connectButton)
		{
			ConnectionDialog dialog = new ConnectionDialog(model);
		}
		else if(event.getSource() == view.disconnectButton)
		{
			System.out.println(view.disconnectButton.getText());
		}
		else if(event.getSource() == view.reconnectButton)
		{
			System.out.println(view.reconnectButton.getText());
		}
		else if(event.getSource() == view.resetButton)
		{
			JOptionPane.showMessageDialog(null,
			    model.getName("RESET_WARNING_MSG"),
			    model.getName("RESET_WARNING_TITLE"),
			    JOptionPane.WARNING_MESSAGE);
		}
		else if(event.getSource() == view.perspectiveButton)
		{
			System.out.println(view.perspectiveButton.getText());
		}
		else if(event.getSource() == view.topButton)
		{
			System.out.println(view.topButton.getText());
		}
		else if(event.getSource() == view.sideButton)
		{
			System.out.println(view.sideButton.getText());
		}
		else if(event.getSource() == view.layoutButton1)
		{
			System.out.println(view.layoutButton1.getText());
		}
		else if(event.getSource() == view.layoutButton2)
		{
			System.out.println(view.layoutButton2.getText());
		}
		else if(event.getSource() == view.layoutButton3)
		{
			System.out.println(view.layoutButton3.getText());
		}
		else if(event.getSource() == view.layoutButton4)
		{
			System.out.println(view.layoutButton4.getText());
		}
	}
}

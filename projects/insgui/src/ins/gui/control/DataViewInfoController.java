package ins.gui.control;

import ins.gui.model.Model;
import ins.gui.view.DataView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class DataViewInfoController implements ActionListener{
	
	private Model model;
	private DataView view;
	
	public DataViewInfoController(Model model, DataView view)
	{
		this.model = model;
		this.view = view;
	}

	public void actionPerformed(ActionEvent event) 
	{
		if(event.getSource() == view.disconnectButton)
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
	}

}

package ins.gui.control;

import ins.gui.model.Model;
import ins.gui.view.TrackerMenuView;
import ins.gui.dialog.ConnectionDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class TrackerMenuController implements ActionListener{
	
	private Model model;
	private TrackerMenuView view;
	
	public TrackerMenuController(Model model, TrackerMenuView view)
	{
		this.model = model;
		this.view = view;
	}

	public void actionPerformed(ActionEvent event) 
	{
		if(event.getSource() == view.exitItem)
		{
			System.exit(0);
		}
		else if(event.getSource() == view.connectItem)
		{
			ConnectionDialog dialog = new ConnectionDialog(model);
		}
		else if(event.getSource() == view.disconnectItem)
		{
			System.out.println(view.disconnectItem.getText());
		}
		else if(event.getSource() == view.reconnectItem)
		{
			System.out.println(view.reconnectItem.getText());
		}
		else if(event.getSource() == view.resetItem)
		{
			JOptionPane.showMessageDialog(null,
				    model.getName("RESET_WARNING_MSG"),
				    model.getName("RESET_WARNING_TITLE"),
				    JOptionPane.WARNING_MESSAGE);
		}
		else if(event.getSource() == view.perspectiveItem)
		{
			System.out.println(view.perspectiveItem.getText());
		}
		else if(event.getSource() == view.sideViewitem)
		{
			System.out.println(view.sideViewitem.getText());
		}
		else if(event.getSource() == view.topViewItem)
		{
			System.out.println(view.topViewItem.getText());
		}
		else if(event.getSource() == view.showGridItem)
		{
			System.out.println(view.showGridItem.getText());
		}
		else if(event.getSource() == view.layoutItem1)
		{
			System.out.println(view.layoutItem1.getText());
		}
		else if(event.getSource() == view.layoutItem2)
		{
			System.out.println(view.layoutItem2.getText());
		}
		else if(event.getSource() == view.layoutItem3)
		{
			System.out.println(view.layoutItem3.getText());
		}
		else if(event.getSource() == view.layoutItem4)
		{
			System.out.println(view.layoutItem4.getText());
		}
		else if(event.getSource() == view.settingsItem)
		{
			System.out.println(view.settingsItem.getText());
		}
		else if(event.getSource() == view.modeItem1)
		{
			System.out.println(view.modeItem1.getText());
		}
		else if(event.getSource() == view.modeItem2)
		{
			System.out.println(view.modeItem2.getText());
		}
		else if(event.getSource() == view.helpItem)
		{
			System.out.println(view.helpItem.getText());
		}
		else if(event.getSource() == view.aboutItem)
		{
			System.out.println(view.aboutItem.getText());
		}
	}

}

package ins.gui.control;

import ins.gui.model.Model;
import ins.gui.view.SideView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SideViewController implements ActionListener{

	private Model model;
	private SideView view;
	
	public SideViewController(Model model, SideView view)
	{
		this.model = model;
		this.view = view;
	}
	
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == view.sideSelector)
		{
			System.out.println(view.sideSelector.getSelectedItem());
		}
		else if(event.getSource() == view.zoomIn)
		{
			System.out.println(view.zoomIn.getText());
		}
		else if(event.getSource() == view.zoomOut)
		{
			System.out.println(view.zoomOut.getText());
		}
	}

}

package ins.gui.control;

import ins.gui.model.Model;
import ins.gui.view.TopView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TopViewController implements ActionListener{

	private Model model;
	private TopView view;
	
	public TopViewController(Model model, TopView view)
	{
		this.model = model;
		this.view = view;
	}
	
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == view.levelSelector)
		{
			System.out.println(view.levelSelector.getSelectedItem());
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

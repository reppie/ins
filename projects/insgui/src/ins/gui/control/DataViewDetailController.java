package ins.gui.control;

import ins.gui.model.Model;
import ins.gui.view.DataView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DataViewDetailController implements ActionListener{
	
	private Model model;
	private DataView view;
	
	public DataViewDetailController(Model model, DataView view)
	{
		this.model = model;
		this.view = view;
	}

	public void actionPerformed(ActionEvent event) 
	{
		if(event.getSource() == view.refreshRateCombo)
		{
			System.out.println(view.refreshRateCombo.getSelectedItem());
		}
	}

}

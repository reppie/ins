package ins.gui.control;

import ins.gui.model.Model;
import ins.gui.view.PerspectiveView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PerspectiveViewController implements ActionListener{

	private Model model;
	private PerspectiveView view;
	
	public PerspectiveViewController(Model model, PerspectiveView view)
	{
		this.model = model;
		this.view = view;
	}
	
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == view.moveDown)
		{
			System.out.println(view.moveDown.getText());
		}
		else if(event.getSource() == view.moveLeft)
		{
			System.out.println(view.moveLeft.getText());
		}
		else if(event.getSource() == view.moveRight)
		{
			System.out.println(view.moveRight.getText());
		}
		else if(event.getSource() == view.moveUp)
		{
			System.out.println(view.moveUp.getText());
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

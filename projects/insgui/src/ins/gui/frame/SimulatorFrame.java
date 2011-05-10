package ins.gui.frame;

import ins.gui.model.SimulatorModel;

import javax.swing.JFrame;

public class SimulatorFrame extends JFrame{

	public SimulatorModel model;
	
	public SimulatorFrame(SimulatorModel model)
	{
		this.model = model;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

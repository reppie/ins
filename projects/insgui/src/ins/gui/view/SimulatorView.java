package ins.gui.view;

import ins.gui.lib.IModelSetter;
import ins.gui.model.Model;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

public class SimulatorView extends JPanel implements IModelSetter, Observer{

	public Model model;

	@Override
	public void setModel(Model model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}

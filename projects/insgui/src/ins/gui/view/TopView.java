package ins.gui.view;

import ins.gui.control.TopViewController;
import ins.gui.lib.GridBagHelper;
import ins.gui.lib.IConstrained;
import ins.gui.lib.IIConLoader;
import ins.gui.lib.IModelSetter;
import ins.gui.model.Model;

import java.awt.GridBagConstraints;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TopView extends JPanel implements IModelSetter, IConstrained, IIConLoader, Observer{

	public Model model;
	
	private JLabel selectorDesc;
	public JComboBox levelSelector;
	public JButton zoomIn;
	public JButton zoomOut;
	
	private TopViewController controller;
	
	public TopView()
	{
		setBorder(BorderFactory.createTitledBorder("TopView"));
	}

	public void setModel(Model model) {
		this.model = model;
		controller = new TopViewController(model, this);
		createComponents();
	}

	public GridBagConstraints getConstraints() {
		GridBagHelper gridBagHelper = new GridBagHelper(model);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = model.getIntValue("TOP_VIEW_GRIDX");
		constraints.gridy = model.getIntValue("TOP_VIEW_GRIDY");
		constraints.gridheight = model.getIntValue("TOP_VIEW_GRIDHEIGHT");
		constraints.gridwidth = model.getIntValue("TOP_VIEW_GRIDWIDTH");
		constraints.weightx = model.getDoubleValue("TOP_VIEW_WEIGHTX");
		constraints.weighty = model.getDoubleValue("TOP_VIEW_WEIGHTY");
		constraints.anchor = gridBagHelper.getGridBagConst("TOP_VIEW_ANCHOR");
		constraints.fill = gridBagHelper.getGridBagConst("TOP_VIEW_FILL");
		return constraints;
	}
	
	private void createComponents()
	{
		selectorDesc = new JLabel(model.getName("TOP_VIEW_LEVEL_SELECTOR_DESC"));
		levelSelector = new JComboBox();
		levelSelector.insertItemAt(model.getName("TOP_VIEW_LEVEL_SELECTOR_DEFAULT"), 0);
		levelSelector.setSelectedIndex(0);
		
		zoomIn = new JButton(new ImageIcon(loadIcon("ZOOM_IN_ICON")));
		zoomOut = new JButton(new ImageIcon(loadIcon("ZOOM_OUT_ICON")));
		
		add(selectorDesc);
		add(levelSelector);
		add(zoomIn);
		add(zoomOut);
		
		levelSelector.addActionListener(controller);
		zoomIn.addActionListener(controller);
		zoomOut.addActionListener(controller);
	}

	public String loadIcon(String name) 
	{
		String path = model.getStringValue("ICONPATH").replace("/", File.separator);
		String fullPath = path+model.getStringValue(name);
		return fullPath;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}

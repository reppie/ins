package ins.gui.view;

import ins.gui.control.SideViewController;
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

public class SideView extends JPanel implements IModelSetter, IConstrained, IIConLoader, Observer{

	public Model model;
	
	private JLabel selectorDesc;
	public JComboBox sideSelector;
	public JButton zoomIn;
	public JButton zoomOut;
	
	private SideViewController controller;
	
	public SideView()
	{
		setBorder(BorderFactory.createTitledBorder("SideView"));
	}

	public void setModel(Model model) {
		this.model = model;
		controller = new SideViewController(model, this);
		createComponents();
	}

	public GridBagConstraints getConstraints() 
	{
		GridBagHelper gridBagHelper = new GridBagHelper(model);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = model.getIntValue("SIDE_VIEW_GRIDX");
		constraints.gridy = model.getIntValue("SIDE_VIEW_GRIDY");
		constraints.gridheight = model.getIntValue("SIDE_VIEW_GRIDHEIGHT");
		constraints.gridwidth = model.getIntValue("SIDE_VIEW_GRIDWIDTH");
		constraints.weightx = model.getDoubleValue("SIDE_VIEW_WEIGHTX");
		constraints.weighty = model.getDoubleValue("SIDE_VIEW_WEIGHTY");
		constraints.anchor = gridBagHelper.getGridBagConst("SIDE_VIEW_ANCHOR");
		constraints.fill = gridBagHelper.getGridBagConst("SIDE_VIEW_FILL");
		return constraints;
	}
	
	private void createComponents()
	{
		selectorDesc = new JLabel(model.getName("SIDE_VIEW_SIDE_SELECTOR_DESC"));
		sideSelector = new JComboBox();
		sideSelector.insertItemAt(model.getName("SIDE_VIEW_SIDE_SELECTOR_VALUE1"), 0);
		sideSelector.insertItemAt(model.getName("SIDE_VIEW_SIDE_SELECTOR_VALUE2"), 1);
		sideSelector.setSelectedIndex(0);
		
		zoomIn = new JButton(new ImageIcon(loadIcon("ZOOM_IN_ICON")));
		zoomOut = new JButton(new ImageIcon(loadIcon("ZOOM_OUT_ICON")));
		add(selectorDesc);
		add(sideSelector);
		add(zoomIn);
		add(zoomOut);
		
		sideSelector.addActionListener(controller);
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

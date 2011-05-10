package ins.gui.view;

import java.awt.GridBagConstraints;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import ins.gui.control.PerspectiveViewController;
import ins.gui.lib.GridBagHelper;
import ins.gui.lib.IConstrained;
import ins.gui.lib.IIConLoader;
import ins.gui.lib.IModelSetter;
import ins.gui.model.Model;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PerspectiveView extends JPanel implements IModelSetter, IConstrained, IIConLoader, Observer{

	public Model model;
	
	public JButton moveLeft;
	public JButton moveRight;
	public JButton moveUp;
	public JButton moveDown;
	public JButton zoomIn;
	public JButton zoomOut;
	
	private PerspectiveViewController controller;
	
	public PerspectiveView()
	{
		setBorder(BorderFactory.createTitledBorder("PerspectiveView"));
	}

	public void setModel(Model model) {
		this.model = model;
		controller = new PerspectiveViewController(model, this);
		createComponents();
	}

	public GridBagConstraints getConstraints() {
		GridBagHelper gridBagHelper = new GridBagHelper(model);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = model.getIntValue("PERSPECTIVE_VIEW_GRIDX");
		constraints.gridy = model.getIntValue("PERSPECTIVE_VIEW_GRIDY");
		constraints.gridheight = model.getIntValue("PERSPECTIVE_VIEW_GRIDHEIGHT");
		constraints.gridwidth = model.getIntValue("PERSPECTIVE_VIEW_GRIDWIDTH");
		constraints.weightx = model.getDoubleValue("PERSPECTIVE_VIEW_WEIGHTX");
		constraints.weighty = model.getDoubleValue("PERSPECTIVE_VIEW_WEIGHTY");
		constraints.anchor = gridBagHelper.getGridBagConst("PERSPECTIVE_VIEW_ANCHOR");
		constraints.fill = gridBagHelper.getGridBagConst("PERSPECTIVE_VIEW_FILL");
		return constraints;
	}
	
	private void createComponents()
	{
		moveLeft = new JButton(new ImageIcon(loadIcon("LEFT_ICON")));
		moveRight = new JButton(new ImageIcon(loadIcon("RIGHT_ICON")));
		moveUp = new JButton(new ImageIcon(loadIcon("UP_ICON")));
		moveDown = new JButton(new ImageIcon(loadIcon("DOWN_ICON")));
		zoomIn = new JButton(new ImageIcon(loadIcon("ZOOM_IN_ICON")));
		zoomOut = new JButton(new ImageIcon(loadIcon("ZOOM_OUT_ICON")));
		
		add(moveLeft);
		add(moveRight);
		add(moveUp);
		add(moveDown);
		add(zoomIn);
		add(zoomOut);
		
		moveLeft.addActionListener(controller);
		moveRight.addActionListener(controller);
		moveUp.addActionListener(controller);
		moveDown.addActionListener(controller);
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

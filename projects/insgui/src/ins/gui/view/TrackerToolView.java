package ins.gui.view;

import ins.gui.control.TrackerToolController;
import ins.gui.lib.GridBagHelper;
import ins.gui.lib.IConstrained;
import ins.gui.lib.IModelSetter;
import ins.gui.lib.IIConLoader;
import ins.gui.model.Model;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;


public class TrackerToolView extends JPanel implements IModelSetter, Observer, IConstrained, IIConLoader{

	public Model model;
	private JToolBar bar;
	private TrackerToolController controller;
	
	public JButton connectButton;
	public JButton disconnectButton;
	public JButton reconnectButton;
	public JButton resetButton;
	
	public JToggleButton perspectiveButton;
	public JToggleButton topButton;
	public JToggleButton sideButton;
	public JToggleButton layoutButton1;
	public JToggleButton layoutButton2;
	public JToggleButton layoutButton3;
	public JToggleButton layoutButton4;
	
	private ArrayList<JComponent> connectionButtons;
	private ArrayList<JComponent> viewButtons;
	private ArrayList<JComponent> layoutButtons;
	
	public TrackerToolView()
	{
		super(new BorderLayout());
		bar = new JToolBar();
		bar.setFloatable(false);
		connectionButtons = new ArrayList<JComponent>();
		viewButtons = new ArrayList<JComponent>();
		layoutButtons = new ArrayList<JComponent>();
	}
	
	private void createButtons()
	{
		connectButton = new JButton(new ImageIcon(loadIcon("CONNECT_BUTTON_ICON")));
		disconnectButton = new JButton(new ImageIcon(loadIcon("DISCONNECT_BUTTON_ICON")));
		reconnectButton = new JButton(new ImageIcon(loadIcon("RECONNECT_BUTTON_ICON")));
		resetButton = new JButton(new ImageIcon(loadIcon("RESET_BUTTON_ICON")));
		perspectiveButton = new JToggleButton(model.getName("PERSPECTIVE_BUTTON_NAME"));
		topButton = new JToggleButton(model.getName("TOP_BUTTON_NAME"));
		sideButton = new JToggleButton(model.getName("SIDE_BUTTON_NAME"));
		layoutButton1 = new JToggleButton(new ImageIcon(loadIcon("LAYOUT1_BUTTON_ICON")));
		layoutButton2 = new JToggleButton(new ImageIcon(loadIcon("LAYOUT2_BUTTON_ICON")));
		layoutButton3 = new JToggleButton(new ImageIcon(loadIcon("LAYOUT3_BUTTON_ICON")));
		layoutButton4 = new JToggleButton(new ImageIcon(loadIcon("LAYOUT4_BUTTON_ICON")));
	}
	
	private void setAltText()
	{
		connectButton.setToolTipText(model.getName("CONNECT_BUTTON_ALT"));
		disconnectButton.setToolTipText(model.getName("DISCONNECT_BUTTON_ALT"));
		reconnectButton.setToolTipText(model.getName("RECONNECT_BUTTON_ALT"));
		resetButton.setToolTipText(model.getName("RESET_BUTTON_ALT"));
		perspectiveButton.setToolTipText(model.getName("PERSPECTIVE_BUTTON_ALT"));
		topButton.setToolTipText(model.getName("TOP_BUTTON_ALT"));
		sideButton.setToolTipText(model.getName("SIDE_BUTTON_ALT"));
		layoutButton1.setToolTipText(model.getName("LAYOUT1_BUTTON_ALT"));
		layoutButton2.setToolTipText(model.getName("LAYOUT2_BUTTON_ALT"));
		layoutButton3.setToolTipText(model.getName("LAYOUT3_BUTTON_ALT"));
		layoutButton4.setToolTipText(model.getName("LAYOUT4_BUTTON_ALT"));
	}
	
	private void gatherButtons()
	{
		connectionButtons.add(connectButton);
		connectionButtons.add(disconnectButton);
		//buttons.add(reconnectButton);
		connectionButtons.add(resetButton);
		viewButtons.add(perspectiveButton);
		viewButtons.add(topButton);
		viewButtons.add(sideButton);
		layoutButtons.add(layoutButton1);
		layoutButtons.add(layoutButton2);
		layoutButtons.add(layoutButton3);
		layoutButtons.add(layoutButton4);
	}
	
	private void setListeners()
	{
		connectButton.addActionListener(controller);
		disconnectButton.addActionListener(controller);
		reconnectButton.addActionListener(controller);
		resetButton.addActionListener(controller);
		perspectiveButton.addActionListener(controller);
		topButton.addActionListener(controller);
		sideButton.addActionListener(controller);
		layoutButton1.addActionListener(controller);
		layoutButton2.addActionListener(controller);
		layoutButton3.addActionListener(controller);
		layoutButton4.addActionListener(controller);
	}
	
	private void attachButtons()
	{
		
		for(JComponent button: connectionButtons)
		{
			bar.add(button);
		}
		bar.addSeparator();
		for(JComponent button: viewButtons)
		{
			bar.add(button);
		}
		bar.addSeparator();
		for(JComponent button: layoutButtons)
		{
			bar.add(button);
		}
		add(new JSeparator(JSeparator.HORIZONTAL));
	}
	
	private void buildToolbar()
	{
		createButtons();
		gatherButtons();
		attachButtons();
		setAltText();
		add(bar, BorderLayout.NORTH);
	}

	public void setModel(Model model) {
		this.model = model;
		buildToolbar();
		controller = new TrackerToolController(model, this);
		setListeners();
	}

	public GridBagConstraints getConstraints() {
		GridBagHelper gridBagHelper = new GridBagHelper(model);
		GridBagConstraints constraints = new GridBagConstraints(
				model.getIntValue("TRACKER_TOOL_VIEW_GRIDX"),
				model.getIntValue("TRACKER_TOOL_VIEW_GRIDY"),
				model.getIntValue("TRACKER_TOOL_VIEW_GRIDWIDTH"),
				model.getIntValue("TRACKER_TOOL_VIEW_GRIDHEIGHT"),
				model.getDoubleValue("TRACKER_TOOL_VIEW_WEIGHTX"),
				model.getDoubleValue("TRACKER_TOOL_VIEW_WEIGHTY"),
				gridBagHelper.getGridBagConst("TRACKER_TOOL_VIEW_ANCHOR"),
				gridBagHelper.getGridBagConst("TRACKER_TOOL_VIEW_FILL"),
				new Insets(0, 0, 0, 0),
				model.getIntValue("TRACKER_TOOL_VIEW_PADX"),
				model.getIntValue("TRACKER_TOOL_VIEW_PADY"));
		return constraints;
	}

	public void update(Observable o, Object arg) 
	{
		
	}

	public String loadIcon(String name) 
	{
		String path = model.getStringValue("ICONPATH").replace("/", File.separator);
		String fullPath = path+model.getStringValue(name);
		return fullPath;
	}
}

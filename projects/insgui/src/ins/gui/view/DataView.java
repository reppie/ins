package ins.gui.view;

import ins.gui.control.DataViewDetailController;
import ins.gui.control.DataViewInfoController;
import ins.gui.lib.GridBagHelper;
import ins.gui.lib.IConstrained;
import ins.gui.lib.IIConLoader;
import ins.gui.lib.IModelSetter;
import ins.gui.model.Model;
import ins.gui.model.TrackerModel;
import ins.sunspot.Spot;

import java.awt.GridBagConstraints;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

public class DataView extends JTabbedPane implements IConstrained, Observer, IModelSetter, IIConLoader{

	public Model model;
	
	private JPanel infoPanel;
	private JPanel detailPanel;
	
	private DataViewDetailController detailController;
	private DataViewInfoController infoController;
	
	//Info tab labels
	private JLabel nameLabel;
	private JLabel numberLabel;
	private JLabel connectionLabel;
	private JLabel positionLabel;
	private JLabel velocityLabel;
	private JLabel accelerationLabel;
	private JLabel statusLabel;
	private JLabel batteryLabel;
	
	public JButton disconnectButton;
	public JButton reconnectButton;
	public JButton resetButton;
	
	private JScrollPane scrollArea;
	private JTextArea rawDataField;
	private JLabel rawDataLabel;
	private JLabel refreshRateLabel;
	public JComboBox refreshRateCombo;
	
	public DataView()
	{
		infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.PAGE_AXIS));
		
		detailPanel = new JPanel();
		detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.PAGE_AXIS));
	}

	public void update(Observable arg0, Object arg1) {
		displaySpotInfo();
	}

	public GridBagConstraints getConstraints() {
		GridBagHelper gridBagHelper = new GridBagHelper(model);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = model.getIntValue("DATA_VIEW_GRIDX");
		constraints.gridy = model.getIntValue("DATA_VIEW_GRIDY");
		constraints.gridheight = model.getIntValue("DATA_VIEW_GRIDHEIGHT");
		constraints.gridwidth = model.getIntValue("DATA_VIEW_GRIDWIDTH");
		constraints.weightx = model.getDoubleValue("DATA_VIEW_WEIGHTX");
		constraints.weighty = model.getDoubleValue("DATA_VIEW_WEIGHTY");
		constraints.anchor = gridBagHelper.getGridBagConst("DATA_VIEW_ANCHOR");
		constraints.fill = gridBagHelper.getGridBagConst("DATA_VIEW_FILL");
		return constraints;
	}

	public void setModel(Model model) {
		this.model = model;
		detailController = new DataViewDetailController(model, this);
		infoController = new DataViewInfoController(model, this);
		attachTabs();
		createInfoComponents();
		attachInfoComponents();
		createDetailComponents();
		setListeners();
	}
	
	private void attachTabs()
	{
		addTab(model.getName("DATA_VIEW_INFO_TAB_NAME"),infoPanel);
		addTab(model.getName("DATA_VIEW_DETAIL_TAB_NAME"),detailPanel);
	}
	
	private void createInfoComponents()
	{
		  nameLabel = new JLabel(model.getName("DATA_VIEW_NAME_LABEL_BASE"));
		  numberLabel = new JLabel(model.getName("DATA_VIEW_NUMBER_LABEL_BASE"));
		  connectionLabel = new JLabel(model.getName("DATA_VIEW_CONNECTION_LABEL_BASE"));
		  positionLabel = new JLabel(model.getName("DATA_VIEW_POSITION_LABEL_BASE"));
		  velocityLabel = new JLabel(model.getName("DATA_VIEW_VELOCITY_LABEL_BASE"));
		  accelerationLabel = new JLabel(model.getName("DATA_VIEW_ACCELERATION_LABEL_BASE"));
		  statusLabel = new JLabel(model.getName("DATA_VIEW_STATUS_LABEL_BASE"));
		  batteryLabel = new JLabel(model.getName("DATA_VIEW_BATTERY_LABEL_BASE"));
		
		  disconnectButton = new JButton(new ImageIcon(loadIcon("DISCONNECT_BUTTON_ICON")));
		  reconnectButton = new JButton(new ImageIcon(loadIcon("RECONNECT_BUTTON_ICON")));
		  resetButton = new JButton(new ImageIcon(loadIcon("RESET_BUTTON_ICON")));
	}
	
	private void attachInfoComponents()
	{
		infoPanel.add(nameLabel);
		infoPanel.add(numberLabel);
		infoPanel.add(connectionLabel);
		infoPanel.add(positionLabel);
		infoPanel.add(velocityLabel);
		infoPanel.add(accelerationLabel);
		infoPanel.add(statusLabel);
		infoPanel.add(batteryLabel);
		infoPanel.add(new JSeparator(JSeparator.HORIZONTAL));
		infoPanel.add(resetButton);
		infoPanel.add(disconnectButton);
		//infoPanel.add(reconnectButton);
	}
	
	private void createDetailComponents() {

		rawDataLabel = new JLabel(
				model.getName("DATA_VIEW_DETAIL_TAB_RAW_DATA_LABEL_NAME"));
		detailPanel.add(rawDataLabel);
		rawDataField = new JTextArea(
				model.getIntValue("DATA_VIEW_DETAILS_TAB_RAW_DATA_FIELD_ROWS"),
				model.getIntValue("DATA_VIEW_DETAILS_TAB_RAW_DATA_FIELD_COLS"));
		rawDataField.setEditable(false);
		scrollArea = new JScrollPane(rawDataField);
		detailPanel.add(scrollArea);
		refreshRateLabel = new JLabel(model.getName("DATA_VIEW_DETAIL_TAB_REFRESH_RATE_LABEL_NAME"));
		detailPanel.add(refreshRateLabel);
		refreshRateCombo = new JComboBox();
		detailPanel.add(refreshRateCombo);
	}
	
	private void setListeners()
	{
		refreshRateCombo.addActionListener(detailController);
		
		reconnectButton.addActionListener(infoController);
		disconnectButton.addActionListener(infoController);
		resetButton.addActionListener(infoController);
	}

	public String loadIcon(String name) 
	{
		String path = model.getStringValue("ICONPATH").replace("/", File.separator);
		String fullPath = path+model.getStringValue(name);
		return fullPath;
	}
	
	public void displaySpotInfo()
	{
		resetSpotInfo();
		
		Spot spot = ((TrackerModel)model).getSelectedSpot();
		nameLabel.setText(model.getName("DATA_VIEW_NAME_LABEL_BASE")+spot.name);
		numberLabel.setText(model.getName("DATA_VIEW_NUMBER_LABEL_BASE")+spot.id);
		connectionLabel.setText(model.getName("DATA_VIEW_CONNECTION_LABEL_BASE")+spot.connectionPort);
		positionLabel.setText(model.getName("DATA_VIEW_POSITION_LABEL_BASE")+spot.position);
		velocityLabel.setText(model.getName("DATA_VIEW_VELOCITY_LABEL_BASE")+spot.velocity);
		accelerationLabel.setText(model.getName("DATA_VIEW_ACCELERATION_LABEL_BASE")+spot.acceleration);
		statusLabel.setText(model.getName("DATA_VIEW_STATUS_LABEL_BASE")+spot.status);
		batteryLabel.setText(model.getName("DATA_VIEW_BATTERY_LABEL_BASE")+spot.batteryStatus);
	}
	
	private void resetSpotInfo()
	{
		nameLabel.setText(model.getName("DATA_VIEW_NAME_LABEL_BASE"));
		numberLabel.setText(model.getName("DATA_VIEW_NUMBER_LABEL_BASE"));
		connectionLabel.setText(model.getName("DATA_VIEW_CONNECTION_LABEL_BASE"));
		positionLabel.setText(model.getName("DATA_VIEW_POSITION_LABEL_BASE"));
		velocityLabel.setText(model.getName("DATA_VIEW_VELOCITY_LABEL_BASE"));
		accelerationLabel.setText(model.getName("DATA_VIEW_ACCELERATION_LABEL_BASE"));
		statusLabel.setText(model.getName("DATA_VIEW_STATUS_LABEL_BASE"));
		batteryLabel.setText(model.getName("DATA_VIEW_BATTERY_LABEL_BASE"));
	}

}

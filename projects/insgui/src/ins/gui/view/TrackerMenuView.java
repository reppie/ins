package ins.gui.view;

import ins.gui.control.TrackerMenuController;
import ins.gui.lib.IModelSetter;
import ins.gui.model.Model;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

public class TrackerMenuView extends JMenuBar implements IModelSetter, Observer{

	public Model model;
	private TrackerMenuController controller;
	
	public JMenuBar menuBar;
	//Menus
	private JMenu fileMenu;
	private JMenu actionMenu;
	private JMenu viewMenu;
	private JMenu toolsMenu;
	private JMenu helpMenu;
	private JMenu layoutSubMenu;
	private JMenu modeSubMenu;
	
	//Menu items
	public JMenuItem openItem;
	public JMenuItem exitItem;
	
	public JMenuItem connectItem;
	public JMenuItem disconnectItem;
	public JMenuItem reconnectItem;
	public JMenuItem resetItem;
	
	public JCheckBoxMenuItem perspectiveItem;
	public JCheckBoxMenuItem sideViewitem;
	public JCheckBoxMenuItem topViewItem;
	public JCheckBoxMenuItem showGridItem;
	//Submenu items
	public JRadioButtonMenuItem  layoutItem1;
	public JRadioButtonMenuItem  layoutItem2;
	public JRadioButtonMenuItem  layoutItem3;
	public JRadioButtonMenuItem  layoutItem4;
	
	//Menu items again
	public JMenuItem settingsItem;
	public JMenuItem modeItem1;
	public JMenuItem modeItem2;
	public JMenuItem helpItem;
	public JMenuItem aboutItem;
	
	public TrackerMenuView()
	{
	}
	
	private void buildMenu()
	{
		menuBar = new JMenuBar();
		menuBar.add(fileMenu);
		menuBar.add(actionMenu);
		menuBar.add(viewMenu);
		menuBar.add(toolsMenu);
		menuBar.add(helpMenu);
	}
	
	private void createMenus()
	{
		fileMenu = new JMenu(model.getName("FILE_MENU_NAME"));
		actionMenu = new JMenu(model.getName("ACTION_MENU_NAME"));
		viewMenu = new JMenu(model.getName("VIEW_MENU_NAME"));
		toolsMenu = new JMenu(model.getName("TOOLS_MENU_NAME"));
		helpMenu= new JMenu(model.getName("HELP_MENU_NAME"));
		layoutSubMenu = new JMenu(model.getName("LAYOUT_SUBMENU_NAME"));
		modeSubMenu = new JMenu(model.getName("MODE_SUBMENU_NAME"));
	}
	
	private void createMenuItems()
	{
		//Regular items
		openItem = new JMenuItem(model.getName("OPEN_ITEM_NAME"));
		exitItem = new JMenuItem(model.getName("EXIT_ITEM_NAME"));
		connectItem = new JMenuItem(model.getName("CONNECT_ITEM_NAME"));
		disconnectItem = new JMenuItem(model.getName("DISCONNECT_ITEM_NAME"));
		reconnectItem = new JMenuItem(model.getName("RECONNECT_ITEM_NAME"));
		resetItem = new JMenuItem(model.getName("RESET_ITEM_NAME"));
		//Checkboxes
		perspectiveItem = new JCheckBoxMenuItem(model.getName("PERSPECTIVE_CBOX_NAME"));
		sideViewitem = new JCheckBoxMenuItem(model.getName("SIDE_VIEW_CBOX_NAME"));
		topViewItem = new JCheckBoxMenuItem(model.getName("TOP_VIEW_CBOX_NAME"));
		showGridItem = new JCheckBoxMenuItem(model.getName("SHOW_GRID_CBOX_NAME"));
		//Radios
		layoutItem1 = new JRadioButtonMenuItem(model.getName("LAYOUT_RADIO_1_NAME"));
		layoutItem2 = new JRadioButtonMenuItem(model.getName("LAYOUT_RADIO_2_NAME"));
		layoutItem3 = new JRadioButtonMenuItem(model.getName("LAYOUT_RADIO_3_NAME"));
		layoutItem4 = new JRadioButtonMenuItem(model.getName("LAYOUT_RADIO_4_NAME"));
		//And more regular items
		settingsItem = new JMenuItem(model.getName("SETTINGS_ITEM_NAME"));
		modeItem1 = new JMenuItem(model.getName("MODE_ITEM_1_NAME"));
		modeItem2 = new JMenuItem(model.getName("MODE_ITEM_2_NAME"));
		helpItem = new JMenuItem(model.getName("HELP_ITEM_NAME"));
		aboutItem = new JMenuItem(model.getName("ABOUT_ITEM_NAME"));
	}
	
	private void setListeners()
	{
		//Regular items
		openItem.addActionListener(controller);
		exitItem.addActionListener(controller);
		connectItem.addActionListener(controller);
		disconnectItem.addActionListener(controller);
		reconnectItem.addActionListener(controller);
		resetItem.addActionListener(controller);
		//Checkboxes
		perspectiveItem.addActionListener(controller);
		sideViewitem.addActionListener(controller);
		topViewItem.addActionListener(controller);
		showGridItem.addActionListener(controller);
		//Radios
		layoutItem1.addActionListener(controller);
		layoutItem2.addActionListener(controller);
		layoutItem3.addActionListener(controller);
		layoutItem4.addActionListener(controller);
		//And more regular items
		settingsItem.addActionListener(controller);
		modeItem1.addActionListener(controller);
		modeItem2.addActionListener(controller);
		helpItem.addActionListener(controller);
		aboutItem.addActionListener(controller);
	}
	
	private void attachItems()
	{
		fileMenu.add(openItem);
		fileMenu.add(exitItem);
		actionMenu.add(connectItem);
		actionMenu.add(disconnectItem);
		//actionMenu.add(reconnectItem);
		actionMenu.add(resetItem);
		viewMenu.add(perspectiveItem);
		viewMenu.add(sideViewitem);
		viewMenu.add(topViewItem);
		viewMenu.add(showGridItem);
		layoutSubMenu.add(layoutItem1);
		layoutSubMenu.add(layoutItem2);
		layoutSubMenu.add(layoutItem3);
		layoutSubMenu.add(layoutItem4);
		viewMenu.add(layoutSubMenu);
		toolsMenu.add(settingsItem);
		modeSubMenu.add(modeItem1);
		modeSubMenu.add(modeItem2);
		toolsMenu.add(modeSubMenu);
		helpMenu.add(helpItem);
		helpMenu.add(aboutItem);
	}
	
	public void setModel(Model model)
	{
		this.model = model;
		controller = new TrackerMenuController(model, this);
		createMenus();
		createMenuItems();
		setListeners();
		attachItems();
		buildMenu();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
}

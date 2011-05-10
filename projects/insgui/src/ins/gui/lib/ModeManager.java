package ins.gui.lib;

import ins.gui.model.Model;
import ins.gui.view.DataView;
import ins.gui.view.PerspectiveView;
import ins.gui.view.SideView;
import ins.gui.view.SpotListView;
import ins.gui.view.StatusView;
import ins.gui.view.TopView;
import ins.gui.view.TrackerMenuView;
import ins.gui.view.TrackerToolView;

import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observer;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

public class ModeManager {

	private HashMap<String, JComponent> views;
	private HashMap<String, Model> models;
	private HashMap<String, JFrame> frames;
	private HashMap<String, GridBagConstraints> trackerViewConstraints;
	private HashMap<String, GridBagConstraints> simulatorViewConstraints;
	
	private ArrayList<String> trackerViews;
	private ArrayList<String> simulatorViews;
	
	private GridBagHelper gridBagHelper;
	
	public String currentMode;
	
	public ModeManager(HashMap<String, Model> models)
	{
		
		this.models = models;
		
		trackerViewConstraints = new HashMap<String, GridBagConstraints>();
		simulatorViewConstraints = new HashMap<String, GridBagConstraints>();
		
		trackerViews = new ArrayList<String>();
		simulatorViews = new ArrayList<String>();
		
		//Pick a random model to get the user settings, both models have the same settings file
		String startupPref = models.get("trackerModel").settingsLoader.userSettings.getProperty("MODE");
		
		if(startupPref.equalsIgnoreCase("tracker"))
		{
			currentMode = "tracker";
		}
		else
		{
			currentMode = "simulator";
		}
	}
	
	public void initializeManager(HashMap<String, JComponent> views, HashMap<String, JFrame> frames)
	{
		this.views = views;
		this.frames = frames;
		
		if(currentMode.equalsIgnoreCase("tracker"))
		{
			startTrackerMode();
		}
		else
		{
			startSimulatorMode();
		}
	}
	
	public void startTrackerMode()
	{
		defineTrackerViews();
		
		((DataView)views.get("dataView")).setModel(models.get("trackerModel"));
		((PerspectiveView)views.get("perspectiveView")).setModel(models.get("trackerModel"));
		((SideView)views.get("sideView")).setModel(models.get("trackerModel"));
		((SpotListView)views.get("spotListView")).setModel(models.get("trackerModel"));
		((StatusView)views.get("statusView")).setModel(models.get("trackerModel"));
		((TopView)views.get("topView")).setModel(models.get("trackerModel"));
		((TrackerMenuView)views.get("trackerMenuView")).setModel(models.get("trackerModel"));
		((TrackerToolView)views.get("trackerToolView")).setModel(models.get("trackerModel"));
		
		setTrackerViewConstraints();
		buildTrackerFrame();
		addTrackerObservers();
		
		frames.get("trackerFrame").setJMenuBar(((TrackerMenuView)views.get("trackerMenuView")).menuBar);
		frames.get("trackerFrame").setExtendedState(frames.get("trackerFrame").MAXIMIZED_BOTH); 
		frames.get("trackerFrame").setVisible(true);
	}
	
	private void setTrackerViewConstraints()
	{
		trackerViewConstraints.put("dataView", ((DataView)views.get("dataView")).getConstraints());
		trackerViewConstraints.put("perspectiveView", ((PerspectiveView)views.get("perspectiveView")).getConstraints());
		trackerViewConstraints.put("sideView", ((SideView)views.get("sideView")).getConstraints());
		trackerViewConstraints.put("spotListView", ((SpotListView)views.get("spotListView")).getConstraints());
		trackerViewConstraints.put("statusView", ((StatusView)views.get("statusView")).getConstraints());
		trackerViewConstraints.put("topView", ((TopView)views.get("topView")).getConstraints());
		trackerViewConstraints.put("trackerToolView", ((TrackerToolView)views.get("trackerToolView")).getConstraints());
	}
	
	private void setSimulatorViewConstraints()
	{
		
	}
	
	public void startSimulatorMode()
	{
		defineSimulatorViews();
		buildSimulatorFrame();
	}
	
	private void buildTrackerFrame()
	{
		for(String viewName: trackerViews)
		{
			JComponent view = views.get(viewName);
			if(!(view instanceof JMenuBar))
			{
				frames.get("trackerFrame").add(view, trackerViewConstraints.get(viewName));
			}
		}
	}
	
	private void buildSimulatorFrame()
	{
		for(String viewName: simulatorViews)
		{
			frames.get("simulatorFrame").add(views.get(viewName));
		}
	}
	
	private void addTrackerObservers()
	{
		for(String view: trackerViews)
		{
			models.get("trackerModel").addObserver((Observer) views.get(view));
		}
	}
	
	private void defineTrackerViews()
	{
		trackerViews.add("dataView");
		trackerViews.add("perspectiveView");
		trackerViews.add("sideView");
		trackerViews.add("spotListView");
		trackerViews.add("statusView");
		trackerViews.add("topView");
		trackerViews.add("trackerMenuView");
		trackerViews.add("trackerToolView");
	}
	
	private void defineSimulatorViews()
	{
		simulatorViews.add("perspectiveView");
		simulatorViews.add("simulatorView");
		simulatorViews.add("statusView");
		simulatorViews.add("trackerMenuView");
		simulatorViews.add("simulatorToolView");
	}
	
}

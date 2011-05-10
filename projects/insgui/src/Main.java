import ins.gui.frame.SimulatorFrame;
import ins.gui.frame.TrackerFrame;
import ins.gui.lib.ModeManager;
import ins.gui.lib.config.LanguageLoader;
import ins.gui.lib.config.SettingsLoader;
import ins.gui.model.Model;
import ins.gui.model.SimulatorModel;
import ins.gui.model.TrackerModel;
import ins.gui.view.DataView;
import ins.gui.view.PerspectiveView;
import ins.gui.view.SideView;
import ins.gui.view.SimulatorToolView;
import ins.gui.view.SimulatorView;
import ins.gui.view.SpotListView;
import ins.gui.view.StatusView;
import ins.gui.view.TopView;
import ins.gui.view.TrackerMenuView;
import ins.gui.view.TrackerToolView;

import java.io.IOException;
import java.util.HashMap;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 * Main class responsible for constructing the first objects and providing an
 * entry point. Also contains all the views, models and controllers used in the
 * application.
 * 
 * @author Censi-HIT, M. Dulkiewicz
 * @version 3.0
 */
public class Main {
	
	//Mode manager
	private ModeManager modeManager;

	// Property loaders
	private SettingsLoader settingsLoader;
	private LanguageLoader languageLoader;

	// Frames
	private TrackerFrame trackerFrame;
	private SimulatorFrame simulatorFrame;

	// Models
	private SimulatorModel simulatorModel;
	private TrackerModel trackerModel;

	// Views
	private PerspectiveView perspectiveView;
	private SideView sideView;
	private TopView topView;
	private SpotListView spotListView;
	private DataView dataView;
	private TrackerMenuView trackerMenuView;
	private StatusView statusView;
	private TrackerToolView trackerToolView;
	private SimulatorView simulatorView;
	private SimulatorToolView simulatorToolView;
	
	//HashMaps
	private HashMap<String, JComponent> views;
	private HashMap<String, JFrame> frames;
	private HashMap<String, Model> models;

	public Main() {
		initiateSettings();
		createModels(languageLoader, settingsLoader);
		createModeManager();
		createFrames();
		createViews();
		modeManager.initializeManager(views, frames);
	}

	/**
	 * Entry point.
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try  
		{  
		  //Tell the UIManager to use the platform look and feel  
		  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());  
		}  
		catch(Exception e)  
		{  
		} 
		Main main = new Main();
	}

	/**
	 * Load the correct settings and language for the application. First the
	 * settings are loaded and according to the language preference stored in
	 * the user settings, the appropriate language file is loaded to be used by
	 * all the UI elements in the application.
	 */
	private void initiateSettings() {
		try {
			settingsLoader = new SettingsLoader();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			languageLoader = new LanguageLoader(settingsLoader.userSettings);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initiates the two models used by the application using the language
	 * loader and the settings loader.
	 * 
	 * @param lang languageLoader, contains the language file.
	 * @param sett settinsLoader, contains the user settings.
	 */
	private void createModels(LanguageLoader lang, SettingsLoader sett) {
		models = new HashMap<String, Model>();
		
		simulatorModel = new SimulatorModel(lang, sett);
		trackerModel = new TrackerModel(lang, sett);
		
		models.put("simulatorModel", simulatorModel);
		models.put("trackerModel", trackerModel);
	}
	
	/**
	 * Creates both the tracker interface frame and the simulator interface
	 * frame. Note that only one of the frames can be active at any given time,
	 * so only one is to be set as visible.
	 */
	private void createFrames()
	{
		frames = new HashMap<String, JFrame>();
		
		trackerFrame = new TrackerFrame(trackerModel);
		simulatorFrame = new SimulatorFrame(simulatorModel);
		
		frames.put("trackerFrame", trackerFrame);
		frames.put("simulatorFrame", trackerFrame);
	}
	
	private void createViews()
	{
		views = new HashMap<String, JComponent>();
		
		dataView = new DataView();
		perspectiveView = new PerspectiveView();
		sideView = new SideView();
		simulatorToolView = new SimulatorToolView();
		simulatorView = new SimulatorView();
		spotListView = new SpotListView();
		statusView = new StatusView();
		topView = new TopView();
		trackerMenuView = new TrackerMenuView();
		trackerToolView = new TrackerToolView();
		
		views.put("dataView", dataView);
		views.put("perspectiveView", perspectiveView);
		views.put("sideView", sideView);
		views.put("simulatorToolView", simulatorToolView);
		views.put("simulatorView", simulatorView);
		views.put("spotListView", spotListView);
		views.put("statusView", statusView);
		views.put("topView", topView);
		views.put("trackerMenuView", trackerMenuView);
		views.put("trackerToolView", trackerToolView);
	}
	
	private void createModeManager()
	{
		modeManager = new ModeManager(models);
		for(Model m: models.values())
		{
			m.setModeManager(modeManager);
		}
	}

}

package ins.gui.model;

import ins.gui.lib.ModeManager;
import ins.gui.lib.config.LanguageLoader;
import ins.gui.lib.config.SettingsLoader;

import java.util.Observable;

public abstract class Model extends Observable{
	
	public LanguageLoader languageLoader;
	public SettingsLoader settingsLoader;
	public ModeManager modeManager;

	public Model(LanguageLoader lang, SettingsLoader sett)
	{
		languageLoader = lang;
		settingsLoader = sett;
	}
	
	public void setModeManager(ModeManager manager)
	{
		modeManager = manager;
	}
	
	public int getIntValue(String name)
	{
		int value = Integer.parseInt(settingsLoader.userSettings.getProperty(name));
		return value;
	}
	
	public String getStringValue(String name)
	{
		String value = settingsLoader.userSettings.getProperty(name);
		return value;
	}
	
	public double getDoubleValue(String name)
	{
		double value = Double.parseDouble(settingsLoader.userSettings.getProperty(name));
		return value;
	}
	
	public String getName(String property)
	{
		if(modeManager.currentMode.equalsIgnoreCase("tracker"))
		{
			return languageLoader.trackerLang.getProperty(property);
		}
		else
		{
			return languageLoader.simulatorLang.getProperty(property);
		}
	}
}

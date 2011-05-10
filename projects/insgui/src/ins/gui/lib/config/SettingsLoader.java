package ins.gui.lib.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class SettingsLoader extends PropertiesLoader{
	
	private static final String DEFAULT_SETTINGS = "default";
	private static final String USER_SETTINGS = "user";
	private static final String PATH = "settings";
	private static final String SETTINGS_EXTENTION = ".ini";

	public Properties defaultSettings;
	public Properties userSettings;
	
	public SettingsLoader() throws IOException
	{
		defaultSettings = new Properties();
		in = new FileInputStream("./"+File.separator+PATH+File.separator+DEFAULT_SETTINGS+SETTINGS_EXTENTION);
		defaultSettings.load(in);
		in.close();
		
		userSettings = new Properties(defaultSettings);
		in = new FileInputStream("./"+File.separator+PATH+File.separator+USER_SETTINGS+SETTINGS_EXTENTION);
		userSettings.load(in);
		in.close();
	}
}

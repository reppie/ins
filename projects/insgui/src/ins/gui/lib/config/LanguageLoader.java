package ins.gui.lib.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LanguageLoader extends PropertiesLoader{
	
	private static final String LANG_DIR = "language";
	private static final String SIM_FILE_NAME = "sim_lang_";
	private static final String TRACKER_FILE_NAME = "tracker_lang_";
	private static final String LANG_EXTENTION = ".ini";
	
	public Properties simulatorLang;
	public Properties trackerLang;

	public LanguageLoader(Properties settings) throws IOException
	{
		String lang = settings.getProperty("LANGUAGE");
		
		simulatorLang = new Properties();
		in = new FileInputStream("./"+LANG_DIR+File.separator+SIM_FILE_NAME+lang+LANG_EXTENTION);
		simulatorLang.load(in);
		in.close();
		
		trackerLang = new Properties();
		in = new FileInputStream("./"+LANG_DIR+File.separator+TRACKER_FILE_NAME+lang+LANG_EXTENTION);
		trackerLang.load(in);
		in.close();
	}
}

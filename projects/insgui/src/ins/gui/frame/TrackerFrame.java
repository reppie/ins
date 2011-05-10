package ins.gui.frame;

import ins.gui.model.TrackerModel;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JToolBar;

public class TrackerFrame extends JFrame{

	public TrackerModel model;
	//private Properties langFile;
	private Toolkit toolKit = Toolkit.getDefaultToolkit();
	
	public TrackerFrame(TrackerModel model)
	{
		this.model = model;
		//langFile = model.languageLoader.trackerLang;
		initialize();
	}
	
	private void initialize()
	{
		setTitle(model.getName("TRACKER_FRAME_TITLE"));
		setSize(toolKit.getScreenSize().width/2, toolKit.getScreenSize().height/2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridBagLayout());
	}
	
}

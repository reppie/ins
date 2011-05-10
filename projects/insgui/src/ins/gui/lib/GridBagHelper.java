package ins.gui.lib;

import ins.gui.model.Model;

import java.awt.GridBagConstraints;
import java.util.HashMap;

public class GridBagHelper {
	
	private Model model;
	
	private HashMap<String, Integer> gridBagAnchorConstants;
	private HashMap<String, Integer> gridBagFillConstants;
	
	public GridBagHelper(Model model)
	{
		this.model = model;
		initGridBagConstants();
	}
	
	public int getGridBagConst(String name)
	{
		String value = model.getStringValue(name);
		if(name.contains("FILL"))
		{
			return gridBagFillConstants.get(value);
		}
		else if(name.contains("ANCHOR"))
		{
			return gridBagAnchorConstants.get(value);
		}
		return 0;
	}

	
	private void initGridBagConstants()
	{
		gridBagAnchorConstants = new HashMap<String, Integer>();
		gridBagFillConstants = new HashMap<String, Integer>();
		
		gridBagAnchorConstants.put("FIRST_LINE_START", GridBagConstraints.FIRST_LINE_START);
		gridBagAnchorConstants.put("PAGE_START", GridBagConstraints.PAGE_START);
		gridBagAnchorConstants.put("FIRST_LINE_END", GridBagConstraints.FIRST_LINE_END);
		gridBagAnchorConstants.put("LINE_START", GridBagConstraints.LINE_START);
		gridBagAnchorConstants.put("CENTER", GridBagConstraints.CENTER);
		gridBagAnchorConstants.put("LINE_END", GridBagConstraints.LINE_END);
		gridBagAnchorConstants.put("LAST_LINE_START", GridBagConstraints.LAST_LINE_START);
		gridBagAnchorConstants.put("PAGE_END", GridBagConstraints.PAGE_END);
		gridBagAnchorConstants.put("LAST_LINE_END", GridBagConstraints.LAST_LINE_END);
		
		gridBagFillConstants.put("NONE", GridBagConstraints.NONE);
		gridBagFillConstants.put("VERTICAL", GridBagConstraints.VERTICAL);
		gridBagFillConstants.put("HORIZONTAL", GridBagConstraints.HORIZONTAL);
		gridBagFillConstants.put("BOTH", GridBagConstraints.BOTH);
	}
	
}

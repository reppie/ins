package ins.gui.model;

import ins.gui.lib.TreeHelper;
import ins.gui.lib.config.LanguageLoader;
import ins.gui.lib.config.SettingsLoader;
import ins.sunspot.Spot;
import ins.sunspot.SpotNode;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.tree.TreePath;

public class TrackerModel extends Model{
	
	private ArrayList<SpotNode> groups;
	private HashMap<Spot, SpotNode> spots;
	private TreeHelper treeHelper;
	private SpotNode defaultNode;
	private Spot selectedSpot;

	public TrackerModel(LanguageLoader lang, SettingsLoader sett) {
		super(lang, sett);
		groups = new ArrayList<SpotNode>();
		defaultNode = new SpotNode(getStringValue("DEFAULT_SPOT_NODE"));
		groups.add(defaultNode);
		fillSpots(10);
		treeHelper = new TreeHelper();
	}
	
	private void fillSpots(int amount)
	{
		spots = new HashMap<Spot, SpotNode>();
		for(int i=0; i<amount; i++)
		{
			spots.put(new Spot("Spot_"+i), defaultNode);
		}
	}
	
	public HashMap<Spot, SpotNode> getSpots()
	{
		return spots;
	}
	
	public ArrayList<SpotNode> getGroups()
	{
		return groups;
	}
	
	public void addGroup(SpotNode node)
	{
		if(!groupExists(node))
		{
			groups.add(node);
			setChanged();
			notifyObservers();
		}
	}
	
	public void removeGroup(TreePath group)
	{
		if(!group.getLastPathComponent().toString().equalsIgnoreCase(defaultNode.toString()))
		{
			for(Spot s: spots.keySet())
			{
				if(spots.get(s).toString().equalsIgnoreCase(group.getLastPathComponent().toString()))
				{
					spots.put(s, defaultNode);
				}
			}
			groups.remove(groups.indexOf(getGroupByName(group.getLastPathComponent().toString())));
			setChanged();
			notifyObservers(group);
		}
	}
	
	public void moveSpot(String spot, String target)
	{
		for(Spot s: spots.keySet())
		{
			if(s.toString().equals(spot.toString()))
			{
				spots.put(s, getGroupByName(target));
				setChanged();
				notifyObservers();
			}
		}
	}
	
	private SpotNode getGroupByName(String name)
	{
		for(SpotNode s: groups)
		{
			if(s.toString().equalsIgnoreCase(name))
			{
				return s;
			}
		}
		return null;
	}
	
	public void renameGroup(String group, String name)
	{
		if(!groupExists(new SpotNode(name)))
		{
			for(SpotNode s: groups)
			{
				if(s.toString().equalsIgnoreCase(group.toString()))
				{
					groups.get(groups.indexOf(s)).setName(name);
					setChanged();
					notifyObservers();
				}
			}
		}
	}
	
	private boolean groupExists(SpotNode node)
	{
		for(int i=0;i<groups.size();i++)
		{
			if(groups.get(i).toString().equalsIgnoreCase(node.toString()))
			{
				return true;
			}
		}
		return false;
	}
	
	public void setSelectedSpot(Spot spot)
	{
		selectedSpot = spot;
		setChanged();
		notifyObservers();
	}
	
	public Spot getSelectedSpot()
	{
		return selectedSpot;
	}

}

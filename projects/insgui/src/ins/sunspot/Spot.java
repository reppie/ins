package ins.sunspot;

import javax.swing.tree.DefaultMutableTreeNode;

public class Spot extends DefaultMutableTreeNode{
	
	public String name;
	public String id;
	public String modelNr;
	public String address;
	public String firmwareVer;
	public String hardwareRev;
	public String serialNr;
	public String status;
	public float batteryStatus;
	public String connectionPort;
	
	public String position;
	public String velocity;
	public String acceleration;
	
	public Spot(String name)
	{
		this.name = name;
		setAllowsChildren(false);
	}
	
	public String toString()
	{
		return name;
	}

}

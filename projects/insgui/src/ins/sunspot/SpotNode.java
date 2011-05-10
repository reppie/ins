package ins.sunspot;

import javax.swing.tree.DefaultMutableTreeNode;

public class SpotNode extends DefaultMutableTreeNode{
	
	private String name;
	
	public SpotNode(String name)
	{
		this.name = name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String toString()
	{
		return name;
	}

}

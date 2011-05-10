package ins.gui.lib;

import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

public class TreeHelper {

	public TreeHelper()
	{
		
	}
	
	public DefaultMutableTreeNode findNode(DefaultMutableTreeNode root, TreeNode target)
	{
		DefaultMutableTreeNode node;
		Enumeration enumeration = ((DefaultMutableTreeNode)root).breadthFirstEnumeration();
		
		while(enumeration.hasMoreElements())
		{
			node = (DefaultMutableTreeNode)enumeration.nextElement();
			
			if(node.equals(target))
			{
				return node;
			}
		}
		return null;
	}
}

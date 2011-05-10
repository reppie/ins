package ins.gui.dialog;

import ins.gui.model.Model;
import ins.gui.model.TrackerModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class SpotPopup extends JPopupMenu implements ActionListener{
	
	private TreePath path;
	private ArrayList<TreeNode> groups;
	
	private Model model;
	private JMenu moveTo;

	public SpotPopup(Model model, TreePath path, int x, int y, JComponent component, ArrayList<TreeNode> children)
	{
		this.model = model;
		this.path = path;
		groups = children;
		initializeItems();
		show(component,x+getPreferredSize().width/2, y+getPreferredSize().height/2);
	}
	
	private void initializeItems()
	{
		moveTo = new JMenu(model.getName("SPOT_POPUP_MOVE_ITEM"));
		
		add(moveTo);
		for(TreeNode n: groups)
		{
			JMenuItem item = new JMenuItem(n.toString());
			item.addActionListener(this);
			moveTo.add(item);
		}
	}

	public void actionPerformed(ActionEvent e) 
	{
		((TrackerModel)model).moveSpot(path.getLastPathComponent().toString(), ((JMenuItem)e.getSource()).getText());
	}
}

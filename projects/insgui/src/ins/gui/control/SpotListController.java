package ins.gui.control;

import ins.gui.dialog.SpotNodePopup;
import ins.gui.dialog.SpotPopup;
import ins.gui.model.Model;
import ins.gui.model.TrackerModel;
import ins.gui.view.SpotListView;
import ins.sunspot.Spot;
import ins.sunspot.SpotNode;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class SpotListController extends MouseAdapter{
	
	private Model model;
	private SpotListView view;
	private JTree tree;
	
	public SpotListController(Model model, SpotListView view)
	{
		this.model = model;
		this.view = view;
		view.tree.addMouseListener(this);
		tree = view.tree;
	}
	
	public void mousePressed(MouseEvent e) {
		int selRow = tree.getRowForLocation(e.getX(), e.getY());
        TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
        if(selRow != -1) {
        	tree.setSelectionPath(selPath);
            if(e.isMetaDown()) {
            	if(selPath.getLastPathComponent().getClass() == SpotNode.class)
            	{
            		new SpotNodePopup(model, selPath, e.getX(), e.getY(), view);
            	}
            	else if(selPath.getLastPathComponent().getClass() == Spot.class)
            	{
            		int childCount = ((DefaultMutableTreeNode)tree.getModel().getRoot()).getChildCount();
            		ArrayList<TreeNode> children = new ArrayList<TreeNode>();
            		for(int i=0;i<childCount;i++)
            		{
            			children.add(((DefaultMutableTreeNode)tree.getModel().getRoot()).getChildAt(i));
            		}
            		new SpotPopup(model, selPath, e.getX(), e.getY(), view, children);
            	}
            }
            else
            {
            	if(selPath.getLastPathComponent().getClass() == Spot.class)
            	{
            		((TrackerModel)model).setSelectedSpot((Spot)selPath.getLastPathComponent());
            	}
            }
        }
    }

}

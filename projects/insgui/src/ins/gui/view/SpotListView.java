package ins.gui.view;

import ins.gui.control.SpotListController;
import ins.gui.lib.GridBagHelper;
import ins.gui.lib.IConstrained;
import ins.gui.lib.IModelSetter;
import ins.gui.lib.TreeHelper;
import ins.gui.model.Model;
import ins.gui.model.TrackerModel;
import ins.sunspot.Spot;
import ins.sunspot.SpotNode;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public class SpotListView extends JPanel implements IModelSetter, Observer, IConstrained{

	public Model model;
	public JTree tree;
	private JScrollPane scrollPane;
	private SpotListController controller;
	private TreeHelper treeHelper;
	private DefaultMutableTreeNode root;
	
	public SpotListView()
	{
		setBorder(BorderFactory.createTitledBorder("SpotListView"));
		setLayout(new BorderLayout());
	}
	
	private void createTreeBase()
	{
		treeHelper = new TreeHelper();
		root = new DefaultMutableTreeNode(model.getName("SPOT_LIST_VIEW_TREE_ROOT_NAME"));
		//createBaseNodes(root);
		tree = new JTree(root);
		scrollPane = new JScrollPane(tree);
		//scrollPane.setPreferredSize(getSize());
		add(scrollPane, BorderLayout.CENTER);
	}
	
	private void createBaseNodes(DefaultMutableTreeNode root)
	{
		SpotNode unassigned = new SpotNode(model.getName("SPOT_LIST_VIEW_TREE_DEFAULT_NODE_0"));
		((TrackerModel)model).addGroup(unassigned);
		
		SpotNode alpha = new SpotNode(model.getName("SPOT_LIST_VIEW_TREE_DEFAULT_NODE_1"));
		((TrackerModel)model).addGroup(alpha);
		
		SpotNode bravo = new SpotNode(model.getName("SPOT_LIST_VIEW_TREE_DEFAULT_NODE_2"));
		((TrackerModel)model).addGroup(bravo);
		
		SpotNode charlie = new SpotNode(model.getName("SPOT_LIST_VIEW_TREE_DEFAULT_NODE_3"));
		((TrackerModel)model).addGroup(charlie);
	}
	
	private void updateTree()
	{
		for(SpotNode n: ((TrackerModel)model).getGroups())
		{
			root.add(n);
		}
		for(Spot s: ((TrackerModel)model).getSpots().keySet())
		{
			DefaultMutableTreeNode parent = treeHelper.findNode(root, ((TrackerModel)model).getSpots().get(s));
			parent.add(s);
		}
		tree.updateUI();
	}
	
	private void cleanTree(TreePath path)
	{
		root.remove((DefaultMutableTreeNode)path.getLastPathComponent());
	}

	public void setModel(Model model) {
		this.model = model;
		createTreeBase();
		updateTree();
		controller = new SpotListController(model, this);
	}

	public GridBagConstraints getConstraints() {
		GridBagHelper gridBagHelper = new GridBagHelper(model);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = model.getIntValue("SPOT_LIST_VIEW_GRIDX");
		constraints.gridy = model.getIntValue("SPOT_LIST_VIEW_GRIDY");
		constraints.gridheight = model.getIntValue("SPOT_LIST_VIEW_GRIDHEIGHT");
		constraints.gridwidth = model.getIntValue("SPOT_LIST_VIEW_GRIDWIDTH");
		constraints.weightx = model.getDoubleValue("SPOT_LIST_VIEW_WEIGHTX");
		constraints.weighty = model.getDoubleValue("SPOT_LIST_VIEW_WEIGHTY");
		constraints.anchor = gridBagHelper.getGridBagConst("SPOT_LIST_VIEW_ANCHOR");
		constraints.fill = gridBagHelper.getGridBagConst("SPOT_LIST_VIEW_FILL");
		return constraints;
	}

	public void update(Observable arg0, Object arg1) 
	{
		if(arg1 != null)
		{
			cleanTree((TreePath)arg1);
		}
		updateTree();
	}
}

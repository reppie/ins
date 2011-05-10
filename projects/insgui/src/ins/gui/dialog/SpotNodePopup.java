package ins.gui.dialog;

import ins.gui.model.Model;
import ins.gui.model.TrackerModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.tree.TreePath;

public class SpotNodePopup extends JPopupMenu implements ActionListener{
	
	private TreePath path;
	
	private Model model;
	private JMenuItem createNode;
	private JMenuItem renameNode;
	private JMenuItem deleteNode;
	
	public SpotNodePopup(Model model, TreePath path, int x, int y, JComponent component)
	{
		this.model = model;
		this.path = path;
		initializeItems();
		show(component,x+getPreferredSize().width/2, y+getPreferredSize().height/2);
	}
	
	private void initializeItems()
	{
		createNode = new JMenuItem(model.getName("SPOT_NODE_POPUP_CREATE_ITEM"));
		createNode.addActionListener(this);
		renameNode = new JMenuItem(model.getName("SPOT_NODE_POPUP_RENAME_ITEM"));
		renameNode.addActionListener(this);
		deleteNode = new JMenuItem(model.getName("SPOT_NODE_POPUP_DELETE_ITEM"));
		deleteNode.addActionListener(this);
		
		add(createNode);
		add(new JSeparator());
		add(renameNode);
		add(deleteNode);
	}

	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == createNode)
		{
			NodeCreationDialog dialog = new NodeCreationDialog(model);
		}
		else if(e.getSource() == renameNode)
		{
			if(isDefaultNode(path))
			{
				JOptionPane.showMessageDialog(null,
					    model.getName("NODE_RENAME_DEFAULT_DIALOG"),
					    model.getName("NODE_RENAME_DEFAULT_DIALOG_TITLE"),
					    JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				NodeRenameDialog dialog = new NodeRenameDialog(model, path);
			}
		}
		else if(e.getSource() == deleteNode)
		{
			if(isDefaultNode(path))
			{
				JOptionPane.showMessageDialog(null,
					    model.getName("NODE_DELETE_DIALOG_DEFAULT_ERROR"),
					    model.getName("NODE_DELETE_DIALOG_DEFAULT_TITLE"),
					    JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				int response = JOptionPane.showConfirmDialog(null, 
						model.getName("NODE_DELETE_DIALOG_CONTENT"), 
						model.getName("NODE_DELETE_DIALOG_TITLE"),
						JOptionPane.YES_NO_OPTION);
				if(response == 0)
				{
					((TrackerModel)model).removeGroup(path);
				}
			}
		}
	}
	
	private boolean isDefaultNode(TreePath path)
	{
		if(path.getLastPathComponent().toString().equalsIgnoreCase(model.getStringValue("DEFAULT_SPOT_NODE")))
			return true;
		else
			return false;
	}

}

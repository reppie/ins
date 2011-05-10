package ins.gui.dialog;

import ins.gui.model.Model;
import ins.gui.model.TrackerModel;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.tree.TreePath;

public class NodeRenameDialog extends JDialog implements ActionListener{

	private Model model;
	
	private JLabel nameLabel;
	private JTextField nameField;
	private JButton confirmButton;
	private JButton cancelButton;
	
	private TreePath path;
	
	public NodeRenameDialog(Model model, TreePath path)
	{
		this.model = model;
		this.path = path;
		setLayout(new FlowLayout());
		setTitle(model.getName("NODE_RENAME_DIALOG_TITLE"));
		initializeItems();
		setSize(new Dimension(250, 90));
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		setAlwaysOnTop(true);
	}
	
	private void initializeItems()
	{
		nameLabel = new JLabel(model.getName("NODE_RENAME_DIALOG_NAME_LABEL"));
		nameField = new JTextField(model.getIntValue("NODE_RENAME_DIALOG_NAME_FIELD_SIZE"));
		confirmButton = new JButton(model.getName("NODE_RENAME_DIALOG_CONFIRM_BUTTON"));
		confirmButton.addActionListener(this);
		cancelButton = new JButton(model.getName("NODE_RENAME_DIALOG_CANCEL_BUTTON"));
		cancelButton.addActionListener(this);
		
		add(nameLabel);
		add(nameField);
		add(confirmButton);
		add(cancelButton);
	}

	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == cancelButton)
		{
			this.dispose();
		}
		else if(e.getSource() == confirmButton)
		{
			((TrackerModel)model).renameGroup(path.getLastPathComponent().toString(), nameField.getText());
			this.dispose();
		}
	}
}

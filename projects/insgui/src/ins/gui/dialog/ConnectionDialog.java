package ins.gui.dialog;

import ins.gui.model.Model;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ConnectionDialog extends JDialog implements ActionListener{
	
	private JLabel msgLabel;
	private JLabel addressLabel;
	private JTextField addressInput;
	private JLabel nameLabel;
	private JTextField nameInput;
	private JComboBox connOptions;
	private JButton confirm;
	private JButton decline;
	private Model model;

	public ConnectionDialog(Model model)
	{
		this.model = model;
		setLayout(new FlowLayout());
		setTitle(model.getName("CONNECTION_DIALOG_TITLE"));
		initiateComponents();
		attachComponents();
		setSize(new Dimension(525, 125));
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void initiateComponents()
	{
		msgLabel = new JLabel(model.getName("CONNECTION_DIALOG_MSG_LABEL"));
		addressLabel = new JLabel(model.getName("CONNECTION_DIALOG_ADDRESS_LABEL"));
		addressInput = new JTextField(model.getIntValue("CONNECTION_DIALOG_ADDRESS_INPUT_SIZE"));
		nameLabel = new JLabel(model.getName("CONNECTION_DIALOG_NAME_LABEL"));
		nameInput = new JTextField(model.getIntValue("CONNECTION_DIALOG_NAME_INPUT_SIZE"));
		connOptions = new JComboBox();
		connOptions.insertItemAt(model.getName("CONNECTION_DIALOG_CONN_OPTION1"), 0);
		connOptions.insertItemAt(model.getName("CONNECTION_DIALOG_CONN_OPTION2"), 1);
		connOptions.setSelectedIndex(0);
		confirm = new JButton(model.getName("CONNECTION_DIALOG_CONFIRM_BUTTON_NAME"));
		confirm.addActionListener(this);
		decline = new JButton(model.getName("CONNECTION_DIALOG_DECLINE_BUTTON_NAME"));
		decline.addActionListener(this);
	}
	
	private void attachComponents()
	{
		add(msgLabel);
		add(connOptions);
		add(addressLabel);
		add(addressInput);
		add(nameLabel);
		add(nameInput);
		add(confirm);
		add(decline);
	}

	public void actionPerformed(ActionEvent event) 
	{
		if(event.getSource() == decline)
		{
			this.dispose();
		}
		else if(event.getSource() == confirm)
		{
			System.out.println("Address: "+addressInput.getText()+";Name: "+nameInput.getText()+";Connection: "+connOptions.getSelectedIndex());
			this.dispose();
		}
	}
}

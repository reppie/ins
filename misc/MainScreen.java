package reis.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;

import reis.dao.domain.User;
import reis.logic.*;

public class MainScreen extends JFrame{
	
	private String mode;
	private Logic logic;
	private JButton usernameSearchButton, userDeleteButton, userEditButton, 
					changePasswordButton, commitChangesButton, cancelChangesButton, newUserButton;
	
	private JTextField usernameSearchTextField, usernameTextField, firstnameTextField, prefixTextField, surnameTextField, 
					passwordTextField, emailTextField, zipcodeTextField, homenumberTextField, 
					birthdateTextField;
	
	private JLabel 	usernameSearchLabel, usernameLabel, firstnameLabel, prefixLabel, surnameLabel,
					passwordLabel, emailLabel, zipcodeLabel, homenumberLabel, 
					birthdateLabel, sexLabel, accountTypeLabel, privacyLevelLabel;

	private JComboBox sexCombo, accountTypeCombo, privacyLevelCombo;
	
	private JPanel notifyPanel, userPanel;
	
	private DateFormat dateformat;
	
			
	public MainScreen(Logic logic) {
		this.logic = logic;
		dateformat = new SimpleDateFormat("yyyy-MM-dd");
		mode = "";
		
		setSize(500,510);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		notifyPanel=new JPanel();
		userPanel=new JPanel();
		
		// panels
		userPanel.setLayout(null);
		notifyPanel.setLayout(null);
		
		// Zoek lay-out
		usernameSearchTextField = new JTextField("");
		usernameSearchTextField.setBounds(180, 10, 200, 22);
		
		usernameSearchLabel = new JLabel("Zoek gebruiker");
		usernameSearchLabel.setBounds(10, 10, 200, 22);
		
		usernameSearchButton = new JButton("Zoeken");
		usernameSearchButton.setBounds(385, 10, 100, 22);
		
		// User info layout
		usernameLabel = new JLabel("Gebruikersnaam");
		usernameLabel.setBounds(10, 70, 200, 22);
		
		usernameTextField = new JTextField("");
		usernameTextField.setBounds(180, 70, 200, 22);
		
		firstnameLabel = new JLabel("Voornaam");
		firstnameLabel.setBounds(10, 95, 200, 22);
		
		firstnameTextField = new JTextField("");
		firstnameTextField.setBounds(180, 95, 200, 22);
		
		prefixLabel = new JLabel("Tussenvoegsel");
		prefixLabel.setBounds(10, 120, 200, 22);
		
		prefixTextField= new JTextField("");
		prefixTextField.setBounds(180, 120, 200, 22);
		
		surnameLabel = new JLabel("Achternaam");
		surnameLabel.setBounds(10, 145, 200, 22);
		
		surnameTextField = new JTextField("");
		surnameTextField.setBounds(180, 145, 200, 22);
		
		passwordLabel = new JLabel("Wachtwoord");
		passwordLabel.setBounds(10, 170, 200, 22);
		
		passwordTextField = new JTextField("");
		passwordTextField.setBounds(180, 170, 200, 22);
		
		changePasswordButton = new JButton("Wijzig");
		changePasswordButton.setBounds(385, 170, 100, 22);
		
		emailLabel = new JLabel("E-mail");
		emailLabel.setBounds(10, 195, 200, 22);
		
		emailTextField = new JTextField("");
		emailTextField.setBounds(180, 195, 200, 22);
		
		zipcodeLabel = new JLabel("Postcode");
		zipcodeLabel.setBounds(10, 220, 200, 22);
		
		zipcodeTextField = new JTextField("");
		zipcodeTextField.setBounds(180, 220, 200, 22);
		
		homenumberLabel = new JLabel("Huisnummer");
		homenumberLabel.setBounds(10, 245, 200, 22);
		
		homenumberTextField = new JTextField("");
		homenumberTextField.setBounds(180, 245, 200, 22);
		
		birthdateLabel = new JLabel("Geboortedatum");
		birthdateLabel.setBounds(10, 270, 200, 22);
		
		birthdateTextField = new JTextField("");
		birthdateTextField.setBounds(180, 270, 200, 22);
		
		sexLabel = new JLabel("Geslacht");
		sexLabel.setBounds(10, 295, 200, 22);
		
		//Array met de mogelijke typen
		String[] sexList = {"M","V"};
		
		sexCombo = new JComboBox(sexList);
		sexCombo.setBounds(180, 295, 45, 22);
		
		accountTypeLabel = new JLabel("Account Type");
		accountTypeLabel.setBounds(10, 320, 200, 22);
		
		//Array met de mogelijke typen
		String[] accountTypeList = {"1","2", "3", "4"};
		
		accountTypeCombo = new JComboBox(accountTypeList);
		accountTypeCombo.setBounds(180, 320, 85, 22);
		
		privacyLevelLabel = new JLabel("Privacy Niveau");
		privacyLevelLabel.setBounds(10, 345, 200, 22);
		
		// Array met de mogelijke typen
		String[] privacyLevelList = {"1","2","3", "4"};
		
		privacyLevelCombo = new JComboBox(privacyLevelList);
		privacyLevelCombo.setBounds(180, 345, 45, 22);
		
		// Edit, Delete and Save buttons
		newUserButton = new JButton("Nieuw");
		newUserButton.setBounds(10,395,235,22);
		
		userEditButton = new JButton("Bewerk");
		userEditButton.setBounds(250,395,235,22);
		
		commitChangesButton = new JButton("Opslaan");
		commitChangesButton.setBounds(10,425,235,22);
		
		userDeleteButton = new JButton("Verwijder");
		userDeleteButton.setBounds(250,425,235,22);
		
		cancelChangesButton = new JButton("Annuleren");
		cancelChangesButton.setBounds(127,455,235,22);
		
		// Attach aan panel
		userPanel.add(usernameSearchTextField);
		userPanel.add(usernameSearchLabel);
		userPanel.add(usernameSearchButton);
		userPanel.add(usernameLabel);
		userPanel.add(usernameTextField);
		userPanel.add(firstnameTextField);
		userPanel.add(firstnameLabel);
		userPanel.add(prefixTextField);
		userPanel.add(prefixLabel);
		userPanel.add(surnameTextField);
		userPanel.add(surnameLabel);
		userPanel.add(passwordTextField);
		userPanel.add(passwordLabel);
		//userPanel.add(changePasswordButton);
		userPanel.add(emailTextField);
		userPanel.add(emailLabel);
		userPanel.add(zipcodeTextField);
		userPanel.add(zipcodeLabel);
		userPanel.add(homenumberTextField);
		userPanel.add(homenumberLabel);
		userPanel.add(birthdateTextField);
		userPanel.add(birthdateLabel);
		userPanel.add(sexCombo);
		userPanel.add(sexLabel);
		userPanel.add(accountTypeCombo);
		userPanel.add(accountTypeLabel);
		userPanel.add(privacyLevelCombo);
		userPanel.add(privacyLevelLabel);
		userPanel.add(userDeleteButton);
		userPanel.add(userEditButton);
		userPanel.add(commitChangesButton);
		userPanel.add(cancelChangesButton);	
		userPanel.add(newUserButton);
		
		// Actionlisteners
		usernameSearchButton.addActionListener(new usernameSearchActionListener());
		userEditButton.addActionListener(new userEditActionListener());
		commitChangesButton.addActionListener(new commitChangesActionListener());
		cancelChangesButton.addActionListener(new cancelChangesActionListener());
		userDeleteButton.addActionListener(new userDeleteActionListener());
		newUserButton.addActionListener(new newUserActionListener());
		
		getContentPane().add(userPanel);
		
		enableFields(false);
		editMode(false);
		
		userPanel.updateUI();
	}
	
	private void enableFields(boolean bool)
	{
		usernameTextField.setEnabled(bool);
		firstnameTextField.setEnabled(bool);
		prefixTextField.setEnabled(bool);
		surnameTextField.setEnabled(bool);
		emailTextField.setEnabled(bool);
		zipcodeTextField.setEnabled(bool);
		passwordTextField.setEnabled(bool);
		homenumberTextField.setEnabled(bool);
		birthdateTextField.setEnabled(bool);
		sexCombo.setEnabled(bool);
		accountTypeCombo.setEnabled(bool);
		privacyLevelCombo.setEnabled(bool);		
	}
	
	private void clearFields(){
		usernameTextField.setText("");
		firstnameTextField.setText("");
		prefixTextField.setText("");
		surnameTextField.setText("");
		passwordTextField.setText("");
		emailTextField.setText("");
		zipcodeTextField.setText("");
		homenumberTextField.setText("");
		birthdateTextField.setText("");
		sexCombo.setSelectedItem("");
		accountTypeCombo.setSelectedItem("");
		privacyLevelCombo.setSelectedItem("");
	}
	
	private void editMode(Boolean bool){
		newUserButton.setEnabled(!bool);
		userDeleteButton.setEnabled(!bool);
		userEditButton.setEnabled(!bool);
		commitChangesButton.setEnabled(bool);
	}

	private void updateFields(String username) {
		User user = logic.getUser(username);
		
		if(user != null){
			usernameTextField.setText(user.getUsername());
			firstnameTextField.setText(user.getFirstName());
			prefixTextField.setText(user.getSurnamePrefix());
			surnameTextField.setText(user.getSurname());
			passwordTextField.setText(user.getPassword());
			emailTextField.setText(user.getEmail());
			zipcodeTextField.setText(user.getZipCode());
			homenumberTextField.setText(user.getHomeNumber());
			birthdateTextField.setText(dateformat.format(user.getBirthDate()));
			sexCombo.setSelectedItem(user.getSex());
			accountTypeCombo.setSelectedItem(Integer.toString(user.getAccountType()));
			privacyLevelCombo.setSelectedItem(Integer.toString(user.getPrivacyLevel()));
		}
		else{
			clearFields();
		}
	}

	private class usernameSearchActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String username = "";
			username = usernameSearchTextField.getText();
			User user = logic.getUser(username);
			usernameSearchTextField.setText("");
			
			if(user != null){
				updateFields(username);
			}
		}	
	}	
	
	private class userEditActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			editMode(true);
			enableFields(true);
			mode = "edit";
		}	
	}
	
	private class commitChangesActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			String username = "";
			try{
				username = usernameTextField.getText();
			}
			catch (Exception excp) {
				System.out.println("Invalid input!");
			}
			
			User user = logic.getUser(username);
			
			if(mode.equals("new")){
				user = new User();
			}
									
			user.setUsername(usernameTextField.getText());
			user.setFirstName(firstnameTextField.getText());
			user.setSurnamePrefix(prefixTextField.getText());
			user.setSurname(surnameTextField.getText());
			user.setPassword(passwordTextField.getText());
			user.setEmail(emailTextField.getText());
			user.setZipCode(zipcodeTextField.getText());
			user.setHomeNumber(homenumberTextField.getText());
			try {
				user.setBirthDate(dateformat.parse(birthdateTextField.getText()));
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			user.setSex(sexCombo.getSelectedItem().toString());
			user.setAccountType(Integer.parseInt(accountTypeCombo.getSelectedItem().toString()));
			user.setPrivacyLevel(Integer.parseInt(privacyLevelCombo.getSelectedItem().toString()));

			logic.updateUser(user);
	
			editMode(false);
			enableFields(false);
		}
	}
	
	private class cancelChangesActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String username = "";
			username = usernameTextField.getText();
			updateFields(username);
			
			editMode(false);
			enableFields(false);
		}
	}
	
	private class userDeleteActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			String username = "";
			username = usernameTextField.getText();			
			logic.deleteUser(username);			
			clearFields();
		}
	}
	
	private class newUserActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			clearFields();
			enableFields(true);
			editMode(true);			
			mode = "new";
		}
	}
}

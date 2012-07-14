package uk.co.harryreeder.lunchnlearn.project;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class StaffUser extends User {
	
	//Main GUI Variable
	JLabel lblStaffLogoutWarning;
	
	//Staff GUI Variables
	Container sCont;
	JButton sbtnNew, sbtnDel, sbtnUpdate;
	ActionListener sbtnNew_Click, sbtnDel_Click, sbtnUpdate_Click;
		
	public StaffUser(String CustomerNumber) {
		super(CustomerNumber);
		addStaffLabelToGUI();
		createStaffGUI();
	}
	
	private void addStaffLabelToGUI(){
		lblStaffLogoutWarning = new JLabel("Remember to close the staff interface when finished.");
		cont.add(lblStaffLogoutWarning);
		lblStaffLogoutWarning.setLocation(20 , 200);
		lblStaffLogoutWarning.setSize(300, 20);
		lblStaffLogoutWarning.setForeground(Color.red);
	}
	
	private void createStaffGUI() {
		JFrame sf = new JFrame("Staff | SKU Banking System");
		sf.setVisible(true);
		sf.setSize(178, 255);
		sf.setLocation(350, 0);
		
		//MAI BUTTONS
		sbtnNew = new JButton("New Account");
		sbtnDel = new JButton("Delete Account");
		sbtnUpdate = new JButton("Update Account");
		
		//Set up the GUI
		sCont = sf.getContentPane();
		sCont.setBackground(Color.white);
		sCont.setLayout(null);
		sCont.add(sbtnNew);
		sCont.add(sbtnDel);
		sCont.add(sbtnUpdate);
		
		sbtnNew.setLocation(10, 10);
		sbtnNew.setSize(150, 23);
		sbtnNew_Click = new ActionListener() {
			public void actionPerformed(ActionEvent e){
				newAccount();
			}
		};
		sbtnNew.addActionListener(sbtnNew_Click);
		
		sbtnDel.setLocation(10, 39);
		sbtnDel.setSize(150, 23);
		sbtnDel_Click = new ActionListener() {
			public void actionPerformed(ActionEvent e){
				deleteAccount();
			}
		};
		sbtnDel.addActionListener(sbtnDel_Click);
		
		sbtnUpdate.setLocation(10, 68);
		sbtnUpdate.setSize(150, 23);
		sbtnUpdate_Click = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateAccount();
			}
		};
		sbtnUpdate.addActionListener(sbtnUpdate_Click);
	}
	
	private void newAccount() {
		String customerNumber = JOptionPane.showInputDialog("Please enter a customer number for the new user.");
		Admin a = new Admin(customerNumber);
		String input = JOptionPane.showInputDialog("What is the opening balance of the account?");
		double amount = Double.valueOf(input.trim());
		a.newUser(amount);
	}
	
	private void deleteAccount() {
		String customerNumber = JOptionPane.showInputDialog("Please enter the customer number of the account you wish to remove.");
		Admin a = new Admin(customerNumber);
		a.deleteUser();
	}
	private void updateAccount() {
		String customerNumber = JOptionPane.showInputDialog("Please enter the customer number of the account you wish to update");
		Admin a = new Admin(customerNumber);
		a.updateAccount();
	}
}

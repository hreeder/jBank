package uk.co.harryreeder.lunchnlearn.project;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class User extends JFrame {

	//GUI Variables
	Container cont;
	JLabel lblTitle, lblCustomerHeader, lblCustomerNumber, lblBalanceHeader, lblBalance;
	JButton btnWithdraw, btnDeposit, btnTransfer, btnLogOut;
	ActionListener btnWithdraw_Click, btnDeposit_Click, btnTransfer_Click, btnLogOut_Click;
	private String CustNo;
	Account ac;
	
	public User(String CustomerNumber) {
		CustNo = CustomerNumber;
		ac = new Account();
		ac.load(CustNo);
		createAccountGUI();
		updateCustomerDetails();
	}
	
	private void createAccountGUI() {
		//Create the window
		JFrame f = new JFrame("Customer | SKU Banking System");
		f.setVisible(true);
		f.setSize(350, 255);
		
		//Let's now create our objects that we've made variables for
		lblTitle = new JLabel("SKU Banking");
		lblCustomerHeader = new JLabel("Customer Number");
		lblBalanceHeader = new JLabel("Balance");
		lblCustomerNumber = new JLabel("000000");
		lblBalance = new JLabel("£0000.00");
		btnWithdraw = new JButton("Withdraw");
		btnDeposit = new JButton("Deposit");
		btnTransfer = new JButton("Transfer");
		btnLogOut = new JButton("Log Out");
		
		//Now we're going to start adding and positioning our controls, BORING SECTION AHOY!!
		cont = f.getContentPane();
		cont.setBackground(Color.white);
		cont.setLayout(null);
		cont.add(lblTitle);
		cont.add(lblCustomerHeader);
		cont.add(lblCustomerNumber);
		cont.add(lblBalanceHeader);
		cont.add(lblBalance);
		cont.add(btnWithdraw);
		cont.add(btnDeposit);
		cont.add(btnTransfer);
		cont.add(btnLogOut);
		
		lblTitle.setLocation(115, 9);
		lblTitle.setSize(135, 25);
		Font titleFont = new Font(lblTitle.getFont().getName(), lblTitle.getFont().getStyle(), 20);
		lblTitle.setFont(titleFont);
		
		lblCustomerHeader.setLocation(12, 56);
		lblCustomerHeader.setSize(200, 20);
		Font headerFont = new Font(lblTitle.getFont().getName(), lblTitle.getFont().getStyle(), 16);
		lblCustomerHeader.setFont(headerFont);
		
		lblBalanceHeader.setLocation(12, 114);
		lblBalanceHeader.setSize(200, 20);
		lblBalanceHeader.setFont(headerFont);
		
		lblCustomerNumber.setLocation(40, 80);
		lblCustomerNumber.setSize(100, 15);
		
		lblBalance.setLocation(40, 138);
		lblBalance.setSize(100, 15);
		
		btnWithdraw.setLocation(220, 56);
		btnWithdraw.setSize(100, 23);
		btnWithdraw_Click = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				withdraw();
			}
		};
		btnWithdraw.addActionListener(btnWithdraw_Click);
		
		btnDeposit.setLocation(220, 85);
		btnDeposit.setSize(100, 23);
		btnDeposit_Click = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				deposit();
			}
		};
		btnDeposit.addActionListener(btnDeposit_Click);
		
		btnTransfer.setLocation(220, 114);
		btnTransfer.setSize(100, 23);
		btnTransfer_Click = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				transfer();
			}
		};
		btnTransfer.addActionListener(btnTransfer_Click);
		
		btnLogOut.setLocation(220, 143);
		btnLogOut.setSize(100, 23);
		btnLogOut_Click = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				logOut();
			}
		};
		btnLogOut.addActionListener(btnLogOut_Click);
	}
	
	private void updateCustomerDetails(){
		lblCustomerNumber.setText(CustNo);
		Number bal;
		NumberFormat nf;
		Locale locale = Locale.UK;
		nf = NumberFormat.getCurrencyInstance(locale);
		try {
			bal = nf.parse(nf.format(ac.getBalance()));
			lblBalance.setText("£" + bal.toString());
		} catch (Exception e) {}
	}
	
	private void withdraw(){
		Transaction t = new Transaction(ac);
		String input = JOptionPane.showInputDialog("How much money would you like to withdraw?");
		double amount = Double.valueOf(input.trim());
		
		t.withdraw(amount);
		updateCustomerDetails();
	}
	
	private void deposit(){
		Transaction t = new Transaction(ac);
		String input = JOptionPane.showInputDialog("How much money would you like to deposit?");
		double amount = Double.valueOf(input.trim());
		
		t.deposit(amount);
		updateCustomerDetails();
	}
	
	private void transfer(){
		Transaction t = new Transaction(ac);
		String input = JOptionPane.showInputDialog("How much money would you like to transfer?");
		String destination = JOptionPane.showInputDialog("What is the account number of the account where you wish to transfer funds");
		double amount = Double.valueOf(input.trim());
		
		t.transfer(amount, destination);
		updateCustomerDetails();
	}
	
	private void logOut(){
		ac.close();
		System.exit(0);
	}
}

package uk.co.harryreeder.lunchnlearn.project;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.security.MessageDigest;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class BankingSystem1 extends JFrame {

	//GUI Variables
	Container cont;
	JLabel lblTitle, lblLogin, lblCustNo, lblPassword, lblInvalidLogin;
	JTextField txtCustNo;
	JPasswordField txtPassword;
	JButton btnLogin;
	ActionListener btnLogin_Click;
	
	public static void main(String[] args) {
		BankingSystem1 program = new BankingSystem1();
		program.run();
	}
	
	private void run() {
		createLoginGUI();
	}
	
	private void createLoginGUI(){
		//Window
		JFrame f = new JFrame("JKE Banking System");
		f.setVisible(true);
		f.setSize(249, 231);
		//Let's create our objects now before we go any further!
		lblTitle = new JLabel("JKE Banking");
		lblLogin = new JLabel("Login");
		lblCustNo = new JLabel("Customer Number:");
		lblPassword = new JLabel("Password:");
		lblInvalidLogin = new JLabel("Invalid Login");
		txtCustNo = new JTextField(8);
		txtPassword = new JPasswordField(32);
		btnLogin = new JButton("Login");
		
		//Below we are just going to set properties on the controls. Boring section ahoy!
		//You will probably only be interested in the last line where the action listener is assigned
		
		//Container
		cont = f.getContentPane();
		cont.setBackground(Color.white);
		cont.setLayout(null);
		cont.add(lblTitle);
		cont.add(lblLogin);
		cont.add(lblCustNo);
		cont.add(lblPassword);
		cont.add(lblInvalidLogin);
		cont.add(txtCustNo);
		cont.add(txtPassword);
		cont.add(btnLogin);
		//Label: Title
		lblTitle.setLocation(53,9);
		lblTitle.setSize(135, 25);
		Font titleFont = new Font(lblTitle.getFont().getName(), lblTitle.getFont().getStyle(), 20);
		lblTitle.setFont(titleFont);
		//Label: Login
		lblLogin.setLocation(100, 34);
		lblLogin.setSize(33, 20);
		//Label: Customer #
		lblCustNo.setLocation(12, 116);
		lblCustNo.setSize(120, 15);
		//Label: Password
		lblPassword.setLocation(12, 142);
		lblPassword.setSize(75, 15);
		//Label: Invalid Login
		lblInvalidLogin.setLocation(85, 70);
		lblInvalidLogin.setSize(120, 15);
		lblInvalidLogin.setForeground(Color.red);
		lblInvalidLogin.setVisible(false);
		//Textbox: Customer #
		txtCustNo.setLocation(125, 113);
		txtCustNo.setSize(100, 20);
		//Textbox: Password
		txtPassword.setLocation(125, 139);
		txtPassword.setSize(100, 20);
		//Button: Login
		btnLogin.setLocation(125, 165);
		btnLogin.setSize(100, 20);
		//ActionListener: LoginButton_Click
		btnLogin_Click = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doLogin();
			}
		};
		btnLogin.addActionListener(btnLogin_Click);
	}
	
	private void doLogin() {
		// Here we will try and log in
		// I will attempt to find the user's settings file and compare the entered password with the one on file
		// If the user login is successful then I will create a new session with the corresponding user type
		boolean success = false;
		boolean staffuser = false;
		
		//Set up what we need to get the password field.
		char[] Chars;
		String strPassword;
		Chars = txtPassword.getPassword();
		strPassword = new String(Chars);
		//And let's set up what we need to hash the user input and compare it with what is on file
		String hash;
		byte[] digest = null;
		
		try {
			//Get the bytes from the password string, in UTF-8
			byte[] bytes = strPassword.getBytes("UTF-8");
			//And setup a message digest in MD5 for this
			MessageDigest md = MessageDigest.getInstance("MD5");
			//Now that byte[] digest we're going to fill with the hash
			digest = md.digest(bytes);
			
			//Now load the text file with the customer number.txt into a buffered reader
			BufferedReader br = new BufferedReader(new FileReader(txtCustNo.getText() + ".txt"));
			System.out.println("Found " + txtCustNo.getText() + ".txt");
			System.out.println("Inputted Password, hashed: " + new String(digest));
			//Now read the first line, which should be the password hash, and compare it to the hash entered. 
			if (br.readLine().equals(new String(digest))) {
				//if it's true then we're authenticated
				success = true;

				//Let's check to see if this is a staff user or a normal user
				if (br.readLine().equals("S „e±ï¨vù‚Ø©ç>ï")){
					//Great, the second line reads that we're a staff user (that's a MD5 hash of staff that we compare)
					staffuser = true;
				} else {
					//OK, we're not a staff user, but we're still authenticated
					staffuser = false;
				}
			}//Close our BufferedReader, Finish our try and catch any exceptions, which we bin.
			br.close();
		} catch(Exception e){}
		
		
		//See if we did manage to log in
		if (success){
			//If we're a staff user (staffuser=true) then we'll create a user of type StaffUser, else create a user of type Customer
			if (staffuser){
				StaffUser user = new StaffUser(txtCustNo.getText());
			}else{
				User user = new User(txtCustNo.getText());
			}
			//For security reasons we should clear the text in the boxes
			txtCustNo.setText("");
			txtPassword.setText("");
		}else{
			//Let's clear the password and inform the user that the login failed
			txtPassword.setText("");
			lblInvalidLogin.setVisible(true);
		}
	}

}

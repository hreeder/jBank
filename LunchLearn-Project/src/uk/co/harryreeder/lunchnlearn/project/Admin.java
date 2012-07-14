package uk.co.harryreeder.lunchnlearn.project;

import java.security.MessageDigest;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

public class Admin {
	private String custNo;
	public Admin(String customerNumber) {
		custNo = customerNumber;
	}
	
	
	public void newUser(double initialBalance) {	
			JDialog dlg = new JDialog();
			int staff;
			String hash;
			staff = JOptionPane.showConfirmDialog(dlg, "Make new user admin?");
			switch (staff) {
			case 0:
				// staff = 0 means user pressed Yes.
				// New File - Line 1: hash; Line 2: Staff hash; Line 3: Balance;
				FileHandler f = new FileHandler(custNo);
				hash = createPasswordHash();
				f.createUserFile(hash, "S „e±ï¨vù‚Ø©ç>ï", initialBalance);
				break;
			case 1:
				// staff = 1 means user pressed No.
				// New File - Line 1: hash; Line 2: "not-a-staff-user"; Line 3: Balance;
				FileHandler f1 = new FileHandler(custNo);
				hash = createPasswordHash();
				f1.createUserFile(hash, "Not-a-Staff-User", initialBalance);
				break;
			case 2:
				// staff = 2 means user pressed Cancel.
				// Do nothing
				break;
			}
	}
	
	public void deleteUser() {
		FileHandler f = new FileHandler(custNo);
		JDialog dlg = new JDialog();
		Object[] options = {"Yes", "No"};
		if (JOptionPane.showOptionDialog(dlg, "Confirm deletion of " + custNo + ".txt", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[1]) == 0) {
			f.killFile();
		}
	}
	
	public void updateAccount() {
		FileHandler f = new FileHandler(custNo);
		//Able to update password, also able to update staff status
		String pass;
		String staff = f.getStaffLineFromeFile();
		Account a = new Account();
		a.load(custNo);
		double Balance = a.getBalance();
		
		JDialog dlg = new JDialog();
		Object[] options = {"Yes", "No"};
		
		if (JOptionPane.showOptionDialog(dlg, "Change Password?", "Password", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1])==0) {
			pass = createPasswordHash();
		} else {
			pass = f.getPasswordHashFromFile();
		}
		
		if (staff == "S „e±ï¨vù‚Ø©ç>ï") {
			if (JOptionPane.showOptionDialog(dlg, "The user is currently a staff user, do you wish to remove that privilege?", "Staff Status", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1])==0) {
				staff = "Not-a-staff-user";
			}
		} else {
			if (JOptionPane.showOptionDialog(dlg, "The user is NOT currently a staff user. Do you wish to make the user a staff user?", "Staff Status", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]) == 0) {
				staff="S „e±ï¨vù‚Ø©ç>ï";
			}
		}
		
		f.killFile();
		f.createUserFile(pass, staff, Balance);
	}
	
	private String createPasswordHash(){
		JPasswordField pass = new JPasswordField(32);
		JOptionPane.showConfirmDialog(null, pass, "Enter the password for the new user.", JOptionPane.OK_CANCEL_OPTION);
		char[] Chars;
		String strPassword;
		Chars = pass.getPassword();
		strPassword = new String(Chars);
		
		String hash;
		byte[] digest = null;
		
		try {
			byte [] bytes = strPassword.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("MD5");
			digest = md.digest(bytes);
			hash = new String(digest);
		}catch(Exception e){
			hash = "HASH FAILED. PLEASE TRY AGAIN";
		}
		
		return hash;
	}
}

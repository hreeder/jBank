package uk.co.harryreeder.lunchnlearn.project;

import java.io.File;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class Transaction {
	private Account acc;
	public Transaction(Account ac){
		acc = ac;
	}
	
	public void withdraw(double amount){
		double oldBalance = acc.getBalance();
		if (amount<oldBalance){
			//We can make a valid withdrawal
			acc.setBalance(oldBalance-amount);
		}else{
			//We can't make a valid withdrawal, let the user know this!
			JDialog dlg = null;
			JOptionPane.showMessageDialog(dlg, "You do not have the funds available to perform that operation", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void deposit (double amount){
		double oldBalance = acc.getBalance();
		acc.setBalance(amount + oldBalance);
	}
	
	public void transfer (double amount, String destCustNo){
		//First let's check to see if the destination account actually exists
		if (new File(destCustNo + ".txt").exists()){
			//If it does then we can go ahead and load the destination account up and start the transaction
			Account destination = new Account();
			destination.load(destCustNo);
		
			//Let's check to see if we can remove the money from the source account first
			double sourceOldBalance = acc.getBalance();
			
			if (amount<sourceOldBalance) {
				//Ok, that's valid, so now we should withdraw the amount from the source account, and insert it into the destination
				double destinationOldBalance = destination.getBalance();
				acc.setBalance(sourceOldBalance-amount);
				destination.setBalance(destinationOldBalance+amount);
			
				JDialog dlg = null;
				JOptionPane.showMessageDialog(dlg, "The funds have been transferred successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
			}else{
				//Let the user know they can't transfer that much money out of their account - they don't have that much money!
				JDialog dlg = null;
				JOptionPane.showMessageDialog(dlg, "You do not have the funds available to perform that operation", "Error", JOptionPane.ERROR_MESSAGE);
			}
			
		}else{
			//Let the user know that the destination account does not exist!
			JDialog dlg = null;
			JOptionPane.showMessageDialog(dlg, "The destination account does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}

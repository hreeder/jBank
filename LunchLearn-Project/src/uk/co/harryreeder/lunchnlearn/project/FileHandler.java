package uk.co.harryreeder.lunchnlearn.project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class FileHandler {
	private String custNo;
	public FileHandler(String customerNumber) {
		custNo = customerNumber;
	}
	
	public double getBalanceFromFile() {
		File f = new File(custNo + ".txt");
		try {
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			for (int i = 0; i<2; i++) {
				br.readLine();
			}
			String balance = br.readLine();
			br.close();
			return Double.valueOf(balance.trim()).doubleValue();
		} catch (Exception e) {return 0;}
	}
	
	public String getPasswordHashFromFile() {
		File f = new File(custNo + ".txt");
		try {
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String out = br.readLine();
			br.close();
			return out;
		} catch (Exception e) {return null;}
	}

	public String getStaffLineFromeFile() {
		File f = new File(custNo + ".txt");
		try {
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			for (int i = 0; i<1; i++) {
				br.readLine();
			}
			String out = br.readLine();
			br.close();
			return out;
		} catch (Exception e) {return null;}
	}
	
	public void createUserFile(String passHash, String staffLine, double initBalance) {
		String initialBalance = Double.toString(initBalance);
		File f = new File(custNo + ".txt");
		try {
			FileWriter fw = new FileWriter(f);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(passHash);
			bw.newLine();
			bw.write(staffLine);
			bw.newLine();
			bw.write(initialBalance);
			bw.close();
		} catch (Exception e) {}
	}
	
	public void killFile() {
		File f = new File(custNo + ".txt");
		f.delete();
			
		//Force some garbage collection, JIC.
		System.gc();
	}
}

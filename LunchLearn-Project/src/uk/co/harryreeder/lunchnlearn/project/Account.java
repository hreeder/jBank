package uk.co.harryreeder.lunchnlearn.project;

public class Account {
	private String CustNo;
	private double Balance;
	public Account(){
	}
	
	public void load(String CustomerNumber){
		CustNo = CustomerNumber;
		//Balance = 1024.32;
		FileHandler fh = new FileHandler(CustNo);
		Balance = fh.getBalanceFromFile();
	}
	
	public void close(){
		FileHandler fh = new FileHandler(CustNo);
		String passHash = fh.getPasswordHashFromFile();
		String staffLine = fh.getStaffLineFromeFile();
		fh.killFile();
		fh.createUserFile(passHash, staffLine, Balance);
	}
	
	public double getBalance(){
		return Balance;		
	}
	
	public void setBalance(double newBalance){
		Balance = newBalance;
	}
}

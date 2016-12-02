package net.huitel.csr.tp5.simulateur;

public class EmployeCaisse extends Thread {
	private Caisse mCaisse;

	public EmployeCaisse(Caisse caisse) {
		mCaisse = caisse;
	}
	
	
	@Override
	public void run() {
		while(true){
			mCaisse.gererCaisse();
		}
		
	}
}

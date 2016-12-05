package net.huitel.csr.tp5.partieA.threads;

import net.huitel.csr.tp5.partieA.ressources.Caisse;

/**
 * Classe representant un caissier.
 * Il gere la caisse tant que son Thread n'est pas tue manuellement.
 * @author alan
 *
 */
public class EmployeCaisse extends Thread {
	private Caisse mCaisse;

	public EmployeCaisse(Caisse caisse) {
		mCaisse = caisse;
	}
	
	
	@Override
	public void run() {
		while(true){
			try {
				mCaisse.gererCaisse();
			} catch (InterruptedException e) {
			}
		}
		
	}
}

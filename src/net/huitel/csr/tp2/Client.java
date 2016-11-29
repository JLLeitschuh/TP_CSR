package net.huitel.csr.tp2;

public class Client extends Thread {

	/** Numéro du client */
	int mNumClient;

	/** Site de depart */
	Site mDepart;

	/** Site d'arrivee */
	Site mArrivee;

	Client(int nbClients, Site depart, Site arrivee) {
		mNumClient = nbClients;
		mDepart = depart;
		mArrivee = arrivee;
	}

	@Override
	public void run() {
		// Affichage à l'arrivée du client sur le site de début de trajet
		System.out.println("(D) " + mDepart.getNumSite() + "\t" + "Client " + mNumClient + "\t\t\t" + mDepart.getStock());
		mDepart.prendreVelo();
		// Affichage au départ du client du site de début de trajet
		System.out.println("(D) " + mDepart.getNumSite() + "\t" + "\t\tClient " + mNumClient + "\t" + mDepart.getStock());
		
		// Représentation du temps de trajet entre le site de début de trajet et le site de fin de trajet. 
		try {
			sleep(100*(mArrivee.getNumSite() - mDepart.getNumSite()));
		} catch (InterruptedException e) {
		}
		
		// Affichage à l'arrivée du client au site de fin de trajet
		System.out.println("(F) " + mArrivee.getNumSite() + "\t" + "Client " + mNumClient + "\t\t\t" + mArrivee.getStock());
		mArrivee.poserVelo();
		// Affichage au départ du client du site de fin de trajet
		System.out.println("(F) " + mArrivee.getNumSite() + "\t" + "\t\tClient " + mNumClient + "\t" + mArrivee.getStock());
	}

}

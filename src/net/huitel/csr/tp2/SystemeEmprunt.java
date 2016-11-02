package net.huitel.csr.tp2;

import java.util.Random;

/* Importation de la classe Lecture. */

class SystemeEmprunt {

	/* Constantes (final indique que la valeur ne peut pas changer) */

	static final int NB_SITES = 5;
	static final int MAX_CLIENTS = 20;

	/* Ces attributs sont statiques */

	private Site[] mSites = new Site[NB_SITES];
	private Client[] mClients = new Client[MAX_CLIENTS];
	private int nbClients = 0;
	private Camion mCamion;

	/*
	 * Cette fonction cr�e un seul client � la fois (� la limite aucun). Elle
	 * renvoie vrai si et seulement si un client a �t� cr��. Elle renvoie faux
	 * d�s que la cr�ation des clients est termin�e.
	 */

	private boolean nouveauClient() {

		Site depart;
		Site arrivee;

		if (nbClients == MAX_CLIENTS) {
			System.out.println("Le nombre maximum de clients est" + " atteint.");
			return false;
		}

		depart = mSites[siteDepartAleatoire()];
		arrivee = mSites[siteArriveeAleatoire(depart.getNumSite())];

		mClients[nbClients] = new Client(nbClients, depart, arrivee);
		nbClients++;

		return true;

	}

	/**
	 * Génère un entier aléatoire entre le numéro de site minimum et le numéro de site maximum
	 * @return Numéro aléatoire de site de départ;
	 */
	private static int siteDepartAleatoire() {
		Random generateur = new Random();
		return generateur.nextInt(NB_SITES - 1);
	}

	/**
	 * Génère un entier aléatoire entre le numéro de site de départ et le numéro
	 * de site maximum
	 * 
	 * @param siteDepart
	 *            Numéro du site de départ du client
	 * @return Numéro aléatoire de site d'arrivée
	 */
	private static int siteArriveeAleatoire(int siteDepart) {
		Random generateur = new Random();
		int nombreAleatoire = generateur.nextInt(NB_SITES - siteDepart) + siteDepart;
		if(nombreAleatoire == siteDepart)
			nombreAleatoire++;
		return nombreAleatoire;
	}

	/*
	 * Constructeur. Il est appel� lors de l'instanciation du syst�me d'emprunt.
	 */

	SystemeEmprunt() {

		int i;

		/* Instanciation des sites */
		for (i = 0; i < NB_SITES; i++)
			mSites[i] = new Site(i);

		/* Instanciation du camion et des clients */
		mCamion = new Camion(mSites);
		while (nouveauClient()) {
		}
		System.out.println("Tableau listant les arrivées et départs sur sites.\n(D) = Site de début de trajet client\n(F) = Site de fin de trajet client\n");
		System.out.println("Site n°\tArrivée de\tDépart de\tNb vélos");
		/* Lancement des Threads client et camion */
		for (i = 0; i < mClients.length; i++) {
			mClients[i].start();
		}
		mCamion.setDaemon(true);
		mCamion.start();
	}

	/* Point d'entr�e du programme */

	public static void main(String[] args) {

		new SystemeEmprunt();

	}

} // class SystemeEmprunt
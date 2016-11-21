package net.huitel.csr.tp2;

class Site {

	// Constantes pour fioriture lors de l'affichage du processus
	private static final String CAMION1 = "\t.--------.__";
	private static final String CAMION2 = "\t|::::::::|[_I___,";
	private static final String CAMION3 = "\t|_.-.____I__.-~;|";
	private static final String CAMION4 = "\t `(_)--------(_)\"";

	/* Constantes associees au site */
	/** Stock initial */
	static final int STOCK_INIT = 6;
	/** Stock maximal */
	static final int STOCK_MAX = 10;
	/** Seuil à partir duquel le camion va charger les vélos excédentaires */
	static final int BORNE_SUP = 8;
	/**
	 * Seuil à partir duquel le camion va décharger des vélos pour arriver au
	 * stock initial
	 */
	static final int BORNE_INF = 2;

	/* Variables globales */
	private int mNumSite;

	/** Nombre de vélos en stock */
	private Integer mStock;

	Site(int numSite) {
		mNumSite = numSite;
		mStock = STOCK_INIT;
	}

	/**
	 * Un client prend un vélo du site
	 */
	synchronized void prendreVelo(Client client) {
		/*
		 * Le while est important car un notify d'un client prenant un vélo peut
		 * potentiellement réveiller un client en attente d'un vélo sur un autre
		 * site.
		 */
		while (mStock == 0)
			try {
				wait();
			} catch (InterruptedException e) {
			}
		mStock--;
		notify();
	}

	/**
	 * Un client pose un vélo sur le site.
	 */
	synchronized void poserVelo(Client client) {
		while (mStock == Site.STOCK_MAX)
			try {
				wait();
			} catch (InterruptedException e) {
			}
		mStock++;
		notify();
	}

	/**
	 * Gère tout le processus d'arrivée du camion d'équilibrage, son travail sur
	 * le site et son départ du site.
	 * 
	 * @param camion
	 *            Camion d'équilibrage chargé de la gestion du stock du site
	 */
	synchronized void gererStockVelos(Camion camion) {
		int stockCamionAvantIntervention = camion.getStock();
		int stockSiteAvantIntervention = this.getStock();
		
		// Charge ou décharge des vélos au site selon le besoin
		if (mStock > BORNE_SUP)
			chargerVelos(camion);
		else if (mStock < Site.BORNE_INF)
			dechargerVelos(camion);
		
		// Affiche avec une fioriture le numéro du site que le Camion
		// consulte, le stock du camion et le stock du site AVANT et
		// APRES intervention du camion
		System.out.print("\nCAMION sur site " + this.getNumSite() + "\t" + CAMION1 + "\n-> Remorque: "
				+ stockCamionAvantIntervention + " Stock du site:" + stockSiteAvantIntervention + CAMION2 + "\nAprès passage:\t\t" + CAMION3
				+ "\n-> Remorque: " + camion.getStock() + " Stock du site:" + this.getStock() + CAMION4
				+ "\n\nSite n°\tArrivée de\tDépart de\tNb vélos\n");

		// Signale à tous les clients que le stock à été modifié
		notifyAll();
	}

	/**
	 * Le camion en paramètre charge tous les vélos excédentaires du site.
	 * 
	 * @param camion
	 *            Camion chargé de la gestion du stock du site.
	 */
	void chargerVelos(Camion camion) {
		while (mStock > BORNE_SUP) {
			mStock--;
			camion.incrementerStock();
		}
	}

	/**
	 * Le camion décharge des vélos de sa remorque pour les mettre sur le site.
	 * 
	 * @param camion
	 *            Camion chargé de la gestion du stock du site.
	 */
	void dechargerVelos(Camion camion) {
		while (camion.getStock() > 0 && mStock < Site.STOCK_INIT) {
			mStock++;
			camion.decrementerStock();
		}
	}

	/**
	 * @return Nombre de vélos en stock sur le Site this
	 */
	int getStock() {
		return mStock;
	}

	/**
	 * Setter du stock du Site this
	 * 
	 * @param stock
	 */
	void setStock(int stock) {
		mStock = stock;
	}

	/**
	 * @return Numéro du Site this
	 */
	public int getNumSite() {
		return mNumSite;
	}

}
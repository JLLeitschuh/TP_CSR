package net.huitel.csr.tp2;

import net.huitel.csr.tp2.interfaces.TrajetCamion;

class Site implements TrajetCamion {

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

	/**
	 * Vaut true si le site est occupé par le camion d'équilibrage ou un client,
	 * false sinon
	 */
	private boolean siteEstOccupe;

	Site(int numSite) {
		mNumSite = numSite;
		mStock = STOCK_INIT;
		siteEstOccupe = false;
	}

	/**
	 * Un client prend un vélo du site
	 */
	synchronized void prendreVelo(Client client) {
		while (siteEstOccupe || mStock == 0)
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
		while (siteEstOccupe || mStock == Site.STOCK_MAX)
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
		// Signale son arrivée sur le site
		camionArrive();

		// Charge ou décharge des vélos au site selon le besoin
		if (mStock > BORNE_SUP)
			chargerVelos(camion);
		else if (mStock < Site.BORNE_INF)
			dechargerVelos(camion);

		// Signale son départ du site
		camionPart();
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


	int getStock() {
		return mStock;
	}

	void setStock(int stock) {
		mStock = stock;
	}

	public int getNumSite() {
		return mNumSite;
	}

	@Override
	public void camionArrive() {
		siteEstOccupe = true;
	}

	@Override
	public void camionPart() {
		siteEstOccupe = false;

	}

}
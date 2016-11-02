package net.huitel.csr.tp2;

public class Camion extends Thread {
	// Constantes pour fioriture lors de l'affichage du processus
	private static final String CAMION1 = "\t.--------.__";
	private static final String CAMION2 = "\t|::::::::|[_I___,";
	private static final String CAMION3 = "\t|_.-.____I__.-~;|";
	private static final String CAMION4 = "\t `(_)--------(_)\"";

	private Site[] mSites;
	private Integer mStockCamion;

	Camion(Site[] sites) {
		mSites = sites;
		mStockCamion = 0;
	}

	public Integer getStock() {
		return mStockCamion;
	}

	/**
	 * Incrémente le nombre de vélos dans la remorque du camion
	 */
	public void incrementerStock() {
		mStockCamion++;
	}

	/**
	 * Décrémente le nombre de vélos dans la remorque du camion
	 */
	public void decrementerStock() {
		mStockCamion--;
	}

	public void run() {
		int index = 0;
		Site siteCourant;

		while (true) {
			// Représentation du temps de trajet entre deux sites (on
			// suppose qu'il y a toujours le même temps de trajet)
			try {
				sleep(100);
			} catch (InterruptedException e) {
			}
			siteCourant = mSites[index];

			// Affiche avec une fioriture le numéro du site que le Camion
			// consulte, le stock du camion et le stock du site AVANT et
			// APRES intervention du camion
			int stockCamionCourant = mStockCamion;
			int stockSiteCourant = siteCourant.getStock();
			siteCourant.gererStockVelos(this);
			System.out.print("\nCAMION sur site " + siteCourant.getNumSite() + "\t" + CAMION1 +
					"\n-> Remorque: " + stockCamionCourant + " Stock du site:" + stockSiteCourant + CAMION2 +
					"\nAprès passage:\t\t" + CAMION3 + "\n-> Remorque: " + mStockCamion + " Stock du site:"
					+ siteCourant.getStock() + CAMION4 + "\n\nSite n°\tArrivée de\tDépart de\tNb vélos\n");

			index = (index + 1) % mSites.length;
		}
		/*
		 * for (index = 0; index < mSites.length; index++) { System.out
		 * .println("Site " + index + " -> " +
		 * mSites[index].getNbClientsEnAttente() + " clients en attente"); }
		 */
	}
}

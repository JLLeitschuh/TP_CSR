package net.huitel.csr.tp2;

public class Camion extends Thread {

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

			siteCourant.gererStockVelos(this);

			index = (index + 1) % mSites.length;
		}
	}
}

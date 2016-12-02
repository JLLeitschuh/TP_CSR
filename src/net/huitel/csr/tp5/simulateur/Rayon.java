package net.huitel.csr.tp5.simulateur;

import net.huitel.csr.tp5.Supermarche;

public class Rayon {
	private Produit mProduitContenu;
	private int mStock;

	public Rayon(Produit produit) {
		mProduitContenu = produit;
		mStock = Supermarche.RAYON_STOCK_INIT;
	}
	

	public Produit getmProduitContenu() {
		return mProduitContenu;
	}

	public void setmProduitContenu(Produit mProduitContenu) {
		this.mProduitContenu = mProduitContenu;
	}

	public int getStock() {
		return mStock;
	}

	public void setmStock(int mStock) {
		this.mStock = mStock;
	}

	/**
	 * Un client prend un produit dans le rayon
	 */
	synchronized void prendreProduit() {
		while (mStock == 0)
			try {
				wait();
			} catch (InterruptedException e) {
			}
		mStock--;
		notify();
	}
	
	/**
	 * Un chef de rayon ajoute un produit au rayon.
	 */
	synchronized void ajouterProduit() {
		while (mStock == Supermarche.RAYON_STOCK_MAX)
			try {
				wait();
			} catch (InterruptedException e) {
			}
		mStock++;
		notify();
	}
}

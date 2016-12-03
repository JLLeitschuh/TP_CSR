package net.huitel.csr.tp5.simulateur;

import net.huitel.csr.tp5.Supermarche;

public class Rayon {
	private Produit mProduitContenu;
	private int mStock;

	public Rayon(Produit produit) {
		mProduitContenu = produit;
		mStock = Supermarche.RAYON_STOCK_INIT;
	}

	public Produit getProduitContenu() {
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
	synchronized public void prendreProduit() {
		while (mStock == 0)
			try {
				wait();
			} catch (InterruptedException e) {
			}
		mStock--;
		notify();
	}

	synchronized public void gererStockRayon(ChefRayon chef) {
		while (chef.getProduitsPortes().get(this.getProduitContenu()) > 0 && getStock() < Supermarche.RAYON_STOCK_MAX) {
			System.out.println(
					"Chef " + mProduitContenu.toString() + ": " + chef.getProduitsPortes().get(mProduitContenu));
			ajouterProduit();
			chef.decrementerStock(this.getProduitContenu());
			System.out.println("\t\tRayon '" + this.getProduitContenu().toString() + "': " + mStock);
		}
		notifyAll();
	}

	/**
	 * Un chef de rayon ajoute un produit au rayon.
	 */
	private void ajouterProduit() {
		while (mStock == Supermarche.RAYON_STOCK_MAX)
			try {
				wait();
			} catch (InterruptedException e) {
			}
		mStock++;
		notify();
	}
}

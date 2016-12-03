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
	 * 
	 * @throws InterruptedException
	 */
	public void prendreProduit(Client client) throws InterruptedException {
		while (mStock == 0){
			client.setEtat(EtatClient.ATTENTE_PRODUIT);
			wait();
		}
		client.setEtat(EtatClient.EN_COURSE);
		mStock--;
		notify();
	}

	synchronized public void prendreProduits(Client client, int quantiteVoulue) throws InterruptedException {
		for (int index = 0; index < quantiteVoulue; index++) {
			// System.out.println("\t\t\t\t\tClient " + client.numClient + "-" +
			// mProduitContenu.toString() + ": "
			// + client.nombreProduits(mProduitContenu));
			prendreProduit(client);
			client.getChariot().add(mProduitContenu);
			// System.out.println("\t\tRayon '" +
			// this.getProduitContenu().toString() + "': " + mStock);
		}
	}

	synchronized public void gererStockRayon(ChefRayon chef) throws InterruptedException {
		while (chef.getProduitsPortes().get(this.getProduitContenu()) > 0 && getStock() < Supermarche.RAYON_STOCK_MAX) {
			// System.out.println(
			// "Chef " + mProduitContenu.toString() + ": " +
			// chef.getProduitsPortes().get(mProduitContenu));
			ajouterProduit();
			chef.decrementerStock(this.getProduitContenu());
			// System.out.println("\t\tRayon '" +
			// this.getProduitContenu().toString() + "': " + mStock);
		}
		notifyAll();
	}

	/**
	 * Un chef de rayon ajoute un produit au rayon.
	 * 
	 * @throws InterruptedException
	 */
	private void ajouterProduit() throws InterruptedException {
		while (mStock == Supermarche.RAYON_STOCK_MAX)
			wait();
		mStock++;
		notify();
	}
}

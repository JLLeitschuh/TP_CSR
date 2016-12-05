package net.huitel.csr.tp5.partieA.ressources;

import net.huitel.csr.tp5.partieA.Supermarche;
import net.huitel.csr.tp5.partieA.enumerations.EtatClient;
import net.huitel.csr.tp5.partieA.enumerations.Produit;
import net.huitel.csr.tp5.partieA.threads.ChefRayon;
import net.huitel.csr.tp5.partieA.threads.Client;

/**
 * Classe representant un rayon du supermarche
 * 
 * @author alan
 *
 */
public class Rayon {
	/**
	 * Attribut du rayon indiquant quel est le produit qu'il propose
	 */
	private Produit mProduitContenu;

	/**
	 * Quantite restante du produit contenu dans le rayon
	 */
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
	 * Un client prend un produit dans le rayon. Un client attend que le chef de
	 * rayons remplisse le rayon si le stock du rayon est vide
	 * 
	 * @param client
	 *            Client voulant prendre un produit dans le rayon
	 * @throws InterruptedException
	 */
	synchronized public void prendreProduit(Client client) throws InterruptedException {
		while (mStock == 0) {
			client.setEtat(EtatClient.ATTENTE_PRODUIT);
			wait();
		}
		client.setEtat(EtatClient.EN_COURSE);
		mStock--;
		notifyAll();
	}

	/**
	 * Un client prend plusieurs produits dans le rayon. C'est la methode
	 * {@link #prendreProduit(Client)} qui doit etre atomique, c'est donc elle
	 * qui est synchronized et pas {@link #prendreProduits(Client, int)}
	 * 
	 * @param client
	 *            Client voulant prendre un produit dans le rayon
	 * @param quantiteVoulue
	 *            Quantite du produit voulue par le client
	 * @throws InterruptedException
	 */
	public void prendreProduits(Client client, int quantiteVoulue) throws InterruptedException {
		for (int index = 0; index < quantiteVoulue; index++) {
			System.out.println("\t\t\t\t\tClient " + client.getIdClient() + "-" + mProduitContenu.toString() + ": "
					+ client.nombreProduits(mProduitContenu));
			prendreProduit(client);
			client.getChariot().add(mProduitContenu);
			client.getListeCourses().put(mProduitContenu, client.getListeCourses().get(mProduitContenu) - 1);
			System.out.println("\t\tRayon '" + this.getProduitContenu().toString() + "': " + mStock);
		}
	}

	/**
	 * Methode appelee par le chef de rayons du supermarche pour remplir le
	 * rayon si besoin.
	 * 
	 * @param chef
	 *            Chef de rayon du supermarche
	 * @throws InterruptedException
	 */
	public void gererStockRayon(ChefRayon chef) throws InterruptedException {
		// Si le chef de rayons a sur lui au moins un produit que le rayon
		// propose et que le stock du rayon n'est pas plein
		while (chef.getProduitsPortes().get(this.getProduitContenu()) > 0 && getStock() < Supermarche.RAYON_STOCK_MAX) {
			System.out.println(
					"Chef " + mProduitContenu.toString() + ": " + chef.getProduitsPortes().get(mProduitContenu));
			ajouterProduit(chef);
			System.out.println("\t\tRayon '" + this.getProduitContenu().toString() + "': " + mStock);
		}
	}

	/**
	 * Un chef de rayon ajoute un produit au rayon.
	 * 
	 * @throws InterruptedException
	 */
	synchronized private void ajouterProduit(ChefRayon chef) throws InterruptedException {
		if (mStock < Supermarche.RAYON_STOCK_MAX) {
			mStock++;
			chef.decrementerStock(this.getProduitContenu());
			notifyAll();
		}
	}
}

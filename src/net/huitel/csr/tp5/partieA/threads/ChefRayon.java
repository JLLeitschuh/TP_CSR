package net.huitel.csr.tp5.partieA.threads;

import java.util.HashMap;
import java.util.List;

import net.huitel.csr.tp5.Supermarche;
import net.huitel.csr.tp5.partieA.Rayon;
import net.huitel.csr.tp5.partieA.enumerations.Produit;

/**
 * Classe representant le chef de rayons du supermarche.
 * 
 * @author alan
 *
 */
public class ChefRayon extends Thread {

	private List<Rayon> rayons;
	private HashMap<Produit, Integer> produitsPortes;

	public ChefRayon(List<Rayon> rayons) {
		this.rayons = rayons;
		produitsPortes = new HashMap<>();
	}

	/**
	 * Represente le fait d'aller dans l'entrepot pour remplir ses stocks de
	 * {@link Supermarche#NB_MAX_PRODUITS_PORTES_PAR_CHEF_RAYON} de
	 * chaque produit disponible pour renflouer les rayons.
	 * 
	 * @throws InterruptedException
	 */
	private void faireLePlein() throws InterruptedException {

		for (Rayon rayon : rayons) {
			produitsPortes.put(rayon.getProduitContenu(), Supermarche.NB_MAX_PRODUITS_PORTES_PAR_CHEF_RAYON);
		}
		// System.out.println("Chef (PLEIN)");
		sleep(Supermarche.TPS_CHEF_RAYON_FAIRE_PLEIN_ARTICLES);
	}

	/**
	 * Travail du chef de rayon. Il doit faire le plein puis parcourir les
	 * rayons pour les remplir si besoin. Le temps de ses actions est marque
	 * ici.
	 * 
	 * @throws InterruptedException
	 */
	private void gererStocksRayons() throws InterruptedException {
		faireLePlein();
		sleep(Supermarche.TPS_CHEF_RAYON_MARCHE_ENTRE_RAYONS);

		for (int index = 0; index < rayons.size(); index++) {
			Rayon rayonCourant = rayons.get(index);
			// System.out.println("\t\tRayon
			// '"+rayonCourant.getProduitContenu().toString()+"':
			// "+rayonCourant.getStock());
			rayonCourant.gererStockRayon(this);
		}

		sleep(Supermarche.TPS_CHEF_RAYON_MARCHE_ENTRE_RAYONS);

	}

	/**
	 * Decremente la quantite du produit en parametre que le chef de rayon porte
	 * sur lui
	 * 
	 * @param produit
	 *            Produit dont la quantite est a decrementer
	 */
	public void decrementerStock(Produit produit) {
		produitsPortes.put(produit, produitsPortes.get(produit) - 1);
	}

	public HashMap<Produit, Integer> getProduitsPortes() {
		return produitsPortes;
	}

	@Override
	public void run() {
		try {
			while (true) {
				gererStocksRayons();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

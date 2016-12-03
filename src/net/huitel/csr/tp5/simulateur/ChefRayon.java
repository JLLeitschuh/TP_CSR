package net.huitel.csr.tp5.simulateur;

import java.util.HashMap;
import java.util.List;

import net.huitel.csr.tp5.Supermarche;

public class ChefRayon extends Thread {

	private List<Rayon> rayons;
	private HashMap<Produit, Integer> produitsPortes;

	public ChefRayon(List<Rayon> rayons) {
		this.rayons = rayons;
		produitsPortes = new HashMap<>();
	}

	/**
	 * Represente pour le chef de rayon le fait d'aller dans l'entrepot pour
	 * chercher {@link Supermarche#NB_MAX_PRODUITS_PORTES_PAR_CHEF_RAYON}
	 * artcles de chaque produit disponible pour renflouer les rayons.
	 * 
	 * @throws InterruptedException
	 */
	private void faireLePlein() throws InterruptedException {

		for (Rayon rayon : rayons) {
			produitsPortes.put(rayon.getProduitContenu(), Supermarche.NB_MAX_PRODUITS_PORTES_PAR_CHEF_RAYON);
		}
		System.out.println("Chef (PLEIN)");
		sleep(Supermarche.TPS_CHEF_RAYON_FAIRE_PLEIN_ARTICLES);
	}

	/**
	 * 
	 * @throws InterruptedException
	 */
	private void gererStocksRayons() throws InterruptedException {
		faireLePlein();
		sleep(Supermarche.TPS_CHEF_RAYON_MARCHE_ENTRE_RAYONS);

		for (int index = 0; index < rayons.size(); index++) {
			Rayon rayonCourant = rayons.get(index);
			System.out.println("\t\tRayon '"+rayonCourant.getProduitContenu().toString()+"': "+rayonCourant.getStock());
			rayonCourant.gererStockRayon(this);
		}

		sleep(Supermarche.TPS_CHEF_RAYON_MARCHE_ENTRE_RAYONS);

	}

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

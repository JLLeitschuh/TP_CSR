package net.huitel.csr.tp5.simulateur;

import java.util.ArrayList;
import java.util.List;

import net.huitel.csr.tp5.Supermarche;

public class ChefRayon extends Thread {

	private List<Rayon> rayons;
	private List<Integer> produitsPortes;

	public ChefRayon(List<Rayon> rayons) {
		super();
		this.rayons = rayons;
		produitsPortes = new ArrayList<>(rayons.size());
	}

	/**
	 * Represente pour le chef de rayon le fait d'aller dans l'entrepot pour
	 * chercher {@link Supermarche#NB_MAX_PRODUITS_PORTES_PAR_CHEF_RAYON}
	 * artcles de chaque produit disponible pour renflouer les rayons.
	 * 
	 * @throws InterruptedException
	 */
	private void faireLePlein() throws InterruptedException {

		for (int i = 0; i < produitsPortes.size(); i++) {
			produitsPortes.set(i, Supermarche.NB_MAX_PRODUITS_PORTES_PAR_CHEF_RAYON);
		}
		sleep(Supermarche.TPS_CHEF_RAYON_FAIRE_PLEIN_ARTICLES);
	}

	/**
	 * 
	 * @throws InterruptedException
	 */
	private void gererStocksRayons() throws InterruptedException {
		int i = 0;
		while (true) {
			faireLePlein();
			sleep(Supermarche.TPS_CHEF_RAYON_MARCHE_ENTRE_RAYONS);
			while (produitsPortes.get(i) > 0 && rayons.get(i).getStock() < Supermarche.RAYON_STOCK_MAX) {
				rayons.get(i).ajouterProduit();
				notifyAll();
				produitsPortes.set(i, produitsPortes.get(i)-1);
				sleep(Supermarche.TPS_CHEF_RAYON_MARCHE_ENTRE_RAYONS);
			}
		}
	}

	@Override
	public void run() {
		try {
			gererStocksRayons();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

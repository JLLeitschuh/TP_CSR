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
	
	private void faireLePlein(){
		for(int i=0; i<produitsPortes.size(); i++){
			produitsPortes.set(i, Supermarche.NB_MAX_PRODUITS_PORTES_PAR_CHEF_RAYON);
		}
	}

	@Override
	public void run() {
		int i = 0;
		while (true) {
			faireLePlein();
			for(int j = 0; i<rayons.size(); i++){
				
			}
			
			i = (i + 1) % rayons.size();
		}
	}
}

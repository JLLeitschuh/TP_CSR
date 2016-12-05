package net.huitel.csr.tp5.partieA;

import java.util.ArrayList;
import java.util.List;

import net.huitel.csr.tp5.partieA.ressources.Caisse;
import net.huitel.csr.tp5.partieA.ressources.Rayon;
import net.huitel.csr.tp5.partieA.ressources.TasDeChariots;
import net.huitel.csr.tp5.partieA.threads.Client;

/**
 * La classe Supermarche ne contenant que des constantes et une methode static
 * d'execution main(), StructureSupermarche permet de conserver une reference
 * sur tous les composants du supermarche necessaires a la creation de clients
 * et a la consultation des clients presents dans le magasin ainsi que de l'etat
 * du stock des rayons.
 * 
 * @author alan
 *
 */
public class StructureSupermarche {
	TasDeChariots chariots;
	List<Rayon> rayons;
	Caisse caisse;
	ArrayList<Client> clients;

	public StructureSupermarche(TasDeChariots chariots, List<Rayon> rayons, Caisse caisse, ArrayList<Client> clients) {
		this.chariots = chariots;
		this.rayons = rayons;
		this.caisse = caisse;
		this.clients = clients;
	}

	public TasDeChariots getChariots() {
		return chariots;
	}

	public List<Rayon> getRayons() {
		return rayons;
	}

	public Caisse getCaisse() {
		return caisse;
	}

	public ArrayList<Client> getClients() {
		return clients;
	}

}

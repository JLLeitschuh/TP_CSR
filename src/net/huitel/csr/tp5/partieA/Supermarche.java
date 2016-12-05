package net.huitel.csr.tp5.partieA;

import java.util.ArrayList;
import java.util.List;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Context;
import org.restlet.data.Protocol;

import net.huitel.csr.tp5.partieA.enumerations.Produit;
import net.huitel.csr.tp5.partieA.ressources.Caisse;
import net.huitel.csr.tp5.partieA.ressources.Rayon;
import net.huitel.csr.tp5.partieA.ressources.TasDeChariots;
import net.huitel.csr.tp5.partieA.threads.ChefRayon;
import net.huitel.csr.tp5.partieA.threads.Client;
import net.huitel.csr.tp5.partieA.threads.EmployeCaisse;
import net.huitel.csr.tp5.partieB.ConstantesApiRest;
import net.huitel.csr.tp5.partieB.SupermarcheRestApp;

/**
 * 
 * @author alan
 * @author gdenoual
 */
public class Supermarche {
	/**
	 * Stock initial present dans les rayons à l’ouverture du magasin
	 */
	public static final int RAYON_STOCK_INIT = 2;

	/**
	 * Nombre maximum d'exemplaires d'un produit dans un rayon.
	 */
	public static final int RAYON_STOCK_MAX = 14;

	/**
	 * Temps, en ms, de l'operation de marche entre les rayons d'un client
	 */
	public static final int TPS_CLIENT_MARCHE_ENTRE_RAYONS = 300;

	/**
	 * Temps, en ms, de l'operation de marche entre les rayons d'un chef de
	 * rayon
	 */
	public static final int TPS_CHEF_RAYON_MARCHE_ENTRE_RAYONS = 200;

	/**
	 * Temps, en ms, de l'operation de recuperation de produits dans l'entrepôt
	 * par un chef de rayon
	 */
	public static final int TPS_CHEF_RAYON_FAIRE_PLEIN_ARTICLES = 500;

	/**
	 * Temps, en ms, de l'operation de depot d'un article sur le tapis de caisse
	 */
	public static final int TPS_POSER_ARTICLE_SUR_TAPIS = 200;

	/**
	 * Nombre maximum d’objets presents sur le tapis de caisse
	 */
	public static final int TAILLE_TAPIS = 4;

	/**
	 * Nombre de chariots dans la file à l’ouverture du magasin
	 */
	public static final int NB_CHARIOTS = 10;

	/**
	 * Le chef de rayon peut transporter jusqu’à 5 exemplaires de chaque produit
	 * à la fois
	 */
	public static final int NB_MAX_PRODUITS_PORTES_PAR_CHEF_RAYON = 5;

	/**
	 * Cree une liste de rayons
	 * 
	 * @return Liste de rayons
	 */
	private static List<Rayon> genererRayons() {
		List<Rayon> rayons = new ArrayList<>();
		rayons.add(new Rayon(Produit.SUCRE));
		rayons.add(new Rayon(Produit.FARINE));
		rayons.add(new Rayon(Produit.BEURRE));
		rayons.add(new Rayon(Produit.LAIT));
		return rayons;
	}

	public static void main(String[] args) throws Exception {
		ArrayList<Client> clients = new ArrayList<>();
		List<Rayon> rayons = genererRayons();
		Caisse caisse = new Caisse();

		StructureSupermarche structure = new StructureSupermarche(new TasDeChariots(clients), rayons, caisse, clients);

		(new EmployeCaisse(caisse)).start();
		(new ChefRayon(rayons)).start();

		// Create a component
		Component component = new Component();
		Context context = component.getContext().createChildContext();
		component.getServers().add(Protocol.HTTP, ConstantesApiRest.PORT_APPLICATION);

		// Create an application
		Application application = new SupermarcheRestApp(structure, context);

		// Add the backend into component's context
		component.getDefaultHost().attach(application);
		// Start the component
		component.start();

	}
}

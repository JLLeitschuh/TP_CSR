package net.huitel.csr.tp5;

import java.util.ArrayList;
import java.util.List;

import net.huitel.csr.tp5.simulateur.Caisse;
import net.huitel.csr.tp5.simulateur.ChefRayon;
import net.huitel.csr.tp5.simulateur.Client;
import net.huitel.csr.tp5.simulateur.Produit;
import net.huitel.csr.tp5.simulateur.Rayon;

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
	public static final int TPS_POSER_ARTICLE_SUR_TAPIS = 20;

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
	
	public static void main(String[] args) {
		List<Rayon> rayons = new ArrayList<>();
		rayons.add(new Rayon(Produit.SUCRE));
		rayons.add(new Rayon(Produit.FARINE));
		rayons.add(new Rayon(Produit.BEURRE));
		rayons.add(new Rayon(Produit.LAIT));
		
		Caisse caisse = new Caisse();
		
		for(int i=0; i<20; i++){
			(new Client(rayons)).start();
		}
		
		(new ChefRayon(rayons)).run();
		
	}
}

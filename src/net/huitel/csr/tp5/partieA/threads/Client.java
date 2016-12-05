package net.huitel.csr.tp5.partieA.threads;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import net.huitel.csr.tp5.partieA.StructureSupermarche;
import net.huitel.csr.tp5.partieA.Supermarche;
import net.huitel.csr.tp5.partieA.enumerations.EtatClient;
import net.huitel.csr.tp5.partieA.enumerations.Produit;
import net.huitel.csr.tp5.partieA.ressources.Rayon;

public class Client extends Thread {
	StructureSupermarche mStructureSupermarche;
	private List<Produit> chariot;
	private HashMap<Produit, Integer> mListeDeCourses;
	private EtatClient mEtat;
	private int idClient;

	public Client(StructureSupermarche structureSupermarche, int id) {
		super();
		mStructureSupermarche = structureSupermarche;
		idClient = id;
		mListeDeCourses = listeCoursesAleatoire();
		chariot = new ArrayList<>();
	}

	/**
	 * @param produit
	 *            Produit dont on cherche la quantite presente dans le chariot
	 * @return La quantite, dans le chariot du client, du produit passe en
	 *         parametre.
	 */
	public int nombreProduits(Produit produit) {
		int nombreProduits = 0;
		for (int index = 0; index < chariot.size(); index++)
			nombreProduits = chariot.get(index) == produit ? nombreProduits + 1 : nombreProduits;

		return nombreProduits;
	}

	@Override
	public void run() {
		System.out.println("Client " + idClient + ": liste= " + mListeDeCourses.toString());
		try {
			mStructureSupermarche.getChariots().prendreChariot(this);
			faireCourses();
			mStructureSupermarche.getCaisse().arriverALaCaisse(this);
			mettreCoursesSurTapis();
			mStructureSupermarche.getChariots().reposerChariot(this);
		} catch (InterruptedException e) {
		}
	}

	/**
	 * Methode representant le parcours des rayons et la prise de tous les
	 * produits dont le client a besoin dans chaque rayon.
	 * 
	 * @throws InterruptedException
	 */
	private void faireCourses() throws InterruptedException {
		for (Rayon rayon : mStructureSupermarche.getRayons()) {
			int quantiteProduitVoulue = mListeDeCourses.get(rayon.getProduitContenu());
			rayon.prendreProduits(this, quantiteProduitVoulue);
		}
		System.out.println("Courses client " + idClient + " terminees");
	}

	/**
	 * Methode representant l'action de mettre le contenu du chariot du client
	 * sur le tapis de la caisse. Il lui faut
	 * {@link Supermarche#TPS_POSER_ARTICLE_SUR_TAPIS} ms pour poser un produit
	 * sur le tapis.
	 * 
	 * @throws InterruptedException
	 */
	private void mettreCoursesSurTapis() throws InterruptedException {
		// Tant que le chariot n'est pas vide, le client place un par un les
		// produits de son chariot sur le tapis
		while (!chariot.isEmpty()) {
			sleep(Supermarche.TPS_POSER_ARTICLE_SUR_TAPIS);
			// System.out.println("Client " + idClient + " met " +
			// chariot.get(0) + " sur le tapis");

			mStructureSupermarche.getCaisse().mettreProduitSurTapis(chariot.get(0));
			chariot.remove(0);

			// System.out.println("chariot client " + idClient + ": " +
			// chariot.toString() + "\n");
		}
		// Lorsque le client a vide son chariot, il place sur le tapis le
		// marqueur de client suivant
		mStructureSupermarche.getCaisse().mettreProduitSurTapis(Produit.MARQUEUR_CLIENT_SUIVANT);
	}

	/**
	 * Genere une liste de courses. Pour chaque produit du magasin, on y associe
	 * un entier entre 0 et 10 pour representer le besoin du client.
	 */
	public HashMap<Produit, Integer> listeCoursesAleatoire() {
		Random rand = new Random();
		HashMap<Produit, Integer> listeDeCourses = new HashMap<>();
		listeDeCourses.put(Produit.SUCRE, rand.nextInt(11));
		listeDeCourses.put(Produit.FARINE, rand.nextInt(11));
		listeDeCourses.put(Produit.BEURRE, rand.nextInt(11));
		listeDeCourses.put(Produit.LAIT, rand.nextInt(11));
		return listeDeCourses;
	}

	public List<Produit> getChariot() {
		return chariot;
	}

	public int getIdClient() {
		return idClient;
	}

	public EtatClient getEtat() {
		return mEtat;
	}

	public void setEtat(EtatClient mEtat) {
		this.mEtat = mEtat;
	}

	public HashMap<Produit, Integer> getListeCourses() {
		return mListeDeCourses;
	}

}

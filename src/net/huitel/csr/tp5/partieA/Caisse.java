package net.huitel.csr.tp5.partieA;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

import net.huitel.csr.tp5.Supermarche;
import net.huitel.csr.tp5.partieA.enumerations.EtatClient;
import net.huitel.csr.tp5.partieA.enumerations.Produit;
import net.huitel.csr.tp5.partieA.threads.Client;

/**
 * Classe gerant la caisse du magasin. Elle resout trois problemes de synchro:
 * Problème d'exclusion mutuelle (passage d'un seul client a la fois en caisse).
 * Problème de rendez-vous entre un client et un caissier (attente mutuelle).
 * Problème de producteur/consommateur (client met des produits sur le tapis
 * alors que le caissier les enleve)
 * 
 * @author alan
 *
 */
public class Caisse {
	/**
	 * Liste chainee representant le tapis de la caisse. L'ordre est conserve
	 * lors des acces au tapis. Le client utilise la methode
	 * {@link LinkedList#add(Object)} pour ajouter un Produit au tapis (a la
	 * fin). Le caissier utilise {@link LinkedList#removeFirst()} pour scanner
	 * un artcile du produit (le premier article mis sur le tapis). On obtient
	 * ainsi le comportement d'une pile FIFO
	 */
	private LinkedList<Produit> tapis;

	/**
	 * Mutex servant a gerer le probleme d'exclusion mutuelle (un seul client a
	 * la fois a la caisse)
	 */
	private Semaphore semaExclusionMutuelleAccesTapis;
	/**
	 * Semaphore servant a gerer le probleme de rencontre dans le processus
	 * d'utilisation de la caisse. Il represente le caissier qui est attendu par
	 * le client
	 */
	private Semaphore semaCaissier;
	/**
	 * Semaphore servant a gerer le probleme de rencontre dans le processus
	 * d'utilisation de la caisse. Il represente le client qui est attendu par
	 * le caissier
	 */
	private Semaphore semaClient;

	public Caisse() {
		tapis = new LinkedList<>();
		semaExclusionMutuelleAccesTapis = new Semaphore(1);
		semaCaissier = new Semaphore(0);
		semaClient = new Semaphore(0);
	}

	/**
	 * Methode utilisee par le caissier pour gerer un client. Il attend que le
	 * client ait besoin de lui, puis scanne tous ses articles et procede au
	 * paiement avant de donner la permission a un autre client d'acceder a la
	 * caisse.
	 * 
	 * @throws InterruptedException
	 */
	public void gererCaisse() throws InterruptedException {
		caissierAttendClient();
		while (scannerProduit()) {
		}
		// Paiement
		// System.out.println("Dernier article rencontré, passage au client
		// suivant");
		semaExclusionMutuelleAccesTapis.release();
	}

	/**
	 * Methode appelee par un client pour signaler son arrivee a la caisse.
	 * 
	 * @throws InterruptedException
	 */
	public void arriverALaCaisse(Client client) throws InterruptedException {
		// System.out.println("Client " + client.getIdClient() + " attend a la
		// caisse");
		// System.out.println(
		// "Client " + client.getIdClient() + " - " +
		// semaExclusionMutuelleAccesTapis.availablePermits());
		// Lorsqu'un client arrive, le semaphore semaExclusionMutuelleAccesTapis
		// lui donne l'acces au tapis s'il n'y a pas un autre client arrive
		// avant lui.
		client.setEtat(EtatClient.ATTENTE_CAISSE);
		semaExclusionMutuelleAccesTapis.acquire();
		client.setEtat(EtatClient.A_LA_CAISSE);
		// System.out.println(
		// "\n==================\nClient " + client.getIdClient() + " passe en
		// caisse");
		// Une fois l'acces au tapis obtenu, le client attend que le caissier
		// soit pret a le recevoir.
		clientAttendCaissier();
	}

	/**
	 * Gestion du rendez-vous client/caissier: le client a la caisse attend que
	 * le caissier soit pret a l'accueillir pour commencer a poser des produits
	 * sur le tapis de caisse.
	 * 
	 * @throws InterruptedException
	 */
	private void clientAttendCaissier() throws InterruptedException {
		semaCaissier.release();
		semaClient.acquire();
	}

	/**
	 * Gestion du rendez-vous client/caissier: le caissier attend qu'un client
	 * ait besoin de lui pour commencer a scanner des produits.
	 * 
	 * @throws InterruptedException
	 */
	private void caissierAttendClient() throws InterruptedException {
		semaClient.release();
		semaCaissier.acquire();
	}

	/**
	 * Methode utilisee par le client pour mettre un produit sur le tapis de
	 * caisse. C'est la partie production du probleme producteur/consommateur.
	 * Ce probleme est gere par des moniteurs, avec une condition d'attente liee
	 * a l'espace restant sur le tapis (le client attend avant de mettre un
	 * nouveau produit tant qu'il n'y a plus de place sur le tapis)
	 * 
	 * @param produit
	 *            Produit a mettre sur le tapis
	 * @throws InterruptedException
	 */
	synchronized public void mettreProduitSurTapis(Produit produit) throws InterruptedException {
		// System.out.println("Avant depot article - tapis: " +
		// tapis.toString());
		// Si tapis plein, on attend
		while (tapis.size() == Supermarche.TAILLE_TAPIS)
			wait();
		// Le tapis a au moins un emplacement libre, on ajoute donc le produit a
		// la fin du tapis
		tapis.add(produit);
		// System.out.println("Apres depot article - tapis: " +
		// tapis.toString());
		// Le client signale au caissier qu'un article a ete ajoute sur le
		// tapis
		notifyAll();
	}

	/**
	 * Methode utilisee par le caissier pour scanner un produit sur le tapis de
	 * caisse. C'est la partie consommation du probleme producteur/consommateur.
	 * Ce probleme est gere par des moniteurs, avec une condition d'attente liee
	 * a l'espace restant sur le tapis (le caissier attend tant que le tapis est
	 * vide)
	 * 
	 * @return true si le dernier article scanne n'est pas un marqueur de client
	 *         suivant et que le caissier doit continuer a scanner les produits
	 *         que le client pose sur le tapis, false sinon
	 * @throws InterruptedException
	 */
	synchronized private boolean scannerProduit() throws InterruptedException {
		while (tapis.size() == 0)
			wait();
		// System.out.println("Avant scan - tapis: " + tapis.toString());
		Produit articleScanne = tapis.removeFirst();
		// System.out.println("Après scan - tapis: " + tapis.toString());
		notifyAll();
		return articleScanne != Produit.MARQUEUR_CLIENT_SUIVANT;
	}

}

package net.huitel.csr.tp5.partieA;

import java.util.List;
import java.util.concurrent.Semaphore;

import net.huitel.csr.tp5.Supermarche;
import net.huitel.csr.tp5.partieA.enumerations.EtatClient;
import net.huitel.csr.tp5.partieA.threads.Client;

/**
 * Classe representant le tas de chariots a l'entree du supermarche. Pour
 * resoudre ce probleme, nous utilisons un semaphore initialise au nombre de
 * chariots du magasin. Chaque client viendra prendre un chariot utilisera la
 * mehode {@link Semaphore#acquire()} du semaphore et il utilisera
 * {@link Semaphore##release()} pour rendre un chariot. Apres avoir repose son
 * chariot, le client est retire de la liste de clients du supermarche
 * 
 * @author alan
 *
 */
public class TasDeChariots {
	private Semaphore chariots;
	private List<Client> clients;

	public TasDeChariots(List<Client> clients) {
		chariots = new Semaphore(Supermarche.NB_CHARIOTS);
		this.clients = clients;
		System.out.println("Tas de chariots initialisé: " + chariots.availablePermits() + " chariots disponibles");
	}

	/**
	 * Un client prend un chariot du tas de chariot
	 * 
	 * @param client
	 *            Client prenant un chariot
	 * @throws InterruptedException
	 */
	public void prendreChariot(Client client) throws InterruptedException {
		client.setEtat(EtatClient.ATTENTE_CHARIOT);
		chariots.acquire();
		client.setEtat(EtatClient.EN_COURSE);
		System.out.println("\nChariots restants: " + chariots.availablePermits());
	}

	/**
	 * 
	 * @param client
	 */
	public void reposerChariot(Client client) {
		chariots.release();
		// Apres avoir repose son chariot, le client est retire de la liste de
		// clients du supermarche
		clientPartDuSupermarche(client);
		System.out.println("\nChariots restants: " + chariots.availablePermits());
	}

	/**
	 * Retire de la liste de clients du supermarche le client en parametre.
	 * 
	 * @param client
	 */
	synchronized private void clientPartDuSupermarche(Client client) {
		Client clientCourrant;
		for (int i = 0; i < clients.size(); i++) {
			clientCourrant = clients.get(i);
			if (clientCourrant.equals(client)) {
				clients.remove(i);
			}
		}
	}
}

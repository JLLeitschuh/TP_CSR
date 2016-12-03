package net.huitel.csr.tp5.simulateur;

import java.util.concurrent.Semaphore;

import net.huitel.csr.tp5.Supermarche;

/**
 * Classe representant le tas de chariots a l'entree du supermarche. Pour
 * resoudre ce probleme, nous utilisons un semaphore initialise au nombre de
 * chariots du magasin. Chaque client viendra prendre un chariot utilisera la
 * mehode {@link Semaphore#acquire()} du semaphore et il utilisera
 * {@link Semaphore##release()} pour rendre un chariot.
 * 
 * @author alan
 *
 */
public class TasDeChariots {
	public Semaphore chariots;
	
	public TasDeChariots(){
		chariots = new Semaphore(Supermarche.NB_CHARIOTS);
		System.out.println("Tas de chariots initialisé: "+ chariots.availablePermits() + "chariots disponibles");
	}
	
	public void prendreChariot() throws InterruptedException {
		chariots.acquire();
		System.out.println("\nChariots restants: "+chariots.availablePermits());
	}

	public void reposerChariot() {
		chariots.release();
		System.out.println("\nChariots restants: "+chariots.availablePermits());
	}
}

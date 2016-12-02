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
	Semaphore chariots = new Semaphore(Supermarche.NB_CHARIOTS);

	public void prendreChariot() {
		try {
			chariots.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void reposerChariot() {
		chariots.release();
	}
}

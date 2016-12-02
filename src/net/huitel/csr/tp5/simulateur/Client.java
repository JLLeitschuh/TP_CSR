package net.huitel.csr.tp5.simulateur;

import java.util.List;
import java.util.Random;

public class Client extends Thread {
	private List<Rayon> rayonsAParcourir;
	private List<Produit> chariot;
	private List<Produit> listeDeCourse;
	private boolean attenteChariot;
	private boolean enCourses;
	private boolean attenteProduit;
	private boolean attenteCaisse;
	private boolean aLaCaisse;

	public Client(List<Rayon> rayons) {
		super();
		rayonsAParcourir = rayons;
	}

	@Override
	public void run() {

	}

	public void generer() {
		Random rand = new Random();
		int nombre = rand.nextInt(50);

		for (int i = 0; i < nombre; i++) {

			Random coolRaoul = new Random();
			int produit = coolRaoul.nextInt(4);

			switch (produit) {
			case 1:
				listeDeCourse.add(Produit.SUCRE);
				break;

			case 2:
				listeDeCourse.add(Produit.FARINE);
				break;
			case 3:
				listeDeCourse.add(Produit.LAIT);
				break;
			case 4:
				listeDeCourse.add(Produit.BEURRE);
				break;

			}

		}

	}
}

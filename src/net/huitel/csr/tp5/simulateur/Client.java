package net.huitel.csr.tp5.simulateur;

import java.util.List;

public class Client extends Thread{
	private List<Rayon> rayonsAParcourir;
	private List<Produit> chariot;
	private boolean attenteChariot;
	private boolean enCourses;
	private boolean attenteProduit;
	private boolean attenteCaisse;
	private boolean aLaCaisse;
	
	
	public Client(List<Rayon> rayons){
		super();
		rayonsAParcourir = rayons;
	}
	
	@Override
	public void run() {
		
	}
}

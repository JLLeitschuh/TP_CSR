package net.huitel.csr.tp5.simulateur;

import java.util.List;

public class Client extends Thread{
	private List<Rayon> rayonsAParcourir;
	
	
	public Client(List<Rayon> rayons){
		super();
		rayonsAParcourir = rayons;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
	}
}

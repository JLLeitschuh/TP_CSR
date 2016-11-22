package net.huitel.csr.tp3;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class FTPServeurThread extends Thread {
	private Socket localSocket;

	private ObjectOutputStream output;
	private ObjectInputStream input;

	private FileExplorerProtocol fep;
	private String reponse;

	public FTPServeurThread(Socket localSocket) {
		this.localSocket = localSocket;
		fep = new FileExplorerProtocol("/");
	}

	@Override
	public void run() {
		try {
			System.out.println("Connexion avec ");
			// getting the streams
			output = new ObjectOutputStream(localSocket.getOutputStream());
			input = new ObjectInputStream(localSocket.getInputStream());
			while (reponse==null || !reponse.equals("Bye.")) {
				// receiving and sending
				System.out.print("Réception de commande: ");
				String question = (String) input.readObject();
				System.out.println(question);
				
				// thinking
		        Thread.sleep(1000);
		        
				reponse = fep.processInput(question);
				System.out.println("Envoi de la réponse:" + reponse);
				output.writeObject(reponse);
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	} // work
}

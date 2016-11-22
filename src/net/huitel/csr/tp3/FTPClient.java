package net.huitel.csr.tp3;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class FTPClient {
	private InetAddress serverAddress;
	private Socket clientSocket;

	private ObjectOutputStream output;
	private ObjectInputStream input;

	public void work() {

		try {
			// connecting to the server
			System.out.print("Connecting to the server...");
			serverAddress = InetAddress.getByName("localhost");
			clientSocket = new Socket(serverAddress, Constantes.PORT_APPLICATION);
			System.out.println(" done.");

			// getting the streams
			output = new ObjectOutputStream(clientSocket.getOutputStream());
			input = new ObjectInputStream(clientSocket.getInputStream());

			// thinking
			Thread.sleep(2000);

			// sending a message
			System.out.println("Commande à envoyer: ");
			Scanner scanner = new Scanner(System.in);
			String resp  = "";
			while (!resp.equals("Bye.")) {
				output.writeObject(scanner.next());

				System.out.println("Message sent, waiting for the response...");
				resp = (String) input.readObject();
				System.out.println("Response received:\n" + resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	} // work

	public static void main(String argv[]) {
		new FTPClient().work();
	}

}

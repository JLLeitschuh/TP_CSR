package net.huitel.csr.tp3;

import java.net.ServerSocket;

public class FTPServeur {
	private ServerSocket serverSocket;

	public void work() {
		try {
			serverSocket = new ServerSocket(Constantes.PORT_APPLICATION);
			System.out.println("Server socket created, waiting for connection...");
			while (true){
				new FTPServeurThread(serverSocket.accept()).start();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	} // work

	public static void main(String argv[]) {
		new FTPServeur().work();
	}
}

package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

class AcceptingThread extends Thread {

	ServerSocket bossSocket;
	ArrayList<ClientThread> listeningThreads;

	Integer index = 0;

	/**
	 * @param bossSocket
	 */
	public AcceptingThread(ServerSocket bossSocket) {
		this.bossSocket = bossSocket;
		this.listeningThreads = new ArrayList<ClientThread>();
		start();
	}

	/**
	 *
	 */
	public void run() {
		try {
			while (true) {
				// bossSocket.
				Socket acceptSocket = bossSocket.accept();
				System.out.println("Communication attempted by port No:" + acceptSocket.getPort());
				this.listeningThreads.add(new ClientThread(acceptSocket));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * public byte[] getMessage() throws IOException { for (; index <
	 * this.listeningThreads.size(); index++) { // System.out.print(index);
	 * ReadThread rd = this.listeningThreads.get(index); byte[] msg =
	 * rd.getOut().poll(); if (msg != null) return msg; } index = 0; return null; }
	 */
}
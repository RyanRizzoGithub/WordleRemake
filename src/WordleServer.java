/**
 * @author Gregory Jenkins
 * 
 * CSC 335 Fall 2022
 * 
 * Run this to start the Wordle leaderboard server.
 * 
 * 
 * 
 */

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.net.InetAddress;

public class WordleServer
{
	private ArrayList<WordlePlayer> players = new ArrayList<>();

	public static void main(String[] args) throws Exception
	{
		System.out.println(InetAddress.getLocalHost());
		try (var listener = new ServerSocket(59896))
		{
			System.out.println("The Wordle Leaderboard server is running...");
			var pool = Executors.newCachedThreadPool();
			while (true)
			{
				pool.execute(new Updater(listener.accept()));
			}
		}
	}
	private static class Updater implements Runnable
	{
		private Socket socket;
		private ObjectInputStream inputFromClient;

		Updater(Socket socket) {
			this.socket = socket;
			try {
				this.inputFromClient = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		@Override
		public void run()
		{
			System.out.println("Connected: " + socket);
			while (socket.isConnected()) {
				try
				{
					Object obj = inputFromClient.readObject();
					WordlePlayer input = (WordlePlayer) obj;
				}
				catch (Exception e)
				{
					System.out.println("Error:" + socket);
				}
				finally
				{
					try {socket.close();}
					catch (IOException e) {}
					System.out.println("Closed: " + socket);
				}
			}
		}
	}
}

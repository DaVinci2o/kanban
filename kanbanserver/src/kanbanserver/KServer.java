package kanbanserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import control.ServerControl;
import model.Project;
import model.SimpleProject;


/**
 * Main thread of the server. Handles xml and storage of data.
 */
public class KServer {

	static List<ServerThread> serverThreads = Collections.synchronizedList(new ArrayList<ServerThread>());
	
	public static void main(String args[]) {

		// Getting port
		int port = 0;
		if (args.length == 1) {
			port = Integer.valueOf(args[0]);
		} else {
			throw new IllegalArgumentException("Server Error: Argument one should be the port.");
		}

		// Waiting for clients
		Socket connectionSocket = null;
		ServerSocket serverSocket = null;
		System.out.println("Server Message: Waiting for connection on port " + port + ".");
		try {
			serverSocket = new ServerSocket(port);
			while (true) {
				try {
					connectionSocket = serverSocket.accept();
					System.out.println("Server Message: Connection OK!");
					ServerThread serverThread = new ServerThread(connectionSocket);
					serverThread.start();
					synchronized (serverThreads) {
						serverThreads.add(serverThread);
					}

				} catch (Exception e) {
					e.printStackTrace();
					System.err.println("Server Error: Connection accept error!");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Server Error: New Server socket Error!");
		} finally {
			// Finally runs after the "endless while" for now, no need to stop
			// the servers main thread.
			if (serverSocket != null) {
				try {
					serverSocket.close();
					System.out.println("Server Message: serverSocket closed!");
				} catch (IOException ie) {
					System.out.println("Server Error: Error closing serverSocket.");
				}
			}
		}
	}
}

/*
 * One server thread per client, handles communication.
 */
class ServerThread extends Thread {

	ServerControl serverControl;
	Boolean receivingMessages = true;
	Object currentObject = null;
	ObjectOutputStream objectOutputStream = null;
	ObjectInputStream objectInputStream = null;
	Socket socket = null;

	public ServerThread(Socket socket) {
		this.socket = socket;
	}

	/*
	 * For now, returns dummy objects. Will get a list from the xml or from
	 * projects that are created from the xml.
	 */
	public void sendSimpleProjects(String userName) {
		try {
			System.out.println("Server Message: Sending SimpleProject 's.");
			objectOutputStream.writeObject(serverControl.getProjectsForUserLogin(userName));
		} catch (IOException e) {
			System.err.println("Server Error: Error sending list of simple objects to client.");
			e.printStackTrace();
		}
	}
	
	public void sendSimpleUser(String userName) {
		try {
			System.out.println("Server Message: Sending SimpleUser.");
			objectOutputStream.writeObject(serverControl.userLogin(userName));
		} catch (IOException e) {
			System.err.println("Server Error: Error sending list of simple objects to client.");
			e.printStackTrace();
		}
	}

	public void sendString(String str) {
		try {
			System.out.println("Server Message: Sending string " + str + " to client.");
			objectOutputStream.writeObject(str);
		} catch (IOException e) {
			System.err.println("Server Error: Error sending string object to client.");
			e.printStackTrace();
		}
	}

	public void run() {

		// Setup streams
		try {
			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectInputStream = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			System.err.println("Server Error: IO error in server thread");
		}

		// Listen for messages
		while (receivingMessages) {
			try {
				currentObject = objectInputStream.readObject();
				if (currentObject instanceof String) {
					System.out.println("Server Message: Received String Object");
					String switchString = (String) currentObject;
					String[] switchArray = switchString.split("|");
					if (switchArray.length == 2) {
						switchString = switchArray[0];
					}
					switch (switchString) {
//					case "SimpleProjects":
//						System.out.println("Server Message: Client is requesting SimpleProject 's");
//						sendSimpleProjects();
					case "Login":
						System.out.println("Server Message: Client is requesting SimpleProject 's");
						sendSimpleUser(switchArray[1]);
						sendSimpleProjects(switchArray[1]);
					}
				} else if (currentObject instanceof Project) {
					Project project = (Project) currentObject;
					if (project.getID().equals("-1")) {
						// neues XML anlegen
					} else {
						// XML raussuchen und updaten
					}
				} else {

					System.err.println("Server Error: Received object is not a String!?");
				}
			} catch (IOException | ClassNotFoundException | ClassCastException e) {
				// TODO: Connection lost, killing thread (kill, save data??,
				// reconnect, how to handle a broken connection?)
				System.err.println("Server Error: Lost connection to client.");
				receivingMessages = false;
			}
		}

		// Shutting down after end of receivingMessages.

		// TODO: Killing thread on lost connection as for now. When
		// reconnecting, a client will get a new thread. How to reconnect client
		// to old thread?
		try {
			System.out.println("Server Message: Server connection closing..");
			if (objectOutputStream != null) {
				objectOutputStream.close();
				System.out.println("Server Message: ObjectOutputStream closed!");
			}

			if (objectInputStream != null) {
				objectInputStream.close();
				System.out.println("Server Message: objectInputStream closed!");
			}

			if (socket != null) {
				socket.close();
				System.out.println("Server Message: Socket closed!");
			}

		} catch (IOException ie) {
			System.out.println("Server Error: Error closing Server socket, objectOutputStream or objectInputStream.");
		} finally {

			// Removing ServerThread from list.
			synchronized (KServer.serverThreads) {
				KServer.serverThreads.remove(this);
			}
		}
	}
}

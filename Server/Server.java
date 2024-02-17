package Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

public class Server {

    public void start() throws Exception {
       ServerSocket serverSocket = new ServerSocket(7777);
       BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
       Slot slot = new Slot();
        
        System.out.println(" Attesa di un socket client sulla porta 7777\n");
        while(true){
            Socket socket = serverSocket.accept();
            System.out.print("-- RICEZIONE DI UNA CHIMATA DI APERTURA DA " + socket.getPort() + "/" + socket.getLocalPort() + " -- \n");
            
            ServerThread serverThread = new ServerThread(socket, slot);
            slot.addClient(serverThread);
            serverThread.start();
        }
        
    }
    public static void main (String[] args) throws Exception {
        Server server = new Server();
        server.start();
    }
}

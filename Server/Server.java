package Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

public class Server {

    public void start() throws Exception {
       ServerSocket serverSocket = new ServerSocket(7777);
       BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
       Slot slot = new Slot();
        //Ciclo infinito di ascolto dei Client
        System.out.println(" Attesa di un socket client sulla porta 7777\n");
        while(true){
            Socket socket = serverSocket.accept();
            System.out.print("-- RICEZIONE DI UNA CHIMATA DI APERTURA DA " + socket.getPort() + "/" + socket.getLocalPort() + " -- \n");
            //avvia il processo per ogni client 
            ServerThread serverThread = new ServerThread(socket, slot);
            serverThread.start();
        }
        //stdIn.close();
        //serverSocket.close();
    }
    public static void main (String[] args) throws Exception {
        Server server = new Server();
        server.start();
    }
}

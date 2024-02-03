package Server;

import java.io.*;
import java.net.*;

public class ServerThread extends Thread{

    private Socket socket;
    private Slot slot;

    public ServerThread (Socket socket, Slot slot) {
        this.socket = socket;
        this.slot = slot;

    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            String[] response = new String[4];
            response[3] = Integer.toString(slot.getCash());
            out.writeUnshared(response);

            while(true) {
                String userInput = in.readLine();
                if (userInput == null || userInput.equals("QUIT")){
                    break;
                }else{

                    int bet = Integer.parseInt(userInput);
                    response = (String[])slot.spin(bet, socket);
                    out.writeUnshared(response);

                    System.out.print("Il Client " + socket.getPort() + "/" + socket.getLocalPort() +" ha puntato: " + userInput + " \n");
                    System.out.println(response[0] + " " + response[1] + " " + response[2] + " " + response[3] + " " + "\n");
                }
                /*System.out.println("e' uscito: " + simbols[r1[0]]  + " " + simbols[r1[1]] + " " + simbols[r1[2]] + "\n");
                System.out.println("e' uscito: " + simbols[r2[0]]  + " " + simbols[r2[1]] + " " + simbols[r2[2]] + "\n");
                System.out.println("e' uscito: " + simbols[r3[0]]  + " " + simbols[r3[1]] + " " + simbols[r3[2]] + "\n");*/

            }
            out.close();
            in.close();
            System.out.print(" -- RICEZIONE DI UNA CHIAMATA DI CHIUSURA DA " + socket.getPort() + "/" + socket.getLocalPort() + " -- \n");
            socket.close();
        }
        catch (IOException e) {
            System.out.println("IOException: " + e);
        }
    }
}

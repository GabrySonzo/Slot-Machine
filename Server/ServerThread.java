package Server;

import java.io.*;
import java.net.*;

public class ServerThread extends Thread{

    private Socket socket;

    public ServerThread (Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            String[] simbols = ["ciliegia","banana","mela","arancia","uva","diamante","spugna","volpe","tasso","sette"];
            boolean indovinato = false;
            System.out.println("Il numero da indovinare per " + socket.getPort() + "/" + socket.getLocalPort() + " e' " + number);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            while(true) {
                String userInput = in.readLine();
                if (userInput == null || userInput.equals("QUIT")){
                    indovinato = true;
                    break;
                }
                System.out.print("Il Client " + socket.getPort() + "/" + socket.getLocalPort() +" ha puntato: " + userInput + " ");
                int bet = Integer.parseInt(userInput);
                int[] r = new int[3];
                for(int i = 0; i < 3; i++){
                    r[i] = (int)(Math.random() * 9);
                }
                System.out.println("e' uscito: " + simbols[r[0]]  + " " + simbols[r[1]] + " " + simbols[r[2]]);
            }
            if(!indovinato){
                out.writeBytes(number + "\n");
                System.out.println("Il Client " + socket.getPort() + "/" + socket.getLocalPort() + " ha esaurito i tentativi");
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

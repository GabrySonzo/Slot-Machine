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
            String[] simbols = new String[]{"ciliegia","banana","mela","arancia","uva","diamante","spugna","volpe","tasso","sette"};
            int[] values = new int[]{2,4,5,8,10,20,50,80,100,1000};
            int cash = 10000;

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            String[] response = new String[4];
            //response[3] = cash + "";
            //out.writeObject(response);

            while(true) {
                String userInput = in.readLine();
                if (userInput == null || userInput.equals("QUIT")){
                    break;
                }
                System.out.print("Il Client " + socket.getPort() + "/" + socket.getLocalPort() +" ha puntato: " + userInput + " \n");
                int bet = Integer.parseInt(userInput);
                cash += bet;
                int[] r1 = new int[3];
                int[] r2 = new int[3];
                int[] r3 = new int[3];
                for(int i = 0; i < 3; i++){
                    r2[i] = (int)(Math.random() * 9);
                }
                for(int i = 0; i < 3; i++){
                    r3[i] = r2[i]+1;
                    if(r3[i] > 9){
                        r3[i] -= 10;
                    }
                }
                for(int i = 0; i < 3; i++){
                    r1[i] = r2[i]-1;
                    if(r1[i] < 0){
                        r1[i] += 10;
                    }
                }
            
                if(r2[0] == r2[1] && r2[1] == r2[2]){
                    bet *= values[r2[0]];
                    System.out.println("moltiplicatore:" + values[r2[0]]/2);
                    response[1] = "Hai fatto una tripletta";
                    response[2] = "x" + values[r2[0]]/2;
                }
                else if(r2[0] == r2[1] || r2[1] == r2[2]){
                    bet *= values[r2[1]]/2;
                    System.out.println("moltiplicatore:" + values[r2[1]]/2);
                    response[1] = "Hai fatto una doppia";
                    response[2] = "x" + values[r2[1]]/2;
                }
                else if(r2[0] == r2[2]){
                    bet *= values[r2[0]]/2;
                    System.out.println("moltiplicatore:" + values[r2[0]]/2);
                    response[1] = "Hai fatto una doppia";
                    response[2] = "x" + values[r2[0]]/2;
                }
                else{
                    bet = 0;
                    response[1] = "Hai perso";
                    response[2] = "x0";
                }
                
                System.out.println("e' uscito: " + simbols[r1[0]]  + " " + simbols[r1[1]] + " " + simbols[r1[2]] + "\n");
                System.out.println("e' uscito: " + simbols[r2[0]]  + " " + simbols[r2[1]] + " " + simbols[r2[2]] + "\n");
                System.out.println("e' uscito: " + simbols[r3[0]]  + " " + simbols[r3[1]] + " " + simbols[r3[2]] + "\n");
                
                cash -= bet;
                response[0] = bet + "";
                response[3] = cash + "";
                response[0] = "" + (int)(Math.random() * 100);
                System.out.println(response[0] + " " + response[1] + " " + response[2] + " " + response[3] + "\n");
                out.writeObject(response);
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

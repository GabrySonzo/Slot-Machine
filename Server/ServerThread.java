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

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            while(true) {
                String userInput = in.readLine();
                if (userInput == null || userInput.equals("QUIT")){
                    break;
                }
                System.out.print("Il Client " + socket.getPort() + "/" + socket.getLocalPort() +" ha puntato: " + userInput + " \n");
                int bet = Integer.parseInt(userInput);
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
                for(int i = 0; i < 3; i++){
                    if(r1[i] == r2[i] && r2[i] == r3[i]){
                        bet *= values[r1[i]];
                    }
                    else if(r1[i] == r2[i] || r2[i] == r3[i] || r1[i] == r3[i]){
                        bet *= values[r1[i]]/2;
                    }
                    else{
                        bet = 0;
                    }
                }
                System.out.println("e' uscito: " + simbols[r1[0]]  + " " + simbols[r1[1]] + " " + simbols[r1[2]] + "\n");
                System.out.println("e' uscito: " + simbols[r2[0]]  + " " + simbols[r2[1]] + " " + simbols[r2[2]] + "\n");
                System.out.println("e' uscito: " + simbols[r3[0]]  + " " + simbols[r3[1]] + " " + simbols[r3[2]] + "\n");
                out.writeBytes(bet + "\n");
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

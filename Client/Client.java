package Client;

import java.io.*;
import java.net.*;

public class Client { 

    public void start()throws IOException { 
        //prova
        //Connessione della Socket con il Server 
        Socket socket = new Socket("localhost", 7777); 

        //Stream di byte da passare al Socket
        DataOutputStream out = new DataOutputStream(socket.getOutputStream()); 
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in)); 
        System.out.print("Per disconnettersi dal Server scrivere: QUIT\n"); 

        //Ciclo infinito per inserimento testo del Client 
        int cash = 1000;
        while (true) 
        { 
            System.out.println("Hai " + cash + " soldi");
            System.out.print("Scrivi la tua puntata: ");
            String userInput = stdIn.readLine();
            cash -= Integer.parseInt(userInput);
            if (userInput.equals("QUIT")){
                break; 
            }else if((!userInput.matches("[0-9]+") || userInput.equals(""))){
                System.out.println("Inserire un numero");
                continue;
            }else if(Integer.parseInt(userInput) > cash){
                System.out.println("Non hai abbastanza soldi");
                continue;
            }
            out.writeBytes(userInput + '\n');
            cash += Integer.parseInt(in.readLine());
        } 
        out.writeBytes("QUIT\n");

        //Chiusura dello Stream e del Socket 
        out.close(); 
        in.close(); 
        socket.close(); 
    } 
    public static void main (String[] args) throws Exception { 
        Client tcpClient = new Client(); 
        tcpClient.start(); 
    } 
} 
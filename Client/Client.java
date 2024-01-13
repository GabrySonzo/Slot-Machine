package Client;

import java.io.*;
import java.net.*;

public class Client { 

    public void start()throws IOException { 
        //prova
        //Connessione della Socket con il Server 
        Socket socket = new Socket("localhost", 7777); 

        //Stream di byte da passare al Socke
        DataOutputStream out = new DataOutputStream(socket.getOutputStream()); 
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in)); 
        System.out.print("Per disconnettersi dal Server scrivere: QUIT\n"); 

        //Ciclo infinito per inserimento testo del Client 
        int i = 0;
        boolean indovinato = false;
        while (i<5) 
        { 
            System.out.print("Indovina il numero hai ancora " + (5-i) + " tentativi : ");
            String userInput = stdIn.readLine(); 
            if (userInput.equals("QUIT")){
                indovinato = true;
                break; 
            }else if((!userInput.matches("[0-9]+") || userInput.equals(""))){
                System.out.println("Inserire un numero");
                continue;
            }
            out.writeBytes(userInput + '\n');
            
            i++;
        } 
        if(!indovinato){
            out.writeBytes("QUIT\n");
            System.out.println("Hai perso il numero da indovinare era " + in.readLine());
        }
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
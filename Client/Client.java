package Client;

import java.io.*;
import java.net.*;

public class Client { 

    public void start()throws IOException, ClassNotFoundException{ 
        //prova
        //Connessione della Socket con il Server 
        Socket socket = new Socket("localhost", 7777); 

        //Stream di byte da passare al Socket
        DataOutputStream out = new DataOutputStream(socket.getOutputStream()); 
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream()); 
        //BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        //String[] serverResponse = {"","","",""};

        System.out.print("Per disconnettersi dal Server scrivere: QUIT\n"); 

        //Ciclo infinito per inserimento testo del Client 
        int cash = 1000;
        int slot = 0;

        try{
            String[] serverResponse = (String[])in.readObject();
            slot = Integer.parseInt(serverResponse[3]); 
         }catch(ClassNotFoundException e){
            System.out.println("Errore");
        }

        while (true) 
        { 
            System.out.println("Hai " + cash + " soldi");
            System.out.println("La slot ha " + slot + " soldi");
            System.out.print("Scrivi la tua puntata: ");
            String userInput = stdIn.readLine();
            if (userInput.equals("QUIT")){
                break; 
            }else if((!userInput.matches("[0-9]+") || userInput.equals(""))){
                System.out.println("Inserire un numero");
            }else if(Integer.parseInt(userInput) > cash){
                System.out.println("Non hai abbastanza soldi");
            }
            else{
                cash -= Integer.parseInt(userInput);
                out.writeBytes(userInput + '\n');

                String[] serverResponse = (String[])in.readObject();
                if(serverResponse[4] == "false"){
                    System.out.println("La slot ha finito i soldi, ha vinto l'utente" + serverResponse[5]);
                    break;
                }
                System.out.println(serverResponse[0] + " " + serverResponse[1] + " " + serverResponse[2] + " " + serverResponse[3] + " ");
                System.out.println(serverResponse[1] + "(" + serverResponse[2] + ")\n");
                cash += Integer.parseInt(serverResponse[0]);
                slot = Integer.parseInt(serverResponse[3]);
            }
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
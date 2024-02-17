package Client;

import java.io.*;
import java.net.*;

public class Client { 

    private Socket socket; 
    private DataOutputStream out; 
    private ObjectInputStream in;
    private BufferedReader stdIn;
    private int cash;
    private int slot;
    private String[] serverResponse;
    private ClientThread clientThread;

    public Client(){
        
        try{
            socket = new Socket("localhost", 7777); 
            out = new DataOutputStream(socket.getOutputStream()); 
            in = new ObjectInputStream(socket.getInputStream());
            stdIn = new BufferedReader(new InputStreamReader(System.in));
            cash = 1000;
            synchronized(in){
                serverResponse = (String[])in.readObject();
            }
            slot = Integer.parseInt(serverResponse[3]);
            clientThread = new ClientThread(this, in, out, slot);
            clientThread.start();
        }
        catch (Exception e) {
            System.out.println("IOException: " + e);
        }
    
    }
    public void start()throws IOException, ClassNotFoundException{ 

        System.out.print("Per disconnettersi dal Server scrivere: QUIT\n"); 

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

                synchronized(in){
                    serverResponse  = (String[])in.readObject();
                }
                //System.out.println(serverResponse[0] + " " + serverResponse[1] + " " + serverResponse[2] + " " + serverResponse[3] + " " + serverResponse[4] + " " + serverResponse[5] + "\n");
                if(serverResponse[4].equals("perso")){
                    System.out.println("La slot ha finito i soldi, ha vinto l'utente " + serverResponse[5]);
                    break;
                }
                System.out.println(serverResponse[1] + "(" + serverResponse[2] + ")\n");
                cash += Integer.parseInt(serverResponse[0]);
                slot = Integer.parseInt(serverResponse[3]);
            }
        } 
        out.writeBytes("QUIT\n");

        out.close(); 
        in.close(); 
        socket.close(); 
    } 

    public void print(String message){
        System.out.println(message);
    }

    public static void main (String[] args) throws Exception { 
        Client tcpClient = new Client(); 
        tcpClient.start(); 
    } 
} 